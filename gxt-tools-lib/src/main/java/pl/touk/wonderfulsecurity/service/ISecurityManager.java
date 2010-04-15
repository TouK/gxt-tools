/*
 * Copyright (c) 2008 TouK.pl
 *
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

import pl.touk.wonderfulsecurity.beans.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.List;

import pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException;

/**
 * This is main interface for interaction with security subsystems.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public interface ISecurityManager {

// -------------------------- OTHER METHODS --------------------------

	/**
	 * Assigns permission to user
	 *
	 * @param caller       id of user who executes this method
	 * @param permissionId id of permission to assign
	 * @param userId       id of user to which assign permission to
     * @throws pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException when permission exclusion occurs
	 */
	public void assignPermissionToUser(String caller, Long permissionId, Long userId) throws PermissionCollisionException;

	/**
	 * Deassign permission from user
	 *
	 * @param caller       id of user who executes this method
	 * @param permissionId id of permission to deassign
	 * @param userId       id of user to deassign permission from
	 */
	public void deassignPermissionFromUser(String caller, Long permissionId, Long userId);

	/**
	 * Assigns role to user
	 *
	 * @param caller id of user who executes this method
	 * @param userId id of user to assign role to
	 * @param roleId id of role to assign to user
     * @throws pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException when permission exclusion occurs
	 */
	public void assignRoleToUser(String caller, Long roleId, Long userId) throws PermissionCollisionException;

	/**
	 * Assigns group to user
	 *
	 * @param caller  id of user who executes this method
	 * @param groupId id of group to assign
	 * @param userId  id of user to which assign group to
     * @throws pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException when permission exclusion occurs
	 */
	public void assignGroupToUser(String caller, Long groupId, Long userId) throws PermissionCollisionException;

	/**
	 * Assigns role to group
	 *
	 * @param caller  id of user who executes this method
	 * @param groupId id of group to assign role to
	 * @param roleId  id of role to assign to group
     * @throws pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException when permission exclusion occurs
	 */
	public void assignRoleToGroup(String caller, Long roleId, Long groupId) throws PermissionCollisionException;

	/**
	 * Assigns permission to role
	 *
	 * @param caller       id of user who executes this method
	 * @param permissionId id of permission to assign
	 * @param roleId       id of role to which assign permission to
     * @throws pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException when permission exclusion occurs
	 */
	public void assignPermissionToRole(String caller, Long permissionId, Long roleId) throws PermissionCollisionException;

	/**
	 * Deassigns permission from role
	 *
	 * @param caller       id of user who executes this method
	 * @param permissionId id of permission to deassign
	 * @param roleId       id of role to deassign permission from
	 */
	public void deassignPermissionFromRole(String caller, Long permissionId, Long roleId);

	/**
	 * Deassign group from user
	 *
	 * @param caller  id of user who executes this method
	 * @param groupId id of group to deassign
	 * @param userId  id of user from which to deassign a group
	 */
	public void deassignGroupFromUser(String caller, Long groupId, Long userId);

	/**
	 * Deassign role from group
	 *
	 * @param caller  id of user who executes this method
	 * @param groupId id of group to deassing role from
	 * @param roleId  id of role to deassign from group
	 */
	public void deassignRoleFromGroup(String caller, Long roleId, Long groupId);

	/**
	 * Deassign role from user
	 *
	 * @param caller id of user who executes this method
	 * @param roleId id of role to deassign
	 * @param userId id of user to deassign role from
	 */
	public void deassignRoleFromUser(String caller, Long roleId, Long userId);

	/**
	 * Fetch all available groups
	 *
	 * @param caller id of user who executes this method
	 */
	public ArrayList<WsecGroup> fetchAllAvailableGroups(String caller);

	/**
	 * Fetches all available groups plus specially marks those assigned for user @see GroupView
	 *
	 * @param caller id of user who executes this method
	 * @param userId mark groups assigned to this user
	 */
	public ArrayList<GroupView> fetchAllAvailableGroupsMarkAssignedToUser(String caller, Long userId);

	/**
	 * Fetch all available roles
	 *
	 * @param caller id of user who executes this method
	 */
	public ArrayList<WsecRole> fetchAllAvailableRoles(String caller);

	/**
	 * Fetch all available permissions
	 *
	 * @param caller
	 */
	public ArrayList<WsecPermission> fetchAllAvailablePermissions(String caller);

	/**
	 * Fetch all available roles plus mark assigned those to groupId
	 */
	public ArrayList<RoleView> fetchAllAvailableRolesMarkAssignedToGroup(String caller, Long groupId);

	/**
	 * Fetch all available roles plus mark those assinged to userId
	 */
	public ArrayList<RoleView> fetchAllAvailableRolesMarkAssignedToUser(String caller, Long userId);

	/**
	 * Fetch any domain object by id
	 *
	 * @param objectId id of domain object to fetch
	 * @param clazz    string literal class name (full with package prefix)
	 * @return domain object or null if not found
	 */
	public Serializable fetchDomainObjectById(String caller, Long objectId, String clazz);

	/**
	 * Fetch inherited roles for user
	 *
	 * @see pl.touk.wonderfulsecurity.beans.RoleView#inheritedFromGroup
	 */
	public ArrayList<RoleView> fetchInheritedRolesForUser(String caller, Long userId);

	/**
	 * Fetch paged list of domain object with overall count. For detailed documentation see
	 * {@link pl.touk.wonderfulsecurity.dao.WsecBaseDao#fetchPagedListWithOverallCount(java.util.Map, Integer, Integer, String, Boolean, Class)}
	 * as this is simple delegation.
	 *
	 * @param clazz string literal class name (full with package prefix)
	 */
	public PagedQueryResult fetchPagedListWithOverallCount(
			String caller, Map<String, ? extends Serializable> queryParameters, Integer offset,
			Integer howMany, String sortColumn, Boolean desc, String clazz);

	/**
	 * Add new user
	 *
	 * @param caller id of user who executes this method
	 * @param user   user object to save
	 */
	public WsecUser saveUser(String caller, WsecUser user);

	public WsecUser updateUser(String caller, WsecUser user);

	public void assignPermissionToGroup(String caller, Long permissionId, Long groupId);

	public void deassignPermissionFromGroup(String caller, Long permissionId, Long groupId);

	public ArrayList<PermissionView> fetchAllAvailablePermissionsMarkAssignedToUser(String caller, Long userId);

	public ArrayList<PermissionView> fetchInheritedPermissionsFromGroupForUser(String caller, Long userId);

	public ArrayList<PermissionView> fetchInheritedPermissionsFromRoleForUser(String caller, Long userId);

	public ArrayList<PermissionView> fetchAllAvailablePermissionsMarkAssignedToGroup(String caller, Long groupId);

	public ArrayList<PermissionView> fetchAllAvailablePermissionsMarkAssignedToRole(String caller, Long roleId);

	public ArrayList<PermissionView> fetchInheritedPermissionsFromRoleForGroup(String caller, Long groupId);

    public WsecGroup saveGroup(String caller, WsecGroup group);

	public WsecGroup updateGroup(String caller, WsecGroup group);

    public WsecRole saveRole(String caller, WsecRole role);

	public WsecRole updateRole(String caller, WsecRole role);

    public WsecUser fetchLoggedInUser();

	public ArrayList<PermissionView> fetchInheritedPermissionsFromInhetiredRolesForUser(String caller, Long userId);

	public void deletePermission(String caller, WsecPermission permission);

	public Set<WsecPermission> getPermissionsAssignedToRole(String caller, String roleName);
}
