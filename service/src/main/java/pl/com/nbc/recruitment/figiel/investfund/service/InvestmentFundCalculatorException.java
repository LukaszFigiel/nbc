package pl.com.nbc.recruitment.figiel.investfund.service;

/**
 * Created by lukaszf on 2017-07-18.
 */
public class InvestmentFundCalculatorException extends Exception {

    public InvestmentFundCalculatorException() {
    }

    public InvestmentFundCalculatorException(String message) {
        super(message);
    }

    public InvestmentFundCalculatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvestmentFundCalculatorException(Throwable cause) {
        super(cause);
    }

    public InvestmentFundCalculatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
