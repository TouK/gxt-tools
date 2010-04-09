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

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.google.gwt.user.client.Window;
import pl.touk.wonderfulsecurity.gwt.client.ui.group.GroupPagedList;
import pl.touk.wonderfulsecurity.gwt.client.ui.permission.PermissionPagedList;
import pl.touk.wonderfulsecurity.gwt.client.ui.role.RolePagedList;
import pl.touk.wonderfulsecurity.gwt.client.ui.user.WsecUserPagedList;
import pl.touk.wonderfulsecurity.core.ClientSecurity;
import pl.touk.wonderfulsecurity.beans.WsecPermission;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class SecurityManagerView extends View {
// ------------------------------ FIELDS ------------------------------

	protected TabPanel mainTabPanel;
	protected TabItem userTableTab;
	protected TabItem groupTableTab;
	protected TabItem roleTableTab;
	protected TabItem permissionTableTab;


// --------------------------- CONSTRUCTORS ---------------------------
	/**
	 * Creates a new view instance.
	 *
	 * @param controller the parent controller
	 */
	public SecurityManagerView(Controller controller) {
		super(controller);
	}

// -------------------------- OTHER METHODS --------------------------
	protected void handleEvent(AppEvent event) {
	}

	protected void initialize() {
		LayoutContainer mainContainer = Registry.get("securityPanel");
		if (!(mainContainer instanceof LayoutContainer)) {
			Window.alert("Brak zarejestrowanego securityPanel!!!\nBy używać konsoli wonderful security należy zarejestrować LayoutContainer od nazwą securityPanel");
		} else {
			mainTabPanel = new TabPanel();
			mainTabPanel.setTabScroll(true);
			mainTabPanel.setAnimScroll(true);
			mainTabPanel.setBorderStyle(false);
			mainTabPanel.setBodyBorder(false);

			Registry.register("MAIN_TAB_PANEL", mainTabPanel);
			BorderLayoutData centerBorderLayoutData = new BorderLayoutData(Style.LayoutRegion.CENTER);
			centerBorderLayoutData.setMargins(new Margins(2));

			userTableTab = new TabItem("Użytkownicy");
			userTableTab.setLayout(new BorderLayout());
			userTableTab.add(new WsecUserPagedList(), centerBorderLayoutData);
            userTableTab.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_USERS_TAB));

			groupTableTab = new TabItem("Grupy użytkowników");
			groupTableTab.setLayout(new BorderLayout());
			groupTableTab.add(new GroupPagedList(), centerBorderLayoutData);
            groupTableTab.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_GROUPS_TAB));


			roleTableTab = new TabItem("Role");
			roleTableTab.setLayout(new BorderLayout());
			roleTableTab.add(new RolePagedList(), centerBorderLayoutData);
            roleTableTab.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_ROLES_TAB));

			permissionTableTab = new TabItem("Uprawnienia");
			permissionTableTab.setLayout(new BorderLayout());
			permissionTableTab.add(new PermissionPagedList(), centerBorderLayoutData);
            permissionTableTab.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_PERMISSIONS_TAB));


			mainTabPanel.add(userTableTab);
			mainTabPanel.add(groupTableTab);
			mainTabPanel.add(roleTableTab);
			mainTabPanel.add(permissionTableTab);
			mainContainer.add(mainTabPanel);
		}
	}
}
