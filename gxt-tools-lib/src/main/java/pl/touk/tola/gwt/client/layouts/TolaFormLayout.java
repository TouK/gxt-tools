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
package pl.touk.tola.gwt.client.layouts;


import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.core.Template;
import com.extjs.gxt.ui.client.core.XDOM;
import com.extjs.gxt.ui.client.util.Params;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Container;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.AnchorLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;

import com.google.gwt.user.client.Element;

import pl.touk.tola.gwt.client.widgets.form.FormFieldAddons;


/**
 * Layout for form fields and their labels.
 */
public class TolaFormLayout extends AnchorLayout {
    private LabelAlign labelAlign = LabelAlign.LEFT;
    private boolean hideLabels;
    private String labelSeperator = ":";
    private int labelWidth = 100;
    private int defaultWidth = 200;
    private Template fieldTemplate;
    private String labelStyle;
    private String elementStyle;
    private int padding = 10;

    /**
     * Creates a new form layout.
     */
    public TolaFormLayout() {
    }

    public TolaFormLayout(LabelAlign labelAlign) {
        this.labelAlign = labelAlign;
    }

    /**
     * Returns the default field width.
     *
     * @return the default field width
     */
    public int getDefaultWidth() {
        return defaultWidth;
    }

    /**
     * Returns true if labels are being hidden.
     *
     * @return the hide label state
     */
    public boolean getHideLabels() {
        return hideLabels;
    }

    /**
     * Returns the label alignment.
     *
     * @return the label alignment
     */
    public LabelAlign getLabelAlign() {
        return labelAlign;
    }

    /**
     * Returns the label seperator.
     *
     * @return the label seperaotr
     */
    public String getLabelSeperator() {
        return labelSeperator;
    }

    /**
     * Returns the label width.
     *
     * @return the label width
     */
    public int getLabelWidth() {
        return labelWidth;
    }

    /**
     * Returns the panel's padding.
     *
     * @return the padding
     */
    public int getPadding() {
        return padding;
    }

    @Override
    public void setContainer(Container ct) {
        super.setContainer(ct);

        if (labelAlign != null) {
            ct.addStyleName("x-form-label-" + labelAlign.name().toLowerCase());
        }

        if (hideLabels) {
            labelStyle = "display:none";
            elementStyle = "padding-left:0;";
        } else {
            labelStyle = "width:" + labelWidth + "px";
            elementStyle = "padding-left:" + (labelWidth + 5) + "px";

            if (labelAlign == LabelAlign.TOP) {
                labelStyle = "width:auto;";
                elementStyle = "padding-left:0;";
            }
        }

        if (fieldTemplate == null) {
            StringBuffer sb = new StringBuffer();
            sb.append("<div class='x-form-item {5}' tabIndex='-1'>");
            sb.append(
                "<label for={0} style='{2};{7}' class=x-form-item-label>{1}{4}</label>");
            sb.append(
                "<div class='x-form-element' id='x-form-el-{0}' style='{3}'>");
            sb.append("</div>");
            sb.append(
                "<div class='tola-form-description' style='{3};{9}'>{8}</div>");
            sb.append("<div class='{6}'></div>");
            sb.append("</div>");
            fieldTemplate = new Template(sb.toString());
            fieldTemplate.compile();
        }
    }

    /**
     * Sets the default width for fields (defaults to 200).
     *
     * @param defaultWidth the default width
     */
    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    /**
     * True to hide field labels by default (defaults to false).
     *
     * @param hideLabels true to hide labels
     */
    public void setHideLabels(boolean hideLabels) {
        this.hideLabels = hideLabels;
    }

    /**
     * Sets the label alignment.
     *
     * @param labelAlign the label align
     */
    public void setLabelAlign(LabelAlign labelAlign) {
        this.labelAlign = labelAlign;
    }

    /**
     * Sets the label seperator (defaults to ':').
     *
     * @param labelSeperator the label seperator
     */
    public void setLabelSeperator(String labelSeperator) {
        this.labelSeperator = labelSeperator;
    }

    /**
     * Sets the default width in pixels of field labels (defaults to 100).
     *
     * @param labelWidth the label width
     */
    public void setLabelWidth(int labelWidth) {
        this.labelWidth = labelWidth;
    }

    /**
     * Sets the padding to be applied to the forms children (defaults to 10).
     *
     * @param padding the padding
     */
    public void setPadding(int padding) {
        this.padding = padding;
    }

    @Override
    protected boolean isValidParent(Element elem, Element parent) {
        return true;
    }

    @Override
    protected void onLayout(Container container, El target) {
        super.onLayout(container, target);

        if (padding > 0) {
            target.setStyleAttribute("padding", padding);
        }
    }

    @Override
    protected void renderComponent(Component component, int index, El target) {
        if (component instanceof Field) {
            Field f = (Field) component;

            renderField((Field) component, index, target);

            FormData formData = (FormData) getLayoutData(f);

            if (formData == null) {
                formData = f.getData("formData");
            }

            f.setWidth(defaultWidth);

            if (formData != null) {
                if (formData.getWidth() > 0) {
                    f.setWidth(formData.getWidth());
                }

                if (formData.getHeight() > 0) {
                    f.setHeight(formData.getHeight());
                }
            }
        }
    }

    private void renderField(Field field, int index, El target) {
        if ((field != null) && !field.isRendered()) {
            String ls = (field.getLabelSeparator() != null)
                ? field.getLabelSeparator() : labelSeperator;
            field.setLabelSeparator(ls);

            Params p = new Params();
            p.add(field.getId());
            p.add(field.getFieldLabel());
            p.add(labelStyle);
            p.add(elementStyle);
            p.add(ls);
            p.add(hideLabels ? "x-hide-label" : "");
            p.add("x-form-clear-left");
            p.add(field.getLabelStyle());

            if (field instanceof FormFieldAddons &&
                    (((FormFieldAddons) field).getDescription() != null)) {
                p.add(((FormFieldAddons) field).getDescription());
                p.add("");
            } else {
                p.add("");
                p.add("display:none");
            }

            fieldTemplate.append(target.dom, p);

            Element parent = XDOM.getElementById("x-form-el-" + field.getId());
            field.render(parent);
        } else {
            super.renderComponent(field, index, target);
        }
    }
}
