package capstone.is4103capstone.finance.requestsMgmt.model.dto;

import capstone.is4103capstone.entities.finance.BJF;
import capstone.is4103capstone.entities.finance.Project;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.Service;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.util.Tools;

import java.math.BigDecimal;

public class BJFModel extends RequestFormModel {

    private GeneralEntityModel service;
    private GeneralEntityModel vendor;
    private String bjfType;
    private BigDecimal capex;
    private BigDecimal revex;

    private GeneralEntityModel purchaseOrder;
    private GeneralEntityModel project;
    private String status;

    private String capexCurr;
    private String revexCurr;
    private String coverage;
    private String sponsor;
    private Boolean competitiveQuotesAvailable;

    public BJFModel() {
    }
//    public BJFModel(BJF e, Service s, Vendor v) {
//        setCostCenterCode(e.getCostCenter().getCode());
//        setId(e.getId());
//        setStatus(e.getBjfStatus().name());
//        setRequester(new EmployeeModel(e.getRequester()));
//        setCreatedDateTime(Tools.datetimeFormatter.format(e.getCreatedDateTime()));
//        setEstimatedBudget(e.getEstimatedBudget());
//        setBjfType(e.getBjfType().name());
//        if (v != null)
//            setVendor(new GeneralEntityModel(v));
//        if (s != null)
//            setService(new GeneralEntityModel(s));
//    }

    public BJFModel(BJF e, Service s, Vendor v, PurchaseOrder po, Project p) {
        if (po != null)
            setPurchaseOrder(new GeneralEntityModel(po));
        if (s != null)
            setService(new GeneralEntityModel(s));
        if (p != null){
            setProject(new GeneralEntityModel(p));
        }
        if (v != null)
            setVendor(new GeneralEntityModel(v));
        setRevex(e.getRevex());
        setCapex(e.getCapex());
        setRevexCurr(e.getRevexCurrency());
        setCapexCurr(e.getCapexCurrency());
        setSponsor(e.getSponsor());
        setCompetitiveQuotesAvailable(e.getCompetitiveQuotesAvailable());
        setCoverage(e.getCoverage());
        setBjfType(e.getBjfType().name());
        setStatus(e.getBjfStatus().name());
        setCurrencyCode(e.getCurrency());
        setCompetitiveQuotesAvailable(e.getCompetitiveQuotesAvailable());
        setEstimatedBudget(e.getEstimatedBudget());
        setAdditionalInfo(e.getAdditionalInfo());
        setDescription(e.getRequestDescription());
        setRequester(new EmployeeModel(e.getRequester()));
        setApprover(new EmployeeModel(e.getApprover()));
        setApprovalStatus(e.getApprovalStatus().name());
        setId(e.getId());
        setCostCenterCode(e.getCostCenter().getCode());
        setCreatedDateTime(Tools.datetimeFormatter.format(e.getCreatedDateTime()));
    }

    public String getCapexCurr() {
        return capexCurr;
    }

    public void setCapexCurr(String capexCurr) {
        this.capexCurr = capexCurr;
    }

    public String getRevexCurr() {
        return revexCurr;
    }

    public void setRevexCurr(String revexCurr) {
        this.revexCurr = revexCurr;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public Boolean getCompetitiveQuotesAvailable() {
        return competitiveQuotesAvailable;
    }

    public void setCompetitiveQuotesAvailable(Boolean competitiveQuotesAvailable) {
        this.competitiveQuotesAvailable = competitiveQuotesAvailable;
    }

    public GeneralEntityModel getService() {
        return service;
    }

    public void setService(GeneralEntityModel service) {
        this.service = service;
    }

    public GeneralEntityModel getVendor() {
        return vendor;
    }

    public void setVendor(GeneralEntityModel vendor) {
        this.vendor = vendor;
    }

    public String getBjfType() {
        return bjfType;
    }

    public void setBjfType(String bjfType) {
        this.bjfType = bjfType;
    }

    public BigDecimal getCapex() {
        return capex;
    }

    public void setCapex(BigDecimal capex) {
        this.capex = capex;
    }

    public BigDecimal getRevex() {
        return revex;
    }

    public void setRevex(BigDecimal revex) {
        this.revex = revex;
    }


    public GeneralEntityModel getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(GeneralEntityModel purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public GeneralEntityModel getProject() {
        return project;
    }

    public void setProject(GeneralEntityModel project) {
        this.project = project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
