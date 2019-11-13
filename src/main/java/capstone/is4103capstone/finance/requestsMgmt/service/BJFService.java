package capstone.is4103capstone.finance.requestsMgmt.service;

import capstone.is4103capstone.admin.service.CostCenterService;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.Outsourcing;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.BjfRepository;
import capstone.is4103capstone.finance.Repository.PlanLineItemRepository;
import capstone.is4103capstone.finance.Repository.PlanRepository;
import capstone.is4103capstone.finance.Repository.PurchaseOrderRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.admin.service.FXTableService;
import capstone.is4103capstone.finance.admin.service.ServiceServ;
import capstone.is4103capstone.finance.requestsMgmt.model.BjfAnalysisModel;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.BJFAggregateModel;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.BJFModel;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateBJFReq;
import capstone.is4103capstone.finance.requestsMgmt.model.res.BjfAnalysisResponse;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.service.ApprovalTicketService;
import capstone.is4103capstone.general.service.MailService;
import capstone.is4103capstone.general.service.WriteAuditTrail;
import capstone.is4103capstone.supplychain.Repository.ChildContractRepository;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.model.ContractAndChildAggre;
import capstone.is4103capstone.supplychain.service.OutsourcingService;
import capstone.is4103capstone.supplychain.service.VendorService;
import capstone.is4103capstone.util.Tools;
import capstone.is4103capstone.util.enums.*;
import org.hibernate.envers.configuration.internal.metadata.EntityXmlMappingData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import javax.tools.Tool;
import java.math.BigDecimal;
import java.util.*;

@org.springframework.stereotype.Service
public class BJFService {
    private static final Logger logger = LoggerFactory.getLogger(BJFService.class);
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
    @Autowired
    MailService mailService;
    @Autowired
    OutsourcingService outsourcingService;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    PlanLineItemRepository planLineItemRepository;
    @Autowired
    ChildContractRepository childContractRepository;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    FXTableService fxService;

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
        if (approver == null){
            approver = cc.getCostCenterManager();
        }
        if (approver == null){
            throw new Exception("Please select business approver!");
        }

        newBjf.setServiceId(service.getId());
        newBjf.setCostCenter(cc);
        newBjf.setVendorId(vendor.getId());
        newBjf.setRequester(requester);
        newBjf.setApprover(approver);
        newBjf.setObjectName(service.getObjectName()+"-"+vendor.getObjectName());

        newBjf.setRequestDescription(req.getJustification() == null? "":req.getJustification());
        newBjf.setAdditionalInfo("");
        newBjf.setCurrency("GBP");
        newBjf.setEstimatedBudget(req.getTotalBudget());
        newBjf.setCoverage(req.getCoverage());
        newBjf.setCompetitiveQuotesAvailable(req.getCompetitiveQuotesAvailable());

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
            ApprovalTicketService.createTicketAndSendEmail(requester,approver,newBjf,"To BJF BM Approver: BJF Submitted / Updated, please have a look", ApprovalTypeEnum.BJF);

        WriteAuditTrail.createExtendedAuditRecord(newBjf,OperationTypeEnum.WRITE);


