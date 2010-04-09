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

import static pl.touk.wonderfulsecurity.utils.Commons.*;
import pl.touk.wonderfulsecurity.beans.WsecUser;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class WsecUserDaoImpl extends WsecBaseDaoImpl implements WsecUserDao<WsecUser> {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface WsecUserDao ---------------------

    public WsecUser getUserByLogin(String login) {
        List<WsecUser> result = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(WsecUser.class).add(Restrictions.eq("login", login)));
        return nullIfEmptyCollection(result);
    }
}
