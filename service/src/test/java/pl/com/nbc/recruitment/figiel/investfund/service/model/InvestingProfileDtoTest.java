package pl.com.nbc.recruitment.figiel.investfund.service.model;

import org.junit.Test;
import pl.com.nbc.recruitment.figiel.investfund.domain.model.InvestingProfile;

import static org.junit.Assert.*;

/**
 * Created by lukaszf on 2017-07-18.
 */
public class InvestingProfileDtoTest {
    @Test
    public void createDto() throws Exception {
        InvestingProfile entity = new InvestingProfile();
        entity.setProfileType("type");
        entity.setPolishFundPercentAssignment(10);
        entity.setForeignFundPercentAssignment(20);
        entity.setMoneyFundPercentAssignment(30);

        InvestingProfileDto dto = InvestingProfileDto.createDto(entity);
        assertNotNull(dto);
        assertEquals("type", dto.getProfileType());
        assertEquals(10, dto.getPolishFundPercentAssignment(), 0);
        assertEquals(20, dto.getForeignFundPercentAssignment(), 0);
        assertEquals(30, dto.getMoneyFundPercentAssignment(), 0);


    }

}