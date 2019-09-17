package capstone.is4103capstone.scm.actionTracking.model.res;

import capstone.is4103capstone.general.model.GeneralRes;

public class CreateActionTrackingRes extends GeneralRes {
    private String someSpecificFeature;
    public CreateActionTrackingRes() {
    }

    public CreateActionTrackingRes(String message, Boolean error, String someSpecificFeature) {
        super(message, error);
        this.someSpecificFeature = someSpecificFeature;
    }

    public String getSomeSpecificFeature() {
        return someSpecificFeature;
    }

    public void setSomeSpecificFeature(String someSpecificFeature) {
        this.someSpecificFeature = someSpecificFeature;
    }
}
