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

import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.TabItem;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.*;
import pl.touk.wonderfulsecurity.gwt.client.ui.BaseCreateEditView;
import pl.touk.wonderfulsecurity.gwt.client.ui.BaseEditTabItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import pl.touk.wonderfulsecurity.beans.PermissionView;
import pl.touk.wonderfulsecurity.beans.WsecRole;

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
public class CreateEditRoleView extends BaseCreateEditView {
// ------------------------------ FIELDS ------------------------------

//	protected WsecRole role;
	protected final ArrayList<PermissionView> availableAndAssignedPermissions = new ArrayList<PermissionView>();
	private TabItem newRole;
	private TabItem editRoleTab;
	private final Map<WsecRole, CreateEditRoleTab> tabMap = new HashMap<WsecRole, CreateEditRoleTab>();
// --------------------------- CONSTRUCTORS ---------------------------

	/**
	 * Creates a new view instance.
	 *
	 * @param controller the parent controller
	 */
	public CreateEditRoleView(Controller controller) {
		super(controller);
	}

// --------------------- GETTER / SETTER METHODS ---------------------
//	public WsecRole getRole() {
//		return role;
//	}

//	public void setRole(WsecRole role) {
//		this.role = role;
//	}

	protected String getTabName() {
		return "Edycja roli";
	}
// -------------------------- OTHER METHODS --------------------------

	protected void handleEvent(AppEvent event) {
        EventType type = event.getType();

			if(type ==  START_ROLE_EDIT){
				doStartRoleEdit(event);
            } else if (type == CREATE_NEW_ROLE){
				doCreateNewRole(event);
            }

	}

	private void doCreateNewRole(AppEvent event) {
		newRole = new BaseEditTabItem("Nowa rola");
		newRole.add(new RoleDetailsForm(true));
		attachTabItemToMainPanel(newRole);

	}

	public void closeNewRoleTab() {
		if (newRole != null) {
			newRole.close();
		}
	}

	private void doStartRoleEdit(AppEvent event) {
		WsecRole role = event.getData("ROLE");

		ArrayList<PermissionView> permissions = event.getData("PERMISSIONS");

		CreateEditRoleTab rt = tabMap.get(role);

		if (rt != null) {
			rt.editForm.getRoleTabData().updateModel(role, permissions);
			rt.editForm.doRedisplayRoleData();
			setActiveTab(rt);
		} else {
			CreateEditRoleTabData roleTabData = new CreateEditRoleTabData(role, permissions);
			RoleEditForm editForm = new RoleEditForm(roleTabData);
			rt = new CreateEditRoleTab("Edycja roli " + role.getName(), editForm);
			rt.add(editForm);

			editForm.doRedisplayRoleData();
			tabMap.put(role, rt);
			attachTabItemToMainPanel(rt);
		}
	}

	class CreateEditRoleTab extends BaseEditTabItem {

		RoleEditForm editForm;

		CreateEditRoleTab(String text, RoleEditForm editForm) {
			super(text);
			this.editForm = editForm;
		}

		@Override
		protected void onTabClose(TabPanelEvent be) {
			super.onTabClose(be);
			tabMap.remove(editForm.getRoleTabData().getRole());
		}
	}

	static class CreateEditRoleTabData {

		private WsecRole role;
		final ArrayList<PermissionView> permissions;

		CreateEditRoleTabData(WsecRole role, ArrayList<PermissionView> permissions) {
			this.role = role;
			this.permissions = permissions;
		}

		public void updateModel(WsecRole role, ArrayList<PermissionView> permissions) {
			this.role = role;
			this.permissions.clear();
			this.permissions.addAll(permissions);
		}

		public WsecRole getRole() {
			return role;
		}

		public void setRole(WsecRole role) {
			this.role = role;
		}

		@Override
		public int hashCode() {
			return role.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return role.equals(obj);
		}

	}
}

