package capstone.is4103capstone.entities;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ApprovalForRequest extends DBEntityTemplate {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    @JsonIgnore
    private Employee approver;//uni-directional

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    @JsonIgnore
    private Employee requester;

    //use approvalType to match entity table, use requestedItemCode to find the actual item;
    /*
        e.g., request for a serviceItem:
        approvalType will be BJFBUSINESS,
        requestedItemCode will be the code for that BJF entity.
     */
    private ApprovalTypeEnum approvalType;

    private String requestedItemId;

    private String commentByApprover = "";

    private String commentByRequester = "";

    private ApprovalStatusEnum approvalStatus = ApprovalStatusEnum.PENDING;

    public ApprovalForRequest() {
    }

    public ApprovalForRequest(String ticketCode, ApprovalTypeEnum approvalType, String requestedItemId, String commentByRequester) {
        super(ticketCode);
        this.approvalType = approvalType;
        this.requestedItemId = requestedItemId;
        this.commentByRequester = commentByRequester;
    }

    public ApprovalForRequest(Employee requester, Employee approver, ApprovalTypeEnum approvalType, String requestedItemId) {
        this.requester = requester;
        this.approver = approver;
        this.approvalType = approvalType;
        this.requestedItemId = requestedItemId;
    }

    public Employee getRequester() {
        return requester;
    }

    public void setRequester(Employee requester) {
        this.requester = requester;
    }

    public String getCommentByRequester() {
        return commentByRequester;
    }

    public void setCommentByRequester(String commentByRequester) {
        this.commentByRequester = commentByRequester;
    }

    public ApprovalStatusEnum getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatusEnum approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Employee getApprover() {
        return approver;
    }

    public void setApprover(Employee approver) {
        this.approver = approver;
    }

    public ApprovalTypeEnum getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(ApprovalTypeEnum approvalType) {
        this.approvalType = approvalType;
    }

    public String getRequestedItemId() {
        return requestedItemId;
    }

    public void setRequestedItemId(String requestedItemCode) {
        this.requestedItemId = requestedItemCode;
    }

    public String getCommentByApprover() {
        return commentByApprover;
    }

    public void setCommentByApprover(String commentByApprover) {
        this.commentByApprover = commentByApprover;
    }


}

