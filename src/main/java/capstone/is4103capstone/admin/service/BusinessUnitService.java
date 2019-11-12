package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.BusinessUnitDto;
import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.entities.BusinessUnit;
import capstone.is4103capstone.general.StandardStatusMessages;
import capstone.is4103capstone.util.exception.BusinessUnitNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessUnitService {

    @Autowired
    BusinessUnitRepository businessUnitRepository;

    public BusinessUnit retrieveBusinessUnitById(String businessUnitId) throws BusinessUnitNotFoundException {
        Optional<BusinessUnit> optionalBusinessUnit = businessUnitRepository.findByIdNonDeleted(businessUnitId);
        if(!optionalBusinessUnit.isPresent()) {
            throw new BusinessUnitNotFoundException("Business unit with ID " + businessUnitId + " does not exist!");
        }
        return optionalBusinessUnit.get();
    }

    // GET ALL BUSINESS UNITS
    public List<BusinessUnitDto> getAllBusinessUnits(boolean suppressDeleted) throws Exception {
        List<BusinessUnit> businessUnits = businessUnitRepository.findAll();
        if (businessUnits.isEmpty()) throw new Exception(StandardStatusMessages.NO_RECORDS_IN_DB);
        List<BusinessUnitDto> businessUnitDtos = entityToDto(businessUnits, suppressDeleted);
        if (businessUnitDtos.isEmpty())
            throw new Exception(StandardStatusMessages.NO_NONDELETED_RECORDS_FOUND);
        return businessUnitDtos;
    }

    // GET BUSINESS UNIT BY UUID
    public BusinessUnitDto getBusinessUnitByUuid(String uuid) throws Exception {
        Optional<BusinessUnit> businessUnit = businessUnitRepository.findById(uuid);
        if (businessUnit.isPresent()) {
            return entityToDto(businessUnit.get());
        } else {
            throw new Exception(StandardStatusMessages.NO_SEARCH_RESULTS_FOUND);
        }
    }

    // GET BUSINESS UNITS BY COUNTRY UUID
    public List<BusinessUnitDto> getBusinessUnitsByCountryUuid(String countryUuid) throws Exception {
        List<BusinessUnit> businessUnits = businessUnitRepository.findBusinessUnitsByCountryUuid(countryUuid);
        return entityToDto(businessUnits, true);
    }

    // CONVERTERS
    // Entity to DTO conversion
    public BusinessUnitDto entityToDto(BusinessUnit input) {
        BusinessUnitDto businessUnitDto = new BusinessUnitDto();
        businessUnitDto.setId(Optional.ofNullable(input.getId()));
        businessUnitDto.setObjectName(Optional.ofNullable(input.getObjectName()));
        businessUnitDto.setCode(Optional.ofNullable(input.getCode()));
        businessUnitDto.setCompanyFunctionUuid(Optional.ofNullable(input.getFunction().getId()));
        return businessUnitDto;
    }

    public List<BusinessUnitDto> entityToDto(List<BusinessUnit> input, boolean suppressDeleted) {
        List<BusinessUnitDto> businessUnitDtos = new ArrayList<>();
        for (BusinessUnit businessUnit : input) {
            if (suppressDeleted) {
                if (!businessUnit.getDeleted()) businessUnitDtos.add(entityToDto(businessUnit));
            } else {
                businessUnitDtos.add(entityToDto(businessUnit));
            }
        }
        return businessUnitDtos;

    }

    // DTO to Entity Conversion
    public BusinessUnit dtoToEntity(BusinessUnitDto input) {
        BusinessUnit businessUnit = new BusinessUnit();
        input.getCode().ifPresent(businessUnit::setCode);
        input.getId().ifPresent(businessUnit::setId);
        input.getObjectName().ifPresent(businessUnit::setObjectName);
        return businessUnit;
    }

    public List<BusinessUnit> dtoToEntity(List<BusinessUnitDto> input) {
        List<BusinessUnit> businessUnits = new ArrayList<>();
        for (BusinessUnitDto businessUnitDto : input) {
            businessUnits.add(dtoToEntity(businessUnitDto));
        }
        return businessUnits;
    }

    public BusinessUnit validationBusinessUnit(String idOrCode) throws EntityNotFoundException {
        Optional<BusinessUnit> buOp = businessUnitRepository.findById(idOrCode);
        if (buOp.isPresent()){
            if (buOp.get().getDeleted())
                throw new EntityNotFoundException("Business Unit already removed");
            return buOp.get();
        }

        BusinessUnit e = businessUnitRepository.findByCode(idOrCode);
        if (e != null || !e.getDeleted())
            return e;

        throw new EntityNotFoundException("code or id not valid");
    }
}
