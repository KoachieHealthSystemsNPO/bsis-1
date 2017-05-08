package org.jembi.bsis.controller;

import org.jembi.bsis.model.bloodtesting.BloodTestType;
import org.jembi.bsis.model.donation.BloodTypingMatchStatus;
import org.jembi.bsis.model.donationbatch.DonationBatch;
import org.jembi.bsis.model.testbatch.TestBatch;
import org.jembi.bsis.model.testbatch.TestBatchStatus;
import org.jembi.bsis.repository.TestBatchRepository;
import org.jembi.bsis.repository.bloodtesting.BloodTestingRepository;
import org.jembi.bsis.suites.UnitTestSuite;
import org.jembi.bsis.viewmodel.BloodTestFullViewModel;
import org.jembi.bsis.viewmodel.BloodTestResultViewModel;
import org.jembi.bsis.viewmodel.BloodTestingRuleResult;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import static org.jembi.bsis.helpers.builders.BloodTestFullViewModelBuilder.aBasicBloodTypingBloodTestFullViewModel;
import static org.jembi.bsis.helpers.builders.BloodTestFullViewModelBuilder.aBasicTTIBloodTestFullViewModel;
import static org.jembi.bsis.helpers.builders.BloodTestFullViewModelBuilder.aConfirmatoryTTIBloodTestFullViewModel;
import static org.jembi.bsis.helpers.builders.BloodTestFullViewModelBuilder.aRepeatBloodTypingBloodTestFullViewModel;
import static org.jembi.bsis.helpers.builders.BloodTestFullViewModelBuilder.aRepeatTTIBloodTestFullViewModel;
import static org.jembi.bsis.helpers.builders.BloodTestResultViewModelBuilder.aBloodTestResultViewModel;
import static org.jembi.bsis.helpers.builders.BloodTestingRuleResultBuilder.aBloodTestingRuleResult;
import static org.jembi.bsis.helpers.builders.DonationBatchBuilder.aDonationBatch;
import static org.jembi.bsis.helpers.builders.TestBatchBuilder.aTestBatch;
import static org.mockito.Mockito.when;

public class TestResultControllerTests extends UnitTestSuite {
  
  private static final UUID DONATION_BATCH_ID = UUID.randomUUID();
  
  @Spy
  @InjectMocks
  private TestResultController testResultController;
  @Mock
  private BloodTestingRepository bloodTestingRepository;
  @Mock
  private TestBatchRepository testBatchRepository;

  // TODO these testcases can be improved to have better and more descriptive names, and for the
  // tests not to test too many logical combinations at the same time.

