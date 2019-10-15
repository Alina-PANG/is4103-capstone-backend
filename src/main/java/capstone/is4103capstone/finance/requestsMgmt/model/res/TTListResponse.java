package capstone.is4103capstone.finance.requestsMgmt.model.res;

import capstone.is4103capstone.finance.requestsMgmt.model.dto.ProjectModel;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.RequestFormModel;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.TrainingModel;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.TravelModel;
import capstone.is4103capstone.general.model.GeneralRes;

import java.util.ArrayList;
import java.util.List;

public class TTListResponse extends GeneralRes {
    List<? extends RequestFormModel> results = new ArrayList<>();


    public TTListResponse() {
    }

    public TTListResponse(String message, Boolean hasError) {
        super(message, hasError);
    }

    public TTListResponse(String message, Boolean hasError, List<? extends RequestFormModel> forms) {
        super(message, hasError);
        this.results = forms;
    }

    public List<? extends RequestFormModel> getResults() {
        return results;
    }

    public void setResults(List<? extends RequestFormModel> results) {
        this.results = results;
    }
}
