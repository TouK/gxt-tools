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

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.util.BaseEventPreview;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.util.KeyNav;
import com.extjs.gxt.ui.client.util.Size;
import com.google.gwt.user.client.*;

/**
 * Combo box with clear trigger.
 *
 * @author Michał Zalewski
 * @author Łukasz Kucharski
 * @param <D>
 */
public class ComboBoxWithClear<D extends ModelData> extends ComboBox<D> {

    protected El twinTrigger;
    protected String twinTriggerStyle = "x-form-clear-trigger";
    protected El span;
    protected boolean editable = true;

    @Override
    protected void onRender(Element target, int index) {
        input = new El(DOM.createInputText());
        setElement(DOM.createDiv(), target, index);
        addStyleName("x-form-field-wrap");

        trigger = new El(DOM.createImg());
        trigger.dom.setClassName("x-form-trigger " + triggerStyle);
        trigger.dom.setPropertyString("src", GXT.BLANK_IMAGE_URL);

        twinTrigger = new El(DOM.createImg());
        twinTrigger.dom.setClassName("x-form-trigger " + twinTriggerStyle);
        twinTrigger.dom.setPropertyString("src", GXT.BLANK_IMAGE_URL);

        span = new El(DOM.createSpan());
        span.dom.setClassName("x-form-twin-triggers");

        span.appendChild(trigger.dom);
        span.appendChild(twinTrigger.dom);

        el().appendChild(input.dom);
        el().appendChild(span.dom);

        if (isHideTrigger()) {
            span.setVisible(false);
        }

        super.onRender(target, index);

        if (!isEditable()) {
            setEditable(false);
        }

        twinTrigger.addEventsSunk(Event.ONCLICK | Event.MOUSEEVENTS);
        trigger.addEventsSunk(Event.ONCLICK | Event.MOUSEEVENTS);
        sinkEvents(Event.FOCUSEVENTS);

//        twinTriggerStyle = "x-form-clear-trigger";
//        input = new El(DOM.createInputText());
//        wrap = new El(DOM.createDiv());
//        wrap.dom.setClassName("x-form-field-wrap");
//
//        input.addStyleName(fieldStyle);
//
//        trigger = new El(DOM.createImg());
//        trigger.dom.setClassName("x-form-trigger " + triggerStyle);
//        trigger.dom.setPropertyString("src", GXT.BLANK_IMAGE_URL);
//
//
//        twinTrigger = new El(DOM.createImg());
//        twinTrigger.dom.setClassName("x-form-trigger " + twinTriggerStyle);
//        twinTrigger.dom.setPropertyString("src", GXT.BLANK_IMAGE_URL);
//
//        span = new El(DOM.createSpan());
//        span.dom.setClassName("x-form-twin-triggers");
//        DOM.appendChild(span.dom, twinTrigger.dom);
//        DOM.appendChild(span.dom, trigger.dom);
//
//        DOM.appendChild(wrap.dom, input.dom);
//        DOM.appendChild(wrap.dom, span.dom);
//
//        setElement(wrap.dom, target, index);
//
//        addStyleOnOver(twinTrigger.dom, "x-form-trigger-over");
//        addStyleOnOver(trigger.dom, "x-form-trigger-over");
//
//        if (isHideTrigger()) {
//            span.setVisible(false);
//        }
//
//        if (!editable) {
//            editable = true;
//            setEditable(false);
//        }
//
//        focusEventPreview = new BaseEventPreview() {
//
//            @Override
//            protected boolean onAutoHide(final PreviewEvent ce) {
//                if (ce.getEventTypeInt() == Event.ONMOUSEDOWN) {
//                    final Element target = ce.getTarget();
//                    DeferredCommand.addCommand(new Command() {
//
//                        public void execute() {
//                            mimicBlur(ce, target);
//                        }
//                    });
//                }
//                return false;
//            }
//        };
//
//        super.onRender(target, index);
//
//
//        DOM.sinkEvents(twinTrigger.dom, Event.ONCLICK | Event.MOUSEEVENTS);
//
//        DOM.sinkEvents(wrap.dom, Event.FOCUSEVENTS);
//        DOM.sinkEvents(trigger.dom, Event.ONCLICK | Event.MOUSEEVENTS);
    }

    protected void onTwinTriggerClick(ComponentEvent ce) {
        this.clearSelections();
        ComboBoxWithClear.this.fireEvent(Events.Change, ce);
    }

    @Override
    protected void onResize(int width, int height) {
        super.onResize(width, height);
        int tw = span.getWidth();
        if (width != Style.DEFAULT) {
            getInputEl().setWidth(width - tw);
        }
    }

    @Override
    public void onComponentEvent(ComponentEvent ce) {
        super.onComponentEvent(ce);
        int type = ce.getEventTypeInt();
        if (ce.getTarget() == twinTrigger.dom && type == Event.ONCLICK) {
            onTwinTriggerClick(ce);
        }
    }

    /**
     * Returns the twin trigger style.
     *
     * @return the twin trigger style
     */
    public String getTwinTriggerStyle() {
        return twinTriggerStyle;
    }

    /**
     * Sets the field's twin trigger style
     *
     * @param twinTriggerStyle the twin trigger style
     */
    public void setTwinTriggerStyle(String twinTriggerStyle) {
        this.twinTriggerStyle = twinTriggerStyle;
    }

    @Override
    protected void afterRender() {
        super.afterRender();
        addStyleOnOver(twinTrigger.dom, "x-form-trigger-over");
        addListener(Events.Select, new Listener<ComponentEvent>() {

            public void handleEvent(ComponentEvent componentEvent) {
                ComboBoxWithClear.this.fireEvent(Events.Change, componentEvent);
            }
        });

        new KeyNav(this) {

            @Override
            public void onEnter(ComponentEvent ce) {
                super.onEnter(ce);
                ComboBoxWithClear.this.fireEvent(Events.Change);
            }
        };
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        //1px position adjust for ie
        if (GXT.isIE && !isHideTrigger()) {
            int y;
            if ((y = input.getY()) != twinTrigger.getY()) {
                twinTrigger.setY(y);
            }
        }
    }

    /**
     * Allow or prevent the user from directly editing the field text. If false is
     * passed, the user will only be able to select from the items defined in the
     * dropdown list.
     *
     * @param value true to allow the user to directly edit the field text
     */
    @Override
    public void setEditable(boolean value) {
        if (value == this.editable) {
            return;
        }
        this.editable = value;
        if (rendered) {
            El fromEl = getInputEl();
            if (!value) {
                fromEl.dom.setPropertyBoolean("readOnly", true);
                fromEl.addStyleName("x-triggerfield-noedit");
            } else {
                fromEl.dom.setPropertyBoolean("readOnly", false);
                fromEl.removeStyleName("x-triggerfield-noedit");
            }
        }
    }

    @Override
    protected Size adjustInputSize() {
      return new Size(span.getWidth(), 0);
    }
 

    @Override
    protected void onClick(ComponentEvent ce) {
        if (!editable && ce.getTarget() == getInputEl().dom) {
            onTriggerClick(ce);
            return;
        }
        super.onClick(ce);
    }

}