  @Test
  public void testFindTestResultsOverviewForTestBatchWithReEntryRequiredForTTITestsOnly_shouldReturnCorrectResults() {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    Set<DonationBatch> donationBatches = new HashSet<>(Arrays.asList(
        aDonationBatch()
            .withId(DONATION_BATCH_ID)
            .withBatchNumber("123")
            .withDonationBatchDate(new Date())
            .build()
      )
    );
    
    TestBatch aTestBatch = aTestBatch()
        .withId(UUID.randomUUID())
        .withBatchNumber("00001")
        .withCreatedDate(new Date())
        .withStatus(TestBatchStatus.OPEN)
        .withDonationBatches(donationBatches)
        .build();
    
    BloodTestFullViewModel basicTTIBloodFullTestViewModel = aBasicTTIBloodTestFullViewModel()
        .withId(1L)
        .withBloodTestType(BloodTestType.BASIC_TTI)
        .build();
    
    BloodTestResultViewModel basicTTIBloodTestResultViewModel = aBloodTestResultViewModel()
        .withId(1L)
        .withBloodTest(basicTTIBloodFullTestViewModel)
        .withReEntryRequired()
        .build();
            
    Map<Long, BloodTestResultViewModel> recentTestResults = new HashMap<>();
    recentTestResults.put(1L, basicTTIBloodTestResultViewModel);
    
    List<Long> noTestIds = new ArrayList<>();
    List<BloodTestingRuleResult> bloodTestingRuleResult = Arrays.asList(
        aBloodTestingRuleResult()
            .withBloodTypingMatchStatus(BloodTypingMatchStatus.MATCH)
            .withPendingRepeatAndConfirmatoryTtiTestsIds(noTestIds)
            .withRecentResults(recentTestResults)
            .withPendingBloodTypingTestsIds(noTestIds)
            .withPendingConfirmatoryTTITestsIds(noTestIds)
            .withPendingRepeatTTITestsIds(noTestIds)
            .build());
  
    // Expected Data.
    Map<String, Object> expectedOverviewFlags = new HashMap<String, Object>();
    expectedOverviewFlags.put("hasReEntryRequiredTTITests", true);
    expectedOverviewFlags.put("hasReEntryRequiredBloodTypingTests", false);
    expectedOverviewFlags.put("hasReEntryRequiredRepeatBloodTypingTests", false);
    expectedOverviewFlags.put("hasReEntryRequiredConfirmatoryTTITests", false);
    expectedOverviewFlags.put("hasReEntryRequiredRepeatTTITests", false);
    expectedOverviewFlags.put("hasRepeatBloodTypingTests", false);
    expectedOverviewFlags.put("hasConfirmatoryTTITests", false);
    expectedOverviewFlags.put("hasRepeatTTITests", false);
    expectedOverviewFlags.put("hasPendingRepeatTTITests", false);
    expectedOverviewFlags.put("hasPendingConfirmatoryTTITests", false);
    expectedOverviewFlags.put("hasPendingRepeatBloodTypingTests", false);
    expectedOverviewFlags.put("hasPendingBloodTypingConfirmations", false);
    
    when(testBatchRepository.findTestBatchById(aTestBatch.getId())).thenReturn(aTestBatch);
    when(testResultController.getBloodTestingRuleResults(aTestBatch)).thenReturn(bloodTestingRuleResult);

    // Test
    ResponseEntity<Map<String, Object>> returnedResponse = testResultController.findTestResultsOverviewForTestBatch(
        request, aTestBatch.getId());

    Map<String, Object> overViewFlags = returnedResponse.getBody();
    Assert.assertNotNull("map is returned", overViewFlags);
    Assert.assertEquals(expectedOverviewFlags,overViewFlags);
  }
  
