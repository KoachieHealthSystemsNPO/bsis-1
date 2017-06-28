package org.jembi.bsis.helpers.matchers;

import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jembi.bsis.viewmodel.TestBatchFullViewModel;

public class TestBatchFullViewModelMatcher extends TypeSafeMatcher<TestBatchFullViewModel> {

  private TestBatchFullViewModel expected;

  public TestBatchFullViewModelMatcher(TestBatchFullViewModel expected) {
    this.expected = expected;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("A test batch view model with the following state:")
        .appendText("\nId: ").appendValue(expected.getId())
        .appendText("\nStatus: ").appendValue(expected.getStatus())
        .appendText("\nBatch number: ").appendValue(expected.getBatchNumber())
        .appendText("\nTest batch date: ").appendValue(expected.getTestBatchDate())
        .appendText("\nLast updated date: ").appendValue(expected.getLastUpdated())
        .appendText("\nNotes: ").appendValue(expected.getNotes())
        .appendText("\nDonation batches: ").appendValue(expected.getDonationBatches())
        .appendText("\nPermissions: ").appendValue(expected.getPermissions())
        .appendText("\nReady for release count: ").appendValue(expected.getReadyForReleaseCount());
  }

  @Override
  protected void describeMismatchSafely(TestBatchFullViewModel actual, Description description) {

    description.appendText("The following fields did not match:");

    if (!Objects.equals(actual.getId(), expected.getId())) {
      description.appendText("\nId: expected = ").appendValue(expected.getId())
          .appendText(", actual = ").appendValue(actual.getId());
    }

    if (!Objects.equals(actual.getStatus(), expected.getStatus())) {
      description.appendText("\nStatus: expected = ").appendValue(expected.getStatus())
          .appendText(", actual = ").appendValue(actual.getStatus());
    }

    if (!Objects.equals(actual.getBatchNumber(), expected.getBatchNumber())) {
      description.appendText("\nBatch number: expected = ").appendValue(expected.getBatchNumber())
          .appendText(", actual = ").appendValue(actual.getBatchNumber());
    }

    if (!Objects.equals(actual.getTestBatchDate(), expected.getTestBatchDate())) {
      description.appendText("\nTest batch date: expected = ").appendValue(expected.getTestBatchDate())
          .appendText(", actual = ").appendValue(actual.getTestBatchDate());
    }

    if (!Objects.equals(actual.getLastUpdated(), expected.getLastUpdated())) {
      description.appendText("\nLast updated: expected = ").appendValue(expected.getLastUpdated())
          .appendText(", actual = ").appendValue(actual.getLastUpdated());
    }

    if (!Objects.equals(actual.getNotes(), expected.getNotes())) {
      description.appendText("\nNotes: expected = ").appendValue(expected.getNotes())
          .appendText(", actual = ").appendValue(actual.getNotes());
    }

    if (!Objects.equals(actual.getDonationBatches(), expected.getDonationBatches())) {
      description.appendText("\nDonation batches: expected = ").appendValue(expected.getDonationBatches())
          .appendText(", actual = ").appendValue(actual.getDonationBatches());
    }

    if (!Objects.equals(actual.getPermissions(), expected.getPermissions())) {
      description.appendText("\nPermissions: expected = ").appendValue(expected.getPermissions())
          .appendText(", actual = ").appendValue(actual.getPermissions());
    }

    if (!Objects.equals(actual.getReadyForReleaseCount(), expected.getReadyForReleaseCount())) {
      description.appendText("\nReady for release count: expected = ").appendValue(expected.getReadyForReleaseCount())
          .appendText(", actual = ").appendValue(actual.getReadyForReleaseCount());
    }
  }

  @Override
  public boolean matchesSafely(TestBatchFullViewModel actual) {
    return Objects.equals(actual.getId(), expected.getId()) &&
        Objects.equals(actual.getStatus(), expected.getStatus()) &&
        Objects.equals(actual.getBatchNumber(), expected.getBatchNumber()) &&
        Objects.equals(actual.getTestBatchDate(), expected.getTestBatchDate()) &&
        Objects.equals(actual.getLastUpdated(), expected.getLastUpdated()) &&
        Objects.equals(actual.getNotes(), expected.getNotes()) &&
        Objects.equals(actual.getDonationBatches(), expected.getDonationBatches()) &&
        Objects.equals(actual.getPermissions(), expected.getPermissions()) &&
        Objects.equals(actual.getReadyForReleaseCount(), expected.getReadyForReleaseCount());
  }

  public static TestBatchFullViewModelMatcher hasSameStateAsTestBatchFullViewModel(TestBatchFullViewModel expected) {
    return new TestBatchFullViewModelMatcher(expected);
  }

}
