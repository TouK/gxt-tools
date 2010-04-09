/*
* Copyright (c) 2009 TouK
* All rights reserved
*/
package pl.touk.wonderfulsecurity.dao;

import pl.touk.top.dictionary.model.provider.DictionaryProvider;
import pl.touk.top.dictionary.model.domain.DictionaryEntry;
import pl.touk.wonderfulsecurity.beans.WsecGroup;

import java.util.Collection;
import java.util.ArrayList;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class GroupsDictionaryProvider implements DictionaryProvider{

    private WsecGroupDao groupDao;

    public Collection<DictionaryEntry> getDictionary() {

        ArrayList<WsecGroup> groups = groupDao.fetchAll(WsecGroup.class);

        ArrayList entries = new ArrayList();

        for (WsecGroup group : groups) {
            DictionaryEntry de = new DictionaryEntry();
            de.setEntryKey(group.getId().toString());
            de.setValue(group.getName());
            entries.add(de);
        }
        return entries;
        
    }

    public void setGroupDao(WsecGroupDao groupDao) {
        this.groupDao = groupDao;
    }
}
