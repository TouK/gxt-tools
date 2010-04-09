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
package pl.touk.top.dictionary.impl.service;

import pl.touk.top.dictionary.model.domain.DictionaryEntry;
import pl.touk.top.dictionary.model.dao.DictionaryEntryDao;
import pl.touk.top.dictionary.model.service.DictionaryService;

import java.util.Map;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class DictionaryServiceImpl implements DictionaryService {
// ------------------------------ FIELDS ------------------------------

    protected DictionaryEntryDao dictionaryDao;

// --------------------- GETTER / SETTER METHODS ---------------------

    public void setDictionaryDao(DictionaryEntryDao dictionaryDao) {
        this.dictionaryDao = dictionaryDao;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface DictionaryService ---------------------

    public Map<String, String> fetchCategory(String categoryName) {
        if (categoryName == null || "".equals(categoryName.trim())) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }

        Collection<DictionaryEntry> coll = dictionaryDao.fetchByCategory(categoryName);
        Map<String, String> map = new HashMap<String, String>(coll.size());

        for (DictionaryEntry de : coll) {
            map.put(de.getEntryKey(), de.getValue());
        }

        return map.isEmpty() ? null : map;
    }

    public Collection<DictionaryEntry> fetchAllEntries(boolean includeLazyLoaded) {

        return dictionaryDao.fetchAllEntries(includeLazyLoaded);
    }

    public Map<String, Map<String, DictionaryEntry>> fetchAllEntryObjectsCategorized(boolean includeLazyLoaded) {

        Collection<DictionaryEntry> coll = dictionaryDao.fetchAllEntries(includeLazyLoaded);

        Map<String,Map<String,DictionaryEntry>> finalMap = new HashMap<String, Map<String, DictionaryEntry>>();

        for (DictionaryEntry de : coll) {
            Map<String, DictionaryEntry> subMap = finalMap.get(de.getCategory());
            if (subMap == null) {
                subMap = new HashMap<String, DictionaryEntry>();
                finalMap.put(de.getCategory(), subMap);
            }

            subMap.put(de.getEntryKey(), de);
        }

        return finalMap;
    }
}
