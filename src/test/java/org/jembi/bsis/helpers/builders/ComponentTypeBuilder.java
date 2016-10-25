package org.jembi.bsis.helpers.builders;

import java.util.HashSet;
import java.util.Set;

import org.jembi.bsis.model.componenttype.ComponentType;
import org.jembi.bsis.model.componenttype.ComponentTypeCombination;
import org.jembi.bsis.model.componenttype.ComponentTypeTimeUnits;

public class ComponentTypeBuilder extends AbstractEntityBuilder<ComponentType> {
  
  // static counter that is used to create a unique default name and code
  private static int UNIQUE_INCREMENT = 0;

  private Long id;
  private String componentTypeName = "Component Type " + ++UNIQUE_INCREMENT;
  private Boolean isDeleted = false;
  private int expiresAfter;
  private ComponentTypeTimeUnits expiresAfterUnits = ComponentTypeTimeUnits.DAYS;
  private String componentTypeCode = String.format("%05d", UNIQUE_INCREMENT);
  private String description;
  private boolean hasBloodGroup = false;
  private Integer lowStorageTemperature;
  private Integer highStorageTemperature;
  private String preparationInfo;
  private String transportInfo;
  private String storageInfo;
  private boolean canBeIssued = true;
  private Set<ComponentTypeCombination> producedComponentTypeCombinations = new HashSet<>();
  private boolean containsPlasma = false;

  public ComponentTypeBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public ComponentTypeBuilder withComponentTypeName(String componentTypeName) {
    this.componentTypeName = componentTypeName;
    return this;
  }

  public ComponentTypeBuilder thatIsNotDeleted() {
    this.isDeleted = false;
    return this;
  }
  
  public ComponentTypeBuilder thatIsDeleted() {
    this.isDeleted = true;
    return this;
  }
  
  public ComponentTypeBuilder withExpiresAfter(int expiresAfter) {
    this.expiresAfter = expiresAfter;
    return this;
  }
  
  public ComponentTypeBuilder withExpiresAfterUnits(ComponentTypeTimeUnits expiresAfterUnits) {
    this.expiresAfterUnits = expiresAfterUnits;
    return this;
  }
  
  public ComponentTypeBuilder withDescription(String description) {
    this.description = description;
    return this;
  }
  
  public ComponentTypeBuilder thatHasBloodGroup() {
    this.hasBloodGroup = true;
    return this;
  }
  
  public ComponentTypeBuilder withComponentTypeCode(String componentTypeCode) {
    this.componentTypeCode = componentTypeCode;
    return this;
  }
  
  public ComponentTypeBuilder withLowStorageTemperature(Integer lowStorageTemperature) {
    this.lowStorageTemperature = lowStorageTemperature;
    return this;
  }
  
  public ComponentTypeBuilder withHighStorageTemperature(Integer highStorageTemperature) {
    this.highStorageTemperature = highStorageTemperature;
    return this;
  }
  
  public ComponentTypeBuilder withPreparationInfo(String preparationInfo) {
    this.preparationInfo = preparationInfo;
    return this;
  }

  public ComponentTypeBuilder withTransportInfo(String transportInfo) {
    this.transportInfo = transportInfo;
    return this;
  }

  public ComponentTypeBuilder withStorageInfo(String storageInfo) {
    this.storageInfo = storageInfo;
    return this;
  }

  public ComponentTypeBuilder withCanBeIssued(boolean canBeIssued) {
    this.canBeIssued = canBeIssued;
    return this;
  }

  public ComponentTypeBuilder withProducedComponentTypeCombination(ComponentTypeCombination producedComponentTypeCombination) {
    this.producedComponentTypeCombinations.add(producedComponentTypeCombination);
    return this;
  }
  
  public ComponentTypeBuilder thatContainsPlasma() {
    this.containsPlasma = true;;
    return this;
  }

  @Override
  public ComponentType build() {
    ComponentType componentType = new ComponentType();
    componentType.setId(id);
    componentType.setComponentTypeName(componentTypeName);
    componentType.setIsDeleted(isDeleted);
    componentType.setExpiresAfter(expiresAfter);
    componentType.setExpiresAfterUnits(expiresAfterUnits);
    componentType.setComponentTypeCode(componentTypeCode);
    componentType.setDescription(description);
    componentType.setHasBloodGroup(hasBloodGroup);
    componentType.setLowStorageTemperature(lowStorageTemperature);
    componentType.setHighStorageTemperature(highStorageTemperature);
    componentType.setPreparationInfo(preparationInfo);
    componentType.setProducedComponentTypeCombinations(producedComponentTypeCombinations);
    componentType.setTransportInfo(transportInfo);
    componentType.setStorageInfo(storageInfo);
    componentType.setCanBeIssued(canBeIssued);
    componentType.setContainsPlasma(containsPlasma);
    return componentType;
  }

  public static ComponentTypeBuilder aComponentType() {
    return new ComponentTypeBuilder();
  }

}
