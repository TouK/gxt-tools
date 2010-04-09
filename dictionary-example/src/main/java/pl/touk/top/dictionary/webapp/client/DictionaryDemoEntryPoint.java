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

package pl.touk.top.dictionary.webapp.client;

import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import pl.touk.top.dictionary.webapp.client.widgets.SimpleDictionary;
import pl.touk.top.dictionary.webapp.client.widgets.SimpleCombos;
import pl.touk.top.dictionary.webapp.client.widgets.ComboBoxWithClearDemo;
import pl.touk.top.dictionary.webapp.client.widgets.RemoteFilterComboDemo;
import pl.touk.top.dictionary.impl.gwt.client.ClientDictionary;


public class DictionaryDemoEntryPoint implements EntryPoint {

    protected Dispatcher dispatcher;
    protected Viewport viewport;


    public void onModuleLoad() {

        ClientDictionary.initialize("secure/rpc/dictionaryService.do", new AsyncCallback() {
            public void onFailure(Throwable throwable) {
                Window.alert("Nie mozna pobrać startowych danych słownikowych" + throwable);
            }

            public void onSuccess(Object o) {
                initializeMainContainers();
            }
        });
    }



    private void initializeMainContainers() {

        TabPanel mainTabs = new TabPanel();


        TabItem simpleDictionary = new SimpleDictionary("Przyklad wykorzystania słownika");
        mainTabs.add(simpleDictionary);


        TabItem combosTab = new SimpleCombos("Przykład wykorzystania combo boxów");
        mainTabs.add(combosTab);

        TabItem remoteFilter = new RemoteFilterComboDemo("Przykład wykorzystania do filtrowania gridów");
        mainTabs.add(remoteFilter);

        TabItem combosWihtProviders = new ComboBoxWithClearDemo("ComboBoxWithClearDemo & providers since v 1.1.3");
        mainTabs.add(combosWihtProviders);

        viewport = new Viewport();


        viewport.setLayout(new FitLayout());
        viewport.setBorders(false);
        viewport.add(mainTabs);

        RootPanel.get().add(viewport);
        DeferredCommand.addCommand(new Command() {

            public void execute() {
                viewport.layout();
            }
        });


    }
}