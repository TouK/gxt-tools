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

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import pl.touk.wonderfulsecurity.beans.PermissionView;
import pl.touk.wonderfulsecurity.beans.RoleView;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.gwt.client.ui.group.CreateEditGroupView.CreateEditGroupTabData;
import pl.touk.wonderfulsecurity.gwt.client.ui.permission.InheritedPermissionsFromRoleGrid;
import pl.touk.wonderfulsecurity.gwt.client.ui.role.BaseAssignableRoleGrid;
import pl.touk.wonderfulsecurity.core.ClientSecurity;

import java.util.ArrayList;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class GroupEditForm extends LayoutContainer {
// --------------------------- CONSTRUCTORS ---------------------------

	protected AssignableToGroupRoleGrid assignableToGroupRoleGrid;
	protected AssignableToGroupPermissionGrid assignableToGroupPermissionGrid;
	protected InheritedPermissionsFromRoleGrid inheritedPermissionsFromRolesGrid;
	protected GroupDetailsForm groupDetailsForm;
	protected CreateEditGroupView.CreateEditGroupTabData groupTabData;

	public GroupEditForm() {
	}

	public GroupEditForm(CreateEditGroupTabData groupTabData) {
		this.groupTabData = groupTabData;
		this.assignableToGroupRoleGrid = new AssignableToGroupRoleGrid(this.groupTabData.roles);
		this.assignableToGroupPermissionGrid = new AssignableToGroupPermissionGrid(this.groupTabData.permissions);
		this.inheritedPermissionsFromRolesGrid = new InheritedPermissionsFromRoleGrid(this.groupTabData.rolePermissionsInherited);
		this.groupDetailsForm = new GroupDetailsForm(false);

		this.setLayout(new RowLayout());
		RowData topRowData = new RowData(1, -1, new Margins(2, 2, 0, 2));
		RowData middleRowData = new RowData(1, -1, new Margins(2));
		RowData bottomRowData = new RowData(1, -1, new Margins(0, 2, 2, 2));

		this.add(groupDetailsForm, topRowData);
		this.add(assignableToGroupRoleGrid, middleRowData);
		this.add(assignableToGroupPermissionGrid, middleRowData);
		this.add(inheritedPermissionsFromRolesGrid, bottomRowData);
	}

	public void doRedisplayGroupData() {
		assignableToGroupPermissionGrid.load();
		assignableToGroupRoleGrid.load();
		inheritedPermissionsFromRolesGrid.load();
		groupDetailsForm.setGroupModelData(groupTabData.getGroup());
	}

	public CreateEditGroupTabData getModelData() {
		return groupTabData;
	}

	public void setModelData(CreateEditGroupTabData modelData) {
		this.groupTabData = modelData;
	}

	protected ListStore buildListStore(ArrayList beansToDisplay, BaseListLoader loader) {
		final ListStore ls = new ListStore(loader);


		StoreListener<BeanModel> assignmentChangeListener = buildStoreChangeListener();
		if (assignmentChangeListener != null) {
			ls.addStoreListener(assignmentChangeListener);
		}
		return ls;
	}

	private StoreListener<BeanModel> buildStoreChangeListener() {
		throw new UnsupportedOperationException("Not yet implemented");
	}


// -------------------------- OTHER METHODS --------------------------

// -------------------------- INNER CLASSES --------------------------
	/**
	 * @author Lukasz Kucharski - lkc@touk.pl
	 */
	class AssignableToGroupRoleGrid extends BaseAssignableRoleGrid {

		public AssignableToGroupRoleGrid(ArrayList beansToDisplay) {
			super(beansToDisplay);
		}

    @Override
    protected void beforeRender() {
        this.getSaveButton().setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_CHNG_ROLE_4GRP));
    }

    protected String buildHeading() {
			return "Role przypisane do tej grupy";
		}

		protected StoreListener<BeanModel> buildStoreChangeListener() {
			StoreListener<BeanModel> assignmentChangeListener = new StoreListener<BeanModel>() {

				public void storeUpdate(StoreEvent<BeanModel> se) {
					if (se.getOperation() == com.extjs.gxt.ui.client.store.Record.RecordUpdate.COMMIT) {
						RoleView assignedRole = (RoleView) se.getModel().getBean();
						if (assignedRole.isAssignedToGroup()) {
							AppEvent ae = new AppEvent(pl.touk.wonderfulsecurity.gwt.client.WsEvents.ASSIGN_ROLE_TO_GROUP, assignedRole);
							ae.setData("GROUP", groupTabData.getGroup());
							Dispatcher.get().dispatch(ae);
						} else {
							AppEvent ae = new AppEvent(pl.touk.wonderfulsecurity.gwt.client.WsEvents.DEASSIGN_ROLE_FROM_GROUP, assignedRole);
							ae.setData("GROUP", groupTabData.getGroup());
							Dispatcher.get().dispatch(ae);
						}
					}
				}
			};
			return assignmentChangeListener;
		}

		protected String getAssignedToColumnName() {
			return "assignedToGroup";
		}

		protected String getAssignedToColumnLabel() {
			return "Przypisana do tej grupy";
		}
	}

	class AssignableToGroupPermissionGrid extends BaseAssignableRoleGrid {

		public AssignableToGroupPermissionGrid(ArrayList beansToDisplay) {
			super(beansToDisplay);
		}

		@Override
		protected String getAssignedToColumnName() {
			return "assignedToGroup";
		}

        @Override
        protected void beforeRender() {
            this.getSaveButton().setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_CHNG_PERM_4GRP));
        }

        @Override
		protected String getAssignedToColumnLabel() {
			return "Przypisane do tej grupy";
		}

		@Override
		protected String buildHeading() {
			return "Uprawnienia przypisane do tej grupy";
		}

		@Override
		protected StoreListener<BeanModel> buildStoreChangeListener() {
			StoreListener<BeanModel> assignmentChangeListener = new StoreListener<BeanModel>() {

				@Override
				public void storeUpdate(StoreEvent<BeanModel> se) {
					if (se.getOperation() == com.extjs.gxt.ui.client.store.Record.RecordUpdate.COMMIT) {
						PermissionView assignedPermission = (PermissionView) se.getModel().getBean();
						if (assignedPermission.isAssignedToGroup()) {
							Dispatcher.get().dispatch(pl.touk.wonderfulsecurity.gwt.client.WsEvents.ASSIGN_PERMISSION_TO_GROUP, assignedPermission);
						} else {
							Dispatcher.get().dispatch(pl.touk.wonderfulsecurity.gwt.client.WsEvents.DEASSIGN_PERMISSION_FROM_GROUP, assignedPermission);
						}
					}
				}
			};
			return assignmentChangeListener;
		}
	}
}
