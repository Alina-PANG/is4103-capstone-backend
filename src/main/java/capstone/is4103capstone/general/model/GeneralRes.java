package capstone.is4103capstone.general.model;

import java.io.Serializable;

public class GeneralRes implements Serializable {
    String message;
    Boolean error;

    public GeneralRes() {
    }

    public GeneralRes(String message, Boolean error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}
