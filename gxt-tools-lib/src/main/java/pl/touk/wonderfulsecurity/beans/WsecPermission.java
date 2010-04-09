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

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Model object that represents permission.
 *
 * @author Szymon Doroz - sdr@touk.pl
 */
@Entity
@Table(name="WSEC_PERMISSION",
uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})

public class WsecPermission implements Serializable {

    public static enum SystemPermissions{
        WSEC_USERS_TAB,
        WSEC_PERMISSIONS_TAB,
        WSEC_ROLES_TAB,
        WSEC_GROUPS_TAB,
        WSEC_ADD_USR_BTN,
        WSEC_ADD_ROLE_BTN,
        WSEC_ADD_GRP_BTN,
        WSEC_LOGIN_CHANGE,
        WSEC_GRP_NAME_CHANGE,
        WSEC_ROLE_NAME_CHANGE,
        WSEC_PASSWORD_CHANGE,
        WSEC_CHNG_GRP_4USER,
        WSEC_CHNG_ROLE_4USER,
        WSEC_CHNG_ROLE_4GRP,
        WSEC_CHNG_PERM_4USER,
        WSEC_CHNG_PERM_4ROLE,
        WSEC_CHNG_PERM_4GRP,
        WSEC_SAVE_USR_DTLS,
        WSEC_SAVE_ROLE_DTLS,
        WSEC_SAVE_GRP_DTLS,
        WSEC_ALLOW_EMPTY_PASSWORD
    }

    public static List<WsecPermission> SYSTEM_PERMISSIONS_COLLECTION = new ArrayList();

    public static WsecPermission WSEC_USERS_TAB = new WsecPermission(SystemPermissions.WSEC_USERS_TAB.name(), "Uprawnienie do oglądania zakładki Użytkownicy");
    public static WsecPermission WSEC_PERMISSIONS_TAB = new WsecPermission(SystemPermissions.WSEC_PERMISSIONS_TAB.name(), "Uprawnienie do oglądania zakładki Uprawnienia");
    public static WsecPermission WSEC_ROLES_TAB = new WsecPermission(SystemPermissions.WSEC_ROLES_TAB.name(), "Uprawnienie do oglądania zakładki Role");
    public static WsecPermission WSEC_GROUPS_TAB = new WsecPermission(SystemPermissions.WSEC_GROUPS_TAB.name(), "Uprawnienie do oglądania zakładki Grupy");
    public static WsecPermission WSEC_ADD_USR_BTN = new WsecPermission(SystemPermissions.WSEC_ADD_USR_BTN.name(), "Uprawnienie do przycisku Dodaj nowego użytkownika");
    public static WsecPermission WSEC_ADD_ROLE_BTN = new WsecPermission(SystemPermissions.WSEC_ADD_ROLE_BTN.name(), "Uprawnienie do przycisku Dodaj nową rolę");
    public static WsecPermission WSEC_ADD_GRP_BTN = new WsecPermission(SystemPermissions.WSEC_ADD_GRP_BTN.name(), "Uprawnienie do przycisku Dodaj nową grupę");
    public static WsecPermission WSEC_LOGIN_CHANGE = new WsecPermission(SystemPermissions.WSEC_LOGIN_CHANGE.name(), "Uprawnienie do modyfikacji pola login");
    public static WsecPermission WSEC_GRP_NAME_CHANGE = new WsecPermission(SystemPermissions.WSEC_GRP_NAME_CHANGE.name(), "Uprawnienie do modyfikacji pola Nazwa dla Grupy użytkowników");
    public static WsecPermission WSEC_ROLE_NAME_CHANGE = new WsecPermission(SystemPermissions.WSEC_ROLE_NAME_CHANGE.name(), "Uprawnienie do modyfikacji pola Nazwa dla roli");
    public static WsecPermission WSEC_PASSWORD_CHANGE = new WsecPermission(SystemPermissions.WSEC_PASSWORD_CHANGE.name(), "Uprawnienie do modyfikacji pola hasło");
    public static WsecPermission WSEC_CHNG_GRP_4USER = new WsecPermission(SystemPermissions.WSEC_CHNG_GRP_4USER.name(), "Uprawnienie do przycisku zapisz zmiany w grupie dla zakładki Edycja użytkownika");
    public static WsecPermission WSEC_CHNG_ROLE_4USER = new WsecPermission(SystemPermissions.WSEC_CHNG_ROLE_4USER.name(), "Uprawnienie do przycisku zapisz zmiany w rolach dla zakładki Edycja użytkownika");
    public static WsecPermission WSEC_CHNG_ROLE_4GRP = new WsecPermission(SystemPermissions.WSEC_CHNG_ROLE_4GRP.name(), "Uprawnienie do przycisku zapisz zmiany w rolach dla zakładki Edycja grupy");
    public static WsecPermission WSEC_CHNG_PERM_4USER = new WsecPermission(SystemPermissions.WSEC_CHNG_PERM_4USER.name(), "Uprawnienie do przycisku zapisz zmiany w uprawnieniach dla zakładki Edycja użytkownika");
    public static WsecPermission WSEC_CHNG_PERM_4ROLE = new WsecPermission(SystemPermissions.WSEC_CHNG_PERM_4ROLE.name(), "Uprawnienie do przycisku zapisz zmiany w uprawnieniach dla zakładki Edycja roli");
    public static WsecPermission WSEC_CHNG_PERM_4GRP = new WsecPermission(SystemPermissions.WSEC_CHNG_PERM_4GRP.name(), "Uprawnienie do przycisku zapisz zmiany w uprawnieniach dla zakładki Edycja grupy");
    public static WsecPermission WSEC_SAVE_USR_DTLS = new WsecPermission(SystemPermissions.WSEC_SAVE_USR_DTLS.name(), "Uprawnienie do przycisku zapisz dla danych personalnych na zakładce Edycja użytkownika ...");
    public static WsecPermission WSEC_SAVE_ROLE_DTLS = new WsecPermission(SystemPermissions.WSEC_SAVE_ROLE_DTLS.name(), "Uprawnienie do przycisku zapisz dla danych szczegółowych na zakładce Edycja roli ...");
    public static WsecPermission WSEC_SAVE_GRP_DTLS = new WsecPermission(SystemPermissions.WSEC_SAVE_GRP_DTLS.name(), "Uprawnienie do przycisku zapisz dla danych szczegółowych grupy na zakładce Edycja grupy ...");
    public static WsecPermission WSEC_ALLOW_EMPTY_PASSWORD = new WsecPermission(SystemPermissions.WSEC_ALLOW_EMPTY_PASSWORD.name(), "Uprawnienie dające możliwość dodawania użytkownika z pustym hasłem");

