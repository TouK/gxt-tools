/*
* Copyright (c) 2008 TouK.pl
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
package pl.touk.wonderfulsecurity.gwt.client.ui;

import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.Registry;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public abstract class BaseCreateEditView extends View {

// --------------------------- CONSTRUCTORS ---------------------------

    public BaseCreateEditView(com.extjs.gxt.ui.client.mvc.Controller controller) {
        super(controller);
    }

// -------------------------- OTHER METHODS --------------------------

    protected void attachTabItemToMainPanel(TabItem item) {
        TabPanel mainTabPanel = Registry.get("MAIN_TAB_PANEL");
        mainTabPanel.add(item);
        mainTabPanel.setSelection(item);
    }

    protected void setActiveTab(TabItem ti) {
        TabPanel mainTabPanel = Registry.get("MAIN_TAB_PANEL");
        mainTabPanel.setSelection(ti);
    }

//    protected abstract String getTabName();

// -------------------------- INNER CLASSES --------------------------

}
