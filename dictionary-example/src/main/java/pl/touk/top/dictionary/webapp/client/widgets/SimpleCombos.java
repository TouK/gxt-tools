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
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import pl.touk.top.dictionary.impl.gwt.client.ComboFactory;
import pl.touk.top.dictionary.impl.gwt.client.widgets.DictionaryComboBindingToStringField;
import pl.touk.top.dictionary.model.domain.DictionaryEntry;


/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class SimpleCombos extends TabItem {

    static class ModelExample extends BaseModel{}

    public SimpleCombos(String text) {
        super(text);
        this.setLayout(new RowLayout());
        FormPanel fp = new FormPanel();



        ComboBox comboEntryKey = ComboFactory.buildStaticCombo("ERROR_CATEGORY");
        comboEntryKey.setFieldLabel("Wybierz");
        ComboBox comboId = ComboFactory.buildStaticCombo("ERROR_CATEGORY");
        comboId.setFieldLabel("Wybierz");
        ComboBox comboValue = ComboFactory.buildStaticCombo("ERROR_CATEGORY");
        comboValue.setFieldLabel("Wybierz");
        ComboBox comboComment = ComboFactory.buildStaticCombo("ERROR_CATEGORY");
        comboComment.setFieldLabel("Wybierz");
        TextField entryKey = new TextField();
        entryKey.setFieldLabel("entryKey");
        TextField id = new TextField();
        id.setFieldLabel("id");
        TextField value = new TextField();
        value.setFieldLabel("value");
        TextField comment = new TextField();
        comment.setFieldLabel("comment");

        fp.add(comboEntryKey);
        fp.add(comboId);
        fp.add(comboValue);
        fp.add(comboComment);
        fp.add(entryKey);
        fp.add(id);
        fp.add(value);
        fp.add(comment);


        FormBinding fb = new FormBinding(fp);
        ModelExample model = new ModelExample();

        fb.bind(model);

        fb.addFieldBinding(new DictionaryComboBindingToStringField(comboEntryKey,"modelEntryKey", DictionaryEntry.EntryProperty.entryKey));
        fb.addFieldBinding(new DictionaryComboBindingToStringField(comboId,"modelId", DictionaryEntry.EntryProperty.id));
        fb.addFieldBinding(new DictionaryComboBindingToStringField(comboValue,"modelValue", DictionaryEntry.EntryProperty.value));
        fb.addFieldBinding(new DictionaryComboBindingToStringField(comboComment,"modelComment", DictionaryEntry.EntryProperty.comment));
        fb.addFieldBinding(new FieldBinding(entryKey,"modelEntryKey"));
        fb.addFieldBinding(new FieldBinding(id,"modelId"));
        fb.addFieldBinding(new FieldBinding(value,"modelValue"));
        fb.addFieldBinding(new FieldBinding(comment,"modelComment"));

        comboEntryKey.setEditable(false);
        comboEntryKey.setEmptyText("wybierz");
        this.add(fp);
        


    }
}
