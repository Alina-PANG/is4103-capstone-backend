package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.dto.CurrencyDto;
import capstone.is4103capstone.admin.repository.CurrencyRepository;
import capstone.is4103capstone.entities.Currency;
import capstone.is4103capstone.util.exception.DbObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
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

    public List<CurrencyDto> getAllCurrencies() {
        List<CurrencyDto> currencyDtos = new ArrayList<>();
        for (Currency currency : getAllCurrencyEntities()) {
            if (!currency.getDeleted()) currencyDtos.add(entityToDto(currency));
        }
        return currencyDtos;
    }

    public Currency getCurrencyEntityByUuid(String input) {
        try {
            return currencyRepository.getOne(input);
        } catch (EntityNotFoundException ex) {
            throw new DbObjectNotFoundException("Currency object with UUID " + input + "  not found!");
        }
    }

    public CurrencyDto getCurrencyByUuid(String input) {
        return entityToDto(getCurrencyEntityByUuid(input));
    }

    // === UPDATE ===
    @Transactional
    public Currency updateCurrencyEntity(Currency input) {
        try {
            Currency toUpdate = currencyRepository.getOne(input.getId());
            toUpdate.setObjectName(input.getObjectName());
            toUpdate.setCurrencyCode(input.getCurrencyCode());
            toUpdate.setCode(input.getCode());
            return toUpdate;
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Currency object with UUID " + input + "  not found!");
        }
    }

    @Transactional
    public CurrencyDto updateCurrency(CurrencyDto input) {
        try {
            Currency toUpdate = currencyRepository.getOne(input.getId().orElseThrow(() -> new NullPointerException("UUID is null!")));
            input.getObjectName().ifPresent(toUpdate::setObjectName);
            input.getCurrencyCode().ifPresent(toUpdate::setCurrencyCode);
            return entityToDto(toUpdate);
        } catch (IllegalArgumentException ex) {
            throw new DbObjectNotFoundException("Currency object with UUID " + input + "  not found!");
        }
    }

    // === DELETE ===
    @Transactional
    public boolean deleteCurrency(String input) {
        Currency working = getCurrencyEntityByUuid(input);
        working.setDeleted(true);
        return true;
    }

    // ===== ENTITY TO DTO CONVERSION METHODS =====
    public Currency dtoToEntity(CurrencyDto input) {
        Currency currency = new Currency();
        input.getId().ifPresent(id -> currency.setId(id));
        input.getCode().ifPresent(code -> currency.setCode(code));
        input.getCurrencyCode().ifPresent(code -> currency.setCurrencyCode(code));
        input.getObjectName().ifPresent(name -> currency.setObjectName(name));
        return currency;
    }

    public CurrencyDto entityToDto(Currency input) {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setId(Optional.of(input.getId()));
        currencyDto.setCode(Optional.of(input.getCode()));
        currencyDto.setCurrencyCode(Optional.of(input.getCurrencyCode()));
        currencyDto.setObjectName(Optional.of(input.getObjectName()));
        return currencyDto;
    }
}
