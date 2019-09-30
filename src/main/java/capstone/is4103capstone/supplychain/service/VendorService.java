package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.model.VendorModel;
import capstone.is4103capstone.supplychain.model.req.CreateVendorReq;
import capstone.is4103capstone.supplychain.model.res.GetAllVendorsRes;
import capstone.is4103capstone.supplychain.model.res.GetVendorRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VendorService {
    private static final Logger logger = LoggerFactory.getLogger(VendorService.class);

    @Autowired
    private VendorRepository vendorRepository;

    public GeneralRes createNewVendor(CreateVendorReq createVendorReq){
        try{
            Vendor newVendor = new Vendor();
            newVendor.setObjectName(createVendorReq.getVendorName());
            newVendor.setBusinessUnit(createVendorReq.getBusinessUnit());
            newVendor.setServiceDescription(createVendorReq.getServiceDescription());
            newVendor.setBillingContactName(createVendorReq.getBillingContactName());
            newVendor.setBillingContactEmail(createVendorReq.getBillingContactEmail());
            newVendor.setEscalationContactName(createVendorReq.getEscalationContactName());
            newVendor.setEscalationContactEmail(createVendorReq.getEscalationContactEmail());
            newVendor.setRelationshipManagerName(createVendorReq.getRelationshipManagerName());
            newVendor.setRelationshipManagerEmail(createVendorReq.getRelationshipManagerEmail());
            newVendor.setCreatedBy(createVendorReq.getUsername());
            newVendor.setLastModifiedBy(createVendorReq.getUsername());
            newVendor.setLastModifiedDateTime(new Date());
            newVendor.setCreatedDateTime(new Date());

            newVendor = vendorRepository.saveAndFlush(newVendor);
            logger.info("Successully created a new vendor! -- "+createVendorReq.getUsername()+" "+new Date());
            return new GeneralRes("Successully created a new vendor!", false);
        }catch (Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

    public GetVendorRes getVendor(String id){
        try {
            logger.info("Getting vendor by vendor id: " + id);
            Vendor vendor = vendorRepository.getOne(id);

            if (vendor == null) {
                return new GetVendorRes("There is no vendor in the database with id " + id, true, null);
            }
            else if(vendor.getDeleted()){
                return new GetVendorRes("This vendor is deleted.", true, null);
            }
            else {
                VendorModel vendorModel = new VendorModel(
                        vendor.getId(), vendor.getCode(), vendor.getObjectName(), vendor.getBusinessUnit(),
                        vendor.getServiceDescription(), vendor.getRelationshipManagerName(), vendor.getRelationshipManagerEmail(),
                        vendor.getBillingContactName(), vendor.getBillingContactEmail(),
                        vendor.getEscalationContactName(), vendor.getEscalationContactEmail());
                return new GetVendorRes("Successfully retrieved the vendor with id " + id, false, vendorModel);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetVendorRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetAllVendorsRes getAllVendors(){
        try {
            logger.info("Getting all vendors");
            List<VendorModel> returnList = new ArrayList<>();
            List<Vendor> vendorList = vendorRepository.findAll();

            if(vendorList.size() == 0){
                throw new Exception("No vendor available.");
            }

            for(Vendor vendor: vendorList){
                if(vendor.getDeleted()){
                    continue;
                }

                VendorModel vendorModel = new VendorModel(
                        vendor.getId(), vendor.getCode(), vendor.getObjectName(), vendor.getBusinessUnit(),
                        vendor.getServiceDescription(), vendor.getRelationshipManagerName(), vendor.getRelationshipManagerEmail(),
                        vendor.getBillingContactName(), vendor.getBillingContactEmail(),
                        vendor.getEscalationContactName(), vendor.getEscalationContactEmail());

                returnList.add(vendorModel);
            }

            return new GetAllVendorsRes("Successfully retrieved all vendors", false, returnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetAllVendorsRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GeneralRes updateVendor(CreateVendorReq updateVendorReq, String id){
        try{
            Vendor vendor = vendorRepository.getOne(id);
            if (updateVendorReq.getVendorName() != null) {
                vendor.setObjectName(updateVendorReq.getVendorName());
            }
            if(updateVendorReq.getBusinessUnit() != null){
                vendor.setBusinessUnit(updateVendorReq.getBusinessUnit());
            }
            if(updateVendorReq.getBillingContactName() != null){
                vendor.setBillingContactName(updateVendorReq.getBillingContactName());
            }
            if(updateVendorReq.getBillingContactEmail() != null){
                vendor.setBillingContactEmail(updateVendorReq.getBillingContactEmail());
            }
            if(updateVendorReq.getEscalationContactName() != null){
                vendor.setEscalationContactName(updateVendorReq.getEscalationContactName());
            }
            if(updateVendorReq.getEscalationContactEmail() != null){
                vendor.setEscalationContactEmail(updateVendorReq.getEscalationContactEmail());
            }
            if(updateVendorReq.getRelationshipManagerName() != null){
                vendor.setRelationshipManagerName(updateVendorReq.getRelationshipManagerName());
            }
            if(updateVendorReq.getRelationshipManagerEmail() != null){
                vendor.setRelationshipManagerEmail(updateVendorReq.getRelationshipManagerEmail());
            }
            if(updateVendorReq.getServiceDescription() != null){
                vendor.setServiceDescription(updateVendorReq.getServiceDescription());
            }

            vendor.setLastModifiedBy(updateVendorReq.getUsername());
            vendor.setLastModifiedDateTime(new Date());
            vendorRepository.saveAndFlush(vendor);
            return new GeneralRes("Successfully updated the vendor!", false);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }
}
