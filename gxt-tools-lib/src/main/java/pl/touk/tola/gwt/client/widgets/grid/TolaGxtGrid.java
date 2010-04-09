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
package pl.touk.tola.gwt.client.widgets.grid;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.user.client.Element;
import java.util.ArrayList;
import java.util.List;
import pl.touk.tola.gwt.client.model.exportcsv.BaseModelToCsvConverter;
import pl.touk.tola.gwt.client.widgets.Clipboard;

public class TolaGxtGrid<M extends ModelData> extends Grid {

    public TolaGxtGrid(ListStore store, ColumnModel cm) {
        super(store, cm);
    }

    @Override
    protected void onRender(Element target, int index) {
        super.onRender(target, index);

        Menu contextMenu = new Menu();
        MenuItem copySelected = new MenuItem("Skopiuj zaznaczone wiersze");
        copySelected.setIconStyle("CopySelectedRows-icon");
        copySelected.addSelectionListener(new SelectionListener<MenuEvent>() {

            @Override
            public void componentSelected(MenuEvent ce) {
                List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
                
                ColumnConfig col;
                for (int i = 0; i < getColumnModel().getColumnCount(); i++) {
                    col = getColumnModel().getColumn(i);
                    if (col instanceof TolaGxtColumnConfig) {
                        TolaGxtColumnConfig rcol = (TolaGxtColumnConfig) col;

                        if (rcol.isParticipateInCsvGeneration()) {
                            columns.add(rcol);
                        }
                    }
                }
                BaseModelToCsvConverter converter = new BaseModelToCsvConverter(getSelectionModel().getSelectedItems());
                String csv = converter.convert(columns.toArray(
                        new ColumnConfig[0]));
                Clipboard.setText(csv);

            }
        });
        contextMenu.add(copySelected);
        setContextMenu(contextMenu);

    }

    protected List<M> getSelectedRows() {
        List<M> result = new ArrayList<M>();

        for (int i = 0; i < getStore().getCount(); i++) {
            result.add((M) getStore().getAt(i));
        }
        return result;
    }
}
