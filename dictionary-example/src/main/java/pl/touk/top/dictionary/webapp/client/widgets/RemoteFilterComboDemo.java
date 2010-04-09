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
package pl.touk.top.dictionary.webapp.client.widgets;

import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.store.ListStore;


import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import java.util.ArrayList;
import java.util.Iterator;

import pl.touk.top.dictionary.webapp.client.DemoBaseModel;
import pl.touk.top.dictionary.impl.gwt.client.ComboFactory;
import pl.touk.top.dictionary.impl.gwt.client.widgets.DictionaryBasedRemoteFilter;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class RemoteFilterComboDemo extends TabItem {

    static ArrayList<ModelData> MODEL_DATA = null;

    static {

        MODEL_DATA = new ArrayList<ModelData>(50);
        for (int i = 0; i < 50; i++) {
            MODEL_DATA.add(new DemoBaseModel());
        }
    }

    public RemoteFilterComboDemo(String text) {
        super(text);
        this.setLayout(new FitLayout());

        MemoryProxy<ArrayList<ModelData>> proxy = new MemoryProxy<ArrayList<ModelData>>(MODEL_DATA);
        ModelReader reader = new ModelReader(){
            @Override
            public ListLoadResult read(Object loadConfig, Object data) {
               BasePagingLoadConfig config = (BasePagingLoadConfig) loadConfig;
                ArrayList<ModelData> oData = (ArrayList<ModelData>) data;
                Integer instanceCount = config.get("instanceCount");

                if (instanceCount != null) {
                    ArrayList copy = new ArrayList(oData);

                    for (Iterator i = copy.iterator(); i.hasNext();) {
                        ModelData md = (ModelData) i.next();
                        if (!instanceCount.equals(md.get("instanceCount"))) {
                            i.remove();
                        }

                    }

                    oData = copy;
                }
                
                int start = config.getOffset();
                int end = config.getOffset() + config.getLimit() >= oData.size() ? config.getOffset() + oData.size() - config.getOffset() : config.getLimit() + config.getOffset();
                ArrayList sublist = new ArrayList(end - start);
                int index = 0;
                for (int i = start; i < end; i++,index++) {
                    sublist.add(oData.get(i));
                }



                return new BasePagingLoadResult(sublist,config.getOffset(),oData.size());
            }
        };

        BasePagingLoader loader = new BasePagingLoader(proxy,reader);
        ListStore store = new ListStore(loader);


        ArrayList columnConfig = new ArrayList();
        columnConfig.add(new ColumnConfig("name", "Nazwa", 150));
        columnConfig.add(new ColumnConfig("instanceCount", "Liczba instancji", 150));

        ColumnModel cm = new ColumnModel(columnConfig);
        Grid grid = new Grid(store,cm);

        PagingToolBar tb = new PagingToolBar(10);
        tb.bind(loader);

        ToolBar filters = new ToolBar();
        DictionaryBasedRemoteFilter comboFilter =  ComboFactory.buildRemoteFilterComboBox("INSTANCE", loader, tb, "instanceCount", DictionaryBasedRemoteFilter.TargetFieldType.INTEGER);
        comboFilter.setEmptyText("Wybierz instancje");
        filters.add(comboFilter);




        ContentPanel cp = new ContentPanel();
        cp.setLayout(new FitLayout());
        cp.setLayoutOnChange(true);
        cp.add(grid);
        cp.setBottomComponent(tb);
        cp.setTopComponent(filters);


        loader.load();

        this.add(cp);
    }
}
