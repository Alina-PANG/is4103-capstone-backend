package capstone.is4103capstone.finance.requestsMgmt.model.res;

import capstone.is4103capstone.finance.requestsMgmt.model.dto.RequestFormModel;
import capstone.is4103capstone.general.model.GeneralRes;

public class TTFormResponse extends GeneralRes {
    RequestFormModel form;
    Boolean userHasRightToApprove;


    public TTFormResponse() {
    }

    public TTFormResponse(String message, Boolean hasError, RequestFormModel form) {
        super(message, hasError);
        this.form = form;
    }

    public TTFormResponse(String message, Boolean hasError, RequestFormModel form, Boolean userHasRightToApprove) {
        super(message, hasError);
        this.form = form;
        this.userHasRightToApprove = userHasRightToApprove;
    }

    public Boolean getUserHasRightToApprove() {
        return userHasRightToApprove;
    }

    public void setUserHasRightToApprove(Boolean userHasRightToApprove) {
        this.userHasRightToApprove = userHasRightToApprove;
    }

    public TTFormResponse(String message, Boolean hasError) {
        super(message, hasError);
    }

    public RequestFormModel getForm() {
        return form;
    }

    public void setForm(RequestFormModel form) {
        this.form = form;
    }
}
