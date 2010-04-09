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
package pl.touk.top.dictionary.model.dao;

import pl.touk.top.dictionary.model.domain.DictionaryEntry;
import pl.touk.wonderfulsecurity.dao.WsecBaseDao;

import java.util.Collection;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public interface DictionaryEntryDao extends WsecBaseDao {
// -------------------------- OTHER METHODS --------------------------

    /**
     * Fetch all data available in Dictionary
     *
     * @param includeLazyLoaded set this parameter to true when you also need lazily loaded entries in the result
     *                 is you set this parameters to false then DictionaryEntry object whith property
     *                 {@link pl.touk.top.dictionary.model.domain.DictionaryEntry#lazyLoad} to true will not be loaded
     * @return collection of DictionatyEntry objects or empty collection if none found
     */
    Collection<DictionaryEntry> fetchAllEntries(boolean includeLazyLoaded);

    /**
     * Fetch dictionary entries by category name
     *
     * @param categoryName category to fetch
     * @return Collection of DictionaryEntry objects or empty collection if category not found
     */
    Collection<DictionaryEntry> fetchByCategory(String categoryName);

    /**
     * Fetch dictionary object by its entryKey
     *
     * @param dictionaryEntryKey entryKey to find
     * @return pl.touk.mnp.model.domain.DictionaryEntry object or null if not found
     */
    DictionaryEntry fetchByKey(String dictionaryEntryKey);
}
