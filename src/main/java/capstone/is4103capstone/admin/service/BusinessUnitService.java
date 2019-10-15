package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.BusinessUnitDto;
import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.entities.BusinessUnit;
import capstone.is4103capstone.general.StandardStatusMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessUnitService {

    @Autowired
    BusinessUnitRepository businessUnitRepository;

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
        businessUnitDto.setId(Optional.of(input.getId()));
        businessUnitDto.setObjectName(Optional.of(input.getObjectName()));
        businessUnitDto.setCode(Optional.of(input.getCode()));
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

}
