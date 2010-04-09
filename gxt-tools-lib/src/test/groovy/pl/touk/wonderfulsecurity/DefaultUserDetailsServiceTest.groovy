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
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import pl.touk.wonderfulsecurity.dao.WsecGroupDao
import pl.touk.wonderfulsecurity.dao.WsecPermissionDao
import pl.touk.wonderfulsecurity.dao.WsecRoleDao
import pl.touk.wonderfulsecurity.dao.WsecUserDao
import pl.touk.wonderfulsecurity.springsecurity.DefaultUserDetailsService
import pl.touk.wonderfulsecurity.springsecurity.WsecUserDetails
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */

@RunWith(SpringJUnit4ClassRunner.class)
// specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations = ["classpath:ws-library-sessionFactory-context.xml", "classpath:ws-library-dao-context.xml", "classpath:datasource-context.xml"])
@TransactionConfiguration(transactionManager = "wsHibernateTxManager", defaultRollback = true)
@Transactional
public class DefaultUserDetailsServiceTest {

  private static final Log log = LogFactory.getLog(DefaultUserDetailsServiceTest.class)

  @Resource
  WsecUserDao wsecUserDao;

  @Resource
  WsecGroupDao wsecGroupDao

  @Resource
  WsecRoleDao wsecRoleDao

  @Resource
  WsecPermissionDao wsecPermissionDao

  protected DefaultUserDetailsService defaultUserDetailsService

  @Test(expected = UsernameNotFoundException.class)
  public void testUsernameNotFound() {
    defaultUserDetailsService.loadUserByUsername("exceptionUser")
  }

  @Test
  public void testLoadUserByUsername() {
    WsecUserDetails user = defaultUserDetailsService.loadUserByUsername("czesio")
    assertNotNull user
    assertEquals user.getWsecUser().getLastName(), "Czesiarski"
  }

  @Before
  void setUp() {
    defaultUserDetailsService = new DefaultUserDetailsService()
    defaultUserDetailsService.setWsecUserDao(wsecUserDao)
    FixtureUtils.prepareUserTestFixture wsecUserDao, wsecGroupDao, wsecRoleDao, wsecPermissionDao
  }


}
