package capstone.is4103capstone.admin;

import capstone.is4103capstone.admin.repository.*;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.helper.Address;
import capstone.is4103capstone.entities.seat.SeatRequestAdminMatch;
import capstone.is4103capstone.seat.repository.SeatRequestAdminMatchRepository;
import capstone.is4103capstone.util.enums.EmployeeTypeEnum;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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

    @PostConstruct
    public void init() {
//
        List<Currency> currencyList = currencyRepository.findAll();
        if (currencyList == null || currencyList.size() == 0) {
            createCurrency();
            createGeo();
            System.out.println("-----Created Geographies-----");
            createEmployee();
        }
//        List<CostCenter> costCenterList = costCenterRepository.findAll();
//        if (costCenterList == null || costCenterList.size() == 0) {
//            createCostCenter();
//        }
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
        Employee newEmployee = new Employee("yingshi2502", "Yingshi", "Huang", "", "password");
        newEmployee.setEmployeeType(EmployeeTypeEnum.PERMANENT);
        newEmployee.setCode("EMPLOYEE-yingshi2502");
        newEmployee.setCreatedBy("admin");
        newEmployee.setLastModifiedBy("admin");

        Employee newEmployee2 = new Employee("caiyuqian", "Yuqian", "Cai", "", "password");
        newEmployee2.setEmployeeType(EmployeeTypeEnum.WORKING_FROM_HOME);
        newEmployee2.setCode("EMPLOYEE-caiyuqian");
        newEmployee2.setCreatedBy("admin");
        newEmployee2.setLastModifiedBy("admin");

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

        newEmployee = employeeRepository.save(newEmployee);
        newEmployee2 = employeeRepository.save(newEmployee2);
        admin = employeeRepository.save(admin);

        Team team1 = teamRepository.findTeamByCode("SG-Tech-FixIncTech-Dev");

        newEmployee.setTeam(team1);
        newEmployee2.setTeam(team1);
        team1.getMembers().add(newEmployee);
        team1.getMembers().add(newEmployee2);
        newEmployee.setHierachyPath("APAC-SG-Tech-FixIncTech-Dev-yingshi2502");
        newEmployee2.setHierachyPath("APAC-SG-Tech-FixIncTech-Dev-caiyuqian");

        newEmployee2.setManager(newEmployee);
        newEmployee.getSubordinates().add(newEmployee2);

        Team team2 = teamRepository.findTeamByCode("SG-Tech-FixIncTech-ProdSupp");
        Team team3 = teamRepository.findTeamByCode("SG-Tech-InfraTech-DBAdmin");
        Team team4 = teamRepository.findTeamByCode("SG-Tech-InfraTech-Networks");

        // Seat admin setup for different hierarchies
        SeatRequestAdminMatch seatRequestAdminMatch1 = new SeatRequestAdminMatch();
        seatRequestAdminMatch1.setHierarchyId(team1.getId());
        seatRequestAdminMatch1.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch1.setSeatAdmin(newEmployee);

        SeatRequestAdminMatch seatRequestAdminMatch2 = new SeatRequestAdminMatch();
        seatRequestAdminMatch2.setHierarchyId(team1.getBusinessUnit().getId());
        seatRequestAdminMatch2.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
        seatRequestAdminMatch2.setSeatAdmin(admin);

        SeatRequestAdminMatch seatRequestAdminMatch3 = new SeatRequestAdminMatch();
        seatRequestAdminMatch3.setHierarchyId(team1.getBusinessUnit().getFunction().getId());
        seatRequestAdminMatch3.setHierarchyType(HierarchyTypeEnum.COMPANY_FUNCTION);
        seatRequestAdminMatch3.setSeatAdmin(admin);

        SeatRequestAdminMatch seatRequestAdminMatch4 = new SeatRequestAdminMatch();
        seatRequestAdminMatch4.setHierarchyId(team2.getId());
        seatRequestAdminMatch4.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch4.setSeatAdmin(admin);

        SeatRequestAdminMatch seatRequestAdminMatch5 = new SeatRequestAdminMatch();
        seatRequestAdminMatch5.setHierarchyId(team3.getId());
        seatRequestAdminMatch5.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch5.setSeatAdmin(admin);

        SeatRequestAdminMatch seatRequestAdminMatch6 = new SeatRequestAdminMatch();
        seatRequestAdminMatch6.setHierarchyId(team4.getId());
        seatRequestAdminMatch6.setHierarchyType(HierarchyTypeEnum.TEAM);
        seatRequestAdminMatch6.setSeatAdmin(admin);

        SeatRequestAdminMatch seatRequestAdminMatch7 = new SeatRequestAdminMatch();
        seatRequestAdminMatch7.setHierarchyId(team4.getBusinessUnit().getId());
        seatRequestAdminMatch7.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
        seatRequestAdminMatch7.setSeatAdmin(admin);

        teamRepository.saveAndFlush(team1);
        employeeRepository.saveAndFlush(newEmployee);
        employeeRepository.saveAndFlush(newEmployee2);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch1);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch2);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch3);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch4);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch5);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch6);
        seatRequestAdminMatchRepository.save(seatRequestAdminMatch7);
    }

    public void createGeo() {

        // ---------------------------------- Region ----------------------------------

        Region region = new Region("Asia-Pacific", "APAC", "APAC");
        region.setCreatedBy("admin");
        region.setLastModifiedBy("admin");
        region = regionRepository.save(region);

        Region region2 = new Region("The United Kingdom", "UK", "UK");
        region2.setCreatedBy("admin");
        region2.setLastModifiedBy("admin");
        region2 = regionRepository.save(region2);

        Region region3 = new Region("Europe", "EUR", "EUR");
        region3.setCreatedBy("admin");
        region3.setLastModifiedBy("admin");
        region3 = regionRepository.save(region3);

        // ---------------------------------- Country ----------------------------------

        Country countrySG = new Country("Singapore", "SG", "APAC-SG");
        countrySG.setCreatedBy("admin");
        countrySG.setLastModifiedBy("admin");
        countrySG = countryRepository.save(countrySG);
        countrySG.setRegion(region);

        Country countryHK = new Country("Hong Kong", "HK", "APAC-HK");
        countryHK.setCreatedBy("admin");
        countryHK.setLastModifiedBy("admin");
        countryHK = countryRepository.save(countryHK);
        countryHK.setRegion(region);

        Country countryIND = new Country("India", "IND", "APAC-IND");
        countryIND.setCreatedBy("admin");
        countryIND.setLastModifiedBy("admin");
        countryIND = countryRepository.save(countryIND);
        countryIND.setRegion(region);

        region.getCountries().add(countrySG);
        region.getCountries().add(countryHK);
        region.getCountries().add(countryIND);

        // ---------------------------------- Function ----------------------------------

        CompanyFunction techFunctionSG = new CompanyFunction("Technology", "SG-Tech", "SG-Tech");
        techFunctionSG.setCreatedBy("admin");
        techFunctionSG.setLastModifiedBy("admin");
        techFunctionSG = functionRepository.save(techFunctionSG);
        techFunctionSG.setCountry(countrySG);
        countrySG.getFunctions().add(techFunctionSG);

        CompanyFunction hrFunctionSG = new CompanyFunction("Human Resource", "SG-HR", "SG-HR");
        hrFunctionSG.setCreatedBy("admin");
        hrFunctionSG.setLastModifiedBy("admin");
        hrFunctionSG = functionRepository.save(hrFunctionSG);
        hrFunctionSG.setCountry(countrySG);
        countrySG.getFunctions().add(hrFunctionSG);

        CompanyFunction salesFunctionSG = new CompanyFunction("Sales", "SG-Sales", "SG-Sales");
        salesFunctionSG.setCreatedBy("admin");
        salesFunctionSG.setLastModifiedBy("admin");
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
        office = officeRepository.save(office);
        office.setCountry(countrySG);
        countrySG.getOffices().add(office);

        // ---------------------------------- Business Unit ----------------------------------

        BusinessUnit businessUnitInfraTech = new BusinessUnit("Infrastructure Tech", "SG-Tech-InfraTech", "SG-Tech-InfraTech");
        businessUnitInfraTech.setCreatedBy("admin");
        businessUnitInfraTech.setLastModifiedBy("admin");
        businessUnitInfraTech = businessUnitRepository.save(businessUnitInfraTech);
        businessUnitInfraTech.setFunction(techFunctionSG);

        BusinessUnit businessUnitCurrencyTech = new BusinessUnit("Currency Tech", "SG-Tech-CurrTech", "SG-Tech-CurrTech");
        businessUnitCurrencyTech.setCreatedBy("admin");
        businessUnitCurrencyTech.setLastModifiedBy("admin");
        businessUnitCurrencyTech = businessUnitRepository.save(businessUnitCurrencyTech);
        businessUnitCurrencyTech.setFunction(techFunctionSG);

        BusinessUnit businessUnitFixIncomeTech = new BusinessUnit("Fix Income Tech", "SG-Tech-FixIncTech", "SG-Tech-FixIncTech");
        businessUnitFixIncomeTech.setCreatedBy("admin");
        businessUnitFixIncomeTech.setLastModifiedBy("admin");
        businessUnitFixIncomeTech = businessUnitRepository.save(businessUnitFixIncomeTech);
        businessUnitFixIncomeTech.setFunction(techFunctionSG);

        // ---------------------------------- Team ----------------------------------

        Team endUserComputingTeam = new Team("End User Computing", "SG-Tech-InfraTech-EndUserCom", "SG-Tech-InfraTech-EndUserCom");
        endUserComputingTeam.setCreatedBy("admin");
        endUserComputingTeam.setLastModifiedBy("admin");
        endUserComputingTeam.setOffice(office);
        endUserComputingTeam = teamRepository.save(endUserComputingTeam);
        endUserComputingTeam.setBusinessUnit(businessUnitInfraTech);

        Team dataCenterOpeTeam = new Team("Data Center Operation", "SG-Tech-InfraTech-DataCenOpr", "SG-Tech-InfraTech-DataCenOpr");
        dataCenterOpeTeam.setCreatedBy("admin");
        dataCenterOpeTeam.setLastModifiedBy("admin");
        dataCenterOpeTeam.setOffice(office);
        dataCenterOpeTeam = teamRepository.save(dataCenterOpeTeam);
        dataCenterOpeTeam.setBusinessUnit(businessUnitInfraTech);

        Team databaseAdminTeam = new Team("Database Admin", "SG-Tech-InfraTech-DBAdmin", "SG-Tech-InfraTech-DBAdmin");
        databaseAdminTeam.setCreatedBy("admin");
        databaseAdminTeam.setLastModifiedBy("admin");
        databaseAdminTeam.setOffice(office);
        databaseAdminTeam = teamRepository.save(databaseAdminTeam);
        databaseAdminTeam.setBusinessUnit(businessUnitInfraTech);

        Team networkTeam = new Team("Networks", "SG-Tech-InfraTech-Networks", "SG-Tech-InfraTech-Networks");
        networkTeam.setCreatedBy("admin");
        networkTeam.setLastModifiedBy("admin");
        networkTeam.setOffice(office);
        networkTeam = teamRepository.save(networkTeam);
        networkTeam.setBusinessUnit(businessUnitInfraTech);

        Team productionSupportTeam = new Team("Production Support", "SG-Tech-FixIncTech-ProdSupp", "SG-Tech-FixIncTech-ProdSupp");
        productionSupportTeam.setCreatedBy("admin");
        productionSupportTeam.setLastModifiedBy("admin");
        productionSupportTeam.setOffice(office);
        productionSupportTeam = teamRepository.save(productionSupportTeam);
        productionSupportTeam.setBusinessUnit(businessUnitFixIncomeTech);

        Team developmentTeam = new Team("Development", "SG-Tech-FixIncTech-Dev", "SG-Tech-FixIncTech-Dev");
        developmentTeam.setCreatedBy("admin");
        developmentTeam.setLastModifiedBy("admin");
        developmentTeam.setOffice(office);
        developmentTeam = teamRepository.save(developmentTeam);
        developmentTeam.setBusinessUnit(businessUnitFixIncomeTech);

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
        teamRepository.saveAndFlush(endUserComputingTeam);
        teamRepository.saveAndFlush(dataCenterOpeTeam);
        teamRepository.saveAndFlush(databaseAdminTeam);
        teamRepository.saveAndFlush(networkTeam);
        teamRepository.saveAndFlush(productionSupportTeam);
        teamRepository.saveAndFlush(developmentTeam);

        office.getFunctionsCodeInOffice().add("SG-Tech");
        office.getFunctionsCodeInOffice().add("SG-HR");
        office.getFunctionsCodeInOffice().add("SG-Sales");
        office.getBusinessUnitsCodeInOffice().add("SG-Tech-InfraTech");
        office.getBusinessUnitsCodeInOffice().add("SG-Tech-CurrTech");
        office.getBusinessUnitsCodeInOffice().add("SG-Tech-FixIncTech");
        techFunctionSG.getOfficesCodeOfFunction().add("ORQ");
        hrFunctionSG.getOfficesCodeOfFunction().add("ORQ");
        salesFunctionSG.getOfficesCodeOfFunction().add("ORQ");
        officeRepository.save(office);
        functionRepository.save(techFunctionSG);
        functionRepository.save(hrFunctionSG);
        functionRepository.save(salesFunctionSG);
    }
}
