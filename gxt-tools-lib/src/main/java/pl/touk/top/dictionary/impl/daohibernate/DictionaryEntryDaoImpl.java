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
package pl.touk.top.dictionary.impl.daohibernate;

import pl.touk.top.dictionary.model.domain.DictionaryEntry;
import pl.touk.top.dictionary.model.dao.DictionaryEntryDao;
import pl.touk.wonderfulsecurity.dao.WsecBaseDaoImpl;
import pl.touk.wonderfulsecurity.utils.Commons;

import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class DictionaryEntryDaoImpl extends WsecBaseDaoImpl implements DictionaryEntryDao {
// ------------------------------ FIELDS ------------------------------

    private int mode;

// --------------------- GETTER / SETTER METHODS ---------------------
    @Required
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface DictionaryEntryDao ---------------------

    public Collection<DictionaryEntry> fetchAllEntries(boolean includeLazyLoaded) {
        DetachedCriteria crit = DetachedCriteria.forClass(DictionaryEntry.class);

        if (!includeLazyLoaded) {
            crit.add(Restrictions.ne("lazyLoad", true));
        }
        Collection<DictionaryEntry> coll = getHibernateTemplate().findByCriteria(crit);
        return coll;
    }

    public Collection<DictionaryEntry> fetchByCategory(String categoryName) {
        Commons.checkArgumentsNotNull("Nazwa kategori nie moze byc nullem", categoryName);

        return getHibernateTemplate().find("from DictionaryEntry de where de.category = ?", categoryName);
    }

    public DictionaryEntry fetchByKey(String dictionaryEntryKey) {
        Commons.checkArgumentsNotNull("Nazwa klucza nie moze byc nullem", dictionaryEntryKey);

        Collection<DictionaryEntry> coll = getHibernateTemplate().find("from DictionaryEntry de where de.entryKey = ?", dictionaryEntryKey);
        return Commons.nullIfEmptyCollection(coll);
    }
}
