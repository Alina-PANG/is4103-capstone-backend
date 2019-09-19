package capstone.is4103capstone.admin.controller;

import capstone.is4103capstone.admin.repository.CurrencyRepository;
import capstone.is4103capstone.entities.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ce")
public class CompanyEntityController {

    // ===== ALL REPOS =====
    @Autowired
    CurrencyRepository currencyRepository;

    // === CURRENCY METHODS ===
    @PostMapping("/currency")
    public Currency createCurrency(@RequestParam(name = "name") String currencyName, @RequestParam(name = "code") String currencyCode) {
        Currency toCreate = new Currency(currencyName, currencyCode);
        currencyRepository.save(toCreate);
        return toCreate;
    }

    @GetMapping("/currency")
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    @DeleteMapping("/currency")
    public ResponseEntity deleteCurrency(String currencyCode) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
