package org.jembi.bsis.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.jembi.bsis.helpers.builders.DivisionBuilder.aDivision;
import static org.jembi.bsis.helpers.matchers.DivisionMatcher.hasSameStateAsDivision;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jembi.bsis.model.location.Division;
import org.jembi.bsis.repository.DivisionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DivisionCRUDServiceTests {

  @InjectMocks
  private DivisionCRUDService divisionCRUDService;

  @Mock
  private DivisionRepository divisionRepository;

  @Test
  public void testCreateDivision_shouldSaveDivisionCorrectly() {
    String name = "aDiv";
    int level = 2;

    Division parentDivision = aDivision().build();

    Division expectedEntity = aDivision()
        .withLevel(level)
        .withName(name)
        .withParent(parentDivision)
        .build();

    Division createdDivision = divisionCRUDService.createDivision(expectedEntity);

    verify(divisionRepository).save(argThat(hasSameStateAsDivision(expectedEntity)));

    assertThat(createdDivision, is(expectedEntity));
  }
  
  @Test
  public void testUpdateDivision_shouldSetCorrectFieldsAndUpdate() {
    // Set up fixture
    long divisionId = 877L;

    Division existingDivision = aDivision()
        .withId(divisionId)
        .withName("Existing")
        .withLevel(1)
        .withParent(null)
        .build();
    
    Division updatedDivision = aDivision()
        .withId(divisionId)
        .withName("Updated")
        .withLevel(2)
        .withParent(aDivision().withId(1003L).build())
        .build();
    
    // Set up expectations
    when(divisionRepository.findDivisionById(divisionId)).thenReturn(existingDivision);
    when(divisionRepository.update(any(Division.class))).thenAnswer(returnsFirstArg());
    
    // Exercise SUT
    Division returnedDivision = divisionCRUDService.updateDivision(updatedDivision);
    
    // Verify
    verify(divisionRepository).update(argThat(hasSameStateAsDivision(updatedDivision)));
    assertThat(returnedDivision, hasSameStateAsDivision(updatedDivision));
  }
}
