package org.jembi.bsis.repository;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.jembi.bsis.model.user.Role;
import org.jembi.bsis.model.user.User;
import org.jembi.bsis.suites.DBUnitContextDependentTestSuite;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test using DBUnit to test the UserRepository
 */
public class UserRepositoryTest extends DBUnitContextDependentTestSuite {

  @Override
  protected IDataSet getDataSet() throws Exception {
    File file = new File("src/test/resources/dataset/UserRepositoryDataset.xml");
    return new FlatXmlDataSetBuilder().setColumnSensing(true).build(file);
  }

  @Test
  public void testGetAllUsers() throws Exception {
    List<User> all = userRepository.getAllUsers();
    Assert.assertNotNull("There are Users defined", all);
    Assert.assertEquals("There are 16 Users", 16, all.size());
  }

  @Test
  public void testGetAllRoles() throws Exception {
    List<Role> all = userRepository.getUserRole(new String[]{"1", "2", "3"});
    Assert.assertNotNull("There are Roles defined", all);
    Assert.assertEquals("There are 3 Roles", 3, all.size());
  }

  @Test
  public void testGetAllRolesNull() throws Exception {
    List<Role> all = userRepository.getUserRole(null);
    Assert.assertNotNull("There are Roles defined", all);
    Assert.assertTrue("There are no Roles", all.isEmpty());
  }

  @Test(expected = javax.persistence.NoResultException.class)
  public void testGetAllRolesUnknown() throws Exception {
    userRepository.getUserRole(new String[]{"123"});
  }

  @Test
  public void testFindUserById() throws Exception {
    User user = userRepository.findUserById(UUID.fromString("9bb07a38-eb7f-4e35-b5b1-34e77ad79a81"));
    Assert.assertNotNull("User is defined", user);
    Assert.assertEquals("User is correct", "superuser", user.getUsername());
  }

  @Test(expected = javax.persistence.NoResultException.class)
  public void testFindUserByIdUnknown() throws Exception {
    userRepository.findUserById(UUID.fromString("9bb07a38-eb7f-4e35-b5b1-34e77ad79123"));
  }

  @Test
  public void testAddUser() throws Exception {
    User user = new User();
    user.setUsername("tester");
    user.setFirstName("Test");
    user.setLastName("Er");
    user.setPassword("tester");
    user.setIsActive(true);
    User savedUser = userRepository.addUser(user);
    Assert.assertNotNull("User id is set after save", savedUser.getId());
  }

  @Test
  public void testUpdateBasicUserInfo() throws Exception {
    User user = userRepository.findUserById(UUID.fromString("9bb07a38-eb7f-4e35-b5b1-34e77ad79a81"));
    user.setFirstName("Test");
    user.setLastName("Tester");
    user.setEmailId("test@jembi.org");
    userRepository.updateBasicUserInfo(user, false);
    User savedUser = userRepository.findUserById(UUID.fromString("9bb07a38-eb7f-4e35-b5b1-34e77ad79a81"));
    Assert.assertEquals("FirstName changed", "Test", savedUser.getFirstName());
    Assert.assertEquals("LastName changed", "Tester", savedUser.getLastName());
    Assert.assertEquals("Email changed", "test@jembi.org", savedUser.getEmailId());
    Assert.assertEquals("Password was not changed", user.getPassword(), savedUser.getPassword());
  }

  @Test
  public void testUpdateBasicUserInfoPassword() throws Exception {
    User user = userRepository.findUserById(UUID.fromString("9bb07a38-eb7f-4e35-b5b1-34e77ad79a81"));
    user.setPassword("newpassword");
    userRepository.updateBasicUserInfo(user, true);
    User savedUser = userRepository.findUserById(UUID.fromString("9bb07a38-eb7f-4e35-b5b1-34e77ad79a81"));
    Assert.assertFalse("Password was changed", !user.getPassword().equals(savedUser.getPassword()));
  }

  @Test
  public void testUpdateLastLogin() throws Exception {
    User user = userRepository.findUserById(UUID.fromString("9bb07a38-eb7f-4e35-b5b1-34e77ad79a81"));
    userRepository.updateLastLogin(user);
    User savedUser = userRepository.findUserById(UUID.fromString("9bb07a38-eb7f-4e35-b5b1-34e77ad79a81"));
    Assert.assertFalse("Last login was changed", !user.getLastLogin().equals(savedUser.getLastLogin()));
  }
}
