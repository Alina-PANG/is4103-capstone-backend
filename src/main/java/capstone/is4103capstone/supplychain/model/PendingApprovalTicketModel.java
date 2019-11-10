package capstone.is4103capstone.supplychain.model;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.util.Tools;

import java.io.Serializable;

public class PendingApprovalTicketModel implements Serializable {
    private GeneralEntityModel item;
    private String entityName;
    private String ticketCode;
    private String commentByRequester;
    private EmployeeModel requester;
    private String ticketCreatedDateTime;

    public PendingApprovalTicketModel() {
    }

    public PendingApprovalTicketModel(DBEntityTemplate entity, ApprovalForRequest ticket){
        setCommentByRequester(ticket.getCommentByRequester());
        setTicketCode(ticket.getCode());
        setEntityName(entity.getClass().getSimpleName());
        setItem(new GeneralEntityModel(entity));
        setRequester(new EmployeeModel(ticket.getRequester()));
        setTicketCreatedDateTime(Tools.datetimeFormatter.format(ticket.getCreatedDateTime()));
    }
    public GeneralEntityModel getItem() {
        return item;
    }

    public void setItem(GeneralEntityModel item) {
        this.item = item;
    }

    public String getTicketCreatedDateTime() {
        return ticketCreatedDateTime;
    }

    public void setTicketCreatedDateTime(String ticketCreatedDateTime) {
        this.ticketCreatedDateTime = ticketCreatedDateTime;
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

    public EmployeeModel getRequester() {
        return requester;
    }

    public void setRequester(EmployeeModel requester) {
        this.requester = requester;
    }
}
