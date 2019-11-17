package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.PendingApprovalTicketModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class GetPendingApprovalTicketsRes extends GeneralRes implements Serializable {
    private List<PendingApprovalTicketModel> approvalTicketModelList;
    private BigDecimal contract;
    private BigDecimal budgetPlan;
    private BigDecimal travel;
    private BigDecimal training;
    private BigDecimal project;
    private BigDecimal bjf;
    private BigDecimal outsourcing_assessment_form;
    private BigDecimal seat_allocation;
    private BigDecimal outsourcing_self_assessment;

    public GetPendingApprovalTicketsRes() {
    }

    public GetPendingApprovalTicketsRes(String message, Boolean hasError, List<PendingApprovalTicketModel> approvalTicketModelList, BigDecimal contract, BigDecimal budgetPlan, BigDecimal travel, BigDecimal training, BigDecimal project, BigDecimal bjf, BigDecimal outsourcing_assessment_form, BigDecimal seat_allocation, BigDecimal outsourcing_self_assessment) {
        super(message, hasError);
        this.approvalTicketModelList = approvalTicketModelList;
        this.contract = contract;
        this.budgetPlan = budgetPlan;
        this.travel = travel;
        this.training = training;
        this.project = project;
        this.bjf = bjf;
        this.outsourcing_assessment_form = outsourcing_assessment_form;
        this.seat_allocation = seat_allocation;
        this.outsourcing_self_assessment = outsourcing_self_assessment;
    }

    public List<PendingApprovalTicketModel> getApprovalTicketModelList() {
        return approvalTicketModelList;
    }

    public void setApprovalTicketModelList(List<PendingApprovalTicketModel> approvalTicketModelList) {
        this.approvalTicketModelList = approvalTicketModelList;
    }

    public BigDecimal getContract() {
        return contract;
    }

    public void setContract(BigDecimal contract) {
        this.contract = contract;
    }

    public BigDecimal getBudgetPlan() {
        return budgetPlan;
    }

    public void setBudgetPlan(BigDecimal budgetPlan) {
        this.budgetPlan = budgetPlan;
    }

    public BigDecimal getTravel() {
        return travel;
    }

    public void setTravel(BigDecimal travel) {
        this.travel = travel;
    }

    public BigDecimal getTraining() {
        return training;
    }

    public void setTraining(BigDecimal training) {
        this.training = training;
    }

    public BigDecimal getProject() {
        return project;
    }

    public void setProject(BigDecimal project) {
        this.project = project;
    }

    public BigDecimal getBjf() {
        return bjf;
    }

    public void setBjf(BigDecimal bjf) {
        this.bjf = bjf;
    }

    public BigDecimal getOutsourcing_assessment_form() {
        return outsourcing_assessment_form;
    }

    public void setOutsourcing_assessment_form(BigDecimal outsourcing_assessment_form) {
        this.outsourcing_assessment_form = outsourcing_assessment_form;
    }

    public BigDecimal getSeat_allocation() {
        return seat_allocation;
    }

    public void setSeat_allocation(BigDecimal seat_allocation) {
        this.seat_allocation = seat_allocation;
    }

    public BigDecimal getOutsourcing_self_assessment() {
        return outsourcing_self_assessment;
    }

    public void setOutsourcing_self_assessment(BigDecimal outsourcing_self_assessment) {
        this.outsourcing_self_assessment = outsourcing_self_assessment;
    }
}

