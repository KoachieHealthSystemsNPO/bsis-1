package repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.donor.Donor;
import model.donordeferral.DeferralReason;
import model.donordeferral.DonorDeferral;
import model.user.User;
import model.util.BloodGroup;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import security.LoginUserService;
import security.V2VUserDetails;
import backingform.DonorBackingForm;
import backingform.validator.DonorBackingFormValidator;
import controller.UtilController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:**/v2v-servlet.xml")
@WebAppConfiguration
public class DonorRepositoryTest {
	@SuppressWarnings("unused")
	private MockHttpServletRequest request;
	private Validator validator;
	@Autowired
	private UtilController utilController;
	@Autowired
	DonorRepository donorRepository;
	@Autowired
	private SequenceNumberRepository sequenceNumberRepository;
	private User user;
	private Donor donor;
	private long dbid, deletedbid, updatedbid, donoridfordefer;
	private String donorNumber;
	private DonorBackingForm donorBackingForm;
	private DonorBackingFormValidator donorBackingFormValidator;
	String donorBirthdate = null;
	private BindException errors;
	ApplicationContext applicationContext = null;
	UserDetailsService userDetailsService;

	/**
	 * This method is useful to configure instance variable value which is used by different test case .
	 */
	@Before
	public void init() {
		dbid = 218;// For Find Record set db id here.
		donoridfordefer = 230;// Donor Id for defer list
		deletedbid = 223; // Delete datbase record set db id here.
		updatedbid = 224;// For update record set db id here.
		donorNumber = "000311";
		donorBirthdate = "10/06/1989";
		validator = new DonorBackingFormValidator();
		donorBackingFormValidator = new DonorBackingFormValidator(validator,
				utilController);
		donor = new Donor();
		donorBackingForm = new DonorBackingForm(donor);
		user = new User();
		user.setId(2);
		

	}

