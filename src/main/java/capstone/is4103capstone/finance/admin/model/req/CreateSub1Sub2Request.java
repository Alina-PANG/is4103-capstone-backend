package capstone.is4103capstone.finance.admin.model.req;

public class CreateSub1Sub2Request {
    String upperLvlCode;
    String name;
    String username;

    public CreateSub1Sub2Request() {
    }

    public CreateSub1Sub2Request(String upperLvlCode, String name, String username) {
        this.upperLvlCode = upperLvlCode;
        this.name = name;
        this.username = username;
    }

    public String getUpperLvlCode() {
        return upperLvlCode;
    }

    public void setUpperLvlCode(String upperLvlCode) {
        this.upperLvlCode = upperLvlCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
