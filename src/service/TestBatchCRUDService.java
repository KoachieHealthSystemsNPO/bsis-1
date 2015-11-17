package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.donationbatch.DonationBatch;
import model.testbatch.TestBatch;
import model.testbatch.TestBatchStatus;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repository.DonationBatchRepository;
import repository.TestBatchRepository;

@Service
@Transactional
public class TestBatchCRUDService {

    private static final Logger LOGGER = Logger.getLogger(TestBatchCRUDService.class);

    @Autowired
    private TestBatchRepository testBatchRepository;
    @Autowired
    private TestBatchConstraintChecker testBatchConstraintChecker;
    @Autowired
    private TestBatchStatusChangeService testBatchStatusChangeService;

    @Autowired
    private DonationBatchRepository donationBatchRepository;

    public TestBatch updateTestBatch(Long testBatchId, TestBatchStatus newStatus, Date newCreatedDate, List<Integer> newDonationBatchIds) {
    	
        TestBatch testBatch = testBatchRepository.findTestBatchById(testBatchId);
        
        if (newStatus != null) {
        	testBatch = changeTestBatchStatus(testBatch, newStatus);
        }
        
        if (newCreatedDate != null) {
        	testBatch.setCreatedDate(newCreatedDate);
        }

        if (newDonationBatchIds != null) {
        	List<DonationBatch> newDonationBatches = new ArrayList<DonationBatch>();
        	// unlink old donation batches
        	List<Integer> existingDonationBatchIds = new ArrayList<Integer>();
        	for (DonationBatch donationBatch : testBatch.getDonationBatches()) {
        		existingDonationBatchIds.add(donationBatch.getId());
        		if (!newDonationBatchIds.contains(donationBatch.getId())) {
        			donationBatch.setTestBatch(null);
        			donationBatchRepository.updateDonationBatch(donationBatch);
        		}
        	}
        	// link new donation batches
        	for (Integer batchId : newDonationBatchIds) {
        		DonationBatch donationBatch = donationBatchRepository.findDonationBatchById(batchId);
        		newDonationBatches.add(donationBatch);
        		if (!existingDonationBatchIds.contains(batchId)) {
	        		donationBatch.setTestBatch(testBatch);
	        		donationBatchRepository.updateDonationBatch(donationBatch);
        		}
        	}
        	testBatch.setDonationBatches(newDonationBatches);
        }

        return testBatchRepository.updateTestBatch(testBatch);
    }

    protected TestBatch changeTestBatchStatus(TestBatch testBatch, TestBatchStatus newStatus) {
    	LOGGER.info("Updating status of test batch " + testBatch.getId() + " to " + newStatus);

        if (newStatus == testBatch.getStatus()) {
            // The status is not being changed so return early
            return testBatch;
        }

        if (newStatus == TestBatchStatus.RELEASED && !testBatchConstraintChecker.canReleaseTestBatch(testBatch)) {
            throw new IllegalStateException("Test batch cannot be released");
        }
        
        if (newStatus == TestBatchStatus.CLOSED && !testBatchConstraintChecker.canCloseTestBatch(testBatch)) {
            throw new IllegalStateException("Only released test batches can be closed");
        }

        // Set the new status
        testBatch.setStatus(newStatus);

        testBatch = testBatchRepository.updateTestBatch(testBatch);

        if (newStatus == TestBatchStatus.RELEASED) {
            testBatchStatusChangeService.handleRelease(testBatch);
        }
            
        return testBatch;
    }
}
