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

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.touk.wonderfulsecurity.dao.WsecUserDaoImpl;
import pl.touk.wonderfulsecurity.beans.WsecUser;

import javax.annotation.Resource
import pl.touk.wonderfulsecurity.dao.WsecUserDao
import org.junit.Before
import org.junit.BeforeClass
import org.junit.After
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import pl.touk.wonderfulsecurity.dao.WsecGroupDao;
import pl.touk.wonderfulsecurity.beans.WsecGroup
import pl.touk.wonderfulsecurity.beans.WsecGroup
import pl.touk.wonderfulsecurity.dao.WsecGroupDao
import pl.touk.wonderfulsecurity.dao.WsecRoleDao
import pl.touk.wonderfulsecurity.beans.WsecRole
import pl.touk.wonderfulsecurity.beans.WsecUser
import pl.touk.wonderfulsecurity.beans.WsecUser

import org.apache.commons.logging.LogFactory
import org.apache.commons.logging.Log
import pl.touk.wonderfulsecurity.dao.WsecPermissionDao
import pl.touk.wonderfulsecurity.beans.WsecPermission

/**
 * @author Łukasz Kucharski - lkc@touk.pl
 */
@RunWith(SpringJUnit4ClassRunner.class)
// specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations=["classpath:ws-library-sessionFactory-context.xml","classpath:ws-library-dao-context.xml","classpath:datasource-context.xml"])
@TransactionConfiguration(transactionManager="wsHibernateTxManager", defaultRollback=true)
@Transactional
public final class CrudDaoTest {

    private static final Log log = LogFactory.getLog(CrudDaoTest.class)

    @Resource
    WsecUserDao wsecUserDao;

    @Resource
    WsecGroupDao wsecGroupDao

    @Resource
    WsecRoleDao wsecRoleDao

    @Resource
    WsecPermissionDao wsecPermissionDao

    // sanity test check
    @Test
    public void testReadUsers(){
        assertNotNull(wsecUserDao);
        def count = FixtureUtils.USERS.size();
        assertTrue("Powinno być  $count userow", wsecUserDao.fetchAll(WsecUser.class).size() == count)
    }

    @Test
    public void testReadGroups(){
        assertNotNull(wsecGroupDao);
        def count = FixtureUtils.GROUPS.size();
        assertTrue("Powinno być  $count rol", wsecGroupDao.fetchAll(WsecGroup.class).size() == count)
    }

    @Test
    public void testReadRoles(){
        assertNotNull(wsecRoleDao);
        def count = FixtureUtils.ROLES.size();
        assertTrue("Powinno być  $count rol", wsecRoleDao.fetchAll(WsecRole.class).size() == count)
    }

    @Test
    public void testReadPermissions(){
        assertNotNull(wsecPermissionDao);
        def count = FixtureUtils.PERMISSIONS.size();
        assertTrue("Powinno być  $count uprawnień", wsecPermissionDao.fetchAll(WsecPermission.class).size() == count)
    }

    @Test
    public void testDeleteAllUsers(){
        wsecUserDao.deleteAll FixtureUtils.USERS;
        assertTrue("Tabela uzytkownikow powinna byc pusta", wsecUserDao.fetchAll(WsecUser.class).size() == 0)
    }

    @Test
    public void testDeleteAllGroups(){
        wsecGroupDao.deleteAll FixtureUtils.GROUPS;
        assertTrue("Tabela grup powinna byc pusta", wsecGroupDao.fetchAll(WsecGroup.class).size() == 0)
    }

    @Test
    public void testDeleteAllRoles(){
        wsecGroupDao.deleteAll FixtureUtils.ROLES;
        assertTrue("Tabela ról powinna byc pusta", wsecRoleDao.fetchAll(WsecRole.class).size() == 0)
    }

    @Test
    public void testDeleteAllPermissions(){
      wsecPermissionDao.deleteAll FixtureUtils.PERMISSIONS;
      assertTrue("Tabela uprawnień powinna byc pusta", wsecPermissionDao.fetchAll(WsecPermission.class).size() == 0) 
    }


    @Test
    public void testGetUserByLoginName(){
        assertNotNull(wsecUserDao.getUserByLogin("czesio"))
        assertNull(wsecUserDao.getUserByLogin("doesNotExist"))
    }

    @Test
    public void testGetGroupByName(){
        assertNotNull(wsecGroupDao.getGroupByName("GROUP_USERS"))
        assertNull(wsecGroupDao.getGroupByName("ROLEDOESNOTEXIST"))

    }

