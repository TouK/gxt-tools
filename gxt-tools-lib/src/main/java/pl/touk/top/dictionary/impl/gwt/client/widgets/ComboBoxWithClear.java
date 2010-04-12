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

    private String twinTriggerStyle = "x-form-clear-trigger";
    private El span;


    public String getTwinTriggerStyle() {
      return twinTriggerStyle;
    }

    public void setTwinTriggerStyle(String twinTriggerStyle) {
      this.twinTriggerStyle = twinTriggerStyle;
    }



    @Override
    public void onComponentEvent(ComponentEvent ce) {
      super.onComponentEvent(ce);
      int type = ce.getEventTypeInt();
      if (ce.getTarget() == twinTrigger.dom && type == Event.ONCLICK) {
        onTwinTriggerClick(ce);
      }
    }

    @Override
    protected Size adjustInputSize() {
      return new Size(span.getWidth(), 0);
    }

    protected void afterRender() {
      super.afterRender();
      addStyleOnOver(twinTrigger.dom, "x-form-trigger-over");
    }

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
    }

    protected void onTwinTriggerClick(ComponentEvent ce) {
      this.clearSelections();  
      fireEvent(Events.TwinTriggerClick, ce);
    }

}
