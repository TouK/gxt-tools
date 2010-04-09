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
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import pl.touk.wonderfulsecurity.beans.WsecRole;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.*;
import pl.touk.wonderfulsecurity.gwt.client.ui.Logger;
import pl.touk.wonderfulsecurity.core.ClientSecurity;

/**
 *
 * @author Paweł Tomaszewski
 */
public class RoleDetailsForm extends FormPanel {
    private TextField<Long> roleId;
    private TextField<String> roleName;
    private TextField<String> roleDescription;
    private Logger output;
    private WsecRole role;

    public RoleDetailsForm(boolean isNewRole) {
        this.setHeading("Tworzenie/edycja roli");
        this.setFrame(true);
        this.setButtonAlign(Style.HorizontalAlignment.CENTER);

        roleId = new TextField<Long>();
        roleId.setFieldLabel("Id");
        roleId.setReadOnly(true);

        roleName = new TextField<String>();
        roleName.setFieldLabel("Nazwa");

        roleDescription = new TextField<String>();
        roleDescription.setFieldLabel("Opis roli");

        this.add(roleId);
        this.add(roleName);
        this.add(roleDescription);

		if (isNewRole) {
			Button nextStep = new Button("Zapisz i edytuj uprawnienia", new SelectionListener<ButtonEvent>() {

				public void componentSelected(ButtonEvent ce) {
					if (RoleDetailsForm.this.isValid()) {

						AppEvent ae = new AppEvent(SAVE_NEW_ROLE);
						ae.setData("ROLE", getRoleModelData());
						Dispatcher.get().dispatch(ae);

						output.info("Zapisałem nową rolę");

					}
				}
			});

			this.addButton(nextStep);

		} else {
			Button save = new Button("Zapisz", new SelectionListener<ButtonEvent>() {

				public void componentSelected(ButtonEvent ce) {
					if (RoleDetailsForm.this.isValid()) {

						AppEvent ae = new AppEvent(SAVE_EXISTING_ROLE);
						ae.setData("ROLE", getRoleModelData());
						Dispatcher.get().dispatch(ae);

						output.info("Zaktualizowałem dane roli");

					}
				}
			});
            save.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_SAVE_ROLE_DTLS));
			this.addButton(save);

		}
    }

    private WsecRole getRoleModelData() {
        if(!this.isValid()) {
            output.info("Formularz nie wypełniony poprawnie");
            return null;
        }

        WsecRole role = new WsecRole();

        role.setName(roleName.getValue());
        role.setDescription(roleDescription.getValue());

        if(roleId.getValue() != null) {
            role.setId(Long.parseLong(roleId.getValue()+""));
        }

        return role;
    }

    protected void setRoleModelData(WsecRole role) {
        this.role = role;

        this.roleId.setValue(Long.parseLong(role.getId().toString()));
        this.roleName.setValue(role.getName());
        this.roleDescription.setValue(role.getDescription());
    }

    @Override
    protected void beforeRender() {
        roleName.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_ROLE_NAME_CHANGE));
    }
}
