package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.OfficeDto;
import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.helper.Address;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OfficeService {

    @Autowired
    OfficeRepository officeRepository;

    @Autowired
    CountryRepository countryRepository;

    // ===== CRUD METHODS =====
    // === CREATE ===
    public Office createNewOfficeEntity(Office input) {
        return officeRepository.save(input);
    }

    public OfficeDto createNewOffice(OfficeDto input) {
        return entityToDto(createNewOfficeEntity(dtoToEntity(input)));
    }

    // === RETRIEVE ===
    public List<Office> getAllOfficeEntities() {
        return officeRepository.findAll();
    }

    public List<OfficeDto> getAllOffices() {
        List<OfficeDto> OfficeDtos = new ArrayList<>();
        for (Office Office : getAllOfficeEntities()) {
            if (!Office.getDeleted()) OfficeDtos.add(entityToDto(Office));
        }
        return OfficeDtos;
    }

    public Office getOfficeEntityByUuid(String input) {
        try {
            return officeRepository.getOne(input);
        } catch (EntityNotFoundException ex) {
            throw new DbObjectNotFoundException("Office with UUID " + input + " not found!");
        }
    }

    public OfficeDto getOfficeByUuid(String input) {
        return entityToDto(getOfficeEntityByUuid(input));
    }

    // === UPDATE ===
    @Transactional
    public Office updateOfficeEntity(Office input) {
        Office working = officeRepository.getOne(input.getId());
        working.setObjectName(input.getObjectName());
        // non-standard attributes
        working.getAddress().setAddressLine1(input.getAddress().getAddressLine1());
        working.getAddress().setAddressLine2(input.getAddress().getAddressLine2());
        working.getAddress().setPostalCode(input.getAddress().getPostalCode());
        working.getAddress().setCity(input.getAddress().getCity());
        // set country-specific data
        try {
            Country country = countryRepository.getOne(input.getCountry().getId());
            working.getAddress().setCountryCode(country.getCode());
            working.setCountry(country);
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Country " + input.getCountry().getId() + " not found!");
        }
        return working;
    }

    @Transactional
    public OfficeDto updateOffice(OfficeDto input) {
        return entityToDto(updateOfficeEntity(dtoToEntity(input)));
    }

    // === DELETE ===
    @Transactional
    public boolean deleteOffice(String uuid) {
        try {
            officeRepository.getOne(uuid).setDeleted(true);
            return true;
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Office " + uuid + " not found!");
        }
    }

    // ===== ENTITY TO DTO CONVERTER METHODS =====
    public OfficeDto entityToDto(Office input) {
        OfficeDto officeDto = new OfficeDto();
        // standard attributes
        officeDto.setId(Optional.of(input.getId()));
        officeDto.setCode(input.getCode());
        officeDto.setObjectName(input.getObjectName());
        // non-standard
        officeDto.setAddressLine1(input.getAddress().getAddressLine1());
        officeDto.setAddressLine2(input.getAddress().getAddressLine2());
        officeDto.setPostalCode(input.getAddress().getPostalCode());
        officeDto.setCountryCode(input.getAddress().getCountryCode());
        officeDto.setCountryId(input.getCountry().getId());
        return officeDto;
    }

    public Office dtoToEntity(OfficeDto input) {
        Office office = new Office();
        // standard attributes
        input.getId().ifPresent(id -> office.setId(id));
        office.setCode(input.getCode());
        office.setObjectName(input.getObjectName());
        // non-standard attributes
        Address address = new Address();
        address.setAddressLine1(input.getAddressLine1());
        address.setAddressLine2(input.getAddressLine2());
        address.setPostalCode(input.getPostalCode());
        address.setCity(input.getCity());
        // set country-specific data
        try {
            Country country = countryRepository.getOne(input.getCountryId());
            address.setCountryCode(country.getCode());
            office.setCountry(country);
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Country " + input.getCountryId() + " not found!");
        }
        office.setAddress(address);
        return office;
    }

}
