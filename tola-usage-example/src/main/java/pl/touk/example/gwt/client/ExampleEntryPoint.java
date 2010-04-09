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
package pl.touk.example.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ExampleEntryPoint implements EntryPoint {
// ------------------------------ FIELDS ------------------------------

//    private SecurityTestServiceAsync testService;
//	private ISecurityManagerRpcAsync securityManagerRpcAsync;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface EntryPoint ---------------------

    public void onModuleLoad() {
       
                initializeExample();
    }

// -------------------------- OTHER METHODS --------------------------

    protected void initializeExample() {

        
        Viewport viewport = new Viewport();
        viewport.setLayout(new FitLayout());
        viewport.setStyleAttribute("background", "none");
        RootPanel.get().add(viewport);

        TabPanel tabs = new TabPanel();

        TabItem messageHistoryExample = new TabItem("Historia komunikatuff");
        messageHistoryExample.setLayout(new FitLayout());
        messageHistoryExample.setLayoutOnChange(true);
        messageHistoryExample.add(new MessageHistoryExample());


        TabItem variablePageSizePagiginToolbarExample = new TabItem("VariablePageSizePagingToolbar  & ComboBoxWithMemory");
        variablePageSizePagiginToolbarExample.setLayout(new FitLayout());
        variablePageSizePagiginToolbarExample.setLayoutOnChange(true);
        variablePageSizePagiginToolbarExample.add(new VariablePageSizePagingToolBarExample());


        tabs.add(messageHistoryExample);
        tabs.add(variablePageSizePagiginToolbarExample);
        viewport.add(tabs);
        viewport.layout();


    }


}
