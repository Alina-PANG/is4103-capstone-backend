package capstone.is4103capstone.general.model;

import capstone.is4103capstone.util.enums.ApprovalStatusEnum;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApprovalTicketModel implements Serializable {
    private String id;
    private String reviewerUsername;
    private String comment;
    private String createdDateTime;
    private String reviewedDateTime;
    private String ticketResult;

    private final SimpleDateFormat datetimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public ApprovalTicketModel() {
    }

    public ApprovalTicketModel(String id, String reviewerUsername, String comment, Date createdDateTime, Date reviewedDateTime, ApprovalStatusEnum ticketResult) {
        this.id = id;
        this.reviewerUsername = reviewerUsername;
        this.comment = comment;
        this.createdDateTime = datetimeFormatter.format(createdDateTime);
        this.reviewedDateTime = datetimeFormatter.format(reviewedDateTime);
        this.ticketResult = ticketResult.name();
    }
    public ApprovalTicketModel(String reviewerUsername, String comment, String createdDateTime, String reviewedDateTime, String ticketResult) {
        this.id = id;
        this.reviewerUsername = reviewerUsername;
        this.comment = comment;
        this.createdDateTime = createdDateTime;
        this.reviewedDateTime = reviewedDateTime;
        this.ticketResult = ticketResult;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReviewerUsername() {
        return reviewerUsername;
    }

    public void setReviewerUsername(String reviewerUsername) {
        this.reviewerUsername = reviewerUsername;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getTicketResult() {
        return ticketResult;
    }

    public void setTicketResult(String ticketResult) {
        this.ticketResult = ticketResult;
    }
}
