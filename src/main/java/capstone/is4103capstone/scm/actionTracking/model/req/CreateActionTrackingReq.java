package capstone.is4103capstone.scm.actionTracking.model.req;

public class CreateActionTrackingReq {
    private String username;

    public CreateActionTrackingReq(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
