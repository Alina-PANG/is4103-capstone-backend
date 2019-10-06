package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.CurrencyDto;
import capstone.is4103capstone.admin.repository.CurrencyRepository;
import capstone.is4103capstone.entities.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    // ===== CRUD Methods =====
    // === CREATE ===
    public Currency createCurrencyEntity(Currency input) {
        return currencyRepository.save(input);
    }

    public CurrencyDto createCurrency(CurrencyDto input) {
        return entityToDto(createCurrencyEntity(dtoToEntity(input)));
    }

    // === RETRIEVE ===
    public List<Currency> getAllCurrencyEntities() {
        return currencyRepository.findAll();
    }

    public List<CurrencyDto> getAllCurrencies() throws Exception {
        List<CurrencyDto> currencyDtos = entityToDto(getAllCurrencyEntities(), true);
        if (currencyDtos.isEmpty()) throw new Exception("No currencies were found in the database!");
        return currencyDtos;
    }

    public Currency getCurrencyEntityByUuid(String input) throws Exception {
        Currency currency = currencyRepository.findCurrencyById(input);
        if (Objects.isNull(currency)) throw new Exception("Currency with UUID " + input + " was not found!");
        return currency;
    }

    public CurrencyDto getCurrencyByUuid(String input) throws Exception {
        return entityToDto(getCurrencyEntityByUuid(input));
    }

    // === UPDATE ===
    @Transactional
    public Currency updateCurrencyEntity(Currency input) throws Exception {
        Currency toUpdate = getCurrencyEntityByUuid(input.getId());
        toUpdate.setObjectName(input.getObjectName());
        toUpdate.setCountryCode(input.getCountryCode());
        return toUpdate;
    }

    @Transactional
    public CurrencyDto updateCurrency(CurrencyDto input) throws Exception {
        Currency toUpdate = getCurrencyEntityByUuid(input.getId().orElseThrow(() -> new NullPointerException("UUID is null!")));
        if (toUpdate.getDeleted())
            throw new Exception("Currency with UUID " + input.getId().get() + " has already been deleted and cannot be modified!");
        input.getObjectName().ifPresent(toUpdate::setObjectName);
        input.getCountryCode().ifPresent(toUpdate::setCountryCode);
        return entityToDto(toUpdate);
    }

    // === DELETE ===
    @Transactional
    public boolean deleteCurrency(String input) throws Exception {
        Currency working = getCurrencyEntityByUuid(input);
        if (working.getDeleted()) throw new Exception("Currency with UUID " + input + " has already been deleted!");
        working.setDeleted(true);
        return true;
    }

    // ===== ENTITY TO DTO CONVERSION METHODS =====
    public Currency dtoToEntity(CurrencyDto input) {
        Currency currency = new Currency();
        input.getId().ifPresent(id -> currency.setId(id));
        input.getCode().ifPresent(code -> currency.setCode(code));
        input.getCountryCode().ifPresent(code -> currency.setCountryCode(code));
        input.getObjectName().ifPresent(name -> currency.setObjectName(name));
        return currency;
    }

    public List<Currency> dtoToEntity(List<CurrencyDto> input) {
        List<Currency> currencies = new ArrayList<>();
        for (CurrencyDto currencyDto : input) {
            currencies.add(dtoToEntity(currencyDto));
        }
        return currencies;
    }

    public CurrencyDto entityToDto(Currency input) {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setId(Optional.of(input.getId()));
        currencyDto.setCode(Optional.of(input.getCode()));
        currencyDto.setCountryCode(Optional.of(input.getCountryCode()));
        currencyDto.setObjectName(Optional.of(input.getObjectName()));
        return currencyDto;
    }

    public List<CurrencyDto> entityToDto(List<Currency> input, boolean suppressDeleted) {
        List<CurrencyDto> currencyDtos = new ArrayList<>();
        for (Currency currency : input) {
            if (suppressDeleted) {
                if (!currency.getDeleted()) currencyDtos.add(entityToDto(currency));
            } else {
                currencyDtos.add(entityToDto(currency));
            }
        }
        return currencyDtos;
    }
}
