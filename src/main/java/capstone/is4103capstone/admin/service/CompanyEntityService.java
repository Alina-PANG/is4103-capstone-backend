package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.repository.CountryRepository;
import capstone.is4103capstone.admin.repository.CurrencyRepository;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.Currency;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyEntityService {

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    CountryRepository countryRepository;

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
    // ===== START COUNTRY METHODS =====
    public Country createCountry(Country countryReq) {
        Country newCountry = new Country();
        newCountry.setObjectName(countryReq.getObjectName());
        newCountry.setRegion(countryReq.getRegion());
        newCountry.setCode(countryReq.getCode());
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
