package capstone.is4103capstone.general.model;

public class FilePathReq {
    String filePath;
    String username;

    public FilePathReq() {
    }

    public FilePathReq(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
