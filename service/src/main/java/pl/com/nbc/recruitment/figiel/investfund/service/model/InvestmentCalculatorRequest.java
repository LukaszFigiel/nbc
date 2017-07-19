package pl.com.nbc.recruitment.figiel.investfund.service.model;

import java.io.Serializable;
import java.util.List;

/**
 *  Request DTO for InvestmentFundCalculator
 * Created by lukaszf on 2017-07-17.
 */
public class InvestmentCalculatorRequest implements Serializable {

    private static final long serialVersionUID = -3205626920928394003L;

    /** Investing profile type*/
    private String investingProfile;

    /** Amount of money that should be assigned to investemnt funds*/
    private int amount;

    private List<SelectedInvestmentFund> selectedInvestmentFunds;

    public InvestmentCalculatorRequest() {
    }

    public InvestmentCalculatorRequest(String investingProfile, int amount, List<SelectedInvestmentFund> selectedInvestmentFunds) {
        this.investingProfile = investingProfile;
        this.amount = amount;
        this.selectedInvestmentFunds = selectedInvestmentFunds;
    }

    public String getInvestingProfile() {
        return investingProfile;
    }

    public void setInvestingProfile(String investingProfile) {
        this.investingProfile = investingProfile;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<SelectedInvestmentFund> getSelectedInvestmentFunds() {
        return selectedInvestmentFunds;
    }

    public void setSelectedInvestmentFunds(List<SelectedInvestmentFund> selectedInvestmentFunds) {
        this.selectedInvestmentFunds = selectedInvestmentFunds;
    }
}
