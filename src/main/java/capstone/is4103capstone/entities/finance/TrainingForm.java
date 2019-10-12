package capstone.is4103capstone.entities.finance;

import capstone.is4103capstone.entities.RequestFormTemplate;
import capstone.is4103capstone.entities.enums.TrainingTypeEnum;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    private TrainingTypeEnum trainingType;

    public TrainingForm() {
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
        this.trainingType = trainingType;
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

    public TrainingTypeEnum getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingTypeEnum trainingType) {
        this.trainingType = trainingType;
    }
}
