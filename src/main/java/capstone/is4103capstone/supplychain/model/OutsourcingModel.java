package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.util.enums.MaterialityEnum;
import capstone.is4103capstone.util.enums.OutsourcingCategoryEnum;
import capstone.is4103capstone.util.enums.OutsourcingTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OutsourcingModel implements Serializable {
    private String code;
    private String id;
    private Long seqNo;
    private String outsourcingTitle;
    private GeneralEntityModel region;
    private GeneralEntityModel country;
    private GeneralEntityModel department;
    private OutsourcingTypeEnum outsourcingType;
    private OutsourcingCategoryEnum outsourcingCategory;
    private MaterialityEnum materiality;
    private List<GeneralEntityModel> serviceList;
    private GeneralEntityModel outsourcedVendor;
    private GeneralEntityModel outsourcingAssessment;

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

    public OutsourcingModel() {
    }

    public OutsourcingModel(String code, String id, Long seqNo, String outsourcingTitle, GeneralEntityModel region, GeneralEntityModel country, GeneralEntityModel department, OutsourcingTypeEnum outsourcingType, OutsourcingCategoryEnum outsourcingCategory, MaterialityEnum materiality, List<GeneralEntityModel> serviceList, GeneralEntityModel outsourcedVendor, GeneralEntityModel outsourcingAssessment, Date dueDiligenceDate, Date materialityAssessmentDate, Date bcpTestDate, Date annualSelfAssessmentDate, Date independentAuditDate) {
        this.code = code;
        this.id = id;
        this.seqNo = seqNo;
        this.outsourcingTitle = outsourcingTitle;
        this.region = region;
        this.country = country;
        this.department = department;
        this.outsourcingType = outsourcingType;
        this.outsourcingCategory = outsourcingCategory;
        this.materiality = materiality;
        this.serviceList = serviceList;
        this.outsourcedVendor = outsourcedVendor;
        this.outsourcingAssessment = outsourcingAssessment;
        this.dueDiligenceDate = dueDiligenceDate;
        this.materialityAssessmentDate = materialityAssessmentDate;
        this.bcpTestDate = bcpTestDate;
        this.annualSelfAssessmentDate = annualSelfAssessmentDate;
        this.independentAuditDate = independentAuditDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    public String getOutsourcingTitle() {
        return outsourcingTitle;
    }

    public void setOutsourcingTitle(String outsourcingTitle) {
        this.outsourcingTitle = outsourcingTitle;
    }

    public GeneralEntityModel getRegion() {
        return region;
    }

    public void setRegion(GeneralEntityModel region) {
        this.region = region;
    }

    public GeneralEntityModel getCountry() {
        return country;
    }

    public void setCountry(GeneralEntityModel country) {
        this.country = country;
    }

    public GeneralEntityModel getDepartment() {
        return department;
    }

    public void setDepartment(GeneralEntityModel department) {
        this.department = department;
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

    public List<GeneralEntityModel> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<GeneralEntityModel> serviceList) {
        this.serviceList = serviceList;
    }

    public GeneralEntityModel getOutsourcedVendor() {
        return outsourcedVendor;
    }

    public void setOutsourcedVendor(GeneralEntityModel outsourcedVendor) {
        this.outsourcedVendor = outsourcedVendor;
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

    public GeneralEntityModel getOutsourcingAssessment() {
        return outsourcingAssessment;
    }

    public void setOutsourcingAssessment(GeneralEntityModel outsourcingAssessment) {
        this.outsourcingAssessment = outsourcingAssessment;
    }
}
