package capstone.is4103capstone.general.service;

import capstone.is4103capstone.admin.repository.*;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.finance.BJF;
import capstone.is4103capstone.finance.Repository.*;
import capstone.is4103capstone.finance.requestsMgmt.model.req.CreateBJFReq;
import capstone.is4103capstone.seat.repository.SeatAllocationRepository;
import capstone.is4103capstone.supplychain.Repository.ContractRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingAssessmentRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingRepository;
import capstone.is4103capstone.supplychain.Repository.OutsourcingSelfAssessmentRepository;
import capstone.is4103capstone.util.exception.EntityClassNameNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class EntityMappingService {

    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    FunctionRepository companyFunctionRepo;
    @Autowired
    BusinessUnitRepository businessUnitRepo;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CostCenterRepository costCenterRepository;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    TravelFormRepository travelRepo;
    @Autowired
    TrainingFormRepository trainingFormRepo;
    @Autowired
    ProjectRepository projectRepo;
    @Autowired
    BjfRepository bjfRepo;
    @Autowired
    OutsourcingRepository outsourcingRepo;
    @Autowired
    OutsourcingAssessmentRepository outsourcingAssessmentRepo;
    @Autowired
    SeatAllocationRepository seatAllocationRepo;
    @Autowired
    OutsourcingSelfAssessmentRepository outsourcingSelfAssessmentRepo;

    public DBEntityTemplate getEntityByClassNameAndId(String className, String id)
            throws EntityNotFoundException, EntityClassNameNotValidException {
        JpaRepository repo = getRepoByClassName(className);

        Optional<DBEntityTemplate> entity = repo.findById(id);
        if (entity.isPresent())
            return entity.get();
        throw new EntityNotFoundException("Id ["+id+"] not found in table ["+className+"].");
    }

    public JpaRepository getRepoByClassName(String className) throws EntityClassNameNotValidException{
        switch (className.toLowerCase()){
            case "region":
                return regionRepository;
            case "country":
                return countryRepository;
            case "function":
                return companyFunctionRepo;
            case "businessunit":
                return businessUnitRepo;
            case "team":
                return teamRepository;
            case "costcenter":
                return costCenterRepository;
            case "contract":
                return contractRepository;
            case "plan":
                return planRepository;
            case "travelform":
                return travelRepo;
            case "trainingform":
                return trainingFormRepo;
            case "project":
                return projectRepo;
            case "outsourcingassessment":
                return outsourcingAssessmentRepo;
            case "outsourcing":
                return outsourcingRepo;
            case "seatallocation":
                return seatAllocationRepo;
            case "outsourcingselfassessment":
                return outsourcingSelfAssessmentRepo;
            case "bjf":
                return bjfRepo;
            default:
                throw new EntityClassNameNotValidException("Class name ["+className+"]given input is not valid");
        }
    }
}
