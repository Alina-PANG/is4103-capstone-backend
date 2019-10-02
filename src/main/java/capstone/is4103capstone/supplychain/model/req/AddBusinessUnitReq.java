package capstone.is4103capstone.supplychain.model.req;

import java.io.Serializable;

public class AddBusinessUnitReq implements Serializable {
    private String username;
    private String teamId;

    public AddBusinessUnitReq() {
    }

    public AddBusinessUnitReq(String username, String teamId) {
        this.username = username;
        this.teamId = teamId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
