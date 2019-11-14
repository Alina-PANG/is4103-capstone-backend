package capstone.is4103capstone.finance.budget.model.res;

import capstone.is4103capstone.general.model.GeneralRes;

import java.math.BigDecimal;

public class BudgetDashboardRes extends GeneralRes {
    private BigDecimal year1; //2017
    private BigDecimal year2; //2018
    private BigDecimal year3; //2019
    private BigDecimal year4; //2020

    public BudgetDashboardRes() {
    }

    public BudgetDashboardRes(String message, Boolean hasError, BigDecimal year1, BigDecimal year2, BigDecimal year3, BigDecimal year4) {
        super(message, hasError);
        this.year1 = year1;
        this.year2 = year2;
        this.year3 = year3;
        this.year4 = year4;
    }

    public BigDecimal getYear1() {
        return year1;
    }

    public void setYear1(BigDecimal year1) {
        this.year1 = year1;
    }

    public BigDecimal getYear2() {
        return year2;
    }

    public void setYear2(BigDecimal year2) {
        this.year2 = year2;
    }

    public BigDecimal getYear3() {
        return year3;
    }

    public void setYear3(BigDecimal year3) {
        this.year3 = year3;
    }

    public BigDecimal getYear4() {
        return year4;
    }

    public void setYear4(BigDecimal year4) {
        this.year4 = year4;
    }
}
