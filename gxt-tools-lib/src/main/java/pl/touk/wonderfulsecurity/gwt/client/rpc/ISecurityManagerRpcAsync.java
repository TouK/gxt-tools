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



package pl.touk.wonderfulsecurity.gwt.client.rpc;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import pl.touk.wonderfulsecurity.beans.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException;

/**
 * Asynchronous version of {@link pl.touk.wonderfulsecurity.gwt.client.rpc.ISecurityManagerRpcAsync} for gwt RPC
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public interface ISecurityManagerRpcAsync {
// -------------------------- OTHER METHODS --------------------------

	public RequestBuilder assignPermissionToUser(String caller, Long permissionId, Long userId, AsyncCallback callback) throws PermissionCollisionException;

	public RequestBuilder deassignPermissionFromUser(String caller, Long permissionId, Long userId, AsyncCallback callback);

	public RequestBuilder assignPermissionToRole(String caller, Long permissionId, Long roleId, AsyncCallback callback) throws PermissionCollisionException;

	public RequestBuilder deassignPermissionFromRole(String caller, Long permissionId, Long roleId, AsyncCallback callback);

	public RequestBuilder assignGroupToUser(String caller, Long groupId, Long userId, AsyncCallback callback) throws PermissionCollisionException;

	public RequestBuilder assignRoleToGroup(String caller, Long roleId, Long groupId, AsyncCallback callback) throws PermissionCollisionException;

	public RequestBuilder assignRoleToUser(String caller, Long roleId, Long userId, AsyncCallback callback) throws PermissionCollisionException;

	public RequestBuilder deassignGroupFromUser(String caller, Long groupId, Long userId, AsyncCallback callback);

	public RequestBuilder deassignRoleFromGroup(String caller, Long roleId, Long groupId, AsyncCallback callback);

	public RequestBuilder deassignRoleFromUser(String caller, Long roleId, Long userId, AsyncCallback callback);

	public void doNotImplement(WsecUser user, AsyncCallback callback);

	public RequestBuilder fetchAllAvailableGroups(String caller, AsyncCallback<ArrayList<WsecGroup>> callback);

	public RequestBuilder fetchAllAvailablePermissions(String caller, AsyncCallback<ArrayList<WsecPermission>> callback);

	public RequestBuilder fetchAllAvailableGroupsMarkAssignedToUser(String caller, Long userId, AsyncCallback<ArrayList<GroupView>> callback);

	public RequestBuilder fetchAllAvailableRoles(String caller, AsyncCallback<ArrayList<WsecRole>> callback);

	public RequestBuilder fetchAllAvailableRolesMarkAssignedToGroup(String caller, Long groupId, AsyncCallback<ArrayList<RoleView>> callback);

	public RequestBuilder fetchAllAvailableRolesMarkAssignedToUser(String caller, Long userId, AsyncCallback<ArrayList<RoleView>> callback);

	public RequestBuilder fetchDomainObjectById(String caller, Long userId, String clazzName, AsyncCallback<Serializable> callback);

	public RequestBuilder fetchInheritedRolesForUser(String caller, Long userId, AsyncCallback<ArrayList<RoleView>> callback);

	public RequestBuilder fetchPagedListWithOverallCount(String caller, Map<String, Object> queryParameters, Integer offset, Integer howMany,
			String sortColumn, Boolean desc, String clazz,
			AsyncCallback<PagedQueryResult<ArrayList<? extends Serializable>>> callback);

	public RequestBuilder saveUser(String caller, WsecUser user, AsyncCallback<WsecUser> callback);

	public RequestBuilder updateUser(String caller, WsecUser user, AsyncCallback<WsecUser> callback);

	public RequestBuilder fetchAllAvailablePermissionsMarkAssignedToUser(String caller, Long userId, AsyncCallback<ArrayList<PermissionView>> callback);

	public RequestBuilder assignPermissionToGroup(String caller, Long permissionId, Long groupId, AsyncCallback callback) throws PermissionCollisionException;

	public RequestBuilder deassignPermissionFromGroup(String caller, Long permissionId, Long groupId, AsyncCallback callback);

	public RequestBuilder fetchInheritedPermissionsFromGroupForUser(String caller, Long userId, AsyncCallback<ArrayList<PermissionView>> callback);

	public RequestBuilder fetchInheritedPermissionsFromRoleForUser(String caller, Long userId, AsyncCallback<ArrayList<PermissionView>> callback);

	public RequestBuilder fetchAllAvailablePermissionsMarkAssignedToGroup(String caller, Long groupId, AsyncCallback<ArrayList<PermissionView>> callback);

	public RequestBuilder fetchAllAvailablePermissionsMarkAssignedToRole(String caller, Long roleId, AsyncCallback<ArrayList<PermissionView>> callback);

	public RequestBuilder fetchInheritedPermissionsFromRoleForGroup(String caller, Long groupId, AsyncCallback<ArrayList<PermissionView>> callback);

	public RequestBuilder saveGroup(String caller, WsecGroup group, AsyncCallback<WsecGroup> callback);

	public RequestBuilder updateGroup(String caller, WsecGroup group, AsyncCallback<WsecGroup> callback);

	public RequestBuilder saveRole(String caller, WsecRole role, AsyncCallback<WsecRole> callback);

	public RequestBuilder updateRole(String caller, WsecRole role, AsyncCallback<WsecRole> callback);

    public void fetchLoggedInUser(AsyncCallback<WsecUser> callback);

	public RequestBuilder fetchInheritedPermissionsFromInhetiredRolesForUser(String caller, Long userId, AsyncCallback<WsecUser> callback);

	public RequestBuilder deletePermission(String caller, WsecPermission permission, AsyncCallback<WsecUser> callback);

	public RequestBuilder getPermissionsAssignedToRole(String caller, String roleName, AsyncCallback<WsecUser> callback);
}
