package capstone.is4103capstone.finance;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.ApprovalForRequest;
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
public class FinanceTestCodeHPGeneration {
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
    @Autowired
    ApprovalForRequestRepository approvalForRequestRepository;
    @Autowired
    ActualsTableRepository actualsTableRepository;


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
    public void financeInit() {

//        System.out.println(getClass().getSimpleName());
//        fxRecordCode();
//        approvalForRequestCode();
//        actualsTableCode();
//        String catCode = budgetCategoryCode();
//        System.out.println("Cat Code: " + catCode);
//        String sub1Code = budgetSub1(catCode);
//        System.out.println("Sub1Code: "+sub1Code);
//        String sub2Code = budgetSub2(sub1Code);
//        System.out.println("Sub2Code: "+sub2Code);

    }

    private void fxRecordCode(){
        FXRecord fx = new FXRecord("USD","CNY", BigDecimal.valueOf(7.03),new Date());
        fx = fxRecordRepository.saveAndFlush(fx);
        String code = generateCode(fxRecordRepository,fx);
        System.out.println(code);
    }

    private void approvalForRequestCode(){
        ApprovalForRequest a = new ApprovalForRequest();
        a.setCreatedBy("yingshi2502");
        a.setRequestedItemCode("BJF-Dummy-Code-004");
        a = approvalForRequestRepository.save(a);
        System.out.println(generateCode(approvalForRequestRepository,a));
    }
    private void actualsTableCode(){
        ActualsTable a = new ActualsTable();
        a.setObjectName("2019-ACTUALS-TABLE");
        a = actualsTableRepository.save(a);
        System.out.println(generateCode(actualsTableRepository,a));
    }
    private String budgetCategoryCode(){
        Country c = new Country("China","CN","APAC-CN");
        BudgetCategory cat = new BudgetCategory("Telecom");
        cat.setCreatedBy("yingshi2502");
        cat.setHierachyPath(g.generateHierachyPath(c,cat));
        cat = budgetCategoryRepository.save(cat);
        System.out.println(cat.getHierachyPath());
        return generateCode(budgetCategoryRepository,cat);
    }

    private String budgetSub1(String catCode){
        BudgetCategory parent = budgetCategoryRepository.findBudgetCategoryByCode(catCode);
        BudgetSub1 cat = new BudgetSub1("Data");
        cat.setCreatedBy("yingshi2502");
        cat.setHierachyPath(g.generateHierachyPath(parent,cat));
        cat = budgetSub1Repository.save(cat);
        System.out.println(cat.getHierachyPath());

        return generateCode(budgetSub1Repository,cat);
    }
    private String budgetSub2(String sub1Code){
        BudgetSub1 parent = budgetSub1Repository.findBudgetSub1ByCode(sub1Code);
        BudgetSub2 cat = new BudgetSub2("Data Link");
        cat.setCreatedBy("yingshi2502");
        cat.setHierachyPath(g.generateHierachyPath(parent,cat));
        cat = budgetSub2Repository.save(cat);
        System.out.println(cat.getHierachyPath());

        return generateCode(budgetSub2Repository,cat);
    }



}
