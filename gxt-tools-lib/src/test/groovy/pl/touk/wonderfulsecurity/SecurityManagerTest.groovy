/*
 * Copyright (c) 2009 TouK
 * All rights reserved
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
import pl.touk.wonderfulsecurity.service.ISecurityManager

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
@RunWith(SpringJUnit4ClassRunner.class)
// specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations=["classpath:ws-library-sessionFactory-context.xml","classpath:ws-library-dao-context.xml","classpath:datasource-context.xml", "classpath:ws-library-service-context.xml"])
@TransactionConfiguration(transactionManager="wsHibernateTxManager", defaultRollback=true)
@Transactional
public final class SecurityManagerTest {

	private static final Log log = LogFactory.getLog(SecurityManagerTest.class)

	@Resource
    ISecurityManager securityManager;

	@Resource
    WsecUserDao wsecUserDao;

    @Resource
    WsecGroupDao wsecGroupDao

    @Resource
    WsecRoleDao wsecRoleDao

    @Resource
    WsecPermissionDao wsecPermissionDao

	@Test
	public void testGetPermissionsAssignedToRole(){
		assertNotNull(securityManager.getPermissionsAssignedToRole(null, "ROLE_WS_USER"))
	}

	@Before
    void setUp(){
        FixtureUtils.prepareUserTestFixture wsecUserDao, wsecGroupDao, wsecRoleDao, wsecPermissionDao
		FixtureUtils.prepareFixtureForPermissionBidirectionalRelationsTest wsecUserDao, wsecRoleDao, wsecGroupDao, wsecPermissionDao
    }

    @Test
    void testDeletePermission() {
        WsecPermission perm1 = wsecPermissionDao.getPermissionByName("PERMISSION_NO1")
        WsecGroup group = wsecGroupDao.getGroupByName("GROUP_USERS");
        perm1.getReceivingGroups().add(group);
        WsecUser user = wsecUserDao.getUserByLogin("czesio");
        user.addPermission(perm1);
        securityManager.deletePermission(null, perm1)
        assertEquals(0, perm1.getReceivingGroups().size())
        assertEquals(0, perm1.getReceivingUsers().size());
    }
}
