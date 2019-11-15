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
    @Autowired
    EmployeeOfficeWorkingScheduleRepository employeeOfficeWorkingScheduleRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    SeatInitializationService seatInitializationService;

    @PostConstruct
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
        if (seatAdminMatchRepository.findAll().isEmpty()){
            seatManagementInitialisation();
            seatInitializationService.initialiseSeatManagement();
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
            put("vaiyuqian", "yuqiancai987@gmail.com");
            put("joshuachew", "chewzhj@gmail.com");
        }};
        String defaultEmailAddr = "lonelytown98@gmail.com";
        for (int i = 0; i < firstNameList.length; i++) {
            createEmployeeTemplate(firstNameList[i], lastNameList[i], usernameList[i], "password", (String) usernameEmail.getOrDefault(usernameList[i], defaultEmailAddr), codeCpntList[i],adminRightConf[i]);
        }
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
        }
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

    private void seatManagementInitialisation() {
        Employee hangzhi = employeeRepository.findEmployeeByUserName("panghangzhi");
        Employee yuqian = employeeRepository.findEmployeeByUserName("caiyuqian");
        Employee admin = employeeRepository.findEmployeeByUserName("admin");
        Employee joshua = employeeRepository.findEmployeeByUserName("joshuachew");

        // Create Employee Office Working Schedule
        Office office = officeRepository.getOfficeByCode("ORQ");
        if (office!=null) {

            EmployeeOfficeWorkingSchedule yuqianWorkingSchedule = new EmployeeOfficeWorkingSchedule();
            Schedule yuqianSchedule1 = new Schedule();
            yuqianSchedule1.setStartDateTime(DateHelper.getDateByYearMonthDateHourMinute(2019, 10, 1, 9, 0));
            yuqianSchedule1.setEndDateTime(DateHelper.getDateByYearMonthDateHourMinute(2020, 1, 1, 18, 0));
            yuqianSchedule1 = scheduleRepository.save(yuqianSchedule1);
            yuqianWorkingSchedule.getSchedules().add(yuqianSchedule1);
            Schedule yuqianSchedule2 = new Schedule();
            yuqianSchedule2.setStartDateTime(DateHelper.getDateByYearMonthDateHourMinute(2019, 3, 1, 9, 0));
            yuqianSchedule2.setEndDateTime(DateHelper.getDateByYearMonthDateHourMinute(2020, 3, 30, 18, 0));
            yuqianSchedule2 = scheduleRepository.save(yuqianSchedule2);
            yuqianWorkingSchedule.getSchedules().add(yuqianSchedule2);
            yuqianWorkingSchedule.setEmployee(yuqian);
            yuqianWorkingSchedule.setOffice(office);

            EmployeeOfficeWorkingSchedule hangzhiWorkingSchedule = new EmployeeOfficeWorkingSchedule();
            Schedule hangzhiSchedule = new Schedule();
            hangzhiSchedule.setStartDateTime(DateHelper.getDateByYearMonthDateHourMinute(2018, 0, 1, 9, 0));
            hangzhiSchedule = scheduleRepository.save(hangzhiSchedule);
            hangzhiWorkingSchedule.getSchedules().add(hangzhiSchedule);
            hangzhiWorkingSchedule.setEmployee(hangzhi);
            hangzhiWorkingSchedule.setOffice(office);

            EmployeeOfficeWorkingSchedule joshuaWorkingSchedule = new EmployeeOfficeWorkingSchedule();
            Schedule joshuaSchedule = new Schedule();
            joshuaSchedule.setStartDateTime(DateHelper.getDateByYearMonthDateHourMinute(2018, 0, 1, 9, 0));
            joshuaSchedule = scheduleRepository.save(joshuaSchedule);
            joshuaWorkingSchedule.getSchedules().add(joshuaSchedule);
            joshuaWorkingSchedule.setEmployee(joshua);
            joshuaWorkingSchedule.setOffice(office);

            employeeOfficeWorkingScheduleRepository.save(yuqianWorkingSchedule);
            employeeOfficeWorkingScheduleRepository.save(hangzhiWorkingSchedule);
            employeeOfficeWorkingScheduleRepository.save(joshuaWorkingSchedule);
        }

        // Create management hierarchy levels

        // Fixed Income Development
        Team devTeam = teamRepository.findTeamByCode("T-SG-FixIncTech-Dev");

        hangzhi.setTeam(devTeam);
        devTeam.setTeamLeader(hangzhi);
        yuqian.setTeam(devTeam);
        devTeam.getMembers().add(hangzhi);
        devTeam.getMembers().add(yuqian);
        hangzhi.setHierachyPath(devTeam.getHierachyPath()+":"+hangzhi.getCode());
        yuqian.setHierachyPath(devTeam.getHierachyPath()+":"+yuqian.getCode());

        yuqian.setManager(hangzhi);
        hangzhi.getSubordinates().add(yuqian);

        hangzhi.setManager(joshua);
        joshua.getSubordinates().add(hangzhi);

        Team prodSuppTeam = teamRepository.findTeamByCode("T-SG-FixIncTech-ProdSupp");
        Team dbAdminTeam = teamRepository.findTeamByCode("T-SG-InfraTech-DBAdmin");
        Team networksTeam = teamRepository.findTeamByCode("T-SG-InfraTech-Networks");

        // Seat admin setup for different hierarchies
        // SG-Tech-FixIncTech-Dev
        SeatAdminMatch seatAdminMatch1 = new SeatAdminMatch();
        seatAdminMatch1.setHierarchyId(devTeam.getId());
        seatAdminMatch1.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatAdminMatch1.setSeatAdmin(hangzhi);

        // SG-Tech-FixIncTech
        SeatAdminMatch seatAdminMatch2 = new SeatAdminMatch();
        seatAdminMatch2.setHierarchyId(devTeam.getBusinessUnit().getId());
        seatAdminMatch2.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
        seatAdminMatch2.setSeatAdmin(joshua);

        // SG-Tech-FixIncTech
        SeatAdminMatch seatAdminMatch3 = new SeatAdminMatch();
        seatAdminMatch3.setHierarchyId(devTeam.getBusinessUnit().getFunction().getId());
        seatAdminMatch3.setHierarchyType(HierarchyTypeEnum.COMPANY_FUNCTION);
        seatAdminMatch3.setSeatAdmin(admin);

        // SG-Tech-FixIncTech-ProdSupp
        SeatAdminMatch seatAdminMatch4 = new SeatAdminMatch();
        seatAdminMatch4.setHierarchyId(prodSuppTeam.getId());
        seatAdminMatch4.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatAdminMatch4.setSeatAdmin(admin);

        // SG-Tech-InfraTech-DBAdmin
        SeatAdminMatch seatAdminMatch5 = new SeatAdminMatch();
        seatAdminMatch5.setHierarchyId(dbAdminTeam.getId());
        seatAdminMatch5.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatAdminMatch5.setSeatAdmin(admin);

        // SG-Tech-InfraTech-Networks
        SeatAdminMatch seatAdminMatch6 = new SeatAdminMatch();
        seatAdminMatch6.setHierarchyId(networksTeam.getId());
        seatAdminMatch6.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatAdminMatch6.setSeatAdmin(admin);

        // SG-Tech-InfraTech
        SeatAdminMatch seatAdminMatch7 = new SeatAdminMatch();
        seatAdminMatch7.setHierarchyId(networksTeam.getBusinessUnit().getId());
        seatAdminMatch7.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
        seatAdminMatch7.setSeatAdmin(admin);

        SeatAdminMatch seatAdminMatch8 = new SeatAdminMatch();
        seatAdminMatch8.setHierarchyId(devTeam.getOffice().getId());
        seatAdminMatch8.setHierarchyType(HierarchyTypeEnum.OFFICE);
        seatAdminMatch8.setSeatAdmin(admin);

        teamRepository.saveAndFlush(devTeam);
        employeeRepository.saveAndFlush(hangzhi);
        employeeRepository.saveAndFlush(yuqian);
        employeeRepository.saveAndFlush(joshua);
        seatAdminMatchRepository.save(seatAdminMatch1);
        seatAdminMatchRepository.save(seatAdminMatch2);
        seatAdminMatchRepository.save(seatAdminMatch3);
        seatAdminMatchRepository.save(seatAdminMatch4);
        seatAdminMatchRepository.save(seatAdminMatch5);
        seatAdminMatchRepository.save(seatAdminMatch6);
        seatAdminMatchRepository.save(seatAdminMatch7);
        seatAdminMatchRepository.save(seatAdminMatch8);
    }
}
