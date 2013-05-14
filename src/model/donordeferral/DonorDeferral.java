package model.donordeferral;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import model.donor.Donor;
import model.user.User;

@Entity
@Audited
public class DonorDeferral {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable=false, updatable=false, insertable=false)
  private Long id;

  @ManyToOne
  private Donor deferredDonor;

  @Temporal(TemporalType.DATE)
  private Date deferredOn;

  @Temporal(TemporalType.DATE)
  private Date deferredUntil;

  @ManyToOne
  private User deferredBy;

  @ManyToOne
  private DeferralReason deferralReason;

  @Lob
  private String deferralReasonText;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Donor getDeferredDonor() {
    return deferredDonor;
  }

  public void setDeferredDonor(Donor deferredDonor) {
    this.deferredDonor = deferredDonor;
  }

  public Date getDeferredOn() {
    return deferredOn;
  }

  public void setDeferredOn(Date deferredOn) {
    this.deferredOn = deferredOn;
  }

  public Date getDeferredUntil() {
    return deferredUntil;
  }

  public void setDeferredUntil(Date deferredUntil) {
    this.deferredUntil = deferredUntil;
  }

  public DeferralReason getDeferralReason() {
    return deferralReason;
  }

  public void setDeferralReason(DeferralReason deferralReason) {
    this.deferralReason = deferralReason;
  }

  public User getDeferredBy() {
    return deferredBy;
  }

  public void setDeferredBy(User deferredBy) {
    this.deferredBy = deferredBy;
  }

  public String getDeferralReasonText() {
    return deferralReasonText;
  }

  public void setDeferralReasonText(String deferralReasonText) {
    this.deferralReasonText = deferralReasonText;
  }
}
