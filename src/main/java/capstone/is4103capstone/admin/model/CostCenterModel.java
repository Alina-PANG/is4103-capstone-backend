package capstone.is4103capstone.admin.model;

import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.CostCenter;
import capstone.is4103capstone.general.model.GeneralEntityModel;
import capstone.is4103capstone.seat.model.EmployeeModel;

import java.io.Serializable;

public class CostCenterModel implements Serializable {

    private String name;
    private String code;
    private String id;
    private GeneralEntityModel team;
    private EmployeeModel costCenterManager;
    private EmployeeModel bmApprover;
    private EmployeeModel functionApprover;
    private GeneralEntityModel country;
    private GeneralEntityModel businessUnit;
    private GeneralEntityModel function;

    public GeneralEntityModel getCountry() {
        return country;
    }

    public void setCountry(GeneralEntityModel country) {
        this.country = country;
    }

    public CostCenterModel() {
    }

    public CostCenterModel(String name, String code, String id, GeneralEntityModel team, EmployeeModel costCenterManager, EmployeeModel bmApprover, EmployeeModel functionApprover, GeneralEntityModel country, GeneralEntityModel businessUnit, GeneralEntityModel function) {
        this.name = name;
        this.code = code;
        this.id = id;
        this.team = team;
        this.costCenterManager = costCenterManager;
        this.bmApprover = bmApprover;
        this.functionApprover = functionApprover;
        this.country = country;
        this.businessUnit = businessUnit;
        this.function = function;
    }

    public CostCenterModel(CostCenter c) {
        setName(c.getObjectName());
        setCode(c.getCode());
        setCostCenterManager(c.getCostCenterManager() == null? null: new EmployeeModel(c.getCostCenterManager()));
        setBmApprover(c.getBmApprover() == null? null : new EmployeeModel(c.getBmApprover()));
        setFunctionApprover(c.getFunctionApprover() == null? null: new EmployeeModel(c.getFunctionApprover()));
        setTeam(c.getTeam() == null? null : new GeneralEntityModel(c.getTeam()));
        setCountry(c.getTeam() == null? null : new GeneralEntityModel(c.getTeam().getOffice().getCountry()));
        setBusinessUnit(c.getTeam() == null? null : new GeneralEntityModel(c.getTeam().getBusinessUnit()));
    }

    public GeneralEntityModel getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(GeneralEntityModel businessUnit) {
        this.businessUnit = businessUnit;
    }

    public GeneralEntityModel getFunction() {
        return function;
    }

    public void setFunction(GeneralEntityModel function) {
        this.function = function;
    }

    public CostCenterModel(String name, String code, String id) {
        this.name = name;
        this.code = code;
        this.id = id;
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

    public EmployeeModel getCostCenterManager() {
        return costCenterManager;
    }

    public void setCostCenterManager(EmployeeModel costCenterManager) {
        this.costCenterManager = costCenterManager;
    }

    public EmployeeModel getBmApprover() {
        return bmApprover;
    }

    public void setBmApprover(EmployeeModel bmApprover) {
        this.bmApprover = bmApprover;
    }

    public EmployeeModel getFunctionApprover() {
        return functionApprover;
    }

    public void setFunctionApprover(EmployeeModel functionApprover) {
        this.functionApprover = functionApprover;
    }
}
