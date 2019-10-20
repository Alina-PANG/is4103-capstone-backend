package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.admin.repository.FunctionRepository;
import capstone.is4103capstone.admin.repository.RegionRepository;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.finance.Service;
import capstone.is4103capstone.entities.supplyChain.Outsourcing;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.ServiceRepository;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.Repository.OutsourcingRepository;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.supplychain.model.OutsourcingModel;
import capstone.is4103capstone.supplychain.model.res.GetOutsourcingRes;
import capstone.is4103capstone.supplychain.model.res.GetOutsourcingsRes;
import org.springframework.security.core.Authentication;
import capstone.is4103capstone.supplychain.model.req.CreateOutsourcingReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class OutsourcingService {
    private static final Logger logger = LoggerFactory.getLogger(OutsourcingService.class);

    @Autowired
    OutsourcingRepository outsourcingRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    FunctionRepository functionRepository;
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    ServiceRepository serviceRepository;

    public GeneralRes createOutsourcing(CreateOutsourcingReq createOutsourcingReq){
        try{
            Outsourcing outsourcing = new Outsourcing();
            outsourcing.setOutsourcingTitle(createOutsourcingReq.getOutsourcingTitle());
            outsourcing.setOutsourcingType(createOutsourcingReq.getOutsourcingType());
            outsourcing.setOutsourcingCategory(createOutsourcingReq.getOutsourcingCategory());
            outsourcing.setMateriality(createOutsourcingReq.getMateriality());
            outsourcing.setDueDiligenceDate(createOutsourcingReq.getDueDiligenceDate());
            outsourcing.setBcpTestDate(createOutsourcingReq.getBcpTestDate());
            outsourcing.setIndependentAuditDate(createOutsourcingReq.getIndependentAuditDate());
            outsourcing.setMaterialityAssessmentDate(createOutsourcingReq.getMaterialityAssessmentDate());
            outsourcing.setAnnualSelfAssessmentDate(createOutsourcingReq.getAnnualSelfAssessmentDate());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Employee currentEmployee = (Employee) auth.getPrincipal();
            outsourcing.setLastModifiedBy(currentEmployee.getUserName());
            outsourcing.setCreatedBy(currentEmployee.getUserName());

            Region region = regionRepository.getOne(createOutsourcingReq.getRegionId());
            outsourcing.setRegion(region);

            Country country = countryRepository.getOne(createOutsourcingReq.getCountryId());
            outsourcing.setCountry(country);

            CompanyFunction department = functionRepository.getOne(createOutsourcingReq.getDepartmentId());
            outsourcing.setDepartment(department);

            Vendor vendor = vendorRepository.getOne(createOutsourcingReq.getVendorId());
            outsourcing.setOutsourcedVendor(vendor);
            vendor.getOutsourcingList().add(outsourcing);

            for(String serviceId : createOutsourcingReq.getServiceIdList()){
                Service service = serviceRepository.getOne(serviceId);
                outsourcing.getServiceList().add(service);
            }

            outsourcing = outsourcingRepository.saveAndFlush(outsourcing);
            if(outsourcing.getSeqNo() == null){
                outsourcing.setSeqNo(new Long(outsourcingRepository.findAll().size()));
            }

            AuthenticationTools.configurePermissionMap(outsourcing);

            outsourcing = outsourcingRepository.saveAndFlush(outsourcing);
            outsourcing.setCode(SCMEntityCodeHPGeneration.getCode(outsourcingRepository,outsourcing));
            outsourcingRepository.saveAndFlush(outsourcing);
            vendorRepository.saveAndFlush(vendor);

            logger.info("Successfully created new outsourcing! -- " + outsourcing.getId());
            return new GeneralRes("Successfully created new outsourcing!", false);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: "+ex.getMessage(), true);
        }
    }

    public GetOutsourcingRes getOutsourcing (String id){
        try{
            logger.info("Getting outsourcing by outsourcing id: " + id);
            Outsourcing outsourcing = outsourcingRepository.getOne(id);

            if(outsourcing == null){
                return new GetOutsourcingRes("There is no outsourcing in the database with id " + id, true, null);
            }
            else if(outsourcing.getDeleted()){
                return new GetOutsourcingRes("This outsourcing is deleted", true, null);
            }
            else{
                OutsourcingModel outsourcingModel = transformToOutsourcingModel(outsourcing);
                return new GetOutsourcingRes("Successfully retrieved the outsourcing with id " + id, false, outsourcingModel);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetOutsourcingRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GetOutsourcingsRes getAllOutsourcings(){
        try {
            logger.info("Getting all outsourcings");
            List<OutsourcingModel> returnList = new ArrayList<>();
            List<Outsourcing> outsourcingList = outsourcingRepository.findAll();

            for(Outsourcing outsourcing: outsourcingList){
                if(outsourcing.getDeleted()){
                    continue;
                }

                OutsourcingModel outsourcingModel = transformToOutsourcingModel(outsourcing);
                returnList.add(outsourcingModel);
            }

            if(returnList.size() == 0){
                throw new Exception("No outsourcing available.");
            }

            return new GetOutsourcingsRes("Successfully retrieved all outsourcings", false, returnList);
        }catch(Exception ex){
            ex.printStackTrace();
            return new GetOutsourcingsRes("An unexpected error happens: "+ex.getMessage(), true, null);
        }
    }

    public GeneralRes updateOutsourcing(CreateOutsourcingReq updateOutsourcingReq, String id) {
        try {
            Outsourcing outsourcing = outsourcingRepository.getOne(id);
            if(updateOutsourcingReq.getOutsourcingTitle() != null){
                outsourcing.setOutsourcingTitle(updateOutsourcingReq.getOutsourcingTitle());
            }
            if(updateOutsourcingReq.getOutsourcingType() != null){
                outsourcing.setOutsourcingType(updateOutsourcingReq.getOutsourcingType());
            }
            if(updateOutsourcingReq.getOutsourcingCategory() != null){
                outsourcing.setOutsourcingCategory(updateOutsourcingReq.getOutsourcingCategory());
            }
            if(updateOutsourcingReq.getMateriality() != null){
                outsourcing.setMateriality(updateOutsourcingReq.getMateriality());
            }
            if(updateOutsourcingReq.getDueDiligenceDate() != null){
                outsourcing.setDueDiligenceDate(updateOutsourcingReq.getDueDiligenceDate());
            }
            if(updateOutsourcingReq.getBcpTestDate() != null){
                outsourcing.setBcpTestDate(updateOutsourcingReq.getBcpTestDate());
            }
            if(updateOutsourcingReq.getAnnualSelfAssessmentDate() != null){
                outsourcing.setAnnualSelfAssessmentDate(updateOutsourcingReq.getAnnualSelfAssessmentDate());
            }
            if(updateOutsourcingReq.getMaterialityAssessmentDate() != null){
                outsourcing.setMaterialityAssessmentDate(updateOutsourcingReq.getMaterialityAssessmentDate());
            }
            if(updateOutsourcingReq.getIndependentAuditDate() != null){
                outsourcing.setIndependentAuditDate(updateOutsourcingReq.getIndependentAuditDate());
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Employee currentEmployee = (Employee) auth.getPrincipal();
            outsourcing.setLastModifiedBy(currentEmployee.getUserName());

            if(updateOutsourcingReq.getRegionId() != null){
                Region region = regionRepository.getOne(updateOutsourcingReq.getRegionId());
                outsourcing.setRegion(region);
                outsourcing = outsourcingRepository.saveAndFlush(outsourcing);
            }

            if(updateOutsourcingReq.getCountryId() != null) {
                Country country = countryRepository.getOne(updateOutsourcingReq.getCountryId());
                outsourcing.setCountry(country);
                outsourcing = outsourcingRepository.saveAndFlush(outsourcing);
            }

            if(updateOutsourcingReq.getDepartmentId() != null) {
                CompanyFunction department = functionRepository.getOne(updateOutsourcingReq.getDepartmentId());
                outsourcing.setDepartment(department);
                outsourcing = outsourcingRepository.saveAndFlush(outsourcing);
            }

            if(updateOutsourcingReq.getVendorId() != null) {
                Vendor vendor = vendorRepository.getOne(updateOutsourcingReq.getVendorId());
                outsourcing.setOutsourcedVendor(vendor);
                vendor.getOutsourcingList().add(outsourcing);
                outsourcing = outsourcingRepository.saveAndFlush(outsourcing);
                vendorRepository.saveAndFlush(vendor);
            }

            if(updateOutsourcingReq.getServiceIdList() != null && updateOutsourcingReq.getServiceIdList().size() != 0) {
                for (String serviceId : updateOutsourcingReq.getServiceIdList()) {
                    Service service = serviceRepository.getOne(serviceId);
                    outsourcing.getServiceList().add(service);
                    outsourcingRepository.saveAndFlush(outsourcing);
                }
            }

            outsourcingRepository.saveAndFlush(outsourcing);
            return new GeneralRes("Successfully updated the outsourcing!", false);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: " + ex.getMessage(), true);
        }
    }

    public OutsourcingModel transformToOutsourcingModel(Outsourcing outsourcing){
        GeneralEntityModel region = null;
        GeneralEntityModel country = null;
        GeneralEntityModel department = null;
        List<GeneralEntityModel> serviceList = new ArrayList<>();
        GeneralEntityModel vendor = null;

        if(outsourcing.getRegion() != null){
            region = new GeneralEntityModel(outsourcing.getRegion());
        }
        if(outsourcing.getCountry() != null){
            country = new GeneralEntityModel(outsourcing.getCountry());
        }
        if(outsourcing.getDepartment() != null){
            department = new GeneralEntityModel(outsourcing.getDepartment());
        }
        if(outsourcing.getOutsourcedVendor() != null){
            vendor = new GeneralEntityModel(outsourcing.getOutsourcedVendor());
        }
        if(outsourcing.getServiceList() != null && outsourcing.getServiceList().size() != 0){
            for(Service s : outsourcing.getServiceList()){
                GeneralEntityModel serviceModel = new GeneralEntityModel(s);
                serviceList.add(serviceModel);
            }
        }

        OutsourcingModel outsourcingModel = new OutsourcingModel(outsourcing.getCode(), outsourcing.getId(), outsourcing.getSeqNo(),
                outsourcing.getOutsourcingTitle(), region, country, department,
                outsourcing.getOutsourcingType(), outsourcing.getOutsourcingCategory(), outsourcing.getMateriality(),
                serviceList, vendor, outsourcing.getDueDiligenceDate(),outsourcing.getMaterialityAssessmentDate(),
                outsourcing.getBcpTestDate(), outsourcing.getAnnualSelfAssessmentDate(), outsourcing.getIndependentAuditDate());

        return outsourcingModel;
    }
}
