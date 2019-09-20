package capstone.is4103capstone.finance.admin.service;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.finance.BudgetCategory;
import capstone.is4103capstone.finance.Repository.BudgetCategoryRepository;
import capstone.is4103capstone.finance.admin.model.CategoryModel;
import capstone.is4103capstone.finance.admin.model.req.CreateBudgetCategoryRequest;
import capstone.is4103capstone.finance.admin.model.res.BudgetCategoryRes;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.util.FinanceEntityCodeHPGenerator;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import javafx.scene.chart.ScatterChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    BudgetCategoryRepository budgetCategoryRepository;
    @Autowired
    CountryRepository countryRepository;


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

            newCat = budgetCategoryRepository.save(newCat);
            newCat.setCountry(country);
            newCat.setCode(generateCode(budgetCategoryRepository, newCat));

            //below: unmanaged/change for reponse sending
//        newCat.setPermissionMap(null);
//        newCat.setBudgetSub1s(null);

            return new BudgetCategoryRes("Successfully created new category!", false, new CategoryModel(newCat.getObjectName(), newCat.getCode()));
        }catch (Exception e){
            return new BudgetCategoryRes(e.getMessage(),true,null);
        }
    }

//    public BudgetCategoryRes createCategory2(CreateBudgetCategoryRequest categoryRequest){
//        Country country = countryRepository.findCountryByCode(categoryRequest.getCountryCode());
//        if (country == null){
//            return new BudgetCategoryRes("Country Code does not exist!",true,null);
//        }
//
//        BudgetCategory newCat = new BudgetCategory(categoryRequest.getCategoryName());
//        newCat.setCreatedBy(categoryRequest.getUsername());
//        newCat.setHierachyPath(g.generateHierachyPath(country,newCat));
//
//        Authentication.configurePermissionMap(newCat);
//
//        newCat = budgetCategoryRepository.save(newCat);
//        newCat.setCountry(country);
//        newCat.setCode(generateCode(budgetCategoryRepository,newCat));
//
//
//
//        return new BudgetCategoryRes("Successfully created new category!",false,new Ca);
//    }

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
