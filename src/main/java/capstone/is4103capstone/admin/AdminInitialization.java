package capstone.is4103capstone.admin;

import capstone.is4103capstone.admin.repository.*;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.helper.Address;
import capstone.is4103capstone.util.enums.EmployeeTypeEnum;
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

    @PostConstruct
    public void init() {

        List<Currency> currencyList = currencyRepository.findAll();
        if (currencyList == null || currencyList.size() == 0) {
            createCurrency();
            createGeo();
            System.out.println("-----Created Geographies-----");
            createEmployee();
        }
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        if (costCenterList == null || costCenterList.size() == 0) {
            createCostCenter();
        }
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
        newEmployee2.setEmployeeType(EmployeeTypeEnum.WORKINGFROMHOME);
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

        employeeRepository.save(newEmployee);
        employeeRepository.save(newEmployee2);
        employeeRepository.save(admin);

        Team team = teamRepository.findTeamByCode("GCP-T1");

        newEmployee.getMemberOfTeams().add(team);
        newEmployee2.getMemberOfTeams().add(team);
        team.getMembers().add(newEmployee);
        team.getMembers().add(newEmployee2);
        newEmployee.setHierachyPath("APAC-SG-TECH-GCP-T1-yingshi2502");
        newEmployee2.setHierachyPath("APAC-SG-TECH-GCP-T1-caiyuqian");

        newEmployee2.setManager(newEmployee);
        newEmployee.getSubordinates().add(newEmployee2);

        teamRepository.saveAndFlush(team);
        employeeRepository.saveAndFlush(newEmployee);
        employeeRepository.saveAndFlush(newEmployee2);

    }

    public void createGeo() {
        Region region = new Region("Asia-Pacific", "APAC", "APAC");
        region.setCreatedBy("admin");
        region.setLastModifiedBy("admin");
        region = regionRepository.save(region);

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

        region.getCountries().add(countrySG);
        region.getCountries().add(countryHK);

        CompanyFunction function = new CompanyFunction("Technology", "Tech", "Tech");
        function.setCreatedBy("admin");
        function.setLastModifiedBy("admin");
        function = functionRepository.save(function);
        function.getCountries().add(countryHK);
        function.getCountries().add(countrySG);
        countryHK.getFunctions().add(function);
        countrySG.getFunctions().add(function);

        CompanyFunction function2 = new CompanyFunction("Human Resource", "HR", "HR");
        function2.setCreatedBy("admin");
        function2.setLastModifiedBy("admin");
        function2 = functionRepository.save(function2);
        function2.getCountries().add(countryHK);
        function2.getCountries().add(countrySG);
        countryHK.getFunctions().add(function2);
        countrySG.getFunctions().add(function2);

        Office office = new Office("One Raffles Quay", "ORQ", "APAC-SG-ORQ");
        Address orqAddress = new Address("1 Raffles Quay", "", "048583", "Singapore", "SG");
        office.setAddress(orqAddress);
        office.setNumOfFloors(2);

        office.setCreatedBy("admin");
        office.setLastModifiedBy("admin");
        office = officeRepository.save(office);
        office.setCountry(countrySG);
        countrySG.getOffices().add(office);

        BusinessUnit businessUnit = new BusinessUnit("Google Cloud Platform", "GCP", "Tech-GCP");
        businessUnit.setCreatedBy("admin");
        businessUnit.setLastModifiedBy("admin");
        businessUnit = businessUnitRepository.save(businessUnit);
        businessUnit.setFunction(function);

        BusinessUnit businessUnit2 = new BusinessUnit("App Development", "APP", "Tech-APP");
        businessUnit2.setCreatedBy("admin");
        businessUnit2.setLastModifiedBy("admin");
        businessUnit2 = businessUnitRepository.save(businessUnit2);
        businessUnit2.setFunction(function);

        Team team = new Team("GCP team 1", "GCP-T1", "Tech-GCP-T1");
        team.setCreatedBy("admin");
        team.setLastModifiedBy("admin");
        team = teamRepository.save(team);
        team.setBusinessUnit(businessUnit);
        team.setFunction(function);

        Team team2 = new Team("GCP team 2", "GCP-T2", "Tech-GCP-T1");
        team2.setCreatedBy("admin");
        team2.setLastModifiedBy("admin");
        team2 = teamRepository.save(team2);
        team2.setBusinessUnit(businessUnit);
        team2.setFunction(function);

        Team team3 = new Team("HR Team 1", "HR-T1", "HR-T1");
        team3.setCreatedBy("admin");
        team3.setLastModifiedBy("admin");
        team3 = teamRepository.save(team3);
        team3.setFunction(function2);

        //last update
        regionRepository.saveAndFlush(region);
        countryRepository.saveAndFlush(countryHK);
        countryRepository.saveAndFlush(countrySG);
        functionRepository.saveAndFlush(function);
        functionRepository.saveAndFlush(function2);
        officeRepository.saveAndFlush(office);
        businessUnitRepository.saveAndFlush(businessUnit);
        businessUnitRepository.saveAndFlush(businessUnit2);
        teamRepository.saveAndFlush(team);
        teamRepository.saveAndFlush(team2);
        teamRepository.saveAndFlush(team3);

        office.getFunctionsCodeInOffice().add("Tech");
        office.getFunctionsCodeInOffice().add("HR");
        office.getBusinessUnitsCodeInOffice().add("GCP");
        office.getBusinessUnitsCodeInOffice().add("APP");
        businessUnit.getOfficesCodeOfBusinessUnit().add("ORQ");
        businessUnit2.getOfficesCodeOfBusinessUnit().add("ORQ");
        function.getOfficesCodeOfFunction().add("ORQ");
        function2.getOfficesCodeOfFunction().add("ORQ");
        officeRepository.save(office);
        functionRepository.save(function);
        functionRepository.save(function2);
    }
}
