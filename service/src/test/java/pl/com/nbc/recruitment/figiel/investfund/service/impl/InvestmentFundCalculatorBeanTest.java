package pl.com.nbc.recruitment.figiel.investfund.service.impl;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import pl.com.nbc.recruitment.figiel.investfund.domain.model.*;
import pl.com.nbc.recruitment.figiel.investfund.repo.InvestingProfileRepo;
import pl.com.nbc.recruitment.figiel.investfund.service.InvestmentFundCalculatorException;
import pl.com.nbc.recruitment.figiel.investfund.service.model.*;
import pl.com.nbc.recruitment.figiel.investfund.service.model.InvestmentFundType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszf on 2017-07-18.
 */
public class InvestmentFundCalculatorBeanTest {
    InvestmentFundCalculatorBean bean;

    @Before
    public void prepare() {
        bean = new InvestmentFundCalculatorBean();

        InvestingProfile bezpieczny = new InvestingProfile();
        bezpieczny.setProfileType("bezpieczny");
        bezpieczny.setPolishFundPercentAssignment(20);
        bezpieczny.setForeignFundPercentAssignment(75);
        bezpieczny.setMoneyFundPercentAssignment(5);

        InvestingProfileRepo repo = Mockito.mock(InvestingProfileRepo.class);
        Mockito.when(repo.getByType("bezpieczny")).thenReturn(bezpieczny);

        bean.investingProfileRepo = repo;
    }

