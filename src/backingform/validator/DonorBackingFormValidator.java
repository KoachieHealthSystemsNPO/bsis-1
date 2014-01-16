package backingform.validator;

import java.util.Arrays;

import model.donor.Donor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import utils.CustomDateFormatter;
import viewmodel.DonorViewModel;
import backingform.DonorBackingForm;
import backingform.FindDonorBackingForm;
import controller.UtilController;

public class DonorBackingFormValidator implements Validator {

  private Validator validator;

  private UtilController utilController;

  public DonorBackingFormValidator(Validator validator, UtilController utilController) {
    super();
    this.validator = validator;
    this.utilController = utilController;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean supports(Class<?> clazz) {
    return Arrays.asList(DonorBackingForm.class, FindDonorBackingForm.class, Donor.class, DonorViewModel.class).contains(clazz);
  }

  @Override
  public void validate(Object obj, Errors errors) {
    if (obj == null || validator == null)
      return;
    
    ValidationUtils.invokeValidator(validator, obj, errors);
    DonorBackingForm form = (DonorBackingForm) obj;
    form.setBirthDate();
    //Commented the following line to fix issue #16
    updateAutogeneratedFields(form);
    //form.setDonorNumber("");
    if (utilController.isDuplicateDonorNumber(form.getDonor()))
      errors.rejectValue("donor.donorNumber", "donorNumber.nonunique",
          "There exists a donor with the same donor number.");
    validateDonorAge(form, errors);
    validateDonorHistory(form, errors);
    utilController.commonFieldChecks(form, "donor", errors);
  }

  //Commented the following method to fix issue 16, now it is unused method
  private void updateAutogeneratedFields(DonorBackingForm form) {
    if (StringUtils.isBlank(form.getDonorNumber()) &&
        utilController.isFieldAutoGenerated("donor", "donorNumber")) {
        form.setDonorNumber(utilController.getNextDonorNumber());
    }
  }

  private void validateDonorAge(DonorBackingForm form, Errors errors) {

    String birthDate = form.getBirthDate();
    
    System.out.println("age validation"+birthDate);

    if (birthDate == null)
        return;

    if (!CustomDateFormatter.isDateStringValid(birthDate)) {
      errors.rejectValue("donor.birthDate", "dateFormat.incorrect",
          CustomDateFormatter.getDateErrorMessage());
    }

    Boolean isAgeFormatCorrect = form.isAgeFormatCorrect();
    if (isAgeFormatCorrect != null && !isAgeFormatCorrect) {
      errors.rejectValue("age", "ageFormat.incorrect", "Age should be number of years.");
      return;
    }

    String errorMessage = utilController.verifyDonorAge(form.getDonor());
    if (StringUtils.isNotBlank(errorMessage))
      errors.rejectValue("age", "donor.age", errorMessage);
  }

  private void validateDonorHistory(DonorBackingForm form, Errors errors) {
  }
}
