package capstone.is4103capstone.admin.controller.model.res;

import capstone.is4103capstone.admin.dto.CurrencyDto;
import capstone.is4103capstone.general.model.GeneralRes;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY) // don't serialize nulls since there are optional classes
public class CurrencyRes extends GeneralRes {

    public Integer totalRecords;
    private Optional<List<CurrencyDto>> results;

    public CurrencyRes() {
    }

    public CurrencyRes(String message, Boolean hasError, Optional<List<CurrencyDto>> results) {
        super(message, hasError);
        this.results = results;
        results.ifPresent(obj -> {
            totalRecords = obj.size();
        });
    }
}
