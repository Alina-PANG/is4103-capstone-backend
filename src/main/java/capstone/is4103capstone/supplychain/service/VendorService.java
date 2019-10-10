package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.admin.repository.TeamRepository;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.supplyChain.Contract;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.general.Authentication;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.supplychain.model.ContractModel;
import capstone.is4103capstone.supplychain.model.VendorModel;
import capstone.is4103capstone.supplychain.model.req.AddBusinessUnitReq;
import capstone.is4103capstone.supplychain.model.req.CreateVendorReq;
import capstone.is4103capstone.supplychain.model.res.GetVendorsRes;
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
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ContractService contractService;

    public GeneralRes createNewVendor(CreateVendorReq createVendorReq){
        try{
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
            Authentication.configurePermissionMap(newVendor);

            newVendor = vendorRepository.saveAndFlush(newVendor);
            if(createVendorReq.getBusinessUnits() != null && createVendorReq.getBusinessUnits().size() != 0) {
                for (Team t : createVendorReq.getBusinessUnits()) {
                    newVendor.getBusinessUnits().add(t);
                    newVendor = vendorRepository.saveAndFlush(newVendor);
                    t.getVendors().add(newVendor);
                    teamRepository.saveAndFlush(t);
                }
            }

            if (newVendor.getSeqNo() == null) {
                newVendor.setSeqNo(new Long(vendorRepository.findAll().size()));
            }
            newVendor.setCode(SCMEntityCodeHPGeneration.getCode(vendorRepository,newVendor));
            vendorRepository.saveAndFlush(newVendor);
            logger.info("Successfully created a new vendor! -- "+createVendorReq.getUsername()+" "+new Date());
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
                VendorModel vendorModel = transformToGeneralEntityModel(vendor);
                return new GetVendorRes("Successfully retrieved the vendor with id " + id, false, vendorModel);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetVendorRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetVendorsRes getAllVendors(){
        try {
            logger.info("Getting all vendors");
            List<VendorModel> returnList = new ArrayList<>();
            List<Vendor> vendorList = vendorRepository.findAll();

            for(Vendor vendor: vendorList){
                if(vendor.getDeleted()){
                    continue;
                }

                VendorModel vendorModel = transformToGeneralEntityModel(vendor);
                returnList.add(vendorModel);
            }

            if(returnList.size() == 0){
                throw new Exception("No vendor available.");
            }

            return new GetVendorsRes("Successfully retrieved all vendors", false, returnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetVendorsRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GeneralRes updateVendor(CreateVendorReq updateVendorReq, String id){
        try{
            Vendor vendor = vendorRepository.getOne(id);
            if (updateVendorReq.getVendorName() != null) {
                vendor.setObjectName(updateVendorReq.getVendorName());
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
            vendorRepository.saveAndFlush(vendor);
            return new GeneralRes("Successfully updated the vendor!", false);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

    public GeneralRes addBusinessUnit(AddBusinessUnitReq addBusinessUnitReq, String vendorId){
        try{
            Vendor vendor = vendorRepository.getOne(vendorId);
            Team team = teamRepository.getOne(addBusinessUnitReq.getTeamId());

            if(vendor.getDeleted() || team.getDeleted()){
                return new GetVendorRes("This vendor or team is deleted.", true, null);
            }

            vendor.getBusinessUnits().add(team);
            vendor.setLastModifiedBy(addBusinessUnitReq.getUsername());

            vendor = vendorRepository.saveAndFlush(vendor);
            team.getVendors().add(vendor);
            team.setLastModifiedBy(addBusinessUnitReq.getUsername());
            teamRepository.saveAndFlush(team);

            return new GeneralRes("Successfully add the business unit to vendor!", false);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

    private VendorModel transformToGeneralEntityModel(Vendor vendor){
        List<GeneralEntityModel> businessUnits = new ArrayList<>();
        for(Team t: vendor.getBusinessUnits()){
            GeneralEntityModel team = new GeneralEntityModel(t);
            businessUnits.add(team);
        }

        VendorModel vendorModel = new VendorModel(
                vendor.getId(), vendor.getCode(), vendor.getObjectName(), vendor.getSeqNo(), businessUnits,
                vendor.getServiceDescription(), vendor.getRelationshipManagerName(), vendor.getRelationshipManagerEmail(),
                vendor.getBillingContactName(), vendor.getBillingContactEmail(),
                vendor.getEscalationContactName(), vendor.getEscalationContactEmail());

        return vendorModel;
    }

//    public GeneralRes removeBusinessUnit(){
//
//    }

}
