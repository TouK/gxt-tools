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



package pl.touk.wonderfulsecurity.beans;

import java.io.Serializable;
import java.util.List;

public class PermissionView extends WsecPermission implements Serializable {

	protected List<String> inheritedFromRoles;
	protected boolean assignedToUser = false;
	protected long assignedToUserId;
	protected String inheritedFromGroup;
	protected boolean assignedToGroup = false;
	protected long assignedToGroupId;
	protected String inheritedFromRole;
	protected boolean assignedToRole = false;
	protected long assignedToRoleId;

// --------------------------- CONSTRUCTORS ---------------------------
	public PermissionView() {
	}

	public PermissionView(WsecPermission perm) {
		super(perm);
	}

// --------------------- GETTER / SETTER METHODS ---------------------

	public boolean isAssignedToRole(){
		return assignedToRole;
	}

	public boolean isAssignedToUser() {
		return assignedToUser;
	}

	public boolean isAssignedToGroup() {
		return assignedToGroup;
	}

	public long getAssignedToUserId() {
		return assignedToUserId;
	}

	public List<String> getInheritedFromRoles() {
		return inheritedFromRoles;
	}

	public String getInheritedFromRole() {
		return inheritedFromRole;
	}

	public void setAssignedToUser(boolean assignedToUser) {
		this.assignedToUser = assignedToUser;
	}

	public void setInheritedFromRole(String role) {
		this.inheritedFromRole = role;
	}

	public void setInheritedFromRoles(List<String> inheritedFromRoles) {
		this.inheritedFromRoles = inheritedFromRoles;
	}

	public String getInheritedFromGroup() {
		return inheritedFromGroup;
	}

	public void setInheritedFromGroup(String inheritedFromGroup) {
		this.inheritedFromGroup = inheritedFromGroup;
	}

	public void setAssignedToGroup(boolean assignedToGroup) {
		this.assignedToGroup = assignedToGroup;
	}

	public long getAssignedToGroupId() {
		return assignedToGroupId;
	}

	public void setAssignedToGroupId(long assignedToGroupId) {
		this.assignedToGroupId = assignedToGroupId;
	}

	public long getAssignedToRoleId() {
		return assignedToRoleId;
	}

	public void setAssignedToRoleId(long assignedToRoleId) {
		this.assignedToRoleId = assignedToRoleId;
	}

	public void setAssignedToUserId(long assignedToUserId) {
		this.assignedToUserId = assignedToUserId;
	}

	public void setAssignedToRole(boolean assignedToRole) {
		this.assignedToRole = assignedToRole;
	}

}
