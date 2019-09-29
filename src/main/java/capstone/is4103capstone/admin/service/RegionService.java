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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        Region toCreate = dtoToEntity(input);
        return entityToDto(createRegionByEntity(toCreate));
    }

    // === RETRIEVE ===
    public List<Region> getAllRegionEntities() {
        List<Region> regionList = regionRepository.findAll();
        if (regionList.isEmpty()) throw new DbObjectNotFoundException("No regions found.");
        return regionList;
    }

    public List<RegionDto> getAllRegions() throws Exception {
        return entityToDto(getAllRegionEntities(), true);
    }

    public Region getRegionEntityByUuid(String uuid) throws Exception {
        Region region = regionRepository.findRegionById(uuid);
        if (Objects.isNull(region)) throw new Exception("Region with UUID " + uuid + " was not found.");
        return region;
    }

    public RegionDto getRegionByUuid(String uuid) throws Exception {
        return entityToDto(getRegionEntityByUuid(uuid));
    }

    // === UPDATE ===
    @Transactional
    public Region updateRegionEntity(Region regionToChange) throws Exception {
        Region workingRegion = regionRepository.findRegionById(regionToChange.getId());
        if (Objects.isNull(workingRegion))
            throw new Exception("Region with UUID " + regionToChange.getId() + " was not found!");
        if (workingRegion.getDeleted())
            throw new Exception("Region with UUID " + regionToChange.getId() + " has already been deleted and cannot be modified!");
        workingRegion.setObjectName(regionToChange.getObjectName());
        return workingRegion;
    }

    @Transactional
    public RegionDto updateRegion(RegionDto input) throws Exception {
        return entityToDto(updateRegionEntity(dtoToEntity(input)));
    }

    // === DELETE ===
    @Transactional
    public boolean deleteRegion(String regionUuid) throws Exception {
        Region region = regionRepository.findRegionById(regionUuid);
        if (Objects.isNull(region)) throw new Exception("Region with UUID " + regionUuid + " was not found!");
        if (region.getDeleted()) throw new Exception(("Region with UUID " + regionUuid + " has already been deleted!"));
        region.setDeleted(true);
        return true;
    }

    // ===== DTO TO ENTITY CONVERSION METHODS =====
    public RegionDto entityToDto(Region input) {

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

    public List<RegionDto> entityToDto(List<Region> input, boolean suppressDeleted) throws Exception {
        List<RegionDto> regionDtos = new ArrayList<>();
        for (Region region : input) {
            if (suppressDeleted) {
                if (!region.getDeleted()) regionDtos.add(entityToDto(region));
            } else {
                regionDtos.add(entityToDto(region));
            }
        }
        return regionDtos;
    }

    public Region dtoToEntity(RegionDto input) {

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

    public List<Region> dtoToEntity(List<RegionDto> input) throws Exception {
        List<Region> regions = new ArrayList<>();
        for (RegionDto regionDto : input) {
            regions.add(dtoToEntity(regionDto));
        }
        if (regions.isEmpty()) throw new Exception("No regions were input!");
        return regions;
    }

}
