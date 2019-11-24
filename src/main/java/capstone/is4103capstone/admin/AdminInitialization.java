package capstone.is4103capstone.admin;

import capstone.is4103capstone.admin.repository.*;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.helper.Address;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.helper.WebAppPermissionMap;
import capstone.is4103capstone.entities.seat.EmployeeOfficeWorkingSchedule;
import capstone.is4103capstone.entities.seat.SeatAdminMatch;
import capstone.is4103capstone.seat.repository.EmployeeOfficeWorkingScheduleRepository;
import capstone.is4103capstone.seat.repository.ScheduleRepository;
import capstone.is4103capstone.seat.repository.SeatAdminMatchRepository;
import capstone.is4103capstone.seat.service.SeatInitializationService;
import capstone.is4103capstone.seat.service.SeatManagementGeneralService;
import capstone.is4103capstone.util.enums.EmployeeTypeEnum;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminInitialization {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    FunctionRepository functionRepository;
    @Autowired
    BusinessUnitRepository businessUnitRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CostCenterRepository costCenterRepository;
    @Autowired
    SeatAdminMatchRepository seatAdminMatchRepository;
    public void init() {
        if (currencyRepository.findAll().isEmpty()) {
            createCurrency();
        }
        if (regionRepository.findAll().isEmpty()) {
            createRegionCountry();
        }
        if (employeeRepository.findAll().isEmpty()) {
            createEmployees();
            configureEmployeeHier();
        }
        if (officeRepository.findAll().isEmpty()) {
            createOffice();
        }
        if (functionRepository.findAll().isEmpty()) {
            createFunctions();
        }
        if (businessUnitRepository.findAll().isEmpty()){
            createBU();
        }
        if (teamRepository.findAll().isEmpty()){
            createTeamAndCC();
        }

    }

    private void createEmployeeTemplate(String fName, String lName, String username, String password, String email, String codeCPnt, String adminRight) {
        Employee newEmployee = new Employee(username, fName, lName, "", password);
        newEmployee.setEmployeeType(EmployeeTypeEnum.PERMANENT);
        newEmployee.setCode("EMP-" + codeCPnt);
        newEmployee.setCreatedBy("admin");
        newEmployee.setEmail(email);
        newEmployee.setLastModifiedBy("admin");

        switch (adminRight){
            case "normal":
                newEmployee.setWebAppPermissionMap(new WebAppPermissionMap(
                        false,true,false,true,false));
                break;
            case "all":
                newEmployee.setWebAppPermissionMap(new WebAppPermissionMap(true,true,true,true,true));
                break;
            case "supply":
                newEmployee.setWebAppPermissionMap(new WebAppPermissionMap(false,true,true,true,false));
                break;
            case "dashboard":
                newEmployee.setWebAppPermissionMap(new WebAppPermissionMap(false,true,true,true,true));
                break;
            default:
                break;
        }


        employeeRepository.save(newEmployee);

    }
    private void createEmployees() {
        String[] firstNameList = {"Admin", "PO", "Yingshi", "Hong", "Tom", "Amy", "Daivd", "Outsourcing", "Hangzhi", "Yuqian", "Joshua", "Ryan", "Viet"};
        String[] lastNameList = {"", "Manager", "Huang", "Xu", "Green", "Brown", "Cook", "Staff", "Pang", "Cai", "Chew", "Sim", "Nguyen"};
        String[] usernameList = {"admin", "poteam", "yingshi2502", "xuhong", "user01", "user02", "user03", "outsourcing1", "panghangzhi", "caiyuqian", "joshuachew", "ryan", "viet"};
        String[] codeCpntList = {"1", "PO_TEAM", "2", "5", "6", "7", "8", "OUTSOURCING", "PHZ", "CYQ", "JC", "RS", "VN"};
        String[] adminRightConf = {"all","normal","dashboard","normal","normal","normal","normal","supply","dashboard","dashboard","dashboard","dashboard","dashboard"};
        HashMap usernameEmail = new HashMap<String, String>() {{
            put("yingshi2502", "yingshi.h@u.nus.edu");
            put("ryan", "simweijie@gmail.com");
            put("viet", "viet200798@gmail.com");
            put("caiyuqian", "yuqiancai987@gmail.com");
            put("joshuachew", "chewzhj@gmail.com");
        }};
        String defaultEmailAddr = "lonelytown98@gmail.com";
        for (int i = 0; i < firstNameList.length; i++) {
            createEmployeeTemplate(firstNameList[i], lastNameList[i], usernameList[i], "password", (String) usernameEmail.getOrDefault(usernameList[i], defaultEmailAddr), codeCpntList[i],adminRightConf[i]);
        }
    }

    //TODO:
    @Transactional
    public void configureEmployeeTeamAndDefaultCC(){
        String[] usernameList = {"admin", "poteam", "yingshi2502", "xuhong", "user01", "user02", "user03",
                "outsourcing1", "panghangzhi", "caiyuqian", "joshuachew", "ryan", "viet"};
        String[] matchTeam = {"T-SG-ADMIN-ADMIN","T-SG-ADMIN-ADMIN","T-SG-ADMIN-ADMIN","T-SG-RCR-INT","T-SG-InfraTech-DBAdmin","T-SG-FixIncTech-ProdSupp","T-SG-FixIncTech-ProdSupp",
        "T-SG-ADMIN-ADMIN","T-SG-InfraTech-Networks","T-SG-InfraTech-Networks","T-SG-InfraTech-Networks","T-SG-FixIncTech-Dev","T-SG-FixIncTech-Dev"
        };

    }


    @Transactional
    public void configureEmployeeHier() {
        Employee admin = employeeRepository.findEmployeeByUserName("admin");
        Employee yingshi = employeeRepository.findEmployeeByUserName("yingshi2502");
        List<Employee> all = employeeRepository.findAll();
        for (Employee e : all) {
            if (e.getUserName().contains("user")) {
                e.setManager(yingshi);
                yingshi.getSubordinates().add(e);
            } else if (!e.getUserName().equals("admin")) {
                e.setManager(admin);
                admin.getSubordinates().add(e);
            }
            employeeRepository.saveAndFlush(e);
        }
        employeeRepository.saveAndFlush(admin);
        employeeRepository.saveAndFlush(yingshi);

    }
    private void createCurrency() {
        String[] defaultCurrencyCode = {"SGD", "GBP", "USD", "HKD", "JPY", "EURO", "CNY"};
        String[] defaultCurrencyName = {"Singapore Dollars", "Pound Sterling", "US Dollars", "Hong Kong Dollars", "Japan Yen", "Euro", "Chinese Yuan"};
        String[] defaultCurrCountryCode = {"SG", "UK", "US", "HK", "JP", "CN"};//usually this field won't be used
        for (int i = 0; i < defaultCurrCountryCode.length; i++) {
            Currency c = new Currency(defaultCurrencyName[i], defaultCurrCountryCode[i], defaultCurrencyCode[i]);
            currencyRepository.save(c);
        }
        //TODO: Initialize some history fx rate;
    }
    private void createRegionCountry() {
        String[] regionName = {"Asia-Pacific", "The United Kingdom", "Europe"};
        String[] regionCode = {"APAC", "UK", "EU"};
        for (int i = 0; i < regionName.length; i++) {
            Region r = new Region(regionName[i], regionCode[i], ":" + regionCode[i]);
            regionRepository.saveAndFlush(r);
        }

        Region regionAPAC = regionRepository.getRegionByCode("APAC");
        String[] apacCountries = {"Singapore", "Hong Kong", "China", "Japan"};
        String[] apacCountryCode = {"SG", "HK", "CN", "JP"};
        for (int i = 0; i < apacCountries.length; i++) {
            Country c = new Country(apacCountries[i], apacCountryCode[i], regionAPAC.getHierachyPath() + ":" + apacCountryCode[i], "admin", regionAPAC);
            c = countryRepository.saveAndFlush(c);
            regionAPAC.getCountries().add(c);
        }
        regionAPAC = regionRepository.saveAndFlush(regionAPAC);

        Region regionUK = regionRepository.getRegionByCode("UK");
        Country uk = new Country("United Kingdom", "UK", regionUK.getHierachyPath() + ":" + "UK", "admin", regionUK);
        uk = countryRepository.saveAndFlush(uk);
        regionUK.getCountries().add(uk);
        regionUK = regionRepository.save(regionUK);
    }
    private void createOffice() {
        Country sg = countryRepository.findCountryByCode("SG");
        Office office = new Office("One Raffles Quay", "ORQ", sg.getHierachyPath()+":"+"ORQ");
        Address orqAddress = new Address("1 Raffles Quay", "", "048583", "Singapore", "SG");
        office.setAddress(orqAddress);
        office.setNumOfFloors(2);
        office.setCreatedBy("admin");
        office.setCountry(sg);
        office = officeRepository.saveAndFlush(office);
        sg.getOffices().add(office);
        sg = countryRepository.saveAndFlush(sg);
    }
    private void createFunctions() {
        Country sg = countryRepository.findCountryByCode("SG");
        Office office = officeRepository.getOfficeByCode("ORQ");
        String[] sgFuncsName = {"Administration", "Human Resource", "Technology", "Sales"};
        String[] sgFuncsCode = {"F-SG-ADMIN", "F-SG-HR", "F-SG-TECH", "F-SG-SALES"};
        for (int i = 0; i < sgFuncsCode.length; i++) {
            CompanyFunction f = new CompanyFunction(sgFuncsName[i], sgFuncsCode[i], sg.getHierachyPath() + ":" + sgFuncsCode[i], "admin", sg);
            f.getOfficesCodeOfFunction().add(office.getCode());
            f = functionRepository.saveAndFlush(f);
            sg.getFunctions().add(f);
            office.getFunctionsCodeInOffice().add(sgFuncsCode[i]);
        }
        sg = countryRepository.saveAndFlush(sg);

        officeRepository.saveAndFlush(office);
    }
    private void createBU(){
        Office office = officeRepository.getOfficeByCode("ORQ");

        CompanyFunction techFunc = functionRepository.getByCode("F-SG-TECH");
        String[] buTechNames = {"Infrastructure Tech","Currency Tech","Fix Income Tech"};
        String[] buTechCodes = {"BU-SG-InfraTech","BU-SG-CurrTech","BU-SG-FixIncTech"};

        for (int i=0;i<buTechNames.length;i++){
            BusinessUnit bu = new BusinessUnit(buTechNames[i],buTechCodes[i],techFunc.getHierachyPath()+":"+buTechCodes[i],"admin",techFunc);
            bu = businessUnitRepository.saveAndFlush(bu);
            office.getBusinessUnitsCodeInOffice().add(buTechCodes[i]);
        }

        CompanyFunction hrFunc = functionRepository.getByCode("F-SG-HR");
        String[] buHRNames = {"Recruiting"};
        String[] buHRCodes = {"BU-SG-RCR"};
        for (int i=0;i<buHRNames.length;i++){
            BusinessUnit bu = new BusinessUnit(buHRNames[i],buHRCodes[i],hrFunc.getHierachyPath()+":"+buHRCodes[i],"admin",hrFunc);
            bu = businessUnitRepository.saveAndFlush(bu);
            office.getBusinessUnitsCodeInOffice().add(buHRCodes[i]);

        }

        CompanyFunction adminFunc = functionRepository.getByCode("F-SG-ADMIN");
        String[] buAdminNames = {"Administration"};
        String[] buAdminCodes = {"BU-SG-ADMIN"};
        for (int i=0;i<buAdminNames.length;i++){
            BusinessUnit bu = new BusinessUnit(buAdminNames[i],buAdminCodes[i],adminFunc.getHierachyPath()+":"+buAdminCodes[i],"admin",adminFunc);
            bu = businessUnitRepository.saveAndFlush(bu);
            office.getBusinessUnitsCodeInOffice().add(buAdminCodes[i]);

        }
        officeRepository.saveAndFlush(office);
    }

    private void createTeamAndCC(){
        Office orq = officeRepository.getOfficeByCode("ORQ");
        Employee yingshi = employeeRepository.findEmployeeByUserName("yingshi2502");
        Employee admin = employeeRepository.findEmployeeByUserName("admin");

        //-------------------------Infrastructure Tech team creation--------------
        BusinessUnit infraTech = businessUnitRepository.findByCode("BU-SG-InfraTech");
        String[] infraTeamNames = {"End User Computing","Data Center Operation","Database Admin","Networks"};
        String[] infraTechCode = {"T-SG-Tech-EndUserCom","T-SG-InfraTech-DataCenOpr","T-SG-InfraTech-DBAdmin","T-SG-InfraTech-Networks"};

        for (int i=0;i<infraTeamNames.length;i++){
            Team t = new Team(infraTeamNames[i],infraTechCode[i],infraTech.getHierachyPath()+":"+infraTechCode[i],"admin",infraTech,orq);
            t.setTeamLeader(yingshi);// define team lead
            t = teamRepository.saveAndFlush(t);
            infraTech.getTeams().add(t);
            createCCofTeam(t,admin);
            orq.getTeams().add(t);
        }
        infraTech = businessUnitRepository.saveAndFlush(infraTech);

        //----------------------- Fix Income BU Team creation--------------

        BusinessUnit fixIncTechBU = businessUnitRepository.findByCode("BU-SG-FixIncTech");
        String[] fixIncName = {"FixInc Production Support","FixInc Development"};
        String[] fixIncCode = {"T-SG-FixIncTech-ProdSupp","T-SG-FixIncTech-Dev"};

        for (int i=0;i<fixIncName.length;i++){
            Team t = new Team(fixIncName[i],fixIncCode[i],fixIncTechBU.getHierachyPath()+":"+fixIncCode[i],"admin",fixIncTechBU,orq);
            t.setTeamLeader(yingshi);// define team lead
            t = teamRepository.saveAndFlush(t);
            infraTech.getTeams().add(t);
            createCCofTeam(t,admin);
            orq.getTeams().add(t);
        }
        fixIncTechBU = businessUnitRepository.saveAndFlush(infraTech);


        //-------------- HR Recruiting Team creation------------
        BusinessUnit hrReruBu = businessUnitRepository.findByCode("BU-SG-RCR");
        Team interviewTeam = new Team("RCR Interview", "T-SG-RCR-INT", hrReruBu.getHierachyPath()+":"+"T-SG-RCR-INT","admin",hrReruBu,orq);
        interviewTeam.setTeamLeader(yingshi);
        interviewTeam = teamRepository.saveAndFlush(interviewTeam);
        hrReruBu.getTeams().add(interviewTeam);
        createCCofTeam(interviewTeam,admin);
        orq.getTeams().add(interviewTeam);
        officeRepository.saveAndFlush(orq);


        //------------ADMIN Team Creation-------
        BusinessUnit adminBU = businessUnitRepository.findByCode("BU-SG-ADMIN");
        Team adminTeam = new Team("Admin", "T-SG-ADMIN-ADMIN", adminBU.getHierachyPath()+":"+"T-SG-ADMIN-ADMIN","admin",adminBU,orq);
        adminTeam.setTeamLeader(yingshi);
        adminTeam = teamRepository.saveAndFlush(adminTeam);
        hrReruBu.getTeams().add(adminTeam);
        createCCofTeam(adminTeam,admin);
        orq.getTeams().add(adminTeam);
        officeRepository.saveAndFlush(orq);

    }

    private CostCenter createCCofTeam(Team t, Employee funcApprover){
        CostCenter costCenter = new CostCenter(t.getObjectName()+"Cost Center","CC-"+t.getCode(),t.getHierachyPath()+":+CC-"+t.getCode(),"admin",t);
        costCenter.setBmApprover(t.getTeamLeader());
        costCenter.setCostCenterManager(t.getTeamLeader());
        costCenter.setFunctionApprover(funcApprover);

        costCenter = costCenterRepository.save(costCenter);
        t.setCostCenter(costCenter);
        t = teamRepository.saveAndFlush(t);
        return costCenter;
    }


}
