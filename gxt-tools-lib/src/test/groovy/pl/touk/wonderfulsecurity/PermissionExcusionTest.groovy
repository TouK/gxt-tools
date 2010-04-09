/*
 * Copyright (c) 2008 TouK.pl
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.touk.wonderfulsecurity;


import javax.annotation.Resource
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import pl.touk.wonderfulsecurity.beans.WsecGroup
import pl.touk.wonderfulsecurity.beans.WsecRole
import pl.touk.wonderfulsecurity.beans.WsecUser
import pl.touk.wonderfulsecurity.dao.WsecGroupDao
import pl.touk.wonderfulsecurity.dao.WsecPermissionDao
import pl.touk.wonderfulsecurity.dao.WsecRoleDao
import pl.touk.wonderfulsecurity.dao.WsecUserDao
import pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
@RunWith(SpringJUnit4ClassRunner.class)
// specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations = ["classpath:ws-library-sessionFactory-context.xml", "classpath:ws-library-dao-context.xml", "classpath:datasource-context.xml"])
@TransactionConfiguration(transactionManager = "wsHibernateTxManager", defaultRollback = true)
@Transactional
public class PermissionExclusionTest {

  private static final Log log = LogFactory.getLog(PermissionExclusionTest.class)

  @Resource
  WsecUserDao wsecUserDao;

  @Resource
  WsecGroupDao wsecGroupDao

  @Resource
  WsecRoleDao wsecRoleDao

  @Resource
  WsecPermissionDao wsecPermissionDao

  @Test(expected = PermissionCollisionException.class)
  public void testDirectAssignedToUserPermissionsCollision() {
    WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
    czesio.addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO12"))
  }

  @Test(expected = PermissionCollisionException.class)
  public void testAssignedToGroupAndToUserPermissionsCollision() {
    WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
    czesio.addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO14"))
  }

  @Test(expected = PermissionCollisionException.class)
  public void testAssignedToRoleAndToUserPermissionsCollision() {
    WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
    czesio.addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO16"))
  }

  @Test(expected = PermissionCollisionException.class)
  public void testAssignedToUserAndToGroupPermissionsCollision() {
    WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
    czesio.removeFromGroup(wsecGroupDao.getGroupByName("GROUP_LOOSERS"))
    czesio.addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO12"))
    czesio.addToGroup(wsecGroupDao.getGroupByName("GROUP_LOOSERS"))
  }

  @Test(expected = PermissionCollisionException.class)
  public void testAssignedToUserAndToRolePermissionsCollision() {
    WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
    czesio.removeRole(wsecRoleDao.getRoleByName("ROLE_WS_USER"))
    czesio.addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO16"))
    czesio.addRole(wsecRoleDao.getRoleByName("ROLE_WS_USER"))
  }

  @Test(expected = PermissionCollisionException.class)
  public void testAssignedToGroupAndToRolePermissionsCollision() {
    WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
    czesio.removePermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO12"))
    czesio.addToGroup(wsecGroupDao.getGroupByName("GROUP_LOOSERS"))
    WsecRole wsecRole = wsecRoleDao.getRoleByName("ROLE_WS_USER")
    wsecRole.addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO14"))
    czesio.addRole(wsecRole)
  }

  @Test(expected = PermissionCollisionException.class)
  public void testAssignedToGroupsPermissionsCollision() {
    WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
    WsecGroup wsecGroup = wsecGroupDao.getGroupByName("GROUP_USERS")
    wsecGroup.addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO14"))
    czesio.addToGroup(wsecGroup)
  }

  @Test(expected = PermissionCollisionException.class)
  public void testAssignedToRoleAndToGroupPermissionsCollision() {
    WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
    czesio.removeFromGroup(wsecGroupDao.getGroupByName("GROUP_LOOSERS"))
    WsecRole wsecRole = wsecRoleDao.getRoleByName("ROLE_WS_USER")
    wsecRole.addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO14"))
    czesio.addRole(wsecRole)
    czesio.addToGroup(wsecGroupDao.getGroupByName("GROUP_LOOSERS"))
  }

  @Test(expected = PermissionCollisionException.class)
  public void testAssignedToRolesPermissionsCollision() {
    WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
    WsecRole wsecRole = wsecRoleDao.getRoleByName("ROLE_FE_USER")
    wsecRole.addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO16"))
    czesio.addRole(wsecRole)
  }

  @Before
  void setUp() {
    FixtureUtils.prepareUserTestFixture wsecUserDao, wsecGroupDao, wsecRoleDao, wsecPermissionDao
    FixtureUtils.prepareFixtureForPermissionCollisionTesting wsecUserDao, wsecRoleDao, wsecGroupDao, wsecPermissionDao
  }
}