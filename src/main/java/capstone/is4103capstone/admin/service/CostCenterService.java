package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.model.CostCenterModel;
import capstone.is4103capstone.admin.model.res.GetCostCenterListRes;
import capstone.is4103capstone.admin.repository.CostCenterRepository;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.Project;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CostCenterService {


    @Autowired
    CostCenterRepository costCenterRepository;

    //return a simplified cost center model
//    public JSONObject getCostCentersByUserSimple(){
//        JSONObject res = new JSONObject();
//        try{
//            List<CostCenter> ccList = retrieveCostCenterEntitiesByUser();
//
//            JSONArray list = new JSONArray();
//            for (CostCenter c: ccList){
//                JSONObject simpleCCModel = new JSONObject();
//                simpleCCModel.put("code",c.getCode());
//                simpleCCModel.put("id",c.getId());
//                simpleCCModel.put("teamCode",c.getTeam().getCode());
//                simpleCCModel.put("countryCode",c.getTeam().getBusinessUnit().getFunction().getCountry().getCode());
//                list.put(simpleCCModel);
//            }
//            res.put("costCenterList",list);
//            res.put("message","Successfully retrieved categories");
//            res.put("hasError",false);
//        }catch (Exception e){
//            res.put("message",e.getMessage());
//            res.put("hasError",true);
//        }
//        return res;
//
//    }


    public GetCostCenterListRes getCostCentersByUser(String username){
        try{
            List<CostCenter> ccList = new ArrayList<>();
            if (username.isEmpty() || username.length() == 0){
                ccList = costCenterRepository.findAll();
            }

            List<CostCenterModel> returnList = new ArrayList<>();

            if (ccList.size() == 0){
                throw new Exception("No cost center available for this user");
            }

            for (CostCenter c: ccList){
                if (c.getDeleted())
                    continue;

                CostCenterModel cModel = new CostCenterModel(c);
                returnList.add(cModel);
            }

            return new GetCostCenterListRes("Successfully retrieved the cost centers based on the user's access right",false,returnList);
        }catch (Exception e){
            e.printStackTrace();
            return new GetCostCenterListRes(e.getMessage(),true);
        }
    }

    private List<CostCenter> findAll(){
        //get session key, get user, get the permission map;
        //now assume retrieve all the cost centers;
        List<CostCenter> ccList = costCenterRepository.findAll();//TODO
        return ccList;
    }

    public CostCenter validateCostCenter(String idOrCode) throws EntityNotFoundException {
        Optional<CostCenter> cc = costCenterRepository.findById(idOrCode);
        if (cc.isPresent())
            if (!cc.get().getDeleted())
                return cc.get();
            else
                throw new EntityNotFoundException("Cost center code or id not valid");

        CostCenter e = costCenterRepository.findCostCenterByCode(idOrCode);
        if (e != null && !e.getDeleted()){
            e.getCostCenterManager();
            e.getBmApprover();
            e.getFunctionApprover();
            return e;
        }



        throw new EntityNotFoundException("Cost center code or id not valid");
    }

    public Employee getCCManager(String ccId){
        try{
            Employee e = costCenterRepository.findById(ccId).get().getCostCenterManager();
            return e;
        }catch (Exception ex){
            return null;
        }
    }
}
