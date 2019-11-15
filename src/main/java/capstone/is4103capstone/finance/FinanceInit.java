package capstone.is4103capstone.finance;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.finance.*;
import capstone.is4103capstone.entities.supplyChain.ChildContract;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.finance.Repository.*;
import capstone.is4103capstone.finance.admin.model.req.CreateFXRequest;
import capstone.is4103capstone.finance.admin.model.req.CreateServiceRequest;
import capstone.is4103capstone.finance.admin.model.req.CreateSub1Sub2Request;
import capstone.is4103capstone.finance.admin.service.FXTableService;
import capstone.is4103capstone.finance.admin.service.ServiceServ;
import capstone.is4103capstone.finance.admin.service.Sub1Service;
import capstone.is4103capstone.finance.admin.service.Sub2Service;
import capstone.is4103capstone.supplychain.Repository.ChildContractRepository;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.util.FinanceEntityCodeHPGenerator;
import capstone.is4103capstone.util.Tools;
import capstone.is4103capstone.util.exception.RepositoryEntityMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

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
    @Autowired
    Sub1Service sub1Service;
    @Autowired
    Sub2Service sub2Service;
    @Autowired
    ServiceServ serviceServ;

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

    public void financeInit(){
        if (fxRecordRepository.findAll().isEmpty()){
            createFXRecord();
        }
        if (budgetCategoryRepository.findAll().isEmpty()){
            createCategory();
            createSub1();
            createSub2();
        }

        if (serviceRepository.findAll().isEmpty()){
            createServices();
        }
    }

    public void createCategory(){
        Country sg = countryRepository.findCountryByCode("SG");
        String[] catNames = {"Office Supplies","Warranty Services","IT Asset","Miscellaneous","Telecom"};
        String[] catCodes= {"CAT-OFFICE_SUPPLIES-4","CAT-WARRANTY_SERVICES-5","CAT-IT_ASSET-3","CAT-MISCELLANEOUS-1","CAT-TELECOM-2"};
        for (int i=0;i<catNames.length;i++){
            BudgetCategory category = new BudgetCategory(catNames[i],catCodes[i],sg.getHierachyPath()+":"+catCodes[i],"admin",sg);
            category = budgetCategoryRepository.saveAndFlush(category);
        }
    }

    private void createSub1(){
        String[][] catsub1 = {
                {"CAT-WARRANTY_SERVICES-5", "Software Liscence"},
                {"CAT-TELECOM-2", "Data"},
                {"CAT-MISCELLANEOUS-1", "Travel"},
                {"CAT-MISCELLANEOUS-1", "Miscellaneous"},
                {"CAT-OFFICE_SUPPLIES-4", "Desk"},
                {"CAT-IT_ASSET-3", "Monitor"},
                {"CAT-OFFICE_SUPPLIES-4", "Chair"},
                {"CAT-WARRANTY_SERVICES-5", "Hardware Warranty"},
                {"CAT-TELECOM-2", "Voice"},
                {"CAT-IT_ASSET-3", "Personal Desktop"},
                {"CAT-MISCELLANEOUS-1", "Training"},
                {"CAT-OFFICE_SUPPLIES-4", "Desktop Devices"}
        };
        for (int i=0;i<catsub1.length;i++){
            BudgetCategory cat = budgetCategoryRepository.findBudgetCategoryByCode(catsub1[i][0]);
            BudgetSub1 sub1 = new BudgetSub1(catsub1[i][1],convert("CATS1",catsub1[i][1],i),"admin",cat);
            sub1 = budgetSub1Repository.saveAndFlush(sub1);
            cat.getBudgetSub1s().add(sub1);
            budgetCategoryRepository.saveAndFlush(cat);
        }
    }
    private String convert(String pre, String name,int i){
        String s = pre+"-"+name+"-"+i;
        s = s.replaceAll(" ","_");
        return s.toUpperCase();

    }
    public void createSub2(){
        String[][] sub1sub2 = {
                {"CHAIR-", "meeting room chair"},
                {"CHAIR-", "office chair"},
                {"DATA-", "Data Link"},
                {"DESK-", "Office Desk"},
                {"DESKTOP_DEVICES-", "Input Hardware"},
                {"HARDWARE_WARRANTY-", "Maintainess"},
                {"MISCELLANEOUS-", "Miscellaneous"},
                {"MONITOR-", "Desktop Monitory"},
                {"MONITOR-", "Meeting Room Monitor"},
                {"MONITOR-", "Elevator Monitor"},
                {"PERSONAL_DESKTOP-", "Windows Machine"},
                {"PERSONAL_DESKTOP-", "MacOS Machine"},
                {"PERSONAL_DESKTOP-", "Lunix Machine"},
                {"SOFTWARE_LISCENCE-", "Apple Services"},
                {"SOFTWARE_LISCENCE-", "Messaging Service"},
                {"TRAINING-", "Internal Training"},
                {"TRAINING-", "External Training"},
                {"TRAVEL-", "Planned Biz Trip"},
                {"TRAVEL-", "Ad-hoc Travel"},
                {"VOICE-", "Personal Voice Equip"},
                {"VOICE-", "Company Voice Equip"},
        };
        
        for (int i=0;i<sub1sub2.length;i++){
            BudgetSub1 cat = budgetSub1Repository.findBudgetSub1ByCodeLike(sub1sub2[i][0]);
            BudgetSub2 sub2 = new BudgetSub2(sub1sub2[i][1],convert("CATS2",sub1sub2[i][1],i),"admin",cat);
            sub2 = budgetSub2Repository.saveAndFlush(sub2);
            cat.getBudgetSub2s().add(sub2);
            budgetSub1Repository.saveAndFlush(cat);
        }
    }
    public void createServices(){
        String[][] sub2serv = {
                {"EXTERNAL_TRAINING-", "External Training"},
                {"PLANNED_BIZ_TRIP-", "Business Trip"},
                {"MISCELLANEOUS-", "MISC"},
                {"MACOS_MACHINE-", "Macbook"},
                {"AD-HOC_TRAVEL-", "Ad-hoc-Business Trip"},
                {"WINDOWS_MACHINE-", "Dell Desktop"},
                {"DATA_LINK-", "Data Link A"},
                {"DATA_LINK", "Data Link B"},
                {"COMPANY_VOICE_EQUIP", "Speaker"},
                {"MEETING_ROOM_MONITOR", "Video Meeting Camera"},
                {"INPUT_HARDWARE", "Keyboard"},
                {"INPUT_HARDWARE", "Mouse"},
                {"DESKTOP_MONITORY", "Personal 24inch"},
                {"PERSONAL_VOICE_EQUIP", "Headset"},
                {"DESKTOP_MONITORY", "2024 Vertical"},
                {"OFFICE_DESK", "Employee Desks"},
                {"APPLE_SERVICES", "Apple Care"},
                {"APPLE_SERVICES", "Apple Cloud"},
                {"MAINTAINESS", "Data Link Maintainese"},
                {"MESSAGING_SERVICE", "Symphony"},
                {"MESSAGING_SERVICE", "Skype"},
                {"OFFICE_CHAIR", "Engineering Chair"},
                {"MEETING_ROOM_CHAIR", "Meeting Room Chair"},
                {"INTERNAL_TRAINING", "Internal Training"},
        };

            for (int i=0;i<sub2serv.length;i++){
                try {
                    BudgetSub2 sub2 = budgetSub2Repository.findBudgetSub2ByCodeLike(sub2serv[i][0]);
                    Service s = new Service(sub2serv[i][1], convert("S", sub2serv[i][1], i), "admin", sub2);
                    serviceRepository.save(s);
                    sub2.getservices().add(s);
                    budgetSub2Repository.saveAndFlush(sub2);

                } catch (Exception ex) {
                    System.out.println(i+" "+sub2serv[i][0]);
                    ex.printStackTrace();
                }
            }
    }

    public void createFXRecord(){
        try {
            double[] fxRatesgdgbp = {1.7303,1.811,1.740,1.716,1.753};
            String[] dates = {"2019-01-01","2019-03-15","2019-05-31","2019-09-15","2019-11-15"};
            for (int i=0;i<fxRatesgdgbp.length;i++){
                CreateFXRequest req = new CreateFXRequest("GBP","SGD",dates[i],BigDecimal.valueOf(fxRatesgdgbp[i]),"admin");
                fxService.createFXRecord(req);
            }

            double[] fxRategbphkd = {9.979,10.131,10.080};
            for (int i=0;i<fxRategbphkd.length;i+=2){
                CreateFXRequest req = new CreateFXRequest("GBP","HKD",dates[i],BigDecimal.valueOf(fxRategbphkd[i]),"admin");
                fxService.createFXRecord(req);
            }

            double[] fxRategbpcny = {9.939,9.983,10.081};
            for (int i=0;i<fxRategbphkd.length;i+=2){
                CreateFXRequest req = new CreateFXRequest("GBP","CNY",dates[i],BigDecimal.valueOf(fxRategbpcny[i]),"admin");
                fxService.createFXRecord(req);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
