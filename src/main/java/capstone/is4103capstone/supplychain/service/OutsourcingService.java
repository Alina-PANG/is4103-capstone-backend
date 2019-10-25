package capstone.is4103capstone.supplychain.service;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.admin.repository.FunctionRepository;
import capstone.is4103capstone.admin.repository.RegionRepository;
import capstone.is4103capstone.admin.service.CompanyFunctionService;
import capstone.is4103capstone.admin.service.CountryService;
import capstone.is4103capstone.admin.service.RegionService;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.finance.Service;
import capstone.is4103capstone.entities.supplyChain.Outsourcing;
import capstone.is4103capstone.entities.supplyChain.OutsourcingAssessment;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.Repository.ServiceRepository;
import capstone.is4103capstone.finance.admin.service.ServiceServ;
import capstone.is4103capstone.finance.requestsMgmt.service.BJFService;
import capstone.is4103capstone.general.AuthenticationTools;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingRepository;
import capstone.is4103capstone.supplychain.Repository.VendorRepository;
import capstone.is4103capstone.supplychain.SCMEntityCodeHPGeneration;
import capstone.is4103capstone.supplychain.model.OutsourcingModel;
import capstone.is4103capstone.supplychain.model.res.GetOutsourcingRes;
import capstone.is4103capstone.supplychain.model.res.GetOutsourcingsRes;
import capstone.is4103capstone.supplychain.outsourcing.assessmentForm.service.AssessmentFormService;
import capstone.is4103capstone.util.enums.OutsourcingAssessmentStatusEnum;
import org.springframework.security.core.Authentication;
import capstone.is4103capstone.supplychain.model.req.CreateOutsourcingReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    RegionService regionService;
    @Autowired
    CountryService countryService;
    @Autowired
    CompanyFunctionService companyFunctionService;
    @Autowired
    ServiceServ serviceServ;
    @Autowired
    AssessmentFormService assessmentFormService;
    @Autowired
    OutsourcingAssessmentRepository outsourcingAssessmentRepository;
    @Autowired
    BJFService bjfService;


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

            if(regionService.validateRegionId(createOutsourcingReq.getRegionId())) {
                outsourcing.setRegionId(createOutsourcingReq.getRegionId());
            }else{
                throw new Exception("This is not a valid region ID.");
            }

            if(countryService.validateCountryId(createOutsourcingReq.getCountryId())) {
                outsourcing.setCountryId(createOutsourcingReq.getCountryId());
            }else{
                throw new Exception("This is not a valid country ID.");
            }

            if(companyFunctionService.validateFunctionId(createOutsourcingReq.getDepartmentId())) {
                outsourcing.setDepartmentId(createOutsourcingReq.getDepartmentId());
            }else{
                throw new Exception("This is not a valid department ID.");
            }

            if(assessmentFormService.validateAssessmentFormId(createOutsourcingReq.getOutsourcingAssessmentId())){
                OutsourcingAssessment outsourcingAssessment = outsourcingAssessmentRepository.getOne(createOutsourcingReq.getOutsourcingAssessmentId());
                if(outsourcingAssessment.getOutsourcingAssessmentStatus() != OutsourcingAssessmentStatusEnum.APPROVED){
                    throw new Exception("This is not a APPROVED outsourcing assessment form. You are not allowed to create new outsourcing based on this assessment form!");
                }else{
                    outsourcing.setOutsourcingAssessmentId(createOutsourcingReq.getOutsourcingAssessmentId());
                    outsourcingAssessment.setOutsourcingAssessmentStatus(OutsourcingAssessmentStatusEnum.OUTSOURCING_RECORD_CREATED);
                    outsourcingAssessmentRepository.saveAndFlush(outsourcingAssessment);
                    bjfService.afterOutsourcing(outsourcingAssessment);
                }
            }else{
                throw new Exception("This is not a valid outsourcing assessment ID.");
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Employee currentEmployee = (Employee) auth.getPrincipal();
            outsourcing.setLastModifiedBy(currentEmployee.getUserName());
            outsourcing.setCreatedBy(currentEmployee.getUserName());

            Vendor vendor = vendorRepository.getOne(createOutsourcingReq.getVendorId());
            outsourcing.setOutsourcedVendor(vendor);
            vendor.getOutsourcingList().add(outsourcing);

            for(String id : createOutsourcingReq.getServiceIdList()){
                if(serviceServ.validateServiceId(id)){
                    outsourcing.getServiceIdList().add(id);
                }else{
                    throw new Exception("This is not a valid service ID.");
                }
            }

            outsourcing = outsourcingRepository.save(outsourcing);
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
                OutsourcingModel outsourcingModel = transformToOutsourcingModelDetails(outsourcing);
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

                OutsourcingModel outsourcingModel = transformToOutsourcingModelOverview(outsourcing);
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

            if(updateOutsourcingReq.getRegionId() != null){
                if(regionService.validateRegionId(updateOutsourcingReq.getRegionId())) {
                    outsourcing.setRegionId(updateOutsourcingReq.getRegionId());
                }else{
                    throw new Exception("This is not a valid region ID.");
                }
            }

            if(updateOutsourcingReq.getCountryId() != null) {
                if(countryService.validateCountryId(updateOutsourcingReq.getCountryId())) {
                    outsourcing.setCountryId(updateOutsourcingReq.getCountryId());
                }else{
                    throw new Exception("This is not a valid country ID.");
                }
            }

            if(updateOutsourcingReq.getDepartmentId() != null) {
                if(companyFunctionService.validateFunctionId(updateOutsourcingReq.getDepartmentId())) {
                    outsourcing.setDepartmentId(updateOutsourcingReq.getDepartmentId());
                }else{
                    throw new Exception("This is not a valid department ID.");
                }
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Employee currentEmployee = (Employee) auth.getPrincipal();
            outsourcing.setLastModifiedBy(currentEmployee.getUserName());


            if(updateOutsourcingReq.getServiceIdList() != null && updateOutsourcingReq.getServiceIdList().size() != 0) {
                outsourcing.setServiceIdList(new ArrayList<>());
                for(String servId : updateOutsourcingReq.getServiceIdList()){
                    if(serviceServ.validateServiceId(servId)){
                        outsourcing.getServiceIdList().add(servId);
                    }else{
                        throw new Exception("This is not a valid service ID.");
                    }
                }
            }

            if(updateOutsourcingReq.getVendorId() != null) {
                Vendor vendor = vendorRepository.getOne(updateOutsourcingReq.getVendorId());
                outsourcing.setOutsourcedVendor(vendor);
                vendor.getOutsourcingList().add(outsourcing);
                vendorRepository.saveAndFlush(vendor);
            }

            outsourcingRepository.saveAndFlush(outsourcing);
            return new GeneralRes("Successfully updated the outsourcing!", false);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new GeneralRes("An unexpected error happens: " + ex.getMessage(), true);
        }
    }

    public OutsourcingModel transformToOutsourcingModelDetails(Outsourcing outsourcing){
        GeneralEntityModel region = null;
        GeneralEntityModel country = null;
        GeneralEntityModel department = null;
        List<GeneralEntityModel> serviceList = new ArrayList<>();
        GeneralEntityModel vendor = null;
        GeneralEntityModel outsouricngAssessment = null;

        if(outsourcing.getRegionId() != null){
            Region r = regionRepository.getOne(outsourcing.getRegionId());
            region = new GeneralEntityModel(r);
        }
        if(outsourcing.getCountryId() != null){
            Country c = countryRepository.getOne(outsourcing.getCountryId());
            country = new GeneralEntityModel(c);
        }
        if(outsourcing.getDepartmentId() != null){
            CompanyFunction d = functionRepository.getOne(outsourcing.getDepartmentId());
            department = new GeneralEntityModel(d);
        }
        if(outsourcing.getOutsourcedVendor() != null){
            vendor = new GeneralEntityModel(outsourcing.getOutsourcedVendor());
        }
        if(outsourcing.getServiceIdList() != null && outsourcing.getServiceIdList().size() != 0){
            for(String id : outsourcing.getServiceIdList()){
                Service service = serviceRepository.getOne(id);
                GeneralEntityModel serviceModel = new GeneralEntityModel(service);
                serviceList.add(serviceModel);
            }
        }
        if(outsourcing.getOutsourcingAssessmentId() != null){
            OutsourcingAssessment assessment = outsourcingAssessmentRepository.getOne(outsourcing.getOutsourcingAssessmentId());
            outsouricngAssessment = new GeneralEntityModel(assessment);
        }

        OutsourcingModel outsourcingModel = new OutsourcingModel(outsourcing.getCode(), outsourcing.getId(), outsourcing.getSeqNo(),
                outsourcing.getOutsourcingTitle(), region, country, department,
                outsourcing.getOutsourcingType(), outsourcing.getOutsourcingCategory(), outsourcing.getMateriality(),
                serviceList, vendor, outsouricngAssessment, outsourcing.getDueDiligenceDate(),outsourcing.getMaterialityAssessmentDate(),
                outsourcing.getBcpTestDate(), outsourcing.getAnnualSelfAssessmentDate(), outsourcing.getIndependentAuditDate());

        return outsourcingModel;
    }

    //faster to load overview info
    public OutsourcingModel transformToOutsourcingModelOverview(Outsourcing outsourcing){
        GeneralEntityModel region = null;
        GeneralEntityModel country = null;
        GeneralEntityModel department = null;
        List<GeneralEntityModel> serviceList = new ArrayList<>();
        GeneralEntityModel vendor = null;
        GeneralEntityModel outsourcingAssessment = null;

        if(outsourcing.getRegionId() != null){
            Region r = regionRepository.getOne(outsourcing.getRegionId());
            region = new GeneralEntityModel(r);
        }
        if(outsourcing.getDepartmentId() != null){
            CompanyFunction d = functionRepository.getOne(outsourcing.getDepartmentId());
            department = new GeneralEntityModel(d);
        }
        if(outsourcing.getCountryId() != null){
            Country c = countryRepository.getOne(outsourcing.getCountryId());
            country = new GeneralEntityModel(c);
        }

        OutsourcingModel outsourcingModel = new OutsourcingModel(outsourcing.getCode(), outsourcing.getId(), outsourcing.getSeqNo(),
                outsourcing.getOutsourcingTitle(), region, country, department,
                outsourcing.getOutsourcingType(), outsourcing.getOutsourcingCategory(), outsourcing.getMateriality(),
                serviceList, vendor, outsourcingAssessment, outsourcing.getDueDiligenceDate(),outsourcing.getMaterialityAssessmentDate(),
                outsourcing.getBcpTestDate(), outsourcing.getAnnualSelfAssessmentDate(), outsourcing.getIndependentAuditDate());

        return outsourcingModel;
    }


    public Boolean validateOutsourcingId(String id){
        Optional<Outsourcing> outsourcing = outsourcingRepository.findUndeletedOutsourcingById(id);
        if (!outsourcing.isPresent()) {
            return false;
        }else{
            return true;
        }
    }

    public Outsourcing getOutsourcingRecordByServiceAndVendor(String serviceId, String vendorId) throws EntityNotFoundException {
        Optional<Outsourcing> outsourcing = outsourcingRepository.findOutsourcingRecordByServiceAndVendor(serviceId,vendorId);
        if (!outsourcing.isPresent())
            throw new EntityNotFoundException("Outsourcing with serivce "+serviceId+" under vendor "+vendorId+" is not found");
        return outsourcing.get();
    }
}
