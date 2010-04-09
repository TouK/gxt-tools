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
import pl.touk.wonderfulsecurity.dao.WsecRoleDao
import pl.touk.wonderfulsecurity.dao.WsecPermissionDao
import pl.touk.wonderfulsecurity.beans.WsecUser
import pl.touk.wonderfulsecurity.beans.WsecRole
import pl.touk.wonderfulsecurity.beans.WsecGroup
//import pl.touk.wonderfulsecurity.springsecurity.DefaultUserDetailsService;
//import org.springframework.security.userdetails.UsernameNotFoundException;

import org.apache.commons.logging.LogFactory
import org.apache.commons.logging.Log

import pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException
import pl.touk.wonderfulsecurity.beans.WsecPermission

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
@RunWith(SpringJUnit4ClassRunner.class)
// specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations=["classpath:ws-library-sessionFactory-context.xml","classpath:ws-library-dao-context.xml","classpath:datasource-context.xml"])
@TransactionConfiguration(transactionManager="wsHibernateTxManager", defaultRollback=true)
@Transactional
public class PermissionBidirectionalRelationsTest {

	private static final Log log = LogFactory.getLog(PermissionBidirectionalRelationsTest.class)

	@Resource
    WsecUserDao wsecUserDao;

    @Resource
    WsecGroupDao wsecGroupDao

    @Resource
    WsecRoleDao wsecRoleDao

    @Resource
    WsecPermissionDao wsecPermissionDao

	@Test
	public void testCount(){
		WsecPermission perm19 = wsecPermissionDao.getPermissionByName("PERMISSION_NO19")
		WsecPermission perm1 = wsecPermissionDao.getPermissionByName("PERMISSION_NO1")
		assertEquals(1, perm1.getReceivingUsers().size())
		assertEquals(1, perm1.getReceivingGroups().size())
		assertEquals(1, perm1.getReceivingRoles().size())
		assertEquals(2, perm19.getReceivingRoles().size())
	}

	@Test
	public void testRolePermissionRemoval(){
		WsecPermission perm19 = wsecPermissionDao.getPermissionByName("PERMISSION_NO19")
		Set<WsecRole> roles = perm19.getReceivingRoles()
		Iterator it = roles.iterator()
		WsecRole role = it.next()
		role.removePermission(perm19)
		assertEquals(1, perm19.getReceivingRoles().size())
		assertFalse(role.hasPermission(perm19))
	}

	@Test
	public void testGroupPermissionRemoval(){
		WsecPermission perm1 = wsecPermissionDao.getPermissionByName("PERMISSION_NO1")
		Set<WsecGroup> groups = perm1.getReceivingGroups()
		Iterator it = groups.iterator()
		WsecGroup group = it.next()
		group.removePermission(perm1)
		assertEquals(0, perm1.getReceivingGroups().size())
		assertFalse(group.hasPermission(perm1))
	}

	@Test
	public void testUserPermissionRemoval(){
		WsecPermission perm1 = wsecPermissionDao.getPermissionByName("PERMISSION_NO1")
		Set<WsecUser> users = perm1.getReceivingUsers()
		Iterator it = users.iterator()
		WsecUser user = it.next()
		user.removePermission(perm1)
		assertEquals(0, perm1.getReceivingUsers().size())
		assertFalse(user.hasPermission(perm1))
	}

	@Before
    void setUp(){
		FixtureUtils.prepareUserTestFixture wsecUserDao, wsecGroupDao, wsecRoleDao, wsecPermissionDao
		FixtureUtils.prepareFixtureForPermissionBidirectionalRelationsTest wsecUserDao, wsecRoleDao, wsecGroupDao, wsecPermissionDao
    }

}
