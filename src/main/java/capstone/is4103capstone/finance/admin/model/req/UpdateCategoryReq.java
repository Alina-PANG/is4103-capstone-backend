package capstone.is4103capstone.finance.admin.model.req;

public class UpdateCategoryReq {
    private String code;
    private String newName;
    private String username;

    public UpdateCategoryReq() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
