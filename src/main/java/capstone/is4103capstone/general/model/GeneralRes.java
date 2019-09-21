package capstone.is4103capstone.general.model;

import java.io.Serializable;

public class GeneralRes implements Serializable {
    String message;
    Boolean hasError;

    public GeneralRes() {
    }

    public GeneralRes(String message, Boolean hasError) {
        this.message = message;
        this.hasError = hasError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getHasError() {
        return hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }
}
