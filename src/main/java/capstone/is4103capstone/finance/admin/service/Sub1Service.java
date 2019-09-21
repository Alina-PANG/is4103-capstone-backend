package capstone.is4103capstone.finance.admin.service;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.finance.BudgetCategory;
import capstone.is4103capstone.entities.finance.BudgetSub1;
import capstone.is4103capstone.entities.finance.BudgetSub2;
import capstone.is4103capstone.finance.Repository.BudgetCategoryRepository;
import capstone.is4103capstone.finance.Repository.BudgetSub1Repository;
import capstone.is4103capstone.finance.Repository.BudgetSub2Repository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.admin.model.CategoryModel;
import capstone.is4103capstone.finance.admin.model.Sub1Model;
import capstone.is4103capstone.finance.admin.model.Sub2Model;
import capstone.is4103capstone.finance.admin.model.req.CreateSub1Sub2Request;
import capstone.is4103capstone.finance.admin.model.req.UpdateCategoryReq;
import capstone.is4103capstone.finance.admin.model.res.BudgetCategoryRes;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.util.FinanceEntityCodeHPGenerator;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class Sub1Service {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    BudgetCategoryRepository budgetCategoryRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    BudgetSub1Repository sub1Repository;
    @Autowired
    BudgetSub2Repository sub2Repository;


    public BudgetCategoryRes createSub1(CreateSub1Sub2Request request){
        try {
            BudgetCategory cat = budgetCategoryRepository.findBudgetCategoryByCode(request.getUpperCategoryCode());
            if (cat == null) {
                return new BudgetCategoryRes("Category Code doesn't exist", true, null);
            }

            boolean hasRepeatedName = checkWhetherSub1NameAlreadyExist(request.getName(),cat.getId());
            if (hasRepeatedName){
                throw new Exception("Sub1 Category '"+request.getName()+"' already exits under CAT["+cat.getObjectName()+"]");
            }


            BudgetSub1 sub1 = new BudgetSub1(request.getName());
            sub1.setCreatedBy(request.getUsername());
            sub1.setLastModifiedBy(request.getUsername());
            EntityCodeHPGeneration.setHP(cat,sub1);
            Authentication.configurePermissionMap(sub1);

            sub1.setBudgetCategory(cat);
            sub1 = sub1Repository.save(sub1);
            String code = EntityCodeHPGeneration.getCode(sub1Repository,sub1);
            sub1.setCode(code);

            return new BudgetCategoryRes("Successfully created new sub1 category under "+cat.getObjectName()+"!", false, new Sub1Model(sub1.getObjectName(), sub1.getCode(),cat.getCode()));
        }catch (Exception e){
            return new BudgetCategoryRes(e.getMessage(),true,null);
        }
    }


    public BudgetCategoryRes updateSub1Name(UpdateCategoryReq updateCategoryReq){
        try {
            BudgetSub1 sub1ToUpdate = sub1Repository.findBudgetSub1ByCode(updateCategoryReq.getCode());
            if (sub1ToUpdate == null){
                throw new Exception("Wrong sub1 budget category code!");
            }
            boolean hasRepeatedName = checkWhetherSub1NameAlreadyExist(updateCategoryReq.getNewName(),sub1ToUpdate.getBudgetCategory().getId());
            if (hasRepeatedName){
                throw new Exception("Sub1 category '"+updateCategoryReq.getNewName()+"' already exits under CAT["+sub1ToUpdate.getBudgetCategory().getObjectName()+"]");
            }

            sub1ToUpdate.setObjectName(updateCategoryReq.getNewName());
            sub1ToUpdate.setLastModifiedBy(updateCategoryReq.getUsername());

            EntityCodeHPGeneration.setHP(sub1ToUpdate.getBudgetCategory(),sub1ToUpdate);
            sub1ToUpdate = sub1Repository.save(sub1ToUpdate);
            String newCode = EntityCodeHPGeneration.getCode(sub1Repository,sub1ToUpdate);

            List<Sub2Model> sub2s = new ArrayList<>();
            for (BudgetSub2 sub: sub1ToUpdate.getBudgetSub2s()){
                if (!sub.getDeleted()){
                    sub2s.add(new Sub2Model(sub.getObjectName(),sub.getCode(),newCode));
            }
            }

            return new BudgetCategoryRes("Successfully updated category name!", false, new Sub1Model(sub1ToUpdate.getObjectName(), newCode,sub1ToUpdate.getBudgetCategory().getCode(),sub2s));
        }catch (Exception e){
            return new BudgetCategoryRes(e.getMessage(),true,null);
        }
    }



    public JSONObject viewSub1sUnderCategory(String categoryCode){
        JSONObject res = new JSONObject();
        try{
           BudgetCategory cat = budgetCategoryRepository.findBudgetCategoryByCode(categoryCode);
            if (cat == null){
                throw new Exception("Wrong category code");
            }

            List<BudgetSub1> sub1s = sub1Repository.findBudgetSub1sByBudgetCategoryId(cat.getId());

            JSONArray list = new JSONArray();
            for (BudgetSub1 sub1: sub1s){
                JSONObject sub1ModelJson = new JSONObject();
                sub1ModelJson.put("name",sub1.getObjectName());
                sub1ModelJson.put("code",sub1.getCode());
                list.put(sub1ModelJson);
            }
            res.put("totalSub1s",list.length());
            res.put("sub1List",list);
            res.put("message","Successfully retrieved sub1 categories under Category["+cat.getObjectName()+"]");
            res.put("hasError",false);
        }catch (Exception e){
            res.put("message",e.getMessage());
            res.put("hasError",true);
        }
        return res;

    }


    @Transactional
    public JSONObject removeSub1Category(String catCode, String username){
        JSONObject res = new JSONObject();

        try{
            BudgetSub1 sub1 = sub1Repository.findBudgetSub1ByCode(catCode);
            if (sub1 == null){
                throw new Exception("Wrong sub1 category code, cannot perform delete.");
            }

            sub1.setDeleted(true);
            sub1.setLastModifiedBy(username);
            for (BudgetSub2 sub2: sub1.getBudgetSub2s()){
                sub2.setDeleted(true);
                sub2.setLastModifiedBy(username);
            }

            res.put("hasError",false);
            res.put("message","Successfully deleted sub1 category, all the sub categories under this category is also removed.");
        }catch (Exception e){
            res.put("message",e.getMessage());
            res.put("hasError",true);
        }
        return res;
    }

    private boolean checkWhetherSub1NameAlreadyExist(String name, String catId){
        Integer numOfThisName = sub1Repository.countBudgetSub1NameByCategoryId(catId,name);
        if (numOfThisName > 0){
            return true;
        }
        return false;
    }

}
