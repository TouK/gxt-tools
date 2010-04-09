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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory
import pl.touk.wonderfulsecurity.beans.WsecUser
import pl.touk.wonderfulsecurity.beans.WsecUser
import pl.touk.wonderfulsecurity.dao.WsecPermissionDao

/**
 * @author Łukasz Kucharski - lkc@touk.pl
 */
@RunWith(SpringJUnit4ClassRunner.class)
// specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations=["classpath:ws-library-sessionFactory-context.xml","classpath:ws-library-dao-context.xml","classpath:datasource-context.xml"])
@TransactionConfiguration(transactionManager="wsHibernateTxManager", defaultRollback=true)
@Transactional
public final class UserHierarchyTest {

    private static final Log log = LogFactory.getLog(UserHierarchyTest.class)

    @Resource
    WsecUserDao wsecUserDao;

    @Resource
    WsecGroupDao wsecGroupDao

    @Resource
    WsecRoleDao wsecRoleDao

    @Resource
    WsecPermissionDao wsecPermissionDao

    @Test
    public void testAddingSupervisorToUser(){
        FixtureUtils.prepareFixtureForUserHierarchyTesting(wsecUserDao)

        log.info "Pobierz czesia"
        WsecUser czesio = wsecUserDao.getUserByLogin("czesio")


        WsecUser rysio = czesio.getSupervisor()
        assertNotNull rysio
        assertTrue rysio.login == "rysio"

        // przetestuj czy wiesia i iza podlagaja czesiowi
        log.info "Sprawdzam czy czesio ma podwladnych"
        assertFalse czesio.getSubordinates().isEmpty()
        assertEquals czesio.subordinates.size(), 2

        WsecUser wiesia = wsecUserDao.getUserByLogin("wiesia")
        WsecUser iza = wsecUserDao.getUserByLogin("iza")

        assertTrue czesio.subordinates.contains(wiesia)
        assertTrue czesio.subordinates.contains(iza)

    }


    @Before
    void setUp(){
        FixtureUtils.prepareUserTestFixture wsecUserDao, wsecGroupDao, wsecRoleDao, wsecPermissionDao
    }

}