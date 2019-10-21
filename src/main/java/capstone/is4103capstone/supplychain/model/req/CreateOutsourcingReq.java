package capstone.is4103capstone.supplychain.model.req;

import capstone.is4103capstone.util.enums.MaterialityEnum;
import capstone.is4103capstone.util.enums.OutsourcingCategoryEnum;
import capstone.is4103capstone.util.enums.OutsourcingTypeEnum;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CreateOutsourcingReq implements Serializable {
    private String regionId;
    private String countryId;
    private String departmentId;
    private OutsourcingTypeEnum outsourcingType;
    private OutsourcingCategoryEnum outsourcingCategory;
    private MaterialityEnum materiality;
    private String outsourcingTitle;
    private List<String> serviceIdList;
    private String vendorId;

    @Temporal(TemporalType.DATE)
    private Date dueDiligenceDate;

    @Temporal(TemporalType.DATE)
    private Date materialityAssessmentDate;

    @Temporal(TemporalType.DATE)
    private Date bcpTestDate;

    @Temporal(TemporalType.DATE)
    private Date annualSelfAssessmentDate;

    @Temporal(TemporalType.DATE)
    private Date independentAuditDate;

    public CreateOutsourcingReq() {
    }

    public CreateOutsourcingReq(String regionId, String countryId, String departmentId, OutsourcingTypeEnum outsourcingType, OutsourcingCategoryEnum outsourcingCategory, MaterialityEnum materiality, String outsourcingTitle, List<String> serviceIdList, String vendorId, Date dueDiligenceDate, Date materialityAssessmentDate, Date bcpTestDate, Date annualSelfAssessmentDate, Date independentAuditDate) {
        this.regionId = regionId;
        this.countryId = countryId;
        this.departmentId = departmentId;
        this.outsourcingType = outsourcingType;
        this.outsourcingCategory = outsourcingCategory;
        this.materiality = materiality;
        this.outsourcingTitle = outsourcingTitle;
        this.serviceIdList = serviceIdList;
        this.vendorId = vendorId;
        this.dueDiligenceDate = dueDiligenceDate;
        this.materialityAssessmentDate = materialityAssessmentDate;
        this.bcpTestDate = bcpTestDate;
        this.annualSelfAssessmentDate = annualSelfAssessmentDate;
        this.independentAuditDate = independentAuditDate;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public OutsourcingTypeEnum getOutsourcingType() {
        return outsourcingType;
    }

    public void setOutsourcingType(OutsourcingTypeEnum outsourcingType) {
        this.outsourcingType = outsourcingType;
    }

    public OutsourcingCategoryEnum getOutsourcingCategory() {
        return outsourcingCategory;
    }

    public void setOutsourcingCategory(OutsourcingCategoryEnum outsourcingCategory) {
        this.outsourcingCategory = outsourcingCategory;
    }

    public MaterialityEnum getMateriality() {
        return materiality;
    }

    public void setMateriality(MaterialityEnum materiality) {
        this.materiality = materiality;
    }

    public String getOutsourcingTitle() {
        return outsourcingTitle;
    }

    public void setOutsourcingTitle(String outsourcingTitle) {
        this.outsourcingTitle = outsourcingTitle;
    }

    public List<String> getServiceIdList() {
        return serviceIdList;
    }

    public void setServiceIdList(List<String> serviceIdList) {
        this.serviceIdList = serviceIdList;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public Date getDueDiligenceDate() {
        return dueDiligenceDate;
    }

    public void setDueDiligenceDate(Date dueDiligenceDate) {
        this.dueDiligenceDate = dueDiligenceDate;
    }

    public Date getMaterialityAssessmentDate() {
        return materialityAssessmentDate;
    }

    public void setMaterialityAssessmentDate(Date materialityAssessmentDate) {
        this.materialityAssessmentDate = materialityAssessmentDate;
    }

    public Date getBcpTestDate() {
        return bcpTestDate;
    }

    public void setBcpTestDate(Date bcpTestDate) {
        this.bcpTestDate = bcpTestDate;
    }

    public Date getAnnualSelfAssessmentDate() {
        return annualSelfAssessmentDate;
    }

    public void setAnnualSelfAssessmentDate(Date annualSelfAssessmentDate) {
        this.annualSelfAssessmentDate = annualSelfAssessmentDate;
    }

    public Date getIndependentAuditDate() {
        return independentAuditDate;
    }

    public void setIndependentAuditDate(Date independentAuditDate) {
        this.independentAuditDate = independentAuditDate;
    }
}
