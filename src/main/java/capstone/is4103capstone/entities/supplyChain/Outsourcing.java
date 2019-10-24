package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.Country;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Region;
import capstone.is4103capstone.entities.finance.Service;
import capstone.is4103capstone.entities.helper.StringListConverter;
import capstone.is4103capstone.util.enums.MaterialityEnum;
import capstone.is4103capstone.util.enums.OutsourcingCategoryEnum;
import capstone.is4103capstone.util.enums.OutsourcingTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Outsourcing extends DBEntityTemplate {
    private String regionId;
    private String countryId;
    private String departmentId;
    private OutsourcingTypeEnum outsourcingType;
    private OutsourcingCategoryEnum outsourcingCategory;
    private MaterialityEnum materiality;
    private String outsourcingTitle;
    private String outsourcingAssessmentId;

    @OneToOne(mappedBy = "outsourcing",optional = true)
    @JsonIgnore
    private OutsourcingAssessment outsourcingAssessment;

    @Convert(converter = StringListConverter.class)
    private List<String> serviceIdList = new ArrayList();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outsourcing_vendor")
    @JsonIgnore
    private Vendor outsourcedVendor;

    @Temporal(TemporalType.DATE)
    private Date dueDiligenceDate;

    @Temporal(TemporalType.DATE)
    private Date materialityAssessmentDate;

    @Temporal(TemporalType.DATE)
    private Date bcpTestDate;

    //can be empty when creating outsourcing
    //after annual self-assessment is done, this date will be updated manually
    @Temporal(TemporalType.DATE)
    private Date annualSelfAssessmentDate;

    //the date that the vendor is audited
    @Temporal(TemporalType.DATE)
    private Date independentAuditDate;

    public Outsourcing() {
    }

    public Outsourcing(String regionId, String countryId, String departmentId, OutsourcingTypeEnum outsourcingType, OutsourcingCategoryEnum outsourcingCategory, MaterialityEnum materiality, String outsourcingTitle, String outsourcingAssessmentId, List<String> serviceIdList, Vendor outsourcedVendor, Date dueDiligenceDate, Date materialityAssessmentDate, Date bcpTestDate, Date annualSelfAssessmentDate, Date independentAuditDate) {
        this.regionId = regionId;
        this.countryId = countryId;
        this.departmentId = departmentId;
        this.outsourcingType = outsourcingType;
        this.outsourcingCategory = outsourcingCategory;
        this.materiality = materiality;
        this.outsourcingTitle = outsourcingTitle;
        this.outsourcingAssessmentId = outsourcingAssessmentId;
        this.serviceIdList = serviceIdList;
        this.outsourcedVendor = outsourcedVendor;
        this.dueDiligenceDate = dueDiligenceDate;
        this.materialityAssessmentDate = materialityAssessmentDate;
        this.bcpTestDate = bcpTestDate;
        this.annualSelfAssessmentDate = annualSelfAssessmentDate;
        this.independentAuditDate = independentAuditDate;
    }

    public OutsourcingAssessment getOutsourcingAssessment() {
        return outsourcingAssessment;
    }

    public void setOutsourcingAssessment(OutsourcingAssessment outsourcingAssessment) {
        this.outsourcingAssessment = outsourcingAssessment;
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

    public Vendor getOutsourcedVendor() {
        return outsourcedVendor;
    }

    public void setOutsourcedVendor(Vendor outsourcedVendor) {
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

    public String getOutsourcingAssessmentId() {
        return outsourcingAssessmentId;
    }

    public void setOutsourcingAssessmentId(String outsourcingAssessmentId) {
        this.outsourcingAssessmentId = outsourcingAssessmentId;
    }
}