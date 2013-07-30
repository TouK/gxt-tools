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
package pl.touk.wonderfulsecurity.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.InitializingBean;
import pl.touk.wonderfulsecurity.beans.*;
import pl.touk.wonderfulsecurity.dao.WsecBaseDao;
import static pl.touk.wonderfulsecurity.utils.Commons.checkArgumentsNotNull;

import java.io.Serializable;
import java.util.*;

import pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException;
import pl.touk.wonderfulsecurity.core.ServerSecurity;
import pl.touk.wonderfulsecurity.dao.WsecRoleDao;

/**
 * Implementation of security manager. This implementation is exported as GWT service.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
@Transactional
public class SecurityManagerImpl implements ISecurityManager{


// ------------------------------ FIELDS ------------------------------

	private static final Log log = LogFactory.getLog(SecurityManagerImpl.class);
	protected WsecBaseDao baseDao;
	protected WsecRoleDao roleDao;

	// --------------------- GETTER / SETTER METHODS ---------------------
	public WsecBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(WsecBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public WsecRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(WsecRoleDao roleDao) {
		this.roleDao = roleDao;
	}

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface ISecurityManagerRpc ---------------------

	public void assignPermissionToUser(String caller, Long permissionId, Long userId) throws PermissionCollisionException {
		WsecPermission permission = baseDao.fetchById(WsecPermission.class, permissionId);
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		checkArgumentsNotNull("Podano uprawnienie lub użytkownika, którego nie ma w bazie danych", permission, user);
		user.addPermission(permission);
	}


	public void deassignPermissionFromUser(String caller, Long permissionId, Long userId) {
		WsecPermission permission = baseDao.fetchById(WsecPermission.class, permissionId);
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		checkArgumentsNotNull("Podano uprawnienie lub użytkownika, którego nie ma w bazie danych", permission, user);
		user.removePermission(permission);
	}


	public void assignPermissionToRole(String caller, Long permissionId, Long roleId) throws PermissionCollisionException {
		WsecPermission permission = baseDao.fetchById(WsecPermission.class, permissionId);
		WsecRole role = baseDao.fetchById(WsecRole.class, roleId);
		checkArgumentsNotNull("Uprawnienie lub rola nie znaleziona w bazie", permission, role);
		role.addPermission(permission);
	}


	public void deassignPermissionFromRole(String caller, Long permissionId, Long roleId) {
		WsecPermission permission = baseDao.fetchById(WsecPermission.class, permissionId);
		WsecRole role = baseDao.fetchById(WsecRole.class, roleId);
		checkArgumentsNotNull("Uprawnienie lub rola nie znaleziona w bazie", permission, role);
		role.removePermission(permission);
	}


	public void assignPermissionToGroup(String caller, Long permissionId, Long groupId) throws PermissionCollisionException {
		WsecPermission permission = baseDao.fetchById(WsecPermission.class, permissionId);
		WsecGroup group = baseDao.fetchById(WsecGroup.class, groupId);
		checkArgumentsNotNull("Uprawnienie lub grupa nie znaleziona w bazie", permission, group);
		group.addPermission(permission);
	}


	public void deassignPermissionFromGroup(String caller, Long permissionId, Long groupId) {
		WsecPermission permission = baseDao.fetchById(WsecPermission.class, permissionId);
		WsecGroup group = baseDao.fetchById(WsecGroup.class, groupId);
		checkArgumentsNotNull("Uprawnienie lub grupa nie znaleziona w bazie", permission, group);
		group.removePermission(permission);
	}

	// TODO: doczytać sposób na zrobienie tego bez dociągania grupy i usera z bazy

	public void assignGroupToUser(String caller, Long groupId, Long userId) throws PermissionCollisionException {
		WsecGroup group = baseDao.fetchById(WsecGroup.class, groupId);
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		checkArgumentsNotNull("Podano grupę lub użytkownika, którego nie ma w bazie danych", group, user);
		user.addToGroup(group);
	}

	// TODO: doczytać sposób na zrobienie tego bez dociągania grupy i usera z bazy

	public void deassignGroupFromUser(String caller, Long groupId, Long userId) {
		WsecGroup group = baseDao.fetchById(WsecGroup.class, groupId);
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		checkArgumentsNotNull("Podano grupę lub użytkownika, którego nie ma w bazie danych", group, user);
		user.removeFromGroup(group);
	}

	// TODO: doczytać sposób na zrobienie tego bez dociągania grupy i usera z bazy

	public void assignRoleToGroup(String caller, Long roleId, Long groupId) throws PermissionCollisionException {
		WsecGroup group = baseDao.fetchById(WsecGroup.class, groupId);
		WsecRole role = baseDao.fetchById(WsecRole.class, roleId);
		checkArgumentsNotNull("Grupa lub rola nie znaleziona w bazie", group, role);
		group.addRole(role);
	}

	// TODO: doczytać sposób na zrobienie tego bez dociągania grupy i usera z bazy

	public void deassignRoleFromGroup(String caller, Long roleId, Long groupId) {
		WsecGroup group = baseDao.fetchById(WsecGroup.class, groupId);
		WsecRole role = baseDao.fetchById(WsecRole.class, roleId);
		checkArgumentsNotNull("Grupa lub rola nie znaleziona w bazie", group, role);
		group.deleteRole(role);
	}


	public void assignRoleToUser(String caller, Long roleId, Long userId) throws PermissionCollisionException {
		WsecRole role = baseDao.fetchById(WsecRole.class, roleId);
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		checkArgumentsNotNull("Rola lub uzytkownik z podanymi identyfikatorami nie istnieją w bazie danych", role, user);
		user.addRole(role);
	}


	public void deassignRoleFromUser(String caller, Long roleId, Long userId) {
		WsecRole role = baseDao.fetchById(WsecRole.class, roleId);
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		checkArgumentsNotNull("Rola lub uzytkownik z podanymi identyfikatorami nie istnieją w bazie danych", role, user);
		user.removeRole(role);
	}

	public ArrayList<WsecRole> fetchAllAvailableRoles(String caller) {
		return baseDao.fetchAll(WsecRole.class);
	}

	public ArrayList<WsecGroup> fetchAllAvailableGroups(String caller) {
		return baseDao.fetchAll(WsecGroup.class);
	}

	public ArrayList<WsecPermission> fetchAllAvailablePermissions(String caller) {
		return baseDao.fetchAll(WsecPermission.class);
	}

	public ArrayList<RoleView> fetchInheritedRolesForUser(String caller, Long userId) {
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		checkArgumentsNotNull("Użytkownik z podanym id nie istnieje w bazie", user);
		Set<WsecGroup> groups = user.getGroups();
		ArrayList<RoleView> roleViews = new ArrayList<RoleView>();

		for (WsecGroup group : groups) {
			for (WsecRole role : group.getRoles()) {
				RoleView rw = new RoleView(role);
				rw.setInheritedFromGroup(group.getName());
				roleViews.add(rw);
			}
		}

		return roleViews;
	}

	public ArrayList<RoleView> fetchAllAvailableRolesMarkAssignedToGroup(String caller, Long groupId) {
		ArrayList<WsecRole> allRole = baseDao.fetchAll(WsecRole.class);
		WsecGroup group = baseDao.fetchById(WsecGroup.class, groupId);

		if (group == null) {
			throw new IllegalArgumentException("No such group");
		}

		ArrayList<RoleView> roleViewList = new ArrayList<RoleView>(allRole.size());

		for (WsecRole role : allRole) {
			RoleView rv = new RoleView(role);
			rv.setAssignedToGroupId(groupId);
			if (group.getRoles().contains(role)) {
				rv.setAssignedToGroup(true);
			}
			roleViewList.add(rv);
		}

		return roleViewList;
	}

	// TODO: doczytać sposób na zrobienie tego bez dociągania grupy i usera z bazy
	public ArrayList<RoleView> fetchAllAvailableRolesMarkAssignedToUser(String caller, Long userId) {
		ArrayList<WsecRole> allRoles = baseDao.fetchAll(WsecRole.class);
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		ArrayList<RoleView> roleViews = new ArrayList<RoleView>(allRoles.size());
		for (WsecRole role : allRoles) {
			RoleView v = new RoleView(role);
			if (user.hasRole(role)) {
				v.setAssignedToUser(true);
				v.setAssignedToUserId(userId);
			}

			roleViews.add(v);
		}

		return roleViews;
	}

	public ArrayList<PermissionView> fetchAllAvailablePermissionsMarkAssignedToUser(String caller, Long userId) {

		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		Set<WsecPermission> userPermissions = user.getPermissions();

		ArrayList<WsecPermission> allPermissions = baseDao.fetchAll(WsecPermission.class);
		ArrayList<PermissionView> permissionViews = new ArrayList<PermissionView>();

		for (WsecPermission perm : allPermissions) {
			PermissionView pv = new PermissionView(perm);
			pv.setAssignedToUserId(userId);
			if (userPermissions.contains(perm)) {
				pv.setAssignedToUser(true);
			}
			permissionViews.add(pv);
		}
		return permissionViews;
	}

	public ArrayList<PermissionView> fetchAllAvailablePermissionsMarkAssignedToGroup(String caller, Long groupId) {

		WsecGroup group = baseDao.fetchById(WsecGroup.class, groupId);
		Set<WsecPermission> groupPermissions = group.getPermissions();
		ArrayList<WsecPermission> allPermissions = baseDao.fetchAll(WsecPermission.class);
		ArrayList<PermissionView> permissionViews = new ArrayList<PermissionView>();

		for (WsecPermission perm : allPermissions) {
			PermissionView pv = new PermissionView(perm);
			pv.setAssignedToGroupId(groupId);
			if (groupPermissions.contains(perm)) {
				pv.setAssignedToGroup(true);
			}
			permissionViews.add(pv);
		}
		return permissionViews;
	}


	public WsecUser saveUser(String caller, WsecUser user) {
		baseDao.saveOrUpdate(user);
		// user should have id set by now
		return user;
	}


	public WsecUser updateUser(String caller, WsecUser updatedUser) {
		WsecUser user = baseDao.fetchById(WsecUser.class, updatedUser.getId());
		user.setLogin(updatedUser.getLogin());
		user.setPassword(updatedUser.getPassword());
		user.setFirstName(updatedUser.getFirstName());
		user.setLastName(updatedUser.getLastName());
		user.setEmailAddress(updatedUser.getEmailAddress());
		user.setEnabled(updatedUser.isEnabled());
        user.setJobTitle(updatedUser.getJobTitle());
        user.setStreet(updatedUser.getStreet());
        user.setCity(updatedUser.getCity());
		return user;
	}

	public ArrayList<GroupView> fetchAllAvailableGroupsMarkAssignedToUser(String caller, Long userId) {
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		// tech groups
		user.getGroups();
		ArrayList<WsecGroup> allGroups = baseDao.fetchAll(WsecGroup.class);

		ArrayList<GroupView> allGroupsWithAssignedMarked = new ArrayList<GroupView>(allGroups.size());
		for (WsecGroup group : allGroups) {
			GroupView gv = new GroupView(group);
			gv.setAssignedToUserId(user.getId());
			if (user.getGroups().contains(group)) {
				gv.setAssigned(true);
			}
			allGroupsWithAssignedMarked.add(gv);
		}

		return allGroupsWithAssignedMarked;
	}

	public ArrayList<PermissionView> fetchInheritedPermissionsFromGroupForUser(String caller, Long userId) {
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		checkArgumentsNotNull("Użytkownik z podanym id nie istnieje w bazie", user);
		Set<WsecGroup> groups = user.getGroups();
		ArrayList<PermissionView> permissionViews = new ArrayList<PermissionView>();

		for (WsecGroup group : groups) {
			for (WsecPermission permission : group.getPermissions()) {
				PermissionView pv = new PermissionView(permission);
				pv.setInheritedFromGroup(group.getName());
				permissionViews.add(pv);
			}
		}

		return permissionViews;
	}

	public ArrayList<PermissionView> fetchInheritedPermissionsFromRoleForUser(String caller, Long userId) {
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		checkArgumentsNotNull("Użytkownik z podanym id nie istnieje w bazie", user);
		Set<WsecRole> roles = user.getRoles();
		ArrayList<PermissionView> permissionViews = new ArrayList<PermissionView>();

		for (WsecRole role : roles) {
			for (WsecPermission permission : role.getPermissions()) {
				PermissionView pv = new PermissionView(permission);
				pv.setInheritedFromRole(role.getName());
				permissionViews.add(pv);
			}
		}

		return permissionViews;
	}

	public ArrayList<PermissionView> fetchAllAvailablePermissionsMarkAssignedToRole(String caller, Long roleId) {

		WsecRole role = baseDao.fetchById(WsecRole.class, roleId);
		Set<WsecPermission> rolePermissions = role.getPermissions();
		ArrayList<WsecPermission> allPermissions = baseDao.fetchAll(WsecPermission.class);
		ArrayList<PermissionView> permissionViews = new ArrayList<PermissionView>();

		for (WsecPermission perm : allPermissions) {
			PermissionView pv = new PermissionView(perm);
			pv.setAssignedToRoleId(roleId);
			if (rolePermissions.contains(perm)) {
				pv.setAssignedToRole(true);
			}
			permissionViews.add(pv);
		}
		return permissionViews;
	}

	public ArrayList<PermissionView> fetchInheritedPermissionsFromRoleForGroup(String caller, Long groupId) {
		WsecGroup group = baseDao.fetchById(WsecGroup.class, groupId);
		checkArgumentsNotNull("Grupa o podanym id nie istnieje w bazie", groupId);
		Set<WsecRole> roles = group.getAllRoles();
		ArrayList<PermissionView> permissionViews = new ArrayList<PermissionView>();

		for (WsecRole role : roles) {
			for (WsecPermission permission : role.getPermissions()) {
				PermissionView pv = new PermissionView(permission);
				pv.setInheritedFromRole(role.getName());
				permissionViews.add(pv);
			}
		}

		return permissionViews;
	}

	public ArrayList<PermissionView> fetchInheritedPermissionsFromInhetiredRolesForUser(String caller, Long userId) {
		WsecUser user = baseDao.fetchById(WsecUser.class, userId);
		checkArgumentsNotNull("Użytkownik z podanym id nie istnieje w bazie", user);
		Set<WsecGroup> groups = user.getGroups();
		ArrayList<PermissionView> permissionViews = new ArrayList<PermissionView>();

		for (WsecGroup group : groups) {
			Set<WsecRole> roles = group.getRoles();
			for (WsecRole role : roles) {
				Set<WsecPermission> permissions = role.getPermissions();
				for (WsecPermission perm : permissions) {
					PermissionView pv = new PermissionView(perm);
					pv.setInheritedFromRole(role.getName());
					pv.setInheritedFromGroup(group.getName());
					permissionViews.add(pv);
				}
			}
		}
		return permissionViews;
	}


	public PagedQueryResult<ArrayList<? extends Serializable>> fetchPagedListWithOverallCount(
			String caller, Map<String, ? extends Serializable> queryParameters, Integer offset,
			Integer howMany, String sortColumn, Boolean desc, String clazz) {
		Class oclazz = loadClassForName(clazz);

//        demoData.initialize();
		return baseDao.fetchPagedListWithOverallCount(queryParameters, offset, howMany, sortColumn, desc, oclazz);
	}

	public Serializable fetchDomainObjectById(String caller, Long objectId, String clazz) {
		Class<Serializable> objectClass = loadClassForName(clazz);
		return baseDao.fetchById(objectClass, objectId);
	}

// -------------------------- OTHER METHODS --------------------------
	protected Class loadClassForName(String clazz) {
		try {
			Class oclazz = Class.forName(clazz);
			return oclazz;
		} catch (ClassNotFoundException e) {
			log.error("Query for nonexisting domain class", e);
			throw new IllegalArgumentException("Query for nonexisting domain class");
		}
	}


	public WsecGroup saveGroup(String caller, WsecGroup group) {
		baseDao.saveOrUpdate(group);
		return group;
	}


	public WsecGroup updateGroup(String caller, WsecGroup updatedGroup) {
		WsecGroup group = baseDao.fetchById(WsecGroup.class, updatedGroup.getId());
		group.setDescription(updatedGroup.getDescription());
		group.setName(updatedGroup.getName());
		return group;
	}


	public WsecRole saveRole(String caller, WsecRole role) {
		baseDao.saveOrUpdate(role);
		return role;
	}


	public WsecRole updateRole(String caller, WsecRole updatedRole) {
		WsecRole role = baseDao.fetchById(WsecRole.class, updatedRole.getId());
		role.setDescription(updatedRole.getDescription());
		role.setName(updatedRole.getName());
		return role;
	}


	public void deletePermission(String caller, WsecPermission permission) {
        List<WsecGroup> groupsToRemoveFromPermission = new ArrayList<WsecGroup>(permission.getReceivingGroups());
		for(WsecGroup group : groupsToRemoveFromPermission){
            group.removePermission(permission);
		}

        List<WsecRole> rolesToRemoveFromPermission = new ArrayList<WsecRole>(permission.getReceivingRoles());
		for(WsecRole role : rolesToRemoveFromPermission){
			role.removePermission(permission);
		}

        List<WsecUser> usersToRemoveFromPermission = new ArrayList<WsecUser>(permission.getReceivingUsers());
		for(WsecUser user : usersToRemoveFromPermission){
			user.removePermission(permission);
		}

		baseDao.delete(permission);
	}

	public WsecUser fetchLoggedInUser() {
        WsecUser wsecUser = ServerSecurity.getLoggedInUser();
        wsecUser.setPassword(null);
        return wsecUser;
	}

	public Set<WsecPermission> getPermissionsAssignedToRole(String caller, String roleName) {
		return roleDao.getRoleByName(roleName).getPermissions();
	}


}
