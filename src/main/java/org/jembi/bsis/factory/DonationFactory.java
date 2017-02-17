package org.jembi.bsis.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jembi.bsis.backingform.DonationBackingForm;
import org.jembi.bsis.model.donation.Donation;
import org.jembi.bsis.repository.DonorRepository;
import org.jembi.bsis.repository.PackTypeRepository;
import org.jembi.bsis.service.DonationConstraintChecker;
import org.jembi.bsis.service.DonorConstraintChecker;
import org.jembi.bsis.viewmodel.AdverseEventViewModel;
import org.jembi.bsis.viewmodel.DonationViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationFactory {

  @Autowired
  private DonationConstraintChecker donationConstraintChecker;
  @Autowired
  private AdverseEventFactory adverseEventFactory;
  @Autowired
  private DonorConstraintChecker donorConstraintChecker;
  @Autowired
  private LocationFactory locationFactory;
  @Autowired
  private PackTypeFactory packTypeFactory;
  @Autowired
  private DonorRepository donorRepository;
  @Autowired
  private PackTypeRepository packTypeRepository;
  @Autowired
  private DonationTypeFactory donationTypeFactory;

  public Donation createEntity(DonationBackingForm form) {
    Donation donation = form.getDonation();
    donation.setDonor(donorRepository.findDonorById(donation.getDonor().getId()));
    donation.setPackType(packTypeRepository.getPackTypeById(donation.getPackType().getId()));
    donation.setAdverseEvent(adverseEventFactory.createEntity(form.getAdverseEvent()));
    return donation;
  }

  public List<DonationViewModel> createDonationViewModelsWithPermissions(List<Donation> donations) {
    List<DonationViewModel> donationViewModels = new ArrayList<>();
    for (Donation donation : donations) {
      donationViewModels.add(createDonationViewModelWithPermissions(donation));
    }
    return donationViewModels;
  }

  public List<DonationViewModel> createDonationViewModelsWithoutPermissions(List<Donation> donations) {
    List<DonationViewModel> donationViewModels = new ArrayList<>();
    for (Donation donation : donations) {
      donationViewModels.add(createDonationViewModelWithoutPermissions(donation));
    }
    return donationViewModels;
  }

  public DonationViewModel createDonationViewModelWithPermissions(Donation donation) {
    DonationViewModel donationViewModel = createDonationViewModelWithoutPermissions(donation);

    boolean canDonate = !donorConstraintChecker.isDonorDeferred(donation.getDonor().getId());
    boolean isBackEntry = donation.getDonationBatch().isBackEntry();

    // Populate permissions
    Map<String, Boolean> permissions = new HashMap<>();
    permissions.put("canDelete", donationConstraintChecker.canDeleteDonation(donation.getId()));
    permissions.put("canEditBleedTimes", donationConstraintChecker.canEditBleedTimes(donation.getId()));
    permissions.put("canDonate", canDonate);
    permissions.put("canEditPackType", donationConstraintChecker.canEditPackType(donation));
    permissions.put("isBackEntry", isBackEntry);
    donationViewModel.setPermissions(permissions);

    return donationViewModel;
  }

  public DonationViewModel createDonationViewModelWithoutPermissions(Donation donation) {
    DonationViewModel donationViewModel = new DonationViewModel();
    donationViewModel.setId(donation.getId());
    donationViewModel.setDonationDate(donation.getDonationDate());
    donationViewModel.setDonationIdentificationNumber(donation.getDonationIdentificationNumber());
    donationViewModel.setDonationType(donationTypeFactory.createViewModel(donation.getDonationType()));
    donationViewModel.setPackType(packTypeFactory.createFullViewModel(donation.getPackType()));
    donationViewModel.setNotes(donation.getNotes());
    donationViewModel.setDonorNumber(donation.getDonorNumber());
    donationViewModel.setLastUpdated(donation.getLastUpdated());
    donationViewModel.setCreatedDate(donation.getCreatedDate());
    donationViewModel.setTTIStatus(donation.getTTIStatus());
    donationViewModel.setDonationBatchNumber(donation.getDonationBatchNumber());
    donationViewModel.setBloodTypingStatus(donation.getBloodTypingStatus());
    donationViewModel.setBloodTypingMatchStatus(donation.getBloodTypingMatchStatus());
    donationViewModel.setBloodAbo(donation.getBloodAbo());
    donationViewModel.setBloodRh(donation.getBloodRh());
    donationViewModel.setHaemoglobinCount(donation.getHaemoglobinCount());
    donationViewModel.setHaemoglobinLevel(donation.getHaemoglobinLevel());
    donationViewModel.setDonorWeight(donation.getDonorWeight());
    donationViewModel.setDonorPulse(donation.getDonorPulse());
    donationViewModel.setBloodPressureSystolic(donation.getBloodPressureSystolic());
    donationViewModel.setBloodPressureDiastolic(donation.getBloodPressureDiastolic());
    donationViewModel.setBleedStartTime(donation.getBleedStartTime());
    donationViewModel.setBleedEndTime(donation.getBleedEndTime());
    donationViewModel.setVenue(locationFactory.createViewModel(donation.getVenue()));
    donationViewModel.setReleased(donation.isReleased());

    if (donation.getAdverseEvent() != null) {
      AdverseEventViewModel adverseEventViewModel =
          adverseEventFactory.createAdverseEventViewModel(donation.getAdverseEvent());
      donationViewModel.setAdverseEvent(adverseEventViewModel);
    }

    return donationViewModel;
  }

}
