package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.PendingApprovalTicketModel;

import java.io.Serializable;
import java.util.List;

public class GetPendingApprovalTicketsRes extends GeneralRes implements Serializable {
    private List<PendingApprovalTicketModel> approvalTicketModelList;

    public GetPendingApprovalTicketsRes() {
    }

    public GetPendingApprovalTicketsRes(String message, Boolean hasError, List<PendingApprovalTicketModel> approvalTicketModelList) {
        super(message, hasError);
        this.approvalTicketModelList = approvalTicketModelList;
    }

    public List<PendingApprovalTicketModel> getApprovalTicketModelList() {
        return approvalTicketModelList;
    }

    public void setApprovalTicketModelList(List<PendingApprovalTicketModel> approvalTicketModelList) {
        this.approvalTicketModelList = approvalTicketModelList;
    }
}
