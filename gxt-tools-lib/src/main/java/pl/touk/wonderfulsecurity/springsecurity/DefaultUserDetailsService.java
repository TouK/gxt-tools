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

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import pl.touk.wonderfulsecurity.dao.WsecUserDao;

import javax.annotation.Resource;

import pl.touk.wonderfulsecurity.beans.WsecUser;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class DefaultUserDetailsService implements UserDetailsService {
// ------------------------------ FIELDS ------------------------------

    @Resource
    protected WsecUserDao wsecUserDao;

// ------------------------ INTERFACE METHODS ------------------------

    // --------------------- Interface UserDetailsService ---------------------
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

        WsecUser wsecUser = wsecUserDao.getUserByLogin(username);

        if (wsecUser == null) {
            throw new UsernameNotFoundException("User not found", username);
        }

        // fetch all permissions while hibernate session is open
        wsecUser.getAllPermissions();

        return new WsecUserDetails(wsecUser);
    }

    public void setWsecUserDao(WsecUserDao wsecUserDao) {
        this.wsecUserDao = wsecUserDao;
    }
}