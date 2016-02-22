package backingform.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;

import backingform.DeferralBackingForm;
import model.donordeferral.DeferralReason;

import static helpers.builders.DeferralReasonBuilder.aDeferralReason;


@RunWith(MockitoJUnitRunner.class)
public class DeferralBackingFormValidatorTest {
  @InjectMocks
  DeferralBackingFormValidator deferralBackingFormValidator;

  @Test
  public void testValid() throws Exception {
    DeferralReason deferralReason = aDeferralReason().withId(1L).build();

    DeferralBackingForm deferralBackingForm = new DeferralBackingForm();
    deferralBackingForm.setDeferralReason(deferralReason);
    deferralBackingForm.setVenueId(1L);

    // run test
    Errors errors = new MapBindingResult(new HashMap<String, String>(), "deferral");
    deferralBackingFormValidator.validate(deferralBackingForm, errors);

    // check asserts
    Assert.assertEquals("No errors exist", 0, errors.getErrorCount());
  }

}
