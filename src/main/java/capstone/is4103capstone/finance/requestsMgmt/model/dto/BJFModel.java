package capstone.is4103capstone.finance.requestsMgmt.model.dto;

import capstone.is4103capstone.entities.finance.BJF;
import capstone.is4103capstone.entities.finance.Project;
import capstone.is4103capstone.entities.finance.PurchaseOrder;
import capstone.is4103capstone.entities.finance.Service;
import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.finance.admin.model.ServiceModel;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.supplychain.model.VendorModel;
import capstone.is4103capstone.util.Tools;

import java.math.BigDecimal;

public class BJFModel extends RequestFormModel {

    private GeneralEntityModel service;
    private GeneralEntityModel vendor;
    private String bjfType;
    private BigDecimal ongoingCost;

    private BigDecimal projectCost; // if type == project

    private GeneralEntityModel purchaseOrder;
    private GeneralEntityModel project;
    private String status;


    public BJFModel() {
    }
    public BJFModel(BJF e, Service s, Vendor v) {
        setCostCenterCode(e.getCostCenter().getCode());
        setId(e.getId());
        setStatus(e.getBjfStatus().name());
        setRequester(new EmployeeModel(e.getRequester()));
        setCreatedDateTime(Tools.datetimeFormatter.format(e.getCreatedDateTime()));
        setEstimatedBudget(e.getEstimatedBudget());
        setBjfType(e.getBjfType().name());
        if (v != null)
            setVendor(new GeneralEntityModel(v));
        if (s != null)
            setService(new GeneralEntityModel(s));
    }

    public BJFModel(BJF e, Service s, Vendor v, PurchaseOrder po, Project p) {
        if (po != null)
            setPurchaseOrder(new GeneralEntityModel(po));
        if (s != null)
            setService(new GeneralEntityModel(s));
        if (p != null)
            setProject(new GeneralEntityModel(p));
        if (v != null)
            setVendor(new GeneralEntityModel(v));

        setBjfType(e.getBjfType().name());
        setStatus(e.getBjfStatus().name());
        setCurrencyCode(e.getCurrency());
        setEstimatedBudget(e.getEstimatedBudget());
        setOngoingCost(e.getOngoingCost());
        setAdditionalInfo(e.getAdditionalInfo());
        setDescription(e.getRequestDescription());
        setRequester(new EmployeeModel(e.getRequester()));
        setId(e.getId());
        setCostCenterCode(e.getCostCenter().getCode());
        setCreatedDateTime(Tools.datetimeFormatter.format(e.getCreatedDateTime()));

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

    public BigDecimal getOngoingCost() {
        return ongoingCost;
    }

    public void setOngoingCost(BigDecimal ongoingCost) {
        this.ongoingCost = ongoingCost;
    }

    public BigDecimal getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(BigDecimal projectCost) {
        this.projectCost = projectCost;
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
