package pl.touk.top.dictionary.impl.gwt.client.widgets.withMemory;

/*
 * Copyright (c) 2010 TouK.pl
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

import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.google.gwt.core.client.GWT;
import java.util.HashSet;
import pl.touk.tola.gwt.client.state.TolaStateManager;
import pl.touk.top.dictionary.impl.gwt.client.widgets.DictionaryBasedRemoteFilter;

/**
 * Combo with coockie based memmory with use with dictionary filters
 *
 * 
 * @author Rafał Pietrasik
 */
public class RemoteComboDictionaryFilterWithMemory extends DictionaryBasedRemoteFilter {

    private static final HashSet<String> names = new HashSet<String>();
    private static final String COMBO_MEMORY_SUFFIX = "dicSuff";
    private String uniqueName;

    /**
     * Creates checkbox with cookie - based memory.
     * @param uniqueName - checkbox name.
     * @param emptyText - field label for this checkbox.
     */
    public RemoteComboDictionaryFilterWithMemory(String dictionaryCategory, PagingLoader loader, PagingToolBar pagingToolbar, String filterProperty, DictionaryBasedRemoteFilter.TargetFieldType type, final String uniqueName) {
        super(loader, pagingToolbar, filterProperty, type);
        if (uniqueName == null) {
            throw new IllegalArgumentException("Nazwa RemotComboDictionaryFilterWithMemory nie może być nullem!");
        }

        if (names.contains(uniqueName)) {
            throw new IllegalArgumentException("To nie jest unikalna nazwa" + uniqueName);
        }

        this.uniqueName = uniqueName;
        this.setEmptyText(emptyText);


        this.addListener(Events.Select, new Listener<FieldEvent>() {

            
            public void handleEvent(FieldEvent be) {
                if (be.getType() == Events.Select) {
                    int index = getStore().indexOf(getValue());
                    TolaStateManager.get().set(uniqueName + COMBO_MEMORY_SUFFIX, index);
                    GWT.log("ustawiles filtr"+uniqueName+" na:"+index);
                }
            }
        });
    }

    public void clearSelections() {
        super.clearSelections();
        //reset wartości filtra
        TolaStateManager.get().set(uniqueName + COMBO_MEMORY_SUFFIX, -1);
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setStore(ListStore dListStore) {
        super.setStore(dListStore);
        Object obj = TolaStateManager.get().get(uniqueName + COMBO_MEMORY_SUFFIX);
        if (obj != null) {

            int selectedIndex = (Integer) obj;
            this.setEmptyText(""+selectedIndex);
            if (selectedIndex != -1) {
                if (selectedIndex < dListStore.getCount()) {
                    this.disableEvents(true);
                    this.setValue(dListStore.getAt(selectedIndex));
                    this.disableEvents(false);
                }
            }
        }
    }
}
