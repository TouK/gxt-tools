package pl.touk.rapidgxt.dataaccess;

import pl.touk.wonderfulsecurity.beans.PagedQueryResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lkc
 * Date: 2010-04-14
 * Time: 16:37:11
 * To change this template use File | Settings | File Templates.
 */
public interface GenericDataAccessService {

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
     * @param className           which domain objects should be fetched
     * @return ArrayList of objects or empty list for non matching criteria
     */
    public ArrayList fetchPagedList(HashMap<String, ?> queryParameters, Integer offset, Integer howMany, String sortColumn, Boolean desc, String className) ;


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
     * @param className           which domain objects should be fetched
     * @return Object containing page of queried object plus overall count of objects matching given criteria
     */
    public PagedQueryResult fetchPagedListWithOverallCount(HashMap<String, ?> queryParameters, Integer offset, Integer howMany, String sortColumn, Boolean desc, String className) ;

}
