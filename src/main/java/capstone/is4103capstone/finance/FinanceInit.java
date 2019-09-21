package capstone.is4103capstone.finance;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.finance.Repository.*;
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

    @PostConstruct
    public void financeInit(){
//        String thisUser = "yingshi2502";
//        Country country = countryRepository.findCountryByCode("SG");
//
//        List<FXRecord> fxRecords = fxRecordRepository.findAll();
//        if(fxRecords == null || fxRecords.size() == 0){
//            createFXRecord(thisUser);
//            String catCode = createCategories(thisUser);
//            String sub2Code = createSubCategories(thisUser,catCode);
//            createMerchandise(thisUser,sub2Code);
//        }
    }

    public void createFXRecord(String userOps){
        FXRecord fx = new FXRecord("SGD","CNY", BigDecimal.valueOf(5.16),new Date());
        fx.setCreatedBy(userOps);
        fx.setLastModifiedBy(userOps);
        fx = fxRecordRepository.save(fx);
        generateCode(fxRecordRepository,fx);
    }



    public String createCategories(String thisUser){
        Country country = countryRepository.findCountryByCode("SG");
        if (country == null){
            Country countrySG = new Country("Singapore","SG","APAC-SG");
            countrySG.setCreatedBy(thisUser);
            countrySG.setLastModifiedBy(thisUser);
            country = countryRepository.save(countrySG);
        }
        BudgetCategory cat = new BudgetCategory("Telecom255");

        cat.setHierachyPath(g.generateHierachyPath(country,cat));

        cat.setCreatedBy(thisUser);
        cat.setLastModifiedBy(thisUser);
        budgetCategoryRepository.save(cat);
        cat.setCountry(country);
        budgetCategoryRepository.saveAndFlush(cat);

        String catCode = generateCode(budgetCategoryRepository,cat);

        int size = budgetCategoryRepository.findBudgetCategoriesByCountry_Id(country.getId()).size();
        System.out.println(size);

        return catCode;
    }
    /*
        1. Always set createdBy/LastModifiedBy = username
           (front-end should always communicate the active user with backend)
        2. Logical Delete:  setIsDeleted = True
           When retrieving objects: WHERE is_deleted = false/ or filter out the isDeleted = True ones
        3. Setup objectCode/HierachyPath generation rules


     */
    public String createSubCategories(String thisUser,String catCode){
        BudgetCategory cat = budgetCategoryRepository.findBudgetCategoryByCode(catCode);
        BudgetSub1 sub1 = new BudgetSub1("Data3");

        sub1.setHierachyPath(g.generateHierachyPath(cat,sub1));


        sub1.setCreatedBy(thisUser);
        sub1.setLastModifiedBy(thisUser);


        sub1 = budgetSub1Repository.save(sub1);
        cat.getBudgetSub1s().size();
        sub1.setBudgetCategory(cat);
        cat.getBudgetSub1s().add(sub1);
        sub1 = budgetSub1Repository.save(sub1);
        budgetCategoryRepository.save(cat);
        String sub1Code = generateCode(budgetSub1Repository,sub1);

        BudgetSub2 sub2 = new BudgetSub2("Data Center3");
        sub1 = budgetSub1Repository.findBudgetSub1ByCode(sub1Code);
        sub2.setHierachyPath(g.generateHierachyPath(sub1,sub2));

        sub2.setCreatedBy(thisUser);
        sub2.setLastModifiedBy(thisUser);
        sub2 = budgetSub2Repository.save(sub2);
        sub1.getBudgetSub2s().add(sub2);
        sub2.setBudgetSub1(sub1);
        String sub2Code = generateCode(budgetSub2Repository,sub2);

        budgetSub2Repository.saveAndFlush(sub2);
        budgetSub1Repository.saveAndFlush(sub1);

        return sub2Code;
    }

    public void createMerchandise(String thisUser,String sub2Code){
        Merchandise m = new Merchandise("Data Link","MER-DL-12","SG-TELECOM-DATA-DATA_LINK-SINGTEL","bps",thisUser);
        merchandiseRepository.save(m);

        BudgetSub2 sub2 = budgetSub2Repository.findBudgetSub2ByCode(sub2Code);
        System.out.println(sub2.getId());
        sub2.getMerchandises().size();
        sub2.getMerchandises().add(m);
        m.setBudgetSub2(sub2);

        budgetSub2Repository.saveAndFlush(sub2);
        merchandiseRepository.saveAndFlush(m);
    }
}
