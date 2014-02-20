package repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
	private long dbid, deletedbid, updatedbid,donoridfordefer;
	private String donorNumber;
	private DonorBackingForm donorBackingForm;
	private DonorBackingFormValidator donorBackingFormValidator;
	String DateToString = null;
	private BindException errors;
	ApplicationContext applicationContext = null;
	UserDetailsService userDetailsService;

	@Before
	public void init() {
		applicationContext = new ClassPathXmlApplicationContext(
				"file:**/security-v2v-servlet.xml");
		dbid = 218;// For Find Record set db id here.
		donoridfordefer =230;//Donor Id for defer list
		deletedbid = 217; // Delete datbase record set db id here.
		updatedbid = 218;// For update record set db id here.
		donorNumber = "000221";
		DateToString = "10/06/1989";
		validator = new DonorBackingFormValidator();
		donorBackingFormValidator = new DonorBackingFormValidator(validator,
				utilController);
		donor = new Donor();
		donorBackingForm = new DonorBackingForm(donor);
		user = new User();
		user.setId(2);

		userDetailsService = applicationContext.getBean(LoginUserService.class);

	}

	@Test
	public void testAddDonor() {
		
		donorBackingForm.setBirthDate(DateToString);
		// Donorrandomnumber generation
		donorBackingForm.setDonorNumber("");
		setBackingFormValue(donorBackingForm);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		donorBackingFormValidator.validate(obj, errors);
		List<ObjectError> list = errors.getAllErrors();
		for (ObjectError oe : list) {
			System.out.println(oe.getDefaultMessage());
		}
		assertFalse(errors.hasErrors());
		Donor donor = donorBackingForm.getDonor();
		donorRepository.addDonor(donor);
		boolean isIdZero = donor.getId() == 0 ? false : true;
		// dbid = donor.getId();
		assertTrue(isIdZero);
		System.out.println("success");
	}

	 @Test
	public void testGenerateUniqueDonorNumber() {
		String generateRandomUniqueNo = DonorRepository
				.generateUniqueDonorNumber();
		assertFalse(StringUtils.isEmpty(generateRandomUniqueNo));
	}

	 @Test
	public void testfindDonorByDonorNumber() {
		Donor donor = donorRepository
				.findDonorByDonorNumber(donorNumber, false);
		System.out.println("Donor ::::" + donor);
		assertNotNull(donor);
	}

	/*
	 * Issue 
	 * doonorHash value is set to null. 
	 * donor status value is set to  null.
	 */

	@Test
	public void testSaveDonor() {
		donorBackingForm.setBirthDate(DateToString);
		donorBackingForm.setDonorNumber("");
		setBackingFormValue(donorBackingForm);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		donorBackingFormValidator.validate(obj, errors);
		List<ObjectError> list = errors.getAllErrors();
		for (ObjectError oe : list) {
			System.out.println(oe.getDefaultMessage());
		}
		assertFalse(errors.hasErrors());
		Donor donor = donorBackingForm.getDonor();
		donorRepository.saveDonor(donor);
		
		boolean isIdZero = donor.getId() == 0 ? false : true;
		// dbid = donor.getId();
		assertTrue(isIdZero);
		System.out.println("success");
	}

	 @Test
	public void testUpdateDonor() {
		this.authentication();
		Donor editDonor = donorRepository.findDonorById(updatedbid);
		donorBackingForm = new DonorBackingForm(editDonor);
		setBackingUpdateFormValue(donorBackingForm);
		errors = new BindException(donorBackingForm, "donor");
		Object obj = donorBackingForm;
		donorBackingFormValidator.validate(obj, errors);
		List<ObjectError> list = errors.getAllErrors();
		for (ObjectError oe : list) {
			System.out.println(oe.getDefaultMessage());
		}
		assertFalse(errors.hasErrors());
		System.out.println("editDonor__________" + editDonor.getLastUpdated());
		Donor updateDonor = donorBackingForm.getDonor();
		assertNotNull(donorRepository.updateDonor(updateDonor));
	}

	

	 @Test
	public void testgetAllDonors() {
		List<Donor> lists = donorRepository.getAllDonors();
		for (Donor donor : lists) {
			assertFalse(donor.getIsDeleted());
		}
	}
	
	 @Test
	public void testFindDonorByIdLong() {
		Donor donor = donorRepository.findDonorById(dbid);
		assertNotNull("Find Donor By Id method argument is long", donor);
	}

	 @Test
	public void testFindDonorByIdStringWithoutSpace() {
		Donor donor = donorRepository.findDonorById(String.valueOf(dbid));
		assertNotNull("Find Donor By Id method argument is String number",
				donor);
	}

	 @Test
	public void testFindDonorByNumber() {
		Donor donor = donorRepository.findDonorByNumber(donorNumber);
		assertNotNull("Find Donor by DonorNumber", donor);
	}

	 @Test
	public void testDeleteDonorById() {
		donorRepository.deleteDonor(deletedbid);
		assertTrue(true);
	}

	 @Test
	public void testdeferDonor() {
		boolean isScucess = false;
		try {
			this.authentication();
			donorRepository.deferDonor(String.valueOf(donoridfordefer), "28/02/2014", "1", "text");
			isScucess = true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			isScucess = false;
		} finally {
			assertTrue(isScucess);
		}
	}

	@Test
	public void testaddAllDonors() {
		Donor donor = new Donor();
		DonorBackingForm donorBackingForm = new DonorBackingForm(donor);
		donorBackingForm.setBirthDate(DateToString);
		donorBackingForm.setDonorNumber(sequenceNumberRepository
				.getNextDonorNumber());
		setBackingFormValue(donorBackingForm);
		List<Donor> listAllDonor = new ArrayList<Donor>();
		listAllDonor.add(donorBackingForm.getDonor());
		donor = new Donor();
		donorBackingForm = new DonorBackingForm(donor);
		DateToString = "11/06/1991";
		donorBackingForm.setBirthDate(DateToString);
		donorBackingForm.setDonorNumber(sequenceNumberRepository
				.getNextDonorNumber());
		setBackingFormValue(donorBackingForm);
		listAllDonor.add(donorBackingForm.getDonor());
		donor = new Donor();
		donorBackingForm = new DonorBackingForm(donor);
		DateToString = "12/06/1991";
		donorBackingForm.setBirthDate(DateToString);
		donorBackingForm.setDonorNumber(sequenceNumberRepository
				.getNextDonorNumber());
		setBackingFormValue(donorBackingForm);
		listAllDonor.add(donorBackingForm.getDonor());
		donorRepository.addAllDonors(listAllDonor);
	}

	 @Test
	public void testGetDeferralReasons() {
		List<DeferralReason> list = donorRepository.getDeferralReasons();
		if (list != null && list.size() > 0) {
			for (DeferralReason deferralReason : list) {
				if (deferralReason.getIsDeleted() == true) {
					assertTrue(false);
				}
			}
		}
	}

	 @Test
	public void testGetDonorDeferrals() {
		List<DonorDeferral> list = donorRepository.getDonorDeferrals(1l);
		if (list != null && list.size() > 0) {
			for (DonorDeferral donorDeferral : list) {
				System.out.println(donorDeferral.getId());
			}
		}

	}

	 @Test
	public void testisCurrentlyDeferredPassList() {
		List<DonorDeferral> list = donorRepository.getDonorDeferrals(donoridfordefer);
		System.out.println("Is defer donor found with list as a argument:::"
				+ donorRepository.isCurrentlyDeferred(list));
	}

	 @Test
	public void testisCurrentlyDeferredPassDonor() {
		Donor donor = donorRepository.findDonorById(230l);
		System.out
				.println("Is defer donor found with Donor argument as argument:::"
						+ donorRepository.isCurrentlyDeferred(donor));
	}

	@Test
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

	
	 @Test
	public void testFindAnyDonorStartWith() {
		String term = "Fir";

		System.out.println("Term:::" + term);
		List<Donor> listObject = donorRepository.findAnyDonorStartsWith(term);

		if (listObject != null && listObject.size() > 0) {
			System.out
					.println("ID\tDonorNumber\tFirstName\tLastName\tGender\tBirthDate");
			for (Donor donor : listObject) {
				System.out.println();
				System.out.print(donor.getId());
				System.out.print("\t");
				System.out.print(donor.getDonorNumber());
				System.out.print("\t");
				System.out.print(donor.getFirstName());
				System.out.print("\t\t");
				System.out.print(donor.getLastName());
				System.out.print("\t");
				System.out.print(donor.getGender());
				System.out.print("\t");
				System.out.print(donor.getBirthDate());
				System.out.println();
			}
		}

	}
	 
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
	}

	 
	public void authentication() {
		V2VUserDetails userDetails = (V2VUserDetails) userDetailsService
				.loadUserByUsername("admin");
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

}
