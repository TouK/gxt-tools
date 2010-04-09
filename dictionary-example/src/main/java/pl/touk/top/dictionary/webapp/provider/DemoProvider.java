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
package pl.touk.top.dictionary.webapp.provider;

import java.util.Collection;
import pl.touk.top.dictionary.model.dao.DictionaryEntryDao;
import pl.touk.top.dictionary.model.domain.DictionaryEntry;
import pl.touk.top.dictionary.model.provider.DictionaryProvider;

/**
 * DictionaryProvider implementation example.
 * @author Michał Zalewski
 */
public class DemoProvider implements DictionaryProvider {

    private DictionaryEntryDao dictionaryEntryDao;

    /**
     * Returns collection of DictionaryEntries, which
     * is used to fill ComboBox, ComboBoxWithClear or DictionaryBasedRemoteFilter.
     * Each provider should return dictionaryEntries from only one category.
     * @return
     */
    public Collection<DictionaryEntry> getDictionary() {
        return dictionaryEntryDao.fetchByCategory("ERROR_CATEGORY");
    }

    public void setDictionaryEntryDao(DictionaryEntryDao dictionaryEntryDao) {
        this.dictionaryEntryDao = dictionaryEntryDao;
    }

    public DictionaryEntryDao getDictionaryEntryDao() {
        return dictionaryEntryDao;
    }
}
