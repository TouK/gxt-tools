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
package pl.touk.example.gwt.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.Style;
import com.google.gwt.user.client.Random;


import java.util.ArrayList;
import java.util.List;

import pl.touk.tola.gwt.client.utils.ListUtils;
import pl.touk.tola.gwt.client.widgets.grid.VariablePageSizePagingToolBar;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class VariablePageSizePagingToolBarExample extends ContentPanel {


    protected static final ArrayList<SampleItem> sampleItems = new ArrayList<SampleItem>(500);



    static {

        for (int i = 0; i < 500; i++) {
            sampleItems.add(new SampleItem());
        }
    }

    protected Grid demoGrid;

    public VariablePageSizePagingToolBarExample() {

        this.setLayoutOnChange(true);
        this.setScrollMode(Style.Scroll.AUTOY);
        this.setLayout(new FitLayout());

        PagingLoader loader = new BasePagingLoader(new MemoryProxy(sampleItems), new DataReader<PagingLoadResult<SampleItem>>(){

            public PagingLoadResult<SampleItem> read(Object loadConfig, Object data) {
                PagingLoadConfig config = (PagingLoadConfig) loadConfig;
                ArrayList<SampleItem> items = (ArrayList<SampleItem>) data;
                int offset = config.getOffset();
                int limit = config.getLimit();
                int size = items.size();

                List<SampleItem> sublist =   ListUtils.subList(items,offset,offset + limit);
                BasePagingLoadResult<SampleItem> result =
                new BasePagingLoadResult<SampleItem>(sublist,offset, size);

                return result;
            }
        });

        ListStore ls = new ListStore(loader);


        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
        columns.add(new ColumnConfig("random1", "random1", 100));
        columns.add(new ColumnConfig("random2", "random2", 100));
        ColumnModel cm = new ColumnModel(columns);
        demoGrid = new Grid(ls,cm);
        demoGrid.setAutoHeight(true);
        
        this.add(demoGrid);

        VariablePageSizePagingToolBar toolBar = new VariablePageSizePagingToolBar("ListForSampleItem");
        toolBar.bind(loader);
        this.setBottomComponent(toolBar);
        this.setTopComponent(new Text("Prezentuje mozliwość zmieniania ilości wynikow na stronie. Wybrana wartość jest zapamiętywana w ciasteczkach przez ComboBoxWithMemory"));

        loader.load();


    }


    static class SampleItem extends BaseModelData{

        SampleItem() {
            set("random1",Random.nextInt());
            set("random2", Random.nextInt());
        }
    }

    
}
