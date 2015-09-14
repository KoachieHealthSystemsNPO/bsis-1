package helpers.persisters;

import static helpers.persisters.EntityPersisterFactory.aDonorPersister;
import static helpers.persisters.EntityPersisterFactory.aLocationPersister;
import static helpers.persisters.EntityPersisterFactory.anAdverseEventPersister;

import javax.persistence.EntityManager;

import model.donation.Donation;

public class DonationPersister extends AbstractEntityPersister<Donation> {

    @Override
    public Donation deepPersist(Donation donation, EntityManager entityManager) {
        if (donation.getDonor() != null) {
            aDonorPersister().deepPersist(donation.getDonor(), entityManager);
        }
        
        if (donation.getDonorPanel() != null) {
            aLocationPersister().deepPersist(donation.getDonorPanel(), entityManager);
        }
        
        if (donation.getAdverseEvent() != null) {
            anAdverseEventPersister().deepPersist(donation.getAdverseEvent(), entityManager);
        }
        
        return persist(donation, entityManager);
    }

}
