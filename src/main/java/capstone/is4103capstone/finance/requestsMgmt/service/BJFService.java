package capstone.is4103capstone.finance.requestsMgmt.service;

import capstone.is4103capstone.admin.service.CostCenterService;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.BJF;
import capstone.is4103capstone.entities.finance.Project;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.Service;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.BjfRepository;
import capstone.is4103capstone.finance.Repository.PurchaseOrderRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.admin.service.ServiceServ;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.BJFAggregateModel;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.BJFModel;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateBJFReq;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.supplychain.service.VendorService;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.BJFStatusEnum;
import capstone.is4103capstone.util.enums.BjfTypeEnum;
import org.hibernate.envers.configuration.internal.metadata.EntityXmlMappingData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class BJFService {
    @Autowired
    BjfRepository bjfRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    CostCenterService costCenterService;
    @Autowired
    ProjectService projectService;
    @Autowired
    VendorService vendorService;
    @Autowired
    ServiceServ serviceServ;
    @Autowired
    PurchaseOrderRepository poRepository;

    //TODO: can't same person created another request for same item before approval
    public BJFModel createBJF(CreateBJFReq req, boolean isUpdate) throws Exception{

        BJF newBjf = null;
        boolean changeFromReject = false;
        if (isUpdate){
            System.out.println("Updating BJF");
            newBjf = validateBJF(req.getBjfId());
            if (!(newBjf.getBjfStatus().equals(BJFStatusEnum.REJECTED) &&
                    newBjf.getBjfStatus().equals(BJFStatusEnum.PENDING_APPROVAL))){
                //ALLOW Updating fields of the bjf
                throw new Exception("The status of the bjf is "+newBjf.getBjfStatus()+", you are not able to modify at this state.");
            }else if (newBjf.getBjfStatus().equals(BJFStatusEnum.REJECTED)){
                newBjf.setBjfStatus(BJFStatusEnum.PENDING_APPROVAL);
                changeFromReject = true;
            }
            req.cleanFields(newBjf);
        }else{
            newBjf = new BJF();
        }

        Employee requester = employeeService.validateUser(req.getRequester());
        Vendor vendor = vendorService.validateVendor(req.getVendor());
        CostCenter cc = costCenterService.validateCostCenter(req.getCostCenter());
        Service service = serviceServ.validateService(req.getServiceOrProduct());
//        BjfTypeEnum type = req.getRequestType().equalsIgnoreCase("BAU")?BjfTypeEnum.BAU:BjfTypeEnum.PROJECT;

        Employee approver = null;//= employeeService.getEmployeeEntityBySid(cc.getCostCenterManager().getSecurityId());
        if (req.getApprover() != null)
            approver = employeeService.validateUser(req.getApprover());
        if (approver == null)
            throw new Exception("Please select business approver!");

        newBjf.setServiceId(service.getId());
        newBjf.setCostCenter(cc);
        newBjf.setVendorId(vendor.getId());
        newBjf.setRequester(requester);
        newBjf.setApprover(approver);
        newBjf.setObjectName(service.getObjectName()+"-"+vendor.getObjectName());

        newBjf.setRequestDescription(req.getJustification());
        newBjf.setAdditionalInfo(req.getSponsor());
        newBjf.setCurrency("GBP");
        newBjf.setEstimatedBudget(req.getTotalBudget());

        newBjf.setSponsor(req.getSponsor());
        newBjf.setCapex(req.getCapex());
        newBjf.setCapexCurrency(req.getCapexCurr());
        newBjf.setRevex(req.getRevex());
        newBjf.setRevexCurrency(req.getRevexCurr());

        Project p = null;
        if (req.getProject() == null || !req.getProject().isEmpty()){
            p = projectService.validateProject(req.getProject());
            newBjf.setProjectCode(p.getCode());
            newBjf.setBjfType(BjfTypeEnum.PROJECT);
        }else
            newBjf.setBjfType(BjfTypeEnum.BAU);


        newBjf = bjfRepository.save(newBjf);
        String code = EntityCodeHPGeneration.getCode(bjfRepository,newBjf);
        newBjf.setCode(code);

        //create approval request
        if (!isUpdate || changeFromReject)
            ApprovalTicketService.createTicketAndSendEmail(requester,approver,newBjf,"BJF Submitted / Updated, please have a look", ApprovalTypeEnum.BJF);

        return new BJFModel(newBjf,service,vendor,null,p);
    }

    public void bjfApproval(ApprovalForRequest ticket){
        BJF bjf = validateBJF(ticket.getRequestedItemId());
        bjf.setApprovalStatus(ticket.getApprovalStatus());
        boolean isApproved = ticket.getApprovalStatus().equals(ApprovalStatusEnum.APPROVED);
        if (isApproved){
            bjf.setBjfStatus(BJFStatusEnum.APPROVED);
        }else{
            bjf.setBjfStatus(BJFStatusEnum.REJECTED);
        }
        bjfRepository.saveAndFlush(bjf);

    //TODO: can be approve or reject
        //approve -> go to outsourcing steps
    }

    public List<BJFAggregateModel> getBJFByApprover(String idOrUsername) throws Exception{
        Employee user = employeeService.validateUser(idOrUsername);
        List<BJFAggregateModel> myRequests = bjfRepository.getSimpleBJFsByApprover(user.getId());
        if (myRequests.isEmpty())
            throw new Exception("No records found.");

        return myRequests;
    }

    //TODO: collaborate with Outsourcing
    public void afterOutsourcing(String bjfId){
        //start purchase order steps
    }

    //TODO: Collaborate with Purchase Order
    public void afterPOUpdated(String poId){
        //need to update bjf purchase value.
    }

    public BJFModel getBJFDetails(String idOrCode) throws Exception{
        BJF bjf = validateBJF(idOrCode);
        Vendor vendor = vendorService.validateVendor(bjf.getVendorId());
        Service service = serviceServ.validateService(bjf.getServiceId());
        Project p = null;
        PurchaseOrder po = null;
        if (bjf.getBjfType().equals(BjfTypeEnum.PROJECT))
            p = projectService.validateProject(bjf.getProjectCode());
        if (bjf.getPurchaseOrderNumber() != null)
            po = poRepository.getOne(bjf.getPurchaseOrderNumber());

        return new BJFModel(bjf,service,vendor,po,p);
    }

    public List<BJFAggregateModel> getBJFByRequester(String userIdOrUsername) throws Exception{
        Employee user = employeeService.validateUser(userIdOrUsername);
        List<BJFAggregateModel> myRequests = bjfRepository.getSimpleBJFsByRequester(user.getId());
        if (myRequests.isEmpty())
            throw new Exception("No records found.");

        return myRequests;
    }

    public List<BJFAggregateModel> getBjfByVendorWithoutPurchaseOrder(String vendorId) throws Exception{
        Vendor vendor = vendorService.validateVendor(vendorId);
        List<BJFAggregateModel> allBjfsWithoutPO = bjfRepository.getBjfByVendorWithoutPurchaseOrder(vendor.getId());
        if (allBjfsWithoutPO.isEmpty())
            throw new Exception("No records of this vendor found.");
        return allBjfsWithoutPO;
    }

    public GeneralRes bjfItemReceived(String bjfId){

        return new GeneralRes();
    }

    public void purchaseOrderApproved(PurchaseOrder po){
        //update the 'committed' value;
    }

    public void purchaseOrderClosed(PurchaseOrder po){
        //update the 'actuals' spending;
    }

    public BJF validateBJF(String idOrCode) throws EntityNotFoundException{
        Optional<BJF> bjfOps = bjfRepository.findById(idOrCode);
        if (bjfOps.isPresent()){
            if (bjfOps.get().getDeleted())
                throw new EntityNotFoundException("BJF already removed");
            return bjfOps.get();
        }

        BJF e = bjfRepository.getBJFByCode(idOrCode);
        if (e != null || !e.getDeleted())
            return e;

        throw new EntityNotFoundException("BJF Code or Id not valid");

    }
}
