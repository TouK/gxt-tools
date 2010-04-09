/*
 * Copyright (c) 2006-2007 TouK
 * All rights reserved
 */
package pl.touk.tola.gwt.client.widgets;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.MemoryProxy;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.*;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import pl.touk.tola.gwt.client.widgets.grid.TolaGxtColumnConfig;
import pl.touk.tola.gwt.client.widgets.grid.TolaGxtGrid;

import java.util.*;


/**
 * @author Rafał Pietrasik rpt@touk.pl
 *
 * Migracja i przerobki na potrzeby toli - Lukasz Kucharski lkc@touk.pl
 */
public class MessagesHistoryPanel extends ContentPanel {

    public static enum Type{
        INFO("INFO"),
        WARNING("UWAGA"),
        ERROR("BŁĄD");

        private String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private static DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd/MM/yy HH:mm:ss");
    private TolaGxtGrid messagesGrid;


    public MessagesHistoryPanel() {
        
        Button clearBt = new Button("Wyczyść", new SelectionListener<ButtonEvent>() {

            public void componentSelected(ButtonEvent arg0) {
                messagesGrid.getStore().removeAll();
            }
        });

        clearBt.setId("MHPclearBtn");
        ToolBar topToolBar = new ToolBar();
        topToolBar.add(clearBt);
        this.setBottomComponent(topToolBar);
        this.setHeading("Komunikaty");
        this.setLayout(new FitLayout());
    }

    @Override
    protected void onRender(Element parent, int pos) {
        super.onRender(parent, pos);

        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();


        ColumnConfig col = new TolaGxtColumnConfig("date","Czas", 102, true);
        columns.add(col);

        col = new TolaGxtColumnConfig("type", "Typ", 72, true, false);
        col.setRenderer(new GridCellRenderer() {

            public Object render(ModelData model, String property, ColumnData columnData, int i, int i1, ListStore listStore, Grid grid) {

                String style = "";
                switch ((Type)model.get(property)) {
                    case INFO :
                        style = "Info";
                        break;
                    case WARNING :
                        style = "Warning";
                        break;
                    case ERROR :
                        style = "Error";
                        break;
                }
                return "<span class='historyMessage-" + style + "'>" + model.get(property) + "</span>";
            }

        });
        columns.add(col);

        col = new TolaGxtColumnConfig("message", "Treść", 550, true, false);
        col.setRenderer(new GridCellRenderer() {

            public Object render(ModelData model, String property, ColumnData columnData, int i, int i1, ListStore listStore, Grid grid) {

                return "<span class='historyMessage-Body'>" + model.get(property) + "</span>";
            }

        });

        columns.add(col);
        ColumnModel cm = new ColumnModel(columns);

        messagesGrid = new TolaGxtGrid(new ListStore(new BaseListLoader(new MemoryProxy(new ArrayList()))),cm);

        messagesGrid.setAutoExpandColumn("message");
        messagesGrid.setAutoExpandMax(2000);

        this.add(messagesGrid);
    }

    public void addMessage(String  message, Type type) {


        Map<String,Object> data = new HashMap();
        data.put("date", DATE_FORMAT.format(new Date()));
        data.put("type", type);
        data.put("message", message);


        messagesGrid.getStore().insert(new BaseModel(data),0);
    }
}
