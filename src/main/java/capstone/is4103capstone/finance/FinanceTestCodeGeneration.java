package capstone.is4103capstone.finance;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.finance.Repository.*;
import capstone.is4103capstone.util.EntityCodeGenerator;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class FinanceTestCodeGeneration {
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


    EntityCodeGenerator g = new EntityCodeGenerator();
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
        System.out.println(getClass().getSimpleName());
        fxRecordCode();
        approvalForRequestCode();
        actualsTableCode();
        budgetCategoryCode();
        budgetSub1();
        budgetSub2();
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
        a.setRequestedItemCode("BJF-Dummy-Code-000");
        a = approvalForRequestRepository.save(a);
        System.out.println(generateCode(approvalForRequestRepository,a));
    }
    private void actualsTableCode(){
        ActualsTable a = new ActualsTable();
        a.setObjectName("2019-ACTUALS-TABLE");
        a = actualsTableRepository.save(a);
        System.out.println(generateCode(actualsTableRepository,a));
    }
    private void budgetCategoryCode(){
        BudgetCategory cat = new BudgetCategory("Telecom");
        cat.setCreatedBy("yingshi2502");
        cat = budgetCategoryRepository.save(cat);
        System.out.println(generateCode(budgetCategoryRepository,cat));
    }
    private void budgetSub1(){
        BudgetSub1 cat = new BudgetSub1("Data");
        cat.setCreatedBy("yingshi2502");
        cat = budgetSub1Repository.save(cat);
        System.out.println(generateCode(budgetSub1Repository,cat));
    }
    private void budgetSub2(){
        BudgetSub2 cat = new BudgetSub2("Data-Link");
        cat.setCreatedBy("yingshi2502");
        cat = budgetSub2Repository.save(cat);
        System.out.println(generateCode(budgetSub2Repository,cat));
    }



}
