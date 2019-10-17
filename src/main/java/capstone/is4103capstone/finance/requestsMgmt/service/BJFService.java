package capstone.is4103capstone.finance.requestsMgmt.service;

import capstone.is4103capstone.admin.service.CostCenterService;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.BJF;
import capstone.is4103capstone.entities.finance.Project;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.Service;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.BjfRepository;
import capstone.is4103capstone.finance.admin.service.ServiceServ;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.BJFModel;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateBJFReq;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTListResponse;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.service.VendorService;
import capstone.is4103capstone.util.enums.BjfTypeEnum;
import capstone.is4103capstone.util.enums.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

    public BJFModel createBJF(CreateBJFReq req) throws Exception{
        Employee requester = employeeService.validateUser(req.getRequester());
        Vendor vendor = vendorService.validateVendor(req.getVendor());
        CostCenter cc = costCenterService.validateCostCenter(req.getCostCenter());
        Service service = serviceServ.validateService(req.getServiceOrProduct());
//        BjfTypeEnum type = req.getRequestType().equalsIgnoreCase("BAU")?BjfTypeEnum.BAU:BjfTypeEnum.PROJECT;

        BJF newBjf = new BJF();
        newBjf.setBjfType(req.getRequestType().equalsIgnoreCase("BAU")?BjfTypeEnum.BAU:BjfTypeEnum.PROJECT);
        newBjf.setServiceId(service.getId());
        newBjf.setCostCenter(cc);
        newBjf.setVendorId(vendor.getId());
        newBjf.setRequester(requester);

        newBjf.setRequestDescription(req.getJustification());
        newBjf.setAdditionalInfo(req.getAdditionalInfo());
        newBjf.setCurrencyCode(req.getCurrency());
        newBjf.setTotalAmt(req.getTotalBudget());

        if (req.getRequestType().equalsIgnoreCase("Project")){
            if (req.getOngoingCost().add(req.getProjectCost()).compareTo(req.getTotalBudget()) != 0){
                throw new Exception("Ongoing Cost + Project Cost not equal to total amount");
            }

            newBjf.setProjectCode(projectService.validateProject(req.getProject()).getCode());
            newBjf.setOngoingCost(req.getOngoingCost());
            newBjf.setProjectCost(req.getProjectCost());
        }








        return new BJFModel();
    }

    public BJFModel getBJFDetails(String idOrCode){
        return new BJFModel();
    }

    public List<BJFModel> getBJFByRequester(String userIdOrUsername){
        return new ArrayList<>();
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

}
