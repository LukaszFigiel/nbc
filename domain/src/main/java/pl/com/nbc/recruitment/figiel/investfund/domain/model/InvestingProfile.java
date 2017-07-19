package pl.com.nbc.recruitment.figiel.investfund.domain.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entity for information about investing profile
 * Created by lukaszf on 2017-07-17.
 */
@Entity()
@Table(name="INVESTING_PROFILE")
@NamedQueries({
        @NamedQuery(name = InvestingProfile.FIND_BY_TYPE_QUERY, query = "SELECT ip FROM InvestingProfile ip WHERE ip.profileType = ?1"),
        @NamedQuery(name = InvestingProfile.FIND_ALL_QUERY, query = "SELECT ip FROM InvestingProfile ip")
})
public class InvestingProfile {

    public static final String FIND_BY_TYPE_QUERY = "InvestingProfile.FIND_BY_TYPE_QUERY";
    public static final String FIND_ALL_QUERY = "InvestingProfile.FIND_ALL_QUERY";

    /** technical id of entity*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InvestingProfileGen")
    @SequenceGenerator(name = "InvestingProfileGen", sequenceName = "INVESTING_PROFILE_ID_SEQ", allocationSize = 1)
    private Long id;

    /** Type of investing profile*/
    @Column(name="PROFILE_TYPE")
    private String profileType;

    /** Polish fund percent assignment*/
    @Column(name="POLISH_FUND_ASSIGN")
    private double polishFundPercentAssignment;

    /** Foreing fund percent assignment*/
    @Column(name="FOREIGN_FUND_ASSIGN")
    private double foreignFundPercentAssignment;

    /** Money fund percent assignment*/
    @Column(name="MONEY_FUND_ASSIGN")
    private double moneyFundPercentAssignment;

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    /** Verify that sum of all percent assignments is equal 100*/
    public boolean verifyPercentAssignmetns() {
        double sum = polishFundPercentAssignment + foreignFundPercentAssignment + moneyFundPercentAssignment;

        return Double.compare(sum, 100) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvestingProfile that = (InvestingProfile) o;

        if (Double.compare(that.polishFundPercentAssignment, polishFundPercentAssignment) != 0) return false;
        if (Double.compare(that.foreignFundPercentAssignment, foreignFundPercentAssignment) != 0) return false;
        if (Double.compare(that.moneyFundPercentAssignment, moneyFundPercentAssignment) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return profileType != null ? profileType.equals(that.profileType) : that.profileType == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (profileType != null ? profileType.hashCode() : 0);
        temp = Double.doubleToLongBits(polishFundPercentAssignment);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(foreignFundPercentAssignment);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(moneyFundPercentAssignment);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
