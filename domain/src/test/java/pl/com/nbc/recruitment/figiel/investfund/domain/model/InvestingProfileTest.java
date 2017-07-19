package pl.com.nbc.recruitment.figiel.investfund.domain.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lukaszf on 2017-07-18.
 */
public class InvestingProfileTest {
    @Test
    public void testVerifyPercentAssignmetns(){
        InvestingProfile entity = new InvestingProfile();
        boolean result = entity.verifyPercentAssignmetns();
        assertFalse(result);

        entity.setForeignFundPercentAssignment(50);
        result = entity.verifyPercentAssignmetns();
        assertFalse(result);

        entity.setPolishFundPercentAssignment(20);
        result = entity.verifyPercentAssignmetns();
        assertFalse(result);

        entity.setMoneyFundPercentAssignment(30);
        result = entity.verifyPercentAssignmetns();
        assertTrue(result);

        entity.setMoneyFundPercentAssignment(30.001);
        result = entity.verifyPercentAssignmetns();
        assertFalse(result);

    }

}