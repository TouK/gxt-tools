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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.AsyncCallback;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import pl.touk.wonderfulsecurity.gwt.client.rpc.*;

/**
 * This class should be used on client side to checks logged in users's permmissions.
 * <p/>
 * Configure instance of this utility class by calling initialize  static method. Remember that initialize method makes asynchronous call
 * to server to fetch logged in user so security framework is not functionall and you cannot use it until onSuccess method (second argument to initialize)
 * is called. Bottom line is that you always have to initialize your application in onSuccess method of this callback. Only then  you can be sure
 * that security is initialized
 *
 * @author Michał Zalewski mzl@touk.pl
 * @author Lukasz Kucharski lkc@touk.pl
 */
public final class ClientSecurity {
// ------------------------------ FIELDS ------------------------------

    private static boolean initialized;
    private static ISecurityManagerRpcAsync securityManagerRpc;
    private static WsecUser loggedInUser;

    private static ClientSecurity INSTANCE;

// -------------------------- STATIC METHODS --------------------------

    /**
     * Initialize client side security framework. You have to call this method first before you do anyting security related.
     * Remember that security is fully initialized only after on succes in callback parameter is called. So fully initialize your
     * application in onSuccess method of callback given as parameter to this method
     *
     * @param securityManagerEndpointUrl url of security manager endpoint on server side eg "secure/rpc/wsecurityManager.do"
     * @param callback                   this callback onFailue will be called when sth. bad happens when initializing client side security
     *                                   (  cannot fetch logged in user, or you specified wrong securityManagerEndpointUrl. onSuccess method is called
     *                                   when client side security is fully initialized and ready to use. Initialize your application in onSuccess method of this callback.
     */
    public static void initialize(String securityManagerEndpointUrl, final AsyncCallback callback) {
        if (initialized) {
            throw new IllegalStateException("Initialized already, should not be called again");
        }


        securityManagerRpc = (ISecurityManagerRpcAsync) GWT.create(ISecurityManagerRpc.class);
        ServiceDefTarget securityManagerEndpoint = (ServiceDefTarget) securityManagerRpc;
        securityManagerEndpoint.setServiceEntryPoint(securityManagerEndpointUrl);


        securityManagerRpc.fetchLoggedInUser(new AsyncCallback<WsecUser>() {
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }

            public void onSuccess(WsecUser wsecUser) {
                loggedInUser = wsecUser;
                INSTANCE = new ClientSecurity();
                initialized = true;

                callback.onSuccess(null);
            }
        });
    }

    private static void checkInitialized() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Did you forget to call initialize method? Are you using this method before asynchronous initialize() method completed?");
        }

    }

    /**
     * Get security rpc service in case you need it
     */
    public static ISecurityManagerRpcAsync getAsyncSecurityManager() {
        checkInitialized();

        return securityManagerRpc;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private ClientSecurity() {
        // prevent instantiation
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public static WsecUser getLoggedInUser() {
        return loggedInUser;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Security ---------------------

    /**
     * Checks if currently logged in user has certain granted permission
     *
     * @param permission that will be looked up in currentlu logged in user
     * @return true if user has given permission, false otherwise
     */
    public static boolean hasPermission(WsecPermission permission) {
        return getLoggedInUser().hasPermission(permission);
    }

    /**
     * Checks if currently logged in user has certain granted permission
     *
     * @param permissionName that will be looked up in currentlu logged in user
     * @return true if user has given permission, false otherwise
     */
    public static boolean hasPermission(String permissionName) {
        return getLoggedInUser().hasPermission(new WsecPermission(permissionName));
    }

}