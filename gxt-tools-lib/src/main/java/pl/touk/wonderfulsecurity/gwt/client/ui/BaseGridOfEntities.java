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


import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.MemoryProxy;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import pl.touk.wonderfulsecurity.gwt.client.model.FixedBeanModelReader;

import java.util.ArrayList;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public abstract class BaseGridOfEntities extends ContentPanel {
// ------------------------------ FIELDS ------------------------------

    private final BaseListLoader loader;
    public final EditorGrid grid;
    private final ListStore store;
    private String expandedColumnId;

// --------------------------- CONSTRUCTORS ---------------------------

    protected BaseGridOfEntities(ArrayList beansToDisplay) {
        ColumnModel cm = createColumnModel();
        loader = new BaseListLoader(new MemoryProxy(beansToDisplay), new FixedBeanModelReader());
        store = buildListStore(beansToDisplay, loader);

        grid = new EditorGrid(store, cm);
        grid.setBorders(true);
        grid.setAutoExpandMax(800);
        grid.setAutoExpandColumn(expandedColumnId);
        grid.setAutoHeight(true);
        ComponentPlugin plugin = getGridPlugin();

        if (plugin != null) {
            grid.addPlugin(getGridPlugin());
        }

        

        grid.addListener(Events.RowDoubleClick, new Listener<GridEvent>() {
            public void handleEvent(GridEvent be) {
                ModelData md = be.getGrid().getStore().getAt(be.getRowIndex());

                onGridRowDoubleClick(md);
            }
        });

        String heading = buildHeading();
        this.setLayout(new FitLayout());
        this.setHeading(heading);
        this.setFrame(true);
        this.add(grid);
    }

    protected abstract ColumnModel createColumnModel();

    protected ListStore buildListStore(ArrayList beansToDisplay, BaseListLoader loader) {
        final ListStore ls = new ListStore(loader);
                 

        StoreListener<BeanModel> assignmentChangeListener = buildStoreChangeListener();
        if (assignmentChangeListener != null) {
            ls.addStoreListener(assignmentChangeListener);
        }
        return ls;
    }

    protected StoreListener<BeanModel> buildStoreChangeListener(){return null;}

    protected ComponentPlugin getGridPlugin() {
        return null;
    }

    protected void onGridRowDoubleClick(ModelData ge) {

    }

    protected abstract String buildHeading();

// --------------------- GETTER / SETTER METHODS ---------------------

    public ListStore getStore() {
        return store;
    }

    public String getExpandedColumnId() {
        return expandedColumnId;
    }

    public void setExpandedColumnId(String expandedColumnId) {
        this.expandedColumnId = expandedColumnId;
    }

    // -------------------------- OTHER METHODS --------------------------

    public boolean load() {
        
        return loader.load();
    }
}
