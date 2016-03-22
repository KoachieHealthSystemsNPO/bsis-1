package factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.donation.Donation;
import model.donationbatch.DonationBatch;
import model.testbatch.TestBatch;
import service.TestBatchConstraintChecker;
import service.TestBatchConstraintChecker.CanReleaseResult;
import viewmodel.DonationBatchViewModel;
import viewmodel.TestBatchFullViewModel;
import viewmodel.TestBatchViewModel;

/**
 * A factory for creating TestBatchViewModel and TestBatchFullViewModel objects.
 */
@Service
public class TestBatchViewModelFactory {

  /** The donation batch view model factory. */
  @Autowired
  private DonationBatchViewModelFactory donationBatchViewModelFactory;

  /** The test batch constraint checker. */
  @Autowired
  private TestBatchConstraintChecker testBatchConstraintChecker;

  /**
   * Creates a list of basic view models for the given list of test batches.
   *
   * @param testBatches the test batches
   * @param isTestingSupervisor the is testing supervisor
   * @return the list< test batch view model>
   */
  public List<TestBatchViewModel> createTestBatchBasicViewModels(List<TestBatch> testBatches) {
    List<TestBatchViewModel> viewModels = new ArrayList<>();
    for (TestBatch testBatch : testBatches) {
      TestBatchViewModel testBatchViewModel = new TestBatchViewModel();
      populateBasicViewModel(testBatch, testBatchViewModel);
      viewModels.add(testBatchViewModel);
    }
    return viewModels;
  }

  /**
   * Creates a list of full view models for the given list of test batches.
   *
   * @param testBatch the test batch
   * @param isTestingSupervisor the is testing supervisor
   * @return the test batch full view model
   */
  public List<TestBatchFullViewModel> createTestBatchFullViewModels(List<TestBatch> testBatches,
      boolean isTestingSupervisor) {
    List<TestBatchFullViewModel> viewModels = new ArrayList<>();
    for (TestBatch testBatch : testBatches) {
      viewModels.add(createTestBatchFullViewModel(testBatch, isTestingSupervisor));
    }
    return viewModels;
  }

  /**
   * Creates a full view model for the given test batch.
   *
   * @param testBatch the test batch
   * @param isTestingSupervisor the is testing supervisor
   * @return the test batch full view model
   */
  public TestBatchFullViewModel createTestBatchFullViewModel(TestBatch testBatch, boolean isTestingSupervisor) {
    TestBatchFullViewModel testBatchViewModel = new TestBatchFullViewModel();
    populateFullViewModel(testBatch, testBatchViewModel, isTestingSupervisor);
    return testBatchViewModel;
  }

  /**
   * Populate basic view model.
   *
   * @param testBatch the test batch
   * @param testBatchViewModel the test batch view model
   */
  private void populateBasicViewModel(TestBatch testBatch, TestBatchViewModel testBatchViewModel) {
    testBatchViewModel.setId(testBatch.getId());
    testBatchViewModel.setStatus(testBatch.getStatus());
    testBatchViewModel.setBatchNumber(testBatch.getBatchNumber());
    testBatchViewModel.setCreatedDate(testBatch.getCreatedDate());
    testBatchViewModel.setLastUpdated(testBatch.getLastUpdated());
    testBatchViewModel.setNotes(testBatch.getNotes());

    // Calculate number of samples (only consider donations with test samples)
    int numSamples = 0;
    for (DonationBatch donationBatch : testBatch.getDonationBatches()) {
      for (Donation donation : donationBatch.getDonations()) {
        if (donation.getPackType().getTestSampleProduced()) {
          numSamples++;
        }
      }
    }
    testBatchViewModel.setNumSamples(numSamples);
  }

  /**
   * Populate full view model.
   *
   * @param testBatch the test batch
   * @param testBatchViewModel the test batch view model
   * @param isTestingSupervisor the is testing supervisor
   * @return the test batch full view model
   */
  private TestBatchFullViewModel populateFullViewModel(TestBatch testBatch, TestBatchFullViewModel testBatchViewModel,
      boolean isTestingSupervisor) {

    // First populate basic fields
    populateBasicViewModel(testBatch, testBatchViewModel);

    // Get list of donation view models with test samples
    List<DonationBatchViewModel> donationsWithTestSamples = new ArrayList<>();
    if (testBatch.getDonationBatches() != null) {
      for (DonationBatch donationBatch : testBatch.getDonationBatches()) {
        donationsWithTestSamples.add(
            donationBatchViewModelFactory.createDonationBatchViewModelWithoutDonationPermissions(donationBatch, true));
      }
    }
    testBatchViewModel.setDonationBatches(donationsWithTestSamples);

    // Check if this test batch can be released
    CanReleaseResult canReleaseResult = testBatchConstraintChecker.canReleaseTestBatch(testBatch);
    if (canReleaseResult.canRelease()) {
      // Include the number of donations ready for release
      testBatchViewModel.setReadyForReleaseCount(canReleaseResult.getReadyCount());
    }

    // Set permissions
    Map<String, Boolean> permissions = new HashMap<>();
    permissions.put("canRelease", isTestingSupervisor && canReleaseResult.canRelease());
    permissions.put("canClose", isTestingSupervisor && testBatchConstraintChecker.canCloseTestBatch(testBatch));
    permissions.put("canDelete", isTestingSupervisor && testBatchConstraintChecker.canDeleteTestBatch(testBatch));
    permissions.put("canEdit", isTestingSupervisor && testBatchConstraintChecker.canEditTestBatch(testBatch));
    permissions.put("canEditDonationBatches",
        isTestingSupervisor && testBatchConstraintChecker.canAddOrRemoveDonationBatch(testBatch));
    permissions.put("canReopen", isTestingSupervisor && testBatchConstraintChecker.canReopenTestBatch(testBatch));
    testBatchViewModel.setPermissions(permissions);

    return testBatchViewModel;
  }
}
