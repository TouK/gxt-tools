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
package pl.touk.top.dictionary.impl.gwt.client;

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import pl.touk.wonderfulsecurity.utils.Commons;
import pl.touk.top.dictionary.model.domain.DictionaryEntry;
import pl.touk.top.dictionary.impl.gwt.client.widgets.DictionaryBasedRemoteFilter;

import java.util.Map;
import java.util.List;
import pl.touk.top.dictionary.impl.gwt.client.widgets.ComboBoxWithClear;
import pl.touk.top.dictionary.impl.gwt.client.widgets.withMemory.RemoteComboDictionaryFilterWithMemory;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 * @author Rafał Pietrasik - rpt@touk.pl
 */
public final class ComboFactory {

    private ComboFactory() {
    }

    public static ComboBox buildStaticCombo(String dictionaryCategory) {

        ComboBox combo = new ComboBox();
        setUpCombo(dictionaryCategory, combo);
        return combo;

    }

    /**
     * Builds static combo box with clear trigger.
     * @param dictionaryCategory
     * @return
     */
    public static ComboBoxWithClear buildStaticComboWithClear(String dictionaryCategory) {

        ComboBoxWithClear combo = new ComboBoxWithClear();
        setUpCombo(dictionaryCategory, combo);
        return combo;
    }

    /**
     * Builds static combo box with clear trigger.
     * @param dictionaryCategory
     * @param fieldName
     * @return
     */
    public static ComboBoxWithClear buildStaticComboWithClear(String dictionaryCategory, String fieldName) {
        ComboBoxWithClear combo = buildStaticComboWithClear(dictionaryCategory);
        combo.setName(fieldName);
        return combo;
    }

    /**
     * Builds static combo box with clear trigger.
     * @param dictionaryCategory
     * @param fieldName
     * @param emptyText
     * @return
     */
    public static ComboBoxWithClear buildStaticComboWithClear(String dictionaryCategory, String fieldName, String emptyText) {
        ComboBoxWithClear combo = buildStaticComboWithClear(dictionaryCategory, fieldName);
        combo.setEmptyText(emptyText);
        return combo;
    }

    /**
     * Builds remote combo box.
     * 
     * @param dictionaryCategory
     * @param loader
     * @param pagingToolbar
     * @param filterProperty
     * @param type flag, if property should be send to hibernate as integerValue or long. Have to be specified due to errors with class cast String-->int
     * @return
     */
    public static DictionaryBasedRemoteFilter buildRemoteFilterComboBox(String dictionaryCategory, PagingLoader loader, PagingToolBar pagingToolbar, String filterProperty, DictionaryBasedRemoteFilter.TargetFieldType type) {
        DictionaryBasedRemoteFilter combo = new DictionaryBasedRemoteFilter(loader, pagingToolbar, filterProperty, type);
        combo.setDisplayField("value");
        combo.setEditable(false);
        combo.setStore(prepareStore(dictionaryCategory));
        combo.setTriggerAction(ComboBox.TriggerAction.ALL);
        return combo;
    }

    /**
     * Builds remote combo box with last select value stored in coockie memmory.
     *
     * @param dictionaryCategory
     * @param loader
     * @param pagingToolbar
     * @param filterProperty
     * @param type flag, if property should be send to hibernate as integerValue or long. Have to be specified due to errors with class cast String-->int
     * @param uniqueName - the name of the combo in coockie
     * @return
     */
    public static DictionaryBasedRemoteFilter buildRemoteFilterComboBoxWithCoockie(String dictionaryCategory, PagingLoader loader, PagingToolBar pagingToolbar, String filterProperty, DictionaryBasedRemoteFilter.TargetFieldType type, String uniqueName) {
        DictionaryBasedRemoteFilter combo = new RemoteComboDictionaryFilterWithMemory(dictionaryCategory, loader, pagingToolbar, filterProperty, type, uniqueName);
        combo.setDisplayField("value");
        combo.setEditable(false);
        combo.setStore(prepareStore(dictionaryCategory));
        combo.setTriggerAction(ComboBox.TriggerAction.ALL);
        return combo;
    }

    protected static ListStore prepareStore(String dictionaryCategory) {

        Commons.checkArgumentsNotNull("Kategoria nie moze byc nullem", dictionaryCategory);

        checkDictionaryInitialized();

        ListStore store = new ListStore();

        Map<String, DictionaryEntry> category = ClientDictionary.fetchCategoryAsObjects(dictionaryCategory);

        Commons.checkArgumentsNotNull("Kategorii slownika: " + dictionaryCategory + " nie znaleziono w repozytorium", category);

        BeanModelFactory factory = BeanModelLookup.get().getFactory(DictionaryEntry.class);

        Commons.checkArgumentsNotNull("Bean model factory not found for class" + DictionaryEntry.class, factory);

        List<BeanModel> models = factory.createModel(category.values());

        store.add(models);

        return store;

    }

    protected static void checkDictionaryInitialized() {
        if (!ClientDictionary.isInitialized()) {
            throw new IllegalStateException("ClientDictionary service is not initialized, are you trying to use this service"
                    + "before initializing ClientDictionary???");
        }
    }

    private static void setUpCombo(String dictionaryCategory, ComboBox combo) {
        combo.setDisplayField("value");
        combo.setStore(prepareStore(dictionaryCategory));
        combo.setEditable(false);
        combo.setTriggerAction(ComboBox.TriggerAction.ALL);
    }
}
