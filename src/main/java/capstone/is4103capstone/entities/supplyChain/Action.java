package capstone.is4103capstone.entities.supplyChain;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.util.enums.ActionStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Action extends DBEntityTemplate {
    private String actionDescription;
    private ActionStatusEnum actionStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_assignee")
    @JsonIgnore
    private Employee assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_creator")
    @JsonIgnore
    private Employee creator;

    public Action(String actionDescription, ActionStatusEnum actionStatus, Employee assignee, Employee creator) {
        this.actionDescription = actionDescription;
        this.actionStatus = actionStatus;
        this.assignee = assignee;
        this.creator = creator;
    }

    public Action() {
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public ActionStatusEnum getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(ActionStatusEnum actionStatus) {
        this.actionStatus = actionStatus;
    }

    public Employee getAssignee() {
        return assignee;
    }

    public void setAssignee(Employee assignee) {
        this.assignee = assignee;
    }

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }
}
