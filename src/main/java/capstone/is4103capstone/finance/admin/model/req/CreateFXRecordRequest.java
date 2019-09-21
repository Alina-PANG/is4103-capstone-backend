package capstone.is4103capstone.finance.admin.model.req;

import java.io.Serializable;
import java.math.BigDecimal;

//Date format: yyyy-MM-dd;
public class CreateFXRecordRequest implements Serializable {
    String baseCurrency;
    String priceCurrency;
    BigDecimal exchangeRate;
    String effectiveDate;
    String username;

    public CreateFXRecordRequest() {
    }

    public CreateFXRecordRequest(String baseCurrency, String priceCurrency, BigDecimal exchangeRate, String effectiveDate, String username) {
        this.baseCurrency = baseCurrency;
        this.priceCurrency = priceCurrency;
        this.exchangeRate = exchangeRate;
        this.effectiveDate = effectiveDate;
        this.username = username;
    }

}
