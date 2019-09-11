package capstone.is4103capstone.supplychain;

import capstone.is4103capstone.entities.Vendor;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SupplyChainInitialization {
    @Autowired
    VendorRepository vendorRepository;

    @PostConstruct
    public void init(){
        createVendors();
    }

    public void createVendors(){
        Vendor vendor1 = new Vendor();
        vendor1.setBusinessUnit("Lenovo");
        vendor1.setServiceDescription("Lenovo will provide PC and harware to NatWest.");
        vendor1.setRelationshipManagerName("Manager1");
        vendor1.setRelationshipManagerEmail("Manager1@gamil.com");
        vendor1.setBillingContactName("BillStaff");
        vendor1.setBillingContactEmail("BillStaff@gmail.com");
        vendor1.setEscalationContactName("EscalationStaff");
        vendor1.setEscalationContactEmail("EscalationStaff@gmail.com");
        vendor1.setCreatedBy("admin");
        vendor1.setCode("Vendor-Lenovo");
        vendor1.setLastModifiedBy("admin");

        vendorRepository.save(vendor1);
    }
}
