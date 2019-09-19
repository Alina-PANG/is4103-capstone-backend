package capstone.is4103capstone.admin;

import capstone.is4103capstone.admin.repository.*;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.util.enums.EmployeeTypeEnum;
import capstone.is4103capstone.entities.helper.Address;
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
    TeamRepository teamRepository;
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CostCenterRepository costCenterRepository;

    @PostConstruct
    public void init(){
        List<Currency> currencyList = currencyRepository.findAll();
        if(currencyList == null || currencyList.size() == 0){
            createCurrency();
            createGeo();
            System.out.println("-----Created Geographies-----");
            createEmployee();
        }
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        if(costCenterList == null || costCenterList.size() == 0){
            createCostCenter();
        }
    }
    public void createCurrency(){
        Currency c = new Currency("Singapore Dollars","SGD","SGD");
        currencyRepository.save(c);
    }

    public void createCostCenter(){
        CostCenter costCenter = new CostCenter();
        costCenter.setCountry(countryRepository.findCountryByCode("SG"));
        costCenter.setCostCenterManager(employeeRepository.findEmployeeByUserName("yingshi2502"));
        costCenter.setCreatedBy("test");
        costCenter.setCode("TEST_COSTCENTER");
        costCenterRepository.save(costCenter);
    }
    public void createEmployee(){
        Employee newEmployee = new Employee("yingshi2502","Yingshi","Huang","","password");
        newEmployee.setEmployeeType(EmployeeTypeEnum.PERMANENT);
        newEmployee.setCode("ENPLOYEE-yingshi2502");
        newEmployee.setCreatedBy("admin");
        newEmployee.setLastModifiedBy("admin");

        Employee admin = new Employee("admin","adminFirstName","adminLastName","adminMiddleName","password");
        admin.setEmployeeType(EmployeeTypeEnum.PERMANENT);
        admin.setCreatedBy("admin");
        admin.setCode("EMPLOYEE-admin");
        admin.setLastModifiedBy("admin");



        employeeRepository.save(newEmployee);
        employeeRepository.save(admin);

        Team team = teamRepository.findTeamByCode("DTS");

        newEmployee.getMemberOfTeams().add(team);
        admin.getMemberOfTeams().add(team);
        team.getMembers().add(newEmployee);
        team.getMembers().add(admin);
        newEmployee.setHierachyPath("APAC-SG-TECH-DTS-yingshi2502");
        admin.setHierachyPath("APAC-ADMIN");
//
        newEmployee.setManager(admin);
        admin.getSubordinates().add(newEmployee);

        teamRepository.saveAndFlush(team);
        employeeRepository.saveAndFlush(newEmployee);
        employeeRepository.saveAndFlush(admin);

    }

    public void createGeo(){
        Region region = new Region("Asia-Pacific","APAC","APAC");
        region.setCreatedBy("admin");
        region.setLastModifiedBy("admin");
        region = regionRepository.save(region);

        Country countrySG = new Country("Singapore","SG","APAC-SG");
        countrySG.setCreatedBy("admin");
        countrySG.setLastModifiedBy("admin");
        countrySG = countryRepository.save(countrySG);
        countrySG.setRegion(region);

        Country countryHK = new Country("Hong Kong","HK","APAC-HK");
        countryHK.setCreatedBy("admin");
        countryHK.setLastModifiedBy("admin");
        countryHK = countryRepository.save(countryHK);
        countryHK.setRegion(region);

        region.getCountries().add(countrySG);
        region.getCountries().add(countryHK);

        CompanyFunction function = new CompanyFunction("Technology","Tech","Tech");
        function.setCreatedBy("admin");
        function.setLastModifiedBy("admin");
        function = functionRepository.save(function);
        function.getCountries().add(countryHK);
        function.getCountries().add(countrySG);
        countryHK.getFunctions().add(function);
        countrySG.getFunctions().add(function);

        Office office = new Office("One Raffles Quay","ORQ","APAC-SG-ORQ");
        Address orqAddress = new Address("1 Raffles Quay","","048583","Singapore","SG","APAC");
        office.setAddress(orqAddress);
        office.setNumOfFloors(2);

        office.setCreatedBy("admin");
        office.setLastModifiedBy("admin");
        office = officeRepository.save(office);
        office.setCountry(countrySG);
        countrySG.getOffices().add(office);

        function.getOfficesCodeOfFunction().add("ORQ");
        office.getFunctionsCodeInOffice().add("Tech");


        Team team = new Team("Data Security","DTS","Tech-DTS");
        team.setCreatedBy("admin");
        team.setLastModifiedBy("admin");
        team = teamRepository.save(team);
        team.setFunction(function);


        //last update
        regionRepository.saveAndFlush(region);
        countryRepository.saveAndFlush(countryHK);
        countryRepository.saveAndFlush(countrySG);
        functionRepository.saveAndFlush(function);
        officeRepository.saveAndFlush(office);
        teamRepository.saveAndFlush(team);

    }
}
