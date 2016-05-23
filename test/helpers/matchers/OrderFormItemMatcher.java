package helpers.matchers;

import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import model.order.OrderFormItem;

public class OrderFormItemMatcher extends TypeSafeMatcher<OrderFormItem> {
  
  private OrderFormItem expected;

  public OrderFormItemMatcher(OrderFormItem expected) {
    this.expected = expected;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("An order form item with the following state:")
      .appendText("\nId: ").appendValue(expected.getId())
      .appendText("\nBloodAbo: ").appendValue(expected.getBloodAbo())
      .appendText("\nBloodRh: ").appendValue(expected.getBloodRh())
      .appendText("\nComponentType: ").appendValue(expected.getComponentType())
      .appendText("\nNumberOfUnits: ").appendValue(expected.getNumberOfUnits())
      .appendText("\nLastUpdated: ").appendValue(expected.getLastUpdated())
      .appendText("\nLastUpdatedBy: ").appendValue(expected.getLastUpdatedBy());
  }

  @Override
  protected boolean matchesSafely(OrderFormItem actual) {
    return Objects.equals(actual.getId(), expected.getId())
        && Objects.equals(actual.getBloodAbo(), expected.getBloodAbo())
        && Objects.equals(actual.getBloodRh(), expected.getBloodRh())
        && Objects.equals(actual.getComponentType(), expected.getComponentType())
        && Objects.equals(actual.getNumberOfUnits(), expected.getNumberOfUnits())
        && Objects.equals(actual.getLastUpdated(), expected.getLastUpdated())
        && Objects.equals(actual.getLastUpdatedBy(), expected.getLastUpdatedBy());
  }
  
  public static OrderFormItemMatcher hasSameStateAsOrderFormItem(OrderFormItem expected) {
    return new OrderFormItemMatcher(expected);
  }

}
