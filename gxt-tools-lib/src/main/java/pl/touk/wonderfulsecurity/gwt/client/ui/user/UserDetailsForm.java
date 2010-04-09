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


import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Field;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.gwt.client.Log;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.SAVE_EXISTING_USER;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.SAVE_NEW_USER;
import pl.touk.wonderfulsecurity.gwt.client.ui.Logger;
import pl.touk.wonderfulsecurity.core.ClientSecurity;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class UserDetailsForm extends FormPanel {

    private WsecUser user;
    private TextField userId;
    private TextField<String> login;
    private TextField<String> password;
    private TextField<String> password1;
    private TextField<String> firstName;
    private TextField<String> lastName;
    private TextField<String> emailAddress;
    private TextField<String> jobTitle;
    private TextField<String> street;
    private TextField<String> city;
    private Logger output;
	private CheckBox enabled;

    public UserDetailsForm(boolean isNewUser) {
        this.setHeading("Tworzenie/edycja użytkownika");
        this.setFrame(true);
        this.setLabelWidth(180);
        userId = new TextField();
        userId.setFieldLabel("Id");
        userId.setReadOnly(true);

        login = new TextField();
        login.setFieldLabel("Login");
        login.setMinLength(3);


        // TODO: waliduj oba hasla pasuja
        password = new TextField();
        password.setFieldLabel("Hasło");
        password.setPassword(true);
        password.addListener(Events.OnKeyUp, new PasswordValidator());
       

        password1 = new TextField();
        password1.setFieldLabel("Powtórz hasło");
        password1.setPassword(true);
        password1.addListener(Events.OnKeyUp, new PasswordValidator());

        firstName = new TextField();
        firstName.setFieldLabel("Imię");

        jobTitle = new TextField();
        jobTitle.setFieldLabel("Stanowisko w firmie");
        city = new TextField();
        city.setFieldLabel("Adres miasto");
        street = new TextField();
        street.setFieldLabel("Adres ulica");
        lastName = new TextField();
        lastName.setFieldLabel("Nazwisko");
        emailAddress = new TextField();
        emailAddress.setFieldLabel("Adres email");
        emailAddress.setRegex(".+@.+");

		enabled = new CheckBox();
		enabled.setFieldLabel("Aktywny");

        TextField<String>.TextFieldMessages msg = emailAddress.getMessages();
        msg.setRegexText("Nieprawidłowy format adresu email.");
        emailAddress.setMessages(msg);
        
//        firstName, lastName, fullName, emailAddress,

        this.add(userId);
        this.add(login);
        this.add(password);
        this.add(password1);
        this.add(firstName);
        this.add(jobTitle);
        this.add(lastName);
        this.add(emailAddress);
        this.add(city);
        this.add(street);
		this.add(enabled);
        this.setButtonAlign(Style.HorizontalAlignment.CENTER);

        if(isNewUser) {
            Button nextStep = new Button("Zapisz i edytuj uprawnienia",new SelectionListener<ButtonEvent>(){
                public void componentSelected(ButtonEvent ce) {
                    if (UserDetailsForm.this.isValid()) {

                        AppEvent ae = new AppEvent(SAVE_NEW_USER);
                        ae.setData("USER",getModelObject());
                        Dispatcher.get().dispatch(ae);

                        output.info("Zapisałem nowego użytkownika");

                    }
                }
            });

            this.addButton(nextStep);

        } else {
            Button save = new Button("Zapisz",new SelectionListener<ButtonEvent>(){
                public void componentSelected(ButtonEvent ce) {
                    if (UserDetailsForm.this.isValid()) {
                        
                        AppEvent ae = new AppEvent(SAVE_EXISTING_USER);
                        ae.setData("USER",getModelObject());
                        Dispatcher.get().dispatch(ae);

                        output.info("Zaktualizowałem dane użytkownika");

                    }
                }
            });
            save.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_SAVE_USR_DTLS));
            this.addButton(save);

        }
    }

    public WsecUser getModelObject() {

        if (!this.isValid()) {
            Log.warn("Form in invalid state, cannot create domain object");
            return null;
        }

        WsecUser user = new WsecUser();
		user.setLogin(this.login.getValue());
        user.setPassword(this.password.getValue());
        user.setFirstName(this.firstName.getValue());
        user.setLastName(this.lastName.getValue());
        user.setEmailAddress(this.emailAddress.getValue());
		user.setEnabled(this.enabled.getValue());
        user.setJobTitle(this.jobTitle.getValue());
        user.setCity(this.city.getValue());
        user.setStreet(this.street.getValue());

        if(userId.getValue() != null) {
            user.setId(Long.parseLong(userId.getValue().toString()));
        }

        return user;
    }

    public void setModelObject(WsecUser user) {
        this.user = user;
        this.userId.setValue(user.getId());
        this.login.setValue(user.getLogin());
        this.password.setValue(user.getPassword());
        this.password1.setValue(user.getPassword());
        this.firstName.setValue(user.getFirstName());
        this.lastName.setValue(user.getLastName());
        this.emailAddress.setValue(user.getEmailAddress());
		this.enabled.setValue(user.isEnabled());
        this.jobTitle.setValue(user.getJobTitle());
        this.city.setValue(user.getCity());
        this.street.setValue(user.getStreet());
    }

    @Override
    protected void beforeRender() {
        password.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_PASSWORD_CHANGE));
        password1.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_PASSWORD_CHANGE));
        login.setEnabled(ClientSecurity.hasPermission(WsecPermission.WSEC_LOGIN_CHANGE));
    }

    class PasswordValidator implements Listener {

        public void handleEvent(BaseEvent be) {

            boolean bothNulls = password.getValue() == null && password1.getValue() == null;
            boolean oneIsNull = password.getValue() == null || password1.getValue() == null && password.getValue() != password1.getValue();

            boolean allowEmptyPasswords = ClientSecurity.hasPermission(WsecPermission.WSEC_ALLOW_EMPTY_PASSWORD);

            if((allowEmptyPasswords && bothNulls) ||
                    (!oneIsNull && password.getValue().trim().equals(password1.getValue().trim()))){
                password.clearInvalid();
                password1.clearInvalid();

            } else {
                password.forceInvalid("Popraw hasło");
                password1.forceInvalid("Popraw hasło");
            }

//            if((!allowEmptyPasswords && bothNulls)
//                    || (allowEmptyPasswords && oneIsNull)
//                    || (!oneIsNull && !password.getValue().trim().equals(password1.getValue().trim()))) {
//            } else {
//            }
        }
    }
}
