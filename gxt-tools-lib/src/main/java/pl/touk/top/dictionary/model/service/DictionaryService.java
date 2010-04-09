
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
package pl.touk.top.dictionary.model.service;

import pl.touk.top.dictionary.model.domain.DictionaryEntry;

import java.util.Map;
import java.util.Collection;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public interface DictionaryService {

    /**
     * Fetch whole category of dictionary at once as map
     *
     * @param categoryName category name of dictionary to fetch
     * @return dictionary map or null if category not found
     */
    Map<String, String> fetchCategory(String categoryName);

    /**
     * Fetch all entries from dictionary
     *
     * @param includeLazyLoaded if set to true lazily loadable items will be fetched too
     *
     */
    Collection<DictionaryEntry> fetchAllEntries(boolean includeLazyLoaded);

    /**
     * Fetch all entries to map of maps keyd by categories and then by entry key
     *
     * @param includeLazyLoaded if set to true lazily loadable items will be fetched too
     * @return first map is keyed by categories then each category contains another map keyed by dictionaryElement keys
     */
    Map<String, Map<String, DictionaryEntry>> fetchAllEntryObjectsCategorized(boolean includeLazyLoaded);
}
