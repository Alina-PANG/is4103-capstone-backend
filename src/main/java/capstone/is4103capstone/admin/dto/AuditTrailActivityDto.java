package capstone.is4103capstone.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY) // don't serialize nulls since there are optional classes
public class AuditTrailActivityDto implements Serializable {

    private Optional<Integer> id = Optional.empty();
    private Optional<String> userUuid = Optional.empty();
    private Optional<String> activity = Optional.empty();
    private Optional<String> timeStamp = Optional.empty();

    public Optional<Integer> getId() {
        return id;
    }

    public void setId(Optional<Integer> id) {
        this.id = id;
    }

    public Optional<String> getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(Optional<String> userUuid) {
        this.userUuid = userUuid;
    }

    public Optional<String> getActivity() {
        return activity;
    }

    public void setActivity(Optional<String> activity) {
        this.activity = activity;
    }

    public Optional<String> getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Optional<String> timeStamp) {
        this.timeStamp = timeStamp;
    }
}
