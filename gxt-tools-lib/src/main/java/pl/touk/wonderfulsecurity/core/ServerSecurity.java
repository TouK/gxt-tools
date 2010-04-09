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
package pl.touk.wonderfulsecurity.core;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import pl.touk.wonderfulsecurity.springsecurity.WsecUserDetails;

/**
 * @author Michał Zalewski mzl@touk.pl
 * @author Lukasz Kucharski lkc@touk.pl
 */
public final class ServerSecurity {
// -------------------------- STATIC METHODS --------------------------

    /**
     * Return currently logged in user
     */
    public static WsecUser getLoggedInUser() {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (obj instanceof UserDetails) {
            WsecUserDetails wsecUserDetails = (WsecUserDetails) obj;
            return wsecUserDetails.getWsecUser();
        }
        throw new IllegalStateException("No one logged in!!!");
    }

    /**
     * Checks if currently logged in user has certain granted permission
     *
     * @param permission that will be looked up in currentlu logged in user
     * @return true if user has given permission, false otherwise
     */

    public static boolean hasPermission(WsecPermission permission) {
        if (getLoggedInUser().getAllPermissions().contains(permission)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if currently logged in user has certain granted permission
     *
     * @param permissionName that will be looked up in currentlu logged in user
     * @return true if user has given permission, false otherwise
     */

    public static boolean hasPermission(String permissionName) {
        if (getLoggedInUser().getAllPermissions().contains(new WsecPermission(permissionName))) {
            return true;
        }
        return false;
    }

    /**
     * Checks if user has certain granted permission
     *
     * @param user       user to perform check on
     * @param permission that will be looked up in user
     * @return true if user has given permission, false otherwise
     */

    public static boolean hasPermission(WsecUser user, WsecPermission permission) {
        // lkc fix check also in inherited permissions
        if (user.getAllPermissions().contains(permission)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if user has certain granted permission
     *
     * @param user           user to perform check on
     * @param permissionName that will be looked up in user
     * @return true if user has given permission, false otherwise
     */
    public static boolean hasPermission(WsecUser user, String permissionName) {
        if (user.getAllPermissions().contains(new WsecPermission(permissionName))) {
			return true;
		}
		return false;
	}

// --------------------------- CONSTRUCTORS ---------------------------

    private ServerSecurity() {
    }
}
