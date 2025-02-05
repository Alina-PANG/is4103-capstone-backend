package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.util.enums.DisputeStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Dispute extends DBEntityTemplate {
    private String disputeDescription;
    private DisputeStatusEnum disputeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispute_handler")
    @JsonIgnore
    private Employee handler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispute_creator")
    @JsonIgnore
    private Employee creator;

    public Dispute(String disputeDescription, DisputeStatusEnum disputeStatus, Employee handler, Employee creator) {
        this.disputeDescription = disputeDescription;
        this.disputeStatus = disputeStatus;
        this.handler = handler;
        this.creator = creator;
    }

    public Dispute() {
    }

    public String getDisputeDescription() {
        return disputeDescription;
    }

    public void setDisputeDescription(String disputeDescription) {
        this.disputeDescription = disputeDescription;
    }

    public DisputeStatusEnum getDisputeStatus() {
        return disputeStatus;
    }

    public void setDisputeStatus(DisputeStatusEnum disputeStatus) {
        this.disputeStatus = disputeStatus;
    }

    public Employee getHandler() {
        return handler;
    }

    public void setHandler(Employee handler) {
        this.handler = handler;
    }

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }
}
