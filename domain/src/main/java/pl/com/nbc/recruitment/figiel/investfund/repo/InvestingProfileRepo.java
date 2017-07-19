package pl.com.nbc.recruitment.figiel.investfund.repo;

import pl.com.nbc.recruitment.figiel.investfund.domain.model.InvestingProfile;

import javax.ejb.Local;
import java.util.List;

/**
 * Repository of InvestmentFound
 * Created by lukaszf on 2017-07-17.
 */
@Local
public interface InvestingProfileRepo {

    InvestingProfile get(Long id);

    InvestingProfile getByType(String type);

    InvestingProfile save(InvestingProfile investingProfile);

    List listAll();

    void remove(InvestingProfile investingProfile);


}
