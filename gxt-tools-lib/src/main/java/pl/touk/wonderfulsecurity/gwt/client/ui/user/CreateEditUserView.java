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

import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.TabItem;
import pl.touk.wonderfulsecurity.beans.PermissionView;
import pl.touk.wonderfulsecurity.beans.RoleView;
import pl.touk.wonderfulsecurity.beans.WsecGroup;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.CREATE_NEW_USER;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.START_USER_EDIT;
import pl.touk.wonderfulsecurity.gwt.client.ui.BaseCreateEditView;
import pl.touk.wonderfulsecurity.gwt.client.ui.BaseEditTabItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.beans.WsecRole;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class CreateEditUserView extends BaseCreateEditView {
// ------------------------------ FIELDS ------------------------------

	private final Map<WsecUser, CreateEditUserTab> tabMap = new HashMap<WsecUser, CreateEditUserTab>();
	private TabItem newUserStep1Tab;

// --------------------------- CONSTRUCTORS ---------------------------
	/**
	 * Creates a new view instance.
	 *
	 * @param controller the parent controller
	 */
	public CreateEditUserView(Controller controller) {
		super(controller);
	}

// -------------------------- OTHER METHODS --------------------------
    protected void handleEvent(AppEvent event) {
        EventType type = event.getType();
         if (type == START_USER_EDIT) {
            doStartUserEdit(event);
        } else if (type == CREATE_NEW_USER) {
            doCreateNewUser(event);
        }

    }

	public void closeNewUserSetp1Tab() {
		if (newUserStep1Tab == null) {
			throw new IllegalStateException("Nie można zamknąć nieistniejącej zakladki");
		}

		newUserStep1Tab.close();
	}

	private void doCreateNewUser(AppEvent event) {
		newUserStep1Tab = new BaseEditTabItem("Nowy użytkownik");
		UserDetailsForm uForm = new UserDetailsForm(true);
		newUserStep1Tab.add(uForm);
		attachTabItemToMainPanel(newUserStep1Tab);
	}

	private void doStartUserEdit(AppEvent event) {
		WsecUser user = (WsecUser) event.getData("USER");
		ArrayList<WsecGroup> groups = (ArrayList<WsecGroup>) event.getData("GROUPS");
		ArrayList<RoleView> roles = (ArrayList<RoleView>) event.getData("ROLES");
		ArrayList<RoleView> rolesInherited = (ArrayList<RoleView>) event.getData("ROLES_INHERITED");
		ArrayList<PermissionView> permissions = (ArrayList<PermissionView>) event.getData("PERMISSIONS");
		ArrayList<PermissionView> groupPermissionsInherited = (ArrayList<PermissionView>) event.getData("PERMISSIONS_INHERITED_FROM_GROUPS");
		ArrayList<PermissionView> rolePermissionsInherited = (ArrayList<PermissionView>) event.getData("PERMISSIONS_INHERITED_FROM_ROLES");
		ArrayList<PermissionView> permissionsInheritedFromInheritedRoles = (ArrayList<PermissionView>) event.getData("PERMISSIONS_INHERITED_FROM_INHERITED_ROLES");

		// test if there is such tab in cache
		CreateEditUserTab ti = tabMap.get(user);
		if (ti != null) {
			ti.editForm.getModelData().updateModel(user, rolesInherited, roles, groups, permissions, groupPermissionsInherited, rolePermissionsInherited, permissionsInheritedFromInheritedRoles);
			ti.editForm.doRedisplayUserData();
			setActiveTab(ti);
		} else {
			CreateEditUserTabData userTabData = new CreateEditUserTabData(user, rolesInherited, roles, groups, permissions, groupPermissionsInherited, rolePermissionsInherited, permissionsInheritedFromInheritedRoles);
			WsecUserEditForm edit = new WsecUserEditForm(userTabData);
			ti = new CreateEditUserTab("Edycja użytkownika " + user.getLogin(), edit);
//            ti.setScrollMode(Style.Scroll.AUTO);
//            ti.setScrollMode(Style.Scroll.AUTO);
			ti.add(edit);

			edit.doRedisplayUserData();
			tabMap.put(user, ti);
			attachTabItemToMainPanel(ti);
		}
	}

// -------------------------- INNER CLASSES --------------------------
	class CreateEditUserTab extends BaseEditTabItem {

		WsecUserEditForm editForm;

		CreateEditUserTab(String text, WsecUserEditForm editForm) {
			super(text);
			this.editForm = editForm;
		}

		protected void onTabClose(TabPanelEvent be) {
			super.onTabClose(be);
			tabMap.remove(editForm.getModelData().getUser());
		}
	}

	static class CreateEditUserTabData {
		final ArrayList groups;
		private WsecUser user;
		final ArrayList<RoleView> roles;
		final ArrayList<RoleView> rolesInherited;
		final ArrayList<PermissionView> permissions;
		final ArrayList<PermissionView> groupPermissionsInherited;
		final ArrayList<PermissionView> rolePermissionsInherited;
		final ArrayList<PermissionView> permissionsInheritedFromInheritedRoles;

		CreateEditUserTabData(WsecUser user, ArrayList<RoleView> rolesInherited, ArrayList<RoleView> roles, ArrayList groups, ArrayList<PermissionView> permissions, ArrayList<PermissionView> groupPermissionsInherited, ArrayList<PermissionView> rolePermissionsInherited, ArrayList<PermissionView> permissionsInheritedFromInheritedRoles) {
			this.user = user;
			this.rolesInherited = rolesInherited;
			this.roles = roles;
			this.groups = groups;
			this.permissions = permissions;
			this.groupPermissionsInherited = groupPermissionsInherited;
			this.rolePermissionsInherited = rolePermissionsInherited;
			this.permissionsInheritedFromInheritedRoles = permissionsInheritedFromInheritedRoles;
		}

		public void updateModel(WsecUser user, ArrayList<RoleView> rolesInherited, ArrayList<RoleView> roles, ArrayList groups, ArrayList<PermissionView> permissions, ArrayList<PermissionView> groupPermissionsInherited, ArrayList<PermissionView> rolePermissionsInherited, ArrayList<PermissionView> permissionsInheritedFromInheritedRoles) {
			this.user = user;
			this.groups.clear();
			this.roles.clear();
			this.rolesInherited.clear();
			this.permissions.clear();
			this.groupPermissionsInherited.clear();
			this.rolePermissionsInherited.clear();
			this.permissionsInheritedFromInheritedRoles.clear();
			this.groups.addAll(groups);
			this.roles.addAll(roles);
			this.rolesInherited.addAll(rolesInherited);
			this.permissions.addAll(permissions);
			this.groupPermissionsInherited.addAll(groupPermissionsInherited);
			this.rolePermissionsInherited.addAll(rolePermissionsInherited);
			this.permissionsInheritedFromInheritedRoles.addAll(permissionsInheritedFromInheritedRoles);
		}

		public WsecUser getUser() {
			return user;
		}

		public void setUser(WsecUser user) {
			this.user = user;
		}

		public int hashCode() {
			return user.hashCode();
		}

		public boolean equals(Object obj) {
			return user.equals(obj);
		}
	}
}
