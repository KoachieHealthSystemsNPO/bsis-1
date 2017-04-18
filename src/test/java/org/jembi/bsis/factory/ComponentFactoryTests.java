package org.jembi.bsis.factory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.jembi.bsis.helpers.builders.DonationBuilder.aDonation;
import static org.jembi.bsis.helpers.builders.ComponentBuilder.aComponent;
import static org.jembi.bsis.helpers.builders.ComponentFullViewModelBuilder.aComponentFullViewModel;
import static org.jembi.bsis.helpers.builders.ComponentManagementViewModelBuilder.aComponentManagementViewModel;
import static org.jembi.bsis.helpers.builders.ComponentTypeBuilder.aComponentType;
import static org.jembi.bsis.helpers.builders.ComponentTypeFullViewModelBuilder.aComponentTypeFullViewModel;
import static org.jembi.bsis.helpers.builders.ComponentTypeViewModelBuilder.aComponentTypeViewModel;
import static org.jembi.bsis.helpers.builders.ComponentViewModelBuilder.aComponentViewModel;
import static org.jembi.bsis.helpers.builders.LocationBuilder.aLocation;
import static org.jembi.bsis.helpers.builders.LocationViewModelBuilder.aLocationViewModel;
import static org.jembi.bsis.helpers.matchers.ComponentFullViewModelMatcher.hasSameStateAsComponentFullViewModel;
import static org.jembi.bsis.helpers.matchers.ComponentManagementViewModelMatcher.hasSameStateAsComponentManagementViewModel;
import static org.jembi.bsis.helpers.matchers.ComponentViewModelMatcher.hasSameStateAsComponentViewModel;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jembi.bsis.model.component.Component;
import org.jembi.bsis.model.component.ComponentStatus;
import org.jembi.bsis.model.componenttype.ComponentType;
import org.jembi.bsis.model.donation.Donation;
import org.jembi.bsis.model.inventory.InventoryStatus;
import org.jembi.bsis.model.location.Location;
import org.jembi.bsis.repository.ComponentRepository;
import org.jembi.bsis.service.ComponentConstraintChecker;
import org.jembi.bsis.viewmodel.ComponentFullViewModel;
import org.jembi.bsis.viewmodel.ComponentManagementViewModel;
import org.jembi.bsis.viewmodel.ComponentTypeFullViewModel;
import org.jembi.bsis.viewmodel.ComponentTypeViewModel;
import org.jembi.bsis.viewmodel.ComponentViewModel;
import org.jembi.bsis.viewmodel.LocationViewModel;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ComponentFactoryTests {
  
  @InjectMocks
  private ComponentFactory componentFactory;
  
  @Mock
  private LocationFactory locationFactory;

  @Mock
  private ComponentTypeFactory componentTypeFactory;

  @Mock
  private PackTypeFactory packTypeFactory;

  @Mock
  private ComponentConstraintChecker componentConstraintChecker;
  
  @Mock
  private ComponentRepository componentRepository;
  
  private static final UUID COMPONENT_ID_1 = UUID.randomUUID();
  private static final UUID COMPONENT_ID_2 = UUID.randomUUID();

  @Test
  public void createComponentFullViewModel_oneComponent() throws Exception {
    // set up data
    Donation donation = aDonation().withBloodAbo("A").withBloodRh("+").build();
    Location location = aLocation().withId(1L).build();

    Component parentComponent = aComponent().withId(COMPONENT_ID_2).build();
    
    ComponentType componentType = aComponentType()
        .withId(1L)
        .withComponentTypeName("name")
        .withComponentTypeCode("0000")
        .build();
    
    ComponentTypeViewModel componentTypeViewModel = aComponentTypeViewModel()
          .withId(componentType.getId())
          .withComponentTypeName(componentType.getComponentTypeName())
          .withComponentTypeCode(componentType.getComponentTypeCode())
          .build();
    LocationViewModel locationViewModel = aLocationViewModel().withId(1L).build();
    
    Component component = aComponent()
        .withId(COMPONENT_ID_1)
        .withStatus(ComponentStatus.AVAILABLE)
        .withInventoryStatus(InventoryStatus.IN_STOCK)
        .withComponentType(componentType)
        .withLocation(location)
        .withDonation(donation)
        .withParentComponent(parentComponent)
        .build();
    
    ComponentFullViewModel expectedViewModel = aComponentFullViewModel()
        .withId(COMPONENT_ID_1)
        .withStatus(ComponentStatus.AVAILABLE)
        .withInventoryStatus(InventoryStatus.IN_STOCK)
        .withComponentType(componentTypeViewModel)
        .withLocation(locationViewModel)
        .withBloodAbo(donation.getBloodAbo())
        .withBloodRh(donation.getBloodRh())
        .thatIsNotInitialComponent()
        .build();

    // setup mocks
    when(locationFactory.createViewModel(location)).thenReturn(locationViewModel);
    when(componentTypeFactory.createViewModel(componentType)).thenReturn(componentTypeViewModel);

    // run test
    ComponentFullViewModel convertedViewModel = componentFactory.createComponentFullViewModel(component);
    
    // do asserts
    Assert.assertNotNull("View model created", convertedViewModel);
    assertThat("Correct view model", convertedViewModel, hasSameStateAsComponentFullViewModel(expectedViewModel));
  }
  
  @Test
  public void createComponentFullViewModels_componentList() throws Exception {
    // set up data
    ArrayList<Component> components = new ArrayList<>();
    Donation donation = aDonation().withBloodAbo("A").withBloodRh("+").build();
    components.add(aComponent().withId(COMPONENT_ID_1).withStatus(ComponentStatus.AVAILABLE).withDonation(donation).build());
    components.add(aComponent().withId(COMPONENT_ID_2).withStatus(ComponentStatus.DISCARDED).withDonation(donation).build());
    
    // run test
    List<ComponentFullViewModel> viewModels = componentFactory.createComponentFullViewModels(components);
    
    // do asserts
    Assert.assertNotNull("View models created", viewModels);
    Assert.assertEquals("Correct number of view models created", 2, viewModels.size());
  }
  
  @Test
  public void createComponentFullViewModels_nullCollection() throws Exception {
    // set up data

    // run test
    List<ComponentFullViewModel> viewModels = componentFactory.createComponentFullViewModels(null);

    // do asserts
    Assert.assertNotNull("View models created", viewModels);
    Assert.assertTrue("No view models", viewModels.isEmpty());
  }

  @Test
  public void createComponentManagementViewModels_returnsCollection() throws Exception {
    // set up data
    ArrayList<Component> components = new ArrayList<>();
    Donation donation = aDonation().withBloodAbo("A").withBloodRh("+").build();
    components.add(aComponent().withId(COMPONENT_ID_1).withStatus(ComponentStatus.AVAILABLE).withDonation(donation).build());
    components.add(aComponent().withId(COMPONENT_ID_2).withStatus(ComponentStatus.DISCARDED).withDonation(donation).build());
    donation.addComponent(components.get(0));
    donation.addComponent(components.get(1));

    // run test
    List<ComponentManagementViewModel> viewModels = componentFactory.createManagementViewModels(components);

    // do asserts
    Assert.assertNotNull("View models created", viewModels);
    Assert.assertEquals("Correct number of view models created", 2, viewModels.size());
  }

  @Test
  public void createComponentManagementViewModelsWithNull_returnsNullCollection() throws Exception {
    // set up data

    // run test
    List<ComponentManagementViewModel> viewModels = componentFactory.createManagementViewModels(null);

    // do asserts
    Assert.assertNotNull("View models created", viewModels);
    Assert.assertTrue("No view models", viewModels.isEmpty());
  }

  @Test
  public void createManagementViewModel_oneComponent() throws Exception {
    // set up data
    Date createdOn = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(createdOn);
    cal.add(Calendar.DAY_OF_YEAR, 1);
    Date processedOn = cal.getTime();
    cal.add(Calendar.DAY_OF_YEAR, -2);
    Date expiresOn = cal.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Component initialComponent = aComponent()
        .withId(COMPONENT_ID_1)
        .withCreatedOn(createdOn)
        .build();
    Donation donation = aDonation()
        .withDonationIdentificationNumber("1234567")
        .withBleedStartTime(sdf.parse("2016-01-01 13:00"))
        .withBleedEndTime(sdf.parse("2016-01-01 13:17"))
        .withComponent(initialComponent)
        .build();
    ComponentType componentType = aComponentType().build();
    Component component = aComponent()
        .withId(COMPONENT_ID_2)
        .withStatus(ComponentStatus.AVAILABLE)
        .withComponentCode("0011")
        .withComponentType(componentType)
        .withCreatedOn(processedOn)
        .withExpiresOn(expiresOn)
        .withWeight(222)
        .withInventoryStatus(InventoryStatus.IN_STOCK)
        .withDonation(donation)
        .withParentComponent(initialComponent)
        .build();
    
    ComponentTypeFullViewModel componentTypeFullViewModel = aComponentTypeFullViewModel()
        .withId(2L)
        .build();
    
    ComponentManagementViewModel expectedViewModel = aComponentManagementViewModel()
        .withId(COMPONENT_ID_2)
        .withStatus(ComponentStatus.AVAILABLE)
        .withComponentCode("0011")
        .withComponentType(componentTypeFullViewModel)
        .withCreatedOn(processedOn)
        .withExpiresOn(expiresOn)
        .withWeigth(222)
        .withPermission("canDiscard", true)
        .withPermission("canProcess", true)
        .withPermission("canPreProcess", true)
        .withPermission("canUnprocess", true)
        .withPermission("canUndiscard", true)
        .withPermission("canRecordChildComponentWeight", true)
        .withExpiryStatus("Already expired")
        .whichHasNoComponentBatch()
        .withInventoryStatus(InventoryStatus.IN_STOCK)
        .withBleedStartTime(donation.getBleedStartTime())
        .withBleedEndTime(donation.getBleedEndTime())
        .withDonationDateTime(createdOn)
        .withParentComponentId(initialComponent.getId())
        .build();

    // setup mocks
    when(componentTypeFactory.createFullViewModel(componentType)).thenReturn(componentTypeFullViewModel);
    when(componentConstraintChecker.canDiscard(component)).thenReturn(true);
    when(componentConstraintChecker.canProcess(component)).thenReturn(true);
    when(componentConstraintChecker.canPreProcess(component)).thenReturn(true);
    when(componentConstraintChecker.canUnprocess(component)).thenReturn(true);
    when(componentConstraintChecker.canUndiscard(component)).thenReturn(true);
    when(componentConstraintChecker.canRecordChildComponentWeight(component)).thenReturn(true);

    // run test
    ComponentManagementViewModel convertedViewModel = componentFactory.createManagementViewModel(component);

    // do asserts
    Assert.assertNotNull("View model created", convertedViewModel);
    assertThat("Created correctly", convertedViewModel, hasSameStateAsComponentManagementViewModel(expectedViewModel));
  }

  @Ignore("Need to fix issue with the wrong expiry status being calculated")
  @Test
  public void createManagementViewModelWithExpiryDateInTheFuture_returnsCorrectViewModel() throws Exception {
    // set up data
    Date createdOn = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(createdOn);
    cal.add(Calendar.DAY_OF_YEAR, 1);
    Date processedOn = cal.getTime();
    cal.add(Calendar.DAY_OF_YEAR, 200);
    Date expiresOn = cal.getTime();
    Component initialComponent = aComponent().withId(COMPONENT_ID_1).build();
    Donation donation = aDonation()
        .withComponent(initialComponent)
        .build();
    ComponentType componentType = aComponentType().build();
    Component component = aComponent()
        .withId(COMPONENT_ID_2)
        .withComponentType(componentType)
        .withCreatedOn(processedOn)
        .withExpiresOn(expiresOn)
        .withDonation(donation)
        .withParentComponent(initialComponent)
        .build();

    ComponentTypeFullViewModel componentTypeFullViewModel = aComponentTypeFullViewModel()
        .withId(2L)
        .build();

    ComponentManagementViewModel expectedViewModel = aComponentManagementViewModel()
        .withId(COMPONENT_ID_2)
        .withStatus(ComponentStatus.QUARANTINED)
        .withComponentCode(null)
        .withComponentType(componentTypeFullViewModel)
        .withCreatedOn(processedOn)
        .withExpiresOn(expiresOn)
        .withWeigth(null)
        .withPermission("canDiscard", true)
        .withPermission("canProcess", true)
        .withPermission("canPreProcess", true)
        .withPermission("canUnprocess", true)
        .withPermission("canUndiscard", true)
        .withPermission("canRecordChildComponentWeight", true)
        .withExpiryStatus("200 days to expire") // note: can be "201 days to expire" in some edge cases
        .whichHasNoComponentBatch()
        .withInventoryStatus(InventoryStatus.NOT_IN_STOCK)
        .withBleedStartTime(null)
        .withBleedEndTime(null)
        .withDonationDateTime(null)
        .withParentComponentId(COMPONENT_ID_1)
        .build();

    // setup mocks
    when(componentTypeFactory.createFullViewModel(componentType)).thenReturn(componentTypeFullViewModel);
    when(componentConstraintChecker.canDiscard(component)).thenReturn(true);
    when(componentConstraintChecker.canProcess(component)).thenReturn(true);
    when(componentConstraintChecker.canPreProcess(component)).thenReturn(true);
    when(componentConstraintChecker.canUnprocess(component)).thenReturn(true);
    when(componentConstraintChecker.canUndiscard(component)).thenReturn(true);
    when(componentConstraintChecker.canRecordChildComponentWeight(component)).thenReturn(true);

    // run test
    ComponentManagementViewModel convertedViewModel = componentFactory.createManagementViewModel(component);

    // do asserts
    Assert.assertNotNull("View model created", convertedViewModel);
    System.out.println("Returned view model: " + ToStringBuilder.reflectionToString(convertedViewModel));
    assertThat("Created correctly", convertedViewModel, hasSameStateAsComponentManagementViewModel(expectedViewModel));
  }

  @Test
  public void createManagementViewModelForInitialComponent_viewModelWithNullParentComponentIdReturned() throws Exception {
    // set up data
    ComponentType componentType = aComponentType()
        .withId(1L)
        .build();
    Donation donation = aDonation().build();
    Component initialComponent = aComponent()
        .withId(COMPONENT_ID_1)
        .withComponentType(componentType)
        .withDonation(donation)
        .build();
    donation.addComponent(initialComponent);

    ComponentTypeFullViewModel componentTypeFullViewModel = aComponentTypeFullViewModel()
        .withId(1L)
        .build();

    ComponentManagementViewModel expectedViewModel = aComponentManagementViewModel()
        .withId(COMPONENT_ID_1)
        .withStatus(ComponentStatus.QUARANTINED)
        .withComponentCode(null)
        .withComponentType(componentTypeFullViewModel)
        .withCreatedOn(null)
        .withExpiresOn(null)
        .withWeigth(null)
        .withPermission("canDiscard", true)
        .withPermission("canProcess", true)
        .withPermission("canPreProcess", true)
        .withPermission("canUnprocess", true)
        .withPermission("canUndiscard", true)
        .withPermission("canRecordChildComponentWeight", true)
        .withExpiryStatus("")
        .whichHasNoComponentBatch()
        .withInventoryStatus(InventoryStatus.NOT_IN_STOCK)
        .withBleedStartTime(null)
        .withBleedEndTime(null)
        .withDonationDateTime(null)
        .withParentComponentId(null)
        .build();

    // setup mocks
    when(componentTypeFactory.createFullViewModel(componentType)).thenReturn(componentTypeFullViewModel);
    when(componentConstraintChecker.canDiscard(initialComponent)).thenReturn(true);
    when(componentConstraintChecker.canProcess(initialComponent)).thenReturn(true);
    when(componentConstraintChecker.canPreProcess(initialComponent)).thenReturn(true);
    when(componentConstraintChecker.canUnprocess(initialComponent)).thenReturn(true);
    when(componentConstraintChecker.canUndiscard(initialComponent)).thenReturn(true);
    when(componentConstraintChecker.canRecordChildComponentWeight(initialComponent)).thenReturn(true);

    // run test
    ComponentManagementViewModel convertedViewModel = componentFactory.createManagementViewModel(initialComponent);

    // do asserts
    Assert.assertNotNull("View model created", convertedViewModel);
    assertThat("Created correctly", convertedViewModel, hasSameStateAsComponentManagementViewModel(expectedViewModel));
  }

  @Test
  public void createComponentViewModel_oneComponent() throws Exception {
    // set up data
    Date createdOn = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(createdOn);
    cal.add(Calendar.DAY_OF_YEAR, -1);
    Date expiresOn = cal.getTime();

    Donation donation = aDonation()
        .withDonationIdentificationNumber("1234567")
        .withFlagCharacters("09")
        .build();
    ComponentType componentType = aComponentType().build();
    Location location = aLocation().withId(1L).build();
    Component component = aComponent()
        .withId(COMPONENT_ID_1)
        .withStatus(ComponentStatus.AVAILABLE)
        .withComponentType(componentType)
        .withComponentCode("componentCode")
        .withCreatedOn(createdOn)
        .withExpiresOn(expiresOn)
        .withDonation(donation)
        .withLocation(location)
        .build();
    
    ComponentTypeViewModel componentTypeViewModel = aComponentTypeViewModel()
        .withId(1L)
        .build();

    LocationViewModel locationViewModel = aLocationViewModel().withId(1L).build();
    
    ComponentViewModel expectedViewModel = aComponentViewModel().withId(COMPONENT_ID_1)
        .withStatus(ComponentStatus.AVAILABLE)
        .withComponentType(componentTypeViewModel)
        .withComponentCode("componentCode")
        .withCreatedOn(createdOn)
        .withExpiresOn(expiresOn)
        .withDonationIdentificationNumber("1234567")
        .withDonationFlagCharacters("09")
        .withExpiryStatus("Already expired")
        .withLocation(locationViewModel)
        .build();

    // setup mocks
    when(componentTypeFactory.createViewModel(componentType)).thenReturn(componentTypeViewModel);
    when(locationFactory.createViewModel(location)).thenReturn(locationViewModel);

    // run test
    ComponentViewModel convertedViewModel = componentFactory.createComponentViewModel(component);

    // do asserts
    Assert.assertNotNull("View model created", convertedViewModel);
    assertThat("Correct view model", convertedViewModel, hasSameStateAsComponentViewModel(expectedViewModel));
  }

  @Test
  public void createComponentViewModels_componentList() throws Exception {
    // set up data
    ArrayList<Component> components = new ArrayList<>();
    components.add(aComponent().withId(COMPONENT_ID_1).withStatus(ComponentStatus.AVAILABLE).build());
    components.add(aComponent().withId(COMPONENT_ID_2).withStatus(ComponentStatus.DISCARDED).build());

    // run test
    List<ComponentViewModel> viewModels = componentFactory.createComponentViewModels(components);

    // do asserts
    Assert.assertNotNull("View models created", viewModels);
    Assert.assertEquals("Correct number of view models created", 2, viewModels.size());
  }
  
  @Test
  public void createComponentFullViewModelWithNullParentComponent_shouldSetIntialComponent() {
    // set up data
    
    Component component = aComponent()
        .withId(COMPONENT_ID_1)
        .withStatus(ComponentStatus.AVAILABLE)
        .withInventoryStatus(InventoryStatus.IN_STOCK)
        .withParentComponent(null)
        .build();
    
    ComponentFullViewModel expectedViewModel = aComponentFullViewModel()
        .withId(COMPONENT_ID_1)
        .withStatus(ComponentStatus.AVAILABLE)
        .withInventoryStatus(InventoryStatus.IN_STOCK)
        .thatIsInitialComponent()
        .build();

    // run test
    ComponentFullViewModel returnedViewModel = componentFactory.createComponentFullViewModel(component);
  
    assertThat(returnedViewModel, is(hasSameStateAsComponentFullViewModel(expectedViewModel)));
  }
}
