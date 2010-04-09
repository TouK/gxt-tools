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
package pl.touk.wonderfulsecurity.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException;
import pl.touk.wonderfulsecurity.helpers.PermissionExclusion;

/**
 * Model object that represents group of users. Navigation from group to users is not needed.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
@Entity
@Table(name = "WSEC_GROUP",
uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class WsecGroup implements Serializable {
// ------------------------------ FIELDS ------------------------------

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

    protected String name;
	
    protected String description;

	@ManyToMany
	@JoinTable(name = "WSEC_GROUP_ROLE", joinColumns = {@JoinColumn(name = "GROUP_ID")},
	inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
	protected Set<WsecRole> roles = new HashSet<WsecRole>();

    @ManyToMany
	@JoinTable(name = "WSEC_GROUP_PERMISSION", joinColumns = {@JoinColumn(name = "GROUP_ID")},
	inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID")})
	protected Set<WsecPermission> permissions = new HashSet<WsecPermission>();
    
    @ManyToMany(mappedBy="groups")
    protected Set<WsecUser> users = new HashSet<WsecUser>();


// --------------------------- CONSTRUCTORS ---------------------------
	/**
	 * Copy constructor
	 * */
	public WsecGroup(WsecGroup group) {
		this(group.getId(), group.getName(), group.getDescription());
	}

	public WsecGroup(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public WsecGroup() {
	}

	public WsecGroup(String name, String description) {
		this.name = name;
		this.description = description;
	}

// --------------------- GETTER / SETTER METHODS ---------------------
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<WsecRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<WsecRole> roles) {
		this.roles = roles;
	}

	public Set<WsecPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<WsecPermission> permissions) {
		this.permissions = permissions;
	}

    public Set<WsecUser> getUsers() {
        return users;
    }

    public void setUsers(Set<WsecUser> users) {
        this.users = users;
    }

	// ------------------------ CANONICAL METHODS ------------------------
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof WsecGroup)) {
			return false;
		}
		WsecGroup rhs = (WsecGroup) obj;
		return ((rhs.id == null ? this.id == null : rhs.id.equals(this.id)) && (rhs.name == null ? this.name == null : rhs.name.equals(this.name)));
	}

	public int hashCode() {
		int result = 17;
		result = 31 * result + (id==null ? 0 : id.hashCode());
		result = 31 * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	public String toString() {
		return new StringBuilder().append("id:").append(id).append("\nname:").append(name).toString();
	}

// -------------------------- OTHER METHODS --------------------------
	public boolean addUser(WsecUser user){
        PermissionExclusion.checkIfConflicts(user, this);
        user.getGroups().add(this);
        return users.add(user);
    }

    public boolean removeUser(WsecUser user){
        user.getGroups().remove(this);
        return users.remove(user);
    }

    public boolean addRole(WsecRole role)  throws PermissionCollisionException{
		PermissionExclusion.checkIfConflicts(this, role);

		return roles.add(role);
	}

	public boolean deleteRole(WsecRole wsecRole) {
		return roles.remove(wsecRole);
	}

	public Set<WsecRole> getAllRoles() {
		return roles;
	}

	public boolean addPermission(WsecPermission permission)  throws PermissionCollisionException{
        PermissionExclusion.checkIfConflicts(this, permission);
		permission.getReceivingGroups().add(this);
		return permissions.add(permission);
	}

	public boolean removePermission(WsecPermission permission) {
        permission.getReceivingGroups().remove(this);
        return permissions.remove(permission);
	}

	public Set<WsecPermission> getAllPermissions() {
		Set<WsecPermission> result = new HashSet<WsecPermission>();
		result.addAll(getPermissionsInheritedFromRoles());
		result.addAll(this.permissions);
		return result;
	}

	public Set<WsecPermission> getPermissionsInheritedFromRoles() {
		Set<WsecPermission> result = new HashSet<WsecPermission>();
		for (WsecRole role : roles) {
			result.addAll(role.getPermissions());
		}
		return result;
	}

	public Set<WsecPermission> getAllPermissionExcludes() {
		Set<WsecPermission> result = new HashSet<WsecPermission>();
		Set<WsecPermission> perms = getAllPermissions();

		for (WsecPermission permission : perms) {
			result.addAll(permission.getExcludes());
		}
		return result;
	}

	public boolean hasPermission(WsecPermission permission){
        return permissions.contains(permission);
    }
}
