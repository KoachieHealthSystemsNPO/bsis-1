package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import model.location.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import repository.LocationRepository;
import utils.PermissionConstants;
import viewmodel.LocationViewModel;
import backingform.LocationBackingForm;
import backingform.validator.LocationBackingFormValidator;
import factory.LocationViewModelFactory;

@RestController
@RequestMapping("locations")
public class LocationsController {

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private LocationBackingFormValidator locationBackingFormValidator;

  @Autowired
  private LocationViewModelFactory locationViewModelFactory;

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.setValidator(locationBackingFormValidator);
  }

  @RequestMapping(method = RequestMethod.GET)
  @PreAuthorize("hasRole('" + PermissionConstants.MANAGE_DONATION_SITES + "')")
  public Map<String, Object> configureLocationsFormGenerator(
      HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<Location> allLocations = locationRepository.getAllLocations();
    map.put("allLocations", locationViewModelFactory.createLocationViewModels(allLocations));
    return map;
  }

  @RequestMapping(method = RequestMethod.POST)
  @PreAuthorize("hasRole('" + PermissionConstants.MANAGE_DONATION_SITES + "')")
  public ResponseEntity<LocationViewModel> addLocation(
      @RequestBody @Valid LocationBackingForm formData) {
    Location location = formData.getLocation();
    location.setIsDeleted(false);
    locationRepository.saveLocation(location);
    return new ResponseEntity<>(locationViewModelFactory.createLocationViewModel(location), HttpStatus.CREATED);

  }

  @RequestMapping(value = "{id}", method = RequestMethod.PUT)
  @PreAuthorize("hasRole('" + PermissionConstants.MANAGE_DONATION_SITES + "')")
  public ResponseEntity<Map<String, Object>> updateLocation(@PathVariable Long id,
                                       @RequestBody @Valid LocationBackingForm formData) {
    Map<String, Object> map = new HashMap<String, Object>();
    Location location = formData.getLocation();
    Location updatedLocation = locationRepository.updateLocation(id, location);
    map.put("location", locationViewModelFactory.createLocationViewModel(updatedLocation));
    return new ResponseEntity<>(map, HttpStatus.OK);

  }

  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  @PreAuthorize("hasRole('" + PermissionConstants.MANAGE_DONATION_SITES + "')")
  public ResponseEntity<Map<String, Object>> getLocationById(@PathVariable Long id) {

    Map<String, Object> map = new HashMap<String, Object>();
    Location location = locationRepository.getLocation(id);
    map.put("location", locationViewModelFactory.createLocationViewModel(location));
    return new ResponseEntity<>(map, HttpStatus.OK);

  }

  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  @PreAuthorize("hasRole('" + PermissionConstants.MANAGE_DONATION_SITES + "')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLocation(@PathVariable Long id) {
    locationRepository.deleteLocation(id);
  }
}
