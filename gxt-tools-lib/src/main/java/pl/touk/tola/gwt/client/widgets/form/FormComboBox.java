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
package pl.touk.tola.gwt.client.widgets.form;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.KeyboardListener;

import java.util.List;


public class FormComboBox<D extends ModelData> extends ComboBox<D>
    implements FormFieldAddons {
    private String desc;
    private boolean editable;
    private boolean required;

    public FormComboBox() {
        setWidth(240);
        setForceSelection(true);
        setEditable(false);

        // TODO: moze to bug ale bez dodania store ComboBox wywala
        //       NullPointer w trakcie renderowania formularza
        addDummyValues(3);
    }

    public FormComboBox(String fieldLabel) {
        this();
        setFieldLabel(fieldLabel);
    }

    protected void onRender(Element element, int i) {
        if (!editable && getAllowBlank()) {
            setTriggerStyle("x-form-clear-trigger");
        }

        super.onRender(element, i);
    }

    @Override
    protected void onTriggerClick(ComponentEvent ce) {
        if (editable || !getAllowBlank()) {
            super.onTriggerClick(ce);
        } else {
            if (disabled || isReadOnly()) {
                return;
            }

            if (isExpanded()) {
                collapse();
            }

            clear();
        }
    }

    protected void onKeyDown(FieldEvent fe) {
        super.onKeyDown(fe); //To change body of overridden methods use File | Settings | File Templates.

        if (!editable && getAllowBlank() &&
                (fe.getTarget() == getInputEl().dom)) {
            if (fe.getKeyCode() == KeyboardListener.KEY_DOWN) {
                showList();
            }

            if ((fe.getKeyCode() == KeyboardListener.KEY_BACKSPACE) ||
                    (fe.getKeyCode() == KeyboardListener.KEY_DELETE)) {
                clear();
            }
        }
    }

    //    @Override
    //    protected void onFocus(ComponentEvent ce) {
    //        if (!editable && getAllowBlank() && ce.getTarget() == getInputEl().dom) {
    //            showList();
    //        }
    //        super.onFocus(ce);
    //    }
    @Override
    protected void onClick(ComponentEvent ce) {
        if (editable || !getAllowBlank()) {
            super.onClick(ce);
        } else {
            showList();
        }

        ce.stopEvent();
    }

    protected void showList() {
        if (disabled || isReadOnly()) {
            return;
        }

        if (!isExpanded()) {
            expand();
        }

        getInputEl().focus();
    }

    public void clear() {
        setRawValue(null);
        setValue(null);
        applyEmptyText();
        clearInvalid();
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        super.setEditable(editable);
    }

    public boolean isEditable() {
        return editable;
    }

    @Override
    protected void onBlur(ComponentEvent arg0) {
        super.onBlur(arg0);
        applyEmptyText(); //TODO: only temporary solution for #4298: Dziwne zachowanie combosow w edycji uzytkownika
    }

    @Override
    protected void onKeyPress(FieldEvent fe) {
        super.onKeyPress(fe);

        if (fe.getKeyCode() == KeyboardListener.KEY_ESCAPE) {
            reset();

            blur();

            return;
        }
    }

    public void setRequired(boolean required) {
        this.required = required;

        if (required) {
            addStyleName("tola-field-required");
        } else {
            removeStyleName("tola-field-required");
        }
    }

    public boolean getRequired() {
        return required;
    }

    @Override
    public void setStore(ListStore<D> store) {
        super.setStore(store);
        addIdsToItems();
    }

    public void changeStore(ListStore<D> store) {
        setStore(store);
        initList();
    }

    public void changeStore(List<D> values) {
        ListStore<D> store = new ListStore<D>();
        store.add(values);
        changeStore(store);
    }

    public void changeStore(List<D> values, String displayField) {
        changeStore(values);
        setDisplayField(displayField);
    }

    protected void addDummyValues(int count) {
        ListStore<D> ls = new ListStore<D>();

        for (int i = 0; i < count; i++) {
            BaseModelData dummyValue = new BaseModelData();
            dummyValue.set("text", "wartość " + (i + 1));
            ls.add((D) dummyValue);
        }

        setStore(ls);
    }

    protected void addIdsToItems() {
        ListStore<D> store = getStore();

        if (store == null) {
            GWT.log(getFieldLabel() + ": store is null!", null);

            return;
        }

        if (getDisplayField() == null) {
            GWT.log(getFieldLabel() + ": no displayField set!", null);

            return;
        }

        Object displayObj;
        String displayStr;
        String comboId = (getId() == null) ? "null" : getId().toString();

        for (int i = 0; i < store.getCount(); i++) {
            D d = store.getAt(i);
            displayObj = d.get(getDisplayField());

            if (displayObj == null) {
                GWT.log(getFieldLabel() + ": text is null!", null);

                continue;
            }

            displayStr = displayObj.toString();
            // rzutowanie na Object, bo w przegladarce wyrzucalo ClassCastException
            d.set("id", (Object) (comboId + "_" + displayStr.replace(' ', '-')));
        }
    }
}
