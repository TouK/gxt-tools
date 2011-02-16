package pl.touk.wonderfulsecurity.gwt.client.ui.table;

import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.core.client.GWT;

import java.util.Arrays;
import java.util.Map;
import pl.touk.tola.gwt.client.widgets.grid.TolaPagingToolbar;

import pl.touk.tola.gwt.client.widgets.grid.VariablePageSizePagingToolBar;
import pl.touk.tola.gwt.client.widgets.grid.VariablePageSizePagingToolBar.ChoosePageSizeComboBox;
import pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.handlers.*;
import pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.ViewMemberFactory;

/**
 * Ta klasa powinna zastąpic BasePagedList z pakietu pl.touk.wonderfulsecurity.gwt.client.ui.
 * @author rpietra
 * @param <T>
 */
public abstract class BasePagedList<T> extends pl.touk.wonderfulsecurity.gwt.client.ui.BasePagedList {
 
    private static final int INITIAL_RECORDS = 100;

     protected BasePagedList(Class clazz, String uniqueName, String id) {
        this(clazz, uniqueName, id, true);
    }

    protected BasePagedList(Class clazz, String uniqueName, String id, boolean withMemory) {
        super(clazz, uniqueName, withMemory);
        setId(id);
        grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        pagingLoader.setLimit(INITIAL_RECORDS);
        pagingToolbar.setPageSize(INITIAL_RECORDS);
        ((TolaPagingToolbar) pagingToolbar).selectLastOption();
    }

    
    protected void afterGridRowDoubleClicked(GridEvent ge) {
    }

    protected void turnPagingOff() {
        getPagingLoader().setLimit(9999999);
        pagingToolbar.setPageSize(9999999);
        pagingToolbar.setVisible(false);
    }

    public void refreshTable() {
        pagingToolbar.refresh();
    }

    public Grid getGrid() {
        return this.grid;
    }

    public HasSelected getBeanInTableSelected() {
        return ViewMemberFactory.createHasSelected(grid);
    }

    public HasDataLoaded<LoadEvent> getTableReloaded() {
        return ViewMemberFactory.createHasDataLoaded(pagingLoader);
    }

    public HasSelected<SelectionChangedEvent> getSelectionInTableChanged() {
        return ViewMemberFactory.createSelectionChange(grid);
    }

    public T getSelectedBean() {
        BeanModel model = getSelectedBeanModel();
        return (model != null ? (T) model.getBean() : null);
    }

    public BeanModel getSelectedBeanModel() {
        return (BeanModel) grid.getSelectionModel().getSelectedItem();
    }

    public void clickRow(int rowToClick) {
        // GridEvent ge = new GridEvent(grid);
        // ge.setRowIndex(rowToClick);
        GWT.log("clicking?" + grid.getStore().getModels().get(rowToClick));
        try {
            grid.getSelectionModel().setSelection(Arrays.asList(grid.getStore().getModels().get(rowToClick)));
            // grid.fireEvent(Events.RowClick, ge);
        } catch (Exception e) {
            e.printStackTrace();
            GWT.log("exception: " + e + " " + e.getMessage());
        }
        GWT.log("row clicked");
    }

    public boolean selectFirstRow() {
        boolean result = false;
        if (grid.getStore().getCount() > 0) {
            clickRow(0);
            result = true;
        }
        return result;
    }

    protected void prepareValueIfNeeded(Map<String, Object> parameters, String key, Field field) {
        if (field != null && field.getValue() != null) {
            parameters.put(key, field.getValue());
        }
    }
}
