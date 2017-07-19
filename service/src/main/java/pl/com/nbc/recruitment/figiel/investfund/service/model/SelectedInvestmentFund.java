package pl.com.nbc.recruitment.figiel.investfund.service.model;


import java.io.Serializable;

/**
 * Information about selected investemnt found for InvestmentFundCalculator
 * Created by lukaszf on 2017-07-17.
 */
public class SelectedInvestmentFund implements Serializable{
    private static final long serialVersionUID = 3822950338206297982L;

    /** identity of ivestment fund*/
    private String id;

    /** type of investment fund*/
    private InvestmentFundType fundType;

    /** name of investment fund*/
    private String name;

    public SelectedInvestmentFund() {
    }

    public SelectedInvestmentFund(String id, InvestmentFundType fundType, String name) {
        this.id = id;
        this.fundType = fundType;
        this.name = name;
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
}
