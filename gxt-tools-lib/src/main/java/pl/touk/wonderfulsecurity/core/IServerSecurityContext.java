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



package pl.touk.wonderfulsecurity.core;

import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.beans.WsecUser;

/**
 * This is the main interface for interaction with security on the server side 
 * that is extendable and mockable.
 * No static is needed.    
 *
 * @author jnb
 */
public interface IServerSecurityContext {
	
	/**
	 * 
	 * @return
	 */
    public WsecUser getLoggedInUser();

    /**
     * Checks if currently logged user has a permission
     *
     * @param permission that will be looked up
     * @return true if user has given permission, false otherwise
     */
    public boolean hasPermission(WsecPermission permission);

    /**
     * Checks if currently logged user has a permission
     *
     * @param permissionName that will be looked up
     * @return true if user has given permission, false otherwise
     */
    public boolean hasPermission(String permissionName);

    /**
     * Checks if a user has a permission
     *
     * @param user       user to perform check on
     * @param permission that will be looked up 
     * @return true if user has given permission, false otherwise
     */
    public boolean hasPermission(WsecUser user, WsecPermission permission);

    /**
     * Checks if a user has a permission
     *
     * @param user           user to perform check on
     * @param permissionName that will be looked
     * @return true if user has given permission, false otherwise
     */
    public boolean hasPermission(WsecUser user, String permissionName);
}