    @Test
    public void testGetRoleByName(){
        assertNotNull(wsecRoleDao.getRoleByName("ROLE_WS_USER"))
        assertNull(wsecRoleDao.getRoleByName("ROLEDOESNOTEXIST"))

    }

    @Test
    public void testGetPermissionByName(){
        assertNotNull(wsecPermissionDao.getPermissionByName("PERMISSION_NO1"));
        assertNull(wsecPermissionDao.getPermissionByName("PERM_DOES_NOT_EXIST"))
    }

    @Test
    public void testCzesioMaGrupeUser(){
        FixtureUtils.prepareFixtureForUserGroupAssociation wsecUserDao, wsecGroupDao
        WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
        assertNotNull czesio
        assertFalse czesio.getGroups().isEmpty()

        WsecGroup grupaCzesia = czesio.getGroups().iterator().next()
        assertNotNull "grupa czesia jest nullem", grupaCzesia
        assertTrue grupaCzesia.name == "GROUP_USERS"

    }

    @Test
    public void testGrupaUserMaRoleWsUser(){
         FixtureUtils.prepareFixtureForGroupRoleAssociation(wsecGroupDao,wsecRoleDao)
         WsecGroup userGroup = wsecGroupDao.getGroupByName("GROUP_USERS")
        assertFalse(userGroup.getRoles().isEmpty())
        assertEquals("ROLE_WS_USER", userGroup.getRoles().iterator().next().name)
    }


    /**
     Przetestuj dziedziczenie roli z grupy oraz role dodane bezpośrednio do użytkownika

     Grupa GROUP_USERS posiada role ROLE_WS_USER dodatkowo przypisuje role ROLE_FE_USER bezpośrednio do
     użytkownika (nie przez grupę) Test sprawdza czy suma ról użytkownika == 2
     */
    @Test
    public void testujSumowanieRol(){
         FixtureUtils.prepareFixtureForGroupRoleAssociation(wsecGroupDao,wsecRoleDao)

        log.info "Przygotowalem role i grupe"
         WsecGroup userGroup = wsecGroupDao.getGroupByName("GROUP_USERS")
        WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
        log.info "Pobralem czesia"
        WsecRole roleFeUser = wsecRoleDao.getRoleByName("ROLE_FE_USER")
        log.info "Pobralem role FE_USER"

        assertNotNull czesio
        assertNotNull roleFeUser

        log.info "Dodaje grupe USERS do czesia"
        czesio.addToGroup(userGroup)
        
        log.info "Dodaje role fe_user do czesia"
        czesio.addRole(roleFeUser)
        // refetch from db or cache
        log.info "Pobieram czesia ponownie"
        czesio = wsecUserDao.getUserByLogin("czesio")

        assertFalse(userGroup.getRoles().isEmpty())
        assertEquals(2, czesio.getAllRoles().size())
        
        
//        assertEquals("ROLE_WS_USER", userGroup.getRoles().iterator().next().name)
    }

    @Test
    public void testGettingAuthoritiesFromUser(){
        FixtureUtils.prepareFixtureForUserGroupAssociation wsecUserDao, wsecGroupDao
        FixtureUtils.prepareFixtureForGroupRoleAssociation wsecGroupDao, wsecRoleDao

        WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
        assertNotNull czesio
        assertFalse czesio.getGroups().isEmpty()

        WsecGroup grupaCzesia = czesio.getGroups().iterator().next()
        assertNotNull "grupa czesia jest nullem", grupaCzesia
        assertTrue grupaCzesia.name == "GROUP_USERS"

        assertFalse grupaCzesia.getRoles().isEmpty()
        assertEquals "ROLE_WS_USER", grupaCzesia.getRoles().iterator().next().name

        assertFalse czesio.getAllRoles().isEmpty()
        assertEquals "ROLE_WS_USER", czesio.getAllRoles().iterator().next().name

    }

    @Test
    public void testGetUsersFromGroup(){
      FixtureUtils.prepareFixtureForUserGroupAssociation wsecUserDao, wsecGroupDao

      List<WsecUser> users = wsecGroupDao.getUsers("GROUP_USERS");

      assertEquals 1, users.size();

      WsecGroup group = wsecGroupDao.getGroupByName("GROUP_USERS");

      Set<WsecUser> usersSet = group.getUsers();

      assertEquals 1, usersSet.size();
  }

   
    @Before
    void setUp(){
        FixtureUtils.prepareUserTestFixture wsecUserDao, wsecGroupDao, wsecRoleDao, wsecPermissionDao
    }


}