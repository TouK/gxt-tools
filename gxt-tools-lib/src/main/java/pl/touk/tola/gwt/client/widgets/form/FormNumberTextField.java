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

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.KeyboardListener;

import java.util.ArrayList;
import java.util.List;


public class FormNumberTextField<D> extends FormTextField<D>
    implements FormFieldAddons {
    private String baseChars = "0123456789";
    private String additionalChars = "`abcdefghi";
    private String separators = "";
    private boolean allowSeparators = false;
    private List<Character> allowed;
    private int maxLength = Integer.MAX_VALUE;

    public FormNumberTextField() {
        setRegex("[" + baseChars + separators + "]*");
        //TODO: localize
        getMessages()
            .setRegexText("Użyto błednych znaków. " + getRegex().toString());
    }

    @Override
    public void setMaxLength(int maxLength) {
        super.setMaxLength(maxLength);
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public FormNumberTextField(String fieldLabel) {
        this();
        setFieldLabel(fieldLabel);
    }

    public String getBaseChars() {
        return baseChars;
    }

    public void setBaseChars(String baseChars) {
        assertPreRender();
        this.baseChars = baseChars;
    }

    public String getSeparators() {
        return separators;
    }

    public void setSeparators(String separators) {
        assertPreRender();
        this.separators = separators;
    }

    public boolean getAllowSeparators() {
        return allowSeparators;
    }

    public void setAllowSeparators(boolean allowSeparators) {
        this.allowSeparators = allowSeparators;
    }

    @Override
    protected void onRender(Element target, int index) {
        super.onRender(target, index);
        allowed = new ArrayList<Character>();

        for (int i = 0; i < baseChars.length(); i++) {
            allowed.add(baseChars.charAt(i));
        }

        for (int i = 0; i < additionalChars.length(); i++) {
            allowed.add(additionalChars.charAt(i));
        }

        if (allowSeparators) {
            for (int i = 0; i < separators.length(); i++) {
                allowed.add(separators.charAt(i));
            }
        }
    }

    @Override
    protected void onKeyDown(FieldEvent fe) {
        super.onKeyDown(fe);

        char k = (char) fe.getKeyCode();

        if (fe.isSpecialKey() || (k == KeyboardListener.KEY_BACKSPACE) ||
                (k == KeyboardListener.KEY_DELETE) ||
                (fe.isControlKey() &&
                ((k == 'V') || (k == 'C') || (k == 'X') || (k == 'A')))) {
            return;
        }

        if (!"".equals(getSelectedText())) {
            int c = getCursorPos();
            setRawValue(getRawValue().substring(0, c) +
                getRawValue().substring(c + getSelectionLength()));
            setCursorPos(c);
        }

        if (getRawValue().length() >= getMaxLength()) {
            setRawValue(getRawValue().substring(0, getMaxLength()));
        }

        if (!allowed.contains(k) || fe.isShiftKey() ||
                (getCursorPos() >= getMaxLength())) {
            fe.stopEvent();
            GWT.log("zablokowany niedozwolony klawisz", null);
        }
    }

    @Override
    public boolean validate(boolean preventMark) {
        setRawValue(getRawValue().toUpperCase()
                        .replaceAll("[^" + getBaseChars() +
                getAllowSeparators() + "]", ""));

        return super.validate(preventMark);
    }

    @Override
    protected void onBlur(ComponentEvent be) {
        super.onBlur(be);
    }

    /**
     * Additional chars to filter key press events, NOT for checking value.
     */
    public String getAdditionalChars() {
        return additionalChars;
    }

    /**
     * Additional chars to filter key press events, NOT for checking value.
     */
    public void setAdditionalChars(String additionalChars) {
        this.additionalChars = additionalChars;
    }
}
