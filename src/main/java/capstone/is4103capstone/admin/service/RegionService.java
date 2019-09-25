package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.RegionDto;
import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.admin.repository.RegionRepository;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.Region;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RegionService {

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    CountryRepository countryRepository;

    // ===== START REGION METHODS =====
    // === CREATE ===
    public Region createRegionByEntity(Region input) {
        Region toPersist = new Region();
        toPersist.setObjectName(input.getObjectName());
        toPersist.setCode(input.getCode());
        return regionRepository.save(toPersist);
    }

    public RegionDto createRegion(RegionDto input) {
        Region toCreate = convertDtoToRegion(input);
        return convertRegionToDto(createRegionByEntity(toCreate));
    }

    // === RETRIEVE ===
    public List<Region> getAllRegionEntities() {
        List<Region> regionList = regionRepository.findAll();
        if (regionList.isEmpty()) throw new DbObjectNotFoundException("No regions found.");
        return regionList;
    }

    public List<RegionDto> getAllRegions() {
        List<Region> regionList = getAllRegionEntities();
        List<RegionDto> regionDtos = new ArrayList<>();
        for (Region region : regionList) {
            if (!region.getDeleted()) regionDtos.add(convertRegionToDto(region));
        }
        return regionDtos;
    }

    public Region getRegionEntityByUuid(String uuid) {
        try {
            Region region = regionRepository.getOne(uuid);
            return region;
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Region with UUID " + uuid + " was not found.");
        }
    }

    public RegionDto getRegionByUuid(String uuid) {
        return convertRegionToDto(getRegionEntityByUuid(uuid));
    }

    // === UPDATE ===
    @Transactional
    public Region updateRegionEntity(Region regionToChange) throws EntityNotFoundException {
        Region workingRegion = regionRepository.getOne(regionToChange.getId());
        workingRegion.setObjectName(regionToChange.getObjectName());
        workingRegion.setCode(regionToChange.getCode());
        return workingRegion;
    }

    @Transactional
    public RegionDto updateRegion(RegionDto input) {
        Region region = convertDtoToRegion(input);
        return convertRegionToDto(updateRegionEntity(region));
    }

    // === DELETE ===
    @Transactional
    public boolean deleteRegion(String regionUuid) {
        try {
            regionRepository.getOne(regionUuid).setDeleted(true);
            return true;
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Object not found!");
        }
    }

    // ===== DTO TO ENTITY CONVERSION METHODS =====
    public RegionDto convertRegionToDto(Region input) {

        RegionDto regionDto = new RegionDto();
        regionDto.setId(input.getId());
        regionDto.setCode(input.getCode());
        regionDto.setObjectName(input.getObjectName());

        // convert all country list objects
        List<String> countryList = new ArrayList<>();
        for (Country country : input.getCountries()) {
            countryList.add(country.getId());
        }
        regionDto.setCountryIds(countryList);
        return regionDto;
    }

    public Region convertDtoToRegion(RegionDto input) {

        Region region = new Region();
        if (!Objects.isNull(input.getId())) region.setId(input.getId());
        region.setCode(input.getCode());
        region.setObjectName(input.getObjectName());

        if (!Objects.isNull(input.getCountryIds())) {
            // convert all country list objects
            List<Country> countryList = new ArrayList<>();
            for (String countryUuid : input.getCountryIds()) {
                countryList.add(countryRepository.findById(countryUuid).get());
            }
            region.setCountries(countryList);
        }
        return region;
    }

}
