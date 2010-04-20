package pl.touk.top.dictionary.impl.service;

import pl.touk.top.dictionary.model.domain.DictionaryEntry;
import pl.touk.top.dictionary.model.service.DictionaryService;

import java.util.*;


public class AggregatingDictionaryServiceImpl implements DictionaryService{

    private Set<DictionaryService> services = new HashSet();

    public Map<String, String> fetchCategory(String categoryName) {
        Map<String, String> result = new HashMap<String, String>();

        for (DictionaryService service : services) {
            result.putAll(service.fetchCategory(categoryName));
        }

        return result;

    }

    public Collection<DictionaryEntry> fetchAllEntries(boolean includeLazyLoaded) {
        Collection<DictionaryEntry> result = new HashSet<DictionaryEntry>();

        for (DictionaryService service : services) {
            result.addAll(service.fetchAllEntries(includeLazyLoaded));
        }

        return result;
    }

    public Map<String, Map<String, DictionaryEntry>> fetchAllEntryObjectsCategorized(boolean includeLazyLoaded) {
        Map<String, Map<String, DictionaryEntry>> result = new HashMap<String, Map<String, DictionaryEntry>>();

        for (DictionaryService service : services) {
            result.putAll(service.fetchAllEntryObjectsCategorized(includeLazyLoaded));
        }

        return result;
    }


    public void setServices(Set<DictionaryService> services) {
        this.services = services;
    }
}
