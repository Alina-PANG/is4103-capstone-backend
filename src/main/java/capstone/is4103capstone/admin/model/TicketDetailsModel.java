package capstone.is4103capstone.admin.model;

import java.io.Serializable;

public class TicketDetailsModel implements Serializable {
    private String ticketId;
    private String requesterUsername;
    private String requesterEmail;
    private String commentByRequester;
    private String commentByApprover;
    private String approverUsername;
    private String approverEmail;
    private String approvalStatus;
    private String createdDateTime;
    private String reviewDateTime;
    private Object requestedItem;
    private String description;

    public TicketDetailsModel(String ticketId, String requesterUsername, String requesterEmail, String commentByRequester, String commentByApprover, String approverUsername, String approverEmail) {
        this.ticketId = ticketId;
        this.requesterUsername = requesterUsername;
        this.requesterEmail = requesterEmail;
        this.commentByRequester = commentByRequester;
        this.commentByApprover = commentByApprover;
        this.approverUsername = approverUsername;
        this.approverEmail = approverEmail;
    }

    public TicketDetailsModel(String requesterUsername, String requesterEmail, String commentByRequester, String commentByApprover, String approverUsername, String approverEmail, String approvalStatus, String createdDateTime, String reviewDateTime) {
        this.requesterUsername = requesterUsername;
        this.requesterEmail = requesterEmail;
        this.commentByRequester = commentByRequester;
        this.commentByApprover = commentByApprover;
        this.approverUsername = approverUsername;
        this.approverEmail = approverEmail;
        this.approvalStatus = approvalStatus;
        this.createdDateTime = createdDateTime;
        this.reviewDateTime = reviewDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Object getRequestedItem() {
        return requestedItem;
    }

    public void setRequestedItem(Object requestedItem) {
        this.requestedItem = requestedItem;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }

    public void setRequesterUsername(String requesterUsername) {
        this.requesterUsername = requesterUsername;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }

    public String getCommentByRequester() {
        return commentByRequester;
    }

    public void setCommentByRequester(String commentByRequester) {
        this.commentByRequester = commentByRequester;
    }

    public String getCommentByApprover() {
        return commentByApprover;
    }

    public void setCommentByApprover(String commentByApprover) {
        this.commentByApprover = commentByApprover;
    }

    public String getApproverUsername() {
        return approverUsername;
    }

    public void setApproverUsername(String approverUsername) {
        this.approverUsername = approverUsername;
    }

    public String getApproverEmail() {
        return approverEmail;
    }

    public void setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getReviewDateTime() {
        return reviewDateTime;
    }

    public void setReviewDateTime(String reviewDateTime) {
        this.reviewDateTime = reviewDateTime;
    }
}
