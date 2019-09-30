package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.OfficeDto;
import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.helper.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OfficeService {

    @Autowired
    OfficeRepository officeRepository;

    @Autowired
    CountryService countryService;

    // ===== CRUD METHODS =====
    // === CREATE ===
    public Office createNewOfficeEntity(Office input) {
        return officeRepository.save(input);
    }

    public OfficeDto createNewOffice(OfficeDto input) throws Exception {
        return entityToDto(createNewOfficeEntity(dtoToEntity(input)));
    }

    // === RETRIEVE ===
    public List<Office> getAllOfficeEntities() throws Exception {
        List<Office> offices = officeRepository.findAll();
        if (offices.isEmpty()) throw new Exception("No offices in database!");
        return offices;
    }

    public List<OfficeDto> getAllOffices() throws Exception {
        List<OfficeDto> officeDtos = entityToDto(getAllOfficeEntities(), true);
        if (officeDtos.isEmpty()) throw new Exception("No offices with non-deleted status to display!");
        return officeDtos;
    }

    public Office getOfficeEntityByUuid(String input) throws Exception {
        Optional<Office> office = officeRepository.findUndeletedOfficeById(input);
        if (office.isPresent()) {
            return office.get();
        } else {
            throw new Exception("Office with UUID " + input + " does not exist!");
        }
    }

    public OfficeDto getOfficeByUuid(String input) throws Exception {
        return entityToDto(getOfficeEntityByUuid(input));
    }

    // === UPDATE ===
    @Transactional
    public Office updateOfficeEntity(Office input) throws Exception {
        Office working = getOfficeEntityByUuid(input.getId());
        working.setObjectName(input.getObjectName());
        // non-standard attributes
        working.getAddress().setAddressLine1(input.getAddress().getAddressLine1());
        working.getAddress().setAddressLine2(input.getAddress().getAddressLine2());
        working.getAddress().setPostalCode(input.getAddress().getPostalCode());
        working.getAddress().setCity(input.getAddress().getCity());
        // set country-specific data
        Country country = countryService.getCountryEntityByUuid(input.getCountry().getId());
        working.getAddress().setCountryCode(country.getCode());
        working.setCountry(country);
        return working;
    }

    @Transactional
    public OfficeDto updateOffice(OfficeDto input) throws Exception {
        Office working = getOfficeEntityByUuid(input.getId().orElseThrow(() -> new NullPointerException("UUID is empty!")));
        if (working.getDeleted())
            throw new Exception("Office with UUID " + input.getId() + " has already been deleted and cannot be modified!");
        input.getObjectName().ifPresent(working::setObjectName);
        input.getAddressLine1().ifPresent(value -> working.getAddress().setAddressLine1(value));
        input.getAddressLine2().ifPresent(value -> working.getAddress().setAddressLine2(value));
        input.getPostalCode().ifPresent(value -> working.getAddress().setPostalCode(value));
        input.getCity().ifPresent(value -> working.getAddress().setCity(value));
        // set country-specific data
        if (input.getCountryId().isPresent()) {
            Country country = countryService.getCountryEntityByUuid(input.getCountryId().orElseThrow(() -> new NullPointerException("UUID is null!")));
            if (country.getDeleted())
                throw new Exception("Country with UUID " + input.getCountryId() + " has already been deleted and cannot be used!");
            working.getAddress().setCountryCode(country.getCode());
            working.setCountry(country);
        }
        return entityToDto(working);
    }

    // === DELETE ===
    @Transactional
    public boolean deleteOffice(String uuid) throws Exception {
        Office office = getOfficeEntityByUuid(uuid);
        if (office.getDeleted())
            throw new Exception("Office with UUID " + uuid + " has already been deleted and cannot be modified!");
        office.setDeleted(true);
        return true;
    }

    // ===== ENTITY TO DTO CONVERTER METHODS =====
    public OfficeDto entityToDto(Office input) {
        OfficeDto officeDto = new OfficeDto();
        // standard attributes
        officeDto.setId(Optional.of(input.getId()));
        officeDto.setCode(Optional.of(input.getCode()));
        officeDto.setObjectName(Optional.of(input.getObjectName()));
        // non-standard
        officeDto.setAddressLine1(Optional.of(input.getAddress().getAddressLine1()));
        officeDto.setAddressLine2(Optional.of(input.getAddress().getAddressLine2()));
        officeDto.setPostalCode(Optional.of(input.getAddress().getPostalCode()));
        officeDto.setCountryCode(Optional.of(input.getAddress().getCountryCode()));
        officeDto.setCountryId(Optional.of(input.getCountry().getId()));
        return officeDto;
    }

    public List<OfficeDto> entityToDto(List<Office> offices, boolean suppressDeleted) {
        List<OfficeDto> officeDtos = new ArrayList<>();
        for (Office office : offices) {
            if (suppressDeleted) {
                if (!office.getDeleted()) officeDtos.add(entityToDto(office));
            } else {
                officeDtos.add(entityToDto(office));
            }
        }
        return officeDtos;
    }

    public Office dtoToEntity(OfficeDto input) throws Exception {
        Office office = new Office();
        // standard attributes
        input.getId().ifPresent(id -> office.setId(id));
        input.getCode().ifPresent(office::setCode);
        input.getObjectName().ifPresent(name -> office.setObjectName(name));
        // non-standard attributes
        Address address = new Address();
        input.getAddressLine1().ifPresent(value -> address.setAddressLine1(value));
        input.getAddressLine2().ifPresent(value -> address.setAddressLine2(value));
        input.getPostalCode().ifPresent(value -> address.setPostalCode(value));
        input.getCity().ifPresent(value -> address.setCity(value));
        // set country-specific data
        if (input.getCountryId().isPresent()) {
            Country country = countryService.getCountryEntityByUuid(input.getCountryId().get());
            address.setCountryCode(country.getCode());
            office.setCountry(country);
        }
        office.setAddress(address);
        return office;
    }

    public List<Office> dtoToEntity(List<OfficeDto> officeDtos) throws Exception {
        List<Office> offices = new ArrayList<>();
        for (OfficeDto officeDto : officeDtos) {
            offices.add(dtoToEntity(officeDto));
        }
        return offices;
    }
}
