package capstone.is4103capstone.admin.dto;

import capstone.is4103capstone.util.enums.OperationTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
//@JsonInclude(JsonInclude.Include.NON_EMPTY) // don't serialize nulls since there are optional classes
public class AuditTrailActivityDto implements Serializable {

    private Optional<Integer> id = Optional.empty();
    private Optional<String> userUuid = Optional.empty();
    private Optional<String> username = Optional.empty();
    private Optional<String> activity = Optional.empty();
    private Optional<String> timeStamp = Optional.empty();
    private Optional<String> modifiedObjectUuid = Optional.empty();
    private Optional<String> modifiedObjectType = Optional.empty();
    private Optional<OperationTypeEnum> operationTypeEnum = Optional.empty();

}