    @Test
    public void testExceptionWhenProfileNotFound() {
        boolean error = false;

        InvestmentCalculatorRequest request = new InvestmentCalculatorRequest("nieznany", 100, new ArrayList<SelectedInvestmentFund>());

        try {
            bean.assignAmountToInvestmentFunds(request);
        } catch (InvestmentFundCalculatorException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void testExceptionWhenNoInvestmentFunds() {
        boolean error = false;

        InvestmentCalculatorRequest request = new InvestmentCalculatorRequest("bezpieczny", 100, null);

        try {
            bean.assignAmountToInvestmentFunds(request);
        } catch (InvestmentFundCalculatorException e) {
            error = true;
        }
        assertTrue(error);

        error = false;

        request.setSelectedInvestmentFunds(new ArrayList<>());

        try {
            bean.assignAmountToInvestmentFunds(request);
        } catch (InvestmentFundCalculatorException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void testAssignmentsWithNoRest() throws InvestmentFundCalculatorException {
        InvestmentCalculatorRequest request = createInvestmentCalculatorRequest(10000);

        InvestmentCalculatorResponse response = bean.assignAmountToInvestmentFunds(request);
        assertNotNull(response);
        assertNotNull(response.getAssignedInvestmentFunds());
        assertEquals(0, response.getAmountRest(), 0);

        response.getAssignedInvestmentFunds().stream().filter(assignment -> assignment.getFundType() == InvestmentFundType.POLISH).forEach(assignment -> {
            assertEquals(1000, assignment.getAmount(), 0d);
            assertEquals(10, assignment.getAssignmentPercent(), 0d);
        });

        response.getAssignedInvestmentFunds().stream().filter(assignment -> assignment.getFundType() == InvestmentFundType.FOREIGN).forEach(assignment -> {
            assertEquals(2500, assignment.getAmount(), 0d);
            assertEquals(25, assignment.getAssignmentPercent(), 0d);
        });

        response.getAssignedInvestmentFunds().stream().filter(assignment -> assignment.getFundType() == InvestmentFundType.MONEY).forEach(assignment -> {
            assertEquals(500, assignment.getAmount(), 0d);
            assertEquals(5, assignment.getAssignmentPercent(), 0d);
        });
    }

    @Test
    public void testAssignmentsWithRest() throws InvestmentFundCalculatorException {
        InvestmentCalculatorRequest request = createInvestmentCalculatorRequest(10001);

        InvestmentCalculatorResponse response = bean.assignAmountToInvestmentFunds(request);
        assertNotNull(response);
        assertNotNull(response.getAssignedInvestmentFunds());
        assertEquals(1, response.getAmountRest(), 0);

        response.getAssignedInvestmentFunds().stream().filter(assignment -> assignment.getFundType() == InvestmentFundType.POLISH).forEach(assignment -> {
            assertEquals(1000, assignment.getAmount(), 0d);
            assertEquals(10, assignment.getAssignmentPercent(), 0d);
        });

        response.getAssignedInvestmentFunds().stream().filter(assignment -> assignment.getFundType() == InvestmentFundType.FOREIGN).forEach(assignment -> {
            assertEquals(2500, assignment.getAmount(), 0d);
            assertEquals(25, assignment.getAssignmentPercent(), 0d);
        });

        response.getAssignedInvestmentFunds().stream().filter(assignment -> assignment.getFundType() == InvestmentFundType.MONEY).forEach(assignment -> {
            assertEquals(500, assignment.getAmount(), 0d);
            assertEquals(5, assignment.getAssignmentPercent(), 0d);
        });
    }

    @Test
    public void testAssignmentsWithRestAndUnevenAssignLevel() throws InvestmentFundCalculatorException {
        List<SelectedInvestmentFund> funds = new ArrayList<>(6);
        funds.add(new SelectedInvestmentFund("1", InvestmentFundType.POLISH, "Fundusz Polski 1"));
        funds.add(new SelectedInvestmentFund("2", InvestmentFundType.POLISH, "Fundusz Polski 2"));
        funds.add(new SelectedInvestmentFund("3", InvestmentFundType.POLISH, "Fundusz Polski 3"));
        funds.add(new SelectedInvestmentFund("4", InvestmentFundType.FOREIGN, "Fundusz Zagraniczny 1"));
        funds.add(new SelectedInvestmentFund("5", InvestmentFundType.FOREIGN, "Fundusz Zagraniczny 2"));
        funds.add(new SelectedInvestmentFund("6", InvestmentFundType.MONEY, "Fundusz Pieniezny 1"));

        InvestmentCalculatorRequest request = new InvestmentCalculatorRequest("bezpieczny", 10000, funds);

        InvestmentCalculatorResponse response = bean.assignAmountToInvestmentFunds(request);
        assertNotNull(response);
        assertNotNull(response.getAssignedInvestmentFunds());
        assertEquals(0, response.getAmountRest(), 0);

        response.getAssignedInvestmentFunds().stream().filter(assignment -> assignment.getId().equals("1")).forEach(assignment -> {
            assertEquals(668, assignment.getAmount(), 0d);
            assertEquals(6.68, assignment.getAssignmentPercent(), 0d);
        });

        response.getAssignedInvestmentFunds().stream().filter(assignment -> assignment.getId().equals("2")).forEach(assignment -> {
            assertEquals(666, assignment.getAmount(), 0d);
            assertEquals(6.66, assignment.getAssignmentPercent(), 0d);
        });

        response.getAssignedInvestmentFunds().stream().filter(assignment -> assignment.getId().equals("3")).forEach(assignment -> {
            assertEquals(666, assignment.getAmount(), 0d);
            assertEquals(6.66, assignment.getAssignmentPercent(), 0d);
        });

        response.getAssignedInvestmentFunds().stream().filter(assignment -> assignment.getFundType() == InvestmentFundType.FOREIGN).forEach(assignment -> {
            assertEquals(3750, assignment.getAmount(), 0d);
            assertEquals(37.5, assignment.getAssignmentPercent(), 0d);
        });

        response.getAssignedInvestmentFunds().stream().filter(assignment -> assignment.getFundType() == InvestmentFundType.MONEY).forEach(assignment -> {
            assertEquals(500, assignment.getAmount(), 0d);
            assertEquals(5, assignment.getAssignmentPercent(), 0d);
        });
    }

    @Test
    public void testCalculateAmountForFundType() {
        double result = bean.calculateAmountForFundType(1000, 50);
        assertEquals(500, result, 0);

        result = bean.calculateAmountForFundType(1000, 10);
        assertEquals(100, result, 0);

        result = bean.calculateAmountForFundType(1000, 100);
        assertEquals(1000, result, 0);
    }

    @Test
    public void testGetFundsByType(){
        List<SelectedInvestmentFund> input = new ArrayList<>();
        input.add(new SelectedInvestmentFund("1", InvestmentFundType.POLISH, "Fundusz Polski 1"));
        input.add(new SelectedInvestmentFund("2", InvestmentFundType.FOREIGN, "Fundusz Zagraniczny 1"));
        input.add(new SelectedInvestmentFund("3", InvestmentFundType.FOREIGN, "Fundusz Zagraniczny 2"));
        input.add(new SelectedInvestmentFund("4", InvestmentFundType.MONEY, "Fundusz Pieniezny 1"));
        input.add(new SelectedInvestmentFund("5", InvestmentFundType.MONEY, "Fundusz Pieniezny 2"));
        input.add(new SelectedInvestmentFund("6", InvestmentFundType.MONEY, "Fundusz Pieniezny 3"));

        List<SelectedInvestmentFund> result = bean.getFundsByType(input, InvestmentFundType.POLISH);
        assertNotNull(result);
        assertEquals(1, result.size());

        result = bean.getFundsByType(input, InvestmentFundType.FOREIGN);
        assertNotNull(result);
        assertEquals(2, result.size());

        result = bean.getFundsByType(input, InvestmentFundType.MONEY);
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testAssignForFundType() {
        List<SelectedInvestmentFund> funds = new ArrayList<>();
        funds.add(new SelectedInvestmentFund("1", InvestmentFundType.POLISH, "Fundusz Polski 1"));
        funds.add(new SelectedInvestmentFund("2", InvestmentFundType.POLISH, "Fundusz Polski 2"));

        List<AssignedInvestmentFund> result = bean.assignForFundType(1000, funds, 50);
        result.stream().forEach(assign -> assertEquals(250, assign.getAmount()));

        funds.add(new SelectedInvestmentFund("3", InvestmentFundType.POLISH, "Fundusz Polski 3"));

        result = bean.assignForFundType(1000, funds, 75);
        result.stream().forEach(assign -> assertEquals(250, assign.getAmount()));
    }

    @Test
    public void testAssignForFundTypeUnevelAmount() {
        List<SelectedInvestmentFund> funds = new ArrayList<>();
        funds.add(new SelectedInvestmentFund("1", InvestmentFundType.POLISH, "Fundusz Polski 1"));
        funds.add(new SelectedInvestmentFund("2", InvestmentFundType.POLISH, "Fundusz Polski 2"));
        funds.add(new SelectedInvestmentFund("3", InvestmentFundType.POLISH, "Fundusz Polski 3"));

        List<AssignedInvestmentFund> result = bean.assignForFundType(10000, funds, 20);
        assertEquals(3, result.size());
        assertEquals(668, result.get(0).getAmount());
        assertEquals(666, result.get(1).getAmount());
        assertEquals(666, result.get(2).getAmount());
    }

    private InvestmentCalculatorRequest createInvestmentCalculatorRequest(int amount) {
        List<SelectedInvestmentFund> funds = new ArrayList<>(6);
        funds.add(new SelectedInvestmentFund("1", InvestmentFundType.POLISH, "Fundusz Polski 1"));
        funds.add(new SelectedInvestmentFund("2", InvestmentFundType.POLISH, "Fundusz Polski 2"));
        funds.add(new SelectedInvestmentFund("3", InvestmentFundType.FOREIGN, "Fundusz Zagraniczny 1"));
        funds.add(new SelectedInvestmentFund("4", InvestmentFundType.FOREIGN, "Fundusz Zagraniczny 2"));
        funds.add(new SelectedInvestmentFund("5", InvestmentFundType.FOREIGN, "Fundusz Zagraniczny 3"));
        funds.add(new SelectedInvestmentFund("6", InvestmentFundType.MONEY, "Fundusz Pieniezny 1"));

        return new InvestmentCalculatorRequest("bezpieczny", amount, funds);
    }
}