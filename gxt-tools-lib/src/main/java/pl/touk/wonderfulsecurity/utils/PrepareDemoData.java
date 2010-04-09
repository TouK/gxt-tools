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
package pl.touk.wonderfulsecurity.utils;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.touk.wonderfulsecurity.FixtureUtils;
import pl.touk.wonderfulsecurity.dao.WsecGroupDao;
import pl.touk.wonderfulsecurity.dao.WsecRoleDao;
import pl.touk.wonderfulsecurity.dao.WsecUserDao;
import pl.touk.wonderfulsecurity.dao.WsecPermissionDao;
import javax.annotation.Resource;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class PrepareDemoData {
// ------------------------------ FIELDS ------------------------------

    private static final transient Log log = LogFactory.getLog(PrepareDemoData.class);

    // do not insert startup  data to DB
    private static final int MODE_PRODUCTION = 0;

    // insert startup data to DB
    private static final int MODE_DEMO = 1;

    private static volatile boolean initialized = false;

    private int mode;

    private WsecUserDao wsecUserDao;

    private WsecRoleDao wsecRoleDao;

    private WsecGroupDao wsecGroupDao;

    private WsecPermissionDao wsecPermissionDao;

    private TransactionTemplate hibernateTransactionTemplate;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Required
    public void setMode(int mode) {
        this.mode = mode;
    }

    public WsecGroupDao getWsecGroupDao() {
        return wsecGroupDao;
    }

    @Resource
    @Required
    public void setWsecGroupDao(WsecGroupDao wsecGroupDao) {
        this.wsecGroupDao = wsecGroupDao;
    }

    public WsecRoleDao getWsecRoleDao() {
        return wsecRoleDao;
    }

    @Resource
    @Required
    public void setWsecRoleDao(WsecRoleDao wsecRoleDao) {
        this.wsecRoleDao = wsecRoleDao;
    }

    public WsecUserDao getWsecUserDao() {
        return wsecUserDao;
    }

    @Resource
    @Required
    public void setWsecUserDao(WsecUserDao wsecUserDao) {
        this.wsecUserDao = wsecUserDao;
    }

    public WsecPermissionDao getWsecPermissionDao() {
        return wsecPermissionDao;
    }

    @Resource
    @Required
    public void setWsecPermissionDao(WsecPermissionDao wsecPermissionDao) {
        this.wsecPermissionDao = wsecPermissionDao;
    }

    @Resource
    @Required
    public void setHibernateTransactionTemplate(TransactionTemplate hibernateTransactionTemplate) {
        this.hibernateTransactionTemplate = hibernateTransactionTemplate;
    }

// -------------------------- OTHER METHODS --------------------------

    public void initialize() {
        if (!initialized) {
            initialized = true;
            if (mode == MODE_DEMO) {
                log.info("PREPARE DEMO DATA WORKS IN MODE_DEMO");
                hibernateTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        FixtureUtils.prepareUserTestFixture(wsecUserDao, wsecGroupDao, wsecRoleDao, wsecPermissionDao);
                        FixtureUtils.prepareFixtureForUserGroupAssociation(wsecUserDao, wsecGroupDao);
                        FixtureUtils.prepareFixtureForGroupRoleAssociation(wsecGroupDao, wsecRoleDao);
                        FixtureUtils.prepareFixtureForPermissionCollisionTesting(wsecUserDao, wsecRoleDao, wsecGroupDao, wsecPermissionDao);
                    }
                });
            }
            if (mode == MODE_PRODUCTION) {
                log.info("PREPARE DEMO DATA WORKS IN PRODUCTION MODE, NOT INSERTING STARTUP DATA");
            }
        }
    }
}
