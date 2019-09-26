package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.model.CostCenterModel;
import capstone.is4103capstone.admin.model.res.GetCostCentersRes;
import capstone.is4103capstone.admin.repository.CostCenterRepository;
import capstone.is4103capstone.entities.CostCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CostCenterService {


    @Autowired
    CostCenterRepository costCenterRepository;

    public GetCostCentersRes getCostCentersByUser(String username){
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

                CostCenterModel cModel = new CostCenterModel(c.getObjectName(),c.getCode(),c.getId(), c.getCountry().getCode(),c.getCostCenterManager() == null? "":c.getCostCenterManager().getId());
                returnList.add(cModel);
            }

            return new GetCostCentersRes("Successfully retrieved the cost centers based on the user's access right",false,returnList);
        }catch (Exception e){
            return new GetCostCentersRes(e.getMessage(),true);
        }
    }


}
