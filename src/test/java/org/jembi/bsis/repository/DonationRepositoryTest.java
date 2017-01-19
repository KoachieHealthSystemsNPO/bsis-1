package org.jembi.bsis.repository;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.jembi.bsis.model.donation.Donation;
import org.jembi.bsis.suites.DBUnitContextDependentTestSuite;
import org.jembi.bsis.viewmodel.BloodTestingRuleResult;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test using DBUnit to test the Donation Repository
 */
public class DonationRepositoryTest extends DBUnitContextDependentTestSuite {

  @Autowired
  DonationRepository donationRepository;

  @Override
  protected IDataSet getDataSet() throws Exception {
    File file = new File("src/test/resources/dataset/DonationRepositoryDataset.xml");
    return new FlatXmlDataSetBuilder().setColumnSensing(true).build(file);
  }

  @Test
  public void testFindDonationById() throws Exception {
    Donation donation = donationRepository.findDonationById(1L);
    Assert.assertNotNull("There is a donation with id 1", donation);
    Assert.assertEquals("The donation has a DIN of 1234567", "1234567", donation.getDonationIdentificationNumber());
  }

  @Test
  public void testFindDonationByDIN() throws Exception {
    Donation donation = donationRepository.findDonationByDonationIdentificationNumber("1234567");
    Assert.assertNotNull("There is a donation with DIN 1234567", donation);
    Assert.assertEquals("The donation has a DIN of 1234567", "1234567", donation.getDonationIdentificationNumber());
  }

  @Test
  public void testVerifyDonationIdentificationNumber() throws Exception {
    Donation donation = donationRepository.verifyDonationIdentificationNumber("1234567");
    Assert.assertNotNull("There is a donation with DIN 1234567", donation);
    Assert.assertEquals("The donation has a DIN of 1234567", "1234567", donation.getDonationIdentificationNumber());
  }

  @Test
  @Ignore("This test fails because a javax.persistence.NoResultException is thrown. I believe this is a bug as the method wants to return null")
  public void testVerifyDonationIdentificationNumberUnknown() throws Exception {
    Donation donation = donationRepository.verifyDonationIdentificationNumber("999999999");
    Assert.assertNull("There is no donation with DIN 999999999", donation);
  }

  @Test
  @Ignore("This test will fail - see above test")
  public void testVerifyDonationIdentificationNumbers() throws Exception {
    // this test will fail as soon as one of the DINs in the list is unknown - it will throw an
    // exception and the method will not return any results for the other DINs.
  }

  @Test(expected = javax.persistence.NoResultException.class)
  public void testFindDonationByIdUnknown() throws Exception {
    donationRepository.findDonationById(123L);
  }

  @Test
  public void testGetDonations() throws Exception {
    Date start = new SimpleDateFormat("yyyy-MM-dd").parse("2015-02-01");
    Date end = new SimpleDateFormat("yyyy-MM-dd").parse("2015-02-10");
    List<Donation> all = donationRepository.getDonations(start, end);
    Assert.assertNotNull("There are donations", all);
    Assert.assertEquals("There are 5 donations", 5, all.size());
  }

  @Test
  public void testGetDonationsNone() throws Exception {
    Date start = new SimpleDateFormat("yyyy-MM-dd").parse("2012-02-01");
    Date end = new SimpleDateFormat("yyyy-MM-dd").parse("2012-02-10");
    List<Donation> all = donationRepository.getDonations(start, end);
    Assert.assertNotNull("There are no donations but list is not null", all);
    Assert.assertEquals("There are 0 donations", 0, all.size());
  }

  @Test
  public void testFindNumberOfDonationsDaily() throws Exception {
    Date donationDateFrom = new SimpleDateFormat("yyyy-MM-dd").parse("2015-02-01");
    Date donationDateTo = new SimpleDateFormat("yyyy-MM-dd").parse("2015-02-10");
    List<String> venues = new ArrayList<String>();
    venues.add("1");
    venues.add("2");
    List<String> bloodGroups = new ArrayList<String>();
    bloodGroups.add("A-");
    Map<String, Map<Long, Long>> results = donationRepository.findNumberOfDonations(donationDateFrom, donationDateTo,
        "daily", venues, bloodGroups);
    Assert.assertEquals("One blood type searched", 1, results.size());
    Map<Long, Long> aResults = results.get("A-");
    Assert.assertEquals("10 days in the searched period", 10, aResults.size());
    Date formattedDate = new SimpleDateFormat("dd/MM/yyyy").parse("02/02/2015");
    Long secondOfFebResults = aResults.get(formattedDate.getTime());
    Assert.assertEquals("2 A- donations on 2015-02-02", new Long(2), secondOfFebResults);
  }

