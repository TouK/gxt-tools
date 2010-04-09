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

import pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException;
import pl.touk.wonderfulsecurity.helpers.PermissionExclusion;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Model object that represents system user. Fields are pretty self explanatory.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
@Entity
@Table(name = "WSEC_USER",
uniqueConstraints = {@UniqueConstraint(columnNames = {"login"})})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("WU")
public class WsecUser implements Serializable {
// ------------------------------ FIELDS ------------------------------

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

    protected String firstName,  lastName,  fullName,  emailAddress,  street,  city,  code,  mailStreet,  mailCity,  mailCode,  login,  password, jobTitle;

    @Basic(optional = false)
	@Column(nullable = false)
	protected boolean enabled = true;

    @ManyToMany
	@JoinTable(name = "WSEC_USER_GROUP", joinColumns = {@JoinColumn(name = "USER_ID")},
	inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")})
	protected Set<WsecGroup> groups = new HashSet<WsecGroup>();

    @ManyToMany
	@JoinTable(name = "WSEC_USER_ROLE", joinColumns = {@JoinColumn(name = "USER_ID")},
	inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
	protected Set<WsecRole> roles = new HashSet<WsecRole>();

    @ManyToMany
	@JoinTable(name = "WSEC_USER_PERMISSION", joinColumns = {@JoinColumn(name = "USER_ID")},
	inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID")})
	protected Set<WsecPermission> permissions = new HashSet<WsecPermission>();

    @OneToMany
	@JoinColumn(name = "SUPERVISOR_ID")
	protected Set<WsecUser> subordinates = new HashSet<WsecUser>();
	
    @ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "SUPERVISOR_ID")
	protected WsecUser supervisor;

// --------------------------- CONSTRUCTORS ---------------------------
	public WsecUser() {
	}

	public WsecUser(String firstName, String lastName, String fullName, String emailAddress, String street,
			String city, String code, String mailStreet, String mailCity, String mailCode, String login, String password, String enabled) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.emailAddress = emailAddress;
		this.street = street;
		this.city = city;
		this.code = code;
		this.mailStreet = mailStreet;
		this.mailCity = mailCity;
		this.mailCode = mailCode;
		this.login = login;
		this.password = password;
		this.enabled = Boolean.valueOf(enabled);
	}

// --------------------- GETTER / SETTER METHODS ---------------------
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Set<WsecGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<WsecGroup> groups) {
		this.groups = groups;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMailCity() {
		return mailCity;
	}

	public void setMailCity(String mailCity) {
		this.mailCity = mailCity;
	}

	public String getMailCode() {
		return mailCode;
	}

	public void setMailCode(String mailCode) {
		this.mailCode = mailCode;
	}

	public String getMailStreet() {
		return mailStreet;
	}

	public void setMailStreet(String mailStreet) {
		this.mailStreet = mailStreet;
	}

    /**
     * Tabela w bazie danych zwraca nulla kiedy zapisujemy tam pustego stringa.
     * Nulla powinnismy traktowac jako puste haslo
     */
	public String getPassword() {
        if (password == null) {
            return "";
        }
        return password;
    }

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns roles added to this particular instance of user only.
	 * <p/>
	 * To fetch roles inherited from groups use #getAllRoles method
	 */
	public Set<WsecRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<WsecRole> roles) {
		this.roles = roles;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Set<WsecUser> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(Set<WsecUser> subordinates) {
		this.subordinates = subordinates;
	}

	public WsecUser getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(WsecUser supervisor) {
		this.supervisor = supervisor;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<WsecPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<WsecPermission> permissions) {
		this.permissions = permissions;
	}

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    // ------------------------ CANONICAL METHODS ------------------------

    public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof WsecUser)) {
			return false;
		}
		WsecUser rhs = (WsecUser) obj;
		return ((rhs.id == null ? this.id == null : rhs.id.equals(this.id)) && (rhs.login == null ? this.login == null : rhs.login.equals(this.login)));
	}

	public int hashCode() {
		int result = 17;
		result = 31 * result + (id==null?0:id.hashCode());
		result = 31 * result + (login == null ? 0 : login.hashCode());
		return result;
	}

	public String toString() {
		return new StringBuilder().append(super.toString()).append("\n").append("id:").append(this.id).append("\n").append("firstname:").append(this.firstName).append("\n").append("lastName:").append(this.lastName).append("\n").append("emailAddress:").append(this.emailAddress).append("\n").append("login:").append(this.login).append("\n").toString();
	}

