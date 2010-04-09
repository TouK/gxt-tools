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
package pl.touk.wonderfulsecurity.dao;

import java.util.ArrayList;
import java.util.Set;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import static pl.touk.wonderfulsecurity.utils.Commons.*;
import pl.touk.wonderfulsecurity.beans.WsecGroup;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class WsecGroupDaoImpl extends WsecBaseDaoImpl implements WsecGroupDao<WsecGroup> {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface WsecGroupDao ---------------------

    public WsecGroup getGroupByName(String groupName) {
        List<WsecGroup> result = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(WsecGroup.class).add(Restrictions.eq("name", groupName)));

        return nullIfEmptyCollection(result);
    }

    /**
     * Use WsecGroup.getUsers() instead
     * @param groupName
     * @return
     * @deprecated
     */
    @Deprecated
    public List<WsecUser> getUsers(String groupName) {

        if(groupName == null){
            throw new IllegalArgumentException("groupName in null");
        }

        WsecGroup group = getGroupByName(groupName);
        if(group != null){
            Set<WsecUser> users = group.getUsers();
            return new ArrayList<WsecUser>(users);
        }
        return null;
    }
}
