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
import pl.touk.wonderfulsecurity.beans.PagedQueryResult
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
public final class SortingAndPagingTest {

    @Resource
    WsecUserDao wsecUserDao;

    @Resource
    WsecGroupDao wsecGroupDao

    @Resource
    WsecRoleDao wsecRoleDao

    @Resource
    WsecPermissionDao wsecPermissionDao

    @Test(expected = IllegalArgumentException.class)
    public void testOneOfLimitOffsetNull(){
         wsecUserDao.fetchPagedList(null,null,0,null,null,WsecUser.class)
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOneOfLimitOffsetNull1(){
         wsecUserDao.fetchPagedList(null,0,null,null,null,WsecUser.class)
    }

    @Test
    public void testFetchOneElementPageFirstUser(){
        def list =  wsecUserDao.fetchPagedList(null,0,1,null,null,WsecUser.class)
        assertFalse list.isEmpty()
        assertEquals 1, list.size()
        assertTrue list[0].login == "czesio"
    }

    @Test
    public void testFetchOneElementPagePagedQueryResult(){
        PagedQueryResult pagedResult =  wsecUserDao.fetchPagedListWithOverallCount(null,0,1,null,null,WsecUser.class)
        assertFalse pagedResult.getResult().isEmpty()
        assertEquals FixtureUtils.USERS.size(), pagedResult.getOverallCount()
        
    }
    
    @Test
    public void testFetch0ElementPagePagedQueryResult(){
        PagedQueryResult pagedResult =  wsecUserDao.fetchPagedListWithOverallCount(["login":"NONEXISTING"],0,10,null,null,WsecUser.class)
        assertTrue pagedResult.getResult().isEmpty()
        assertEquals 0, pagedResult.getOverallCount()

    }


    @Test
    public void testFetchOneElementPageSecondUser(){
        def list =  wsecUserDao.fetchPagedList(null,1,1,null,null,WsecUser.class)
        assertFalse list.isEmpty()
        assertEquals 1, list.size()
        assertTrue list[0].login == "rysio"
    }

     @Test
    public void testDoNotPageWhenLimitAndOffsetNull(){
        def list =  wsecUserDao.fetchPagedList(null,null,null,null,null,WsecUser.class)
        assertFalse list.isEmpty()
        assertEquals FixtureUtils.USERS.size(), list.size()
        
    }

    @Test
    public void testFetchPageAndFilter(){
        def list =  wsecUserDao.fetchPagedList(["login":"czesio"],0,10,null,null,WsecUser.class)
        assertFalse list.isEmpty()
        assertEquals 1, list.size()
        assertTrue list[0].login == "czesio"
    }

//    @Test
//    public void testFetchPageAndFilterByGroupName(){
//        def list =  wsecUserDao.fetchPagedList(["groups.name":"GROUP_USERS"],0,10,null,null,WsecUser.class)
//        assertFalse list.isEmpty()
//        assertEquals 1, list.size()
//        assertTrue list[0].login == "czesio"
//    }

  @Test
    public void testDoNotSortWhenSortColumnNull(){
        def list =  wsecUserDao.fetchPagedList(null,0,10,null,null,WsecUser.class)
        assertFalse list.isEmpty()
        assertTrue list[0].login == "czesio"
        assertTrue list[1].login == "rysio"
        assertTrue list[2].login == "wiesia"

    }


  @Test
    public void testDoNotSortWhenSortColumnEmpty(){
        def list =  wsecUserDao.fetchPagedList(null,0,10,"   ",null,WsecUser.class)
        assertFalse list.isEmpty()
        assertTrue list[0].login == "czesio"
        assertTrue list[1].login == "rysio"
        assertTrue list[2].login == "wiesia"

    }

  
    @Test
    public void testSortingByLoginDesc(){
        def list =  wsecUserDao.fetchPagedList(null,null,null,"login",true,WsecUser.class)
        assertFalse list.isEmpty()
        assertTrue list[0].login == "wiesia"
        assertTrue list[1].login == "rysio"
        assertTrue list[2].login == "ola"

    }

    @Test
    public void testFetch2ElementPageCzesioAndRysio(){
        def list =  wsecUserDao.fetchPagedList(null,0,2,null,null,WsecUser.class)
        assertFalse list.isEmpty()
        assertEquals 2, list.size()
        assertTrue list[0].login == "czesio"
        assertTrue list[1].login == "rysio"
    }

    @Test
    public void testFetch0ElementPage(){
        def list =  wsecUserDao.fetchPagedList(null,0,0,null,null,WsecUser.class)
        assertTrue list.isEmpty()
    }


    @Before
    void setUp(){
        FixtureUtils.prepareUserTestFixture wsecUserDao, wsecGroupDao, wsecRoleDao, wsecPermissionDao
        FixtureUtils.prepareFixtureForUserGroupAssociation wsecUserDao, wsecGroupDao
     

    }


}