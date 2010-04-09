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

import org.springframework.security.core.GrantedAuthority;
import pl.touk.wonderfulsecurity.beans.WsecPermission;

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
public class WsecGrantedAuthority implements GrantedAuthority{

	protected  WsecPermission permission;

	public WsecGrantedAuthority() {
	}

	public WsecGrantedAuthority(WsecPermission permission) {
		this.permission = permission;
	}

	public int compareTo(Object o) {
		if(o instanceof WsecGrantedAuthority){
			WsecGrantedAuthority grantedAuthority = (WsecGrantedAuthority) o;
			return permission.getName().compareTo(grantedAuthority.getAuthority());
		}
		throw new RuntimeException("Not a WsecGrantedAuthority");
	}

	public String getAuthority() {
		return permission.getName();
	}

    @Override
    public String toString() {
        return permission.getName();
    }
}
