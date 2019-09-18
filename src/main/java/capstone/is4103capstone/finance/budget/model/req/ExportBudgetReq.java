package capstone.is4103capstone.finance.budget.model.req;

import java.util.ArrayList;
import java.util.List;

public class ExportBudgetReq {
    private String[] cols; // e.g. {"Country", "Category", "Item", "Amount"}
    private ArrayList<ArrayList<String>> colsRestriction; // e.g. {{"SG", "US"}, "", "", ""} only get the items from SG & US
    private String username;
    private ArrayList<ArrayList<String>> colContent;

    public ExportBudgetReq() {
    }


    public ExportBudgetReq(String[] cols, ArrayList<ArrayList<String>> colsRestriction, String username, ArrayList<ArrayList<String>> colContent) {
        this.cols = cols;
        this.colsRestriction = colsRestriction;
        this.username = username;
        this.colContent = colContent;
    }

    public String[] getCols() {
        return cols;
    }

    public void setCols(String[] cols) {
        this.cols = cols;
    }

    public ArrayList<ArrayList<String>> getColsRestriction() {
        return colsRestriction;
    }

    public void setColsRestriction(ArrayList<ArrayList<String>> colsRestriction) {
        this.colsRestriction = colsRestriction;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<ArrayList<String>> getColContent() {
        return colContent;
    }

    public void setColContent(ArrayList<ArrayList<String>> colContent) {
        this.colContent = colContent;
    }
}
