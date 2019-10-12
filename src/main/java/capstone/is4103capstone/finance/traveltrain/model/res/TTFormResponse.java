package capstone.is4103capstone.finance.traveltrain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;

public class TTFormResponse extends GeneralRes {

    public TTFormResponse() {
    }

    public TTFormResponse(String message, Boolean hasError) {
        super(message, hasError);
    }
}
