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
package pl.touk.tola.gwt.client.widgets;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.DateWrapper;

import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.DateTimePropertyEditor;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Event;

import java.util.Date;


/**
 *
 * @deprecated Klasa jest wyspecjalizowana (działa tylko w projekcie RACA) i niepotrzebnie zostala dodana do Toli.
 * Oznaczam ją jako depricated zamiast usuwać, po to aby służyła jako przykład do pisania filtrów 
 * wykorzystujących kalendarz jako edytor.
 */
@Deprecated
public class DateRemoteFilterField extends DateField implements Listener {
    protected PagingToolBar pager;
    protected Loader loader;
    protected Date currentFilterValue = null;
    protected String filteredPropertyName;

    public DateRemoteFilterField(PagingToolBar pager, Loader loader) {
        this.pager = pager;
        this.loader = loader;
        loader.addListener(Loader.BeforeLoad, this);
        setAutoValidate(true);
        setValidateOnBlur(false);
        setTriggerStyle("x-form-clear-trigger");
        setWidth(112);
        setPropertyEditor(new DateTimePropertyEditor(
                DateTimeFormatsUtils.DATE_FORMAT));
    }

    public DateRemoteFilterField(PagingToolBar pager, Loader loader,
        String filterPropertyName) {
        this(pager, loader);
        this.filteredPropertyName = filterPropertyName;
    }

    @Override
    protected void onTriggerClick(ComponentEvent ce) {
        //super.onTriggerClick(ce);
        GWT.log("on tc " + ce.getType() + " " + ce, null);
        setValue(null);

        //onFilter();
    }

    protected void onClick(ComponentEvent ce) {
        GWT.log("on click " + ce.getType() + " " + ce, null);
        super.onTriggerClick(ce);

        //onFilter();
    }

    protected void onCompositeEvent(ComponentEvent ce) {
        GWT.log("Handle event ce - " + ce.getType() + " " + ce, null);

        // Ten fragment kodu nie zostal zmigrowany do wersji 1.6.
        //if (ce.getEventType() == Event.ONCHANGE) {
        //    onFilter();
        //}
    }

    @Override
    protected boolean validateValue(String value) {
        boolean ret = super.validateValue(value);
        GWT.log("validate --- " + getValue(), null);
        onFilter();

        return ret;
    }

    protected void onFilter() {
        GWT.log("On filter" + getValue(), null);
        currentFilterValue = getValue();
        pager.first();
    }

    public void handleEvent(BaseEvent be) {
        GWT.log("Handle event before load - " + be.getType() + " " + be, null);

        if ((be != null) && (be.getType() == Loader.BeforeLoad)) {
            LoadEvent loadEvent = (LoadEvent) be;
            BasePagingLoadConfig config = (BasePagingLoadConfig) loadEvent.getConfig();

            //        Map filterMap = config.getFilterMap();
            if ((this.getValue() == null) ||
                    this.getValue().toString().trim().equals("")) {
                config.remove(filteredPropertyName);
            } else {
                // !INFO
                // dodaję jeden dzień do daty aby prawidłowo porównywała dni mniejsze niż ...
                if (filteredPropertyName.endsWith("_LESS_THAN")) {
                    GWT.log("and one day do date for _LESS_THAN", null);

                    DateWrapper lessThanDateWrapper = new DateWrapper(getValue());
                    lessThanDateWrapper = lessThanDateWrapper.addDays(1);
                    config.set(filteredPropertyName,
                        lessThanDateWrapper.asDate());
                } else {
                    config.set(filteredPropertyName, this.getValue());
                }
            }
        }
    }

    /*public void setValue(Date date) {
        super.setValue(date);
        if ( date != getValue() ) {
           GWT.log("setValue " + date + " current value " + getValue(), null);
           onFilter();
        }
    }*/
    public String getFilteredPropertyName() {
        return filteredPropertyName;
    }

    public void setFilteredPropertyName(String filteredPropertyName) {
        this.filteredPropertyName = filteredPropertyName;
    }
}
