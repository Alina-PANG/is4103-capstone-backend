package capstone.is4103capstone.finance.requestsMgmt.service;

import capstone.is4103capstone.admin.service.CostCenterService;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.finance.Project;
import capstone.is4103capstone.finance.Repository.BjfRepository;
import capstone.is4103capstone.finance.admin.service.ServiceServ;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.BJFModel;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateBJFReq;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTFormResponse;
import capstone.is4103capstone.finance.requestsMgmt.model.res.TTListResponse;
import capstone.is4103capstone.supplychain.service.VendorService;
import capstone.is4103capstone.util.enums.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

    public BJFModel createBJF(CreateBJFReq req){

        return new BJFModel();
    }

    public BJFModel getBJFDetails(String idOrCode){
        return new BJFModel();
    }

    public List<BJFModel> getBJFByRequester(String userIdOrUsername){
        return new ArrayList<>();
    }



}
