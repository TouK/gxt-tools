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
package pl.touk.tola.gwt.client.widgets;

import com.extjs.gxt.ui.client.Style.Scroll;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.DelayedTask;
import com.extjs.gxt.ui.client.util.KeyNav;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.core.XDOM;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.WindowResizeListener;


/**
 * @author j@touk.pl
 */
public class AutosizeWindow extends Window {
    public AutosizeWindow() {
        setLayoutOnChange(true);
        setMinWidth(700);
        setWidth(700);
        setConstrain(true);
        setResizable(false);
        setScrollMode(Scroll.AUTO);
    }

    @Override
    protected void afterRender() {
        super.afterRender();

        com.google.gwt.user.client.Window.addWindowResizeListener(new WindowResizeListener() {
                public void onWindowResized(int width, int height) {
                    arrangeWindow();
                }
            });

        if (isOnEsc()) {
            new KeyNav<ComponentEvent>(this) {
                    @Override
                    public void onEsc(ComponentEvent ce) {
                        super.onEsc(ce);
                        hide();
                    }
                };
        }
    }

    public void arrangeWindow() {
        DelayedTask arrangeWindow = new DelayedTask(new Listener<BaseEvent>() {
                    public void handleEvent(BaseEvent be) {
                        setAutoHeight(true);
                        setSize(getWidth(), getHeight());

                        if (XDOM.getViewportSize().width >= getMinWidth()) {
                            setWidth(getMinWidth());
                        } else {
                            setWidth(XDOM.getViewportSize().width);
                        }

                        if (XDOM.getViewportSize().height <= getHeight()) {
                            setAutoHeight(false);
                            setHeight(XDOM.getViewportSize().height);
                        }

                        int xOffset = XDOM.getViewportSize().width -
                            (getAbsoluteLeft() + getWidth());
                        int yOffset = XDOM.getViewportSize().height -
                            (getAbsoluteTop() + getHeight());
                        int top = getAbsoluteTop();
                        int left = getAbsoluteLeft();

                        if (xOffset < 0) {
                            left += xOffset;
                        }

                        if (yOffset < 0) {
                            top += yOffset;
                        }

                        setPosition((left < 0) ? 0 : left, (top < 0) ? 0 : top);
                        layout();
                    }
                });
        GWT.log("arrange", null);
        arrangeWindow.delay(100);
    }
}
