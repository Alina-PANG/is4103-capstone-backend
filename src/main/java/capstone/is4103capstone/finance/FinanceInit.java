package capstone.is4103capstone.finance;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.*;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.util.FinanceEntityCodeHPGenerator;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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

    private String generateCode(JpaRepository repo, DBEntityTemplate entity){
        try {
            FinanceEntityCodeHPGenerator g = new FinanceEntityCodeHPGenerator();
            return g.generateCode(repo,entity);
        }catch (RepositoryEntityMismatchException e){
            return null;//should log down
        } catch (Exception e) {
            return null;
        }
    }

    @PostConstruct
    public void financeInit(){
//        BudgetCategory cat = new BudgetCategory();
//        cat.setObjectName("CATEGORY");
//        cat.setCreatedBy("yingshi2502");
//        cat = budgetCategoryRepository.save(cat);
//        generateCode(budgetCategoryRepository,cat);
//        String thisUser = "yingshi2502";
//        createFXRecord(thisUser);
//        createCategoriezs(thisUser);
//        createSubCategories(thisUser);
//        createMerchandise(thisUser);
    }

    public void createFXRecord(String userOps){
        FXRecord fx = new FXRecord("SGD","CNY", BigDecimal.valueOf(5.16),new Date());
        fx.setCreatedBy(userOps);
        fx.setLastModifiedBy(userOps);
        fx = fxRecordRepository.save(fx);

        generateCode(fxRecordRepository,fx);
    }

    public void createCategories(String thisUser){
        Country country = countryRepository.findCountryByCode("SG");
        if (country == null){
            Country countrySG = new Country("Singapore","SG","APAC-SG");
            countrySG.setCreatedBy(thisUser);
            countrySG.setLastModifiedBy(thisUser);
            country = countryRepository.save(countrySG);
//            generateCode()
        }
        BudgetCategory cat = new BudgetCategory();
        cat.setObjectName("Telecom");
        cat.setCreatedBy(thisUser);
        cat.setLastModifiedBy(thisUser);
        cat = budgetCategoryRepository.save(cat);
        cat.setCountry(country);
        cat = budgetCategoryRepository.saveAndFlush(cat);

        int size = budgetCategoryRepository.findBudgetCategoriesByCountry_Id(country.getId()).size();
        System.out.println(size);

        generateCode(budgetCategoryRepository,cat);


    }
    /*
        1. Always set createdBy/LastModifiedBy = username
           (front-end should always communicate the active user with backend)
        2. Logical Delete:  setIsDeleted = True
           When retrieving objects: WHERE is_deleted = false/ or filter out the isDeleted = True ones
        3. Setup objectCode/HierachyPath generation rules
     */
    public void createSubCategories(String thisUser){
        BudgetCategory cat = budgetCategoryRepository.findBudgetCategoryByCode("CAT-Telecom-null");
        BudgetSub1 sub1 = new BudgetSub1();
        sub1.setObjectName("Data");
        sub1.setCreatedBy(thisUser);
        sub1.setLastModifiedBy(thisUser);

        sub1 = budgetSub1Repository.save(sub1);
        cat.getBudgetSub1s().size();
        sub1.setBudgetCategory(cat);
        cat.getBudgetSub1s().add(sub1);
        sub1 = budgetSub1Repository.save(sub1);
        budgetCategoryRepository.save(cat);

        BudgetSub2 sub2 = new BudgetSub2();
        sub2.setObjectName("Data Link");
        sub2.setCreatedBy(thisUser);
        sub2.setLastModifiedBy(thisUser);
        sub2 = budgetSub2Repository.save(sub2);
        sub1.getBudgetSub2s().add(sub2);
        sub2.setBudgetSub1(sub1);

        sub2 = budgetSub2Repository.saveAndFlush(sub2);
        sub1 = budgetSub1Repository.saveAndFlush(sub1);

        generateCode(budgetSub1Repository,sub1);
        generateCode(budgetSub2Repository,sub2);
    }
    @Autowired
    VendorRepository vendorRepository;
    public void createMerchandise(String thisUser){
        Merchandise m = new Merchandise();
        m.setObjectName("Data Link");
        Vendor v = new Vendor();
        v.setObjectName("Singtel");
        v.setCode("SINGTEL");
        v = vendorRepository.save(v);
        merchandiseRepository.save(m);

        BudgetSub2 sub2 = budgetSub2Repository.getBudgetSub2ByCode("SUB2-DATALINK");
        sub2.getMerchandises().size();
        sub2.getMerchandises().add(m);
        m.setBudgetSub2(sub2);
        v.getMerchandises().add(m);
        m.setVendor(v);

        vendorRepository.saveAndFlush(v);
        budgetSub2Repository.saveAndFlush(sub2);
        merchandiseRepository.saveAndFlush(m);

        generateCode(merchandiseRepository,m);
    }
}
