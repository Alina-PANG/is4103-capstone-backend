package capstone.is4103capstone.supplychain;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.Merchandise;
import capstone.is4103capstone.entities.supplyChain.Action;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.Dispute;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.MerchandiseRepository;
import capstone.is4103capstone.supplychain.Repository.*;
import capstone.is4103capstone.util.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class SupplyChainInitialization {
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    MerchandiseRepository merchandiseRepository;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    DisputeRepository disputeRepository;
    @Autowired
    ActionRepository actionRepository;
    @Autowired
    OutsourcingAssessmentLineRepository outsourcingAssessmentLineRepository;
    @Autowired
    OutsourcingAssessmentSectionRepository outsourcingAssessmentSectionRepository;
    @Autowired
    OutsourcingAssessmentRepository outsourcingAssessmentRepository;
    @Autowired
    OutsourcingRepository outsourcingRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @PostConstruct
    public void init(){
        createMechandise();
        createVendors();
        createContract();
        createDispute();
        createAction();
        createOutsourcingAssessmentLine();
        createOutsourcingAssessmentSection();
        createOutsourcingAssessment();
        createOutsourcing();
    }

    public void createMechandise(){
        Merchandise merchandise1 = new Merchandise("Banana", "SG_Banana", "dummy_hierachy", 2.0, "SGD");
       merchandise1.setCreatedBy("Admin");
        merchandise1.setLastModifiedBy("admin");
        merchandiseRepository.save(merchandise1);
    }

    public void createVendors(){
        Vendor vendor1 = new Vendor();
        vendor1.setBusinessUnit("Lenovo");
        vendor1.setServiceDescription("Lenovo will provide PC and harware to NatWest.");
        vendor1.setRelationshipManagerName("Manager1");
        vendor1.setRelationshipManagerEmail("Manager1@gmail.com");
        vendor1.setBillingContactName("BillStaff");
        vendor1.setBillingContactEmail("BillStaff@gmail.com");
        vendor1.setEscalationContactName("EscalationStaff");
        vendor1.setEscalationContactEmail("EscalationStaff@gmail.com");
        vendor1.setCreatedBy("Admin");
        vendor1.setCode("Vendor-Lenovo");
        vendor1.setLastModifiedBy("admin");

        Merchandise merchandise = merchandiseRepository.findMerchandiseByCode("SG_Banana").get(0);
        vendor1.getMerchandises().add(merchandise);

        vendorRepository.save(vendor1);
        merchandise.setVendor(vendor1);
        merchandiseRepository.saveAndFlush(merchandise);
    }


    public void createContract(){
        Contract contract1 = new Contract();
        contract1.setPurchaseType(PurchaseTypeEnum.TERMLICENSE);
        contract1.setSpendType("DummyType");
        contract1.setContractStatus(ContractStatusEnum.ACTIVE);
        contract1.setContractType(ContractTypeEnum.SCHEDULE);
        contract1.setContractTerm("9 months");
        Date startDate = new Date();
        long time = startDate.getTime();
        Timestamp startTs = new Timestamp(time);
        contract1.setStartDate(startTs);

        Merchandise merchandise = merchandiseRepository.findMerchandiseByCode("SG_Banana").get(0);
        Vendor vendor = vendorRepository.findVendorByCode("Vendor-Lenovo").get(0);
        Employee newEmployee = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong").get(0);

        contract1.setVendor(vendor);
        contract1.getMerchandises().add(merchandise);
        contract1.setEmployeeInChargeContract(newEmployee);
        contractRepository.save(contract1);

        vendor.setContract(contract1);
        merchandise.getContractList().add(contract1);
        newEmployee.getContractInCharged().add(contract1);

        vendorRepository.saveAndFlush(vendor);
        merchandiseRepository.saveAndFlush(merchandise);
        employeeRepository.saveAndFlush(newEmployee);
    }

    public void createDispute(){
        Employee handler = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong").get(0);
        Employee creator = employeeRepository.findEmployeeByCode("EMPLOYEE-admin").get(0);

        Dispute dispute1 = new Dispute("This is a ... dispute.", DisputeStatusEnum.ACTIVE, handler, creator);
        disputeRepository.save(dispute1);

        handler.getDisputesHandling().add(dispute1);
        creator.getDisputesCreated().add(dispute1);
        employeeRepository.saveAndFlush(handler);
        employeeRepository.saveAndFlush(creator);
    }

    public void createAction(){
        Employee assignee = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong").get(0);
        Employee creator = employeeRepository.findEmployeeByCode("EMPLOYEE-admin").get(0);

        Action action = new Action("This is a ... action.", ActionStatusEnum.ACTIVE, assignee, creator);
        actionRepository.save(action);

        assignee.getActionsAssigned().add(action);
        creator.getActionsCreated().add(action);
        employeeRepository.saveAndFlush(assignee);
        employeeRepository.saveAndFlush(creator);
    }

    public void createOutsourcingAssessmentLine(){

    }

    public void createOutsourcingAssessmentSection(){

    }

    public void createOutsourcingAssessment(){

    }

    public void createOutsourcing(){

    }
}
