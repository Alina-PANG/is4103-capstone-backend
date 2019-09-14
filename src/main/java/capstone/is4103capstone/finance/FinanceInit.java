package capstone.is4103capstone.finance;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.finance.BudgetCategory;
import capstone.is4103capstone.entities.finance.FXRecord;
import capstone.is4103capstone.entities.finance.Merchandise;
import capstone.is4103capstone.finance.Repository.*;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;

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
        createFXRecord(thisUser);
        createCategories(thisUser);
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
}
