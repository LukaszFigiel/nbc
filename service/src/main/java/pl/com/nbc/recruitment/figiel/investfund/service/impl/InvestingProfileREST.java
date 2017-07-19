package pl.com.nbc.recruitment.figiel.investfund.service.impl;

import pl.com.nbc.recruitment.figiel.investfund.domain.model.InvestingProfile;
import pl.com.nbc.recruitment.figiel.investfund.repo.InvestingProfileRepo;
import pl.com.nbc.recruitment.figiel.investfund.service.model.InvestingProfileDto;
import pl.com.nbc.recruitment.figiel.investfund.service.model.ValidationResult;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.OneToMany;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszf on 2017-07-17.
 */
@Path("/investingProfile")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvestingProfileREST {

    @Inject
    InvestingProfileRepo investingProfileRepo;

    @GET
    @Path("/details/{id}")
    public InvestingProfileDto getInvestingProfile(@PathParam("id") Long id) {

        InvestingProfile investingProfile = investingProfileRepo.get(id);

        return InvestingProfileDto.createDto(investingProfile);
    }

    @GET
    @Path("/list")
    public List<InvestingProfileDto> list() {
        List<InvestingProfile> profiles = investingProfileRepo.listAll();
        List<InvestingProfileDto> result = new ArrayList<>();

        profiles.stream().forEach(entity -> {
            result.add(InvestingProfileDto.createDto(entity));
        });
        return result;
    }

    @POST
    @Path("/")
    public ValidationResult saveInvestingProfile(InvestingProfileDto investingProfileDto) {

        InvestingProfile investingProfile = investingProfileRepo.getByType(investingProfileDto.getProfileType());
        if (investingProfile != null) {
            return ValidationResult.createInValid("Investing profile with type" + investingProfileDto.getProfileType() + " already exists");
        }

        investingProfile = new InvestingProfile();
        investingProfile.setProfileType(investingProfileDto.getProfileType());

        mapDtoToEntity(investingProfileDto, investingProfile);

        if (!investingProfile.verifyPercentAssignmetns()) {
            return ValidationResult.createInValid("Sum of assignments is not equal to 100%");
        }

        investingProfileRepo.save(investingProfile);

        return ValidationResult.createValid();
    }

    private void mapDtoToEntity(InvestingProfileDto investingProfileDto, InvestingProfile investingProfile) {
        investingProfile.setForeignFundPercentAssignment(investingProfileDto.getForeignFundPercentAssignment());
        investingProfile.setPolishFundPercentAssignment(investingProfileDto.getPolishFundPercentAssignment());
        investingProfile.setMoneyFundPercentAssignment(investingProfileDto.getMoneyFundPercentAssignment());
    }

    @PUT
    @Path("/")
    public ValidationResult updateInvestingProfile(InvestingProfileDto investingProfileDto) {

        InvestingProfile investingProfile = investingProfileRepo.get(investingProfileDto.getId());
        if (investingProfile == null) {
            return ValidationResult.createInValid("Investing profile did not find");
        }

        mapDtoToEntity(investingProfileDto, investingProfile);

        if (!investingProfile.verifyPercentAssignmetns()) {
            return ValidationResult.createInValid("Sum of assignments is not equal to 100%");
        }

        investingProfileRepo.save(investingProfile);

        return ValidationResult.createValid();
    }

    @DELETE
    @Path("/{id}")
    public ValidationResult delete(@PathParam("id") Long id) {
        InvestingProfile investingProfile = investingProfileRepo.get(id);
        if (investingProfile != null) {
            investingProfileRepo.remove(investingProfile);
        }
        return ValidationResult.createValid();
    }
}
