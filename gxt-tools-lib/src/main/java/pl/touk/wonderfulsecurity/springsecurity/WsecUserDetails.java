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
package pl.touk.wonderfulsecurity.springsecurity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.beans.WsecUser;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class WsecUserDetails implements UserDetails {
// ------------------------------ FIELDS ------------------------------

	protected WsecUser wsecUser;

// --------------------------- CONSTRUCTORS ---------------------------
	public WsecUserDetails(WsecUser wsecUser) {
		this.wsecUser = wsecUser;
	}

// ------------------------ INTERFACE METHODS ------------------------
// --------------------- Interface UserDetails ---------------------
	public Collection<GrantedAuthority> getAuthorities() {
		Set<WsecPermission> allPermissions = wsecUser.getAllPermissions();
		GrantedAuthority[] authorities = new WsecGrantedAuthority[allPermissions.size()];
		int i = 0;
		for (WsecPermission perm : allPermissions) {
			authorities[i] = new WsecGrantedAuthority(perm);
			i++;
		}
		return Arrays.asList(authorities);
	}

	public String getPassword() {
		return wsecUser.getPassword();
	}

	public String getUsername() {
		return wsecUser.getLogin();
	}

	public boolean isAccountNonExpired() {
		//        TODO: implement in WsecUser
		return true;
	}

	public boolean isAccountNonLocked() {
		//        TODO: implement in WsecUser
		return true;
	}

	public boolean isCredentialsNonExpired() {
		//        TODO: implement in WsecUser
		return true;
	}

	public boolean isEnabled() {
		return wsecUser.isEnabled();
	}

	public WsecUser getWsecUser() {
		return wsecUser;
	}
}
