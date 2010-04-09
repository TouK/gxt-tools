/**
 * @author jnb
 */
package pl.touk.wonderfulsecurity.core;

import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.beans.WsecUser;

/**
 * Basic implementation of IServerSecurityContext that allows you to use IoC and mocking when dealing with WS
 * @author jnb
 */
public class ServerSecurityContext implements IServerSecurityContext {

	/* (non-Javadoc)
	 * @see pl.touk.wonderfulsecurity.core.IServerSecurityContext#getLoggedInUser()
	 */
	public WsecUser getLoggedInUser() {
		return ServerSecurity.getLoggedInUser();
	}

	/* (non-Javadoc)
	 * @see pl.touk.wonderfulsecurity.core.IServerSecurityContext#hasPermission(pl.touk.wonderfulsecurity.beans.WsecPermission)
	 */
	public boolean hasPermission(WsecPermission permission) {
		return ServerSecurity.hasPermission(permission);
	}

	/* (non-Javadoc)
	 * @see pl.touk.wonderfulsecurity.core.IServerSecurityContext#hasPermission(java.lang.String)
	 */
	public boolean hasPermission(String permissionName) {
		return ServerSecurity.hasPermission(permissionName);
	}

	/* (non-Javadoc)
	 * @see pl.touk.wonderfulsecurity.core.IServerSecurityContext#hasPermission(pl.touk.wonderfulsecurity.beans.WsecUser, pl.touk.wonderfulsecurity.beans.WsecPermission)
	 */
	public boolean hasPermission(WsecUser user, WsecPermission permission) {
		return ServerSecurity.hasPermission(user, permission);
	}

	/* (non-Javadoc)
	 * @see pl.touk.wonderfulsecurity.core.IServerSecurityContext#hasPermission(pl.touk.wonderfulsecurity.beans.WsecUser, java.lang.String)
	 */
	public boolean hasPermission(WsecUser user, String permissionName) {
		return ServerSecurity.hasPermission(user, permissionName);
	}

}
