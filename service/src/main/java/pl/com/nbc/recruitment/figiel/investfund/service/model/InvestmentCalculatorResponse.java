package pl.com.nbc.recruitment.figiel.investfund.service.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszf on 2017-07-17.
 */
public class InvestmentCalculatorResponse implements Serializable{

    private static final long serialVersionUID = 3114805586252747609L;

    /** Rest of not assignment amount*/
    private double amountRest;

    private List<AssignedInvestmentFund> assignedInvestmentFunds = new ArrayList<>();

    public double getAmountRest() {
        return amountRest;
    }

    public void setAmountRest(double amountRest) {
        this.amountRest = amountRest;
    }

    public List<AssignedInvestmentFund> getAssignedInvestmentFunds() {
        return assignedInvestmentFunds;
    }

    public void setAssignedInvestmentFunds(List<AssignedInvestmentFund> assignedInvestmentFunds) {
        this.assignedInvestmentFunds = assignedInvestmentFunds;
    }
}
