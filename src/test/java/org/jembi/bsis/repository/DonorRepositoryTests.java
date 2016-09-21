package org.jembi.bsis.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.jembi.bsis.helpers.builders.DonationBuilder.aDonation;
import static org.jembi.bsis.helpers.builders.DonorBuilder.aDonor;
import static org.jembi.bsis.helpers.builders.LocationBuilder.aVenue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.NoResultException;

import org.jembi.bsis.dto.DonorExportDTO;
import org.jembi.bsis.dto.DuplicateDonorDTO;
import org.jembi.bsis.dto.MobileClinicDonorDTO;
import org.jembi.bsis.helpers.builders.AddressBuilder;
import org.jembi.bsis.helpers.builders.AddressTypeBuilder;
import org.jembi.bsis.helpers.builders.ContactBuilder;
import org.jembi.bsis.helpers.builders.ContactMethodTypeBuilder;
import org.jembi.bsis.helpers.builders.DonorBuilder;
import org.jembi.bsis.helpers.builders.IdTypeBuilder;
import org.jembi.bsis.helpers.builders.LocationBuilder;
import org.jembi.bsis.helpers.builders.PreferredLanguageBuilder;
import org.jembi.bsis.helpers.matchers.DonorExportDTOMatcher;
import org.jembi.bsis.model.address.Address;
import org.jembi.bsis.model.address.AddressType;
import org.jembi.bsis.model.address.Contact;
import org.jembi.bsis.model.address.ContactMethodType;
import org.jembi.bsis.model.donor.Donor;
import org.jembi.bsis.model.donor.DonorStatus;
import org.jembi.bsis.model.idtype.IdType;
import org.jembi.bsis.model.location.Location;
import org.jembi.bsis.model.preferredlanguage.PreferredLanguage;
import org.jembi.bsis.model.util.Gender;
import org.jembi.bsis.suites.SecurityContextDependentTestSuite;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DonorRepositoryTests extends SecurityContextDependentTestSuite {
  
  @Autowired
  DonorRepository donorRepository;

  @Test
  public void testFindDuplicateDonorsBasic() throws Exception {
    List<Donor> donors = new ArrayList<Donor>();
    donors.add(DonorBuilder.aDonor().withDonorNumber("1").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    donors.add(DonorBuilder.aDonor().withDonorNumber("2").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    donors.add(DonorBuilder.aDonor().withDonorNumber("3").withFirstName("Jo").withLastName("Smith")
        .withGender(Gender.female).withBirthDate("1978-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    donors.add(DonorBuilder.aDonor().withDonorNumber("4").withFirstName("Nancy").withLastName("Drew")
        .withGender(Gender.female).withBirthDate("1964-11-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    List<DuplicateDonorDTO> duplicateDonors = donorRepository.getDuplicateDonors();
    Assert.assertEquals("One set of matching donors", 1, duplicateDonors.size());
    Assert.assertEquals("Two matching donors", 2, duplicateDonors.get(0).getCount());
    Assert.assertEquals("David is matching Donor", "David", duplicateDonors.get(0).getFirstName());
  }

  @Test
  public void testFindDuplicateDonorsDouble() throws Exception {
    List<Donor> donors = new ArrayList<Donor>();
    donors.add(DonorBuilder.aDonor().withDonorNumber("1").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    donors.add(DonorBuilder.aDonor().withDonorNumber("2").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    donors.add(DonorBuilder.aDonor().withDonorNumber("3").withFirstName("Jo").withLastName("Smith")
        .withGender(Gender.female).withBirthDate("1978-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    donors.add(DonorBuilder.aDonor().withDonorNumber("4").withFirstName("Sue").withLastName("Simpson")
        .withGender(Gender.female).withBirthDate("1982-02-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    donors.add(DonorBuilder.aDonor().withDonorNumber("5").withFirstName("Nancy").withLastName("Drew")
        .withGender(Gender.female).withBirthDate("1964-11-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    donors.add(DonorBuilder.aDonor().withDonorNumber("6").withFirstName("Sue").withLastName("Simpson")
        .withGender(Gender.female).withBirthDate("1982-02-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    donors.add(DonorBuilder.aDonor().withDonorNumber("7").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    List<DuplicateDonorDTO> duplicateDonors = donorRepository.getDuplicateDonors();
    Assert.assertEquals("Two sets of matching donors", 2, duplicateDonors.size());
    Assert.assertEquals("Three matching donors", 3, duplicateDonors.get(0).getCount());
    Assert.assertEquals("David is matching Donor", "David", duplicateDonors.get(0).getFirstName());
    Assert.assertEquals("Two matching donors", 2, duplicateDonors.get(1).getCount());
    Assert.assertEquals("Sue is matching Donor", "Sue", duplicateDonors.get(1).getFirstName());
  }
  
  @Test
  public void testFindDuplicateDonorsMerged() throws Exception {
    List<Donor> donors = new ArrayList<Donor>();
    donors.add(DonorBuilder.aDonor().withDonorNumber("1").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    donors.add(DonorBuilder.aDonor().withDonorNumber("2").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.MERGED)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    List<DuplicateDonorDTO> duplicateDonors = donorRepository.getDuplicateDonors();
    Assert.assertEquals("No duplicate donors", 0, duplicateDonors.size());
  }
  
  @Test
  public void testFindDuplicateDonorsDeleted() throws Exception {
    List<Donor> donors = new ArrayList<Donor>();
    donors.add(DonorBuilder.aDonor().withDonorNumber("1").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager));
    donors.add(DonorBuilder.aDonor().withDonorNumber("3").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsDeleted().buildAndPersist(entityManager));
    List<DuplicateDonorDTO> duplicateDonors = donorRepository.getDuplicateDonors();
    Assert.assertEquals("No duplicate donors", 0, duplicateDonors.size());
  }
  
  @Test
  public void testFindDuplicateDonorsMatchingOneBasic() throws Exception {
    Donor donor = DonorBuilder.aDonor().withDonorNumber("1").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager);
    DonorBuilder.aDonor().withDonorNumber("2").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager);
    DonorBuilder.aDonor().withDonorNumber("3").withFirstName("Jo").withLastName("Smith")
        .withGender(Gender.female).withBirthDate("1978-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager);
    DonorBuilder.aDonor().withDonorNumber("4").withFirstName("Nancy").withLastName("Drew")
        .withGender(Gender.female).withBirthDate("1964-11-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager);

    List<Donor> duplicateDonors = donorRepository.getDuplicateDonors(donor.getFirstName(), donor.getLastName(), 
        donor.getBirthDate(), donor.getGender());
    
    Assert.assertEquals("Two matching donors", 2, duplicateDonors.size());
    Assert.assertEquals("David is matching Donor", "David", duplicateDonors.get(0).getFirstName());
    Assert.assertEquals("David is matching Donor", "David", duplicateDonors.get(1).getFirstName());
  }

  @Test
  public void testFindDuplicateMatchingOneDonorsDouble() throws Exception {
    DonorBuilder.aDonor().withDonorNumber("1").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager);
    DonorBuilder.aDonor().withDonorNumber("2").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager);
    DonorBuilder.aDonor().withDonorNumber("3").withFirstName("Jo").withLastName("Smith")
        .withGender(Gender.female).withBirthDate("1978-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager);
    DonorBuilder.aDonor().withDonorNumber("4").withFirstName("Sue").withLastName("Simpson")
        .withGender(Gender.female).withBirthDate("1982-02-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager);
    DonorBuilder.aDonor().withDonorNumber("5").withFirstName("Nancy").withLastName("Drew")
        .withGender(Gender.female).withBirthDate("1964-11-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager);
    Donor donor = DonorBuilder.aDonor().withDonorNumber("6").withFirstName("Sue").withLastName("Simpson")
        .withGender(Gender.female).withBirthDate("1982-02-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager);
    DonorBuilder.aDonor().withDonorNumber("7").withFirstName("David").withLastName("Smith")
        .withGender(Gender.male).withBirthDate("1977-10-20").withDonorStatus(DonorStatus.NORMAL)
        .thatIsNotDeleted().buildAndPersist(entityManager);
    DonorBuilder.aDonor().withDonorNumber("8").withFirstName("Sue").withLastName("Simpson")
        .withGender(Gender.female).withBirthDate("1982-02-20").withDonorStatus(DonorStatus.MERGED)
        .thatIsNotDeleted().buildAndPersist(entityManager);

    List<Donor> duplicateDonors = donorRepository.getDuplicateDonors(donor.getFirstName(), donor.getLastName(), 
        donor.getBirthDate(), donor.getGender());

    Assert.assertEquals("Two matching donors", 2, duplicateDonors.size());
    Assert.assertEquals("Sue is matching Donor", "Sue", duplicateDonors.get(0).getFirstName());
    Assert.assertEquals("Sue is matching Donor", "Sue", duplicateDonors.get(1).getFirstName());
  }
  
  @Test
  public void testMobileClinicDonorsCanBeFound() throws Exception {

    Location venue = LocationBuilder.aLocation()
        .withName("test")
        .buildAndPersist(entityManager);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DonorBuilder.aDonor()
        .withDonorNumber("D1")
        .withFirstName("Clara")
        .withLastName("Donor")
        .withBirthDate(sdf.parse("20/02/1975"))
        .withGender(Gender.female)
        .withDonorStatus(DonorStatus.NORMAL)
        .withVenue(venue)
        .thatIsNotDeleted()
        .buildAndPersist(entityManager);
    DonorBuilder.aDonor()
        .withDonorNumber("D2")
        .withFirstName("Bobby")
        .withLastName("ADonor")
        .withBirthDate(sdf.parse("5/12/1982"))
        .withGender(Gender.male)
        .withDonorStatus(DonorStatus.NORMAL)
        .withVenue(venue)
        .thatIsNotDeleted()
        .buildAndPersist(entityManager);
    DonorBuilder.aDonor()
        .withDonorNumber("D3")
        .withFirstName("Abigail")
        .withLastName("Donor")
        .withBirthDate(sdf.parse("10/10/1985"))
        .withGender(Gender.female)
        .withDonorStatus(DonorStatus.NORMAL)
        .withVenue(venue)
        .thatIsNotDeleted()
        .buildAndPersist(entityManager);

    List<MobileClinicDonorDTO> mobileClinicDonorDTOs = donorRepository.findMobileClinicDonorsByVenue(new HashSet<Long>(Arrays.asList(venue.getId())));

    assertThat("Correct number of MobileClinicDonors returned", mobileClinicDonorDTOs.size(), is(3));
    // check sorting
    MobileClinicDonorDTO returnedDonor1 = mobileClinicDonorDTOs.get(0);
    Assert.assertEquals("MobileClinicDonor sorting is correct", "D2", returnedDonor1.getDonorNumber());
    MobileClinicDonorDTO returnedDonor2 = mobileClinicDonorDTOs.get(1);
    Assert.assertEquals("MobileClinicDonor sorting is correct", "D3", returnedDonor2.getDonorNumber());
    MobileClinicDonorDTO returnedDonor3 = mobileClinicDonorDTOs.get(2);
    Assert.assertEquals("MobileClinicDonor sorting is correct", "D1", returnedDonor3.getDonorNumber());
  }

  @Test
  public void testDeletedMobileClinicDonorsAreNotReturned() throws Exception {

    Location venue = LocationBuilder.aLocation()
        .withName("test")
        .buildAndPersist(entityManager);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DonorBuilder.aDonor()
        .withDonorNumber("D1")
        .withFirstName("Test")
        .withLastName("DonorOne")
        .withBirthDate(sdf.parse("20/02/1975"))
        .withGender(Gender.female)
        .withDonorStatus(DonorStatus.NORMAL)
        .withVenue(venue)
        .thatIsNotDeleted()
        .buildAndPersist(entityManager);
    Donor donor2 = DonorBuilder.aDonor()
        .withDonorNumber("D2")
        .withFirstName("Test")
        .withLastName("DonorTwo")
        .withBirthDate(sdf.parse("5/12/1982"))
        .withGender(Gender.male)
        .withDonorStatus(DonorStatus.NORMAL)
        .withVenue(venue)
        .thatIsDeleted()
        .buildAndPersist(entityManager);

    List<MobileClinicDonorDTO> mobileClinicDonorDTOs = donorRepository.findMobileClinicDonorsByVenue(new HashSet<Long>(Arrays.asList(venue.getId())));

    assertThat("Correct number of MobileClinicDonors returned", mobileClinicDonorDTOs.size(), is(1));
    Assert.assertFalse("Deleted MobileClinicDonor not returned", mobileClinicDonorDTOs.contains(donor2));
  }

  @Test
  public void testMergedMobileClinicDonorsAreNotReturned() throws Exception {

    Location venue = LocationBuilder.aLocation()
        .withName("test")
        .buildAndPersist(entityManager);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DonorBuilder.aDonor()
        .withDonorNumber("D1")
        .withFirstName("Test")
        .withLastName("DonorOne")
        .withBirthDate(sdf.parse("20/02/1975"))
        .withGender(Gender.female)
        .withDonorStatus(DonorStatus.NORMAL)
        .withVenue(venue)
        .thatIsNotDeleted()
        .buildAndPersist(entityManager);
    Donor donor2 = DonorBuilder.aDonor()
        .withDonorNumber("D2")
        .withFirstName("Test")
        .withLastName("DonorTwo")
        .withBirthDate(sdf.parse("5/12/1982"))
        .withGender(Gender.male)
        .withDonorStatus(DonorStatus.MERGED)
        .withVenue(venue)
        .thatIsNotDeleted()
        .buildAndPersist(entityManager);

    List<MobileClinicDonorDTO> mobileClinicDonorDTOs = donorRepository.findMobileClinicDonorsByVenue(new HashSet<Long>(Arrays.asList(venue.getId())));

    assertThat("Correct number of MobileClinicDonors returned", mobileClinicDonorDTOs.size(), is(1));
    Assert.assertFalse("Deleted MobileClinicDonor not returned", mobileClinicDonorDTOs.contains(donor2));
  }

  @Test
  public void testMobileClinicDonorsAreInCorrectVenue() throws Exception {

    Location venue1 = LocationBuilder.aLocation()
        .withName("test1")
        .buildAndPersist(entityManager);
    Location venue2 = LocationBuilder.aLocation()
        .withName("test2")
        .buildAndPersist(entityManager);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DonorBuilder.aDonor()
        .withDonorNumber("D1")
        .withFirstName("Test")
        .withLastName("DonorOne")
        .withBirthDate(sdf.parse("20/02/1975"))
        .withGender(Gender.female)
        .withDonorStatus(DonorStatus.NORMAL)
        .withVenue(venue1)
        .thatIsNotDeleted()
        .buildAndPersist(entityManager);
    DonorBuilder.aDonor()
        .withDonorNumber("D2")
        .withFirstName("Test")
        .withLastName("DonorTwo")
        .withBirthDate(sdf.parse("5/12/1982"))
        .withGender(Gender.male)
        .withDonorStatus(DonorStatus.NORMAL)
        .withVenue(venue2)
        .thatIsNotDeleted()
        .buildAndPersist(entityManager);

    List<MobileClinicDonorDTO> mobileClinicDonorDTOs = donorRepository.findMobileClinicDonorsByVenue(new HashSet<Long>(Arrays.asList(venue1.getId())));

    assertThat("Correct number of MobileClinicDonors returned", mobileClinicDonorDTOs.size(), is(1));
    for (MobileClinicDonorDTO d : mobileClinicDonorDTOs) {
      Assert.assertEquals("MobileClinicDonor in correct venue", venue1, d.getVenue());
    }
  }

  @Test
  public void testFindDonorByDonorNumberWithExistingDonor_shouldReturnDonor() {
    // Set up
    String donorNumber = "000001";
    Donor expectedDonor = aDonor().withDonorNumber(donorNumber).buildAndPersist(entityManager);
    aDonor().withDonorNumber("667754").buildAndPersist(entityManager);

    // Test
    Donor returnedDonor = donorRepository.findDonorByDonorNumber(donorNumber);

    // Verify
    assertThat(returnedDonor, is(expectedDonor));
  }

  @Test(expected = NoResultException.class)
  public void testFindDonorByDonorNumberWithNoExistingDonor_shouldThrowNoResultException() {
    donorRepository.findDonorByDonorNumber("000001");
  }

  @Test(expected = NoResultException.class)
  public void testFindDonorByDonorNumberWithMergedDonor_shouldThrowNoResultException() {
    DonorBuilder.aDonor().withDonorStatus(DonorStatus.MERGED).withDonorNumber("000001").buildAndPersist(entityManager);
    donorRepository.findDonorByDonorNumber("000001");
  }

  @Test(expected = NoResultException.class)
  public void testFindDonorByDonorNumberWithDeletedDonor_shouldThrowNoResultException() {
    DonorBuilder.aDonor().thatIsDeleted().withDonorNumber("000001").buildAndPersist(entityManager);
    donorRepository.findDonorByDonorNumber("000001");
  }

  @Test
  public void testFindDonorByDonationIdentificationNumberWithExistingDonor_shouldReturnDonor() {
    // Set up
    String donationIdentificationNumber = "0000001";
    Donor expectedDonor = aDonor().buildAndPersist(entityManager);
    aDonation()
        .withDonationIdentificationNumber(donationIdentificationNumber)
        .withDonor(expectedDonor)
        .buildAndPersist(entityManager);
    aDonation()
        .withDonationIdentificationNumber("5687411")
        .withDonor(aDonor().build())
        .buildAndPersist(entityManager);

    // Test
    Donor returnedDonor = donorRepository.findDonorByDonationIdentificationNumber(donationIdentificationNumber);

    // Verify
    assertThat(returnedDonor, is(expectedDonor));
  }

  @Test(expected = NoResultException.class)
  public void testFindDonorByDonationIdentificationNumberWithNoExistingDonor_shouldThrowNoResultException() {
    donorRepository.findDonorByDonationIdentificationNumber("0000001");

  }
  
  @Test(expected = NoResultException.class)
  public void testFindDonorByDonationIdentificationNumberWithMergedStatus_shouldThrowNoResultException() {
    String din = "0000001";
    Donor mergedDonor = aDonor().withDonorStatus(DonorStatus.MERGED).buildAndPersist(entityManager);
    aDonation()
      .withDonationIdentificationNumber(din)
      .withDonor(mergedDonor)
      .buildAndPersist(entityManager);
    donorRepository.findDonorByDonationIdentificationNumber(din);
  }
  
  @Test(expected = NoResultException.class)
  public void testFindDonorByDonationIdentificationNumberThatIsDeleted_shouldThrowNoResultException() {
    String din = "0000001";
    Donor deletedDonor = aDonor().thatIsDeleted().buildAndPersist(entityManager);
    aDonation()
      .withDonationIdentificationNumber(din)
      .withDonor(deletedDonor)
      .buildAndPersist(entityManager);
    donorRepository.findDonorByDonationIdentificationNumber(din);
  }

  @Test
  public void testVerifyDonorExists() {
    Donor donor = DonorBuilder.aDonor().buildAndPersist(entityManager);
    Assert.assertTrue("Donor exists", donorRepository.verifyDonorExists(donor.getId()));
  }

  @Test
  public void testVerifyDonorExistsWithInvalidId_shouldNotExist() {
    Assert.assertFalse("Donor does not exist", donorRepository.verifyDonorExists(123L));
  }

  @Test
  public void testVerifyDonorExistsWithMergedStatus_shouldNotExist() {
    Donor mergedDonor = DonorBuilder.aDonor().withDonorStatus(DonorStatus.MERGED).buildAndPersist(entityManager);
    Assert.assertFalse("Donor does not exist", donorRepository.verifyDonorExists(mergedDonor.getId()));
  }

  @Test
  public void testVerifyDonorExistsThatIsDeleted_shouldNotExist() {
    Donor deletedDonor = DonorBuilder.aDonor().thatIsDeleted().buildAndPersist(entityManager);
    Assert.assertFalse("Donor does not exist", donorRepository.verifyDonorExists(deletedDonor.getId()));
  }

  @Test
  public void testFindDonorsForExport_shouldReturnDonorsExportDTOsThatAreNotDeleted() {
    String donorNumber = "1234567";
    String title = "Ms";
    String firstName = "A";
    String middleName = "Sample";
    String lastName = "Donor";
    String callingName = "Sue";
    Gender gender = Gender.female;
    Date birthDate = new DateTime(1977,10,20,0,0,0).toDate();
    PreferredLanguage language = PreferredLanguageBuilder.anEnglishPreferredLanguage().buildAndPersist(entityManager);
    Location venue = aVenue().withName("DonateHere").buildAndPersist(entityManager);
    String bloodAbo = "A";
    String bloodRh = "+";
    String notes = "noted";
    IdType idType = IdTypeBuilder.aNationalId().buildAndPersist(entityManager);
    String idNumber = "77102089390328";
    Date dateOfFirstDonation = new LocalDate(2000,1,1).toDate();
    Date dateOfLastDonation = new DateTime(2016,9,9,9,9,9).toDate();
    Date dueToDonate = new DateTime(2019,12,12,0,0,0).toDate();
    String homeAddressLine1 = "123 Apartment";
    String homeAddressLine2 = "4 Street Rd";
    String city = "Cape Town";
    String province = "Western Cape";
    String district = "Cape Peninsula";
    String state = "Western Cape";
    String country = "South Africa";
    String zipcode = "8001";
    String workAddressLine1 = "Office 123";
    String workAddressLine2 = "4 Road Avenue";
    String postalAddressLine1 = "P.O. Box 1234";
    String postalAddressLine2 = "Centre";
    Address address = AddressBuilder.anAddress()
          .withHomeAddress(homeAddressLine1, homeAddressLine2, city, province, district, country, state, zipcode)
          .withWorkAddress(workAddressLine1, workAddressLine2, city, province, district, country, state, zipcode)
          .withPostalAddress(postalAddressLine1, postalAddressLine2, city, province, district, country, state, zipcode)
          .buildAndPersist(entityManager);
    AddressType addressType = AddressTypeBuilder.aHomeAddressType().buildAndPersist(entityManager);
    String homeNumber = "0214561212";
    String workNumber = "0211234567";
    String mobileNumber = "0734567827";
    String email = "email@jembi.org";
    Contact contact = ContactBuilder.aContact()
          .withHomeNumber(homeNumber)
          .withWorkNumber(workNumber)
          .withMobileNumber(mobileNumber)
          .withEmail(email)
          .buildAndPersist(entityManager);
    ContactMethodType contactMethodType = ContactMethodTypeBuilder.anEmailContactMethodType().buildAndPersist(entityManager);
    
    DonorExportDTO expectedDonorDTO = new DonorExportDTO(donorNumber, new Date(), USERNAME, new Date(), USERNAME,
        title, firstName, middleName, lastName, callingName, gender,
        birthDate, language.getPreferredLanguage(), venue.getName(), bloodAbo, bloodRh, notes,
        idType.getIdType(), idNumber, dateOfFirstDonation, dateOfLastDonation, dueToDonate,
        contactMethodType.getContactMethodType(), mobileNumber, homeNumber, workNumber, email,
        addressType.getPreferredAddressType(), 
        homeAddressLine1, homeAddressLine2, city, province, district, country, state, zipcode, 
        workAddressLine1, workAddressLine2, city, province, district, country, state, zipcode, 
        postalAddressLine1, postalAddressLine2, city, province, district, country, state, zipcode);
    
    // Expected Donor
    aDonor()
      .withDonorNumber(donorNumber)
      .withTitle(title)
      .withFirstName(firstName)
      .withMiddleName(middleName)
      .withLastName(lastName)
      .withCallingName(callingName)
      .withGender(gender)
      .withBirthDate(birthDate)
      .withVenue(venue)
      .withPreferredLanguage(language)
      .withBloodAbo(bloodAbo)
      .withBloodRh(bloodRh)
      .withNotes(notes)
      .withIdType(idType)
      .withIdNumber(idNumber)
      .withDateOfFirstDonation(dateOfFirstDonation)
      .withDateOfLastDonation(dateOfLastDonation)
      .withDueToDonate(dueToDonate)
      .withContact(contact)
      .withAddress(address)
      .withAddressType(addressType)
      .withContactMethodType(contactMethodType)
      .thatIsNotDeleted()
      .buildAndPersist(entityManager);
    
    // Deleted donor (excluded from export)
    aDonor()
      .thatIsDeleted()
      .buildAndPersist(entityManager);
    
    List<DonorExportDTO> exportedDonors = donorRepository.findDonorsForExport();
    
    // Verify
    assertThat(exportedDonors.size(), is(1));
    
    // Assert state
    assertThat(exportedDonors.get(0), is(DonorExportDTOMatcher.hasSameStateAsDonorExport(expectedDonorDTO)));
  }
  
  @Test
  public void testFindDonorsForExportWithNullAddressTypeAndContactType_shouldReturnDonorsExportDTO() {
    String donorNumber = "1234567";
    String title = "Ms";
    String firstName = "A";
    String middleName = "Sample";
    String lastName = "Donor";
    String callingName = "Sue";
    Gender gender = Gender.female;
    Date birthDate = new DateTime(1977,10,20,0,0,0).toDate();
    PreferredLanguage language = PreferredLanguageBuilder.anEnglishPreferredLanguage().buildAndPersist(entityManager);
    Location venue = aVenue().withName("DonateHere").buildAndPersist(entityManager);
    String bloodAbo = "A";
    String bloodRh = "+";
    String notes = "noted";
    IdType idType = IdTypeBuilder.aNationalId().buildAndPersist(entityManager);
    String idNumber = "77102089390328";
    Date dateOfFirstDonation = new LocalDate(2000,1,1).toDate();
    Date dateOfLastDonation = new DateTime(2016,9,9,9,9,9).toDate();
    Date dueToDonate = new DateTime(2019,12,12,0,0,0).toDate();
    String homeAddressLine1 = "123 Apartment";
    String homeAddressLine2 = "4 Street Rd";
    String city = "Cape Town";
    String province = "Western Cape";
    String district = "Cape Peninsula";
    String state = "Western Cape";
    String country = "South Africa";
    String zipcode = "8001";
    String workAddressLine1 = "Office 123";
    String workAddressLine2 = "4 Road Avenue";
    String postalAddressLine1 = "P.O. Box 1234";
    String postalAddressLine2 = "Centre";
    Address address = AddressBuilder.anAddress()
          .withHomeAddress(homeAddressLine1, homeAddressLine2, city, province, district, country, state, zipcode)
          .withWorkAddress(workAddressLine1, workAddressLine2, city, province, district, country, state, zipcode)
          .withPostalAddress(postalAddressLine1, postalAddressLine2, city, province, district, country, state, zipcode)
          .buildAndPersist(entityManager);
    String homeNumber = "0214561212";
    String workNumber = "0211234567";
    String mobileNumber = "0734567827";
    String email = "email@jembi.org";
    Contact contact = ContactBuilder.aContact()
          .withHomeNumber(homeNumber)
          .withWorkNumber(workNumber)
          .withMobileNumber(mobileNumber)
          .withEmail(email)
          .buildAndPersist(entityManager);
    
    DonorExportDTO expectedDonorDTO = new DonorExportDTO(donorNumber, new Date(), USERNAME, new Date(), USERNAME,
        title, firstName, middleName, lastName, callingName, gender,
        birthDate, language.getPreferredLanguage(), venue.getName(), bloodAbo, bloodRh, notes,
        idType.getIdType(), idNumber, dateOfFirstDonation, dateOfLastDonation, dueToDonate,
        (String)null, mobileNumber, homeNumber, workNumber, email,
        (String)null, homeAddressLine1, homeAddressLine2, city, province, district, country, state, zipcode, 
        workAddressLine1, workAddressLine2, city, province, district, country, state, zipcode, 
        postalAddressLine1, postalAddressLine2, city, province, district, country, state, zipcode);
    
    // Expected Donor
    aDonor()
      .withDonorNumber(donorNumber)
      .withTitle(title)
      .withFirstName(firstName)
      .withMiddleName(middleName)
      .withLastName(lastName)
      .withCallingName(callingName)
      .withGender(gender)
      .withBirthDate(birthDate)
      .withVenue(venue)
      .withPreferredLanguage(language)
      .withBloodAbo(bloodAbo)
      .withBloodRh(bloodRh)
      .withNotes(notes)
      .withIdType(idType)
      .withIdNumber(idNumber)
      .withDateOfFirstDonation(dateOfFirstDonation)
      .withDateOfLastDonation(dateOfLastDonation)
      .withDueToDonate(dueToDonate)
      .withContact(contact)
      .withAddress(address)
      .thatIsNotDeleted()
      .buildAndPersist(entityManager);
    
    List<DonorExportDTO> exportedDonors = donorRepository.findDonorsForExport();
    
    // Verify
    assertThat(exportedDonors.size(), is(1));
    
    // Assert state
    assertThat(exportedDonors.get(0), is(DonorExportDTOMatcher.hasSameStateAsDonorExport(expectedDonorDTO)));
  }
  
  @Test
  public void testFindDonorsForExport_shouldReturnDonorsExportDTOInCorrectOrder() {
    Location venue = aVenue().withName("DonateHere").buildAndPersist(entityManager);

    String donorNumber1 = "1234567";
    Date createdDate1 = new DateTime(2016,9,9,11,44).toDate();

    String donorNumber2 = "1234568";
    Date createdDate2 = new DateTime(2016,9,1,15,30).toDate();
 
    // Expected Donor #1
    aDonor()
      .withDonorNumber(donorNumber1)
      .withFirstName("Sample")
      .withLastName("Donor")
      .withGender(Gender.female)
      .withVenue(venue)
      .withCreatedDate(createdDate1)
      .thatIsNotDeleted()
      .buildAndPersist(entityManager);
    
    // Expected Donor #2
    aDonor()
      .withDonorNumber(donorNumber2)
      .withFirstName("Sample")
      .withMiddleName("Too")
      .withLastName("Donor")
      .withGender(Gender.male)
      .withVenue(venue)
      .withCreatedDate(createdDate2)
      .thatIsNotDeleted()
      .buildAndPersist(entityManager);
    
    List<DonorExportDTO> exportedDonors = donorRepository.findDonorsForExport();
    
    // Verify
    assertThat(exportedDonors.size(), is(2));
    
    // Assert state
    assertThat(exportedDonors.get(0).getDonorNumber(), is(donorNumber2));
    assertThat(exportedDonors.get(1).getDonorNumber(), is(donorNumber1));
  }
}
