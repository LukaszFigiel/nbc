package pl.com.nbc.recruitment.figiel.investfund.service.model;

import java.io.Serializable;

/**
 * Information about assigned investment found returned by InvestmentFundCalculator
 * Created by lukaszf on 2017-07-17.
 */
public class AssignedInvestmentFund implements Serializable{
    private static final long serialVersionUID = 3822950338206297982L;

    /** identity of ivestment fund*/
    private String id;

    /** type of investment fund*/
    private InvestmentFundType fundType;

    /** name of investment fund*/
    private String name;

    private int amount;

    private double assignmentPercent;

    public AssignedInvestmentFund() {
    }

    public AssignedInvestmentFund(String id, InvestmentFundType fundType, String name, int amount) {
        this.id = id;
        this.fundType = fundType;
        this.name = name;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InvestmentFundType getFundType() {
        return fundType;
    }

    public void setFundType(InvestmentFundType fundType) {
        this.fundType = fundType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getAssignmentPercent() {
        return assignmentPercent;
    }

    public void setAssignmentPercent(double assignmentPercent) {
        this.assignmentPercent = assignmentPercent;
    }
}
