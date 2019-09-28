package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.CountryDto;
import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.admin.repository.RegionRepository;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.Region;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    RegionRepository regionRepository;

    // ===== START COUNTRY METHODS =====
    // === CREATE ===
    public Country createCountryEntity(Country countryReq) throws Exception {
        Country newCountry = new Country();
        newCountry.setObjectName(countryReq.getObjectName());
        newCountry.setCode(countryReq.getCode());
        newCountry.setRegion(countryReq.getRegion());
        return countryRepository.save(newCountry);
    }

    public CountryDto createCountry(CountryDto input) throws Exception {
        Country toCreate = dtoToEntity(input);
        return entityToDto(createCountryEntity(toCreate));
    }

    // === RETRIEVE ===
    public List<Country> getAllCountryEntities() throws Exception {
        List<Country> results = countryRepository.findAll();
        if (results.isEmpty()) throw new Exception("There are no countries found in the database.");
        return results;
    }

    public List<CountryDto> getAllCountries() throws Exception {
        return entityToDto(getAllCountryEntities(), true);
    }

    public Country getCountryEntityByUuid(String uuid) throws Exception {
        try {
            Country result = countryRepository.findById(uuid).get();
            return result;
        } catch (NoSuchElementException ex) {
            throw new Exception("No country with UUID " + uuid + " found in the database.");
        }
    }

    public CountryDto getCountryByUuid(String uuid) throws Exception {
        return entityToDto(getCountryEntityByUuid(uuid));
    }

    public List<CountryDto> getCountryEntityByRegion(String uuid) throws Exception {
        List<Country> countries = countryRepository.findCountriesByRegionId(uuid);
        if (countries.isEmpty())
            throw new Exception("No countries with region UUID " + uuid + " found in the database!");
        return entityToDto(countries, true);
    }

    // === UPDATE ===
    @Transactional
    public Country updateCountryEntity(Country country) throws Exception {
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
    public CountryDto updateCountry(CountryDto input) throws Exception {
        Country country = getCountryEntityByUuid(input.getId().orElseThrow(() -> new Exception("Empty UUID provided!")));
        if (country.getDeleted())
            throw new Exception("Country with UUID " + country.getId() + " has deleted status and cannot be modified!");
        Country newObj = dtoToEntity(input);
        country.setObjectName(newObj.getObjectName());
        country.setRegion(newObj.getRegion());
        return entityToDto(country);
    }

    // === DELETE ===
    @Transactional
    public boolean deleteCountry(String uuid) throws Exception {
        Optional<Country> country = countryRepository.findById(uuid);
        if (!country.isPresent()) throw new Exception("Country with UUID " + uuid + " not found!");
        if (country.get().getDeleted()) throw new Exception("Country with UUID " + uuid + " has already been deleted!");
        country.ifPresent(result -> result.setDeleted(true));
        return true;
    }
    // ===== END COUNTRY METHODS =====

    // ===== COUNTRY TO DTO CONVERTER METHODS =====
    public Country dtoToEntity(CountryDto input) throws Exception {
        Country country = new Country();
        input.getId().ifPresent(country::setId);
        input.getCode().ifPresent(country::setCode);
        input.getObjectName().ifPresent(country::setObjectName);
        // test if region given is correct
        if (input.getRegionId().isPresent()) {
            Region region = regionRepository.findRegionById(input.getRegionId().get());
            if (Objects.isNull(region))
                throw new Exception("Region with UUID " + input.getRegionId().get() + " not found!");
            country.setRegion(region);
        }
        return country;
    }

    public List<Country> dtoToEntity(List<CountryDto> input) throws Exception {
        List<Country> countries = new ArrayList<>();
        for (CountryDto curr : input) {
            countries.add(dtoToEntity(curr));
        }
        return countries;
    }

    public CountryDto entityToDto(Country input) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(Optional.of(input.getId()));
        countryDto.setCode(Optional.of(input.getCode()));
        countryDto.setObjectName(Optional.of(input.getObjectName()));
        if (!Objects.isNull(input.getRegion())) countryDto.setRegionId(Optional.of(input.getRegion().getId()));
        return countryDto;
    }

    public List<CountryDto> entityToDto(List<Country> input, boolean suppressDeleted) {
        List<CountryDto> countryDtos = new ArrayList<>();
        for (Country curr : input) {
            if (suppressDeleted) {
                if (!curr.getDeleted()) countryDtos.add(entityToDto(curr));
            } else {
                countryDtos.add(entityToDto(curr));
            }
        }
        return countryDtos;
    }
}
