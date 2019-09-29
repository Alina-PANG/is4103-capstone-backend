package capstone.is4103capstone.admin.model;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.general.model.GeneralEntityModel;

import java.io.Serializable;

public class CostCenterModel implements Serializable {

    private String name;
    private String code;
    private String id;
    private GeneralEntityModel team;
    private GeneralEntityModel costCenterManager;
    private GeneralEntityModel bmApprover;
    private GeneralEntityModel functionApprover;


    public CostCenterModel() {
    }

    public CostCenterModel(String name, String code, String id) {
        this.name = name;
        this.code = code;
        this.id = id;
    }

    public CostCenterModel(String name, String code, String id, GeneralEntityModel team, GeneralEntityModel costCenterManager, GeneralEntityModel bmApprover, GeneralEntityModel functionApprover) {
        this.name = name;
        this.code = code;
        this.id = id;
        this.team = team;
        this.costCenterManager = costCenterManager;
        this.bmApprover = bmApprover;
        this.functionApprover = functionApprover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeneralEntityModel getTeam() {
        return team;
    }

    public void setTeam(GeneralEntityModel team) {
        this.team = team;
    }

    public GeneralEntityModel getCostCenterManager() {
        return costCenterManager;
    }

    public void setCostCenterManager(GeneralEntityModel costCenterManager) {
        this.costCenterManager = costCenterManager;
    }

    public GeneralEntityModel getBmApprover() {
        return bmApprover;
    }

    public void setBmApprover(GeneralEntityModel bmApprover) {
        this.bmApprover = bmApprover;
    }

    public GeneralEntityModel getFunctionApprover() {
        return functionApprover;
    }

    public void setFunctionApprover(GeneralEntityModel functionApprover) {
        this.functionApprover = functionApprover;
    }
}