        return new BJFModel(newBjf,service,vendor,null,p);
    }

    public void bjfApproval(ApprovalForRequest ticket) throws Exception{
        BJF bjf = validateBJF(ticket.getRequestedItemId());
        bjf.setApprovalStatus(ticket.getApprovalStatus());
        boolean isApproved = ticket.getApprovalStatus().equals(ApprovalStatusEnum.APPROVED);
        BJFModel model = getBJFDetails(bjf.getId());
        if (isApproved){
            try {
                Outsourcing record = outsourcingService.getOutsourcingRecordByServiceAndVendor(bjf.getServiceId(),bjf.getVendorId());
                System.out.println("has record before, directly go to purchase order team.");
                bjf.setBjfStatus(BJFStatusEnum.APPROVED);
                mailService.sendGeneralEmailDefaultEmail(bjf.getRequester().getEmail(),"["+bjf.getCode()+"] got approved.",
                        "Your BJF regarding service ["+model.getService().getName()+"] with vendor ["+model.getVendor().getName()+"] is approved. Purchase request is sent to the purchase order team. ");
            }catch (EntityNotFoundException ex){
                bjf.setBjfStatus(BJFStatusEnum.PENDING_OUTSOURCING_ASSESSMENT);
                System.out.println("Has no outsourcing record.");
                mailService.sendGeneralEmailDefaultEmail(bjf.getRequester().getEmail(),"[Reminder] Complete Outsourcing Assessment Form for BJF "+bjf.getCode(),
                        "Your BJF regarding service ["+model.getService().getName()+"] with vendor ["+model.getVendor().getName()+"] is approved, but needs outsourcing assessment. Please go to BJF management module to complete assessment form.");
            }
        }else{
            bjf.setBjfStatus(BJFStatusEnum.REJECTED);
        }
        bjfRepository.saveAndFlush(bjf);
        WriteAuditTrail.createExtendedAuditRecord(bjf,OperationTypeEnum.WRITE);

    //TODO: can be approve or reject
        //approve -> go to outsourcing steps
    }

    public List<BJFAggregateModel> getBJFByApprover(String idOrUsername) throws Exception{
        Employee user = employeeService.validateUser(idOrUsername);
        List<BJFAggregateModel> myRequests = bjfRepository.getSimpleBJFsByApprover(user.getId());
        if (myRequests.isEmpty())
            throw new Exception("No records found.");

        WriteAuditTrail.createExtendedAuditRecord(new BJF(),OperationTypeEnum.READ);

        return myRequests;
    }

    public JSONObject bjfAnalysis(String bjfId){
        JSONObject response = new JSONObject();
        try{
            int thisYear = Tools.getCalendarYear();

            BJF bjf = validateBJF(bjfId);
            Service service = serviceServ.validateService(bjf.getServiceId());
            Vendor vendor = vendorService.validateVendor(bjf.getVendorId());
            CostCenter cc = bjf.getCostCenter();
//            response.put("Analysis Title","BJF and related spending summary: Service["+service.getObjectName()+
//                    "], Vendor["+vendor.getObjectName()+"], Cost Center["+cc.getCode()+"].");
//            response.put("service",service.getCode());
//            response.put("vendor",vendor.getCode());

            //Contract and Service and Vendor
            //这里应该只需要check committed，也就是related，approved且已经有purchase order，
            // 也就是说到了PENDING_PAYMENTS & DELIVERED的status的今年的所有bjf里，

            List<PlanLineItem> allPlanItemsThisYear = planLineItemRepository.findItemsByServiceAndCostCenter(thisYear,service.getCode(),cc.getId());
            JSONObject budgetHistory = new JSONObject();
            if (allPlanItemsThisYear.isEmpty()){
                budgetHistory.put(thisYear+"Budget Amount",0);
                budgetHistory.put("message","Cost Center ["+cc.getCode()+"] doesn't not have budget/reforecast regarding service ["+service.getObjectName()+"].");
            }else{
                for (PlanLineItem pl: allPlanItemsThisYear){
                    if (pl.getItemType().equals(BudgetPlanEnum.BUDGET)){
                        budgetHistory.put(thisYear+" Budget Amount",pl.getBudgetAmountInGBP());
                    }else{
                        budgetHistory.put(getUsefulInfoInPlanItemCode(pl.getCode()),pl.getBudgetAmountInGBP());
                    }
                }

            }
            response.put("Budget History",budgetHistory);

            // for checking budget plan; committed:
            List<BJF> bjfsOfServiceInCC = bjfRepository.getBJFByServiceIdAndCostCenterId(service.getId(),cc.getId());
            BigDecimal commitedBudget = BigDecimal.ZERO;
            for (BJF e:bjfsOfServiceInCC){
                if (e.getDeleted() || !Tools.isSameYear(e.getCreatedDateTime(),thisYear))
                    continue;
                if (e.getBjfStatus().equals(BJFStatusEnum.POVALUE_UPDATED) ||e.getBjfStatus().equals(BJFStatusEnum.PENDING_PAYMENTS) || e.getBjfStatus().equals(BJFStatusEnum.DELIVERED)){
                    commitedBudget.add(e.getTotalSpending() == null || e.getTotalSpending().equals(BigDecimal.ZERO)?e.getEstimatedBudget():e.getTotalSpending());
                }
            }

            response.put(thisYear+" Commited Value within cost center",commitedBudget);
            response.put("BJF Estimated Budget",bjf.getEstimatedBudget());
            // current year
            // bjf status
            // this cost center
            // all bjf: to check spending against contract & 20k
            // only bjf by this cost center: check spending again budget planning;
            List<BJF> bjfsWithServiceVendor = bjfRepository.getBJFByVendorIdAndServiceId(vendor.getId(),service.getId());
            BigDecimal yearlySpendingWithVendor = BigDecimal.ZERO;
            for (BJF e:bjfsWithServiceVendor){
                if (e.getDeleted() || Tools.isSameYear(e.getCreatedDateTime(),thisYear))
                    continue;
                if (e.getBjfStatus().equals(BJFStatusEnum.PENDING_PAYMENTS) || e.getBjfStatus().equals(BJFStatusEnum.DELIVERED)){
                    yearlySpendingWithVendor.add(e.getTotalSpending() == null || e.getTotalSpending().equals(BigDecimal.ZERO)?e.getEstimatedBudget():e.getTotalSpending());
                }
            }

            response.put(thisYear+" Commited Value with vendor",yearlySpendingWithVendor);

            JSONObject contractAnalysis = new JSONObject();

            List<ContractAndChildAggre> childContractInfos = childContractRepository.getContractInformationByServiceAndVendor(service.getCode(),vendor.getId());
            List<ContractAndChildAggre> activeChildContractInfos = new ArrayList<>();
            for (ContractAndChildAggre c: childContractInfos){
                if (c.getContractStatus().equals(ContractStatusEnum.MERGED) || c.getContractStatus().equals(ContractStatusEnum.ACTIVE)){
                    activeChildContractInfos.add(c);
                }
            }

            //check child contract
            //check master contact
            if (activeChildContractInfos.isEmpty()){
                // no active child contract available;
                contractAnalysis.put("Child Contract Status","No child contract available");
                List<Contract> masterContractList = contractRepository.findContractsByVendorId(vendor.getId());
                List<Contract> activeContracts = new ArrayList<>();
                for (Contract c: masterContractList){
                    if (c.getContractStatus().equals(ContractStatusEnum.MERGED) || c.getContractStatus().equals(ContractStatusEnum.ACTIVE)){
                        activeContracts.add(c);
                    }
                }
                if (activeContracts.isEmpty()){
                    contractAnalysis.put("Master Contract Status","No Master Contract available with Vendor"+vendor.getObjectName());
                    //really have no contract to check
                    contractAnalysis.put("With in 20k?",yearlySpendingWithVendor.compareTo(BigDecimal.valueOf(20000.0))<0);
                }else{
                    //has active master contract
                    BigDecimal totoalMasterContractValueWithVendor = BigDecimal.ZERO;
                    for (Contract c:activeContracts){
                        totoalMasterContractValueWithVendor.add(c.getTotalContractValue());
                    }
                    contractAnalysis.put("Master Contract Value",totoalMasterContractValueWithVendor);
                }
            }else{
                //has child contract
                if (activeChildContractInfos.size() > 1){
                    logger.warn("Mulitple child contract connected within vendor and service"+vendor.getId()+" "+service.getId());
                }
                ContractAndChildAggre childAggre = activeChildContractInfos.get(0);
                contractAnalysis.put("Child Contract Value",childAggre.getChildContractValue());
                contractAnalysis.put("Child Contract Code",childAggre.getChildContractCode());
                contractAnalysis.put("Contract Start Date",childAggre.getContractStartDate());
                contractAnalysis.put("Contract End Date",childAggre.getContractEndDate());
                contractAnalysis.put("Contract Status",childAggre.getContractStatus());
            }
            response.put("Contract Value Analysis",contractAnalysis);


            // check previuos purchase -> compare against contract value;
            // check budget plan money;
            // check contract value;
        }catch (Exception ex){
            response.put("hasError",true);
            response.put("errrorMessage",ex.getMessage());
        }
        return response;
    }

    //TODO: collaborate with Outsourcing haven't talk to XuHong
    public void afterOutsourcing(OutsourcingAssessment assessment){
        try{
            BJF bjf = assessment.getRelated_BJF();
            bjf.setBjfStatus(BJFStatusEnum.APPROVED);
            bjfRepository.saveAndFlush(bjf);
            mailService.sendGeneralEmailDefaultEmail(bjf.getRequester().getEmail(),"["+bjf.getCode()+"] got approved.",
                    "Your BJF ["+bjf.getCode()+"] is approved. Purchase request is sent to the purchase order team. ");

        }catch (Exception ex){
            ex.printStackTrace();
        }
        //start purchase order steps
    }

    //TODO: Already connect with Hangzhi
    public void afterPOUpdated(PurchaseOrder po){
        try{
            for (String bjfId:po.getRelatedBJF()){
                BJF bjf = validateBJF(bjfId);
                bjf.setBjfStatus(BJFStatusEnum.POVALUE_UPDATED);
                bjf.setPurchaseOrderNumber(po.getId());
                bjfRepository.saveAndFlush(bjf);
                mailService.sendGeneralEmailDefaultEmail(bjf.getRequester().getEmail(),"["+bjf.getCode()+"] Status Update",
                "Purchase order regarding your business request is already created. Operated by "+po.getCreatedBy()+"."
                );
            }
        }catch (Exception ex){
            logger.error("Internal Error, bjf id stored under purchase order ["+po.getId()+"] is invalid");
        }
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

    //TODO: Already connect with Hangzhi
    public void purchaseOrderApproved(PurchaseOrder po){
        try{
            for (String bjfId:po.getRelatedBJF()){
                BJF bjf = validateBJF(bjfId);
                bjf.setBjfStatus(BJFStatusEnum.PENDING_PAYMENTS);
                bjfRepository.saveAndFlush(bjf);
                mailService.sendGeneralEmailDefaultEmail(bjf.getRequester().getEmail(),"["+bjf.getCode()+"] Status Update",
                        "Purchase order regarding your business request is already approved by the PO team. Operated by "+po.getCreatedBy()+"."
                );
            }
        }catch (Exception ex){
            logger.error("Internal Error, bjf id stored under purchase order ["+po.getId()+"] is invalid");
        }
    }

    public void purchaseOrderClosed(PurchaseOrder po){
        for (String bjfId:po.getRelatedBJF()){
            BJF bjf = validateBJF(bjfId);
            bjf.setBjfStatus(BJFStatusEnum.DELIVERED);
            bjfRepository.saveAndFlush(bjf);
        }
        //update the 'actuals' spending;
    }

    public BJF validateBJF(String idOrCode) throws EntityNotFoundException{
        Optional<BJF> bjfOps = bjfRepository.findById(idOrCode);
        if (bjfOps.isPresent()){
            if (bjfOps.get().getDeleted()){
                throw new EntityNotFoundException("BJF already removed");
            }

            return bjfOps.get();
        }

        BJF e = bjfRepository.getBJFByCode(idOrCode);
        if (e != null){
            if (!e.getDeleted()){
                return e;
            }
        }

        throw new EntityNotFoundException("BJF Code or Id not valid");

    }


    private String getUsefulInfoInPlanItemCode(String code){
        String[] parts = code.split("-");
        return String.join("-",Arrays.copyOfRange(parts,1,4));
    }
}
