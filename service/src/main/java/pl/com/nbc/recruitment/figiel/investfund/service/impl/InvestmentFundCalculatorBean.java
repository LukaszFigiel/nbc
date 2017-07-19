package pl.com.nbc.recruitment.figiel.investfund.service.impl;

import pl.com.nbc.recruitment.figiel.investfund.domain.model.InvestingProfile;
import pl.com.nbc.recruitment.figiel.investfund.repo.InvestingProfileRepo;
import pl.com.nbc.recruitment.figiel.investfund.service.InvestmentFundCalculator;
import pl.com.nbc.recruitment.figiel.investfund.service.InvestmentFundCalculatorException;
import pl.com.nbc.recruitment.figiel.investfund.service.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszf on 2017-07-18.
 */
@Stateless
@WebService(serviceName = "InvestmentFundCalculator", portName = "InvestmentFundCalculatorPort", targetNamespace = "http://nbc.com.pl/recruitment/figiel/", name="InvestmentFundCalculator")
public class InvestmentFundCalculatorBean implements InvestmentFundCalculator {

    @Inject
    InvestingProfileRepo investingProfileRepo;

    @Override
    public InvestmentCalculatorResponse assignAmountToInvestmentFunds(@WebParam(name = "request") InvestmentCalculatorRequest request) throws InvestmentFundCalculatorException{

        InvestingProfile profile = investingProfileRepo.getByType(request.getInvestingProfile());
        if (profile == null) {
            throw new InvestmentFundCalculatorException("Investing profile: " + request.getInvestingProfile() + " not found" );
        }

        if (request.getSelectedInvestmentFunds() == null || request.getSelectedInvestmentFunds().size() == 0) {
            throw new InvestmentFundCalculatorException("None investment funds specified" );
        }

        InvestmentCalculatorResponse response = new InvestmentCalculatorResponse();

        List<SelectedInvestmentFund> polishFunds = getFundsByType( request.getSelectedInvestmentFunds(), InvestmentFundType.POLISH);
        List<SelectedInvestmentFund> foreignFunds = getFundsByType( request.getSelectedInvestmentFunds(), InvestmentFundType.FOREIGN);
        List<SelectedInvestmentFund> moneyFunds = getFundsByType( request.getSelectedInvestmentFunds(), InvestmentFundType.MONEY);

        if (profile.getPolishFundPercentAssignment() > 0 && polishFunds.size() == 0) {
            throw new InvestmentFundCalculatorException("None of polish funds specified");
        }
        if (profile.getForeignFundPercentAssignment() > 0 && foreignFunds.size() == 0) {
            throw new InvestmentFundCalculatorException("None of foreign funds specified");
        }
        if (profile.getMoneyFundPercentAssignment() > 0 && moneyFunds.size() == 0) {
            throw new InvestmentFundCalculatorException("None of money funds specified");
        }

        response.getAssignedInvestmentFunds().addAll(assignForFundType(request.getAmount(), polishFunds, profile.getPolishFundPercentAssignment()));
        response.getAssignedInvestmentFunds().addAll(assignForFundType(request.getAmount(), foreignFunds, profile.getForeignFundPercentAssignment()));
        response.getAssignedInvestmentFunds().addAll(assignForFundType(request.getAmount(), moneyFunds, profile.getMoneyFundPercentAssignment()));

        //setting percentage of usage and rest
        int assignedAmount = 0;
        for(AssignedInvestmentFund assign : response.getAssignedInvestmentFunds()) {
            assignedAmount += assign.getAmount();
        }
        response.setAmountRest(request.getAmount() - assignedAmount);
        for(AssignedInvestmentFund assign : response.getAssignedInvestmentFunds()) {
            double percent = (double)assign.getAmount() / assignedAmount * 100;
            percent = new BigDecimal(percent).setScale(2, RoundingMode.HALF_UP).doubleValue();
            assign.setAssignmentPercent(percent);
        }

        return response;
    }

    List<AssignedInvestmentFund> assignForFundType(int totalAmmount,  List<SelectedInvestmentFund> selectedFunds, double typePercentAssign) {
        List<AssignedInvestmentFund> assignedFunds = new ArrayList<>();
        if (typePercentAssign != 0) {

            int fundTypeAmount = calculateAmountForFundType(totalAmmount, typePercentAssign);
            int fundAmount = (fundTypeAmount / selectedFunds.size());
            selectedFunds.stream().forEach(fund -> {
                AssignedInvestmentFund assigned = new AssignedInvestmentFund(fund.getId(), fund.getFundType(), fund.getName(), fundAmount);
                assignedFunds.add(assigned);
            });

            //adding rest to first fund
            if (fundAmount * selectedFunds.size() != fundTypeAmount) {
                AssignedInvestmentFund firstFund = assignedFunds.stream().findFirst().orElse(null);
                firstFund.setAmount(fundAmount + (fundTypeAmount - (fundAmount * selectedFunds.size())));
            }
        }

        return assignedFunds;
    }

    int calculateAmountForFundType(int totalAmmount, double percentage) {
        int result = (int)(totalAmmount * (percentage / 100));

        return result;
    }

    List<SelectedInvestmentFund> getFundsByType(List<SelectedInvestmentFund> input, final InvestmentFundType type) {
        List<SelectedInvestmentFund> funds = new ArrayList<>();
        input.stream().filter(fund -> fund.getFundType() == type).forEach(fund -> funds.add(fund));
        return funds;
    }
}
