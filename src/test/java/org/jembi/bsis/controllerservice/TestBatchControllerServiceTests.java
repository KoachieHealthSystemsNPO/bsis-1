package org.jembi.bsis.controllerservice;

import org.jembi.bsis.backingform.TestBatchDonationRangeBackingForm;
import org.jembi.bsis.backingform.TestBatchDonationsBackingForm;
import org.jembi.bsis.factory.TestBatchFactory;
import org.jembi.bsis.model.donation.Donation;
import org.jembi.bsis.model.testbatch.TestBatch;
import org.jembi.bsis.repository.DonationRepository;
import org.jembi.bsis.repository.TestBatchRepository;
import org.jembi.bsis.service.DonationCRUDService;
import org.jembi.bsis.suites.UnitTestSuite;
import org.jembi.bsis.viewmodel.TestBatchFullViewModel;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.jembi.bsis.helpers.builders.DonationBuilder.aDonation;
import static org.jembi.bsis.helpers.builders.TestBatchBuilder.aTestBatch;
import static org.jembi.bsis.helpers.builders.TestBatchDonationRangeBackingFormBuilder.aTestBatchDonationRangeBackingForm;
import static org.jembi.bsis.helpers.builders.TestBatchFullViewModelBuilder.aTestBatchFullViewModel;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestBatchControllerServiceTests extends UnitTestSuite {

  @Mock
  private TestBatchFactory testBatchFactory;
  @Mock
  private DonationRepository donationRepository;
  @Mock
  private DonationCRUDService donationCRUDService;
  @Mock
  private TestBatchRepository testBatchRepository;

  @InjectMocks
  private TestBatchControllerService controllerService;

  @Test
  public void testAddDonationsToBatch_shouldDelegateToDonationCRUDService() {
    UUID testBatchId = UUID.randomUUID();
    UUID donationOneId = UUID.randomUUID();
    UUID donationTwoId = UUID.randomUUID();
    TestBatchDonationRangeBackingForm backingForm = aTestBatchDonationRangeBackingForm()
        .withTestBatchId(testBatchId)
        .withFromDIN(donationOneId.toString())
        .withToDIN(donationTwoId.toString()).build();

    TestBatch testBatch = aTestBatch().withId(testBatchId).build();
    Donation donationOne = aDonation().withId(donationOneId).build();
    Donation donationTwo = aDonation().withId(donationTwoId).build();
    List<Donation> donations = Arrays.asList(donationOne, donationTwo);
    TestBatch testBatchWithDonations = aTestBatch().withId(testBatchId).withDonations(new HashSet<>(donations)).build();

    TestBatchFullViewModel expected = aTestBatchFullViewModel().build();

    when(testBatchRepository.findTestBatchById(testBatchId)).thenReturn(testBatch);
    when(donationRepository.findDonationsBetweenTwoDins(donationOneId.toString(), donationTwoId.toString()))
        .thenReturn(donations);
    when(donationCRUDService.addDonationsToTestBatch(donations, testBatch)).thenReturn(testBatch);
    when(testBatchFactory.createTestBatchFullViewModel(testBatchWithDonations)).thenReturn(expected);

    TestBatchFullViewModel actual = controllerService.addDonationsToTestBatch(backingForm);

    verify(donationCRUDService).addDonationsToTestBatch(donations, testBatch);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testRemoveDonationsFromBatch_shouldDelegateToDonationCRUDService() {
    UUID testBatchId = UUID.randomUUID();
    UUID donationOneId = UUID.randomUUID();
    UUID donationTwoId = UUID.randomUUID();
    TestBatchDonationsBackingForm backingForm = TestBatchDonationsBackingForm.builder().testBatchId(testBatchId)
        .donationIds(Arrays.asList(donationOneId, donationTwoId)).build();

    TestBatch testBatch = aTestBatch().withId(testBatchId).withDonations(new HashSet<>()).build();
    Donation donationOne = aDonation().withId(donationOneId).build();
    Donation donationTwo = aDonation().withId(donationTwoId).build();
    testBatch.addDonation(donationOne);
    testBatch.addDonation(donationTwo);
    List<Donation> donations = Arrays.asList(donationOne, donationTwo);

    TestBatchFullViewModel expected = aTestBatchFullViewModel().build();

    when(testBatchRepository.findTestBatchById(testBatchId)).thenReturn(testBatch);
    when(donationRepository.findDonationById(donationOneId)).thenReturn(donationOne);
    when(donationRepository.findDonationById(donationTwoId)).thenReturn(donationTwo);
    when(donationCRUDService.removeDonationsFromTestBatch(donations, testBatch)).thenReturn(testBatch);
    when(testBatchFactory.createTestBatchFullViewModel(testBatch)).thenReturn(expected);

    TestBatchFullViewModel actual = controllerService.removeDonationsFromBatch(backingForm);

    verify(donationCRUDService).removeDonationsFromTestBatch(donations, testBatch);
    assertThat(actual, is(equalTo(expected)));
  }
}
