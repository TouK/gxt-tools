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
package pl.touk.top.dictionary.webapp.client.widgets;

import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.binding.Converter;

import com.google.gwt.user.client.Window;

import pl.touk.top.dictionary.impl.gwt.client.ComboFactory;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class ComboBoxWithClearDemo extends TabItem {

    public ComboBoxWithClearDemo(String text) {
        super(text);
        this.setLayout(new RowLayout());

        BeanModel target = new BeanModel() {
        };
        target.addChangeListener(new ChangeListener() {
            public void modelChanged(ChangeEvent changeEvent) {
                Window.alert("HURA!");
            }
        });

        FormPanel form = new FormPanel();
        FormBinding formBinding = new FormBinding(form);
        formBinding.bind(target);

        final ComboBox combo = ComboFactory.buildStaticComboWithClear("ERROR_CATEGORY");
        form.add(combo);

        FieldBinding binding = new FieldBinding(combo, "entryKey");
        binding.setConverter(new Converter() {
            @Override
            public Object convertModelValue(Object value) {

                return combo.getStore().findModel("entryKey",value);
                
            }

            @Override
            public Object convertFieldValue(Object value) {
                
                if(value == null){
                    return null;
                }
                
                return ((BeanModel) value).get("entryKey");
            }
        });

        formBinding.addFieldBinding(binding);

        combo.setEditable(false);
        combo.setEmptyText("wybierz");
        this.add(form);
    }

}

