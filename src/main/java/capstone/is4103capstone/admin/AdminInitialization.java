package capstone.is4103capstone.admin;

import capstone.is4103capstone.admin.repository.*;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.helper.Address;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.EmployeeOfficeWorkingSchedule;
import capstone.is4103capstone.entities.seat.SeatRequestAdminMatch;
import capstone.is4103capstone.seat.repository.EmployeeOfficeWorkingScheduleRepository;
import capstone.is4103capstone.seat.repository.ScheduleRepository;
import capstone.is4103capstone.seat.repository.SeatRequestAdminMatchRepository;
import capstone.is4103capstone.util.enums.EmployeeTypeEnum;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
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
    SeatRequestAdminMatchRepository seatRequestAdminMatchRepository;
    @Autowired
    EmployeeOfficeWorkingScheduleRepository employeeOfficeWorkingScheduleRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    @PostConstruct
    public void init() {
        // Seat management subsystem initialisation for sr2
        seatManagementInitialisation();
//        List<Currency> currencyList = currencyRepository.findAll();
//        if (currencyList == null || currencyList.size() == 0) {
//            createCurrency();
//            createGeo();
//            System.out.println("-----Created Geographies-----");
//            createEmployee();
//        }
//        List<CostCenter> costCenterList = costCenterRepository.findAll();
//        if (costCenterList == null || costCenterList.size() == 0) {
//            createCostCenter();
//        }
//        String[] firstNameList = {"Hong","Tom","Amy","Daivd","Outsourcing","BM","Function"};
//        String[] lastNameList = {"Xu","Green","Brown","Cook","Staff","Approver","Approver"};
//        String[] usernameList = {"xuhong","user01","user02","user03","outsourcing1","bmapprover","funcapprover"};
//        String[] codeCpntList = {"5","6","7","8","OUTSOURCING","BM01","FUNC01"};
//        for (int i=0;i<firstNameList.length;i++){
//            createEmployeeTemplate(firstNameList[i],lastNameList[i],usernameList[i],"password","huangyingshi@gmail.com",codeCpntList[i]);
//        }
//         createEmployeeTemplate("PO","Last name","poteam","password","huangyingshi@gmail.com","PO_TEAM-1");


    }
    private void createEmployeeTemplate(String fName, String lName, String username, String password, String email,String codeCPnt){
        Employee newEmployee = new Employee(username, fName, lName, "", password);
        newEmployee.setEmployeeType(EmployeeTypeEnum.PERMANENT);
        newEmployee.setCode("EMP-"+codeCPnt);
        newEmployee.setCreatedBy("admin");
        newEmployee.setEmail(email);
        newEmployee.setLastModifiedBy("admin");
        employeeRepository.save(newEmployee);

    }
    public void createCurrency() {
        Currency c = new Currency("Singapore Dollars", "SGD");
        currencyRepository.save(c);
    }

    public void createCostCenter() {
        CostCenter costCenter = new CostCenter();
//        costCenter.setCountry(countryRepository.findCountryByCode("SG"));
        costCenter.setCostCenterManager(employeeRepository.findEmployeeByUserName("yingshi2502"));
        costCenter.setCreatedBy("test");
        costCenter.setCode("TEST_COSTCENTER");
        costCenterRepository.save(costCenter);
    }

    public void createEmployee() {
        Employee yingshi = new Employee("yingshi2502", "Yingshi", "Huang", "", "password");
        yingshi.setEmployeeType(EmployeeTypeEnum.PERMANENT);
        yingshi.setCode("EMPLOYEE-yingshi2502");
        yingshi.setCreatedBy("admin");
        yingshi.setEmail("yuqiancai987@gmail.com");
        yingshi.setLastModifiedBy("admin");

        Employee yuqian = new Employee("caiyuqian", "Yuqian", "Cai", "", "password");
        yuqian.setEmployeeType(EmployeeTypeEnum.TEMPORARY);
        yuqian.setCode("EMPLOYEE-caiyuqian");
        yuqian.setCreatedBy("admin");
        yuqian.setEmail("yuqiancai987@gmail.com");
        yuqian.setLastModifiedBy("admin");

        Employee joshua = new Employee("joshuachew", "Joshua", "Chew", "", "password");
        joshua.setEmployeeType(EmployeeTypeEnum.PERMANENT);
        joshua.setCode("EMPLOYEE-joshuachew");
        joshua.setCreatedBy("admin");
        joshua.setEmail("chewzhj@gmail.com");
        joshua.setLastModifiedBy("admin");

        Employee admin = new Employee("admin", "adminFirstName", "adminLastName", "adminMiddleName", "password");
        admin.setEmployeeType(EmployeeTypeEnum.PERMANENT);
        admin.setCreatedBy("admin");
        admin.setCode("EMPLOYEE-admin");
        admin.setLastModifiedBy("admin");


        // REST API Testing
        /*
        Employee restApiTestUser = new Employee("testuser", "Test", "User", "", "password");
        restApiTestUser.setEmployeeType(EmployeeTypeEnum.PERMANENT);
        restApiTestUser.setCode("ENPLOYEE-yingshi2502");
        restApiTestUser.setCreatedBy("admin");
        restApiTestUser.setLastModifiedBy("admin");
        */

        yingshi = employeeRepository.save(yingshi);
        yuqian = employeeRepository.save(yuqian);
        joshua = employeeRepository.save(joshua);
        admin = employeeRepository.save(admin);

        // Create Employee Office Working Schedule
        Optional<Office> optionalOffice = officeRepository.findByName("One Raffles Quay");
        if (optionalOffice.isPresent()) {
            Office office = optionalOffice.get();

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

            EmployeeOfficeWorkingSchedule yingshiWorkingSchedule = new EmployeeOfficeWorkingSchedule();
            Schedule yingshiSchedule = new Schedule();
            yingshiSchedule.setStartDateTime(DateHelper.getDateByYearMonthDateHourMinute(2018, 0, 1, 9, 0));
            yingshiSchedule = scheduleRepository.save(yingshiSchedule);
            yingshiWorkingSchedule.getSchedules().add(yingshiSchedule);
            yingshiWorkingSchedule.setEmployee(yingshi);
            yingshiWorkingSchedule.setOffice(office);

            employeeOfficeWorkingScheduleRepository.save(yuqianWorkingSchedule);
            employeeOfficeWorkingScheduleRepository.save(yingshiWorkingSchedule);
        }


        Team team1 = teamRepository.findTeamByCode("SG-Tech-FixIncTech-Dev");

        yingshi.setTeam(team1);
        team1.setTeamLeader(yingshi);
        yuqian.setTeam(team1);
        team1.getMembers().add(yingshi);
        team1.getMembers().add(yuqian);
        yingshi.setHierachyPath("APAC-SG-Tech-FixIncTech-Dev-yingshi2502");
        yuqian.setHierachyPath("APAC-SG-Tech-FixIncTech-Dev-caiyuqian");

        yuqian.setManager(yingshi);
        yingshi.getSubordinates().add(yuqian);

        yingshi.setManager(joshua);
        joshua.getSubordinates().add(yingshi);

        Team team2 = teamRepository.findTeamByCode("SG-Tech-FixIncTech-ProdSupp");
        Team team3 = teamRepository.findTeamByCode("SG-Tech-InfraTech-DBAdmin");
        Team team4 = teamRepository.findTeamByCode("SG-Tech-InfraTech-Networks");
        Team team5 = teamRepository.findTeamByCode("SG-HR-RCR-INT");


        // Seat admin setup for different hierarchies
        // SG-Tech-FixIncTech-Dev
        SeatRequestAdminMatch seatRequestAdminMatch1 = new SeatRequestAdminMatch();
        seatRequestAdminMatch1.setHierarchyId(team1.getId());
        seatRequestAdminMatch1.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch1.setSeatAdmin(yingshi);

        // SG-Tech-FixIncTech
        SeatRequestAdminMatch seatRequestAdminMatch2 = new SeatRequestAdminMatch();
        seatRequestAdminMatch2.setHierarchyId(team1.getBusinessUnit().getId());
        seatRequestAdminMatch2.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
        seatRequestAdminMatch2.setSeatAdmin(joshua);

        // SG-Tech-FixIncTech
        SeatRequestAdminMatch seatRequestAdminMatch3 = new SeatRequestAdminMatch();
        seatRequestAdminMatch3.setHierarchyId(team1.getBusinessUnit().getFunction().getId());
        seatRequestAdminMatch3.setHierarchyType(HierarchyTypeEnum.COMPANY_FUNCTION);
        seatRequestAdminMatch3.setSeatAdmin(admin);

        // SG-Tech-FixIncTech-ProdSupp
        SeatRequestAdminMatch seatRequestAdminMatch4 = new SeatRequestAdminMatch();
        seatRequestAdminMatch4.setHierarchyId(team2.getId());
        seatRequestAdminMatch4.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch4.setSeatAdmin(admin);

        // SG-Tech-InfraTech-DBAdmin
        SeatRequestAdminMatch seatRequestAdminMatch5 = new SeatRequestAdminMatch();
        seatRequestAdminMatch5.setHierarchyId(team3.getId());
        seatRequestAdminMatch5.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch5.setSeatAdmin(admin);

        // SG-Tech-InfraTech-Networks
        SeatRequestAdminMatch seatRequestAdminMatch6 = new SeatRequestAdminMatch();
        seatRequestAdminMatch6.setHierarchyId(team4.getId());
        seatRequestAdminMatch6.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch6.setSeatAdmin(admin);

        // SG-Tech-InfraTech
        SeatRequestAdminMatch seatRequestAdminMatch7 = new SeatRequestAdminMatch();
        seatRequestAdminMatch7.setHierarchyId(team4.getBusinessUnit().getId());
        seatRequestAdminMatch7.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
        seatRequestAdminMatch7.setSeatAdmin(admin);

        // ORQ Office
        SeatRequestAdminMatch seatRequestAdminMatch8 = new SeatRequestAdminMatch();
        seatRequestAdminMatch8.setHierarchyId(team1.getOffice().getId());
        seatRequestAdminMatch8.setHierarchyType(HierarchyTypeEnum.OFFICE);
        seatRequestAdminMatch8.setSeatAdmin(admin);

        // SG-HR-RCR-INT
        SeatRequestAdminMatch seatRequestAdminMatch9 = new SeatRequestAdminMatch();
        seatRequestAdminMatch9.setHierarchyId(team5.getId());
        seatRequestAdminMatch9.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch9.setSeatAdmin(admin);

        // SG-HR-RCR
        SeatRequestAdminMatch seatRequestAdminMatch10 = new SeatRequestAdminMatch();
        seatRequestAdminMatch10.setHierarchyId(team5.getBusinessUnit().getId());
        seatRequestAdminMatch10.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
        seatRequestAdminMatch10.setSeatAdmin(admin);

        // SG-HR
        SeatRequestAdminMatch seatRequestAdminMatch11 = new SeatRequestAdminMatch();
        seatRequestAdminMatch11.setHierarchyId(team5.getBusinessUnit().getFunction().getId());
        seatRequestAdminMatch11.setHierarchyType(HierarchyTypeEnum.COMPANY_FUNCTION);
        seatRequestAdminMatch11.setSeatAdmin(admin);

        teamRepository.saveAndFlush(team1);
        employeeRepository.saveAndFlush(yingshi);
        employeeRepository.saveAndFlush(yuqian);
        employeeRepository.saveAndFlush(joshua);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch1);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch2);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch3);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch4);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch5);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch6);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch7);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch8);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch9);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch10);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch11);
    }

    public void createGeo() {

        // ---------------------------------- Region ----------------------------------

        Region region = new Region("Asia-Pacific", "APAC", "APAC");
        region.setCreatedBy("admin");
        region.setLastModifiedBy("admin");
        region.setHierachyPath("APAC");
        region = regionRepository.save(region);

        Region region2 = new Region("The United Kingdom", "UK", "UK");
        region2.setCreatedBy("admin");
        region2.setLastModifiedBy("admin");
        region2.setHierachyPath("UK");
        region2 = regionRepository.save(region2);

        Region region3 = new Region("Europe", "EUR", "EUR");
        region3.setCreatedBy("admin");
        region3.setLastModifiedBy("admin");
        region3.setHierachyPath("EUR");
        region3 = regionRepository.save(region3);

        // ---------------------------------- Country ----------------------------------

        Country countrySG = new Country("Singapore", "SG", "APAC-SG");
        countrySG.setCreatedBy("admin");
        countrySG.setLastModifiedBy("admin");
        countrySG.setHierachyPath("APAC:SG");
        countrySG = countryRepository.save(countrySG);
        countrySG.setRegion(region);

        Country countryHK = new Country("Hong Kong", "HK", "APAC-HK");
        countryHK.setCreatedBy("admin");
        countryHK.setLastModifiedBy("admin");
        countryHK.setHierachyPath("APAC:HK");
        countryHK = countryRepository.save(countryHK);
        countryHK.setRegion(region);

        Country countryIND = new Country("India", "IND", "APAC-IND");
        countryIND.setCreatedBy("admin");
        countryIND.setLastModifiedBy("admin");
        countryIND.setHierachyPath("APAC:IND");
        countryIND = countryRepository.save(countryIND);
        countryIND.setRegion(region);

        region.getCountries().add(countrySG);
        region.getCountries().add(countryHK);
        region.getCountries().add(countryIND);

        // ---------------------------------- Function ----------------------------------

        CompanyFunction techFunctionSG = new CompanyFunction("Technology", "SG-Tech", "SG-Tech");
        techFunctionSG.setCreatedBy("admin");
        techFunctionSG.setLastModifiedBy("admin");
        techFunctionSG.setHierachyPath("APAC:SG:F-SG-Tech");
        techFunctionSG = functionRepository.save(techFunctionSG);
        techFunctionSG.setCountry(countrySG);
        countrySG.getFunctions().add(techFunctionSG);

        CompanyFunction hrFunctionSG = new CompanyFunction("Human Resource", "SG-HR", "SG-HR");
        hrFunctionSG.setCreatedBy("admin");
        hrFunctionSG.setLastModifiedBy("admin");
        hrFunctionSG.setHierachyPath("APAC:SG:F-SG-HR");
        hrFunctionSG = functionRepository.save(hrFunctionSG);
        hrFunctionSG.setCountry(countrySG);
        countrySG.getFunctions().add(hrFunctionSG);

        CompanyFunction salesFunctionSG = new CompanyFunction("Sales", "SG-Sales", "SG-Sales");
        salesFunctionSG.setCreatedBy("admin");
        salesFunctionSG.setLastModifiedBy("admin");
        salesFunctionSG.setHierachyPath("APAC:SG:F-SG-Sales");
        salesFunctionSG = functionRepository.save(hrFunctionSG);
        salesFunctionSG.setCountry(countrySG);
        countrySG.getFunctions().add(salesFunctionSG);


        // ---------------------------------- Office ----------------------------------

        Office office = new Office("One Raffles Quay", "ORQ", "APAC-SG-ORQ");
        Address orqAddress = new Address("1 Raffles Quay", "", "048583", "Singapore", "SG");
        office.setAddress(orqAddress);
        office.setNumOfFloors(2);

        office.setCreatedBy("admin");
        office.setLastModifiedBy("admin");
        office.setHierachyPath("APAC:SG:ORQ");
        office = officeRepository.save(office);
        office.setCountry(countrySG);
        countrySG.getOffices().add(office);

        // ---------------------------------- Business Unit ----------------------------------

        BusinessUnit businessUnitInfraTech = new BusinessUnit("Infrastructure Tech", "SG-Tech-InfraTech", "APAC:SG:F-SG-Tech:BU-SG-Tech-InfraTech");
        businessUnitInfraTech.setCreatedBy("admin");
        businessUnitInfraTech.setLastModifiedBy("admin");
        businessUnitInfraTech = businessUnitRepository.save(businessUnitInfraTech);
        businessUnitInfraTech.setFunction(techFunctionSG);

        BusinessUnit businessUnitCurrencyTech = new BusinessUnit("Currency Tech", "SG-Tech-CurrTech", "APAC:SG:F-SG-Tech:BU-SG-Tech-CurrTech");
        businessUnitCurrencyTech.setCreatedBy("admin");
        businessUnitCurrencyTech.setLastModifiedBy("admin");
        businessUnitCurrencyTech = businessUnitRepository.save(businessUnitCurrencyTech);
        businessUnitCurrencyTech.setFunction(techFunctionSG);

        BusinessUnit businessUnitFixIncomeTech = new BusinessUnit("Fix Income Tech", "SG-Tech-FixIncTech", "APAC:SG:F-SG-Tech:BU-SG-Tech-FixIncTech");
        businessUnitFixIncomeTech.setCreatedBy("admin");
        businessUnitFixIncomeTech.setLastModifiedBy("admin");
        businessUnitFixIncomeTech = businessUnitRepository.save(businessUnitFixIncomeTech);
        businessUnitFixIncomeTech.setFunction(techFunctionSG);

        BusinessUnit recruitingUnit = new BusinessUnit("Recruiting", "SG-HR-RCR", "APAC:SG:F-SG-HR:BU-SG-HR-RCR");
        recruitingUnit.setCreatedBy("admin");
        recruitingUnit.setLastModifiedBy("admin");
        recruitingUnit = businessUnitRepository.save(recruitingUnit);
        recruitingUnit.setFunction(hrFunctionSG);


        // ---------------------------------- Team ----------------------------------

        Team endUserComputingTeam = new Team("End User Computing", "SG-Tech-InfraTech-EndUserCom", "APAC:SG:F-SG-Tech:BU-SG-Tech-InfraTech:T-SG-Tech-InfraTech-EndUserCom");
        endUserComputingTeam.setCreatedBy("admin");
        endUserComputingTeam.setLastModifiedBy("admin");
        endUserComputingTeam.setOffice(office);
        endUserComputingTeam = teamRepository.save(endUserComputingTeam);
        endUserComputingTeam.setBusinessUnit(businessUnitInfraTech);

        Team dataCenterOpeTeam = new Team("Data Center Operation", "SG-Tech-InfraTech-DataCenOpr", "APAC:SG:F-SG-Tech:BU-SG-Tech-InfraTech:T-SG-Tech-InfraTech-DataCenOpr");
        dataCenterOpeTeam.setCreatedBy("admin");
        dataCenterOpeTeam.setLastModifiedBy("admin");
        dataCenterOpeTeam.setOffice(office);
        dataCenterOpeTeam = teamRepository.save(dataCenterOpeTeam);
        dataCenterOpeTeam.setBusinessUnit(businessUnitInfraTech);

        Team databaseAdminTeam = new Team("Database Admin", "SG-Tech-InfraTech-DBAdmin", "APAC:SG:F-SG-Tech:BU-SG-Tech-InfraTech:T-SG-Tech-InfraTech-DBAdmin");
        databaseAdminTeam.setCreatedBy("admin");
        databaseAdminTeam.setLastModifiedBy("admin");
        databaseAdminTeam.setOffice(office);
        databaseAdminTeam = teamRepository.save(databaseAdminTeam);
        databaseAdminTeam.setBusinessUnit(businessUnitInfraTech);

        Team networkTeam = new Team("Networks", "SG-Tech-InfraTech-Networks", "APAC:SG:F-SG-Tech:BU-SG-Tech-InfraTech:T-SG-Tech-InfraTech-Networks");
        networkTeam.setCreatedBy("admin");
        networkTeam.setLastModifiedBy("admin");
        networkTeam.setOffice(office);
        networkTeam = teamRepository.save(networkTeam);
        networkTeam.setBusinessUnit(businessUnitInfraTech);

        Team productionSupportTeam = new Team("Production Support", "SG-Tech-FixIncTech-ProdSupp", "APAC:SG:F-SG-Tech:BU-SG-Tech-InfraTech:T-SG-Tech-FixIncTech-ProdSupp");
        productionSupportTeam.setCreatedBy("admin");
        productionSupportTeam.setLastModifiedBy("admin");
        productionSupportTeam.setOffice(office);
        productionSupportTeam = teamRepository.save(productionSupportTeam);
        productionSupportTeam.setBusinessUnit(businessUnitFixIncomeTech);

        Team developmentTeam = new Team("Development", "SG-Tech-FixIncTech-Dev", "APAC:SG:F-SG-Tech:BU-SG-Tech-InfraTech:T-SG-Tech-FixIncTech-Dev");
        developmentTeam.setCreatedBy("admin");
        developmentTeam.setLastModifiedBy("admin");
        developmentTeam.setOffice(office);
        developmentTeam = teamRepository.save(developmentTeam);
        developmentTeam.setBusinessUnit(businessUnitFixIncomeTech);

        Team interviewTeam = new Team("Interview", "SG-HR-RCR-INT", "APAC:SG:F-SG-HR:BU-SG-HR-RCR:T-SG-HR-RCR-INT");
        interviewTeam.setCreatedBy("admin");
        interviewTeam.setLastModifiedBy("admin");
        interviewTeam.setOffice(office);
        interviewTeam = teamRepository.save(interviewTeam);
        interviewTeam.setBusinessUnit(recruitingUnit);

        //last update
        regionRepository.saveAndFlush(region);
        regionRepository.saveAndFlush(region2);
        regionRepository.saveAndFlush(region3);
        countryRepository.saveAndFlush(countryHK);
        countryRepository.saveAndFlush(countrySG);
        countryRepository.saveAndFlush(countryIND);
        functionRepository.saveAndFlush(techFunctionSG);
        functionRepository.saveAndFlush(hrFunctionSG);
        officeRepository.saveAndFlush(office);
        businessUnitRepository.saveAndFlush(businessUnitInfraTech);
        businessUnitRepository.saveAndFlush(businessUnitCurrencyTech);
        businessUnitRepository.saveAndFlush(businessUnitFixIncomeTech);
        businessUnitRepository.saveAndFlush(recruitingUnit);
        teamRepository.saveAndFlush(endUserComputingTeam);
        teamRepository.saveAndFlush(dataCenterOpeTeam);
        teamRepository.saveAndFlush(databaseAdminTeam);
        teamRepository.saveAndFlush(networkTeam);
        teamRepository.saveAndFlush(productionSupportTeam);
        teamRepository.saveAndFlush(developmentTeam);
        teamRepository.saveAndFlush(interviewTeam);

        office.getFunctionsCodeInOffice().add("SG-Tech");
        office.getFunctionsCodeInOffice().add("SG-HR");
        office.getFunctionsCodeInOffice().add("SG-Sales");
        office.getBusinessUnitsCodeInOffice().add("SG-Tech-InfraTech");
        office.getBusinessUnitsCodeInOffice().add("SG-Tech-CurrTech");
        office.getBusinessUnitsCodeInOffice().add("SG-Tech-FixIncTech");
        office.getBusinessUnitsCodeInOffice().add("SG-HR-RCR");
        techFunctionSG.getOfficesCodeOfFunction().add("ORQ");
        hrFunctionSG.getOfficesCodeOfFunction().add("ORQ");
        salesFunctionSG.getOfficesCodeOfFunction().add("ORQ");
        officeRepository.save(office);
        functionRepository.save(techFunctionSG);
        functionRepository.save(hrFunctionSG);
        functionRepository.save(salesFunctionSG);
    }

    private void seatManagementInitialisation() {

        Employee admin = employeeRepository.findEmployeeByUserName("admin");
        if (admin == null) return;

        // Create other employees
        Employee yuqian = new Employee("caiyuqian", "Yuqian", "Cai", "", "password");
        yuqian.setEmployeeType(EmployeeTypeEnum.TEMPORARY);
        yuqian.setCode("EMPLOYEE-caiyuqian");
        yuqian.setCreatedBy("admin");
        yuqian.setEmail("yuqiancai987@gmail.com");
        yuqian.setLastModifiedBy("admin");

        Employee hangzhi = new Employee("panghangzhi", "Hangzhi", "Pang", "", "password");
        hangzhi.setEmployeeType(EmployeeTypeEnum.PERMANENT);
        hangzhi.setCode("EMPLOYEE-hangzhipang");
        hangzhi.setCreatedBy("admin");
        hangzhi.setEmail("yuqiancai987@gmail.com");
        hangzhi.setLastModifiedBy("admin");

        Employee joshua = new Employee("joshuachew", "Joshua", "Chew", "", "password");
        joshua.setEmployeeType(EmployeeTypeEnum.PERMANENT);
        joshua.setCode("EMPLOYEE-joshuachew");
        joshua.setCreatedBy("admin");
        joshua.setEmail("chewzhj@gmail.com");
        joshua.setLastModifiedBy("admin");

        yuqian = employeeRepository.save(yuqian);
        joshua = employeeRepository.save(joshua);
        hangzhi = employeeRepository.save(hangzhi);

        // Create Employee Office Working Schedule
        Optional<Office> optionalOffice = officeRepository.findByName("One Raffles Quay");
        if (optionalOffice.isPresent()) {
            Office office = optionalOffice.get();

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
        Team devTeam = teamRepository.findTeamByCode("T-SG-Tech-FixIncomeTech-Dev");

        hangzhi.setTeam(devTeam);
        devTeam.setTeamLeader(hangzhi);
        yuqian.setTeam(devTeam);
        devTeam.getMembers().add(hangzhi);
        devTeam.getMembers().add(yuqian);
        hangzhi.setHierachyPath("APAC:SG:F-SG-Tech:BU-SG-Tech-FixIncTech:T-SG-Tech-FixIncTech-Dev:Employee-panghangzhi");
        yuqian.setHierachyPath("APAC:SG:F-SG-Tech:BU-SG-Tech-FixIncTech:T-SG-Tech-FixIncTech-Dev:Employee-caiyuqian");

        yuqian.setManager(hangzhi);
        hangzhi.getSubordinates().add(yuqian);

        hangzhi.setManager(joshua);
        joshua.getSubordinates().add(hangzhi);

        Team prodSuppTeam = teamRepository.findTeamByCode("T-SG-Tech-FixIncomeTech-ProdSupp");
        Team dbAdminTeam = teamRepository.findTeamByCode("T-SG-Tech-InfraTech-DBAdmin");
        Team networksTeam = teamRepository.findTeamByCode("T-SG-Tech-InfraTech-Networks");

        // Seat admin setup for different hierarchies
        // SG-Tech-FixIncTech-Dev
        SeatRequestAdminMatch seatRequestAdminMatch1 = new SeatRequestAdminMatch();
        seatRequestAdminMatch1.setHierarchyId(devTeam.getId());
        seatRequestAdminMatch1.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch1.setSeatAdmin(hangzhi);

        // SG-Tech-FixIncTech
        SeatRequestAdminMatch seatRequestAdminMatch2 = new SeatRequestAdminMatch();
        seatRequestAdminMatch2.setHierarchyId(devTeam.getBusinessUnit().getId());
        seatRequestAdminMatch2.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
        seatRequestAdminMatch2.setSeatAdmin(joshua);

        // SG-Tech-FixIncTech
        SeatRequestAdminMatch seatRequestAdminMatch3 = new SeatRequestAdminMatch();
        seatRequestAdminMatch3.setHierarchyId(devTeam.getBusinessUnit().getFunction().getId());
        seatRequestAdminMatch3.setHierarchyType(HierarchyTypeEnum.COMPANY_FUNCTION);
        seatRequestAdminMatch3.setSeatAdmin(admin);

        // SG-Tech-FixIncTech-ProdSupp
        SeatRequestAdminMatch seatRequestAdminMatch4 = new SeatRequestAdminMatch();
        seatRequestAdminMatch4.setHierarchyId(prodSuppTeam.getId());
        seatRequestAdminMatch4.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch4.setSeatAdmin(admin);

        // SG-Tech-InfraTech-DBAdmin
        SeatRequestAdminMatch seatRequestAdminMatch5 = new SeatRequestAdminMatch();
        seatRequestAdminMatch5.setHierarchyId(dbAdminTeam.getId());
        seatRequestAdminMatch5.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch5.setSeatAdmin(admin);

        // SG-Tech-InfraTech-Networks
        SeatRequestAdminMatch seatRequestAdminMatch6 = new SeatRequestAdminMatch();
        seatRequestAdminMatch6.setHierarchyId(networksTeam.getId());
        seatRequestAdminMatch6.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch6.setSeatAdmin(admin);

        // SG-Tech-InfraTech
        SeatRequestAdminMatch seatRequestAdminMatch7 = new SeatRequestAdminMatch();
        seatRequestAdminMatch7.setHierarchyId(networksTeam.getBusinessUnit().getId());
        seatRequestAdminMatch7.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
        seatRequestAdminMatch7.setSeatAdmin(admin);

        SeatRequestAdminMatch seatRequestAdminMatch8 = new SeatRequestAdminMatch();
        seatRequestAdminMatch8.setHierarchyId(devTeam.getOffice().getId());
        seatRequestAdminMatch8.setHierarchyType(HierarchyTypeEnum.OFFICE);
        seatRequestAdminMatch8.setSeatAdmin(admin);

        teamRepository.saveAndFlush(devTeam);
        employeeRepository.saveAndFlush(hangzhi);
        employeeRepository.saveAndFlush(yuqian);
        employeeRepository.saveAndFlush(joshua);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch1);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch2);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch3);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch4);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch5);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch6);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch7);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch8);
    }
}
