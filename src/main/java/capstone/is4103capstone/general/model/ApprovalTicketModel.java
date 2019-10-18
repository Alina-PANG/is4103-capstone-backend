package capstone.is4103capstone.general.model;

import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApprovalTicketModel implements Serializable {
    private String reviewerUsername;
    private String requestedItemId;
    private String approverComment;
    private String requesterComment;
    private String createdDateTime;
    private String reviewedDateTime;
    private ApprovalStatusEnum ticketResult;
    private ApprovalTypeEnum requestType;

    private final SimpleDateFormat datetimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public ApprovalTicketModel() {
    }

    public ApprovalTicketModel(String reviewerUsername, String requestedItemId, String approverComment, String requesterComment, String createdDateTime, String reviewedDateTime, ApprovalStatusEnum ticketResult, ApprovalTypeEnum requestType) {
        this.reviewerUsername = reviewerUsername;
        this.requestedItemId = requestedItemId;
        this.approverComment = approverComment;
        this.requesterComment = requesterComment;
        this.createdDateTime = createdDateTime;
        this.reviewedDateTime = reviewedDateTime;
        this.ticketResult = ticketResult;
        this.requestType = requestType;
    }

    public ApprovalTicketModel(String reviewerUsername, String comment, Date createdDateTime, Date reviewedDateTime, ApprovalStatusEnum ticketResult) {
        this.reviewerUsername = reviewerUsername;
        this.approverComment = comment;
        this.createdDateTime = datetimeFormatter.format(createdDateTime);
        this.reviewedDateTime = datetimeFormatter.format(reviewedDateTime);
        this.ticketResult = ticketResult;
    }

    public String getRequesterComment() {
        return requesterComment;
    }

    public void setRequesterComment(String requesterComment) {
        this.requesterComment = requesterComment;
    }

    public String getRequestedItemId() {
        return requestedItemId;
    }

    public void setRequestedItemId(String requestedItemId) {
        this.requestedItemId = requestedItemId;
    }

    public ApprovalTypeEnum getRequestType() {
        return requestType;
    }

    public void setRequestType(ApprovalTypeEnum requestType) {
        this.requestType = requestType;
    }

    public String getReviewerUsername() {
        return reviewerUsername;
    }

    public void setReviewerUsername(String reviewerUsername) {
        this.reviewerUsername = reviewerUsername;
    }

    public String getApproverComment() {
        return approverComment;
    }

    public void setApproverComment(String approverComment) {
        this.approverComment = approverComment;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getReviewedDateTime() {
        return reviewedDateTime;
    }

    public void setReviewedDateTime(String reviewedDateTime) {
        this.reviewedDateTime = reviewedDateTime;
    }

    public ApprovalStatusEnum getTicketResult() {
        return ticketResult;
    }

    public void setTicketResult(ApprovalStatusEnum ticketResult) {
        this.ticketResult = ticketResult;
    }


}