  @Test
  public void testFindTestResultsOverviewForTestBatchWithMultipleRuleResultsWithReEntryRequiredForBasicAndRepeatTTITests_shouldReturnCorrectResults() {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    Set<DonationBatch> donationBatches = new HashSet<>(Arrays.asList(
        aDonationBatch()
            .withId(DONATION_BATCH_ID)
            .withBatchNumber("123")
            .withDonationBatchDate(new Date())
            .build()
      )
    );
    
    TestBatch aTestBatch = aTestBatch().withId(UUID.randomUUID()).withBatchNumber("00001").withCreatedDate(new Date())
        .withStatus(TestBatchStatus.OPEN).withDonationBatches(donationBatches).build();
    
    BloodTestFullViewModel basicTTIBloodFullTestViewModel = aBasicBloodTypingBloodTestFullViewModel()
        .withId(1L)
        .withBloodTestType(BloodTestType.BASIC_TTI)
        .build();
    
    BloodTestResultViewModel basicTTIBloodTestResultViewModel = aBloodTestResultViewModel()
        .withId(1L)
        .withBloodTest(basicTTIBloodFullTestViewModel)
        .withReEntryRequired()
        .build();
            
    Map<Long, BloodTestResultViewModel> recentTestResults1 = new HashMap<>();
    recentTestResults1.put(1L, basicTTIBloodTestResultViewModel);
     
    BloodTestFullViewModel repeatTTIBloodFullTestViewModel = aBasicBloodTypingBloodTestFullViewModel()
        .withId(1L)
        .withBloodTestType(BloodTestType.REPEAT_TTI)
        .build();
    
    BloodTestResultViewModel repeatTTIBloodTestResultViewModel = aBloodTestResultViewModel()
        .withId(1L)
        .withBloodTest(repeatTTIBloodFullTestViewModel)
        .withReEntryRequired()
        .build();
            
    Map<Long, BloodTestResultViewModel> recentTestResults2 = new HashMap<>();
    recentTestResults2.put(1L, repeatTTIBloodTestResultViewModel);
   
    List<Long> noTestIds = new ArrayList<>();

    List<BloodTestingRuleResult> bloodTestingRuleResult = Arrays.asList(
        aBloodTestingRuleResult()
            .withBloodTypingMatchStatus(BloodTypingMatchStatus.MATCH)
            .withPendingRepeatAndConfirmatoryTtiTestsIds(noTestIds)
            .withRecentResults(recentTestResults1)
            .withPendingBloodTypingTestsIds(noTestIds)
            .withPendingConfirmatoryTTITestsIds(noTestIds)
            .withPendingRepeatTTITestsIds(noTestIds)
            .build(),
        aBloodTestingRuleResult()
            .withBloodTypingMatchStatus(BloodTypingMatchStatus.MATCH)
            .withPendingRepeatAndConfirmatoryTtiTestsIds(noTestIds)
            .withRecentResults(recentTestResults2)
            .withPendingBloodTypingTestsIds(noTestIds)
            .withPendingConfirmatoryTTITestsIds(noTestIds)
            .withPendingRepeatTTITestsIds(noTestIds)
            .build());
    
    // Expected Data.
    Map<String, Object> expectedOverviewFlags = new HashMap<String, Object>();
    expectedOverviewFlags.put("hasReEntryRequiredTTITests", true);
    expectedOverviewFlags.put("hasReEntryRequiredBloodTypingTests", false);
    expectedOverviewFlags.put("hasReEntryRequiredRepeatBloodTypingTests", false);
    expectedOverviewFlags.put("hasReEntryRequiredConfirmatoryTTITests", false);
    expectedOverviewFlags.put("hasReEntryRequiredRepeatTTITests", true);
    expectedOverviewFlags.put("hasRepeatBloodTypingTests", false);
    expectedOverviewFlags.put("hasConfirmatoryTTITests", false);
    expectedOverviewFlags.put("hasRepeatTTITests", true);
    expectedOverviewFlags.put("hasPendingRepeatTTITests", false);
    expectedOverviewFlags.put("hasPendingConfirmatoryTTITests", false);
    expectedOverviewFlags.put("hasPendingRepeatBloodTypingTests", false);
    expectedOverviewFlags.put("hasPendingBloodTypingConfirmations", false);
    
    when(testBatchRepository.findTestBatchById(aTestBatch.getId())).thenReturn(aTestBatch);
    when(testResultController.getBloodTestingRuleResults(aTestBatch)).thenReturn(bloodTestingRuleResult);
    // Test
    ResponseEntity<Map<String, Object>> returnedResponse = testResultController.findTestResultsOverviewForTestBatch(
        request, aTestBatch.getId());

    Map<String, Object> overViewFlags = returnedResponse.getBody();
    Assert.assertNotNull("map is returned", overViewFlags);
    Assert.assertEquals(expectedOverviewFlags,overViewFlags);
  }
  
