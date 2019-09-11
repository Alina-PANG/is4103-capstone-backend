package capstone.is4103capstone.finance.service;


import capstone.is4103capstone.finance.entity.BudgetCategory;
import capstone.is4103capstone.finance.entity.BudgetSub1;
import capstone.is4103capstone.finance.entity.BudgetSub2;
import capstone.is4103capstone.finance.repository.BudgetCategoryRepository;
import capstone.is4103capstone.finance.repository.BudgetSub1Repository;
import capstone.is4103capstone.finance.repository.BudgetSub2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetCategoryService {

    @Autowired
    BudgetCategoryRepository budgetCategoryRepository;
    @Autowired
    BudgetSub1Repository budgetSub1Repository;
    @Autowired
    BudgetSub2Repository budgetSub2Repository;

    public BudgetCategory getCategoryByCountryAndName(String country, String categoryName){
        List<BudgetCategory> category =  budgetCategoryRepository.findBudgetCategoryByCountryAndCategoryName(country,categoryName);
        System.out.println("Should only have 1:" + category.size());
        return category.get(0);
    }

    public List<BudgetSub1> getSub1UnderCategoryCountry(String categoryName, String country){
        return budgetSub1Repository.findBudgetSub1By(country,categoryName);
    }

    public BudgetCategory saveBudgetCategory(BudgetCategory category){
        return budgetCategoryRepository.saveAndFlush(category);
    }

    public BudgetSub1 addSub1(BudgetSub1 newSub1, Long categoryId){
        System.out.println("Add new sub1, categoryId input is " + categoryId);
        BudgetCategory category = budgetCategoryRepository.getOne(categoryId);
        System.out.println("Retrieved: " + category == null);
        System.out.println("Add new sub1, retrieved category " + category.getCategoryName());

        newSub1.setBelongToCategory(category);
        category.getSub1List().add(newSub1);
        newSub1 = budgetSub1Repository.saveAndFlush(newSub1);
        return newSub1;
    }

    public BudgetSub2 addSub2(BudgetSub2 newSub2, Long sub1Id){




        BudgetSub1 sub1 = budgetSub1Repository.getOne(sub1Id);
        newSub2.setBelongtoSub1(sub1);
        sub1.getSub2List().add(newSub2);
        newSub2 = budgetSub2Repository.save(newSub2);
        return newSub2;
    }

    public List<BudgetCategory> getAllCategoriesUnderCountry(String country){
        return budgetCategoryRepository.findBudgetCategoriesByCountry(country);
    }

    public List<BudgetCategory> getAllCategories(){

        return budgetCategoryRepository.findAll();
    }
}
