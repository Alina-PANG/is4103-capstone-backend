package capstone.is4103capstone.finance;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.entities.supplyChain.ChildContract;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.finance.Repository.*;
import capstone.is4103capstone.finance.admin.service.FXTableService;
import capstone.is4103capstone.supplychain.Repository.ChildContractRepository;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.util.FinanceEntityCodeHPGenerator;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
public class FinanceInit {
    @Autowired
    BudgetCategoryRepository budgetCategoryRepository;
    @Autowired
    BudgetSub1Repository budgetSub1Repository;
    @Autowired
    BudgetSub2Repository budgetSub2Repository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    PlanLineItemRepository planLineItemRepository;
    @Autowired
    FXRecordRepository fxRecordRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    FXTableService fxService;
    @Autowired
    ContractRepository contractRepo;
    @Autowired
    ChildContractRepository childContractRepo;
    @Autowired
    SpendingRecordRepository spendingRecordRepository;
    @Autowired
    StatementOfAccountLineItemRepository soaRepo;


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
//        List<StatementOfAcctLineItem> soa = soaRepo.findAll();
//        for (StatementOfAcctLineItem s:soa){
//            PurchaseOrder po = s.getPurchaseOrder();
//            s.setActualPmtInGBP(fxService.convertToGBPWithLatest(po.getCurrencyCode(),s.getActualPmt()));
//            s.setPaidAmtInGBP(fxService.convertToGBPWithLatest(po.getCurrencyCode(),s.getPaidAmt()));
//            soaRepo.saveAndFlush(s);
//        }
//        List<PurchaseOrder> pos = purchaseOrderRepository.findAll();
//        for (PurchaseOrder po: pos){
//            po.setTotalAmtInGBP(fxService.convertToGBPWithLatest(po.getCurrencyCode(),po.getTotalAmount()));
//            purchaseOrderRepository.saveAndFlush(po);
//        }
//
//        List<SpendingRecord> sps = spendingRecordRepository.findAll();
//        for (SpendingRecord sp: sps){
//            sp.setSpendingAmtInGBP(fxService.convertToGBPWithLatest(sp.getCurrencyCode(),sp.getSpendingAmt()));
//            spendingRecordRepository.saveAndFlush(sp);
//        }

//        List<Contract> contracts = contractRepo.findAll();
//        for (Contract c:contracts){
//            c.setContractValueInGBP(fxService.convertToGBPWithLatest(c.getCurrencyCode(),c.getTotalContractValue()));
//            contractRepo.saveAndFlush(c);
//        }
//
//        List<ChildContract> childContracts = childContractRepo.findAll();
//        for (ChildContract c: childContracts){
//            c.setContractValueInGBP(fxService.convertToGBPWithLatest(c.getMasterContract().getCurrencyCode(),c.getContractValue()));
//            childContractRepo.saveAndFlush(c);
//        }

//        List<PurchaseOrder> po = purchaseOrderRepository.findAll();
//        for (PurchaseOrder p :po){
//            p.setCountryId(employeeService.getCountryEmployeeBelongTo(p.getCreatedBy()).getId());
//            purchaseOrderRepository.saveAndFlush(p);
//        }

//        String thisUser = "yingshi2502";
//        Country country = countryRepository.findCountryByCode("SG");
//
//        List<FXRecord> fxRecords = fxRecordRepository.findAll();
//        if(fxRecords == null || fxRecords.size() == 0){
//            createFXRecord(thisUser);
//            String catCode = createCategories(thisUser);
//            String sub2Code = createSubCategories(thisUser,catCode);
//            createservice(thisUser,sub2Code);
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

    public void createservice(String thisUser,String sub2Code){
        Service m = new Service("Data Link","MER-DL-12","SG-TELECOM-DATA-DATA_LINK-SINGTEL","bps",thisUser);
        serviceRepository.save(m);

        BudgetSub2 sub2 = budgetSub2Repository.findBudgetSub2ByCode(sub2Code);
//        System.out.println(sub2.getId());
        sub2.getservices().size();
        sub2.getservices().add(m);
        m.setBudgetSub2(sub2);

        budgetSub2Repository.saveAndFlush(sub2);
        serviceRepository.saveAndFlush(m);
    }
}
