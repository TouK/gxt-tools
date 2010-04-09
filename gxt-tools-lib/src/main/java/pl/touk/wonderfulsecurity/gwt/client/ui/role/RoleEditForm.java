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


import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import pl.touk.wonderfulsecurity.beans.PermissionView;
import pl.touk.wonderfulsecurity.beans.WsecPermission;

import java.util.ArrayList;
import pl.touk.wonderfulsecurity.gwt.client.ui.role.CreateEditRoleView.CreateEditRoleTabData;
import pl.touk.wonderfulsecurity.core.ClientSecurity;

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
public class RoleEditForm extends LayoutContainer {

	
	protected AssignableToRolePermissionGrid assignableToRolePermissionGrid;
	protected RoleDetailsForm roleDetailsForm;
	protected CreateEditRoleView.CreateEditRoleTabData roleTabData;

	public RoleEditForm() {
        this.setLayout(new RowLayout());
    }

	public RoleEditForm(CreateEditRoleTabData roleTabData) {
		this.roleTabData = roleTabData;
		this.assignableToRolePermissionGrid = new AssignableToRolePermissionGrid(this.roleTabData.permissions);
		this.roleDetailsForm = new RoleDetailsForm(false);

		this.setLayout(new RowLayout());
		this.add(roleDetailsForm, new RowData(1, -1, new Margins(2, 2, 0, 2)));
		this.add(assignableToRolePermissionGrid, new RowData(1, -1, new Margins(2)));


	}

	public CreateEditRoleTabData getRoleTabData() {
		return roleTabData;
	}

	public void setRoleTabData(CreateEditRoleTabData roleTabData) {
		this.roleTabData = roleTabData;
	}

	void doRedisplayRoleData() {
		assignableToRolePermissionGrid.load();
		roleDetailsForm.setRoleModelData(roleTabData.getRole());
	}



// -------------------------- INNER CLASSES --------------------------


	class AssignableToRolePermissionGrid extends BaseAssignableRoleGrid {

		public AssignableToRolePermissionGrid(ArrayList beansToDisplay) {
			super(beansToDisplay);
		}

		@Override
		protected String getAssignedToColumnName() {
			return "assignedToRole";
		}

        @Override
        protected void beforeRender() {
            this.getSaveButton().setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_CHNG_PERM_4ROLE));
        }

        @Override
		protected String getAssignedToColumnLabel() {
			return "Przypisane do tej roli";
		}

		@Override
		protected String buildHeading() {
			return "Uprawnienia przypisane do tej roli";
		}

		@Override
		protected StoreListener<BeanModel> buildStoreChangeListener() {
			StoreListener<BeanModel> assignmentChangeListener = new StoreListener<BeanModel>() {

				@Override
				public void storeUpdate(StoreEvent<BeanModel> se) {
					if (se.getOperation() == com.extjs.gxt.ui.client.store.Record.RecordUpdate.COMMIT) {
						PermissionView assignedPermission = (PermissionView) se.getModel().getBean();
						if (assignedPermission.isAssignedToRole()) {
							Dispatcher.get().dispatch(pl.touk.wonderfulsecurity.gwt.client.WsEvents.ASSIGN_PERMISSION_TO_ROLE, assignedPermission);
						} else {
							Dispatcher.get().dispatch(pl.touk.wonderfulsecurity.gwt.client.WsEvents.DEASSIGN_PERMISSION_FROM_ROLE, assignedPermission);
						}
					}
				}
			};
			return assignmentChangeListener;
		}
	}
}
