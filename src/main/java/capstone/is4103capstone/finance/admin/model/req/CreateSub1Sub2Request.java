package capstone.is4103capstone.finance.admin.model.req;

public class CreateSub1Sub2Request {
    String upperCategoryCode;
    String name;
    String username;

    public CreateSub1Sub2Request() {
    }

    public CreateSub1Sub2Request(String upperCategoryCode, String name, String username) {
        this.upperCategoryCode = upperCategoryCode;
        this.name = name;
        this.username = username;
    }

    public String getUpperCategoryCode() {
        return upperCategoryCode;
    }

    public void setUpperCategoryCode(String upperCategoryCode) {
        this.upperCategoryCode = upperCategoryCode;
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
