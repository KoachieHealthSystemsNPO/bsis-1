package org.jembi.bsis.backingform;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jembi.bsis.model.returnform.ReturnStatus;
import org.jembi.bsis.utils.DateTimeSerialiser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ReturnFormBackingForm {

  private Long id;

  private Date returnDate;

  private LocationBackingForm returnedFrom;

  private LocationBackingForm returnedTo;

  private ReturnStatus status;

  private List<ComponentPreProcessingBackingForm> components;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getReturnDate() {
    return returnDate;
  }

  @JsonSerialize(using = DateTimeSerialiser.class)
  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
  }

  public LocationBackingForm getReturnedFrom() {
    return returnedFrom;
  }

  public void setReturnedFrom(LocationBackingForm returnedFrom) {
    this.returnedFrom = returnedFrom;
  }

  public LocationBackingForm getReturnedTo() {
    return returnedTo;
  }

  public void setReturnedTo(LocationBackingForm returnedTo) {
    this.returnedTo = returnedTo;
  }

  public ReturnStatus getStatus() {
    return status;
  }

  public void setStatus(ReturnStatus status) {
    this.status = status;
  }

  public List<ComponentPreProcessingBackingForm> getComponents() {
    return components;
  }

  public void setComponents(List<ComponentPreProcessingBackingForm> components) {
    this.components = components;
  }
  
  @JsonIgnore
  public void setPermissions(Map<String, Boolean> permissions) {
    // Ignore
  }

}
