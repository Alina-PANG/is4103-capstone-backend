package capstone.is4103capstone.finance.requestsMgmt.model;

import capstone.is4103capstone.entities.finance.TrainingForm;
import capstone.is4103capstone.util.Tools;

public class TrainingModel extends RequestFormModel {

    private String startDate;
    private String endDate;
    private String trainingLocation;
    private String targetAudience; // just briefly descripted;
    private String trainingTitle;
    private String trainerName;
    private String trainerEmail;
    private String trainerCompany;// if internal: empty
    private String trainingType;

    public TrainingModel() {
    }

    public TrainingModel(TrainingForm e) {
        super(e);
              setStartDate(Tools.dateFormatter.format(e.getStartDate()));
        setEndDate(Tools.dateFormatter.format(e.getEndDate()));
        setTrainingLocation(e.getTrainingLocation());
        setTargetAudience(e.getTargetAudience());

        setTrainingTitle(e.getTrainingTitle());
        setTrainingType(e.getTrainingType().name());

        setTrainerCompany(e.getTrainerCompany());
        setTrainerEmail(e.getTrainerEmail());
        setTrainerName(e.getTrainerName());
    }

    public TrainingModel(String startDate, String endDate, String trainingLocation, String targetAudience, String trainingTitle, String trainerName, String trainerEmail, String trainerCompany, String trainingType) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTrainingLocation() {
        return trainingLocation;
    }

    public void setTrainingLocation(String trainingLocation) {
        this.trainingLocation = trainingLocation;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
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

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }
}
