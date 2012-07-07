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

import pl.touk.wonderfulsecurity.beans.PagedQueryResult;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Defines base dao operations common to all domain objects in system.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public interface WsecBaseDao {
// ------------------------------ FIELDS ------------------------------

    /**
     * This field should be used as map key in {@link #fetchPagedList(java.util.Map, Integer, Integer, String, Boolean, Class)}
     * and other methods that take query parameters as Map. <br>
     * Eg. if you have User object which have firstName field and you want to generate <code>SELECT * FROM USERS WHERE FNAME LIKE %lkc%</code>
     * then specify map entry <K,V> <LIKE_SUFFIX+firstName, lkc>
     */
    public static final String LIKE_SUFFIX = "@#LIKE";
    public static final String I_LIKE_SUFFIX = "@#I_LIKE";
    /**
     * This field should be used as map key in {@link #fetchPagedList(java.util.Map, Integer, Integer, String, Boolean, Class)}
     * and other methods that take query parameters as Map. <br>
     * Eg. if you have User object which have firstName field and you want to generate <code>SELECT * FROM USERS WHERE FNAME LIKE lkc%</code>
     * then specify map entry <K,V> <LIKE_MATCH_START_SUFFIX+firstName, lkc>
     */
    public static final String LIKE_MATCH_START_SUFFIX = "@#LIKE_MATCH_START";

    /**
     * The same as LIKE_MATCH_START_SUFFIX but CASE INSENSITIVE
     */
    public static final String I_LIKE_MATCH_START_SUFFIX = "@#I_LIKE_MATCH_START";


    /**
     * This field should be used as map key in {@link #fetchPagedList(java.util.Map, Integer, Integer, String, Boolean, Class)}
     * and other methods that take query parameters as Map. <br>
     * Eg. if you have User object which have firstName field and you want to generate <code>SELECT * FROM USERS WHERE FNAME LIKE %lkc</code>
     * then specify map entry <K,V> <LIKE_MATCH_END_SUFFIX+firstName, lkc>
     */
    public static final String LIKE_MATCH_END_SUFFIX = "@#LIKE_MATCH_END";
    public static final String I_LIKE_MATCH_END_SUFFIX = "@#I_LIKE_MATCH_END";
    //TODO: comment this
    public static final String BETWEEN_DATES_END_SUFFIX = "@#BETWEEN_DATE_END";
    public static final String EVERYTHING_EXCEPT_END_SUFFIX = "@#EVERYTHING_EXCEPT_END_SUFFIX";
    public static final String NULL_END_SUFFIX = "@#NULL_END_SUFFIX";
    public static final String NOT_NULL_END_SUFFIX = "@#NOT_NULL_END_SUFFIX";
    public static final String NOT_LIKE_END_SUFFIX = "@#NOT_LIKE_END_SUFFIX";
    public static final String LIKE_DATE_SUFFIX = "@#LIKE_DATE_SUFFIX";

// -------------------------- OTHER METHODS --------------------------

    /**
     * Deletes all objects from specified collection
     *
     * @param collectionToDelete can be empty cannot be null
     */
    public void deleteAll(Collection collectionToDelete);

    /**
     * Fetches all available objects of class clazz
     * Be careful as this method may return huge collections for tables with many rows
     *
     * @param clazz domain object class to fetch
     * @return ArrayList of all available objects or empty list if none can be found
     */
    public <E> ArrayList<E> fetchAll(Class<E> clazz);

    /**
     * Fetch object instance by its id
     *
     * @param c  object class (must be mapped via hibernate first)
     * @param id fetched object id
     * @return fetched object or null if object with specified id cannot be found
     */
    public <E> E fetchById(Class<E> c, Serializable id);

    /**
     * Return number of object of given class for criteria parmeters given as a map
     *
     * @param queryParameters query parameters in form of a map. Key represents fieldName and value represents desired
     *                        criteria value. Eg. If you want to fetch Users whose firstName is lkc then add following entry to queryParametersMap:<br>
     *                        <code>queryParameters.add("firstName","lkc")</code>. <br>
     *                        If you do not want to have full match then use {@link #LIKE_SUFFIX} and similar constructs defined in this interface
     */
    public int fetchCount(Map<String, ?> queryParameters, Class clazz);

    /**
     * Fetch list of entities matching criteria in input map.
     *
     * @param queryParameters query parameters in form of a map. Key represents fieldName and value represents desired
     *                        criteria value. Eg. If you want to fetch Users whose firstName is lkc then add following entry to queryParametersMap:<br>
     *                        <code>queryParameters.add("firstName","lkc")</code>. <br>
     *                        If you do not want to have full match then use {@link #LIKE_SUFFIX} and similar constructs defined in this interface
     * @param sortColumn      which column should we sort by, can be null then sorting is turned off
     * @param desc            if sort order should be descending. If null default sort order is descending
     * @param clazz           which domain objects should be fetched
     * @return ArrayList of objects or empty list for non matching criteria
     * @return
     */
    public <E> ArrayList<E> fetchList(final Map<String, ?> queryParameters, final String sortColumn, final Boolean desc, final Class<E> clazz);

    /**
     * Fetch paged list of entities matching criteria expressed via map of parameters
     *
     * @param queryParameters query parameters in form of a map. Key represents fieldName and value represents desired
     *                        criteria value. Eg. If you want to fetch Users whose firstName is lkc then add following entry to queryParametersMap:<br>
     *                        <code>queryParameters.add("firstName","lkc")</code>. <br>
     *                        If you do not want to have full match then use {@link #LIKE_SUFFIX} and similar constructs defined in this interface
     * @param offset          offset for page can be null however both offset and how many have to be either null or not null
     * @param howMany         how many items return starting from offset can be null however both offset and how many have to be either null or not null
     * @param sortColumn      which column should we sort by, can be null then sorting is turned off
     * @param desc            if sort order should be descending. If null default sort order is descending
     * @param clazz           which domain objects should be fetched
     * @return ArrayList of objects or empty list for non matching criteria
     */
    public <E> ArrayList<E> fetchPagedList(Map<String, ?> queryParameters, Integer offset, Integer howMany, String sortColumn, Boolean desc, Class<E> clazz);

    /**
     * Fetch paged list of entities matching criteria expressed via map of parameters
     *
     * @param queryParameters query parameters in form of a map. Key represents fieldName and value represents desired
     *                        criteria value. Eg. If you want to fetch Users whose firstName is lkc then add following entry to queryParametersMap:<br>
     *                        <code>queryParameters.add("firstName","lkc")</code>. <br>
     *                        If you do not want to have full match then use {@link #LIKE_SUFFIX} and similar constructs defined in this interface
     * @param offset          offset for page can be null however both offset and how many have to be either null or not null
     * @param howMany         how many items return starting from offset can be null however both offset and how many have to be either null or not null
     * @param sortColumn      which column should we sort by, can be null then sorting is turned off
     * @param desc            if sort order should be descending. If null default sort order is descending
     * @param clazz           which domain objects should be fetched
     * @return Object containing page of queried object plus overall count of objects matching given criteria
     */
    public <E extends Serializable> PagedQueryResult<E> fetchPagedListWithOverallCount(Map<String, ?> queryParameters, Integer offset, Integer howMany, String sortColumn, Boolean desc, Class<E> clazz);

    public <E extends Serializable> PagedQueryResult<E> fetchPagedListWithOverallCount(Map<String, ?> queryParameters, Integer offset, Integer howMany, String sortColumn, Boolean desc, Long maxObjectsInPageList, Class<E> clazz);

    /**
     * Save transient object or update detached object
     */
    public void saveOrUpdate(Object object);

    /**
     * Save or update transient collection of objects
     */
    public void saveOrUpdateAll(Collection entityCollection);

    /**
     * Save transient object to database
     *
     * @param o transient object to be saved
     * */
    public void persist(Object o);

    /**
     * Save transient collection to database
     *
     * @param c collection to be saved
     *
     */
    public void persistAll(Collection c);

    /**
     * Delete object
     * @param object
     */
    public void delete(Object object);

}
