/*
 * Copyright (c) 2006-2007 TouK
 * All rights reserved
 */
package pl.touk.tola.gwt.client.widgets.file;


import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;
import pl.touk.tola.gwt.client.model.file.FileDescriptorGxt;

/**
 * Grid storing FileDescriptorGxt
 * Handles double click on row and downloads selected file from server.
 * 
 * 
 * 
 * TODO: move constants parameters to single common class
 * 
 * @author Rafał Pietrasik rpt@touk.pl
 */
public class FileStatusesGrid extends LayoutContainer {
    
    static private final String CONTROLLER_DOWNLOAD = "download=true";
    
    private final ListStore<FileDescriptorGxt> store;
    private final String controllerUrl;

    /**
     * Creates container with grid.
     * Standard configuration - controller url is set to: <app_url>/fileUploadDownload.do
     */
    public FileStatusesGrid() {
        this("fileUploadDownload.do");
    }

    /**
     * 
     * @param externalDownload if true creates external link to file
     */
    public FileStatusesGrid(final String controllerUrl) {

        this.controllerUrl = controllerUrl;

        this.setLayout(new FitLayout());

    
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        ColumnConfig column = new ColumnConfig();
        column.setId(FileDescriptorGxt.FILE_ID);
        column.setHeader("Id pliku");
        column.setWidth(200);
        configs.add(column);

        column = new ColumnConfig();
        column.setId(FileDescriptorGxt.FILE_NAME);
        column.setHeader("Nazwa pliku");
        column.setWidth(100);
        configs.add(column);

        column = new ColumnConfig();
        column.setId(FileDescriptorGxt.FILE_SIZE);
        column.setHeader("Rozmiar pliku");
        column.setWidth(100);
        configs.add(column);

        store = new ListStore<FileDescriptorGxt>();

        ColumnModel cm = new ColumnModel(configs);

        ContentPanel cp = new ContentPanel();
        cp.setBodyBorder(false);
        cp.setHeading("Pliki");
        cp.setButtonAlign(HorizontalAlignment.CENTER);
        cp.setLayout(new FitLayout());

        final Grid<FileDescriptorGxt> grid = new Grid<FileDescriptorGxt>(store, cm);
        grid.setStyleAttribute("borderTop", "none");
        grid.setAutoExpandColumn(FileDescriptorGxt.FILE_NAME);
        grid.setBorders(true);

  
        grid.addListener(Events.RowDoubleClick, new Listener<GridEvent>() {
            public void handleEvent(GridEvent ge) {
                FileDescriptorGxt ufb = grid.getSelectionModel().getSelectedItem();
                GWT.log("downloading" + ufb.getFileName(), null);
                download(GWT.getModuleBaseURL() + controllerUrl+"?" + CONTROLLER_DOWNLOAD+"&fileId="+ufb.getFileId());
            }});
      
        cp.add(grid);
        this.add(cp);
    }

    /**
     * Adds file to grid.
     * 
     * @param ufb file to add
     */
    public void addFile(FileDescriptorGxt ufb) {
        store.add(ufb);
    }
    
    
    private static native void download(String url) /*-{
    	$wnd.open(url,"DownloadWindow","menubar=no,toolbar=no,location=no,status=no,resizable=no,status=no,width=644,height=750");
  	}-*/;
}