  @Test
  public void testFindTestResultsOverviewForTestBatchWithPendingBloodTypingTests_shouldReturnCorrectResults() {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    Set<DonationBatch> donationBatches = new HashSet<>(Arrays.asList(
        aDonationBatch()
            .withId(DONATION_BATCH_ID)
            .withBatchNumber("123")
            .withDonationBatchDate(new Date())
            .build()
      )
    );
    
    TestBatch aTestBatch = aTestBatch().withId(UUID.randomUUID()).withBatchNumber("00001").withCreatedDate(new Date())
        .withStatus(TestBatchStatus.OPEN).withDonationBatches(donationBatches).build();
    
    BloodTestFullViewModel basicBloodTypingBloodFullTestViewModel = aBasicBloodTypingBloodTestFullViewModel()
        .withId(1L)
        .withBloodTestType(BloodTestType.BASIC_BLOODTYPING)
        .build();
    
    BloodTestResultViewModel basicBloodTypingBloodTestResultViewModel = aBloodTestResultViewModel()
        .withId(1L)
        .withBloodTest(basicBloodTypingBloodFullTestViewModel)
        .withReEntryNotRequired()
        .build();
            
    Map<Long, BloodTestResultViewModel> recentTestResults = new HashMap<>();
    recentTestResults.put(1L, basicBloodTypingBloodTestResultViewModel);

    List<Long> bloodTypeTestsIds = Arrays.asList(1L, 2L, 3L);
    List<Long> noTestIds = new ArrayList<>();
    List<BloodTestingRuleResult> bloodTestingRuleResult = Arrays.asList(aBloodTestingRuleResult()
        .withBloodTypingMatchStatus(BloodTypingMatchStatus.MATCH)
        .withPendingRepeatAndConfirmatoryTtiTestsIds(noTestIds)
        .withRecentResults(recentTestResults)
        .withPendingBloodTypingTestsIds(bloodTypeTestsIds)
        .withPendingConfirmatoryTTITestsIds(noTestIds)
        .withPendingRepeatTTITestsIds(noTestIds)
        .build());

    // Expected Data.
    Map<String, Object> expectedOverviewFlags = new HashMap<String, Object>();
    expectedOverviewFlags.put("hasReEntryRequiredTTITests", false);
    expectedOverviewFlags.put("hasReEntryRequiredBloodTypingTests", false);
    expectedOverviewFlags.put("hasReEntryRequiredRepeatBloodTypingTests", false);
    expectedOverviewFlags.put("hasReEntryRequiredConfirmatoryTTITests", false);
    expectedOverviewFlags.put("hasReEntryRequiredRepeatTTITests", false);
    expectedOverviewFlags.put("hasRepeatBloodTypingTests", true);
    expectedOverviewFlags.put("hasConfirmatoryTTITests", false);
    expectedOverviewFlags.put("hasRepeatTTITests", false);
    expectedOverviewFlags.put("hasPendingRepeatTTITests", false);
    expectedOverviewFlags.put("hasPendingConfirmatoryTTITests", false);
    expectedOverviewFlags.put("hasPendingRepeatBloodTypingTests", true);
    expectedOverviewFlags.put("hasPendingBloodTypingConfirmations", false);

    when(testBatchRepository.findTestBatchById(aTestBatch.getId())).thenReturn(aTestBatch);
    when(testResultController.getBloodTestingRuleResults(aTestBatch)).thenReturn(bloodTestingRuleResult);

    // Test
    ResponseEntity<Map<String, Object>> returnedResponse =
        testResultController.findTestResultsOverviewForTestBatch(request, aTestBatch.getId());

    Map<String, Object> overViewFlags = returnedResponse.getBody();
    Assert.assertNotNull("map is returned", overViewFlags);
    Assert.assertEquals(expectedOverviewFlags, overViewFlags);
  }

