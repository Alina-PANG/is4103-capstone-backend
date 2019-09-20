package capstone.is4103capstone.finance.admin.service;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.finance.BudgetCategory;
import capstone.is4103capstone.entities.finance.BudgetSub1;
import capstone.is4103capstone.entities.finance.BudgetSub2;
import capstone.is4103capstone.finance.Repository.BudgetCategoryRepository;
import capstone.is4103capstone.finance.Repository.BudgetSub1Repository;
import capstone.is4103capstone.finance.Repository.BudgetSub2Repository;
import capstone.is4103capstone.finance.admin.model.CategoryModel;
import capstone.is4103capstone.finance.admin.model.Sub1Model;
import capstone.is4103capstone.finance.admin.model.req.CreateBudgetCategoryRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    BudgetCategoryRepository budgetCategoryRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    BudgetSub1Repository sub1Repository;
    @Autowired
    BudgetSub2Repository sub2Repository;


    public BudgetCategoryRes createCategory(CreateBudgetCategoryRequest categoryRequest){
        try {
            Country country = countryRepository.findCountryByCode(categoryRequest.getCountryCode());
            if (country == null) {
                return new BudgetCategoryRes("Country Code does not exist!", true, null);
            }

            BudgetCategory newCat = new BudgetCategory(categoryRequest.getCategoryName());
            newCat.setCreatedBy(categoryRequest.getUsername());
            newCat.setHierachyPath(g.generateHierachyPath(country, newCat));

            Authentication.configurePermissionMap(newCat);
            newCat.setCountry(country);
            newCat = budgetCategoryRepository.save(newCat);
            newCat.setCode(generateCode(budgetCategoryRepository, newCat));

            //below: unmanaged/change for reponse sending
//        newCat.setPermissionMap(null);
//        newCat.setBudgetSub1s(null);

            return new BudgetCategoryRes("Successfully created new category!", false, new CategoryModel(newCat.getObjectName(), newCat.getCode()));
        }catch (Exception e){
            return new BudgetCategoryRes(e.getMessage(),true,null);
        }
    }

    public BudgetCategoryRes updateBudgetName(UpdateCategoryReq updateCategoryReq){
        try {
            BudgetCategory catToUpdate = budgetCategoryRepository.findBudgetCategoryByCode(updateCategoryReq.getCode());
            if (catToUpdate == null){
                throw new Exception("Wrong budget category code!");
            }
            catToUpdate.setObjectName(updateCategoryReq.getNewName());
            catToUpdate.setLastModifiedBy(updateCategoryReq.getUsername());
            catToUpdate.setHierachyPath(g.generateHierachyPath(catToUpdate.getCountry(),catToUpdate));
            catToUpdate.setLastModifiedBy(updateCategoryReq.getUsername());
            catToUpdate = budgetCategoryRepository.save(catToUpdate);
            String newCode = generateCode(budgetCategoryRepository,catToUpdate);


            List<Sub1Model> sub1s = new ArrayList<>();
            for (BudgetSub1 sub: catToUpdate.getBudgetSub1s()){
                if (!sub.getDeleted()){
                    sub1s.add(new Sub1Model(sub.getObjectName(),sub.getCode()));
                }
            }

            return new BudgetCategoryRes("Successfully updated category name!", false, new CategoryModel(catToUpdate.getObjectName(), newCode,sub1s));
        }catch (Exception e){
            return new BudgetCategoryRes(e.getMessage(),true,null);
        }
    }

    public JSONObject viewCategoriesUnderCountry(String countryCode){
        JSONObject res = new JSONObject();
        try{
            Country c = countryRepository.findCountryByCode(countryCode);
            if (c == null){
                throw new Exception("Wrong contry code");
            }
            List<BudgetCategory> cats = budgetCategoryRepository.findBudgetCategoriesByCountry_Id(c.getId());

            JSONArray list = new JSONArray();
            for (BudgetCategory cat: cats){
                if (cat.getDeleted())
                    continue;
                JSONObject catModelJson = new JSONObject();
                catModelJson.put("name",cat.getObjectName());
                catModelJson.put("code",cat.getCode());
                list.put(catModelJson);
            }
            res.put("totalCategories",list.length());
            res.put("categories",list);
            res.put("message","Successfully retrieved categories");
            res.put("hasError",false);
        }catch (Exception e){
            res.put("message",e.getMessage());
            res.put("hasError",true);
        }
        return res;

    }

    @Transactional
    public JSONObject removeBudgetCategory(String catCode, String username){
        JSONObject res = new JSONObject();

        try{
            BudgetCategory cat = budgetCategoryRepository.findBudgetCategoryByCode(catCode);
            if (cat == null){
                throw new Exception("Wrong category code, cannot perform delete.");
            }

            cat.setDeleted(true);
            cat.setLastModifiedBy(username);
            for (BudgetSub1 sub1: cat.getBudgetSub1s()){
                sub1.setDeleted(true);
                sub1.setLastModifiedBy(username);
                List<BudgetSub2> sub2s = sub2Repository.getBudgetSub2sByBudgetSub1_Id(sub1.getId());
                for (BudgetSub2 sub2: sub2s){
                    sub2.setDeleted(true);
                    sub2.setLastModifiedBy(username);
                }
            }

            res.put("hasError",false);
            res.put("message","Successfully deleted category, all the sub categories under this category is also removed.");
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
