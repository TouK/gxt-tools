/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.top.dictionary.impl.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import static org.junit.Assert.*;
import org.junit.Test;
import pl.touk.top.dictionary.model.domain.DictionaryEntry;
import pl.touk.top.dictionary.model.provider.DictionaryProvider;

/**
 *
 * @author Michał Zalewski
 */
public class DictionaryProviderServiceImplUnitTest {

    private DictionaryProviderServiceImpl dictionaryService;

    @Test
    public void testFetchAllEntryObjectsCategorized(){

        dictionaryService = new DictionaryProviderServiceImpl();

        Mockery mockery = new Mockery() {{
            setImposteriser(ClassImposteriser.INSTANCE);
        }};

        String category = "testCategory";
        int entriesCount = 5;
        final DictionaryProvider dictionaryProvider = mockery.mock(DictionaryProvider.class);
        dictionaryService.getProviders().put(category, dictionaryProvider);

        final Collection<DictionaryEntry> dictionary = new HashSet<DictionaryEntry>();
        for(int i = 1; i <= 5; i++){
            dictionary.add(new DictionaryEntry(category, "key" + i, "value" + i, "comment" + i, true));
        }

        mockery.checking(new Expectations() {
            {
                one(dictionaryProvider).getDictionary();
                will(returnValue(dictionary));
            }
        });

        Map<String, Map<String, DictionaryEntry>> fetchedDictionary = dictionaryService.fetchAllEntryObjectsCategorized(true);

        assertEquals(1, fetchedDictionary.size());
        assertEquals(entriesCount, fetchedDictionary.get(category).size());

        mockery.assertIsSatisfied();
    }
}