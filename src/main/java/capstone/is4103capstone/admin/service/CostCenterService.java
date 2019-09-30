package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.model.CostCenterModel;
import capstone.is4103capstone.admin.model.res.GetCostCenterListRes;
import capstone.is4103capstone.admin.repository.CostCenterRepository;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CostCenterService {


    @Autowired
    CostCenterRepository costCenterRepository;

    public GetCostCenterListRes getCostCentersByUser(String username){
        try{
            //get session key, get user, get the permission map;
            //now assume retrieve all the cost centers;
            List<CostCenterModel> returnList = new ArrayList<>();
            List<CostCenter> ccList = costCenterRepository.findAll();//TODO

            if (ccList.size() == 0){
                throw new Exception("No cost center available for this user");
            }

            for (CostCenter c: ccList){
                if (c.getDeleted())
                    continue;

                GeneralEntityModel team = new GeneralEntityModel(c.getTeam());
                GeneralEntityModel costCenterManager = c.getCostCenterManager() == null? null : new GeneralEntityModel(c.getCostCenterManager());
                GeneralEntityModel bmApprover = new GeneralEntityModel(c.getBmApprover());
                GeneralEntityModel functionApprover = new GeneralEntityModel(c.getFunctionApprover());

                CostCenterModel cModel = new CostCenterModel(c.getObjectName(),c.getCode(),c.getId(),team,costCenterManager,bmApprover,functionApprover);
                returnList.add(cModel);
            }

            return new GetCostCenterListRes("Successfully retrieved the cost centers based on the user's access right",false,returnList);
        }catch (Exception e){
            return new GetCostCenterListRes(e.getMessage(),true);
        }
    }





}
