package service;

import model.donation.Donation;
import model.donationbatch.DonationBatch;
import model.testbatch.TestBatch;
import model.testbatch.TestBatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestBatchConstraintChecker {
    
    @Autowired
    private DonationConstraintChecker donationConstraintChecker;

    /**
     * A test batch can be released if it is open and none of the donations have outstanding outcomes.
     */
    public boolean canReleaseTestBatch(TestBatch testBatch) {

        if (testBatch.getStatus() != TestBatchStatus.OPEN) {
            // Only open test batches can be released
            return false;
        }

        // Check for tests with outstanding test outcomes
        for (DonationBatch donationBatch : testBatch.getDonationBatches()) {

            for (Donation donation : donationBatch.getDonations()) {
                
                if (donationConstraintChecker.donationHasOutstandingOutcomes(donation)) {
                    
                    // This test has an outstanding outcome
                    return false;
                }
            }
        }

        return true;
    }
    
    /**
     * A test batch can be closed if it is released and none of the donations have discrepancies.
     */
    public boolean canCloseTestBatch(TestBatch testBatch) {

        if (testBatch.getStatus() != TestBatchStatus.RELEASED) {
            // Only released batches can be closed
            return false;
        }
        
        for (DonationBatch donationBatch : testBatch.getDonationBatches()) {

            for (Donation donation : donationBatch.getDonations()) {

                if (donationConstraintChecker.donationHasDiscrepancies(donation)) {
                    
                    // This donation has discrepancies
                    return false;
                }
            }
        }

        return true;
    }

}
