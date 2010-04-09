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

import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.event.EventType;
import com.google.gwt.http.client.RequestBuilder;
import pl.touk.wonderfulsecurity.gwt.client.rpc.OnlyLogCallback;
import pl.touk.wonderfulsecurity.gwt.client.rpc.ChainCommandCallback;
import pl.touk.wonderfulsecurity.gwt.client.rpc.ChainCommand;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import pl.touk.wonderfulsecurity.beans.GroupView;
import pl.touk.wonderfulsecurity.beans.WsecGroup;
import pl.touk.wonderfulsecurity.beans.RoleView;
import static pl.touk.wonderfulsecurity.gwt.client.WsEvents.*;
import pl.touk.wonderfulsecurity.gwt.client.ui.user.CreateEditUserView;
import pl.touk.wonderfulsecurity.gwt.client.ui.group.CreateEditGroupView;
import pl.touk.wonderfulsecurity.gwt.client.ui.role.CreateEditRoleView;
import pl.touk.wonderfulsecurity.beans.PermissionView;
import pl.touk.wonderfulsecurity.beans.WsecRole;
import pl.touk.wonderfulsecurity.core.ClientSecurity;
import pl.touk.wonderfulsecurity.gwt.client.rpc.ISecurityManagerRpcAsync;
import pl.touk.wonderfulsecurity.gwt.client.rpc.RpcExecutor;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public final class SecurityManagerController extends Controller {
// ----------------------------- FIELDS ------------------------------

	private ISecurityManagerRpcAsync securityManagerRpc;
	private SecurityManagerView securityManagerView;
	private CreateEditUserView createEditUserView;
	private CreateEditGroupView createEditGroupView;
	private CreateEditRoleView createEditRoleView;


// --------------------------- CONSTRUCTORS ---------------------------
	public SecurityManagerController() {
		securityManagerRpc = ClientSecurity.getAsyncSecurityManager();
		securityManagerView = new SecurityManagerView(this);
		createEditUserView = new CreateEditUserView(this);
		createEditGroupView = new CreateEditGroupView(this);
		createEditRoleView = new CreateEditRoleView(this);
		this.registerEventTypes(USER_GRID_DOUBLE_CLICK,
				START_USER_EDIT,
				ASSIGN_GROUP_TO_USER,
				DEASSIGN_GROUP_FROM_USER,
				DEASSIGN_ROLE_FROM_GROUP,
				ASSIGN_ROLE_TO_GROUP,
				GROUP_GRID_DOUBLE_CLICK,
				START_GROUP_EDIT,
				ASSIGN_ROLE_TO_USER,
				DEASSIGN_ROLE_FROM_USER,
				CREATE_NEW_USER,
				SAVE_NEW_USER,
				INIT_SECURITY_CONSOLE,
				ASSIGN_PERMISSION_TO_USER,
				DEASSIGN_PERMISSION_FROM_USER,
				ASSIGN_PERMISSION_TO_GROUP,
				DEASSIGN_PERMISSION_FROM_GROUP,
				ROLE_GRID_DOUBLE_CLICK,
				START_ROLE_EDIT,
				ASSIGN_PERMISSION_TO_ROLE,
				DEASSIGN_PERMISSION_FROM_ROLE,
				CREATE_NEW_GROUP,
				SAVE_NEW_GROUP,
				CREATE_NEW_ROLE,
				SAVE_NEW_ROLE,
				SAVE_EXISTING_GROUP,
				SAVE_EXISTING_USER,
				SAVE_EXISTING_ROLE);
	}

// -------------------------- OTHER METHODS --------------------------
	@SuppressWarnings("unchecked")
	public void handleEvent(AppEvent event) {
        EventType type = event.getType();
		if(type == USER_GRID_DOUBLE_CLICK) {
				doFetchUserById((AppEvent) event, new ChainCommand(event, "Nie można pobrać informacji o użytkowniku", "USER", "Pobrałem informacje o użytkowniku") {

					public void execute(AppEvent event) {
						dofetchAvailableGroupsMarkAssignedToUser(event, new ChainCommand(event, "Nie można pobrac informacji o  grupach", "GROUPS") {

							public void execute(AppEvent event) {
								doFetchAllAvailableAndAssignedRolesForUser(event, new ChainCommand(event, "Nie można pobrać informacji o rolach użytkownika", "ROLES") {

									public void execute(AppEvent event) {
										dofetchInheritedRolesForUser(event, new ChainCommand(event, "Nie mozna pobrac ról odziedziczonych", "ROLES_INHERITED") {

											public void execute(AppEvent event) {
												doFetchAllAvailablePermissionsMarkAssignedToUser(event, new ChainCommand(event, "Nie mozna pobrac informacji o upawnieniach", "PERMISSIONS") {

													public void execute(AppEvent event) {
														doFetchInheritedPermissionsFromGroupForUser(event, new ChainCommand(event, "Nie mozna pobrac uprawnień odziedziczonych z grup", "PERMISSIONS_INHERITED_FROM_GROUPS") {

															public void execute(AppEvent event) {
																doFetchInheritedPermissionsFromRoleForUser(event, new ChainCommand(event, "Nie mozna pobrac uprawnień odziedziczonych z ról", "PERMISSIONS_INHERITED_FROM_ROLES") {

																	public void execute(AppEvent event) {
																		doFetchInheritedPermissionsFromInhetiredRolesForUser(event, new ChainCommand(event, "Nie mozna pobrac uprawnień odziedziczonych z odziedziczonych ról", "PERMISSIONS_INHERITED_FROM_INHERITED_ROLES") {

																			public void execute(AppEvent event) {
																				event.setType(START_USER_EDIT);
																				Dispatcher.get().dispatch(event);
																			}
																		});
																	}
																});

															}
														});
													}
												});
											}
										});
									}
								});
							}
						});
					}
				});

        }

			if(type == GROUP_GRID_DOUBLE_CLICK){
				doFetchGroupById((AppEvent) event, new ChainCommand(event, "Nie mozna pobrac informacji o grupie", "GROUP") {

					public void execute(AppEvent event) {
						doFetchAllAvailableRolesMarkAssignedToGroup(event, new ChainCommand(event, "Nie można pobrać informacji o rolach", "ROLES") {

							public void execute(AppEvent event) {
								doFetchAllAvailablePermissionsMarkAssignedToGroup(event, new ChainCommand(event, "Nie można pobrać informacji o uprawnieniach", "PERMISSIONS") {

									public void execute(AppEvent event) {
										doFetchInheritedPermissionsFromRoleForGroup(event, new ChainCommand(event, "Nie można pobrać informacji o uprawnieniach odziedziczonych z ról", "PERMISSIONS_INHERITED_FROM_ROLES") {

											public void execute(AppEvent event) {
												event.setType(START_GROUP_EDIT);
												Dispatcher.get().dispatch(event);
											}
										});
									}
								});
							}
						});
					}
				});
            }


			if(type == ROLE_GRID_DOUBLE_CLICK){
				doFetchRoleById((AppEvent) event, new ChainCommand(event, "Nie mozna pobrac informacji o roli", "ROLE") {

					public void execute(AppEvent event) {
						doFetchAllAvailablePermissionsMarkAssignedToRole(event, new ChainCommand(event, "Nie można pobrać informacji o uprawnieniach", "PERMISSIONS") {

							public void execute(AppEvent event) {
								event.setType(START_ROLE_EDIT);
								Dispatcher.get().dispatch(event);
							}
						});

					}
				});
            }

			if(type == START_USER_EDIT){
				doStartEditingUser(event);
            }
			if(type == START_GROUP_EDIT){
				doStartEditingGroup(event);
            }
			if(type == START_ROLE_EDIT){
				doStartEditingRole(event);
            }

			if(type == ASSIGN_GROUP_TO_USER){
				doAssignGroupToUser((AppEvent) event, new ChainCommand(event, "Nie można dodac uzytkownika z grupy", null, "Dodano uzytkownika do grupy") {

					public void execute(AppEvent event) {
						event.setType(USER_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }

			if(type ==  ASSIGN_ROLE_TO_USER){
				doAssignRoleToUser((AppEvent) event, new ChainCommand(event, "Nie można dodac roli do uzytkownika", null, "Dodano role do uzytkownika uzytkownika") {

					public void execute(AppEvent event) {
						event.setType(USER_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }

			if(type ==  DEASSIGN_ROLE_FROM_USER){
				doDeassignRoleFromUser((AppEvent) event, new ChainCommand(event, "Nie można usunac roli z uzytkownika", null, "Usunieto role z uzytkownika uzytkownika") {

					public void execute(AppEvent event) {
						event.setType(USER_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }

			if(type ==  DEASSIGN_GROUP_FROM_USER){
				doDeassignGroupFromUser((AppEvent) event, new ChainCommand(event, "Nie można usunąc uzytkownika z grupy", null, "Usunieto uzytkownika z grupy") {

					public void execute(AppEvent event) {
						event.setType(USER_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }

			if(type ==  ASSIGN_ROLE_TO_GROUP){
				doAssignRoleToGroup((AppEvent) event, new ChainCommand(event, "Nie można dodać roli do grupy", null, "Dodano rolę do grupy") {

					public void execute(AppEvent event) {
						event.setType(GROUP_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }


			if(type ==  DEASSIGN_ROLE_FROM_GROUP){
				doDeassignRoleFromGroup((AppEvent) event, new ChainCommand(event, "Nie można usunąć roli z grupy", null, "Usunięto rolę z grupy") {

					public void execute(AppEvent event) {
						event.setType(GROUP_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }



			if(type ==  CREATE_NEW_USER){
				doCreateNewUser(
						(AppEvent) event);

            }

			if(type ==  SAVE_NEW_USER){
				doSaveUser(
						(AppEvent) event, new ChainCommand(event, "Nie można zapisać użytkownika w bazie", "USER", "Zapisano użytkownika w bazie") {

					public void execute(AppEvent event) {
						createEditUserView.closeNewUserSetp1Tab();
						event.setType(USER_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }
			if(type ==  INIT_SECURITY_CONSOLE){
				forwardToView(securityManagerView, event);
            }

			if(type ==  ASSIGN_PERMISSION_TO_USER){
				doAssignPermissionToUser((AppEvent) event, null);
            }

			if(type ==  DEASSIGN_PERMISSION_FROM_USER){
				doDeassignPermissionFromUser((AppEvent) event, null);
            }

			if(type ==  ASSIGN_PERMISSION_TO_GROUP){
				doAssignPermissionToGroup((AppEvent) event, null);
            }

			if(type ==  DEASSIGN_PERMISSION_FROM_GROUP){
				doDeassignPermissionFromGroup((AppEvent) event, null);
            }

			if(type ==  ASSIGN_PERMISSION_TO_ROLE){
				doAssignPermissionToRole((AppEvent) event, null);
            }

			if(type ==  DEASSIGN_PERMISSION_FROM_ROLE){
				doDeassignPermissionFromRole((AppEvent) event, null);
            }

			if(type ==  CREATE_NEW_GROUP){
				doCreateNewGroup((AppEvent) event);
            }

			if(type ==  SAVE_NEW_GROUP){
				doSaveGroup((AppEvent) event, new ChainCommand(event, "Nie zapisałem nowej grupy", "GROUP", "Zapisałem nową grupę") {

					@Override
					public void execute(AppEvent event) {
						createEditGroupView.closeNewGroupTab();
						event.setType(GROUP_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }

			if(type ==  CREATE_NEW_ROLE){
				doCreateNewRole((AppEvent) event);
            }

			if(type ==  SAVE_NEW_ROLE){
				doSaveRole((AppEvent) event, new ChainCommand(event, "Nie zapisałem nowej roli", "ROLE", "Zapisałem nową rolę") {

					@Override
					public void execute(AppEvent event) {
						createEditRoleView.closeNewRoleTab();
						event.setType(ROLE_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }

			if(type ==  SAVE_EXISTING_GROUP){
				doUpdateGroup((AppEvent) event, new ChainCommand(event, "Nie zapisałem edytowanej grupy", "GROUP", "Zapisałem edytowaną grupę") {

					@Override
					public void execute(AppEvent event) {
						event.setType(GROUP_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }

			if(type ==  SAVE_EXISTING_USER){
				doUpdateUser(
						(AppEvent) event, new ChainCommand(event, "Nie można zapisać użytkownika w bazie", "USER", "Zapisano użytkownika w bazie") {

					public void execute(AppEvent event) {
						event.setType(USER_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }

			if(type ==  SAVE_EXISTING_ROLE){
				doUpdateRole((AppEvent) event, new ChainCommand(event, "Nie zapisałem roli", "ROLE", "Zapisałem rolę") {

					@Override
					public void execute(AppEvent event) {
						event.setType(ROLE_GRID_DOUBLE_CLICK);
						Dispatcher.get().dispatch(event);
					}
				});
            }



	}

	private void doCreateNewRole(AppEvent event) {
		forwardToView(createEditRoleView, event);
	}

	private void doSaveRole(AppEvent event, ChainCommand command) {
		WsecRole role = (WsecRole) event.getData("ROLE");
		RequestBuilder rb = securityManagerRpc.saveRole("caller", role, new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doUpdateRole(AppEvent event, ChainCommand command) {
		WsecRole role = (WsecRole) event.getData("ROLE");
		RequestBuilder rb = securityManagerRpc.updateRole("caller", role, new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doSaveGroup(AppEvent event, ChainCommand command) {
		WsecGroup group = event.getData("GROUP");
		RequestBuilder rb = securityManagerRpc.saveGroup("caller", group, new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doUpdateGroup(AppEvent event, ChainCommand command) {
		WsecGroup group = event.getData("GROUP");
		RequestBuilder rb = securityManagerRpc.updateGroup("caller", group, new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doCreateNewGroup(AppEvent event) {
		forwardToView(createEditGroupView, event);
	}

	private void doSaveUser(AppEvent event, ChainCommand chainCommand) {
		WsecUser user = event.getData("USER");
		RequestBuilder rb = securityManagerRpc.saveUser("caller", user, new ChainCommandCallback(chainCommand));
		RpcExecutor.execute(rb);
	}

	private void doUpdateUser(AppEvent event, ChainCommand chainCommand) {
		WsecUser user = event.getData("USER");
		RequestBuilder rb = securityManagerRpc.updateUser("caller", user, new ChainCommandCallback(chainCommand));
		RpcExecutor.execute(rb);
	}

	private void doCreateNewUser(AppEvent event) {
		forwardToView(createEditUserView, event);
	}

	private void doFetchUserById(final AppEvent event, final ChainCommand onSuccessCommand) {
		// TODO: user
		WsecUser user = event.getData("USER");
		RequestBuilder rb = securityManagerRpc.fetchDomainObjectById("User", user.getId(), "pl.touk.wonderfulsecurity.beans.WsecUser", new ChainCommandCallback(onSuccessCommand));
		RpcExecutor.execute(rb);
	}

	private void dofetchAvailableGroupsMarkAssignedToUser(final AppEvent event, final ChainCommand command) {
		WsecUser user = event.getData("USER");
		RequestBuilder rb = securityManagerRpc.fetchAllAvailableGroupsMarkAssignedToUser("Caller", user.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void dofetchInheritedRolesForUser(final AppEvent event, final ChainCommand command) {
		WsecUser user = event.getData("USER");
		// TODO: caller
		RequestBuilder rb = securityManagerRpc.fetchInheritedRolesForUser("Caller", user.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doFetchAllAvailableAndAssignedRolesForUser(AppEvent event, ChainCommand chainCommand) {
		// TODO: user
		WsecUser user = event.getData("USER");
		RequestBuilder rb = securityManagerRpc.fetchAllAvailableRolesMarkAssignedToUser("User", user.getId(), new ChainCommandCallback(chainCommand));
		RpcExecutor.execute(rb);
	}

	private void doFetchGroupById(AppEvent event, final ChainCommand command) {
		// TODO: caller
		WsecGroup group = event.getData("GROUP");
		RequestBuilder rb = securityManagerRpc.fetchDomainObjectById("Caller", group.getId(), "pl.touk.wonderfulsecurity.beans.WsecGroup", new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doFetchAllAvailableRolesMarkAssignedToGroup(final AppEvent event, final ChainCommand command) {
		WsecGroup group = event.getData("GROUP");
		RequestBuilder rb = securityManagerRpc.fetchAllAvailableRolesMarkAssignedToGroup("Caller", group.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doStartEditingUser(AppEvent event) {
		forwardToView(createEditUserView, event);
	}

	private void doStartEditingGroup(AppEvent event) {
		forwardToView(createEditGroupView, event);
	}

	private void doStartEditingRole(AppEvent event) {
		forwardToView(createEditRoleView, event);
	}

	private void doAssignGroupToUser(AppEvent event, ChainCommand command) {
		WsecUser user = event.getData("USER");
		WsecGroup group = event.getData("GROUP");
		RequestBuilder rb = securityManagerRpc.assignGroupToUser("Caller", group.getId(), user.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doAssignRoleToUser(AppEvent event, ChainCommand command) {
		RoleView rw = event.getData("ROLE");
		WsecUser user = event.getData("USER");
		// TODO: caller
		RequestBuilder rb = securityManagerRpc.assignRoleToUser("Caller", rw.getId(), user.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doDeassignRoleFromUser(AppEvent event, ChainCommand command) {
		// TODO: caller
		RoleView rw = event.getData("ROLE");
		WsecUser user = event.getData("USER");

		RequestBuilder rb = securityManagerRpc.deassignRoleFromUser("Caller", rw.getId(), user.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	// TODO: caller
	private void doDeassignGroupFromUser(AppEvent event, ChainCommand command) {

		WsecUser user = event.getData("USER");
		WsecGroup group = event.getData("GROUP");
		RequestBuilder rb = securityManagerRpc.deassignGroupFromUser("Caller", group.getId(), user.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doAssignRoleToGroup(AppEvent event, ChainCommand chainCommand) {
		RequestBuilder rb = securityManagerRpc.assignRoleToGroup("Caller", ((RoleView)event.getData()).getId(), ((RoleView)event.getData()).getAssignedToGroupId(),
				new ChainCommandCallback(chainCommand));
		RpcExecutor.execute(rb);
	}

	private void doDeassignRoleFromGroup(AppEvent event, ChainCommand chainCommand) {
		// TODO: caller
		RequestBuilder rb = securityManagerRpc.deassignRoleFromGroup("Caller", ((RoleView)event.getData()).getId(), ((RoleView)event.getData()).getAssignedToGroupId(),
				new ChainCommandCallback(chainCommand));
		RpcExecutor.execute(rb);
	}

	private void doFetchAllAvailablePermissionsMarkAssignedToUser(AppEvent event, ChainCommand chainCommand) {
		WsecUser user = event.getData("USER");
		RequestBuilder rb = securityManagerRpc.fetchAllAvailablePermissionsMarkAssignedToUser("User", user.getId(), new ChainCommandCallback(chainCommand));
		RpcExecutor.execute(rb);
	}

	private void doAssignPermissionToUser(AppEvent event, ChainCommand command) {
		// TODO: caller
		RequestBuilder rb = securityManagerRpc.assignPermissionToUser("Caller", ((PermissionView)event.getData()).getId(), ((PermissionView)event.getData()).getAssignedToUserId(),
				new OnlyLogCallback("Nie mozna przypisac uprawnienia do użytkownika", "Przypisano uprawnienie do użytkownika"));
		RpcExecutor.execute(rb);
	}

	private void doDeassignPermissionFromUser(AppEvent event, ChainCommand command) {
		// TODO: caller
		RequestBuilder rb = securityManagerRpc.deassignPermissionFromUser("Caller", ((PermissionView)event.getData()).getId(), ((PermissionView)event.getData()).getAssignedToUserId(),
				new OnlyLogCallback("Nie mozna usunąć uprawnienia użytkownika", "Usunięto uprawnienie użytkownika"));
		RpcExecutor.execute(rb);
	}

	private void doFetchInheritedPermissionsFromGroupForUser(final AppEvent event, final ChainCommand command) {
//		TODO: caller
		WsecUser user = event.getData("USER");
		RequestBuilder rb = securityManagerRpc.fetchInheritedPermissionsFromGroupForUser("Caller", user.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doFetchInheritedPermissionsFromRoleForUser(final AppEvent event, final ChainCommand command) {
//		TODO: caller
		WsecUser user = event.getData("USER");
		RequestBuilder rb = securityManagerRpc.fetchInheritedPermissionsFromRoleForUser("Caller", user.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doFetchInheritedPermissionsFromInhetiredRolesForUser(AppEvent event, ChainCommand chainCommand) {
		WsecUser user = event.getData("USER");
		RequestBuilder rb = securityManagerRpc.fetchInheritedPermissionsFromInhetiredRolesForUser("Caller", user.getId(), new ChainCommandCallback(chainCommand));
		RpcExecutor.execute(rb);
	}

	private void doFetchAllAvailablePermissionsMarkAssignedToGroup(final AppEvent event, final ChainCommand command) {
		WsecGroup group = event.getData("GROUP");
		RequestBuilder rb = securityManagerRpc.fetchAllAvailablePermissionsMarkAssignedToGroup("Caller", group.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doAssignPermissionToGroup(AppEvent event, ChainCommand command) {
		RequestBuilder rb = securityManagerRpc.assignPermissionToGroup("Caller", ((PermissionView)event.getData()).getId(), ((PermissionView)event.getData()).getAssignedToGroupId(),
				new OnlyLogCallback("Nie mozna przypisac uprawnienia do grupy", "Przypisano uprawnieni do grupy"));
		RpcExecutor.execute(rb);
	}

	private void doDeassignPermissionFromGroup(AppEvent event, ChainCommand command) {
		// TODO: caller
		RequestBuilder rb = securityManagerRpc.deassignPermissionFromGroup("Caller", ((PermissionView)event.getData()).getId(), ((PermissionView)event.getData()).getAssignedToGroupId(),
				new OnlyLogCallback("Nie mozna usunąć uprawnienia z grupy", "Usunięto uprawnienie z grupy"));
		RpcExecutor.execute(rb);
	}

	private void doFetchRoleById(AppEvent event, final ChainCommand command) {
		// TODO: caller
		WsecRole role = event.getData("ROLE");
		RequestBuilder rb = securityManagerRpc.fetchDomainObjectById("Caller", role.getId(), "pl.touk.wonderfulsecurity.beans.WsecRole", new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doFetchAllAvailablePermissionsMarkAssignedToRole(final AppEvent event, final ChainCommand command) {
		WsecRole role = event.getData("ROLE");
		RequestBuilder rb = securityManagerRpc.fetchAllAvailablePermissionsMarkAssignedToRole("Caller", role.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}

	private void doAssignPermissionToRole(AppEvent event, ChainCommand command) {
		RequestBuilder rb = securityManagerRpc.assignPermissionToRole("Caller", ((PermissionView)event.getData()).getId(), ((PermissionView)event.getData()).getAssignedToRoleId(),
				new OnlyLogCallback("Nie mozna przypisac uprawnienia do roli", "Przypisano uprawnieni do roli"));
		RpcExecutor.execute(rb);
	}

	private void doDeassignPermissionFromRole(AppEvent event, ChainCommand command) {
		// TODO: caller
		RequestBuilder rb = securityManagerRpc.deassignPermissionFromRole("Caller", ((PermissionView)event.getData()).getId(), ((PermissionView)event.getData()).getAssignedToRoleId(),
				new OnlyLogCallback("Nie mozna usunąć uprawnienia z roli", "Usunięto uprawnienie z roli"));
		RpcExecutor.execute(rb);
	}

	private void doFetchInheritedPermissionsFromRoleForGroup(final AppEvent event, final ChainCommand command) {
//		TODO: caller
		WsecGroup group = event.getData("GROUP");
		RequestBuilder rb = securityManagerRpc.fetchInheritedPermissionsFromRoleForGroup("Caller", group.getId(), new ChainCommandCallback(command));
		RpcExecutor.execute(rb);
	}
}
