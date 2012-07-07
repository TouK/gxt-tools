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
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import java.io.Serializable;
import java.util.*;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import org.apache.maven.artifact.versioning.Restriction;
import pl.touk.wonderfulsecurity.beans.PagedQueryResult;

/**
 * Hibernate base dao implementation
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 * @author <a href="mailto:msk@touk.pl">Michał Sokołowski</a>
 */
public class WsecBaseDaoImpl extends HibernateDaoSupport implements WsecBaseDao {

    public static final String dateFormatAsStr = "yyyy-MM-dd HH:mm:ss";
    public static final DateFormat dateFormat = new SimpleDateFormat(dateFormatAsStr);

    public void deleteAll(Collection collectionToDelete) {
        getHibernateTemplate().deleteAll(collectionToDelete);
    }

    public <E> ArrayList<E> fetchAll(Class<E> clazz) {
        checkIsBeanMapped(clazz);
        List<E> result = getHibernateTemplate().loadAll(clazz);
        if (result instanceof ArrayList) {
            return (ArrayList<E>) result;
        }
        // Collections.EmptyList != ArrayList
        return new ArrayList<E>(result);
    }

    public <E> E fetchById(Class<E> c, Serializable id) {
        checkIsBeanMapped(c);
        return (E) getHibernateTemplate().get(c, id);
    }

