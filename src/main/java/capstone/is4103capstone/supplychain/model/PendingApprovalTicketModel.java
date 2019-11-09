package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.general.model.GeneralEntityModel;

import java.io.Serializable;

public class PendingApprovalTicketModel implements Serializable {
    private String itemId;
    private String itemCode;
    private String entityName;
    private String ticketCode;
    private String commentByRequester;
    private GeneralEntityModel requester;

    public PendingApprovalTicketModel() {
    }

    public PendingApprovalTicketModel(String itemId, String itemCode, String entityName, String ticketCode, String commentByRequester, GeneralEntityModel requester) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.entityName = entityName;
        this.ticketCode = ticketCode;
        this.commentByRequester = commentByRequester;
        this.requester = requester;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getCommentByRequester() {
        return commentByRequester;
    }

    public void setCommentByRequester(String commentByRequester) {
        this.commentByRequester = commentByRequester;
    }

    public GeneralEntityModel getRequester() {
        return requester;
    }

    public void setRequester(GeneralEntityModel requester) {
        this.requester = requester;
    }
}
