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
import java.util.Optional;

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
        try {
            Region workingRegion = regionRepository.getOne(input.getId().orElseThrow(() -> new NullPointerException("UUID is null!")));
            input.getObjectName().ifPresent(workingRegion::setObjectName);
            return convertRegionToDto(workingRegion);
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Region with UUID " + input.getId() + " not found!");
        }
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
        regionDto.setId(Optional.of(input.getId()));
        regionDto.setCode(Optional.of(input.getCode()));
        regionDto.setObjectName(Optional.of(input.getObjectName()));

        // convert all country list objects
        List<String> countryList = new ArrayList<>();
        for (Country country : input.getCountries()) {
            countryList.add(country.getId());
        }
        regionDto.setCountryIds(Optional.of(countryList));
        return regionDto;
    }

    public Region convertDtoToRegion(RegionDto input) {

        Region region = new Region();
        input.getId().ifPresent(value -> region.setId(value));
        input.getCode().ifPresent(value -> region.setCode(value));
        input.getObjectName().ifPresent(value -> region.setObjectName(value));

        input.getCountryIds().ifPresent(value -> {
            List<Country> countryList = new ArrayList<>();
            for (String countryUuid : value) {
                countryList.add(countryRepository.findById(countryUuid).get());
            }
            region.setCountries(countryList);
        });
        return region;
    }

}
