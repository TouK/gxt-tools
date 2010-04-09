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
package pl.touk.top.dictionary.test;

import pl.touk.top.dictionary.model.domain.DictionaryEntry;
import pl.touk.top.dictionary.model.service.DictionaryService;
import pl.touk.top.dictionary.test.DictionaryFixtureUtils;

import java.util.Collection;
import java.util.Map;

/**
 * -Dproperties.file=file:///home/lukasz/projects/mnp/console/trunk/mnpc/mnpc.properties-unitTests
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class DictionaryTest extends BaseTest {

    

    protected DictionaryService dictionaryService;

    public void testDaoFetchAllEntries() {

        Collection<DictionaryEntry> entries = dictionaryDao.fetchAllEntries(true);
        assertNotNull(entries);
        assertTrue(entries.size() == DictionaryFixtureUtils.ENTRIES.size());

        entries = dictionaryDao.fetchAllEntries(false);
        assertNotNull(entries);
        assertTrue(entries.size() == DictionaryFixtureUtils.EAGER_ENTRIES.size());

    }


    public void testDaoFetchByCategory() {

        Collection coll = dictionaryDao.fetchByCategory("Nonexisting category");
        assertNotNull(coll);
        assertTrue(coll.isEmpty());
        coll = dictionaryDao.fetchByCategory("TYPE");
        assertNotNull(coll);
        assertTrue(coll.size() == DictionaryFixtureUtils.TYPE_ENTRIES.size());


    }

    public void testDaoFetchByKey() {

        Object obj = dictionaryDao.fetchByKey("NONEXISTING KEY");
        assertNull(obj);
        obj = dictionaryDao.fetchByKey("BRAND_1");
        assertNotNull(obj);

    }


    public void testDictionaryServiceFetchAll() {

        assertTrue(dictionaryService.fetchAllEntries(true).size() == DictionaryFixtureUtils.ENTRIES.size());
        assertTrue(dictionaryService.fetchAllEntries(false).size() == DictionaryFixtureUtils.EAGER_ENTRIES.size());
    }

    public void testDictionaryServiceFetchCategory() {

        Map<String, String> category = dictionaryService.fetchCategory("NONEXISTING");
        assertNull(category);

        category = dictionaryService.fetchCategory("TYPE");

        assertNotNull(category);
        assertFalse(category.isEmpty());

        assertTrue(category.size() == DictionaryFixtureUtils.TYPE_ENTRIES.size());
    }

    public void testDictionaryServiceFetchAllEntryObjectsCategorized() {

        Map<String, Map<String, DictionaryEntry>> result = dictionaryService.fetchAllEntryObjectsCategorized(false);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        Map<String, DictionaryEntry> events = result.get("TYPE");

        assertNotNull(events);
        assertFalse(events.isEmpty());


        DictionaryEntry event = events.get("1");
        assertNotNull(event);

    }


}
