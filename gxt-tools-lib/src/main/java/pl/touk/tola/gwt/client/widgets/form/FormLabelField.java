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

import com.extjs.gxt.ui.client.widget.form.LabelField;


public class FormLabelField extends LabelField implements FormFieldAddons {
    private String desc;

    public FormLabelField() {
        setLabelSeparator(":");
    }

    public FormLabelField(String label) {
        this();
        setFieldLabel(label);
    }

    public FormLabelField(String label, String value) {
        this(label);
        setValue(value);
    }

    @Override
    public void setValue(Object value) {
        // TODO Auto-generated method stub
        if (value == null) {
            value = "";
        }

        super.setValue(value);
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public void clear() {
        // TODO Auto-generated method stub
    }

    public boolean getRequired() {
        // TODO Auto-generated method stub
        return false;
    }

    public void setRequired(boolean required) {
        // TODO Auto-generated method stub
    }
}