  @Test
  public void testFindNumberOfDonationsMonthly() throws Exception {
    Date donationDateFrom = new SimpleDateFormat("yyyy-MM-dd").parse("2015-02-01");
    Date donationDateTo = new SimpleDateFormat("yyyy-MM-dd").parse("2015-03-10");
    List<String> venues = new ArrayList<String>();
    venues.add("1");
    venues.add("2");
    List<String> bloodGroups = new ArrayList<String>();
    bloodGroups.add("A-");
    bloodGroups.add("A+");
    bloodGroups.add("O-");
    bloodGroups.add("O+");
    Map<String, Map<Long, Long>> results = donationRepository.findNumberOfDonations(donationDateFrom, donationDateTo,
        "monthly", venues, bloodGroups);
    Assert.assertEquals("Four blood type searched", 4, results.size());
    Date formattedDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2015");
    Map<Long, Long> aMinusResults = results.get("A-");
    Assert.assertEquals("2 months in the searched period", 2, aMinusResults.size());
    Long aMinusFebResults = aMinusResults.get(formattedDate.getTime());
    Assert.assertEquals("2 A- donations in Feb", new Long(2), aMinusFebResults);
    Map<Long, Long> aPlusResults = results.get("A+");
    Long aPlusFebResults = aPlusResults.get(formattedDate.getTime());
    Assert.assertEquals("0 A+ donations in Feb", new Long(0), aPlusFebResults);
  }

  @Test
  public void testFindNumberOfDonationsYearly() throws Exception {
    Date donationDateFrom = new SimpleDateFormat("yyyy-MM-dd").parse("2015-02-01");
    Date donationDateTo = new SimpleDateFormat("yyyy-MM-dd").parse("2015-02-10");
    List<String> venues = new ArrayList<String>();
    venues.add("1");
    venues.add("2");
    List<String> bloodGroups = new ArrayList<String>();
    bloodGroups.add("AB-");
    bloodGroups.add("AB+");
    Map<String, Map<Long, Long>> results = donationRepository.findNumberOfDonations(donationDateFrom, donationDateTo,
        "yearly", venues, bloodGroups);
    Assert.assertEquals("2 blood type searched", 2, results.size());
    Date formattedDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2015");
    Map<Long, Long> abMinusResults = results.get("AB-");
    Long abMinus2015Results = abMinusResults.get(formattedDate.getTime());
    Assert.assertEquals("0 AB- donations in 2015", new Long(0), abMinus2015Results);
    Map<Long, Long> abPlusResults = results.get("AB+");
    Long abPlus2015Results = abPlusResults.get(formattedDate.getTime());
    Assert.assertEquals("1 AB+ donations in 2015", new Long(1), abPlus2015Results);
  }

  @Test
  public void testFilterDonationsWithBloodTypingResults() throws Exception {
    List<Donation> donations = new ArrayList<Donation>();
    donations.add(donationRepository.findDonationById(1L));
    donations.add(donationRepository.findDonationById(2L));
    donations.add(donationRepository.findDonationById(3L));
    Map<Long, BloodTestingRuleResult> result = donationRepository.filterDonationsWithBloodTypingResults(donations);
    Assert.assertEquals("There are two donations with completed tests", 2, result.size());
    for (BloodTestingRuleResult r : result.values()) {
      if (r.getDonation().getDonationIdentificationNumber().equals("1234567")) {
        Assert.assertEquals("O type blood match", "O", r.getBloodAbo());
      } else if (r.getDonation().getDonationIdentificationNumber().equals("1212129")) {
        Assert.assertEquals("A type blood match", "A", r.getBloodAbo());
      }
    }
  }

  @Test
  public void testAddDonation() throws Exception {
    Donation newDonation = new Donation();
    Donation existingDonation = donationRepository.findDonationById(1L);
    newDonation.setId(existingDonation.getId());
    newDonation.copy(existingDonation);
    newDonation.setId(null); // don't want to override, just save time with the copy
    newDonation.setDonationIdentificationNumber("JUNIT123");
    Calendar today = Calendar.getInstance();
    newDonation.setCreatedDate(today.getTime());
    newDonation.setBleedEndTime(today.getTime());
    today.add(Calendar.MINUTE, -15);
    newDonation.setBleedStartTime(today.getTime());
    donationRepository.saveDonation(newDonation);
    Donation savedDonation = donationRepository.findDonationByDonationIdentificationNumber("JUNIT123");
    Assert.assertNotNull("Found new donation", savedDonation);
    Assert.assertNotNull("Donor date of lastDonation has been set", savedDonation.getDonor().getDateOfLastDonation());
  }

  @Test(expected = javax.persistence.PersistenceException.class)
  public void testAddDonationWithSameDIN_shouldThrow() throws Exception {
    Donation updatedDonation = new Donation();
    Donation existingDonation = donationRepository.findDonationById(1L);
    updatedDonation.setId(existingDonation.getId());
    updatedDonation.copy(existingDonation);
    updatedDonation.setId(null); // don't want to override, just save time with the copy
    donationRepository.saveDonation(updatedDonation);
    // should fail because DIN already exists
  }

  @Test
  public void testUpdateDonation() throws Exception {
    Donation existingDonation = donationRepository.findDonationById(1L);
    existingDonation.setDonorWeight(new BigDecimal(123));
    donationRepository.updateDonation(existingDonation);
    Donation updatedDonation = donationRepository.findDonationById(1L);
    Assert.assertEquals("donor weight was updataed", new BigDecimal(123), updatedDonation.getDonorWeight());
  }

}