  @Test
  public void testFindTestResultsOverviewForTestBatchWithAllPendingTestsAndNoRecentResults_shouldReturnCorrectResults() {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    Set<DonationBatch> donationBatches = new HashSet<>(
        Arrays.asList(aDonationBatch().withId(DONATION_BATCH_ID).withBatchNumber("123").withDonationBatchDate(new Date()).build()));

    TestBatch aTestBatch = aTestBatch().withId(UUID.randomUUID()).withBatchNumber("00001").withCreatedDate(new Date())
        .withStatus(TestBatchStatus.OPEN).withDonationBatches(donationBatches).build();

    Map<Long, BloodTestResultViewModel> recentTestResults = new HashMap<>();

    List<Long> testsIds = Arrays.asList(1L, 2L, 3L);
    List<BloodTestingRuleResult> bloodTestingRuleResult = Arrays.asList(
        aBloodTestingRuleResult()
            .withBloodTypingMatchStatus(BloodTypingMatchStatus.AMBIGUOUS)
            .withPendingRepeatAndConfirmatoryTtiTestsIds(testsIds)
            .withRecentResults(recentTestResults)
            .withPendingBloodTypingTestsIds(testsIds)
            .withPendingConfirmatoryTTITestsIds(testsIds)
            .withPendingRepeatTTITestsIds(testsIds)
            .build());

    // Expected Data.
    Map<String, Object> expectedOverviewFlags = new HashMap<String, Object>();
    expectedOverviewFlags.put("hasReEntryRequiredTTITests", false);
    expectedOverviewFlags.put("hasReEntryRequiredBloodTypingTests", false);
    expectedOverviewFlags.put("hasReEntryRequiredRepeatBloodTypingTests", false);
    expectedOverviewFlags.put("hasReEntryRequiredConfirmatoryTTITests", false);
    expectedOverviewFlags.put("hasReEntryRequiredRepeatTTITests", false);
    expectedOverviewFlags.put("hasRepeatBloodTypingTests", true);
    expectedOverviewFlags.put("hasConfirmatoryTTITests", true);
    expectedOverviewFlags.put("hasRepeatTTITests", true);
    expectedOverviewFlags.put("hasPendingRepeatTTITests", true);
    expectedOverviewFlags.put("hasPendingConfirmatoryTTITests", true);
    expectedOverviewFlags.put("hasPendingRepeatBloodTypingTests", true);
    expectedOverviewFlags.put("hasPendingBloodTypingConfirmations", true);
    
    when(testBatchRepository.findTestBatchById(aTestBatch.getId())).thenReturn(aTestBatch);
    when(testResultController.getBloodTestingRuleResults(aTestBatch)).thenReturn(bloodTestingRuleResult);

    // Test
    ResponseEntity<Map<String, Object>> returnedResponse = testResultController.findTestResultsOverviewForTestBatch(
        request, aTestBatch.getId());

    Map<String, Object> overViewFlags = returnedResponse.getBody();
    Assert.assertNotNull("map is returned", overViewFlags);
    Assert.assertEquals(expectedOverviewFlags,overViewFlags);
  }
  
