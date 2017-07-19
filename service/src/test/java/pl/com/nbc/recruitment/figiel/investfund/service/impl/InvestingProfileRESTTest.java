package pl.com.nbc.recruitment.figiel.investfund.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.com.nbc.recruitment.figiel.investfund.domain.model.InvestingProfile;
import pl.com.nbc.recruitment.figiel.investfund.repo.InvestingProfileRepo;
import pl.com.nbc.recruitment.figiel.investfund.service.model.InvestingProfileDto;
import pl.com.nbc.recruitment.figiel.investfund.service.model.ValidationResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lukaszf on 2017-07-18.
 */
public class InvestingProfileRESTTest {
    InvestingProfileREST rest;
    InvestingProfile bezpieczny;
    InvestingProfileRepo repo;

    @Before
    public void prepare() {
        rest = new InvestingProfileREST();

        bezpieczny = new InvestingProfile();
        bezpieczny.setId(1l);
        bezpieczny.setProfileType("bezpieczny");
        bezpieczny.setPolishFundPercentAssignment(20);
        bezpieczny.setForeignFundPercentAssignment(75);
        bezpieczny.setMoneyFundPercentAssignment(5);

        repo = Mockito.mock(InvestingProfileRepo.class);
        Mockito.when(repo.getByType("bezpieczny")).thenReturn(bezpieczny);
        Mockito.when(repo.get(1l)).thenReturn(bezpieczny);

        List<InvestingProfile> list = new ArrayList<>(1);
        list.add(bezpieczny);
        Mockito.when(repo.listAll()).thenReturn(list);

        rest.investingProfileRepo = repo;
    }

    @Test
    public void getInvestingProfile() throws Exception {
        InvestingProfileDto dto = rest.getInvestingProfile(1l);
        compare(dto, bezpieczny);
    }

    @Test
    public void list() throws Exception {
        List<InvestingProfileDto> list = rest.list();
        assertNotNull(list);
        assertEquals(1, list.size());
        compare(list.get(0), bezpieczny);
    }

    @Test
    public void saveInvestingProfile() throws Exception {
        InvestingProfileDto dto = new InvestingProfileDto();
        dto.setProfileType("agresywny");
        dto.setForeignFundPercentAssignment(50);
        dto.setPolishFundPercentAssignment(20);
        dto.setMoneyFundPercentAssignment(30);

        ValidationResult result = rest.saveInvestingProfile(dto);
        assertTrue(result.isValid());

        Mockito.verify(repo, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void saveInvestingProfileInvalid() throws Exception {
        InvestingProfileDto dto = new InvestingProfileDto();
        dto.setProfileType("agresywny");
        dto.setForeignFundPercentAssignment(50);
        dto.setPolishFundPercentAssignment(30);
        dto.setMoneyFundPercentAssignment(30);

        ValidationResult result = rest.saveInvestingProfile(dto);
        assertFalse(result.isValid());

    }

    @Test
    public void saveInvestingProfileAlreadyExist() throws Exception {
        InvestingProfileDto dto = new InvestingProfileDto();
        dto.setProfileType("bezpieczny");

        ValidationResult result = rest.saveInvestingProfile(dto);
        assertFalse(result.isValid());
    }

    @Test
    public void updateInvestingProfile() throws Exception {
        InvestingProfileDto dto = new InvestingProfileDto();
        dto.setProfileType("bezpieczny");
        dto.setId(1l);
        dto.setMoneyFundPercentAssignment(20);
        dto.setPolishFundPercentAssignment(50);
        dto.setForeignFundPercentAssignment(30);

        ValidationResult result = rest.updateInvestingProfile(dto);
        assertTrue(result.isValid());
    }

    @Test
    public void updateInvestingProfileInvalid() throws Exception {
        InvestingProfileDto dto = new InvestingProfileDto();
        dto.setProfileType("bezpieczny");
        dto.setId(1l);
        dto.setMoneyFundPercentAssignment(20);
        dto.setPolishFundPercentAssignment(50);
        dto.setForeignFundPercentAssignment(40);

        ValidationResult result = rest.updateInvestingProfile(dto);
        assertFalse(result.isValid());
    }

    @Test
    public void delete() throws Exception {
        rest.delete(1l);
        Mockito.verify(repo, Mockito.times(1)).remove(bezpieczny);
    }

    private void compare(InvestingProfileDto dto, InvestingProfile entity) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getProfileType(), dto.getProfileType());
        assertEquals(entity.getPolishFundPercentAssignment(), dto.getPolishFundPercentAssignment(), 0);
        assertEquals(entity.getForeignFundPercentAssignment(), dto.getForeignFundPercentAssignment(), 0);
        assertEquals(entity.getMoneyFundPercentAssignment(), dto.getMoneyFundPercentAssignment(), 0);
    }

}