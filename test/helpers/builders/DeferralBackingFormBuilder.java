package helpers.builders;

import java.util.Date;

import backingform.DeferralBackingForm;
import model.donordeferral.DeferralReason;
import model.location.Location;

public class DeferralBackingFormBuilder extends AbstractBuilder<DeferralBackingForm> {
  
  private DeferralReason deferralReason;
  private Location venue;
  private Date deferredUntil;

  public DeferralBackingFormBuilder withDeferralReason(DeferralReason deferralReason) {
    this.deferralReason = deferralReason;
    return this;
  }
  
  public DeferralBackingFormBuilder withVenue(Location venue) {
    this.venue = venue;
    return this;
  }
  
  public DeferralBackingFormBuilder withDeferredUntil(Date deferredUntil) {
    this.deferredUntil = deferredUntil;
    return this;
  }

  @Override
  public DeferralBackingForm build() {
    DeferralBackingForm deferralBackingForm = new DeferralBackingForm();
    deferralBackingForm.setDeferralReason(deferralReason);
    deferralBackingForm.setVenue(venue);
    deferralBackingForm.setDeferredUntil(deferredUntil);
    return deferralBackingForm;
  }
  
  public static DeferralBackingFormBuilder aDeferralBackingForm() {
    return new DeferralBackingFormBuilder();
  }

}
