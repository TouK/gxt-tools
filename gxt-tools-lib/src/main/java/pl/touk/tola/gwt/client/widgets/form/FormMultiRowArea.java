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

import com.extjs.gxt.ui.client.widget.form.PropertyEditor;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * Multiline text field which returns holded values as <code>String array</code>
 * one value per line (<code>separatorRegex</code>).
 *
 * @author Tomasz Przybysz
 */
public class FormMultiRowArea extends FormTextField<String[]> {

    protected String valueSeparator = "\n";
    protected String separatorRegex = "[,;\t\n\r]+[,;\t\n\r ]*";
    private int initialWidth = 100;
    private int initialHeight = 120;
    private boolean preventScrollbars = false;

    public FormMultiRowArea() {
        super();

        propertyEditor = new PropertyEditor<String[]>() {

            public String getStringValue(String[] linesArr) {
                if (linesArr == null) {
                    return null;
                }

                StringBuilder strBuilder = new StringBuilder();

                for (int i = 0; i < linesArr.length; i++) {
                    strBuilder.append(linesArr[i]);

                    if (i < (linesArr.length - 1)) {
                        strBuilder.append(valueSeparator);
                    }
                }

                return strBuilder.toString();
            }

            public String[] convertStringValue(String str) {
                return (str == null) ? null : str.split(separatorRegex);
            }
        };
        //TODO: localize
        setDescription("(Lista rozdzielona przecinkami, " +
                "średnikami, tabulacjami lub znakami nowej linii)");
    }

    public FormMultiRowArea(String fieldLabel) {
        this();
        setFieldLabel(fieldLabel);
    }

    public String getSeparatorRegex() {
        return separatorRegex;
    }

    public void setSeparatorRegex(String separatorRegex) {
        this.separatorRegex = separatorRegex;
    }

    public String getValueSeparator() {
        return valueSeparator;
    }

    public void setValueSeparator(String valueSeparator) {
        this.valueSeparator = valueSeparator;
    }

    /**
     * Returns the initial height.
     *
     * @return the initial height
     */
    public int getInitialHeight() {
        return initialHeight;
    }

    /**
     * Returns the initial width.
     *
     * @return the initial width
     */
    public int getInitialWidth() {
        return initialWidth;
    }

    /**
     * Returns true if scroll bars are disabled.
     *
     * @return the scroll bar state
     */
    public boolean isPreventScrollbars() {
        return preventScrollbars;
    }

    /**
     * Sets the inital height (defaults to 60, pre-render).
     *
     * @param initialHeight the initial height
     */
    public void setInitialHeight(int initialHeight) {
        this.initialHeight = initialHeight;
    }

    /**
     * Sets the initial width (defaults to 100, pre-render).
     *
     * @param initialWidth the initial width
     */
    public void setInitialWidth(int initialWidth) {
        this.initialWidth = initialWidth;
    }

    /**
     * True to prevent scrollbars from appearing regardless of how much text is in
     * the field (equivalent to setting overflow: hidden, defaults to false,
     * pre-render).
     *
     * @param preventScrollbars true to disable scroll bars
     */
    public void setPreventScrollbars(boolean preventScrollbars) {
        this.preventScrollbars = preventScrollbars;
    }

    @Override
    protected void onRender(Element target, int index) {
        setElement(DOM.createTextArea(), target, index);

        el().setElementAttribute("autocomplete", "off");

        getSize().width = initialWidth;
        getSize().height = initialHeight;


        if (preventScrollbars) {
            el().setStyleAttribute("overflow", "hidden");
        }

        super.onRender(target, index);
    }
}
