package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.CountryDto;
import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.admin.repository.RegionRepository;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    RegionRepository regionRepository;

    // ===== START COUNTRY METHODS =====
    // === CREATE ===
    public Country createCountryEntity(Country countryReq) {
        Country newCountry = new Country();
        newCountry.setObjectName(countryReq.getObjectName());
        newCountry.setCode(countryReq.getCode());
        newCountry.setRegion(countryReq.getRegion());
        return countryRepository.save(newCountry);
    }

    public CountryDto createCountry(CountryDto input) {
        Country toCreate = dtoToEntity(input);
        return entityToDto(createCountryEntity(toCreate));
    }

    // === RETRIEVE ===
    public List<Country> getAllCountryEntities() {
        return countryRepository.findAll();
    }

    public List<CountryDto> getAllCountries() {
        List<CountryDto> countryDtos = new ArrayList<>();
        for (Country country : getAllCountryEntities()) {
            if (!country.getDeleted()) countryDtos.add(entityToDto(country));
        }
        return countryDtos;
    }

    public Country getCountryEntityByUuid(String uuid) throws DbObjectNotFoundException {
        try {
            return countryRepository.getOne(uuid);
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Country with UUID " + uuid + " not found!");
        }
    }

    public CountryDto getCountryByUuid(String uuid) {
        return entityToDto(getCountryEntityByUuid(uuid));
    }

    // === UPDATE ===
    @Transactional
    public Country updateCountryEntity(Country country) throws DbObjectNotFoundException {
        try {
            Country workingCountry = countryRepository.getOne(country.getId());
            workingCountry.setCode(country.getCode());
            workingCountry.setRegion(country.getRegion());
            workingCountry.setObjectName(country.getObjectName());
            return workingCountry;
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Country with UUID " + country.getId() + " not found!");
        }
    }

    @Transactional
    public CountryDto updateCountry(CountryDto input) {
        Country country = countryRepository.getOne(input.getId().orElseThrow(() -> new NullPointerException("Empty UUID provided!")));
        input.getRegionId().ifPresent(value -> country.setRegion(regionRepository.getOne(value)));
        input.getObjectName().ifPresent(value -> country.setObjectName(value));
        return entityToDto(country);
    }

    // === DELETE ===
    @Transactional
    public boolean deleteCountry(String uuid) throws DbObjectNotFoundException {
        Optional<Country> country = countryRepository.findById(uuid);
        if (!country.isPresent()) throw new DbObjectNotFoundException("Country with UUID " + uuid + " not found!");
        country.get().setDeleted(true);
        return true;
    }
    // ===== END COUNTRY METHODS =====

    // ===== COUNTRY TO DTO CONVERTER METHODS =====
    public Country dtoToEntity(CountryDto input) {
        Country country = new Country();
        input.getId().ifPresent(id -> country.setId(id));
        input.getObjectName().ifPresent(name -> country.setObjectName(name));
        input.getRegionId().ifPresent(regionId -> country.setRegion(regionRepository.getOne(regionId)));
        return country;
    }

    public CountryDto entityToDto(Country input) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(Optional.of(input.getId()));
        countryDto.setCode(Optional.of(input.getCode()));
        countryDto.setObjectName(Optional.of(input.getObjectName()));
        if (!Objects.isNull(input.getRegion())) countryDto.setRegionId(Optional.of(input.getRegion().getId()));
        return countryDto;
    }
}
