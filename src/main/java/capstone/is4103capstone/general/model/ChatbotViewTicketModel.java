package capstone.is4103capstone.general.model;

import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.RequestFormModel;
import capstone.is4103capstone.seat.model.EmployeeModel;

import java.io.Serializable;

public class ChatbotViewTicketModel implements Serializable {

    private RequestFormModel requestDetails;
    private EmployeeModel requester;
    private ApprovalTicketModel ticket;

    public ChatbotViewTicketModel() {
    }

    public ChatbotViewTicketModel(RequestFormModel requestDetails, Employee requester, ApprovalForRequest ticket) {
        this.requestDetails = requestDetails;
        setRequester(new EmployeeModel(requester));
        setTicket(new ApprovalTicketModel(ticket));
    }

    public RequestFormModel getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(RequestFormModel requestDetails) {
        this.requestDetails = requestDetails;
    }

    public EmployeeModel getRequester() {
        return requester;
    }

    public void setRequester(EmployeeModel requester) {
        this.requester = requester;
    }

    public ApprovalTicketModel getTicket() {
        return ticket;
    }

    public void setTicket(ApprovalTicketModel ticket) {
        this.ticket = ticket;
    }
}