// -------------------------- OTHER METHODS --------------------------

// -------------------------- ~ PERMISSION ~ --------------------------
	public boolean addPermission(WsecPermission permission) throws PermissionCollisionException {
		if (permission == null) {
			throw new IllegalArgumentException("Permission cannot be null here");
		}

		PermissionExclusion.checkIfConflicts(this, permission);
		permission.getReceivingUsers().add(this);
		return permissions.add(permission);
	}

	public boolean removePermission(WsecPermission permission) {
		if (permission == null) {
			throw new IllegalArgumentException("Permission cannot be null here");
		}
		permission.getReceivingUsers().remove(this);
		return permissions.remove(permission);
	}

	public Set<WsecPermission> getAllPermissions() {
		Set<WsecPermission> result = new HashSet<WsecPermission>();

		result.addAll(this.permissions);

		for (WsecRole role : this.roles) {
			result.addAll(role.getPermissions());
		}
		for (WsecGroup group : this.groups) {
			result.addAll(group.getAllPermissions());
		}

		return result;
	}

	public Set<WsecPermission> getAllPermissionExcludes() {

		Set<WsecPermission> result = new HashSet<WsecPermission>();
		for (WsecGroup gr : this.groups) {
			result.addAll(gr.getAllPermissionExcludes());
		}
		for (WsecRole ro : this.roles) {
			result.addAll(ro.getAllPermissionExcludes());
		}
		for (WsecPermission pe : this.permissions) {
			result.addAll(pe.getExcludes());
		}
		return result;
	}

	public boolean hasPermission(WsecPermission permission) {
		return getAllPermissions().contains(permission);
	}

// -------------------------- ~ ROLE ~ --------------------------

	//TODO uwaga analogiczna jak w addPermission
	public boolean addRole(WsecRole role) throws PermissionCollisionException {
		if (role == null) {
			throw new IllegalArgumentException("Role cannot be null here");
		}


		PermissionExclusion.checkIfConflicts(this, role);

		return roles.add(role);
	}

	public boolean removeRole(WsecRole role) {
		if (role == null) {
			throw new IllegalArgumentException("Role cannot be null here");
		}

		return roles.remove(role);
	}

	/**
	 * Return roles inherited from groups and roles added
	 * straight to this user
	 */
	public Set<WsecRole> getAllRoles() {
		Set<WsecRole> roles = new HashSet<WsecRole>();
		for (WsecGroup group : groups) {
			roles.addAll(group.getRoles());
		}
		roles.addAll(this.roles);
		return roles;
	}

	public boolean hasRole(WsecRole role) {
		return roles.contains(role);
	}

// -------------------------- ~ GROUP ~ --------------------------
	public boolean addToGroup(WsecGroup group) throws PermissionCollisionException {
		PermissionExclusion.checkIfConflicts(this, group);

        group.getUsers().add(this);
		return groups.add(group);
	}

	public boolean removeFromGroup(WsecGroup group) {
        group.getUsers().remove(this);
        return groups.remove(group);
	}

// -------------------------- ~ SUBORDINATE ~ --------------------------
	public boolean addSubordinate(WsecUser subordinate) {
		subordinate.setSupervisor(this);
		return subordinates.add(subordinate);
	}

	public boolean hasSubordinates() {
		return subordinates.isEmpty();
	}
}
