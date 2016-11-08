package org.jembi.bsis.helpers.matchers;

import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jembi.bsis.model.bloodtesting.rules.BloodTestingRule;

public class BloodTestingRuleMatcher extends TypeSafeMatcher<BloodTestingRule> {

  private BloodTestingRule expected;

  public BloodTestingRuleMatcher(BloodTestingRule expected) {
    this.expected = expected;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("A BloodTestingRule with the following state:")
        .appendText("\nId: ").appendValue(expected.getId())
        .appendText("\nBlood test id: ").appendValue(expected.getBloodTest().getId())
        .appendText("\nCategory: ").appendValue(expected.getCategory())
        .appendText("\nDonation field changed: ").appendValue(expected.getDonationFieldChanged())
        .appendText("\nNew information: ").appendValue(expected.getNewInformation())
        .appendText("\nPattern: ").appendValue(expected.getPattern())
        .appendText("\nPending tests ids: ").appendValue(expected.getPendingTestsIds())
        .appendText("\nIs deleted: ").appendValue(expected.getIsDeleted());
  }

  @Override
  protected boolean matchesSafely(BloodTestingRule actual) {
    return Objects.equals(actual.getId(), expected.getId())
        && Objects.equals(actual.getBloodTest(), expected.getBloodTest())
        && Objects.equals(actual.getCategory(), expected.getCategory())
        && Objects.equals(actual.getDonationFieldChanged(), expected.getDonationFieldChanged())
        && Objects.equals(actual.getNewInformation(), expected.getNewInformation())
        && Objects.equals(actual.getPattern(), expected.getPattern())
        && Objects.equals(actual.getPendingTestsIds(), expected.getPendingTestsIds())
        && Objects.equals(actual.getIsDeleted(), expected.getIsDeleted())
        ;
  }
  
  public static BloodTestingRuleMatcher hasSameStateAsBloodTestingRule(BloodTestingRule expected) {
    return new BloodTestingRuleMatcher(expected);
  }

}
