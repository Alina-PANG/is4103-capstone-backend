package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.model.req.CountryRestApiReq;
import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.admin.repository.CurrencyRepository;
import capstone.is4103capstone.admin.repository.RegionRepository;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.Currency;
import capstone.is4103capstone.entities.Region;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyEntityService {

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    RegionRepository regionRepository;

    // ===== START CURRENCY METHODS =====
    public Currency createCurrency(Currency newCurrency) {
        Currency toCreate = new Currency(newCurrency.getObjectName(), newCurrency.getCurrencyCode());
        currencyRepository.save(toCreate);
        return toCreate;
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Transactional
    public Currency updateCurrency(String uuid, Currency newCurrencyObj) throws DbObjectNotFoundException {
        Optional<Currency> curr = currencyRepository.findById(uuid);
        if (curr.isPresent()) {
            curr.get().setObjectName(newCurrencyObj.getObjectName());
            return curr.get();
        } else {
            throw new DbObjectNotFoundException();
        }
    }

    public boolean deleteCurrency(String currencyCode) throws DbObjectNotFoundException {
        Currency curr = currencyRepository.findCurrencyByCurrencyCode(currencyCode);
        if (Objects.isNull(curr)) throw new DbObjectNotFoundException("Currency not found!");
        currencyRepository.delete(curr);
        return true;
    }

    // ===== END CURRENCY METHODS =====

    // ===== START REGION METHODS =====
    public Region createRegion(Region input) {
        Region toPersist = new Region();
        toPersist.setObjectName(input.getObjectName());
        toPersist.setCode(input.getCode());
        return regionRepository.save(toPersist);
    }

    public List<Region> getAllRegions() {
        List<Region> regionList = regionRepository.findAll();
        if (regionList.isEmpty()) throw new DbObjectNotFoundException("No regions found.");
        return regionList;
    }

    public Region getRegionByName(String regionName) {
        Region region = regionRepository.getRegionByObjectName(regionName);
        if (Objects.isNull(region))
            throw new DbObjectNotFoundException("Region with name " + regionName + " was not found.");
        return region;
    }

    public Region getRegionByUuid(String uuid) {
        try {
            Region region = regionRepository.getOne(uuid);
            return region;
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Region with UUID " + uuid + " was not found.");
        }
    }

    @Transactional
    public Region updateRegion(Region regionToChange) throws EntityNotFoundException {
        Region workingRegion = regionRepository.getOne(regionToChange.getId());
        workingRegion.setObjectName(regionToChange.getObjectName());
        workingRegion.setCode(regionToChange.getCode());
        return workingRegion;
    }

    @Transactional
    public boolean deleteRegion(Region region) {
        try {
            regionRepository.getOne(region.getId()).setDeleted(true);
            return true;
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Object not found!");
        }
    }

    // ===== START COUNTRY METHODS =====
    public Country createCountry(CountryRestApiReq countryReq) {
        Country newCountry = new Country();
        newCountry.setObjectName(countryReq.getName());
        newCountry.setCode(countryReq.getCountryCode());
        return countryRepository.save(newCountry);
    }


    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Transactional
    public Country updateCountry(Country oldCountry, Country newCountry) {
        Country workingCountry = countryRepository.findCountryByCode(oldCountry.getCode());
        if (Objects.isNull(workingCountry))
            throw new DbObjectNotFoundException("Country " + oldCountry.getCode() + " not found!");
        workingCountry.setRegion(newCountry.getRegion());
        workingCountry.setObjectName(newCountry.getObjectName());
        workingCountry.setOffices(newCountry.getOffices());
        return workingCountry;
    }

    public boolean deleteCountry(String uuid) throws DbObjectNotFoundException {
        Optional<Country> country = countryRepository.findById(uuid);
        if (!country.isPresent()) throw new DbObjectNotFoundException("Country with UUID " + uuid + " not found!");
        countryRepository.delete(country.get());
        return true;
    }
    // ===== END COUNTRY METHODS =====

}
