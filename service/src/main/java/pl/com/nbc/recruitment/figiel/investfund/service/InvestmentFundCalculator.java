package pl.com.nbc.recruitment.figiel.investfund.service;

import pl.com.nbc.recruitment.figiel.investfund.service.model.InvestmentCalculatorRequest;
import pl.com.nbc.recruitment.figiel.investfund.service.model.InvestmentCalculatorResponse;

import javax.ejb.Local;
import javax.jws.WebService;

/**
 * Service to calculate amount for declared investment funds in specified investment profiles
 * Created by lukaszf on 2017-07-17.
 */
@Local
@WebService(name = "InvestmentFundCalculator", targetNamespace = "http://nbc.com.pl/recruitment/figiel/")
public interface InvestmentFundCalculator {

    /** Assign given amount for given investment funds in specified investing profile */
    InvestmentCalculatorResponse assignAmountToInvestmentFunds(InvestmentCalculatorRequest investmentCalculatorRequest) throws InvestmentFundCalculatorException;
}
