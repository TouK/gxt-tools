package pl.touk.rapidgxt.gwt.client.rpc;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import pl.touk.rapidgxt.dataaccess.GenericDataAccessService;
import pl.touk.wonderfulsecurity.beans.PagedQueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lkc
 * Date: 2010-04-14
 * Time: 16:52:22
 * To change this template use File | Settings | File Templates.
 */
public interface GenericDataAccessServiceRpcAsync {

    public RequestBuilder fetchPagedList(HashMap<String, ?> queryParameters, Integer offset, Integer howMany, String sortColumn, Boolean desc, String clazz, AsyncCallback<ArrayList> callback);

    public RequestBuilder fetchPagedListWithOverallCount(HashMap<String, ?> queryParameters, Integer offset, Integer howMany, String sortColumn, Boolean desc, String className, AsyncCallback<PagedQueryResult> callback);

    
}