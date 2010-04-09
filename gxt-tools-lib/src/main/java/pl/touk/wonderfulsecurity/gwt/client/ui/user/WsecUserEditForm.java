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

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import static com.extjs.gxt.ui.client.store.Record.RecordUpdate.COMMIT;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.event.EventType;
import pl.touk.wonderfulsecurity.beans.GroupView;
import pl.touk.wonderfulsecurity.beans.PermissionView;
import pl.touk.wonderfulsecurity.beans.RoleView;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.*;
import pl.touk.wonderfulsecurity.gwt.client.ui.BaseGridOfEntities;
import pl.touk.wonderfulsecurity.gwt.client.ui.permission.InheritedPermissionsFromRoleGrid;
import pl.touk.wonderfulsecurity.gwt.client.ui.role.BaseAssignableRoleGrid;
import pl.touk.wonderfulsecurity.core.ClientSecurity;

import java.util.ArrayList;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class WsecUserEditForm extends LayoutContainer {
// ------------------------------ FIELDS ------------------------------

	protected AssignedGroups groupGrid;
	protected AssignedRolesGrid roleGrid;
	protected InheritedRolesGrid inheritedRolesGrid;
	protected UserDetailsForm detailsForm;
	private CreateEditUserView.CreateEditUserTabData modelData;
	protected AssignedPermisionsGrid permissionGrid;
	protected InheritedPermissionsFromGroupGrid inheritedPermissionFromGroupGrid;
	protected InheritedPermissionsFromRoleGrid inheritedPermissionFromRoleGrid;
	protected InheritedPermissionsFromInheritedRolesGrid inheritedPermissionsFromInheritedRolesGrid;

// --------------------------- CONSTRUCTORS ---------------------------
	public WsecUserEditForm(CreateEditUserView.CreateEditUserTabData userTabData) {
		this.modelData = userTabData;
		groupGrid = new AssignedGroups(userTabData.groups);
		roleGrid = new AssignedRolesGrid(userTabData.roles);
		permissionGrid = new AssignedPermisionsGrid(userTabData.permissions);
		detailsForm = new UserDetailsForm(false);
		inheritedRolesGrid = new InheritedRolesGrid(userTabData.rolesInherited);
		inheritedPermissionFromGroupGrid = new InheritedPermissionsFromGroupGrid(userTabData.groupPermissionsInherited);
		inheritedPermissionFromRoleGrid = new InheritedPermissionsFromRoleGrid(userTabData.rolePermissionsInherited);
		inheritedPermissionsFromInheritedRolesGrid = new InheritedPermissionsFromInheritedRolesGrid(userTabData.permissionsInheritedFromInheritedRoles);

        this.setLayout(new RowLayout());
        RowData topRowData = new RowData(1, -1, new Margins(2, 2, 0, 2));
        RowData middleRowData = new RowData(1, -1, new Margins(2));
        RowData bottomRowData = new RowData(1, -1, new Margins(0, 2, 2, 2));

        this.add(detailsForm, topRowData);
		this.add(groupGrid, middleRowData);
		this.add(roleGrid, middleRowData);
		this.add(permissionGrid, middleRowData);
		this.add(inheritedRolesGrid, middleRowData);
		this.add(inheritedPermissionFromGroupGrid, middleRowData);
		this.add(inheritedPermissionFromRoleGrid, bottomRowData);
		this.add(inheritedPermissionsFromInheritedRolesGrid, bottomRowData);
	}

// --------------------- GETTER / SETTER METHODS ---------------------
	public CreateEditUserView.CreateEditUserTabData getModelData() {
		return modelData;
	}

// -------------------------- OTHER METHODS --------------------------
	public void doRedisplayUserData() {
		groupGrid.load();
		roleGrid.load();
		permissionGrid.load();
		inheritedRolesGrid.load();
		inheritedPermissionFromGroupGrid.load();
		inheritedPermissionFromRoleGrid.load();
		inheritedPermissionsFromInheritedRolesGrid.load();
		detailsForm.setModelObject(modelData.getUser());

	}

// -------------------------- INNER CLASSES --------------------------
	class AssignedGroups extends BaseAssignableRoleGrid {



		public AssignedGroups(ArrayList beansToDisplay) {
			super(beansToDisplay);
		}

    @Override
    protected void beforeRender() {
        this.getSaveButton().setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_CHNG_GRP_4USER));
    }

    @Override
		protected String getAssignedToColumnName() {
			return "assigned";
		}

		@Override
		protected String getAssignedToColumnLabel() {
			return "Przypisany";
		}

		@Override
		protected String buildHeading() {
			return "Przynależność do grupy";
		}

		@Override
		protected void onGridRowDoubleClick(ModelData ge) {
			GroupView gv = ((BeanModel) ge).getBean();
			Dispatcher.get().dispatch(GROUP_GRID_DOUBLE_CLICK, gv);
		}

		protected StoreListener<BeanModel> buildStoreChangeListener() {
			StoreListener<BeanModel> assignmentChangeListener = new StoreListener<BeanModel>() {

				public void storeUpdate(StoreEvent<BeanModel> se) {
					if (se.getOperation() == COMMIT) {
						AppEvent ae = new AppEvent(new EventType(-1));
						GroupView assignedGroup = (GroupView) se.getModel().getBean();
						ae.setData("GROUP", assignedGroup);
						ae.setData("USER", modelData.getUser());

						if (assignedGroup.isAssigned()) {
							ae.setType(ASSIGN_GROUP_TO_USER);
						} else {
							ae.setType(DEASSIGN_GROUP_FROM_USER);
						}

						Dispatcher.get().dispatch(ae);
					}
				}
			};
			return assignmentChangeListener;
		}
	}

	/**
	 * @author Lukasz Kucharski - lkc@touk.pl
	 */
	class AssignedRolesGrid extends BaseAssignableRoleGrid {

		public AssignedRolesGrid(ArrayList beansToDisplay) {
			super(beansToDisplay);
		}

        @Override
        protected void beforeRender() {
            this.getSaveButton().setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_CHNG_ROLE_4USER));
        }

        protected String buildHeading() {
			return "Role przypisane bezpośrednio do tego użytkownika";
		}

		protected StoreListener<BeanModel> buildStoreChangeListener() {
			StoreListener<BeanModel> assignmentChangeListener = new StoreListener<BeanModel>() {

				public void storeUpdate(StoreEvent<BeanModel> se) {
					if (se.getOperation() == COMMIT) {
						AppEvent ae = new AppEvent(new EventType(-1));
						RoleView assignedRole = (RoleView) se.getModel().getBean();
						ae.setData("ROLE", assignedRole);
						ae.setData("USER", modelData.getUser());

						if (assignedRole.isAssignedToUser()) {
							ae.setType(ASSIGN_ROLE_TO_USER);
						} else {
							ae.setType( DEASSIGN_ROLE_FROM_USER);
						}

						Dispatcher.get().dispatch(ae);
					}
				}
			};
			return assignmentChangeListener;
		}

		protected String getAssignedToColumnName() {
			return "assignedToUser";
		}

		protected String getAssignedToColumnLabel() {
			return "Przypisana do użytkownika";
		}
	}

	class InheritedRolesGrid extends BaseGridOfEntities {

		protected InheritedRolesGrid(ArrayList beansToDisplay) {
			super(beansToDisplay);
		}

		protected ColumnModel createColumnModel() {
			ArrayList<ColumnConfig> cc = new ArrayList<ColumnConfig>();
			cc.add(new ColumnConfig("name", "Nazwa", 180));
			cc.add(new ColumnConfig("description", "Description", 100));
            this.setExpandedColumnId("description");
            ColumnModel cm = new ColumnModel(cc);
			return cm;
		}

		protected String buildHeading() {
			return "Role odziedziczone z nadanych grup";
		}
	}

	class AssignedPermisionsGrid extends BaseAssignableRoleGrid {

		public AssignedPermisionsGrid(ArrayList beansToDisplay) {
			super(beansToDisplay);
		}

        @Override
        protected void beforeRender() {
            this.getSaveButton().setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_CHNG_PERM_4USER));
        }

        @Override
		protected String getAssignedToColumnName() {
			return "assignedToUser";
		}

		@Override
		protected String getAssignedToColumnLabel() {
			return "Przypisane do tego użytkownika";
		}

		@Override
		protected String buildHeading() {
			return "Przypisane uprawnienia";
		}

		@Override
		protected StoreListener<BeanModel> buildStoreChangeListener() {
			StoreListener<BeanModel> assignmentChangeListener = new StoreListener<BeanModel>() {

				@Override
				public void storeUpdate(StoreEvent<BeanModel> se) {
					if (se.getOperation() == com.extjs.gxt.ui.client.store.Record.RecordUpdate.COMMIT) {
						PermissionView assignedPermission = (PermissionView) se.getModel().getBean();
						if (assignedPermission.isAssignedToUser()) {
							Dispatcher.get().dispatch(pl.touk.wonderfulsecurity.gwt.client.WsEvents.ASSIGN_PERMISSION_TO_USER, assignedPermission);
						} else {
							Dispatcher.get().dispatch(pl.touk.wonderfulsecurity.gwt.client.WsEvents.DEASSIGN_PERMISSION_FROM_USER, assignedPermission);
						}
					}
				}
			};
			return assignmentChangeListener;
		}
	}

	class InheritedPermissionsFromGroupGrid extends BaseGridOfEntities {

		protected InheritedPermissionsFromGroupGrid(ArrayList beansToDisplay) {
			super(beansToDisplay);
		}

		protected ColumnModel createColumnModel() {
			ArrayList<ColumnConfig> cc = new ArrayList<ColumnConfig>();
			cc.add(new ColumnConfig("inheritedFromGroup", "Nazwa grupy", 180));
			cc.add(new ColumnConfig("name", "Nazwa", 180));
			cc.add(new ColumnConfig("description", "Description", 100));
            this.setExpandedColumnId("description");
			ColumnModel cm = new ColumnModel(cc);
			return cm;
		}

		protected String buildHeading() {
			return "Uprawnienia odziedziczone z nadanych grup";
		}
	}

	public class InheritedPermissionsFromInheritedRolesGrid extends BaseGridOfEntities {

	public InheritedPermissionsFromInheritedRolesGrid(ArrayList beansToDisplay) {
		super(beansToDisplay);
	}

	protected ColumnModel createColumnModel() {
		ArrayList<ColumnConfig> cc = new ArrayList<ColumnConfig>();
		cc.add(new ColumnConfig("inheritedFromGroup", "Nazwa grupy", 180));
		cc.add(new ColumnConfig("inheritedFromRole", "Nazwa roli", 180));
		cc.add(new ColumnConfig("name", "Nazwa", 180));
		cc.add(new ColumnConfig("description", "Description", 100));
		this.setExpandedColumnId("description");
		ColumnModel cm = new ColumnModel(cc);
		return cm;
	}

	protected String buildHeading() {
		return "Uprawnienia odziedziczone z odziedziczonych ról";
	}
}
}
