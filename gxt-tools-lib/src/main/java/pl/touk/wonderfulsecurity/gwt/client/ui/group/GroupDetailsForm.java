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

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import pl.touk.wonderfulsecurity.beans.WsecGroup;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.gwt.client.Log;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.SAVE_EXISTING_GROUP;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.SAVE_NEW_GROUP;
import pl.touk.wonderfulsecurity.gwt.client.ui.Logger;
import pl.touk.wonderfulsecurity.core.ClientSecurity;

/**
 *
 * @author Paweł Tomaszewski
 */
public class GroupDetailsForm extends FormPanel {

	private TextField<Long> groupId;
	private TextField<String> groupName;
	private TextField<String> groupDescription;
	private Logger output;
	private WsecGroup group;

	public GroupDetailsForm(boolean isNewGroup) {
		this.setLayout(new FormLayout());
		this.setHeading("Tworzenie/edycja grupy");
        this.setButtonAlign(Style.HorizontalAlignment.CENTER);
        this.setFrame(true);

		groupId = new TextField<Long>();
		groupId.setFieldLabel("Id");
		groupId.setReadOnly(true);

		groupName = new TextField<String>();
		groupName.setFieldLabel("Nazwa");

		groupDescription = new TextField<String>();
		groupDescription.setFieldLabel("Opis");

		this.add(groupId);
		this.add(groupName);
		this.add(groupDescription);

		if (isNewGroup) {
			Button nextStep = new Button("Zapisz i edytuj uprawnienia", new SelectionListener<ButtonEvent>() {

				public void componentSelected(ButtonEvent ce) {
					if (GroupDetailsForm.this.isValid()) {

						AppEvent ae = new AppEvent(SAVE_NEW_GROUP);
						ae.setData("GROUP", getModelObject());
						Dispatcher.get().dispatch(ae);

						output.info("Zapisałem nową grupę");

					}
				}
			});

			this.addButton(nextStep);

		} else {
			Button save = new Button("Zapisz", new SelectionListener<ButtonEvent>() {

				public void componentSelected(ButtonEvent ce) {
					if (GroupDetailsForm.this.isValid()) {

						AppEvent ae = new AppEvent(SAVE_EXISTING_GROUP);
						ae.setData("GROUP", getModelObject());
						Dispatcher.get().dispatch(ae);

						output.info("Zaktualizowałem dane grupy");

					}
				}
			});

            save.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_SAVE_GRP_DTLS));


			this.addButton(save);

		}

	}

	private WsecGroup createModelGroup() {
		if (!this.isValid()) {
			output.info("Formularz nie wypełnłniony poprawnie");
			return null;
		}

		WsecGroup group = new WsecGroup();

		group.setName(groupName.getValue());
		group.setDescription(groupDescription.getValue());

		if (groupId.getValue() != null) {
			GWT.log(groupId.getValue() + "", null);
			group.setId(groupId.getValue());

		}

		return group;
	}

	protected void setGroupModelData(WsecGroup group) {
		this.group = group;

		groupId.setValue(group.getId());
		groupDescription.setValue(group.getDescription());
		groupName.setValue(group.getName());

	}

	public WsecGroup getModelObject() {

		if (!this.isValid()) {
			Log.warn("Form in invalid state, cannot create domain object");
			return null;
		}

		group = new WsecGroup();
		group.setDescription(groupDescription.getValue());
		group.setName(groupName.getValue());

		if (groupId.getValue() != null) {
			Object id = groupId.getValue();
			group.setId(Long.parseLong(id.toString()));
		}

		return group;
	}

    @Override
    protected void beforeRender() {
        groupName.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_GRP_NAME_CHANGE));
    }
}
