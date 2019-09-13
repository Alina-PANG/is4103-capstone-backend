package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class ContractLine extends DBEntityTemplate{
    private String merchandiseCode;
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract")
    @JsonIgnore
    private Contract contract;

    public ContractLine(String merchandiseCode, BigDecimal price) {
        this.merchandiseCode = merchandiseCode;
        this.price = price;
    }

    public ContractLine() {
    }

    public String getMerchandiseCode() {
        return merchandiseCode;
    }

    public void setMerchandiseCode(String merchandiseCode) {
        this.merchandiseCode = merchandiseCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
