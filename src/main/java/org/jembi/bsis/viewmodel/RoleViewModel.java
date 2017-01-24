package org.jembi.bsis.viewmodel;

import java.util.Set;

import org.jembi.bsis.model.user.Permission;
import org.jembi.bsis.model.user.Role;

public class RoleViewModel {

  private Role role;

  public RoleViewModel() {
  }

  public RoleViewModel(Role role) {
    this.role = role;
  }

  public Long getId() {
    return role.getId();
  }

  public String getName() {
    return role.getName();
  }

  public Set<Permission> getPermissions() {
    return role.getPermissions();
  }

  public String getDescription() {
    return role.getDescription();
  }

}
