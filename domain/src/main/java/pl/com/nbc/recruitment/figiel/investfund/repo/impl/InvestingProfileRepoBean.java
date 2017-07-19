package pl.com.nbc.recruitment.figiel.investfund.repo.impl;

import pl.com.nbc.recruitment.figiel.investfund.domain.model.InvestingProfile;
import pl.com.nbc.recruitment.figiel.investfund.repo.InvestingProfileRepo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by lukaszf on 2017-07-18.
 */
@Stateless
public class InvestingProfileRepoBean implements InvestingProfileRepo {

    @PersistenceContext(unitName = "InvestingProfilePU")
    EntityManager entityManager;

    @Override
    public InvestingProfile get(Long id) {
        return entityManager.find(InvestingProfile.class, id);
    }

    @Override
    public InvestingProfile getByType(String type) {
        Query query = entityManager.createNamedQuery(InvestingProfile.FIND_BY_TYPE_QUERY).setParameter(1, type);
        List<InvestingProfile> results = (List<InvestingProfile>)query.getResultList();
        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public InvestingProfile save(InvestingProfile investingProfile) {
        if (entityManager.contains(investingProfile)) {
            //locking Aggregate Root logically protects whole aggregate
            entityManager.lock(investingProfile, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            entityManager.flush();
        } else {
            entityManager.persist(investingProfile);
        }
        return investingProfile;
    }

    @Override
    public List<InvestingProfile> listAll() {
        Query query = entityManager.createNamedQuery(InvestingProfile.FIND_ALL_QUERY);
        return (List<InvestingProfile>)query.getResultList();
    }

    @Override
    public void remove(InvestingProfile investingProfile) {
        entityManager.remove(investingProfile);
    }
}
