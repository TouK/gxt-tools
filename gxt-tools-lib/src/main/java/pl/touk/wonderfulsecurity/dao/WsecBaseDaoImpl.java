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

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import java.io.Serializable;
import java.util.*;
import java.sql.SQLException;

import pl.touk.wonderfulsecurity.beans.PagedQueryResult;

/**
 * Hibernate base dao implementation
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class WsecBaseDaoImpl extends HibernateDaoSupport implements WsecBaseDao {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface WsecBaseDao ---------------------

    public void deleteAll(Collection collectionToDelete) {
        getHibernateTemplate().deleteAll(collectionToDelete);
    }

    public <E> ArrayList<E> fetchAll(Class<E> clazz) {
        List<E> result = getHibernateTemplate().loadAll(clazz);
        if (result instanceof ArrayList){
            return (ArrayList<E>) result;
        }
        // Collections.EmptyList != ArrayList
        return new ArrayList<E>(result);
    }

    public <E> E fetchById(Class<E> c, Serializable id) {
        return (E) getHibernateTemplate().get(c, id);
    }

    public int fetchCount(final Map<String, ?> queryParameters, final Class clazz) {
        Object o = getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                DetachedCriteria criteria = buildCriteriaFromMapOfParameters(queryParameters, clazz);
                criteria.setProjection(Projections.rowCount());
                return criteria.getExecutableCriteria(session).uniqueResult();
            }
        });

        return ((Number) o).intValue();
    }
    
    public  ArrayList fetchList(final Map<String, ?> queryParameters, final String sortColumn, final Boolean desc, final Class clazz) {
        {
            ArrayList list = (ArrayList) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    DetachedCriteria detachedCriteria = buildCriteriaFromMapOfParameters(queryParameters, clazz);
                    Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                    applySorting(criteria, sortColumn, desc);
                    return criteria.list();
                }
            });


            return list != null ? list : new ArrayList();
        }

    }

    

    public <E> ArrayList<E> fetchPagedList(final Map<String, ?> queryParameters, final Integer offset, final Integer howMany,
                                           final String sortColumn, final Boolean desc, final Class<E> clazz) {
        ArrayList<E> list = (ArrayList<E>) getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                DetachedCriteria detachedCriteria = buildCriteriaFromMapOfParameters(queryParameters, clazz);
                Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                applySortingOffSetAndLimit(criteria, offset, howMany, sortColumn, desc);
                return criteria.list();
            }
        });


        return list != null ? list : new ArrayList();
    }

    public PagedQueryResult fetchPagedListWithOverallCount(Map<String, ?> queryParameters, Integer offset,
                                                                        Integer howMany, String sortColumn, Boolean desc, Class clazz) {
        ArrayList list = fetchPagedList(queryParameters, offset, howMany, sortColumn, desc, clazz);
        logger.info("Fatched page");
        int overallCount = fetchCount(queryParameters, clazz);

        return new PagedQueryResult(list, overallCount);
    }

    public void saveOrUpdate(Object object) {
        getHibernateTemplate().saveOrUpdate(object);
        
    }

    public void saveOrUpdateAll(Collection entityCollection) {
        getHibernateTemplate().saveOrUpdateAll(entityCollection);
    }

    public void persist(Object o) {
        getHibernateTemplate().persist(o);
    }

    public void persistAll(Collection c) {
        if (c == null || c.isEmpty()) {
            return;
        }

        for (Iterator i = c.iterator(); i.hasNext();) {
            getHibernateTemplate().persist(i.next());
        }
    }

    public void delete(Object object){
		getHibernateTemplate().delete(object);
	}

// -------------------------- OTHER METHODS --------------------------

    protected Criteria applySorting(Criteria criteria, String sortColumn, Boolean desc) {

        if (desc == null) {
            desc = true;
        }


        boolean doSort = sortColumn != null && !sortColumn.trim().equals("");

        if (doSort) {
            criteria.addOrder(desc ? Order.desc(sortColumn) : Order.asc(sortColumn));
        }

        return criteria;
    }
    protected Criteria applySortingOffSetAndLimit(Criteria criteria ,Integer offset, Integer howMany, String sortColumn, Boolean desc) {
        if (offset == null && howMany != null || offset != null && howMany == null) {
            throw new IllegalArgumentException("Both howMany and offset has to be either null or non null references");
        }

        boolean setLimitAndOffset = offset != null;

        if (desc == null) {
            desc = true;
        }


        boolean doSort = sortColumn != null && !sortColumn.trim().equals("");

        if (setLimitAndOffset) {
            criteria.setFirstResult(offset);
            criteria.setMaxResults(howMany);
        }

        if (doSort) {
            if (sortColumn.indexOf(".")==-1) {
                criteria.addOrder(desc ? Order.desc(sortColumn) : Order.asc(sortColumn));
            } else {
                //handles only one nested property
                //TODO: handle more nested properties
                String[] nestedProperties = sortColumn.split("\\.");
                criteria.createCriteria(nestedProperties[0]).addOrder(desc ? Order.desc(nestedProperties[1]) : Order.asc(nestedProperties[1]));
            }
        }

        return criteria;
    }

    /**
     * Builds Hibernate criteria object from map of parameters that usually comes from front end
     */
    protected <E> DetachedCriteria buildCriteriaFromMapOfParameters(Map<String, ?> parameters, Class<E> clazz) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);

        if (parameters != null) {

            for (String key : parameters.keySet()) {
                if (key.indexOf(".") != -1) {

                    //handles only one nested property
                    //TODO: handle more nested properties
                    String[] nestedProperties = key.split("\\.");
                    //.addOrder(desc ? Order.desc() : Order.asc(nestedProperties[1]));
                    applyFilters(nestedProperties[1], parameters.get(key), criteria.createCriteria(nestedProperties[0]));
                } else {

                    applyFilters(key, parameters.get(key), criteria);
                }

            }
        }

        return criteria;
    }

    private void applyFilters(String key, Object filter, DetachedCriteria criteria) {
        if (key.endsWith(LIKE_SUFFIX)) {
            criteria.add(Restrictions.like(key.substring(0, key.length() - LIKE_SUFFIX.length()), "%" + filter + "%"));
        } else if (key.endsWith(LIKE_MATCH_START_SUFFIX)) {
            criteria.add(Restrictions.like(key.substring(0, key.length() - LIKE_MATCH_START_SUFFIX.length()), filter + "%"));
        } else if (key.endsWith(LIKE_MATCH_END_SUFFIX)) {
            criteria.add(Restrictions.like(key.substring(0, key.length() - LIKE_MATCH_END_SUFFIX.length()), "%" + filter));
        } else if (filter == null) {
            criteria.add(Restrictions.isNull(key));
        } else {
            criteria.add(Restrictions.eq(key, filter));
        }
    }
}
