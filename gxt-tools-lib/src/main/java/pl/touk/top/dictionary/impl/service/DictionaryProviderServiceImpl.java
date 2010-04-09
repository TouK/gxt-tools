/*
 * Copyright (c) 2009 TouK.pl
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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import pl.touk.top.dictionary.model.domain.DictionaryEntry;
import pl.touk.top.dictionary.model.provider.DictionaryProvider;
import pl.touk.top.dictionary.model.service.DictionaryService;

/**
 * DictionaryService implementation for use with DictionaryProviders.
 * Contains Map of DictionaryProviders assigned to categories
 * @author Michał Zalewski
 */
public class DictionaryProviderServiceImpl implements DictionaryService {

    private Map<String, DictionaryProvider> providers = new HashMap<String, DictionaryProvider>();

    /**
     * {@inheritDoc }
     */
    public Map<String, String> fetchCategory(String categoryName) {

        if (!providers.containsKey(categoryName)) {
            throw new IllegalArgumentException("No such category: " + categoryName);
        }

        DictionaryProvider provider = providers.get(categoryName);
        Collection<DictionaryEntry> dictionaryEntries = provider.getDictionary();

        Map<String, String> result = new HashMap<String, String>(dictionaryEntries.size());

        for (DictionaryEntry entry : dictionaryEntries) {
            result.put(entry.getEntryKey(), entry.getValue());
        }

        return result;
    }

    /**
     *  {@inheritDoc }
     */
    public Collection<DictionaryEntry> fetchAllEntries(boolean includeLazyLoaded) {

        if (providers.isEmpty()) {
            throw new IllegalStateException("No DictionaryProviders set");
        }

        Collection<DictionaryEntry> result = new HashSet<DictionaryEntry>();

        for (String category : providers.keySet()) {

            DictionaryProvider provider = providers.get(category);

            Collection<DictionaryEntry> dictionary = provider.getDictionary();

            if (result == null) {
                result = dictionary;
            } else {
                result.addAll(dictionary);
            }
        }

        return result;

    }

    /**
     *  {@inheritDoc }
     */
    public Map<String, Map<String, DictionaryEntry>> fetchAllEntryObjectsCategorized(boolean includeLazyLoaded) {

        if (providers.isEmpty()) {
            throw new IllegalStateException("No DictionaryProviders set");
        }

        Map<String, Map<String, DictionaryEntry>> result = new HashMap<String, Map<String, DictionaryEntry>>();

        for (String categoryName : providers.keySet()) {

            DictionaryProvider provider = providers.get(categoryName);
            Collection<DictionaryEntry> dictionaryEntriesCollection = provider.getDictionary();

            Map<String, DictionaryEntry> dictionaryEntriesMap = result.get(categoryName);
            if (dictionaryEntriesMap == null) {
                dictionaryEntriesMap = new HashMap<String, DictionaryEntry>();
                result.put(categoryName, dictionaryEntriesMap);
            }

            for (DictionaryEntry dictionaryEntry : dictionaryEntriesCollection) {
                dictionaryEntriesMap.put(dictionaryEntry.getEntryKey(), dictionaryEntry);
            }
        }

        return result;
    }

    public void setProviders(Map<String, DictionaryProvider> providers) {
        this.providers = providers;
    }

    public Map<String, DictionaryProvider> getProviders() {
        return providers;
    }
}
