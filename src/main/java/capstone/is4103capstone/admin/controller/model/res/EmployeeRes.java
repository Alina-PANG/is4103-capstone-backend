package capstone.is4103capstone.admin.controller.model.res;

import capstone.is4103capstone.admin.dto.CurrencyDto;
import capstone.is4103capstone.admin.dto.EmployeeDto;
import capstone.is4103capstone.general.model.GeneralRes;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY) // don't serialize nulls since there are optional classes
public class EmployeeRes extends GeneralRes {

    public Integer totalRecords;
    private Optional<List<EmployeeDto>> results;

    public EmployeeRes() {
    }

    public EmployeeRes(String message, Boolean hasError, Optional<List<EmployeeDto>> results) {
        super(message, hasError);
        this.results = results;
        results.ifPresent(obj -> {
            totalRecords = obj.size();
        });
    }
}
