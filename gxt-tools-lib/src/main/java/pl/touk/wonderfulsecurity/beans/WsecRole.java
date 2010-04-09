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


import pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException;
import pl.touk.wonderfulsecurity.helpers.PermissionExclusion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Model object that represents system role
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
@Entity
@Table(name = "WSEC_ROLE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})}
)
public class WsecRole implements Serializable {
// ------------------------------ FIELDS ------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected String name;

    protected String description;

    @ManyToMany
    @JoinTable(name = "WSEC_PERMISSION_ROLE", joinColumns = {@JoinColumn(name = "ROLE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID")})
    protected Set<WsecPermission> permissions = new HashSet<WsecPermission>();

// --------------------------- CONSTRUCTORS ---------------------------

    public WsecRole() {
    }

    // copy constructor
    public WsecRole(WsecRole role) {
        this.id = role.id;
        this.name = role.name;
        this.description = role.description;
    }

    public WsecRole(String name, String description) {
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

    public Set<WsecPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<WsecPermission> permissions) {
        this.permissions = permissions;
    }

// ------------------------ CANONICAL METHODS ------------------------

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WsecRole)) {
            return false;
        }
        WsecRole rhs = (WsecRole) obj;
        return ((rhs.id == null ? this.id == null : rhs.id.equals(this.id))
                && (rhs.name == null ? this.name == null : rhs.name.equals(this.name)));
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + (id==null ? 0 : id.hashCode());
        result = 31 * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    public String toString() {
        return new StringBuilder().append("id: ").append(id).append("\nName: ").append(name).toString();
    }

// -------------------------- OTHER METHODS --------------------------

    public boolean addPermission(WsecPermission permission) throws PermissionCollisionException{
        PermissionExclusion.checkIfConflicts(this, permission);
		permission.receivingRoles.add(this);
        return permissions.add(permission);
    }

    public Set<WsecPermission> getAllPermissionExcludes(){
        Set<WsecPermission> result = new HashSet<WsecPermission>();
        for (WsecPermission perm : this.permissions){
            result.addAll(perm.getExcludes());
        }
        return result;
    }

	public Set<WsecPermission> getAllPermissions() {
		return permissions;
	}

    public boolean hasPermission(WsecPermission permission){
        return permissions.contains(permission);
    }

    public boolean removePermission(WsecPermission permission) {
		permission.receivingRoles.remove(this);
        return permissions.remove(permission);
    }
}
