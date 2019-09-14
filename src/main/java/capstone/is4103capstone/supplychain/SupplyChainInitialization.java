package capstone.is4103capstone.supplychain;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.Merchandise;
import capstone.is4103capstone.entities.supplyChain.*;
import capstone.is4103capstone.finance.Repository.MerchandiseRepository;
import capstone.is4103capstone.supplychain.Repository.*;
import capstone.is4103capstone.util.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
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
    @Autowired
    ContractLineRepository contractLineRepository;

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
        Merchandise merchandise1 = new Merchandise("Banana", "SG_Banana", "dummy_hierachy", BigDecimal.valueOf(2.0), "SGD");
        merchandise1.setCreatedBy("Admin");
        merchandise1.setLastModifiedBy("admin");

        Merchandise merchandise2 = new Merchandise("Mango", "SG_Mango", "dummy_hierachy", BigDecimal.valueOf(5.0), "SGD");
        merchandise2.setCreatedBy("Admin");
        merchandise2.setLastModifiedBy("admin");
        merchandiseRepository.save(merchandise1);
        merchandiseRepository.save(merchandise2);
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
        ContractLine contractLine1 = new ContractLine("SG_Banana",BigDecimal.valueOf(2.0));
        ContractLine contractLine2 = new ContractLine("SG_Mango",BigDecimal.valueOf(5.0));
        contractLine1 = contractLineRepository.save(contractLine1);
        contractLine2 = contractLineRepository.save(contractLine2);

        Contract contract1 = new Contract();
        contract1.setPurchaseType(PurchaseTypeEnum.TERMLICENSE);
        contract1.setSpendType("DummyType");
        contract1.setCode("Contract1");
        contract1.setContractStatus(ContractStatusEnum.ACTIVE);
        contract1.setContractType(ContractTypeEnum.SCHEDULE);
        contract1.setContractTerm("9 months");
        contract1.setStartDate(new Date());
        contract1 = contractRepository.save(contract1);

        contract1.getContractLines().add(contractLine1);
        contract1.getContractLines().add(contractLine2);
        contractLine1.setContract(contract1);
        contractLine2.setContract(contract1);
        contractLineRepository.saveAndFlush(contractLine1);
        contractLineRepository.saveAndFlush(contractLine2);

        Merchandise merchandise = merchandiseRepository.findMerchandiseByCode("SG_Banana").get(0);
        Vendor vendor = vendorRepository.findVendorByCode("Vendor-Lenovo").get(0);
        Employee newEmployee = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong").get(0);

        contract1.setVendor(vendor);
        vendor.setContract(contract1);

        contract1.setEmployeeInChargeContract(newEmployee);
        newEmployee.addContractIC(contract1);

        merchandise.setActiveContractCode("Contract1");
//        newEmployee.getContractInCharged().size();
//        newEmployee.getContractInCharged().add(contract1);
        contractRepository.saveAndFlush(contract1);
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
        OutsourcingAssessmentLine outsourcingAssessmentLine = new OutsourcingAssessmentLine("Question1: Can we do it by ourselves?");
        outsourcingAssessmentLine.setCode("Outsourcing_Assessment_Line1");
        outsourcingAssessmentLineRepository.save(outsourcingAssessmentLine);
    }

    public void createOutsourcingAssessmentSection(){
        OutsourcingAssessmentLine outsourcingAssessmentLine = outsourcingAssessmentLineRepository.findOutsourcingAssessmentLineByCode("Outsourcing_Assessment_Line1").get(0);

        OutsourcingAssessmentSection outsourcingAssessmentSection = new OutsourcingAssessmentSection();
        outsourcingAssessmentSection.setCode("Outsourcing_Assessment_Section1");
        outsourcingAssessmentSection.getOutsourcingAssessmentLines().add(outsourcingAssessmentLine);
        outsourcingAssessmentSectionRepository.save(outsourcingAssessmentSection);

        outsourcingAssessmentLine.setOutsourcingAssessmentSection(outsourcingAssessmentSection);
        outsourcingAssessmentLineRepository.saveAndFlush(outsourcingAssessmentLine);
    }

    public void createOutsourcingAssessment(){
        OutsourcingAssessmentSection outsourcingAssessmentSection = outsourcingAssessmentSectionRepository.findOutsourcingAssessmentSectionByCode("Outsourcing_Assessment_Section1").get(0);
        Employee employeeAssess = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong").get(0);

        OutsourcingAssessment outsourcingAssessment = new OutsourcingAssessment();
        outsourcingAssessment.setCode("Outsourcing_Assessment1");
        outsourcingAssessment.setEmployeeAssess(employeeAssess);
        outsourcingAssessment.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.CREATED);
        outsourcingAssessmentRepository.save(outsourcingAssessment);

        employeeAssess.getOutsourcingAssessmentList().add(outsourcingAssessment);
        outsourcingAssessmentSection.setOutsourcingAssessment(outsourcingAssessment);
        employeeRepository.saveAndFlush(employeeAssess);
        outsourcingAssessmentSectionRepository.saveAndFlush(outsourcingAssessmentSection);
    }

    public void createOutsourcing(){
        OutsourcingAssessment outsourcingAssessment = outsourcingAssessmentRepository.findOutsourcingAssessmentByCode("Outsourcing_Assessment1").get(0);
        Employee employeeInCharge = employeeRepository.findEmployeeByCode("EMPLOYEE-xuhong").get(0);
        Vendor vendor = vendorRepository.findVendorByCode("Vendor-Lenovo").get(0);

        Outsourcing outsourcing = new Outsourcing();
        outsourcing.setOutsourcingDescription("This is a ... outsourcing.");
        outsourcing.setEmployeeInChargeOutsourcing(employeeInCharge);
        outsourcing.setOutsourcedVendor(vendor);
        outsourcing.getOutsourcingAssessmentList().add(outsourcingAssessment);
        outsourcingRepository.save(outsourcing);

        vendor.getOutsourcingList().add(outsourcing);
        employeeInCharge.getOutsourcingInCharged().add(outsourcing);
        outsourcingAssessment.setOutsourcing(outsourcing);
        vendorRepository.saveAndFlush(vendor);
        employeeRepository.saveAndFlush(employeeInCharge);
        outsourcingAssessmentRepository.saveAndFlush(outsourcingAssessment);
    }
}
