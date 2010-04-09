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
package pl.touk.wonderfulsecurity.gwt.client.ui.role;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import pl.touk.wonderfulsecurity.core.ClientSecurity;
import pl.touk.wonderfulsecurity.gwt.client.WsEvents;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.ROLE_GRID_DOUBLE_CLICK;
import pl.touk.wonderfulsecurity.gwt.client.rpc.ISecurityManagerRpcAsync;
import pl.touk.wonderfulsecurity.gwt.client.rpc.RpcExecutor;
import pl.touk.wonderfulsecurity.gwt.client.ui.BasePagedList;
import pl.touk.wonderfulsecurity.gwt.client.ui.StrippedParameters;
import pl.touk.wonderfulsecurity.beans.WsecPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class RolePagedList extends BasePagedList {

    public RolePagedList() {
        super(null,"RolePagedList");
    }


    protected void afterGridRowDoubleClicked(GridEvent ge) {
        Object selected = ge.getGrid().getSelectionModel().getSelectedItem();
		AppEvent ae = new AppEvent(ROLE_GRID_DOUBLE_CLICK);
        ae.setData("ROLE", ((BeanModel) selected).getBean());
        Dispatcher.get().dispatch(ae);
    }

    protected ColumnModel buildColumnModel() {
       List columnConfig = new ArrayList();
        columnConfig.add(new ColumnConfig("id", "Id", 80));
        columnConfig.add(new ColumnConfig("name", "Nazwa", 220));
        columnConfig.add(new ColumnConfig("description", "Opis", 100));

        this.setExpandedColumnId("description");

        ColumnModel cm = new ColumnModel(columnConfig);
        return cm;
    }

    protected String buildHeading() {
        return "Lista wszystkich dostępnych ról";
    }

    protected RpcProxy constructRpcProxy() {
            RpcProxy proxy = new RpcProxy() {
            protected void load(Object loadConfig, final AsyncCallback asyncCallback) {
                BasePagingLoadConfig plc = ((BasePagingLoadConfig) loadConfig);
                StrippedParameters strippedParams = new StrippedParameters(plc);
                // TODO: change somuser to interface call

                ISecurityManagerRpcAsync securityManagerRpc = ClientSecurity.getAsyncSecurityManager();

                RequestBuilder rb = securityManagerRpc.fetchPagedListWithOverallCount("Some user",strippedParams.getProperties(), plc.getOffset(),plc.getLimit()
                        ,plc.getSortInfo().getSortField(), plc.getSortInfo().getSortDir() == Style.SortDir.DESC,
                        "pl.touk.wonderfulsecurity.beans.WsecRole", asyncCallback);
				RpcExecutor.execute(rb);
            }
        };
        return proxy;
    }

    @Override
    protected Component constructTopComponent() {
        ToolBar toolbar = new ToolBar();

        Button addNewRole = new Button("Dodaj nową rolę");
        addNewRole.setIconStyle("icon-add");
        addNewRole.addSelectionListener(new SelectionListener<ButtonEvent>(){
            @Override
            public void componentSelected(ButtonEvent ce) {
                Dispatcher.get().dispatch(WsEvents.CREATE_NEW_ROLE);
            }

        });
        addNewRole.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_ADD_ROLE_BTN));
        toolbar.add( addNewRole);

        return toolbar;
    }
}
