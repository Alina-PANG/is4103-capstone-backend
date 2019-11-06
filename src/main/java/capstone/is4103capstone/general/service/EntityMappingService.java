package capstone.is4103capstone.general.service;

import capstone.is4103capstone.admin.repository.*;
import capstone.is4103capstone.configuration.DBEntityTemplate;
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
            case "business unit":
                return businessUnitRepo;
            case "team":
                return teamRepository;
            case "costcenter":
                return costCenterRepository;
            default:
                throw new EntityClassNameNotValidException("Class name ["+className+"]given input is not valid");
        }

    }
}
