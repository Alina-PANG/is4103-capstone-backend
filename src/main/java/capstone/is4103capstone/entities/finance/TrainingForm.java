package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.RequestFormTemplate;
import capstone.is4103capstone.entities.enums.TrainingTypeEnum;
import capstone.is4103capstone.util.enums.TravelTrainingTypeEnum;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class TrainingForm extends RequestFormTemplate {

    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String trainingLocation;

    private String targetAudience; // just briefly descripted;

    private String trainingTitle;

    private String trainerName;

    private String trainerEmail;

    private String trainerCompany;// if internal: empty

    private TrainingTypeEnum trainerType;
    private TravelTrainingTypeEnum trainingType;


    public TrainingForm() {
    }

    public TrainingForm(String objectName, Employee requester, CostCenter costCenter, String requestDescription, String additionalInfo, BigDecimal estimatedBudget, String currency, Date startDate, Date endDate, String trainingLocation, String targetAudience, String trainingTitle, String trainerName, String trainerEmail, String trainerCompany, TrainingTypeEnum trainingType, TravelTrainingTypeEnum travelType) {
        super(objectName, requester, costCenter, requestDescription, additionalInfo, estimatedBudget, currency);
        this.startDate = startDate;
        this.endDate = endDate;
        this.trainingLocation = trainingLocation;
        this.targetAudience = targetAudience;
        this.trainingTitle = trainingTitle;
        this.trainerName = trainerName;
        this.trainerEmail = trainerEmail;
        this.trainerCompany = trainerCompany;
        this.trainerType = trainingType;
        this.trainingType = travelType;
    }

    public TravelTrainingTypeEnum getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TravelTrainingTypeEnum trainingType) {
        this.trainingType = trainingType;
    }

    public TrainingForm(Date startDate, Date endDate, String trainingLocation, String targetAudience, String trainingTitle, String trainerName, String trainerEmail, String trainerCompany, TrainingTypeEnum trainingType) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.trainingLocation = trainingLocation;
        this.targetAudience = targetAudience;
        this.trainingTitle = trainingTitle;
        this.trainerName = trainerName;
        this.trainerEmail = trainerEmail;
        this.trainerCompany = trainerCompany;
        this.trainerType = trainingType;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTrainingLocation() {
        return trainingLocation;
    }

    public void setTrainingLocation(String trainingLocation) {
        this.trainingLocation = trainingLocation;
    }

    public String getTrainingTitle() {
        return trainingTitle;
    }

    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerEmail() {
        return trainerEmail;
    }

    public void setTrainerEmail(String trainerEmail) {
        this.trainerEmail = trainerEmail;
    }

    public String getTrainerCompany() {
        return trainerCompany;
    }

    public void setTrainerCompany(String trainerCompany) {
        this.trainerCompany = trainerCompany;
    }

    public TrainingTypeEnum getTrainerType() {
        return trainerType;
    }

    public void setTrainerType(TrainingTypeEnum trainerType) {
        this.trainerType = trainerType;
    }
}