  @Test
  public void testFindTestResultsOverviewForTestBatchWithAllTestsRequiringReEntry_shouldReturnCorrectResults() {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    Set<DonationBatch> donationBatches = new HashSet<>(Arrays.asList(
        aDonationBatch()
            .withId(DONATION_BATCH_ID)
            .withBatchNumber("123")
            .withDonationBatchDate(new Date())
            .build()
      )
    );
    
    TestBatch aTestBatch = aTestBatch().withId(UUID.randomUUID()).withBatchNumber("00001").withCreatedDate(new Date())
        .withStatus(TestBatchStatus.OPEN).withDonationBatches(donationBatches).build();
    
    BloodTestFullViewModel basicBloodTypingBloodFullTestViewModel = aBasicBloodTypingBloodTestFullViewModel()
        .withId(1L)
        .withBloodTestType(BloodTestType.BASIC_BLOODTYPING)
        .build();
    
    BloodTestResultViewModel bloodTypingTestResultViewModel = aBloodTestResultViewModel()
        .withId(1L)
        .withBloodTest(basicBloodTypingBloodFullTestViewModel)
        .withReEntryRequired()
        .build();
    
    BloodTestFullViewModel basicTTIBloodFullTestViewModel = aBasicTTIBloodTestFullViewModel()
        .withId(2L)
        .withBloodTestType(BloodTestType.BASIC_TTI)
        .build();
    
    BloodTestResultViewModel bloodTTITestResultViewModel = aBloodTestResultViewModel()
        .withId(2L)
        .withBloodTest(basicTTIBloodFullTestViewModel)
        .withReEntryRequired()
        .build();
            
    BloodTestFullViewModel repeatBloodTypingFullTestViewModel = aRepeatBloodTypingBloodTestFullViewModel().withId(3L)
        .withBloodTestType(BloodTestType.REPEAT_BLOODTYPING).build();

    BloodTestResultViewModel repeatBloodTypingTestResultViewModel = aBloodTestResultViewModel()
        .withId(3L)
        .withBloodTest(repeatBloodTypingFullTestViewModel).withReEntryRequired().build();

    BloodTestFullViewModel confirmatoryTTIBloodFullTestViewModel =
        aConfirmatoryTTIBloodTestFullViewModel().withId(4L).withBloodTestType(BloodTestType.CONFIRMATORY_TTI).build();

    BloodTestResultViewModel confirmatoryTTITestResultViewModel = aBloodTestResultViewModel().withId(4L)
        .withBloodTest(confirmatoryTTIBloodFullTestViewModel).withReEntryRequired().build();

    BloodTestFullViewModel repeatTTIFullTestViewModel =
        aRepeatTTIBloodTestFullViewModel().withId(5L).withBloodTestType(BloodTestType.REPEAT_TTI)
        .build();
    
    BloodTestResultViewModel repeatTTITestResultViewModel =
        aBloodTestResultViewModel().withId(5L).withBloodTest(repeatTTIFullTestViewModel)
        .withReEntryRequired()
        .build();
    
    Map<Long, BloodTestResultViewModel> recentTestResults = new HashMap<>();
    recentTestResults.put(1L, bloodTypingTestResultViewModel);
    recentTestResults.put(2L, bloodTTITestResultViewModel);
    recentTestResults.put(3L, repeatBloodTypingTestResultViewModel);
    recentTestResults.put(4L, confirmatoryTTITestResultViewModel);
    recentTestResults.put(5L, repeatTTITestResultViewModel);


    List<Long> noTestIds = new ArrayList<>();
    List<BloodTestingRuleResult> bloodTestingRuleResult = Arrays.asList(
        aBloodTestingRuleResult()
            .withBloodTypingMatchStatus(BloodTypingMatchStatus.MATCH)
            .withPendingRepeatAndConfirmatoryTtiTestsIds(noTestIds)
            .withRecentResults(recentTestResults)
            .withPendingBloodTypingTestsIds(noTestIds)
            .withPendingConfirmatoryTTITestsIds(noTestIds)
            .withPendingRepeatTTITestsIds(noTestIds)
            .build());
  
    // Expected Data.
    Map<String, Object> expectedOverviewFlags = new HashMap<String, Object>();
    expectedOverviewFlags.put("hasReEntryRequiredTTITests", true);
    expectedOverviewFlags.put("hasReEntryRequiredBloodTypingTests", true);
    expectedOverviewFlags.put("hasReEntryRequiredRepeatBloodTypingTests", true);
    expectedOverviewFlags.put("hasReEntryRequiredConfirmatoryTTITests", true);
    expectedOverviewFlags.put("hasReEntryRequiredRepeatTTITests", true);
    expectedOverviewFlags.put("hasRepeatBloodTypingTests", true);
    expectedOverviewFlags.put("hasConfirmatoryTTITests", true);
    expectedOverviewFlags.put("hasRepeatTTITests", true);
    expectedOverviewFlags.put("hasPendingRepeatTTITests", false);
    expectedOverviewFlags.put("hasPendingConfirmatoryTTITests", false);
    expectedOverviewFlags.put("hasPendingRepeatBloodTypingTests", false);
    expectedOverviewFlags.put("hasPendingBloodTypingConfirmations", false);
    
    when(testBatchRepository.findTestBatchById(aTestBatch.getId())).thenReturn(aTestBatch);
    when(testResultController.getBloodTestingRuleResults(aTestBatch)).thenReturn(bloodTestingRuleResult);
    // Test
    ResponseEntity<Map<String, Object>> returnedResponse = testResultController.findTestResultsOverviewForTestBatch(
        request, aTestBatch.getId());

    Map<String, Object> overViewFlags = returnedResponse.getBody();
    Assert.assertNotNull("map is returned", overViewFlags);
    Assert.assertEquals(expectedOverviewFlags,overViewFlags);
  }
}