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
package pl.touk.top.dictionary.impl.gwt.client.widgets;

import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.*;


/**
 * Implementacja combo, ktora dodatkowo posiada przycisk do resetowania wartosci
 *
 * @param <D> 
 * @author Lukasz Kucharski - lkc@touk.pl
 * @author Michał Zalewski - mzl@touk.pl
 */
public class DictionaryBasedRemoteFilter<D extends ModelData> extends ComboBoxWithClear<D> implements Listener {
    public static final String ENTRYKEY = "entryKey";

    public static enum TargetFieldType{INTEGER, LONG, STRING}
    // ------------------------------ FIELDS ------------------------------
    protected PagingLoader loader;
    protected PagingToolBar pgToolBar;
    protected String filteredPropertyName;
    private TargetFieldType conversionType;

    // --------------------------- CONSTRUCTORS ---------------------------
    /**
     * @param loader
     * @param pgToolBar
     * @param filterPropertyName
     * @param type flag if property should be send to hibernate as integerValue or long. Have to be specified due to errors with class cast String-->int.
     *          Set it to null to prevent conversion of any kind
     */
    public DictionaryBasedRemoteFilter(PagingLoader loader, PagingToolBar pgToolBar, String filterPropertyName, TargetFieldType type) {
        this.pgToolBar = pgToolBar;
        this.loader = loader;
        this.conversionType = type;
        loader.addListener(Loader.BeforeLoad, this);
        this.filteredPropertyName = filterPropertyName;
        this.addListener(Events.Select, this);
        this.addListener(Events.TwinTriggerClick, this);
    }

    // --------------------- GETTER / SETTER METHODS ---------------------
    public String getFilteredPropertyName() {
        return filteredPropertyName;
    }

    public void setFilteredPropertyName(String filteredPropertyName) {
        this.filteredPropertyName = filteredPropertyName;
    }

    protected void doFilter() {
        pgToolBar.first();

    }

// ------------------------ INTERFACE METHODS ------------------------

    // --------------------- Interface Listener ---------------------

    public void handleEvent(BaseEvent baseEvent) {
        if (baseEvent.getType() == Events.Select) {
            handleSelectEvent();
            return;
        }//Events.TwinTriggerClick

        if (baseEvent.getType() == Loader.BeforeLoad) {
            handleBeforeLoadEvent((LoadEvent) baseEvent);
            return;
        }
    }

    protected void handleBeforeLoadEvent(LoadEvent loadEvent) {
        BasePagingLoadConfig config = (BasePagingLoadConfig) loadEvent.getConfig();
        config.setAllowNestedValues(false);

        if ((this.getValue() == null) || this.getValue().toString().trim().equals("")) {
            config.remove(filteredPropertyName);
        } else {
            String key = this.getValue().get(ENTRYKEY);
            Object converted = convertValue(key);

            config.set(filteredPropertyName, converted);
        }
    }

    protected Object convertValue(String key) {
        Object converted = null;
        switch (conversionType) {
            case INTEGER:
                try {
                    converted = new Integer(key);
                } catch (NumberFormatException nex) {
                    GWT.log("Błąd konwersji danych w filtrze. Na integer nie mozna przekonwertowac wartosci: " + key);
                }
                break;
            case LONG:
                try {
                    converted = new Long(key);
                } catch (NumberFormatException nex) {
                    GWT.log("Błąd konwersji danych w filtrze. Na long nie mozna przekonwertowac wartosci: " + key);
                }
                break;
           case STRING:
               converted = key;
        }
        return converted;
    }

    protected void handleSelectEvent() {
        doFilter();
    }

    @Override
    protected void onTwinTriggerClick(ComponentEvent ce) {
        super.onTwinTriggerClick(ce);
        doFilter();
    }
}