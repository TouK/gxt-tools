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

import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Popup;


/**
 * @author j@touk.pl
 */
public class TolaPopup extends Popup {
    static LayoutContainer innerPanel;

    public TolaPopup() {
        setLayoutOnChange(true);
        setShadow(true);
        setAutoHide(false);
// Zmieniono podczas migracji do gxt 2.0-m1       
//        setEventPreview(false);

        innerPanel = new LayoutContainer();
        innerPanel.setLayoutOnChange(true);
        innerPanel.setStyleName("tola-popup");
        add(innerPanel);
    }

    public void display(String html) {
        display(html, 4, 4);
    }

	public void display(String html, int x, int y){
		innerPanel.removeAll();

        Html popupInnerHtml = new Html(html);
        popupInnerHtml.setStyleName("tola-popup-inner");
        innerPanel.add(popupInnerHtml);
        showAt(x, y);
	}
}
