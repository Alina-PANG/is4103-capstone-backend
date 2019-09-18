package capstone.is4103capstone.finance;

import capstone.is4103capstone.admin.repository.CostCenterRepository;
import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.finance.Repository.*;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class FinanceInit {
    @Autowired
    BudgetCategoryRepository budgetCategoryRepository;
    @Autowired
    BudgetSub1Repository budgetSub1Repository;
    @Autowired
    BudgetSub2Repository budgetSub2Repository;
    @Autowired
    MerchandiseRepository merchandiseRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    PlanLineItemRepository planLineItemRepository;
    @Autowired
    FXRecordRepository fxRecordRepository;
    @Autowired
    CountryRepository countryRepository;


    @PostConstruct
    public void financeInit(){
        String thisUser = "yingshi2502";
        Country country = countryRepository.findCountryByCode("SG");

        List<FXRecord> fxRecords = fxRecordRepository.findAll();
        if(fxRecords == null || fxRecords.size() == 0){
            createFXRecord(thisUser);
            createCategories(thisUser);
            createSubCategories(thisUser);
            createMerchandise(thisUser);
        }
    }

    public void createFXRecord(String userOps){
        FXRecord fx = new FXRecord("SGD","CNY", BigDecimal.valueOf(5.16),new Date());
        fx.setCreatedBy(userOps);
        fx.setLastModifiedBy(userOps);
        fx = fxRecordRepository.save(fx);
    }



    public void createCategories(String thisUser){
        Country country = countryRepository.findCountryByCode("SG");
        if (country == null){
            Country countrySG = new Country("Singapore","SG","APAC-SG");
            countrySG.setCreatedBy(thisUser);
            countrySG.setLastModifiedBy(thisUser);
            country = countryRepository.save(countrySG);
        }
        BudgetCategory cat = new BudgetCategory("Telecom","CAT-TEL","SG-TELECOM");
        cat.setCreatedBy(thisUser);
        cat.setLastModifiedBy(thisUser);
        budgetCategoryRepository.save(cat);
        cat.setCountry(country);
        budgetCategoryRepository.saveAndFlush(cat);

        int size = budgetCategoryRepository.findBudgetCategoriesByCountry_Id(country.getId()).size();
        System.out.println(size);


    }
    /*
        1. Always set createdBy/LastModifiedBy = username
           (front-end should always communicate the active user with backend)
        2. Logical Delete:  setIsDeleted = True
           When retrieving objects: WHERE is_deleted = false/ or filter out the isDeleted = True ones
        3. Setup objectCode/HierachyPath generation rules


     */
    public void createSubCategories(String thisUser){
        BudgetCategory cat = budgetCategoryRepository.findBudgetCategoryByCode("CAT-TEL");
        BudgetSub1 sub1 = new BudgetSub1("Data","SUB1-DATA","SG-TELECOM-DATA");



        sub1.setCreatedBy(thisUser);
        sub1.setLastModifiedBy(thisUser);


        sub1 = budgetSub1Repository.save(sub1);
        cat.getBudgetSub1s().size();
        sub1.setBudgetCategory(cat);
        cat.getBudgetSub1s().add(sub1);
        sub1 = budgetSub1Repository.save(sub1);
        budgetCategoryRepository.save(cat);

        BudgetSub2 sub2 = new BudgetSub2("Data Link","SUB2-DATALINK","SG-TELECOM-DATA-DATA_LINK");
        sub2.setCreatedBy(thisUser);
        sub2.setLastModifiedBy(thisUser);
        sub2 = budgetSub2Repository.save(sub2);
        sub1.getBudgetSub2s().add(sub2);
        sub2.setBudgetSub1(sub1);

        budgetSub2Repository.saveAndFlush(sub2);
        budgetSub1Repository.saveAndFlush(sub1);

    }

    public void createMerchandise(String thisUser){
        Merchandise m = new Merchandise("Data Link","MER-DL-1","SG-TELECOM-DATA-DATA_LINK-SINGTEL","bps",thisUser);
        merchandiseRepository.save(m);

        BudgetSub2 sub2 = budgetSub2Repository.getBudgetSub2ByCode("SUB2-DATALINK");
        sub2.getMerchandises().size();
        sub2.getMerchandises().add(m);
        m.setBudgetSub2(sub2);

        budgetSub2Repository.saveAndFlush(sub2);
        merchandiseRepository.saveAndFlush(m);
    }
}
