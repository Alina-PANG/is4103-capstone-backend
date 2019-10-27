package capstone.is4103capstone.finance.requestsMgmt.model.res;

import capstone.is4103capstone.finance.requestsMgmt.model.dto.*;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.ArrayList;
import java.util.List;

public class TTListResponse extends GeneralRes {
    List<? extends Object> results = new ArrayList<>();


    public TTListResponse() {
    }

    public TTListResponse(String message, Boolean hasError) {
        super(message, hasError);
    }

    public TTListResponse(String message, Boolean hasError, List<? extends RequestFormModel> forms) {
        super(message, hasError);
        this.results = forms;
    }

    public TTListResponse(String message, List<BJFAggregateModel> forms) {
        super(message, false);
        this.results = forms;
    }

    public List<? extends Object> getResults() {
        return results;
    }

    public void setResults(List<? extends Object> results) {
        this.results = results;
    }
}