	/**
	 * Purpose: Test addDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * Here we can pass donor firstname value is blank.
	 * Expected Result:Donor object should not persist.
	 */
	@Test
	public void testAddDonorFirstNameBlank() {
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setFirstName("");//Here we can pass donor firstname value is blank.
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.addDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	
	/**
	 * Purpose: Test addDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * Last Name should not null.
	 * Here we can pass donor lastname value is blank.
	 * Expected Result:Donor object should not persist.
	 */
	@Test
	public void testAddDonorLastNameBlank() {
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setLastName("");//Here we can pass donor lastname value is blank.
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//Last Name is blank,donor object should not persist.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.addDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	/**
	 * Purpose: Test addDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * DonorNumber should be unique and not null.
	 * Here we can pass duplicate donor number.
	 * Expected Result:Donor object should not persist.
	 */
	@Test
	public void testAddDonorDonorNumberDuplicate() {
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setDonorNumber("000321");//Here we can pass duplicate donor number and check method result.
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//First Name is blank,donor should not persist.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.addDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	/**
	 * Purpose: Test addDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * DonorNumber should be unique and not null.
	 * Here we can pass blank donor number.
	 * Expected Result:Here automatic new donornumber is generated and assign to persist object and donor object should be persist.
	 */
	
	@Test
	public void testAddDonorDonorNumberBlank() {
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setBirthDateEstimated(true);
		donorBackingForm.setDonorNumber("");//Here we can pass blank donor number and check method result.
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		assertFalse(errors.hasErrors());
		Donor donor = donorBackingForm.getDonor();
		//New donornumber is generated and assign to persist donor object and donor object should persist.
		donorRepository.addDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
	}
	
	/**
	 * Purpose: Test addDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * Gender should not blank.
	 * Here we can pass blank gender.
	 * Expected Result:Donor object should not persist.
	 */
	
	@Test
	public void testAddDonorGenderBlank() {
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		Date date = new Date();
		donorBackingForm.setAddress("myaddress");
		donorBackingForm.setFirstName("firstname");
		donorBackingForm.setMiddleName("middlename");
		donorBackingForm.setLastName("lastname");
		donorBackingForm.setIsDeleted(false);
		donorBackingForm.setCallingName("CallingName");
		donorBackingForm.setCity("City");
		donorBackingForm.setCountry("country");
		donorBackingForm.setCreatedBy(user);
		donorBackingForm.setCreatedDate(date);
		donorBackingForm.setLastUpdated(date);
		donorBackingForm.setLastUpdatedBy(user);
		donorBackingForm.setDistrict("District");
		donorBackingForm.setDonorPanel("3");
		donorBackingForm.setNationalID("1111");
		donorBackingForm.setNotes("Notes");
		donorBackingForm.setOtherPhoneNumber("9999999999");
		donorBackingForm.setPhoneNumber("8888888888");
		donorBackingForm.setPreferredContactMethod("1");
		donorBackingForm.setProvince("Province");
		donorBackingForm.setState("State");
		donorBackingForm.setZipcode("361001");
		donorBackingForm.setBirthDateEstimated(true);
		//donorBackingForm.setGender("");
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//Gender is blank,donor should not persist.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.addDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	/**
	 * Purpose: Test addDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * Birthdate  should be between 16 to 65.
	 * Here we can pass wrong birthdate so that donor's age is less than 16.
	 * Expected Result:Donor object should not persist.
	 */
	@Test
	public void testAddDonorMinimumAge() {
		donorBirthdate = "10/06/2000";//Calculate Donor age is less than 16.
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//Donor's date of birth is invalid. donor object should not persist.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.addDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	/**
	 * Purpose: Test addDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * Birthdate  should be between 16 to 65.
	 * Here we can pass wrong birthdate so that donor's age is greater than 65.
	 * Expected Result:Donor object should not persist.
	 */
	@Test
	public void testAddDonorMaximumAge() {
		donorBirthdate = "24/02/1948";//Calculate Donor age is greater than 65.
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//Donor's date of birth is invalid. donor object should not perist.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.addDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	/**
	 * Purpose: Test addDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * Here we can pass all valid value.
	 * Expected Result:Donor object should be persist.
	 */
	
	@Test
	public void testAddDonor() {
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setFirstName("Rajvi");
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//There is no validation error and Donor Object should persist.
		boolean isError = errors.hasErrors();
		assertFalse(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.addDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	
	/**
	 * Purpose:Test findAnyDonorStartsWith(String) method
	 * Description:
	 * In this test case search string length is less than 2.
	 * Expected Result: List<Donor> size should be zero.
	 */
	@Test
	public void testFindAnyDonorStartWithStringLengthLessthan2() {
		String term = "F";
		List<Donor> listObject = donorRepository.findAnyDonorStartsWith(term);
		//Less than 2 characters than list size should be zero.
		assertEquals(listObject.size(),0);
	}
	
	
	
	/**
	 * Purpose:Test findAnyDonorStartsWith(String) method
	 * Description:
	 * Expected Result: List<Donor> size should not zero.
	 */
	@Test
	public void testFindAnyDonorStartWithDonorNumberMatch() {
		String term = "000310";
		List<Donor> listObject = donorRepository.findAnyDonorStartsWith(term);
		//Search string is valid donornumber and list size should not zero.
		assertNotSame(listObject.size(),0);
	}
	
	/**
	 * Purpose:Test findAnyDonorStartsWith(String) method
	 * Description:
	 * In this test case search string is donornumber.
	 * Expected Result: Soft Deleted record should not added into List<Donor>.
	 */
	@Test
	public void testFindAnyDonorStartWithTestDeleteDonorNotInclude() {
		String term = "000633";
		List<Donor> listObject = donorRepository.findAnyDonorStartsWith(term);
		//Search string is valid donornumber and list size should be zero.
		assertEquals(listObject.size(),0);
	}
	/**
	 * Purpose:Test findAnyDonorStartsWith(String) method
	 * Description:
	 * In this test case search string is firstname.
	 * Expected Result: List<Donor> size should not zero.
	 */
	@Test
	public void testFindAnyDonorStartWithDonorFirstNameMatch() {
		String term = "fi";
		List<Donor> listObject = donorRepository.findAnyDonorStartsWith(term);
		//Search string is donor firstname and list size should not zero.
		assertNotSame(listObject.size(),0);
	}
	
	/**
	 * Purpose:Test findAnyDonorStartsWith(String) method
	 * Description:
	 * In this test case search string is lastname.
	 * Expected Result: List<Donor> size should not zero.
	 */
	@Test
	public void testFindAnyDonorStartWithDonorLastNameMatch() {
		String term = "la";
		List<Donor> listObject = donorRepository.findAnyDonorStartsWith(term);
		//Search string is donor's lastname and list size should not zero.
		assertNotSame(listObject.size(),0);
	}


	/**
	 * Purpose: Test generateUniqueDonorNumber() method
	 * Description:
	 * Generate Unique Donor Number prefix with D-<12 Digit Random Number
	 * UniqueNo>
	 */
	@Test
	public void testGenerateUniqueDonorNumber() {
		String generateRandomUniqueNo = DonorRepository
				.generateUniqueDonorNumber();
		assertFalse(StringUtils.isEmpty(generateRandomUniqueNo));
	}
	
	/**
	 * Purpose: Test findDonorByDonorNumber(String donorNumber,boolean isdeleted) Method
	 * Description:
	 * Donor object should be null, if Input DonorNumber is not match with donor(donorNumber) table.
	 * donornumber value is -1. Isdeleted value is set to true.
	 * Expected Result:
	 * Donor object should be null.
	 */
	@Test
	public void testfindDonorByDonorNumberCheckDonorExistDeleteTrue() {
		donorNumber="-1";
		Donor donor = donorRepository
				.findDonorByDonorNumber(donorNumber, true);
		//Set  donor number as -1 and deleted true so donor object should null.
		assertNull(donor);
	}
	
	/**
	 * Purpose: Test findDonorByDonorNumber(String donorNumber,boolean isdeleted) Method
	 * Description:
	 * Donor object should be null, if Input DonorNumber is not match with donor(donorNumber) table.
	 * donornumber value is -1. Isdeleted value is set to false.
	 * Expected Result:
	 * Donor object should be null.
	 */
	@Test
	public void testfindDonorByDonorNumberCheckDonorExistDeleteFalse() {
		donorNumber="-1";
		Donor donor = donorRepository
				.findDonorByDonorNumber(donorNumber, false);
		//Set  donor number as -1 and deleted false so donor object should null.
		assertNull(donor);
	}

	/**
	 * Purpose: Test findDonorByDonorNumber(String donorNumber,boolean isdeleted) Method
	 * Description:
	 * Set Valid deleted donor donornumber value and set isdeleted = true
	 *Expected Result:
	 * Donor Object should not null.
	 */
	@Test
	public void testfindDonorByDonorNumberSelectDeleteDonorIsDeleteTrue() {
		donorNumber="000633";
		Donor donor = donorRepository
				.findDonorByDonorNumber(donorNumber, true);
		//Set delete donor donornumber value and deleted true so donor object should not null.
		assertNotNull(donor);
	}
	
	/**
	 * Purpose: Test findDonorByDonorNumber(String donorNumber,boolean isdeleted) Method
	 * Description:
	 * Set Valid deleted donor donornumber value and set isdeleted = false
	 *Expected Result:
	 * Donor Object should be null.
	 */
	@Test
	public void testfindDonorByDonorNumberSelectDeleteDonorIsDleteFalse() {
		donorNumber="000633";
		Donor donor = donorRepository
				.findDonorByDonorNumber(donorNumber, false);
		//Set delete donor donornumber value and deleted false so donor object should null.
		assertNull(donor);
	}
	
	/**
	 * Purpose: Test findDonorByDonorNumber(String donorNumber,boolean isdeleted) Method
	 * Description:
	 * Set valid value for donornumber and set isdeleted = false.
	 * Expected Result:
	 * Donor object should null.
	 */
	@Test
	public void testfindDonorByDonorNumberIsDeletedtrue() {
		Donor donor = donorRepository
				.findDonorByDonorNumber(donorNumber, true);
		//valid donor donornumber, donor object should null because isdeleted=true.
		assertNull(donor);
	}
	
	/**
	 * Purpose: Test findDonorByDonorNumber(String donorNumber,boolean isdeleted) Method
	 * Description:
	 * Set valid value for donornumber and set isdeleted = false.
	 * Expected Result:
	 * Donor object should not null.
	 */
	@Test
	public void testfindDonorByDonorNumberIsDeletedfalse() {
		Donor donor = donorRepository
				.findDonorByDonorNumber(donorNumber, false);
		//set valid donor donornumber and isdeleted=false so donor object should not null.
		assertNotNull(donor);
	}

	/**
	 * Purpose: Test saveDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * Here we can pass donor firstname value is blank.
	 * Expected Result:Donor object should not persist.
	 */
	@Test
	public void testSaveDonorFirstNameBlank() {
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setFirstName("");//Here we can pass donor firstname value is blank.
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//First Name is blank,donor object should not persist.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.saveDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	/**
	 * Purpose: Test saveDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * Last Name should not null.
	 * Here we can pass donor lastname value is blank.
	 * Expected Result:Donor object should not persist.
	 */
	@Test
	public void testSaveDonorLastNameBlank() {
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setLastName("");//Here we can pass donor lastname value is blank.
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//Last Name is blank,donor object should not persist.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.saveDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	
	
	/**
	 * Purpose: Test saveDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * DonorNumber should be unique and not null.
	 * Here we can pass duplicate donor number.
	 * Expected Result:Donor object should not persist.
	 */
	@Test
	public void testSaveDonorDonorNumberDuplicate() {
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setDonorNumber("000321");//Here we can pass duplicate donor number and check method result.
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//Donor's donornumber is duplicate,donor object should not persist.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.saveDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	/**
	 * Purpose: Test saveDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * DonorNumber should be unique and not null.
	 * Here we can pass blank donor number.
	 * Expected Result:New donornumber is generated and assign to persist object and donor object should be persist.
	 */
	
	@Test
	public void testSaveDonorDonorNumberBlank() {
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setDonorNumber("");//Here we can pass blank donor number and check method result.
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		assertFalse(errors.hasErrors());
		Donor donor = donorBackingForm.getDonor();
		//New donornumber is generated and assign to persist donor object and donor object should persist.
		donorRepository.saveDonor(donor);
		assertTrue(donor.getId() == 0 ? false : true);
	}
	
	
	/**
	 * Purpose: Test saveDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * Gender should not blank.
	 * Here we can pass blank gender.
	 * Expected Result:Donor object should not persist.
	 */
	
	@Test
	public void testsaveDonorGenderBlank() {
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		Date date = new Date();
		donorBackingForm.setAddress("myaddress");
		donorBackingForm.setFirstName("firstname");
		donorBackingForm.setMiddleName("middlename");
		donorBackingForm.setLastName("lastname");
		donorBackingForm.setIsDeleted(false);
		donorBackingForm.setCallingName("CallingName");
		donorBackingForm.setCity("City");
		donorBackingForm.setCountry("country");
		donorBackingForm.setCreatedBy(user);
		donorBackingForm.setCreatedDate(date);
		donorBackingForm.setLastUpdated(date);
		donorBackingForm.setLastUpdatedBy(user);
		donorBackingForm.setDistrict("District");
		donorBackingForm.setDonorPanel("3");
		donorBackingForm.setNationalID("1111");
		donorBackingForm.setNotes("Notes");
		donorBackingForm.setOtherPhoneNumber("9999999999");
		donorBackingForm.setPhoneNumber("8888888888");
		donorBackingForm.setPreferredContactMethod("1");
		donorBackingForm.setProvince("Province");
		donorBackingForm.setState("State");
		donorBackingForm.setZipcode("361001");
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//Gender is blank,donor should not persist.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.saveDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	/**
	 * Purpose: Test saveDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * Birthdate  should be between 16 to 65.
	 * Here we can pass wrong birthdate so that donor's age is less than 16.
	 * Expected Result:Donor object should not persist.
	 */
	@Test
	public void testSaveDonorMinimumAge() {
		donorBirthdate = "10/06/2000";//Calculate Donor age is less than 16.
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//Donor's date of birth is invalid. donor object should not persist.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.saveDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	/**
	 * Purpose: Test saveDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should persist.
	 * Birthdate  should be between 16 to 65.
	 * Here we can pass wrong birthdate so that donor's age is greater than 65.
	 * Expected Result:Donor object should not persist.
	 */
	@Test
	public void testSaveDonorMaximumAge() {
		donorBirthdate = "24/02/1948";//Calculate Donor age is greater than 65.
		donorBackingForm.setBirthDate(donorBirthdate);
		// Set Inputvalue.
		setBackingFormValue(donorBackingForm);
		donorBackingForm.setBirthDateEstimated(true);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		// Check Input value is valid or not.
		donorBackingFormValidator.validate(obj, errors);
		//Donor's date of birth is invalid. donor object should not perist.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.saveDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	/**
	 * Purpose: Test saveDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid then Donor object should persist.
	 * First Name,Last Name,Gender(M or F),Birth date should not null.
	 * DonorNumber should be unique and not null. Donor Age should be between 16 to 25.
	 * Expected Result: Donor object should pass validation framework and donor object should persist.
	 */
	@Test
	public void testSaveDonor() {
		donorBackingForm.setBirthDate(donorBirthdate);
		donorBackingForm.setDonorNumber("");
		setBackingFormValue(donorBackingForm);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		donorBackingFormValidator.validate(obj, errors);
		boolean isError = errors.hasErrors();
		assertFalse(isError);
		if(!isError){
		Donor donor = donorBackingForm.getDonor();
		donorRepository.saveDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		assertTrue(isIdZero);
		}
	}
	
	
	
	
	
	/**
	 * Purpose: Test updateDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should update.
	 * Here we can pass donor firstname value is blank.
	 * Expected Result:Donor object should not update.
	 */
	@Test
	public void testUpdateDonorFirstNameBlank() {
		this.userAuthentication();
		Donor editDonor = donorRepository.findDonorById(updatedbid);
		donorBackingForm = new DonorBackingForm(editDonor);
		setBackingUpdateFormValue(donorBackingForm);
		donorBackingForm.setFirstName("");
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		donorBackingFormValidator.validate(obj, errors);
		//Donor's firstname is blank. Donor object should not update.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
			Donor updateDonor = donorBackingForm.getDonor();
			assertNotNull(donorRepository.updateDonor(updateDonor));
		}
	}
	
	/**
	 * Purpose: Test updateDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should update.
	 * Here we can pass donor lastname value is blank.
	 * Expected Result:Donor object should not update.
	 */
	@Test
	public void testUpdateDonorLastNameBlank() {
		this.userAuthentication();
		Donor editDonor = donorRepository.findDonorById(updatedbid);
		donorBackingForm = new DonorBackingForm(editDonor);
		setBackingUpdateFormValue(donorBackingForm);
		donorBackingForm.setLastName("");
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		donorBackingFormValidator.validate(obj, errors);
		//Donor's lastname is blank. Donor object should not update.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
			Donor updateDonor = donorBackingForm.getDonor();
			assertNotNull(donorRepository.updateDonor(updateDonor));
		}
	}
	
	/**
	 * Purpose: Test updateDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should update.
	 * Here we can pass donor's duplicate donornumber.
	 * Expected Result:Donor object should not update.
	 */
	@Test
	public void testUpdateDonorDonorNumberDuplicate() {
		this.userAuthentication();
		Donor editDonor = donorRepository.findDonorById(updatedbid);
		donorBackingForm = new DonorBackingForm(editDonor);
		setBackingUpdateFormValue(donorBackingForm);
		donorBackingForm.setDonorNumber("000585");//Here we can pass duplicate donor number and check method result.
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		donorBackingFormValidator.validate(obj, errors);
		//Donor's donornumber is duplicate. Donor object should not update.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
			Donor updateDonor = donorBackingForm.getDonor();
			assertNotNull(donorRepository.updateDonor(updateDonor));
		}
	}

	/**
	 * Purpose: Test updateDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should update.
	 * Here we can pass donor's donornumber is blank.
	 * Expected Result:Donor object should not update.
	 */
	@Test
	public void testUpdateDonorDonorNumberBlank() {
		this.userAuthentication();
		Donor editDonor = donorRepository.findDonorById(updatedbid);
		donorBackingForm = new DonorBackingForm(editDonor);
		setBackingUpdateFormValue(donorBackingForm);
		donorBackingForm.setFirstName("");//Here we can pass duplicate donor number and check method result.
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		donorBackingFormValidator.validate(obj, errors);
		//Donor's donornumber is duplicate. Donor object should not update.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
			Donor updateDonor = donorBackingForm.getDonor();
			assertNotNull(donorRepository.updateDonor(updateDonor));
		}
	}
	
	/**
	 * Purpose: Test updateDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should update.
	 * Here we can pass donor's gender is blank.
	 * Expected Result:Donor object should not update.
	 */
	@Test
	public void testUpdateDonorGenderBlank() {
		this.userAuthentication();
		Donor editDonor = donorRepository.findDonorById(updatedbid);
		donorBackingForm = new DonorBackingForm(editDonor);
		donorBackingForm.setAddress("address_update");
		donorBackingForm.setFirstName("firstName_update");
		donorBackingForm.setMiddleName("middlename_update");
		donorBackingForm.setLastName("lastname_update");
		donorBackingForm.setIsDeleted(false);
		donorBackingForm.setCallingName("CallingName_update");
		donorBackingForm.setCity("City_update");
		donorBackingForm.setCountry("country_update");
		donorBackingForm.setDistrict("District_update");
		donorBackingForm.setDonorPanel("2");
		donorBackingForm.setNationalID("1212");
		donorBackingForm.setNotes("Notes_update");
		donorBackingForm.setOtherPhoneNumber("9878787878");
		donorBackingForm.setPhoneNumber("874525452");
		donorBackingForm.setPreferredContactMethod("2");
		donorBackingForm.setProvince("Province_update");
		donorBackingForm.setState("State_update");
		donorBackingForm.setZipcode("361001");
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		donorBackingFormValidator.validate(obj, errors);
		//Donor's gender value is blank. Donor object should not update.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
			Donor updateDonor = donorBackingForm.getDonor();
			assertNotNull(donorRepository.updateDonor(updateDonor));
		}
	}
	
	/**
	 * Purpose: Test updateDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should update.
	 * Birthdate  should be between 16 to 65.
	 * Here we can pass wrong birthdate so that donor's age is less than 16.
	 * Expected Result:Donor object should not update.
	 */
	@Test
	public void testUpdateDonorMinimumAge() {
		this.userAuthentication();
		Donor editDonor = donorRepository.findDonorById(updatedbid);
		donorBackingForm = new DonorBackingForm(editDonor);
		setBackingUpdateFormValue(donorBackingForm);
		donorBirthdate = "10/06/2000";//Calculate Donor age is less than 16.
		donorBackingForm.setBirthDate(donorBirthdate);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		donorBackingFormValidator.validate(obj, errors);
		//Donor's date of birth is invalid. donor object should not update.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
			Donor updateDonor = donorBackingForm.getDonor();
			assertNotNull(donorRepository.updateDonor(updateDonor));
		}
	}
	
	/**
	 * Purpose: Test updateDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid than Donor object should update.
	 * Birthdate  should be between 16 to 65.
	 * Here we can pass wrong birthdate so that donor's age is greater than 65.
	 * Expected Result:Donor object should not update.
	 */
	@Test
	public void testUpdateDonorMaximumAge() {
		this.userAuthentication();
		Donor editDonor = donorRepository.findDonorById(updatedbid);
		donorBackingForm = new DonorBackingForm(editDonor);
		setBackingUpdateFormValue(donorBackingForm);
		donorBirthdate = "24/02/1948";//Calculate Donor age is greater than 65.
		donorBackingForm.setBirthDate(donorBirthdate);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		donorBackingFormValidator.validate(obj, errors);
		//Donor's date of birth is invalid. donor object should not update.
		boolean isError = errors.hasErrors();
		assertTrue(isError);
		if(!isError){
			Donor updateDonor = donorBackingForm.getDonor();
			assertNotNull(donorRepository.updateDonor(updateDonor));
		}
	}
	/**
	 * Purpose: Test updateDonor(Donor donor) Method
	 * Description:
	 * Check user input value is valid through DonorBackingFormValidator validate(Object,Errors)
	 * method. If user input is valid then Donor object should update.
	 * First Name,Last Name,Gender(M or F),Birth date should not null.
	 * DonorNumber should be unique and not null. Donor Age should be between 16 to 25.
	 * Expected Result: Here Donor object should update.
	 */
		@Test
		public void testUpdateDonor() {
			this.userAuthentication();
			Donor editDonor = donorRepository.findDonorById(updatedbid);
			donorBackingForm = new DonorBackingForm(editDonor);
			setBackingUpdateFormValue(donorBackingForm);
			errors = new BindException(donorBackingForm, "donor");
			Object obj = donorBackingForm;
			donorBackingFormValidator.validate(obj, errors);
			boolean isError = errors.hasErrors();
			assertFalse(isError);
			if(!isError){
				Donor updateDonor = donorBackingForm.getDonor();
				assertNotNull(donorRepository.updateDonor(updateDonor));
			}
		}
	
		/**
		 * Purpose: Test getAllDonors() method
		 * Description:
		 * It should return List<Donor> object with condition that isDeleted=0
		 * Expected Result:
		 * Soft Deleted object should not added into List<Donor>.
		 */
		@Test
		public void testgetAllDonors() {
			List<Donor> lists = donorRepository.getAllDonors();
			for (Donor donor : lists) {
				//If soft deleted object is added into list then test case failed.
				assertFalse(donor.getIsDeleted());
			}
		}
		
		/**
		 * Purpose:Test findDonorById(Long) method
		 * Description:
		 * Here,We can pass donor id as a -1.
		 * Expected Result:
		 * Donor object should null. 
		 */
		@Test
		public void testFindDonorByIdLongDonorIdDoesNotExist() {
			Donor donor = null;
			try{
			dbid=-1;
			donor = donorRepository.findDonorById(dbid);
			}catch(Exception e){
				//Donor Id does not exist into system donor object should null.
				assertNull(donor);
			}
		}
		
		/**
		 * Purpose:Test findDonorById(Long) method
		 * Description:
		 * Here,We can pass soft deleted donor id so return donor object should null.
		 * Expected Result:
		 * Donor object should null. 
		 */
		@Test
		public void testFindDonorByIdLongDonorDeleteTrue() {
			Donor donor = null;
			try{
			donorRepository.deleteDonor(dbid);
			donor = donorRepository.findDonorById(dbid);
			}catch(Exception e){
				//Donor Id exist into system  but that record is softdeleted.So, Donor object should null.
				assertNull(donor);	
			}
		}
		
		/**
		 * Purpose:Test findDonorById(Long) method
		 * Description:
		 * Here,We can pass soft deleted donor id so return donor object should null.
		 * Expected Result:
		 * Donor object should null. 
		 */
		@Test
		public void testFindDonorByIdLongDonorDeleteFalse() {
			dbid=335;
			Donor donor = donorRepository.findDonorById(dbid);
			//Donor Id is exist into system, donor object should not null.
			assertNotNull(donor);
		}
		
		/**
		 * Purpose:Test findDonorById(String) method
		 * Description:
		 * Donor object should be null if Input dbid is blank.
		 * Expected Result:
		 * Donor Object should null.
		 */

		@Test
		public void testFindDonorByIdStringWithblankSpace() {
			Donor donor = donorRepository.findDonorById("");
			//Method argument is blank space donor object should null.
			assertNull(donor);
		}
		
		/**
		 * Purpose:Test findDonorById(String) method
		 * Description:
		 * Here,We can pass donor id as a -1.
		 * Expected Result:
		 * Donor object should null. 
		 */
		@Test
		public void testFindDonorByIdStringDonorIdDoesNotExist() {
			Donor donor = null;
			try{
			dbid=-1;
			 donor = donorRepository.findDonorById(String.valueOf(dbid));
			}catch(Exception e){
			//Donor Id does not exist into system, donor object should null.
			assertNull(donor);
			}
		}
		
		/**
		 * Purpose:Test findDonorById(String) method
		 * Description:
		 * Here,We can pass soft deleted donor id so return donor object should null.
		 * Expected Result:
		 * Donor object should null. 
		 */
		@Test
		public void testFindDonorByIdStringDonorDeleteTrue() {
			Donor donor= null;
			try{
			 donor = donorRepository.findDonorById(String.valueOf(dbid));
			}catch(Exception e){
			//Donor Id is valid  but that record is soft deleted. So,Donor object should null.
			assertNull(donor);
			}
		}
		
		/**
		 * Purpose:Test findDonorById(String) method
		 * Description:
		 * Here,We can pass soft deleted donor id so return donor object should null.
		 * Expected Result:
		 * Donor object should null. 
		 */
		@Test
		public void testFindDonorByIdStringDonorDeleteFalse() {
			dbid=335;
			Donor donor = donorRepository.findDonorById(String.valueOf(dbid));
			//Donor Id is valid and donor object is not softdeleted so object should not null.
			assertNotNull(donor);
		}
		
		
		/**
		 * Purpose:Test findDonorByNumber(String) method
		 * Description:
		 * Pass donor's donornumber value.
		 * Expected Result:
		 * Donor Object should null.
		 */
		@Test
		public void testFindDonorByNumberDonerNumberDoesNotExist() {
			donorNumber="-1";
			Donor donor = donorRepository.findDonorByNumber(donorNumber);
			//Donornumber is invalid,Donor object should null.
			assertNull(donor);
		}
		
		/**
		 * Purpose:Test findDonorByNumber(String) method
		 * Description:
		 * Pass donor's donornumber value.
		 * Expected Result:
		 * Donor Object should null.
		 */
		@Test
		public void testFindDonorByNumberDonerIsDeletedTrue() {
			donorNumber="000633";//
			Donor donor = donorRepository.findDonorByNumber(donorNumber);
			//Donornumber is valid but selected row is softdeleted so,Donor object should null.
			assertNull(donor);
		}
		
		/**
		 * Purpose:Test findDonorByNumber(String) method
		 * Description:
		 * Pass donor's donornumber value.
		 * Expected Result:
		 * Donor Object should not null.
		 */
		@Test
		public void testFindDonorByNumber() {
			Donor donor = donorRepository.findDonorByNumber(donorNumber);
			//Donornumber is valid and selected record is not softdeleted so donor object should not null. 
			assertNotNull(donor);
		}
		
		
		/**
		 * Purpose: Test deleteDonor(long) method
		 * Description:
		 * Here db id is invalid
		 * Expected Result:
		 * Donor Object soft deleted operation is failed.
		 */
		
		@Test
		public void testDeleteDonorIdInvalid() {
			boolean isDelete = true;
			try{
			deletedbid=-1;
			donorRepository.deleteDonor(deletedbid);
			}catch(Exception e){
				isDelete = false;
			}finally{
				//SoftDelete operation is failed because deletedbid is invalid.
				assertFalse(isDelete);
			}
		}
		/**
		 * Purpose: Test deleteDonor(long) method
		 * SoftDelete should be perform on selected donor object.Donor Object should be update with isDeleted=1.
		 */
		
		@Test
		public void testDeleteDonorIdValid() {
			boolean isDelete = true;
			try{
			deletedbid=226;
			donorRepository.deleteDonor(deletedbid);
			}catch(Exception e){
				isDelete = false;
			}finally{
				assertFalse(isDelete);
			}
		}
		
		/**Purpose: Test deferDonor(String,String,String,String) method
		 * Description:
		 * If user authentication is failed through authentication() method,object should not persist. 
		 * If donorid is Invalid , DonorDeferral should not persist. 
		 * Expected Value:
		 * DonorDeferal object should not persist.
		 */

		@Test
		public void testdeferDonorDonorIdInvalid() {
			boolean isScucess = false;
			try {
				this.userAuthentication();
				donoridfordefer=-1;
				donorRepository.deferDonor(String.valueOf(donoridfordefer),
						"28/02/2014", "2", "text");
				isScucess = true;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				isScucess = false;
			}catch(Exception e){
				isScucess = false;
			}finally {
				assertFalse(isScucess);
			}
		}
		
		/**Purpose: Test deferDonor(String,String,String,String) method
		 * Description:
		 * If user authentication is failed through authentication() method,object should not persist. 
		 * If deferUntil date is Invalid , DonorDeferral should not persist. 
		 * Expected Value:
		 * DonorDeferal object should not persist.
		 */

		@Test
		public void testdeferDonorDeferredUntilInvalid() {
			boolean isScucess = false;
			try {
				this.userAuthentication();
				donoridfordefer=216;
				donorRepository.deferDonor(String.valueOf(donoridfordefer),
						"28/02/2013", "1", "text");
				isScucess = true;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				isScucess = false;
			}catch(Exception e){
				e.printStackTrace();
				isScucess = false;
			}finally {
				assertFalse(isScucess);
			}
		}
		
		/**Purpose: Test deferDonor(String,String,String,String) method
		 * Description:
		 * If user authentication is failed through authentication() method,object should not persist. 
		 * If Defer reason id is valid,DonorDeferral should not persist. 
		 * Expected Value:
		 * DonorDeferal object should not persist.
		 */

		@Test
		public void testdeferDonorReasonIdInvalid() {
			boolean isScucess = false;
			try {
				this.userAuthentication();
				donoridfordefer=335;
				donorRepository.deferDonor(String.valueOf(donoridfordefer),
						"28/02/2014", "15", "text");
				isScucess = true;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				isScucess = false;
			}catch(Exception e){
				isScucess = false;
			}finally {
				assertFalse(isScucess);
			}
		}
		
		/**Purpose: Test deferDonor(String,String,String,String) method
		 * Description:
		 * If user authentication is failed through authentication() method,object should not persist. 
		 * Expected Value:
		 * DonorDeferal object should  persist.
		 */

		@Test
		public void testdeferDonor() {
			boolean isScucess = false;
			try {
				this.userAuthentication();
				donoridfordefer=338;
				donorRepository.deferDonor(String.valueOf(donoridfordefer),
						"28/06/2014", "1", "");
				isScucess = true;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(" Ex. testdeferDonor()");
				isScucess = false;
			}catch(Exception e){
				System.out.println(" Ex. testdeferDonor()");
				isScucess = false;
			}finally {
				assertTrue(isScucess);
			}
		}

		/**
		 * Purpose: Test getDonorDeferrals(Long) method
		 * Description:
		 * Here we can pass Invalid donorId,so that list size should be zero.
		 * Expected Result:
		 * List size should be zero.
		 */
		@Test
		public void testGetDonorDeferralsDonorIdIsInvalid() {
			List<DonorDeferral> list = donorRepository.getDonorDeferrals(0l);
			//Here donor Id is invalid so that list size should be zero.
			assertEquals(0, list.size());

		}
		
		/**
		 * Purpose: Test getDonorDeferrals(Long) method
		 * Description:
		 * Here we can pass Invalid donorId,so that list size should be none zero.
		 * Expected Result:
		 * List size should be none zero.
		 */
		@Test
		public void testGetDonorDeferralsDonorIdIsValid() {
			List<DonorDeferral> list = donorRepository.getDonorDeferrals(230l);
			assertNotSame(0, list.size());

		}
		
		/**
		 * Purpose: Test getDeferralReasons() method
		 * Description:
		 * It should return List<DeferralReason>. Soft deleted record in DeferralReason table should not part of List<DeferralReason>.
		 * Expected Result:
		 * Soft Deleted DeferralReason object should not part of List<DeferralReason>.
		 */

		@Test
		public void testGetDeferralReasons() {
			List<DeferralReason> list = donorRepository.getDeferralReasons();
			if (list != null && list.size() > 0) {
				for (DeferralReason deferralReason : list) {
					//Soft Delete DeferralReason object should not part of List<DeferralReason>.
						assertFalse(deferralReason.getIsDeleted());
				}
			}
		}
		
	/**
	 * purpose: Test addAllDonors(List<Donor>) method
	 * Description:
	 * Check user input value is valid or not through DonorBackingFormValidator validate(Object,Errors)
	 * method and user input is valid then donor object is added into donor list.
	 * Donor list will be used to persist donor object. First Name,Last Name,Gender(M or F),Birth date should not null.
	 * DonorNumber should be unique and not null. Donor Age should be between 16 to 25.
	 */
	//@Test
	public void testaddAllDonors() {
		Donor donor = new Donor();
		DonorBackingForm donorBackingForm = new DonorBackingForm(donor);
		donorBackingForm.setBirthDate(donorBirthdate);
		donorBackingForm.setDonorNumber(sequenceNumberRepository
				.getNextDonorNumber());
		setBackingFormValue(donorBackingForm);
		List<Donor> listAllDonor = new ArrayList<Donor>();
		listAllDonor.add(donorBackingForm.getDonor());
		donor = new Donor();
		donorBackingForm = new DonorBackingForm(donor);
		donorBirthdate = "11/06/1991";
		donorBackingForm.setBirthDate(donorBirthdate);
		donorBackingForm.setDonorNumber(sequenceNumberRepository
				.getNextDonorNumber());
		setBackingFormValue(donorBackingForm);
		listAllDonor.add(donorBackingForm.getDonor());
		donor = new Donor();
		donorBackingForm = new DonorBackingForm(donor);
		donorBirthdate = "12/06/1991";
		donorBackingForm.setBirthDate(donorBirthdate);
		donorBackingForm.setDonorNumber(sequenceNumberRepository
				.getNextDonorNumber());
		setBackingFormValue(donorBackingForm);
		listAllDonor.add(donorBackingForm.getDonor());
		donorRepository.addAllDonors(listAllDonor);
	}

	

	

	/**
	 * Purpose:Test isCurrentlyDeferred(List<DonorDeferral>) method
	 * Description:
	 * Here DeferralDonorId is invalid so that List<DonorDeferral> size should be zero and can not findout iscurrentlyDefferal or not. 
	 * DeferDonor calculation is mention as below
	 *  if currentdate >= deferredon and currentdate<=deferredUntil
	 * Expected Result:
	 * List<DonorDeferral> size should be zero.
	 * 
	 */
	@Test
	public void testisCurrentlyDeferredInValidDonorId() {
		try{
			List<DonorDeferral> list = donorRepository
					.getDonorDeferrals(-1l);
			//Here donorId is invalid so that list size should be zero.
			assertEquals(0, list.size());
			////Below code part is never execute.
			if(list.size()>0)
			{
				assertTrue(donorRepository.isCurrentlyDeferred(list));
			}
		}catch(Exception e){
			assertFalse(false);
		}
	}
	
	/**
	 * Purpose:Test isCurrentlyDeferred(List<DonorDeferral>) method
	 * Description:
	 * Here DeferralDonorId is valid but no matching record is found in donordefferal so that List<DonorDeferral> size should be zero. 
	 * DeferDonor calculation is mention as below
	 *  if currentdate >= deferredon and currentdate<=deferredUntil
	 * Expected Result:
	 * List<DonorDeferral> size should be zero.
	 * 
	 */
	@Test
	public void testisCurrentlyDeferredValidDonorId() {
		List<DonorDeferral> list = donorRepository
				.getDonorDeferrals(451l);
		//Here donorId is valid but no matching record is found in donordefferal table so that list size should be zero.
		assertEquals(0, list.size());
		////Below code part is never execute.
		if(list.size()>0)
		{
			assertTrue(donorRepository.isCurrentlyDeferred(list));
		}
	}
	
	/**
	 * Purpose:Test isCurrentlyDeferred(List<DonorDeferral>) method
	 * Description:
	 * Here DeferralDonorId is valid and matching record is found in donordef so that List<DonorDeferral> size should be none zero. 
	 * And deferal donor should be found. 
	 * DeferDonor calculation is mention as below
	 *  if currentdate >= deferredon and currentdate<=deferredUntil
	 * Expected Result:
	 * List<DonorDeferral> size should not zero. And dereral donor isCurrentlyDeferred(List<DonorDeferral>) method should return true.
	 */
	@Test
	public void testisCurrentlyDeferredValidDonorIdAndDeferalDonorFound() {
		List<DonorDeferral> list = donorRepository
				.getDonorDeferrals(216l);
		//Here donorId is valid and  List<DonorDeferral> size should not zero.
		assertNotSame(0, list.size());
			//Here record is found in DonorDeferral table and isCurrentlyDeferred(List<DonorDeferral>) method should return true.
			assertTrue(donorRepository.isCurrentlyDeferred(list));
	}
	
	/**
	 * Purpose:Test isCurrentlyDeferred(List<DonorDeferral>) method
	 * Description:
	 * Here DeferralDonorId is valid and matching record is found in donordef so that List<DonorDeferral> size should be none zero. 
	 * And deferal donor should not found. 
	 * DeferDonor calculation is mention as below
	 *  if currentdate >= deferredon and currentdate<=deferredUntil
	 * Expected Result:
	 * List<DonorDeferral> size should not zero. And dereral donor isCurrentlyDeferred(List<DonorDeferral>) method should return false.
	 * 
	 */
	@Test
	public void testisCurrentlyDeferredValidDonorIdAndDeferalDonorNotFound() {
		List<DonorDeferral> list = donorRepository
				.getDonorDeferrals(337l);
		//Here donorId is invalid so that list size should be zero.
		assertNotSame(0, list.size());
			//Here record is found in DonorDeferral table but isCurrentlyDeferred(List<DonorDeferral>) method should return false.
			assertFalse(donorRepository.isCurrentlyDeferred(list));
	}


	/**
	 * Purpose: Test isCurrentlyDeferred(Donor) method.
	 * Description:
	 * Expected Result:
	 * Here we can pass Invalid DonorId donor object should null.
	 */
	@Test
	public void testisCurrentlydeferredInValidDonorId() {
		Donor donor = donorRepository.findDonorById(-1l);
		//DororId is invalid donor object should null.
		assertNull(donor);
		//Below code part is never execute.
		if(donor!=null){
		assertTrue(donorRepository.isCurrentlyDeferred(donor));
		}
	}
	
	/**
	 * Purpose: Test isCurrentlyDeferred(Donor) method.
	 * Description:
	 * Expected Result:
	 * DororId is valid but donor object is softdelete so donor object should null.
	 */
	@Test
	public void testisCurrentlydeferredDeleteDonorId() {
		try{
		Donor donor = donorRepository.findDonorById(-1l);
		//DororId is valid but donor object is softdelete so donor object should null.
				assertNull(donor);
		if(donor!=null){
			assertTrue(donorRepository.isCurrentlyDeferred(donor));
			}
		}catch(Exception e){
			assertFalse(false);
		}
	}
	
	
	/**
	 * Purpose: Test isCurrentlyDeferred(Donor) method.
	 * Description:
	 * Expected Result:
	 * DororId is valid but donor object is softdelete so donor object should null.
	 */
	@Test
	public void testisCurrentlydeferredValidDonorIdNoRecordFoundIndDeferalDonor() {
		try{
			Donor donor = donorRepository.findDonorById(-1l);
			//DororId is valid  donor object should not null.
					assertNotNull(donor);
				//Donor Id is valid but no matching record found in Donordeferral table so that DonorDeferral object is null. 
					//And isCurrentlyDeferred() method return false. 
				assertFalse(donorRepository.isCurrentlyDeferred(donor));
		}catch(Exception e){
			assertFalse(false);
		}
		}



	/**
	 * Purpose: Test findAnyDonor(String,String,String,List<BloodGroup>,String,Map<String, Object>) method
	 * Searching is work on firstname,lastname,donornumber and bloodgroup.
	 * if anyBloodGroup=false then search List<BloodGroup> should consider otherwise not.
	 *  If matching record is found then Object List is return. Otherwise Object list is return with size 0.
	 *  Soft Deleted object should not be return.
	 */

	////@Test
	public void testFindAnyDonor() {
		String searchDonorNumber = "";
		String donorFirstName = "Fir";
		String donorLastName = "";
		String anyBloodGroup = "true";
		Map<String, Object> pagingParams = new HashMap<String, Object>();
		List<BloodGroup> bloodGroups = new ArrayList<BloodGroup>();
		bloodGroups.add(new BloodGroup("A+"));
		pagingParams.put("sortColumn", "id");
		pagingParams.put("start", "0");
		pagingParams.put("sortColumnId", "0");
		pagingParams.put("length", "10");
		pagingParams.put("sortDirection", "asc");
		System.out.println("donorFirstName:::::" + donorFirstName);
		List<Object> listObject = donorRepository.findAnyDonor(
				searchDonorNumber, donorFirstName, donorLastName, bloodGroups,
				anyBloodGroup, pagingParams);
		System.out.println("1::" + listObject.size());
		List<Donor> donors = (List<Donor>) listObject.get(0);
		System.out.println("1::" + donors.size());
		if (donors != null && donors.size() > 0) {
			System.out
					.println("ID\tDonorNumber\tFirstName\tLastName\tGender\tBloodGroup\tBirthDate");
			for (Donor donor : donors) {
				System.out.println();
				System.out.print(donor.getId());
				System.out.print("\t");
				System.out.print(donor.getDonorNumber());
				System.out.print("\t\t");
				System.out.print(donor.getFirstName());
				System.out.print("\t");
				System.out.print(donor.getLastName());
				System.out.print("\t");
				System.out.print(donor.getGender());
				System.out.print("\t");
				System.out.print(donor.getBloodAbo() + donor.getBloodRh());
				System.out.print("\t\t");
				System.out.print(donor.getBirthDate());
				System.out.println();
			}
		}

	}

	
	/**
	 * Called when update existing record.
	 * 
	 * @param donorBackingForm
	 */
	public void setBackingUpdateFormValue(DonorBackingForm donorBackingForm) {
		donorBackingForm.setAddress("address_update");
		donorBackingForm.setFirstName("firstName_update");
		donorBackingForm.setMiddleName("middlename_update");
		donorBackingForm.setLastName("lastname_update");
		donorBackingForm.setIsDeleted(false);
		donorBackingForm.setGender("female");
		donorBackingForm.setCallingName("CallingName_update");
		donorBackingForm.setCity("City_update");
		donorBackingForm.setCountry("country_update");
		donorBackingForm.setDistrict("District_update");
		donorBackingForm.setDonorPanel("2");
		donorBackingForm.setNationalID("1212");
		donorBackingForm.setNotes("Notes_update");
		donorBackingForm.setOtherPhoneNumber("9878787878");
		donorBackingForm.setPhoneNumber("874525452");
		donorBackingForm.setPreferredContactMethod("2");
		donorBackingForm.setProvince("Province_update");
		donorBackingForm.setState("State_update");
		donorBackingForm.setZipcode("361001");
	}

	/**
	 * Called when insert new record into table.
	 * 
	 * @param donorBackingForm
	 */
	public void setBackingFormValue(DonorBackingForm donorBackingForm) {
		Date date = new Date();
		donorBackingForm.setAddress("myaddress");
		donorBackingForm.setFirstName("firstname");
		donorBackingForm.setMiddleName("middlename");
		donorBackingForm.setLastName("lastname");
		donorBackingForm.setIsDeleted(false);
		donorBackingForm.setGender("male");
		donorBackingForm.setCallingName("CallingName");
		donorBackingForm.setCity("City");
		donorBackingForm.setCountry("country");
		donorBackingForm.setCreatedBy(user);
		donorBackingForm.setCreatedDate(date);
		donorBackingForm.setLastUpdated(date);
		donorBackingForm.setLastUpdatedBy(user);
		donorBackingForm.setDistrict("District");
		donorBackingForm.setDonorPanel("3");
		donorBackingForm.setNationalID("1111");
		donorBackingForm.setNotes("Notes");
		donorBackingForm.setOtherPhoneNumber("9999999999");
		donorBackingForm.setPhoneNumber("8888888888");
		donorBackingForm.setPreferredContactMethod("1");
		donorBackingForm.setProvince("Province");
		donorBackingForm.setState("State");
		donorBackingForm.setZipcode("361001");
		donorBackingForm.setBirthDateEstimated(true);
	}

	/**
	 * UserPassword,V2vUserDetails(Principal) and authority detail store into
	 * SecurityContextHolder.
	 */
	public void userAuthentication() {
		applicationContext = new ClassPathXmlApplicationContext(
				"file:**/security-v2v-servlet.xml");
		userDetailsService = applicationContext.getBean(LoginUserService.class);
		V2VUserDetails userDetails = (V2VUserDetails) userDetailsService
				.loadUserByUsername("admin");
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

}
