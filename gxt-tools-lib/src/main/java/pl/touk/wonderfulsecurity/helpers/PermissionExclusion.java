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



package pl.touk.wonderfulsecurity.helpers;

import pl.touk.wonderfulsecurity.beans.WsecGroup;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.beans.WsecRole;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException;

import java.util.HashSet;
import java.util.Set;

public class PermissionExclusion {

    public static void checkIfConflicts(WsecPermission oldPerm, WsecPermission newPerm) throws PermissionCollisionException {

        if (oldPerm.getExcludes().contains(newPerm) || newPerm.getExcludes().contains(oldPerm)) {
            throw new PermissionCollisionException("Uprawnienie: " + newPerm.getName() + " wyklucza sie z: " + oldPerm.getName());
        }
    }

    public static void checkIfConflicts(WsecRole role, WsecPermission newPerm) throws PermissionCollisionException {

        Set<WsecPermission> allPermissionExcludesInRole = role.getAllPermissionExcludes();

        if (allPermissionExcludesInRole.contains(newPerm)) {
            throw new PermissionCollisionException("Uprawnienie: " + newPerm.getName() + " wyklucza sie z: " + role.getName());
        }

        Set<WsecPermission> newPermExcludes = newPerm.getExcludes();
        Set<WsecPermission> allPermissionsInRole = role.getAllPermissions();

        checkIfSetsAreDisjoint(newPermExcludes, allPermissionsInRole, newPerm.getName() + " wyklucza sie z: " + role.getName());
    }

    public static void checkIfConflicts(WsecGroup group, WsecPermission newPerm) throws PermissionCollisionException {

        Set<WsecPermission> allPermissionExcludesInGroup = group.getAllPermissionExcludes();
        if (allPermissionExcludesInGroup.contains(newPerm)) {
            throw new PermissionCollisionException("Uprawnienie: " + newPerm.getName() + " wyklucza sie z: " + group.getName());
        }

        Set<WsecPermission> newPermExcludes = newPerm.getExcludes();
        Set<WsecPermission> allPermissionsInGroup = group.getAllPermissions();

        checkIfSetsAreDisjoint(newPermExcludes, allPermissionsInGroup, group.getName() + " wyklucza sie z: " + newPerm.getName());
    }

    public static void checkIfConflicts(WsecGroup group, WsecRole newRole) throws PermissionCollisionException {

        String errorMessage = group.getName() + " wyklucza sie z: " + newRole.getName();

        Set<WsecPermission> groupExcludes = group.getAllPermissionExcludes();
        Set<WsecPermission> newRolePermissions = newRole.getAllPermissions();

        checkIfSetsAreDisjoint(groupExcludes, newRolePermissions, errorMessage);

        Set<WsecPermission> groupPermissions = group.getAllPermissions();
        Set<WsecPermission> newRoleExcludes = newRole.getAllPermissionExcludes();

        checkIfSetsAreDisjoint(newRoleExcludes, groupPermissions, errorMessage);
    }

    public static void checkIfConflicts(WsecUser user, WsecPermission newPerm) throws PermissionCollisionException {

        String errorMessage = "Nie mozna dodac: " + newPerm.getName();

        Set<WsecPermission> userPermissionExcludes = user.getAllPermissionExcludes();
        if (userPermissionExcludes.contains(newPerm)) {
            throw new PermissionCollisionException(errorMessage);
        }

        Set<WsecPermission> newPermExcludes = newPerm.getExcludes();
        Set<WsecPermission> allUserPermissions = user.getAllPermissions();

        checkIfSetsAreDisjoint(newPermExcludes, allUserPermissions, errorMessage);
    }

    public static void checkIfConflicts(WsecUser user, WsecRole newRole) throws PermissionCollisionException {

        String errorMessage = "Nie mozna dodac: " + newRole.getName();
        Set<WsecPermission> userPermissionExcludes = user.getAllPermissionExcludes();
        Set<WsecPermission> newRolePermissions = newRole.getAllPermissions();

        checkIfSetsAreDisjoint(userPermissionExcludes, newRolePermissions, errorMessage);

        Set<WsecPermission> userPermissions = user.getAllPermissions();
        Set<WsecPermission> newRoleExcludes = newRole.getAllPermissionExcludes();

        checkIfSetsAreDisjoint(newRoleExcludes, userPermissions, errorMessage);
    }

    public static void checkIfConflicts(WsecUser user, WsecGroup newGroup) throws PermissionCollisionException {

        String errorMessage = "Nie mozna dodac: " + newGroup.getName();
        Set<WsecPermission> userPermissionExcludes = user.getAllPermissionExcludes();
        Set<WsecPermission> newGroupPermissions = newGroup.getAllPermissions();

        checkIfSetsAreDisjoint(userPermissionExcludes, newGroupPermissions, errorMessage);

        Set<WsecPermission> userPermissions = user.getAllPermissions();
        Set<WsecPermission> newGroupPermissionExcludes = newGroup.getAllPermissionExcludes();

        checkIfSetsAreDisjoint(newGroupPermissionExcludes, userPermissions, errorMessage);
    }

    protected static void checkIfSetsAreDisjoint(Set<WsecPermission> first, Set<WsecPermission> second, String errorMessage) throws PermissionCollisionException {

        Set<WsecPermission> intersection = new HashSet<WsecPermission>(first);
        intersection.retainAll(second);

        if (intersection.size() > 0) {
            throw new PermissionCollisionException(errorMessage);
        }
    }
}
