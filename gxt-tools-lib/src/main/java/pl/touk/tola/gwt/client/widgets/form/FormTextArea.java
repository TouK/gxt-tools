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

import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.form.TextArea;

import com.google.gwt.user.client.ui.KeyboardListener;


public class FormTextArea extends TextArea implements FormFieldAddons {
    private String desc;
    private boolean required;

    public FormTextArea() {
    }

    public FormTextArea(String fieldLabel) {
        this();
        setFieldLabel(fieldLabel);
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public void clear() {
        setValue(null);
        clearInvalid();
    }

    //	@Override
    //	public void enable() {
    //		super.enable();
    //		getElement().removeAttribute("disabled");
    //	}
    @Override
    protected void onKeyPress(FieldEvent fe) {
        super.onKeyPress(fe);

        if (fe.getKeyCode() == KeyboardListener.KEY_ESCAPE) {
            reset();
            blur();

            return;
        }
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;

        if (required) {
            addStyleName("tola-field-required");
        } else {
            removeStyleName("tola-field-required");
        }
    }
}
