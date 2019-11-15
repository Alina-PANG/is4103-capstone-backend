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

    @PostConstruct
    public void financeInit(){
        if (fxRecordRepository.findAll().isEmpty()){
            createFXRecord();
        }
        if (budgetCategoryRepository.findAll().isEmpty()){
            createCategory();
            createSub1();
            createSub2();
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
            CreateSub1Sub2Request req = new CreateSub1Sub2Request(catsub1[i][0],catsub1[i][1],"admin");
            sub1Service.createSub1(req);
        }
    }
    public void createSub2(){
        String[][] sub1sub2 = {
                {"CATS1-CHAIR-3", "meeting room chair"},
                {"CATS1-CHAIR-3", "office chair"},
                {"CATS1-DATA-7", "Data Link"},
                {"CATS1-DESK-4", "Office Desk"},
                {"CATS1-DESKTOP_DEVICES-9", "Input Hardware"},
                {"CATS1-HARDWARE_WARRANTY-11", "Maintainess"},
                {"CATS1-MISCELLANEOUS-10", "Miscellaneous"},
                {"CATS1-MONITOR-5", "Desktop Monitory"},
                {"CATS1-MONITOR-5", "Meeting Room Monitor"},
                {"CATS1-MONITOR-5", "Elevator Monitor"},
                {"CATS1-PERSONAL_DESKTOP-6", "Windows Machine"},
                {"CATS1-PERSONAL_DESKTOP-6", "MacOS Machine"},
                {"CATS1-PERSONAL_DESKTOP-6", "Lunix Machine"},
                {"CATS1-SOFTWARE_LISCENCE-12", "Apple Services"},
                {"CATS1-SOFTWARE_LISCENCE-12", "Messaging Service"},
                {"CATS1-TRAINING-2", "Internal Training"},
                {"CATS1-TRAINING-2", "External Training"},
                {"CATS1-TRAVEL-1", "Planned Biz Trip"},
                {"CATS1-TRAVEL-1", "Ad-hoc Travel"},
                {"CATS1-VOICE-8", "Personal Voice Equip"},
                {"CATS1-VOICE-8", "Company Voice Equip"},
        };
        for (int i=0;i<sub1sub2.length;i++){
            CreateSub1Sub2Request req = new CreateSub1Sub2Request(sub1sub2[i][0],sub1sub2[i][1],"admin");
            sub2Service.createSub2(req);
        }
    }
    public void createServices(){
        String[][] sub2serv = {
                {"CATS2-EXTERNAL_TRAINING-7", "External Training"},
                {"CATS2-PLANNED_BIZ_TRIP-5", "Business Trip"},
                {"CATS2-MISCELLANEOUS-17", "MISC"},
                {"CATS2-MACOS_MACHINE-14", "Macbook"},
                {"CATS2-AD-HOC_TRAVEL-4", "Ad-hoc-Business Trip"},
                {"CATS2-WINDOWS_MACHINE-13", "Dell Desktop"},
                {"CATS2-DATA_LINK-1", "Data Link A"},
                {"CATS2-DATA_LINK-1", "Data Link B"},
                {"CATS2-COMPANY_VOICE_EQUIP-3", "Speaker"},
                {"CATS2-MEETING_ROOM_MONITOR-9", "Video Meeting Camera"},
                {"CATS2-INPUT_HARDWARE-16", "Keyboard"},
                {"CATS2-INPUT_HARDWARE-16", "Mouse"},
                {"CATS2-DESKTOP_MONITORY-8", "Personal 24inch"},
                {"CATS2-PERSONAL_VOICE_EQUIP-2", "Headset"},
                {"CATS2-DESKTOP_MONITORY-8", "20-24 Vertical"},
                {"CATS2-OFFICE_DESK-18", "Employee Desks"},
                {"CATS2-APPLE_SERVICES-21", "Apple Care"},
                {"CATS2-APPLE_SERVICES-21", "Apple Cloud"},
                {"CATS2-MAINTAINESS-20", "Data Link Maintainese"},
                {"CATS2-MESSAGING_SERVICE-19", "Symphony"},
                {"CATS2-MESSAGING_SERVICE-19", "Skype"},
                {"CATS2-OFFICE_CHAIR-11", "Engineering Chair"},
                {"CATS2-MEETING_ROOM_CHAIR-12", "Meeting Room Chair"},
                {"CATS2-INTERNAL_TRAINING-6", "Internal Training"},
        };
        try {
            for (int i=0;i<sub2serv.length;i++){
                CreateServiceRequest req = new CreateServiceRequest(sub2serv[i][1],sub2serv[i][0],"n/a",BigDecimal.ZERO,"GBP","admin");
                serviceServ.createService(req);
            }
        }catch (Exception ex){
            ex.printStackTrace();
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
