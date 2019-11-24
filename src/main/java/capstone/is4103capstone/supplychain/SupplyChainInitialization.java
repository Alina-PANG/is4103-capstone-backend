package capstone.is4103capstone.supplychain;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.finance.Service;
import capstone.is4103capstone.entities.supplyChain.*;
import capstone.is4103capstone.finance.Repository.ServiceRepository;
import capstone.is4103capstone.supplychain.Repository.*;
import capstone.is4103capstone.supplychain.model.req.CreateVendorReq;
import capstone.is4103capstone.supplychain.service.VendorService;
import capstone.is4103capstone.util.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@org.springframework.stereotype.Service
@Transactional
public class SupplyChainInitialization {
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    ServiceRepository serviceRepository;
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
    VendorService vendorService;

    private final String opsUserId = "yingshi2502";

    public void init(){
        try{
            if (vendorRepository.findAll().isEmpty()){
                createVendors();
            }
            if (contractRepository.findAll().isEmpty()){
                createContracts();
            }
        }catch (Exception ex){

        }
    }

    private void createVendors() throws Exception {
        String[] vendorNames = {"CISCO","Challenger","Apple","Google","Singtel"};
        String[] managedByBU = {"BU-SG-InfraTech","BU-SG-ADMIN","BU-SG-ADMIN","BU-SG-InfraTech","BU-SG-InfraTech"};

        for (int i=0;i<vendorNames.length;i++){
            CreateVendorReq req = new CreateVendorReq(vendorNames[i],managedByBU[i]);
            vendorService.createNewVendor(req);
        }
    }

    private void createContracts() throws Exception{

    }

}
