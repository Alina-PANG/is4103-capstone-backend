package capstone.is4103capstone.finance.requestsMgmt.model.res;

import capstone.is4103capstone.finance.requestsMgmt.model.dto.RequestFormModel;
import capstone.is4103capstone.general.model.GeneralRes;

public class TTFormResponse extends GeneralRes {
    RequestFormModel form;

    public TTFormResponse() {
    }

    public TTFormResponse(String message, Boolean hasError, RequestFormModel form) {
        super(message, hasError);
        this.form = form;
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
