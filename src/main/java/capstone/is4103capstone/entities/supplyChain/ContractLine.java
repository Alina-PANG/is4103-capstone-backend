package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class ContractLine extends DBEntityTemplate{
    private String merchandiseCode;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "contract")
    @JsonIgnore
    private Contract contract;

    public ContractLine(String merchandiseCode, Double price) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
