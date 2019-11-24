package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.admin.service.BusinessUnitService;
import capstone.is4103capstone.entities.BusinessUnit;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.general.model.GeneralEntityRes;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.supplychain.model.VendorModel;
import capstone.is4103capstone.supplychain.model.req.CreateVendorReq;
import capstone.is4103capstone.supplychain.model.res.GetVendorsRes;
import capstone.is4103capstone.supplychain.model.res.GetVendorRes;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class VendorService {
    private static final Logger logger = LoggerFactory.getLogger(VendorService.class);

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private BusinessUnitService businessUnitService;

    public VendorService() {
    }

    public GeneralRes createNewVendor(CreateVendorReq createVendorReq) throws Exception {

        Vendor newVendor = new Vendor();
        newVendor.setObjectName(createVendorReq.getVendorName());
        newVendor.setServiceDescription(createVendorReq.getServiceDescription());
        newVendor.setBillingContactName(createVendorReq.getBillingContactName());
        newVendor.setBillingContactEmail(createVendorReq.getBillingContactEmail());
        newVendor.setEscalationContactName(createVendorReq.getEscalationContactName());
        newVendor.setEscalationContactEmail(createVendorReq.getEscalationContactEmail());
        newVendor.setRelationshipManagerName(createVendorReq.getRelationshipManagerName());
        newVendor.setRelationshipManagerEmail(createVendorReq.getRelationshipManagerEmail());
        newVendor.setCreatedBy(createVendorReq.getUsername());
        newVendor.setLastModifiedBy(createVendorReq.getUsername());

        List<String> buIds = new ArrayList<>();
        for (String buIdOrCode : createVendorReq.getBusinessUnitIds()) {
            String id = businessUnitService.validationBusinessUnit(buIdOrCode).getId();
            buIds.add(id);
        }
        newVendor.setBusinessUnits(buIds);
        AuthenticationTools.configurePermissionMap(newVendor);

        newVendor = vendorRepository.saveAndFlush(newVendor);
        if (newVendor.getSeqNo() == null) {
            newVendor.setSeqNo((long) vendorRepository.findAll().size());
        }
        newVendor.setCode(SCMEntityCodeHPGeneration.getCode(vendorRepository, newVendor));
        vendorRepository.saveAndFlush(newVendor);
        logger.info("Successfully created a new vendor! -- " + createVendorReq.getUsername() + " " + new Date());
        return new GeneralEntityRes("Successully created a new vendor!", false,new GeneralEntityModel(newVendor));
    }

    public GetVendorRes getVendor(String id) {
        try {
            logger.info("Getting vendor by vendor id: " + id);
            Vendor vendor = vendorRepository.getOne(id);

            if (vendor == null) {
                return new GetVendorRes("There is no vendor in the database with id " + id, true, null);
            } else if (vendor.getDeleted()) {
                return new GetVendorRes("This vendor is deleted.", true, null);
            } else {
                VendorModel vendorModel = transformToGeneralEntityModelDetails(vendor);
                return new GetVendorRes("Successfully retrieved the vendor with id " + id, false, vendorModel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GetVendorRes( ex.getMessage(), true, null);
        }
    }

    public GetVendorsRes getAllVendors() {
        try {
            logger.info("Getting all vendors");
            List<VendorModel> returnList = new ArrayList<>();
            List<Vendor> vendorList = vendorRepository.findAll();

            for (Vendor vendor : vendorList) {
                if (vendor.getDeleted()) {
                    continue;
                }

                VendorModel vendorModel = transformToGeneralEntityModelOverview(vendor);
                returnList.add(vendorModel);
            }

            if (returnList.size() == 0) {
                throw new Exception("No vendor available.");
            }

            return new GetVendorsRes("Successfully retrieved all vendors", false, returnList);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GetVendorsRes( ex.getMessage(), true, null);
        }
    }

    public GeneralRes updateVendor(CreateVendorReq updateVendorReq, String id) {
        try {
            Vendor vendor = vendorRepository.getOne(id);
            if (updateVendorReq.getVendorName() != null) {
                vendor.setObjectName(updateVendorReq.getVendorName());
            }
            if (updateVendorReq.getBillingContactName() != null) {
                vendor.setBillingContactName(updateVendorReq.getBillingContactName());
            }
            if (updateVendorReq.getBillingContactEmail() != null) {
                vendor.setBillingContactEmail(updateVendorReq.getBillingContactEmail());
            }
            if (updateVendorReq.getEscalationContactName() != null) {
                vendor.setEscalationContactName(updateVendorReq.getEscalationContactName());
            }
            if (updateVendorReq.getEscalationContactEmail() != null) {
                vendor.setEscalationContactEmail(updateVendorReq.getEscalationContactEmail());
            }
            if (updateVendorReq.getRelationshipManagerName() != null) {
                vendor.setRelationshipManagerName(updateVendorReq.getRelationshipManagerName());
            }
            if (updateVendorReq.getRelationshipManagerEmail() != null) {
                vendor.setRelationshipManagerEmail(updateVendorReq.getRelationshipManagerEmail());
            }
            if (updateVendorReq.getServiceDescription() != null) {
                vendor.setServiceDescription(updateVendorReq.getServiceDescription());
            }

            //update businessUnits
            if (updateVendorReq.getBusinessUnitIds() != null) {
                vendor.setBusinessUnits(updateVendorReq.getBusinessUnitIds());
            }

            vendor.setLastModifiedBy(updateVendorReq.getUsername());
            vendorRepository.saveAndFlush(vendor);
            return new GeneralRes("Successfully updated the vendor!", false);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes( ex.getMessage(), true);
        }
    }

    private VendorModel transformToGeneralEntityModelDetails(Vendor vendor) {
        List<GeneralEntityModel> businessUnits = new ArrayList<>();

        if (vendor.getBusinessUnits() != null) {
            for (String id : vendor.getBusinessUnits()) {
                BusinessUnit b = businessUnitService.retrieveBusinessUnitById(id);
                GeneralEntityModel businessUnit = new GeneralEntityModel(b);
                businessUnits.add(businessUnit);
            }
        }

        VendorModel vendorModel = new VendorModel(
                vendor.getId(), vendor.getCode(), vendor.getObjectName(), vendor.getSeqNo(), businessUnits,
                vendor.getServiceDescription(), vendor.getRelationshipManagerName(), vendor.getRelationshipManagerEmail(),
                vendor.getBillingContactName(), vendor.getBillingContactEmail(),
                vendor.getEscalationContactName(), vendor.getEscalationContactEmail());

        return vendorModel;
    }

    //faster to load overview information
    private VendorModel transformToGeneralEntityModelOverview(Vendor vendor) {
        List<GeneralEntityModel> businessUnits = new ArrayList<>();

        VendorModel vendorModel = new VendorModel(
                vendor.getId(), vendor.getCode(), vendor.getObjectName(), vendor.getSeqNo(), businessUnits,
                vendor.getServiceDescription(), vendor.getRelationshipManagerName(), vendor.getRelationshipManagerEmail(),
                vendor.getBillingContactName(), vendor.getBillingContactEmail(),
                vendor.getEscalationContactName(), vendor.getEscalationContactEmail());

        return vendorModel;
    }

    public Vendor validateVendor(String idOrUsername) throws EntityNotFoundException {
        Optional<Vendor> vendorOps = vendorRepository.findById(idOrUsername);
        if (vendorOps.isPresent()) {
            if (vendorOps.get().getDeleted())
                throw new EntityNotFoundException("vendor already removed");
            return vendorOps.get();
        }

        Vendor e = vendorRepository.findVendorByCode(idOrUsername);
        if (e != null || !e.getDeleted())
            return e;

        throw new EntityNotFoundException("username or id not valid");
    }
}
