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
package pl.touk.example.gwt.client;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import pl.touk.example.gwt.client.rpc.SecurityTestService;
import pl.touk.example.gwt.client.rpc.SecurityTestServiceAsync;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import pl.touk.wonderfulsecurity.core.ClientSecurity;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.INIT_SECURITY_CONSOLE;
import pl.touk.wonderfulsecurity.gwt.client.rpc.ISecurityManagerRpcAsync;
import pl.touk.wonderfulsecurity.gwt.client.ui.SecurityManagerController;
import pl.touk.top.dictionary.impl.gwt.client.ClientDictionary;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ExampleEntryPoint implements EntryPoint {
// ------------------------------ FIELDS ------------------------------

    private SecurityTestServiceAsync testService;
	private ISecurityManagerRpcAsync securityManagerRpcAsync;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface EntryPoint ---------------------

    public void onModuleLoad() {
        testService = (SecurityTestServiceAsync) GWT.create(SecurityTestService.class);
        ServiceDefTarget testServiceEndpoint = (ServiceDefTarget) testService;
        testServiceEndpoint.setServiceEntryPoint(GWT.getHostPageBaseURL() + "secure/rpc/securedTestService.do");


        // First thing you always do is you initialize security framework like this:
        ClientSecurity.initialize(GWT.getHostPageBaseURL() + "secure/rpc/wsecurityManager.do", new AsyncCallback() {
            public void onFailure(Throwable throwable) {
                Window.alert("Cannot initialize security framework");
            }

            public void onSuccess(Object o) {
                initializeDictionaryService();
            }
        });
    }

    protected void initializeDictionaryService() {

        ClientDictionary.initialize("secure/rpc/dictionaryService.do", new AsyncCallback() {


            public void onSuccess(Object result) {
                // after its done you can start setting up rest of your application
                initializeExample();
                
            }

            
            public void onFailure(Throwable caught) {
                GWT.log("",caught);
                Window.alert("Cannot initialize dictionary framework");
            }
        });


    }


// -------------------------- OTHER METHODS --------------------------

    protected void initializeExample() {
        // you can do this only after asynchronous initialize method of ClientSecurity completed

        // this is how you fetch logged in user
        WsecUser loggedInUser = ClientSecurity.getLoggedInUser();


        // collect permissions just to show them to user at startup
        showPermissions(loggedInUser);

        // you always set up viewport in gxt first
        Viewport viewport = new Viewport();
        viewport.setLayout(new BorderLayout());
        viewport.setStyleAttribute("background", "none");
        viewport.setLayoutOnChange(true);
        RootPanel.get().add(viewport);

        // add few demo buttos which trigger secured server side actions
        VerticalPanel tp = addDemoButtons();

        LayoutContainer sp = new LayoutContainer();
        sp.setLayout(new FitLayout());
        sp.setBorders(true);
        sp.setLayoutOnChange(true);

        BorderLayoutData topBorderLayoutData = new BorderLayoutData(Style.LayoutRegion.NORTH);
        topBorderLayoutData.setSize(160);
        BorderLayoutData centerBorderLayoutData = new BorderLayoutData(Style.LayoutRegion.CENTER);

        viewport.add(tp, topBorderLayoutData);
        viewport.add(sp, centerBorderLayoutData);

        // THIS IS IMPORTANT:
        // When security management console is initialized (after you dispatch INIT_SECURITY_CONSOLE event) framework will look in
        // Registry for object keyed "securityPanel" if this object is of type Container security framework console will attach
        // itself to this panel. So to attach management console to any panel you like you register this panel in Registry under name
        // securityPanel and disptach INIT_SECURITY_CONSOLE event
        Registry.register("securityPanel", sp);


        // this is important too. Set up Dispatcher to notify SecurityManagerController
        Dispatcher.get().addController(new SecurityManagerController());
        Dispatcher.get().dispatch(INIT_SECURITY_CONSOLE);
    }

    private void showPermissions(WsecUser loggedInUser) {
        StringBuilder sb = new StringBuilder();
        sb.append("Logged in user is: ").append(loggedInUser.getLogin());
        sb.append(" Permissions:");

        for (WsecPermission perm : loggedInUser.getAllPermissions()) {
            sb.append(" " + perm.toString());
        }

        Window.alert("" + sb);
    }

    private VerticalPanel addDemoButtons() {
        VerticalPanel tp = new VerticalPanel();
        tp.setSpacing(5);

        tp.add(new Html("<a href=logout.jsp>Wyloguj sie aby zobaczyć zmiany w uprawnieniach</a>"));
        Button perm13 = new Button("Enable this button by granting PERMISSION_NO13");

        // This is how you check permissions on client side
        perm13.setEnabled(ClientSecurity.hasPermission("PERMISSION_NO13"));
        tp.add(perm13);

        Button perm19 = new Button("Enable this button by granting PERMISSION_NO19");
        // This is how you check permissions on client side
        perm19.setEnabled(ClientSecurity.hasPermission("PERMISSION_NO19"));
        tp.add(perm19);


        Button triggerAllowedServerOperation = new Button("This button triggers server side service secured with PERMISSION_NO15 which by default you have");

        triggerAllowedServerOperation.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent buttonEvent) {
                testService.callSecuredMethodWhichICanExecute(new AsyncCallback() {
                    public void onFailure(Throwable throwable) {
                        Window.alert("Failure");
                    }

                    public void onSuccess(Object o) {
                        Window.alert("Success");
                    }
                });
            }
        });

        Button triggerNotAllowedServerOperation = new Button("This button triggers server side service secured with PERMISSION_NO2 which you do not have (see server error)");

        triggerNotAllowedServerOperation.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent buttonEvent) {
                testService.callSecureMethodWhichIHaveNoRightToExecute(new AsyncCallback() {
                    public void onFailure(Throwable throwable) {
                        Window.alert("Failure");
                    }

                    public void onSuccess(Object o) {
                        Window.alert("Success");
                    }
                });
            }
        });

        Button triggerManuallyNotAllowed = new Button("This button triggers server side service manually secured with PERMISSION_NO2 which you do not have (see server error)");

        triggerManuallyNotAllowed.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent buttonEvent) {
                testService.callManuallySecuredMethodWhichICanNotExecute(new AsyncCallback() {
                    public void onFailure(Throwable throwable) {
                        Window.alert("Failure");
                    }

                    public void onSuccess(Object o) {
                        Window.alert("Success");
                    }
                });
            }
        });


        tp.add(triggerAllowedServerOperation);
        tp.add(triggerNotAllowedServerOperation);
        tp.add(triggerManuallyNotAllowed);
        return tp;
    }

}
