/*
 * Copyright (c) 2008 TouK.pl
 *
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
package pl.touk.top.dictionary.webapp.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pl.touk.top.dictionary.model.dao.DictionaryEntryDao;
import pl.touk.top.dictionary.test.DictionaryFixtureUtils;

import javax.annotation.Resource;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class PrepareStartupData {
// ------------------------------ FIELDS ------------------------------

    private static transient Log log = LogFactory.getLog(PrepareStartupData.class);


    private static volatile boolean initialized = false;

    private TransactionTemplate hibernateTransactionTemplate;

    private DictionaryEntryDao dictionaryDao;


// --------------------- GETTER / SETTER METHODS ---------------------


    public void setDictionaryDao(DictionaryEntryDao dictionaryDao) {
        this.dictionaryDao = dictionaryDao;
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

            log.info("PREPARE STARTUP DATA PROCESS WORKS IN DEMO MODE, INSERTING ROWS");
            hibernateTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    DictionaryFixtureUtils.prepareCasesFixture(dictionaryDao);

                }
            });

        }
    }

}
