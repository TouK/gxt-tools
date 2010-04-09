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



package pl.touk.wonderfulsecurity.dao;

import pl.touk.wonderfulsecurity.beans.WsecPermission;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import static pl.touk.wonderfulsecurity.utils.Commons.*;

/**
 * @author Szymon Doroz sdr@touk.pl
 */
public class WsecPermissionDaoImpl extends WsecBaseDaoImpl implements WsecPermissionDao<WsecPermission> {

    public WsecPermission getPermissionByName(String name) {
        List<WsecPermission> result = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(WsecPermission.class).add(Restrictions.eq("name", name)));
        return nullIfEmptyCollection(result);
    }

    public List<WsecPermission> fetchSystemPermissions() {
        List<WsecPermission> result = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(WsecPermission.class).add(Restrictions.like("name", "WSEC_", MatchMode.START)));
        return result;
    }
}
