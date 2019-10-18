package capstone.is4103capstone.finance.finPurchaseOrder.model.req;


import java.io.Serializable;
import java.util.List;

public class CreatePOReq implements Serializable {
    private String currencyCode;
    private List<String> relatedBJF;
    private String username;
    private Double amount;



    public CreatePOReq() {
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<String> getRelatedBJF() {
        return relatedBJF;
    }

    public void setRelatedBJF(List<String> relatedBJF) {
        this.relatedBJF = relatedBJF;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}