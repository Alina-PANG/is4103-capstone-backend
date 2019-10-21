package capstone.is4103capstone.finance.requestsMgmt.model.res;

import capstone.is4103capstone.general.model.GeneralRes;

public class BjfAnalysisResponse extends GeneralRes {


    public BjfAnalysisResponse() {
    }

    public BjfAnalysisResponse(String message, Boolean hasError) {
        super(message, hasError);
    }
}
