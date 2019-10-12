package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChildContract extends DBEntityTemplate{

    @NotNull
    private String serviceCode;

    private BigDecimal price;

    private String currencyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract")
    @JsonIgnore
    private Contract contract;

    public ChildContract(String serviceCode, BigDecimal price, String currencyCode,String createdUser) {
        this.serviceCode = serviceCode;
        this.price = price;
        this.currencyCode = currencyCode;
        this.setCreatedBy(createdUser);
        this.setLastModifiedBy(createdUser);
    }


    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getserviceCode() {
        return serviceCode;
    }

    public void setserviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public ChildContract() {
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