    static {
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_USERS_TAB);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_PERMISSIONS_TAB);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_ROLES_TAB);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_GROUPS_TAB);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_ADD_USR_BTN);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_ADD_ROLE_BTN);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_ADD_GRP_BTN);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_LOGIN_CHANGE);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_GRP_NAME_CHANGE);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_ROLE_NAME_CHANGE);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_PASSWORD_CHANGE);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_CHNG_GRP_4USER);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_CHNG_ROLE_4USER);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_CHNG_ROLE_4GRP);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_CHNG_PERM_4USER);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_CHNG_PERM_4ROLE);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_CHNG_PERM_4GRP);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_SAVE_USR_DTLS);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_SAVE_ROLE_DTLS);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_SAVE_GRP_DTLS);
        SYSTEM_PERMISSIONS_COLLECTION.add(WSEC_ALLOW_EMPTY_PASSWORD);
    }
// ------------------------------ FIELDS ------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected String name;
    
    protected String description;

	@ManyToMany(mappedBy = "permissions")
	protected Set<WsecRole> receivingRoles = new HashSet<WsecRole>();

	@ManyToMany(mappedBy = "permissions")
	protected Set<WsecGroup> receivingGroups = new HashSet<WsecGroup>();

	@ManyToMany(mappedBy = "permissions")
	protected Set<WsecUser> receivingUsers = new HashSet<WsecUser>();
    /**
     * This field is used, to determine exclusion of permissions.
     *
     * If A exludes with B (hence B implicitly exludes with A), we only need to add B to A.exludes
     * Note that exlusion doesn't have to be declared symmetric, but it will work as if it were. 
     * 
     */
    @OneToMany
    @JoinColumn(name = "EXCLUDES_ID")
    protected Set<WsecPermission> excludes = new HashSet<WsecPermission>();

    // --------------------------- CONSTRUCTORS ---------------------------
    public WsecPermission() {
    }

    public WsecPermission(String name) {
        this.name = name;
    }

    public WsecPermission(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public WsecPermission(WsecPermission perm) {
        this.id = perm.id;
        this.name = perm.name;
        this.description = perm.description;
    }

    // --------------------- GETTER / SETTER METHODS ---------------------

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<WsecPermission> getExcludes() {
        return excludes;
    }

    public void setExcludes(Set<WsecPermission> excludes) {
        this.excludes = excludes;
    }

	public Set<WsecRole> getReceivingRoles() {
		return receivingRoles;
	}

	public void setReceivingRoles(Set<WsecRole> receivingRoles) {
		this.receivingRoles = receivingRoles;
	}

	public Set<WsecGroup> getReceivingGroups() {
		return receivingGroups;
	}

	public void setReceivingGroups(Set<WsecGroup> receivingGroups) {
		this.receivingGroups = receivingGroups;
	}

	public Set<WsecUser> getReceivingUsers() {
		return receivingUsers;
	}

	public void setReceivingUsers(Set<WsecUser> receivingUsers) {
		this.receivingUsers = receivingUsers;
	}

    // ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WsecPermission)) return false;

        WsecPermission that = (WsecPermission) o;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = 17;
        result = 31 * result + (name==null ? 0 : name.hashCode());

        return result;
    }

    public String toString() {
        return new StringBuilder().append("id: ").append(id).append("\nName: ").append(name).toString();
    }
}
