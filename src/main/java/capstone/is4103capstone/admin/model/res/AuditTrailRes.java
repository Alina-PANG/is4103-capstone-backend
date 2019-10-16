package capstone.is4103capstone.admin.model.res;

import capstone.is4103capstone.admin.dto.AuditTrailActivityDto;
import capstone.is4103capstone.admin.model.AuditTrailModel;
import capstone.is4103capstone.general.model.GeneralRes;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY) // don't serialize nulls since there are optional classes
public class AuditTrailRes extends GeneralRes {

    public Integer totalRecords;
    private Optional<List<AuditTrailActivityDto>> results;
    private List<AuditTrailModel> trails;
    public AuditTrailRes() {
    }

    public AuditTrailRes(String message, Boolean hasError, Optional<List<AuditTrailActivityDto>> results) {
        super(message, hasError);
        this.results = results;
        results.ifPresent(obj -> {
            totalRecords = obj.size();
        });
    }

    public AuditTrailRes(String message, Boolean hasError, List<AuditTrailModel> trails) {
        super(message, hasError);
        this.trails = trails;
    }

    public AuditTrailRes(String message, Boolean hasError) {
        super(message, hasError);
    }
}