    public int fetchCount(final Map<String, ?> queryParameters, final Class clazz) {
        checkIsBeanMapped(clazz);
        Object o = getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                DetachedCriteria criteria = buildCriteriaFromMapOfParameters(queryParameters, clazz);
                criteria.setProjection(Projections.rowCount());
                return criteria.getExecutableCriteria(session).uniqueResult();
            }
        });

        return o != null ? ((Number) o).intValue() : 0;
    }

    public <E> ArrayList<E> fetchList(final Map<String, ?> queryParameters, final String sortColumn, final Boolean desc, final Class<E> clazz) {
        {
        checkIsBeanMapped(clazz);
            ArrayList<E> list = new ArrayList<E>((Collection<E>)getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    DetachedCriteria detachedCriteria = buildCriteriaFromMapOfParameters(queryParameters, clazz);
                    Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                    applySorting(criteria, sortColumn, desc);
                    return criteria.list();
                }
        }));


            return list != null ? list : new ArrayList();
        }

    }

    public <E> ArrayList<E> fetchPagedList(final Map<String, ?> queryParameters, final Integer offset, final Integer howMany,
                                           final String sortColumn, final Boolean desc, final Class<E> clazz) {
        checkIsBeanMapped(clazz);
        ArrayList<E> list = (ArrayList<E>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                DetachedCriteria detachedCriteria = buildCriteriaFromMapOfParameters(queryParameters, clazz);
                Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                applySortingOffSetAndLimit(criteria, offset, howMany, sortColumn, desc);
                return criteria.list();
            }
        });


        return list != null ? list : new ArrayList();
    }

    public <E extends Serializable> PagedQueryResult<E> fetchPagedListWithOverallCount(Map<String, ?> queryParameters, Integer offset,
                Integer howMany, String sortColumn, Boolean desc, Class<E> clazz) {
            checkIsBeanMapped(clazz);
            ArrayList list = fetchPagedList(queryParameters, offset, howMany, sortColumn, desc, clazz);
            logger.info("Fetched page");
            int overallCount = fetchCount(queryParameters, clazz);

            return new PagedQueryResult(list, overallCount);
    }

    public <E extends Serializable> PagedQueryResult<E> fetchPagedListWithOverallCount(Map<String, ?> queryParameters, Integer offset,
                                                                                       Integer howMany, String sortColumn, Boolean desc, Long maxObjectsInPageList, Class<E> clazz ) {
        checkIsBeanMapped(clazz);
        ArrayList takenListOfClazz;
        logger.info("Fetched page");
        int overallCount = fetchCount(queryParameters, clazz);

        if (maxObjectsInPageList != null && maxObjectsInPageList > 0) {
            takenListOfClazz = overallCount > maxObjectsInPageList ? new ArrayList() : fetchPagedList(queryParameters, offset, howMany, sortColumn, desc, clazz);
        } else {
            takenListOfClazz = fetchPagedList(queryParameters, offset, howMany, sortColumn, desc, clazz);
        }

        return new PagedQueryResult(takenListOfClazz, overallCount);
    }

    private void checkIsBeanMapped(Class clazz) {
        ClassMetadata classMetadata = getSessionFactory().getClassMetadata(clazz);
        if (classMetadata == null) {
            throw new RuntimeException("Bean class not mapped! Add bean class to mapping! Class: " + clazz.getName());
        }
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

    public void delete(Object object) {
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

    protected Criteria applySortingOffSetAndLimit(Criteria criteria, Integer offset, Integer howMany, String sortColumn, Boolean desc) {
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
            if (sortColumn.indexOf(".") == -1) {
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
        } else if (key.endsWith(I_LIKE_SUFFIX)) {
            criteria.add(Restrictions.ilike(key.substring(0, key.length() - I_LIKE_SUFFIX.length()), "%" + filter + "%"));
        } else if (key.endsWith(LIKE_DATE_SUFFIX)) {
            String realKey = key.substring(0, key.length() - LIKE_DATE_SUFFIX.length());
            Date dateFilter = ((Date) filter);
            Date plus24h = new Date();
            plus24h.setTime(dateFilter.getTime()+86399999l);
            criteria.add(Restrictions.between(realKey, dateFilter, plus24h));
        }else if (key.endsWith(NULL_END_SUFFIX)) {
            criteria.add(Restrictions.isNull(key.substring(0, key.length() - NULL_END_SUFFIX.length())));
        } else if (key.endsWith(NOT_NULL_END_SUFFIX)) {
            criteria.add(Restrictions.isNotNull(key.substring(0, key.length() - NOT_NULL_END_SUFFIX.length())));
        } else if (key.endsWith(LIKE_MATCH_START_SUFFIX)) {
            criteria.add(Restrictions.like(key.substring(0, key.length() - LIKE_MATCH_START_SUFFIX.length()), filter + "%"));
        } else if (key.endsWith(I_LIKE_MATCH_START_SUFFIX)) {
            criteria.add(Restrictions.ilike(key.substring(0, key.length() - I_LIKE_MATCH_START_SUFFIX.length()), filter + "%"));
        } else if (key.endsWith(NOT_LIKE_END_SUFFIX)) {
            criteria.add(Restrictions.not(Restrictions.like(key.substring(0, key.length() - NOT_LIKE_END_SUFFIX.length()), "%"+filter + "%")));
        } else if (key.endsWith(LIKE_MATCH_END_SUFFIX)) {
            criteria.add(Restrictions.like(key.substring(0, key.length() - LIKE_MATCH_END_SUFFIX.length()), "%" + filter));
        } else if (key.endsWith(I_LIKE_MATCH_END_SUFFIX)) {
            criteria.add(Restrictions.ilike(key.substring(0, key.length() - I_LIKE_MATCH_END_SUFFIX.length()), "%" + filter));
        } else if (key.endsWith(BETWEEN_DATES_END_SUFFIX)) {
            String realKeyWithUpperLimit = key.substring(0, key.length() - BETWEEN_DATES_END_SUFFIX.length());
            String upperLimitAsStr = realKeyWithUpperLimit.substring(realKeyWithUpperLimit.indexOf("|") + 1);
            try {
                Date upperLimit = new Timestamp(dateFormat.parse(upperLimitAsStr).getTime());
                String realKey = realKeyWithUpperLimit.substring(0, realKeyWithUpperLimit.indexOf("|"));
                criteria.add(Restrictions.between(realKey, new Timestamp(((Date) filter).getTime()), upperLimit));
            } catch (ParseException e) {
                throw new IllegalArgumentException("for the key '" + key + "' the date '" + upperLimitAsStr + "' can't be parsed according to the format '" + dateFormatAsStr, e);
            }
        } else if (key.endsWith(EVERYTHING_EXCEPT_END_SUFFIX)) {
            criteria.add(Restrictions.or(Restrictions.ne(key.substring(0, key.length() - EVERYTHING_EXCEPT_END_SUFFIX.length()), filter), Restrictions.isNull(key.substring(0, key.length() - EVERYTHING_EXCEPT_END_SUFFIX.length()))));
        } else if (filter == null) {
            criteria.add(Restrictions.isNull(key));
        } else if (filter instanceof Collection) {
            criteria.add(Restrictions.in(key, (Collection) filter));
        } else {
            criteria.add(Restrictions.eq(key, filter));
        }
    }
}
