package capstone.is4103capstone.finance.finPurchaseOrder.model.req;


import capstone.is4103capstone.entities.finance.SpendingRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreatePOReq implements Serializable {
    private String currencyCode;
    private List<String> relatedBJF;
    private Double amount;
    private String vendorid;
    private String poNumber;
    private String approverUsername;
    private List<SpendingRecord> spendingRecordList = new ArrayList<>();
    private boolean closePOInstruc;

    public CreatePOReq() {
    }

    public List<SpendingRecord> getSpendingRecordList() {
        return spendingRecordList;
    }

    public boolean isClosePOInstruc() {
        return closePOInstruc;
    }

    public void setClosePOInstruc(boolean closePOInstruc) {
        this.closePOInstruc = closePOInstruc;
    }

    public void setSpendingRecordList(List<SpendingRecord> spendingRecordList) {
        this.spendingRecordList = spendingRecordList;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getApproverUsername() {
        return approverUsername;
    }

    public void setApproverUsername(String approverUsername) {
        this.approverUsername = approverUsername;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
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

}
