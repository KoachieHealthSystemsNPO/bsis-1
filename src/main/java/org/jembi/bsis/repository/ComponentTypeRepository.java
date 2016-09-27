package org.jembi.bsis.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jembi.bsis.model.componenttype.ComponentType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ComponentTypeRepository {

  @PersistenceContext
  private EntityManager em;

  public List<ComponentType> getAllComponentTypes() {
    TypedQuery<ComponentType> query;
    query = em.createQuery("SELECT ct from ComponentType ct where ct.isDeleted=:isDeleted", ComponentType.class);
    query.setParameter("isDeleted", false);
    return query.getResultList();
  }

  public List<ComponentType> getAllComponentTypesThatCanBeIssued() {
    return em.createNamedQuery(ComponentTypeQueryConstants.NAME_GET_COMPONENT_TYPES_THAT_CAN_BE_ISSUED, ComponentType.class)
        .setParameter("deleted", false)
        .getResultList();
  }

  public List<ComponentType> getAllComponentTypesIncludeDeleted() {
    TypedQuery<ComponentType> query;
    query = em.createQuery("SELECT ct from ComponentType ct", ComponentType.class);
    return query.getResultList();
  }

  public boolean verifyComponentTypeExists(Long id) {
    return em.createNamedQuery(ComponentTypeQueryConstants.NAME_VERIFY_COMPONENT_TYPE_WITH_ID_EXISTS, Boolean.class)
        .setParameter("id", id)
        .setParameter("deleted", false)
        .getSingleResult();
  }

  public boolean isUniqueComponentTypeName(Long id, String componentTypeName) {
    // passing null as the ID parameter does not work because the IDs in mysql are never null. So if
    // id is null, the below rather uses -1 which achieves the same result in the case of this
    // query.
    return em.createNamedQuery(ComponentTypeQueryConstants.NAME_VERIFY_UNIQUE_COMPONENT_TYPE_NAME, Boolean.class)
        .setParameter("id", id != null ? id : -1L)
        .setParameter("componentTypeName", componentTypeName)
        .setParameter("deleted", false)
        .getSingleResult();
  }

  public ComponentType getComponentTypeById(Long id) throws NoResultException, NonUniqueResultException {
    TypedQuery<ComponentType> query;
    query = em.createQuery("SELECT ct from ComponentType ct " +
        "where ct.id=:id", ComponentType.class);
    query.setParameter("id", id);
    if (query.getResultList().size() == 0)
      return null;
    return query.getSingleResult();
  }

  public ComponentType saveComponentType(ComponentType componentType) {
    em.persist(componentType);
    return componentType;
  }

  public ComponentType updateComponentType(ComponentType componentType) {
    ComponentType existingComponentType = getComponentTypeById(componentType.getId());
    existingComponentType.copy(componentType);
    return em.merge(existingComponentType);
  }
  
  /**
   * N.B. Includes deleted component types.
   */
  public ComponentType findComponentTypeByCode(String componentTypeCode) throws NoResultException {
    return em.createNamedQuery(ComponentTypeQueryConstants.NAME_FIND_COMPONENT_TYPE_BY_CODE, ComponentType.class)
        .setParameter("componentTypeCode", componentTypeCode)
        .getSingleResult();
  }
}
