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
package pl.touk.wonderfulsecurity.gwt.client.ui.user;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.form.PropertyEditor;
import com.extjs.gxt.ui.client.widget.form.ListModelPropertyEditor;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.Window;
import pl.touk.tola.gwt.client.widgets.BasicRemoteFilterField;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.core.ClientSecurity;
import pl.touk.wonderfulsecurity.gwt.client.WsEvents;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.USER_GRID_DOUBLE_CLICK;
import pl.touk.wonderfulsecurity.gwt.client.rpc.ISecurityManagerRpcAsync;
import pl.touk.wonderfulsecurity.gwt.client.rpc.RpcExecutor;
import pl.touk.wonderfulsecurity.gwt.client.ui.BasePagedList;
import pl.touk.wonderfulsecurity.gwt.client.ui.StrippedParameters;
import pl.touk.top.dictionary.impl.gwt.client.widgets.DictionaryBasedRemoteFilter;
import pl.touk.top.dictionary.impl.gwt.client.ComboFactory;
import pl.touk.top.dictionary.model.domain.DictionaryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class WsecUserPagedList extends BasePagedList {
// --------------------------- CONSTRUCTORS ---------------------------

    public WsecUserPagedList() {
        super(WsecUser.class,"WsecUserPagedList");
    }

// -------------------------- OTHER METHODS --------------------------

    protected void afterGridRowDoubleClicked(GridEvent ge) {
        Object selected = ge.getGrid().getSelectionModel().getSelectedItem();
        AppEvent ae = new AppEvent(USER_GRID_DOUBLE_CLICK);
        ae.setData("USER", ((BeanModel) selected).getBean());
        Dispatcher.get().dispatch(ae);
    }

    protected ColumnModel buildColumnModel() {

        List columnConfig = new ArrayList();
        // id raczej nikomu nie jest potrzebne wiec zakomentowalem
        
//        columnConfig.add(new ColumnConfig("id", "Id", 80));
        columnConfig.add(new ColumnConfig("login", "Login", 100));
        columnConfig.add(new ColumnConfig("jobTitle", "Stanowisko", 100));
        columnConfig.add(new ColumnConfig("firstName", "Imię", 100));
        columnConfig.add(new ColumnConfig("lastName", "Nazwisko", 100));
        columnConfig.add(new ColumnConfig("street", "Ulica", 100));
        columnConfig.add(new ColumnConfig("city", "miasto", 100));
        columnConfig.add(new ColumnConfig("emailAddress", "Email", 100));
        columnConfig.add(new CheckColumnConfig("enabled", "Aktywny", 60));

        this.setExpandedColumnId("emailAddress");

        ColumnModel cm = new ColumnModel(columnConfig);
        return cm;
    }

    protected String buildHeading() {
        return "Lista wszystkich użytkowników";
    }

    protected RpcProxy constructRpcProxy() {
        RpcProxy proxy = new RpcProxy() {
            protected void load(Object loadConfig, final AsyncCallback asyncCallback) {
                BasePagingLoadConfig plc = ((BasePagingLoadConfig) loadConfig);
                StrippedParameters strippedParams = new StrippedParameters(plc);
                // TODO: change somuser to interface call

                ISecurityManagerRpcAsync securityManagerRpc = ClientSecurity.getAsyncSecurityManager();

                RequestBuilder rb = securityManagerRpc.fetchPagedListWithOverallCount("Some user",  strippedParams.getProperties(), plc.getOffset(),
                        plc.getLimit(), plc.getSortInfo().getSortField(), plc.getSortInfo().getSortDir() == Style.SortDir.DESC,
                        "pl.touk.wonderfulsecurity.beans.WsecUser", asyncCallback);
				RpcExecutor.execute(rb);
            }
        };
        return proxy;
    }

    protected Component constructTopComponent() {

        ToolBar tb = new ToolBar();

        BasicRemoteFilterField loginFilterField = new BasicRemoteFilterField(super.pagingToolbar, super.pagingLoader, "login@#LIKE");
        loginFilterField.setEmptyText("Filtruj loginy");
        tb.add(loginFilterField);

        BasicRemoteFilterField lastnameFilterField = new BasicRemoteFilterField(super.pagingToolbar, super.pagingLoader, "lastName@#LIKE");
        lastnameFilterField.setEmptyText("Filtruj nazwiska");
        tb.add(lastnameFilterField);

        BasicRemoteFilterField emailFilterField = new BasicRemoteFilterField(super.pagingToolbar, super.pagingLoader, "emailAddress@#LIKE");
        emailFilterField.setEmptyText("Filtruj email");
        tb.add(emailFilterField);

        DictionaryBasedRemoteFilter groupFilter =  ComboFactory.buildRemoteFilterComboBox("ALL_GROUPS", super.pagingLoader, super.pagingToolbar, "groups.id", DictionaryBasedRemoteFilter.TargetFieldType.LONG);
        groupFilter.setEmptyText("Filtruj grupę");
        tb.add(groupFilter);


        tb.add(new SeparatorToolItem());

        Button addNewUser = new Button("Dodaj nowego użytkownika");
//        addNewUser.setStyleName("icon-add");
        addNewUser.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                Dispatcher.get().dispatch(WsEvents.CREATE_NEW_USER);
            }
        });
        addNewUser.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_ADD_USR_BTN));
        tb.add(addNewUser);

        return tb;
    }
/*
    protected ToolBar buildFilterToolbar() {
        ToolBar tb = new ToolBar();

        BasicRemoteFilterField loginFilterField = new BasicRemoteFilterField(super.pagingToolbar, super.pagingLoader, "user");
        loginFilterField.setEmptyText("Filtruj Login");
        
        
        tb.add(new AdapterToolItem(loginFilterField));
        return tb ;
    }
*/

 }
