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
package pl.touk.wonderfulsecurity.gwt.client.ui.group;

import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.TabItem;
import pl.touk.wonderfulsecurity.beans.RoleView;
import pl.touk.wonderfulsecurity.beans.WsecGroup;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.*;
import pl.touk.wonderfulsecurity.gwt.client.ui.BaseCreateEditView;
import pl.touk.wonderfulsecurity.gwt.client.ui.BaseEditTabItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import pl.touk.wonderfulsecurity.beans.PermissionView;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class CreateEditGroupView extends BaseCreateEditView {
// ------------------------------ FIELDS ------------------------------

	protected final ArrayList<RoleView> availableAndAssignedRoles = new ArrayList<RoleView>();
	protected final ArrayList<PermissionView> availableAndAssignedPermissions = new ArrayList<PermissionView>();
	protected final ArrayList<PermissionView> inheritedPermissionsFromRoles = new ArrayList<PermissionView>();
	private TabItem newGroupTab;
	private final Map<WsecGroup, CreateEditGroupTab> tabMap = new HashMap<WsecGroup, CreateEditGroupTab>();

// --------------------------- CONSTRUCTORS ---------------------------
	/**
	 * Creates a new view instance.
	 *
	 * @param controller the parent controller
	 */
	public CreateEditGroupView(Controller controller) {
		super(controller);
	}

// --------------------- GETTER / SETTER METHODS ---------------------
	public ArrayList<RoleView> getAvailableAndAssignedRoles() {
		return availableAndAssignedRoles;
	}

// -------------------------- OTHER METHODS --------------------------
	protected String getTabName() {
		return "Edycja grupy";
	}

	protected void handleEvent(AppEvent event) {
        EventType type = event.getType();

			if(type == START_GROUP_EDIT){
				doStartGroupEdit(event);
            } else if (type == CREATE_NEW_GROUP){
				doCreateNewGroup(event);
            }

	}

	public void closeNewGroupTab() {
		if (newGroupTab != null) {
			newGroupTab.close();
		}
	}

	public void doCreateNewGroup(AppEvent event) {
		newGroupTab = new BaseEditTabItem("Nowa grupa");
		newGroupTab.add(new GroupDetailsForm(true));
		attachTabItemToMainPanel(newGroupTab);

	}

	private void doStartGroupEdit(AppEvent event) {

		WsecGroup group = event.getData("GROUP");

		ArrayList<RoleView> roles = event.getData("ROLES");
		ArrayList<PermissionView> permissions = event.getData("PERMISSIONS");
		ArrayList<PermissionView> inheritedPermissions = event.getData("PERMISSIONS_INHERITED_FROM_ROLES");

		CreateEditGroupTab gt = tabMap.get(group);

		if (gt != null) {
			gt.editForm.getModelData().updateModel(group, roles, permissions, inheritedPermissions);
			gt.editForm.doRedisplayGroupData();
			setActiveTab(gt);
		} else {
			CreateEditGroupTabData groupTabData = new CreateEditGroupTabData(group, roles, permissions, inheritedPermissions);
			GroupEditForm editForm = new GroupEditForm(groupTabData);
			gt = new CreateEditGroupTab("Edycja grupy " + group.getName(), editForm);
			gt.add(editForm);

			editForm.doRedisplayGroupData();
			tabMap.put(group, gt);
			attachTabItemToMainPanel(gt);
		}

	}
	
	class CreateEditGroupTab extends BaseEditTabItem {

		GroupEditForm editForm;

		CreateEditGroupTab(String text, GroupEditForm editForm) {
			super(text);
			this.editForm = editForm;
		}

		@Override
		protected void onTabClose(TabPanelEvent be) {
			super.onTabClose(be);
			tabMap.remove(editForm.getModelData().getGroup());
		}
	}

	static class CreateEditGroupTabData {

		private WsecGroup group;
		final ArrayList<PermissionView> permissions;
		final ArrayList<RoleView> roles;
		final ArrayList<PermissionView> rolePermissionsInherited;

		CreateEditGroupTabData(WsecGroup group, ArrayList<RoleView> roles, ArrayList<PermissionView> permissions, ArrayList<PermissionView> rolePermissionsInherited) {
			this.group = group;
			this.roles = roles;
			this.permissions = permissions;
			this.rolePermissionsInherited = rolePermissionsInherited;
		}

		public void updateModel(WsecGroup group, ArrayList<RoleView> roles, ArrayList<PermissionView> permissions, ArrayList<PermissionView> rolePermissionsInherited) {
			this.group = group;
			this.roles.clear();
			this.permissions.clear();
			this.rolePermissionsInherited.clear();
			this.roles.addAll(roles);
			this.permissions.addAll(permissions);
			this.rolePermissionsInherited.addAll(rolePermissionsInherited);
		}

		public WsecGroup getGroup() {
			return group;
		}

		public void setGroup(WsecGroup group) {
			this.group = group;
		}

		@Override
		public int hashCode() {
			return group.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return group.equals(obj);
		}

	}
}
