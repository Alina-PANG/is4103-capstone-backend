package capstone.is4103capstone.seat.model.seatAllocationRequest;

import capstone.is4103capstone.general.model.ApprovalTicketModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApproveSeatAllocationRequestModel implements Serializable {
    private ApprovalTicketModel approvalTicket;
    private String seatId;

    public ApproveSeatAllocationRequestModel() {
    }

    public ApproveSeatAllocationRequestModel(ApprovalTicketModel approvalTicket, String seatId) {
        this.approvalTicket = approvalTicket;
        this.seatId = seatId;
    }

    public ApprovalTicketModel getApprovalTicket() {
        return approvalTicket;
    }

    public void setApprovalTicket(ApprovalTicketModel approvalTicket) {
        this.approvalTicket = approvalTicket;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
}
