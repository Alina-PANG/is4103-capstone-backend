package capstone.is4103capstone.finance.admin.service;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.finance.BudgetCategory;
import capstone.is4103capstone.entities.finance.BudgetSub1;
import capstone.is4103capstone.entities.finance.BudgetSub2;
import capstone.is4103capstone.entities.finance.Merchandise;
import capstone.is4103capstone.finance.Repository.BudgetCategoryRepository;
import capstone.is4103capstone.finance.Repository.BudgetSub1Repository;
import capstone.is4103capstone.finance.Repository.BudgetSub2Repository;
import capstone.is4103capstone.finance.admin.model.CategoryModel;
import capstone.is4103capstone.finance.admin.model.MerchandiseModel;
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
public class Sub2Service {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    BudgetCategoryRepository budgetCategoryRepository;
    @Autowired
    BudgetSub1Repository sub1Repository;
    @Autowired
    BudgetSub2Repository sub2Repository;


    public BudgetCategoryRes createSub2(CreateSub1Sub2Request request){
        try {
            BudgetSub1 sub1 = sub1Repository.findBudgetSub1ByCode(request.getUpperCategoryCode());
            if (sub1 == null) {
                return new BudgetCategoryRes("The upper level Sub1 Category Code doesn't exist", true, null);
            }

            BudgetSub2 sub2 = new BudgetSub2(request.getName());
            sub2.setCreatedBy(request.getUsername());
            sub2.setLastModifiedBy(request.getUsername());
            sub2.setHierachyPath(g.generateHierachyPath(sub1,sub2));

            Authentication.configurePermissionMap(sub2);
            sub2.setBudgetSub1(sub1);
            sub1.getBudgetSub2s().add(sub2);

            sub1Repository.save(sub1);
            sub2 = sub2Repository.save(sub2);
            sub2.setCode(generateCode(sub2Repository,sub2));

            return new BudgetCategoryRes("Successfully created new sub2 category under "+sub2.getObjectName()+"!", false, new Sub2Model(sub2.getObjectName(), sub2.getCode(),sub1.getCode()));
        }catch (Exception e){
            return new BudgetCategoryRes(e.getMessage(),true,null);
        }
    }


    public BudgetCategoryRes updateSub2Name(UpdateCategoryReq updateCategoryReq){
        try {
            BudgetSub2 sub2ToUpdate = sub2Repository.findBudgetSub2ByCode(updateCategoryReq.getCode());
            if (sub2ToUpdate == null){
                throw new Exception("Wrong sub2 budget category code given!");
            }
            sub2ToUpdate.setObjectName(updateCategoryReq.getNewName());
            sub2ToUpdate.setLastModifiedBy(updateCategoryReq.getUsername());
            sub2ToUpdate.setHierachyPath(g.generateHierachyPath(sub2ToUpdate.getBudgetSub1(),sub2ToUpdate));
            sub2ToUpdate = sub2Repository.save(sub2ToUpdate);
            String newCode = generateCode(sub2Repository,sub2ToUpdate);


            List<MerchandiseModel> merchandises = new ArrayList<>();
            for (Merchandise m: sub2ToUpdate.getMerchandises()){
                MerchandiseModel model = new MerchandiseModel(m.getObjectName(),m.getCurrencyCode());
                merchandises.add(model);
            }

            return new BudgetCategoryRes("Successfully updated category name!", false, new Sub2Model(sub2ToUpdate.getObjectName(), newCode,sub2ToUpdate.getBudgetSub1().getCode(), merchandises));
        }catch (Exception e){
            return new BudgetCategoryRes(e.getMessage(),true,null);
        }
    }



    public JSONObject viewSub2sUnderSub1Code(String sub1Code){
        JSONObject res = new JSONObject();
        try{
            BudgetSub1 sub1 = sub1Repository.findBudgetSub1ByCode(sub1Code);
            if (sub1 == null){
                throw new Exception("Wrong category code");
            }

            List<BudgetSub2> sub2s = sub2Repository.getBudgetSub2SByBudgetSub1Id(sub1.getId());

            JSONArray list = new JSONArray();
            for (BudgetSub2 sub2: sub2s){
                JSONObject sub2ModelJson = new JSONObject();
                sub2ModelJson.put("name",sub2.getObjectName());
                sub2ModelJson.put("code",sub2.getCode());
                list.put(sub2ModelJson);
            }
            res.put("totalSub2s",list.length());
            res.put("sub2List",list);
            res.put("message","Successfully retrieved sub2 categories under Sub1Category["+sub1.getObjectName()+"]");
            res.put("hasError",false);
        }catch (Exception e){
            res.put("message",e.getMessage());
            res.put("hasError",true);
        }
        return res;
    }


    @Transactional
    public JSONObject removeSub2Category(String catCode, String username){
        JSONObject res = new JSONObject();

        try{
            BudgetSub2 sub2 = sub2Repository.findBudgetSub2ByCode(catCode);
            if (sub2 == null){
                throw new Exception("Wrong sub1 category code, cannot perform delete.");
            }

            sub2.setDeleted(true);
            sub2.setLastModifiedBy(username);

            res.put("hasError",false);
            res.put("message","Successfully deleted sub2 category.");
        }catch (Exception e){
            res.put("message",e.getMessage());
            res.put("hasError",true);
        }
        return res;
    }

    FinanceEntityCodeHPGenerator g = new FinanceEntityCodeHPGenerator();
    private String generateCode(JpaRepository repo, DBEntityTemplate entity){
        try {
            return g.generateCode(repo,entity);
        }catch (RepositoryEntityMismatchException e){
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
