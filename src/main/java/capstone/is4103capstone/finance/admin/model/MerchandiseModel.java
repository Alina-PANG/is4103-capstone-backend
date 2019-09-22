package capstone.is4103capstone.finance.admin.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MerchandiseModel implements Serializable {
    String merchandiseName;
    String merchandiseCode;
    String vendorCode;
    String vendorName;
    String measureUnit;
    BigDecimal currentPrice;

    public MerchandiseModel() {
    }

    public MerchandiseModel(String merchandiseName, String merchandiseCode) {
        this.merchandiseName = merchandiseName;
        this.merchandiseCode = merchandiseCode;
    }

    public MerchandiseModel(String merchandiseName, String merchandiseCode, String vendorCode, String vendorName, String measureUnit, BigDecimal currentPrice) {
        this.merchandiseName = merchandiseName;
        this.merchandiseCode = merchandiseCode;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.measureUnit = measureUnit;
        this.currentPrice = currentPrice;
    }
}
