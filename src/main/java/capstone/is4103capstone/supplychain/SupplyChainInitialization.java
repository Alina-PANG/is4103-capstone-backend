package capstone.is4103capstone.supplychain;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.Merchandise;
import capstone.is4103capstone.entities.supplyChain.*;
import capstone.is4103capstone.finance.Repository.MerchandiseRepository;
import capstone.is4103capstone.supplychain.Repository.*;
import capstone.is4103capstone.util.enums.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
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
    @Autowired
    ContractLineRepository contractLineRepository;

    private final String opsUserId = "yingshi2502";

    @PostConstruct
    @Transactional

    public void init() throws ParseException {
        if(employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong") == null){
            Employee newEmployee2 = new Employee("xuhong","hong","xu","","password");
            newEmployee2.setEmployeeType(EmployeeTypeEnum.PERMANENT);
            newEmployee2.setCode("EMPLOYEE-xuhong");
            employeeRepository.save(newEmployee2);

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
    }

    public void createMechandise() {
        Merchandise merchandise1 = new Merchandise("Banana", "SG_Banana", "dummy_hierachy", "piece", opsUserId);
        Merchandise merchandise2 = new Merchandise("Mango", "SG_Mango", "dummy_hierachy", "piece", opsUserId);

        merchandise1.setCreatedBy("xuhong");
        merchandise1.setCreatedDateTime(new Date());
        merchandise1.setLastModifiedBy("xuhong");
        merchandise1.setLastModifiedDateTime(new Date());

        merchandise2.setCreatedBy("xuhong");
        merchandise2.setCreatedDateTime(new Date());
        merchandise2.setLastModifiedBy("xuhong");
        merchandise2.setLastModifiedDateTime(new Date());
        merchandiseRepository.save(merchandise1);
        merchandiseRepository.save(merchandise2);
    }

    public void createVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setServiceDescription("Lenovo will provide PC and harware to NatWest.");
        vendor1.setRelationshipManagerName("Manager1");
        vendor1.setRelationshipManagerEmail("Manager1@gmail.com");
        vendor1.setBillingContactName("BillStaff");
        vendor1.setBillingContactEmail("BillStaff@gmail.com");
        vendor1.setEscalationContactName("EscalationStaff");
        vendor1.setEscalationContactEmail("EscalationStaff@gmail.com");
        vendor1.setCode("Vendor-Lenovo");
        vendor1.setCreatedBy("xuhong");
        vendor1.setCreatedDateTime(new Date());
        vendor1.setLastModifiedBy("xuhong");
        vendor1.setLastModifiedDateTime(new Date());

        Merchandise merchandise = merchandiseRepository.findMerchandiseByCode("SG_Banana");
        vendor1.getMerchandises().add(merchandise);

        vendorRepository.save(vendor1);
        merchandise.setVendor(vendor1);
        merchandiseRepository.saveAndFlush(merchandise);
    }

    @Transactional
    public void createContract() throws ParseException {

        Merchandise banana = merchandiseRepository.findMerchandiseByCode("SG_Banana");
        Merchandise mango = merchandiseRepository.findMerchandiseByCode("SG_Mango");

        ContractLine contractLine1 = new ContractLine("SG_Banana", BigDecimal.valueOf(2.0), "SGD", opsUserId);
        ContractLine contractLine2 = new ContractLine("SG_Mango", BigDecimal.valueOf(5.0), "SGD", opsUserId);

        contractLine1.setCreatedBy("xuhong");
        contractLine1.setCreatedDateTime(new Date());
        contractLine1.setLastModifiedBy("xuhong");
        contractLine1.setLastModifiedDateTime(new Date());

        contractLine2.setCreatedBy("xuhong");
        contractLine2.setCreatedDateTime(new Date());
        contractLine2.setLastModifiedBy("xuhong");
        contractLine2.setLastModifiedDateTime(new Date());

        contractLine1 = contractLineRepository.save(contractLine1);
        contractLine2 = contractLineRepository.save(contractLine2);

        contractLine1.setMerchandiseCode("SG_Banana");
        contractLine2.setMerchandiseCode("SG_Mango");


        Contract contract1 = new Contract();
        contract1.setPurchaseType(PurchaseTypeEnum.TERMLICENSE);
        contract1.setSpendType("DummyType");
        contract1.setCode("Contract1");
        contract1.setContractStatus(ContractStatusEnum.ACTIVE);
        contract1.setContractType(ContractTypeEnum.SCHEDULE);
        contract1.setContractTerm("12 months");
        contract1.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-15"));
        contract1.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-15"));

        contract1.setCreatedBy("xuhong");
        contract1.setCreatedDateTime(new Date());
        contract1.setLastModifiedBy("xuhong");
        contract1.setLastModifiedDateTime(new Date());

        contract1 = contractRepository.save(contract1);

        banana.setCurrentContractCode("Contract1");
        mango.setCurrentContractCode("Contract1");

        contract1.getContractLines().add(contractLine1);
        contract1.getContractLines().add(contractLine2);
        contractLine1.setContract(contract1);
        contractLine2.setContract(contract1);
        contractLineRepository.saveAndFlush(contractLine1);
        contractLineRepository.saveAndFlush(contractLine2);

        Vendor vendor = vendorRepository.findVendorByCode("Vendor-Lenovo");
        Employee newEmployee = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong");

        contract1.setVendor(vendor);
        contract1.setEmployeeInChargeContract(newEmployee);
//        Hibernate.initialize(newEmployee.getContractInCharged());
        newEmployee.getContractInCharged().size();
        newEmployee.getContractInCharged().add(contract1);

        contractRepository.saveAndFlush(contract1);
        vendorRepository.saveAndFlush(vendor);
        merchandiseRepository.saveAndFlush(banana);
        merchandiseRepository.saveAndFlush(mango);
        employeeRepository.saveAndFlush(newEmployee);
    }

    public void createDispute() {
        Employee handler = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong");
        Employee creator = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong");

        Dispute dispute1 = new Dispute("This is a ... dispute.", DisputeStatusEnum.ACTIVE, handler, creator);
        dispute1.setCreatedBy("xuhong");
        dispute1.setCreatedDateTime(new Date());
        dispute1.setLastModifiedBy("xuhong");
        dispute1.setLastModifiedDateTime(new Date());
        disputeRepository.save(dispute1);

        handler.getDisputesHandling().add(dispute1);
        creator.getDisputesCreated().add(dispute1);
        employeeRepository.saveAndFlush(handler);
        employeeRepository.saveAndFlush(creator);
    }

    public void createAction() {
        Employee assignee = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong");
        Employee creator = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong");

        Action action = new Action("This is a ... action.", ActionStatusEnum.ACTIVE, assignee, creator);
        action.setCreatedBy("xuhong");
        action.setCreatedDateTime(new Date());
        action.setLastModifiedBy("xuhong");
        action.setLastModifiedDateTime(new Date());
        actionRepository.save(action);

        assignee.getActionsAssigned().add(action);
        creator.getActionsCreated().add(action);
        employeeRepository.saveAndFlush(assignee);
        employeeRepository.saveAndFlush(creator);
    }

    public void createOutsourcingAssessmentLine() {
        OutsourcingAssessmentLine outsourcingAssessmentLine = new OutsourcingAssessmentLine("Question1: Can we do it by ourselves?");
        outsourcingAssessmentLine.setCode("Outsourcing_Assessment_Line1");
        outsourcingAssessmentLine.setCreatedBy("xuhong");
        outsourcingAssessmentLine.setCreatedDateTime(new Date());
        outsourcingAssessmentLine.setLastModifiedBy("xuhong");
        outsourcingAssessmentLine.setLastModifiedDateTime(new Date());
        outsourcingAssessmentLineRepository.save(outsourcingAssessmentLine);
    }

    public void createOutsourcingAssessmentSection() {
        OutsourcingAssessmentLine outsourcingAssessmentLine = outsourcingAssessmentLineRepository.findOutsourcingAssessmentLineByCode("Outsourcing_Assessment_Line1").get(0);

        OutsourcingAssessmentSection outsourcingAssessmentSection = new OutsourcingAssessmentSection();
        outsourcingAssessmentSection.setCode("Outsourcing_Assessment_Section1");
        outsourcingAssessmentSection.getOutsourcingAssessmentLines().add(outsourcingAssessmentLine);
        outsourcingAssessmentSection.setCreatedBy("xuhong");
        outsourcingAssessmentSection.setCreatedDateTime(new Date());
        outsourcingAssessmentSection.setLastModifiedBy("xuhong");
        outsourcingAssessmentSection.setLastModifiedDateTime(new Date());
        outsourcingAssessmentSectionRepository.save(outsourcingAssessmentSection);

        outsourcingAssessmentLine.setOutsourcingAssessmentSection(outsourcingAssessmentSection);
        outsourcingAssessmentLineRepository.saveAndFlush(outsourcingAssessmentLine);
    }

    public void createOutsourcingAssessment() {
        OutsourcingAssessmentSection outsourcingAssessmentSection = outsourcingAssessmentSectionRepository.findOutsourcingAssessmentSectionByCode("Outsourcing_Assessment_Section1").get(0);
        Employee employeeAssess = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong");

        OutsourcingAssessment outsourcingAssessment = new OutsourcingAssessment();
        outsourcingAssessment.setCode("Outsourcing_Assessment1");
        outsourcingAssessment.setEmployeeAssess(employeeAssess);
        outsourcingAssessment.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.CREATED);
        outsourcingAssessment.setCreatedBy("xuhong");
        outsourcingAssessment.setCreatedDateTime(new Date());
        outsourcingAssessment.setLastModifiedBy("xuhong");
        outsourcingAssessment.setLastModifiedDateTime(new Date());
        outsourcingAssessmentRepository.save(outsourcingAssessment);

        employeeAssess.getOutsourcingAssessmentList().add(outsourcingAssessment);
        outsourcingAssessmentSection.setOutsourcingAssessment(outsourcingAssessment);
        employeeRepository.saveAndFlush(employeeAssess);
        outsourcingAssessmentSectionRepository.saveAndFlush(outsourcingAssessmentSection);
    }

    public void createOutsourcing() {
        OutsourcingAssessment outsourcingAssessment = outsourcingAssessmentRepository.findOutsourcingAssessmentByCode("Outsourcing_Assessment1").get(0);
        Employee employeeInCharge = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong");
        Vendor vendor = vendorRepository.findVendorByCode("Vendor-Lenovo");

        Outsourcing outsourcing = new Outsourcing();
        outsourcing.setOutsourcingDescription("This is a ... outsourcing.");
        outsourcing.setEmployeeInChargeOutsourcing(employeeInCharge);
        outsourcing.setOutsourcedVendor(vendor);
        outsourcing.getOutsourcingAssessmentList().add(outsourcingAssessment);
        outsourcing.setCreatedBy("xuhong");
        outsourcing.setCreatedDateTime(new Date());
        outsourcing.setLastModifiedBy("xuhong");
        outsourcing.setLastModifiedDateTime(new Date());
        outsourcingRepository.save(outsourcing);

        vendor.getOutsourcingList().add(outsourcing);
        employeeInCharge.getOutsourcingInCharged().add(outsourcing);
        outsourcingAssessment.setOutsourcing(outsourcing);
        vendorRepository.saveAndFlush(vendor);
        employeeRepository.saveAndFlush(employeeInCharge);
        outsourcingAssessmentRepository.saveAndFlush(outsourcingAssessment);
    }
}
