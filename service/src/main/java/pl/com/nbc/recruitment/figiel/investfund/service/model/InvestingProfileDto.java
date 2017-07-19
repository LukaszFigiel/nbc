package pl.com.nbc.recruitment.figiel.investfund.service.model;

import pl.com.nbc.recruitment.figiel.investfund.domain.model.InvestingProfile;

import java.io.Serializable;

/**
 * DTO for information about investing profile
 * Created by lukaszf on 2017-07-17.
 */
public class InvestingProfileDto implements Serializable{

    private static final long serialVersionUID = -2136051172755682784L;

    /** id of entity*/
    private Long id;

    /** Type of investing profile*/
    private String profileType;

    private double polishFundPercentAssignment;

    /** Foreing fund percent assignment*/
    private double foreignFundPercentAssignment;

    /** Money fund percent assignment*/
    private double moneyFundPercentAssignment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public double getPolishFundPercentAssignment() {
        return polishFundPercentAssignment;
    }

    public void setPolishFundPercentAssignment(double polishFundPercentAssignment) {
        this.polishFundPercentAssignment = polishFundPercentAssignment;
    }

    public double getForeignFundPercentAssignment() {
        return foreignFundPercentAssignment;
    }

    public void setForeignFundPercentAssignment(double foreignFundPercentAssignment) {
        this.foreignFundPercentAssignment = foreignFundPercentAssignment;
    }

    public double getMoneyFundPercentAssignment() {
        return moneyFundPercentAssignment;
    }

    public void setMoneyFundPercentAssignment(double moneyFundPercentAssignment) {
        this.moneyFundPercentAssignment = moneyFundPercentAssignment;
    }

    public static InvestingProfileDto createDto(InvestingProfile investingProfile) {
        if (investingProfile == null) {
            return null;
        }

        InvestingProfileDto dto = new InvestingProfileDto();
        dto.setId(investingProfile.getId());
        dto.setProfileType(investingProfile.getProfileType());
        dto.setForeignFundPercentAssignment(investingProfile.getForeignFundPercentAssignment());
        dto.setPolishFundPercentAssignment(investingProfile.getPolishFundPercentAssignment());
        dto.setMoneyFundPercentAssignment(investingProfile.getMoneyFundPercentAssignment());

        return dto;
    }
}
