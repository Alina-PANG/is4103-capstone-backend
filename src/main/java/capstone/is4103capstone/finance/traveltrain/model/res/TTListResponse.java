package capstone.is4103capstone.finance.traveltrain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;

public class TTListResponse extends GeneralRes {

    public TTListResponse() {
    }

    public TTListResponse(String message, Boolean hasError) {
        super(message, hasError);
    }
}
