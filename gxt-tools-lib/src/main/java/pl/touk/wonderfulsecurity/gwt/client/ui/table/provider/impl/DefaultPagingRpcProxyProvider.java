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
package pl.touk.wonderfulsecurity.gwt.client.ui.table.provider.impl;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import pl.touk.wonderfulsecurity.beans.PagedQueryResult;
import pl.touk.wonderfulsecurity.gwt.client.ui.StrippedParameters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import pl.touk.wonderfulsecurity.gwt.client.ui.table.provider.PagingRpcProxyProvider;
import pl.touk.wonderfulsecurity.shared.table.providers.PagedDataProviderRpc;
import pl.touk.wonderfulsecurity.shared.table.providers.PagedDataProviderRpcAsync;

/**
 * @author mproch
 * @author rpt
 *
 */
public class DefaultPagingRpcProxyProvider implements PagingRpcProxyProvider {

    private static final String ENDPOINT_URL = "rpc/pagedData.do";
    private static PagedDataProviderRpcAsync service;

    @Override
    public RpcProxy<Object> createProxy(final Class<?> clazz) {
        return createProxy(clazz, new HashMap<String, Object>());
    }

    @Override
    public RpcProxy<Object> createProxy(final Class<?> clazz, final Map<String, Object> queryParameters) {
        RpcProxy<Object> proxy = new RpcProxy<Object>() {

            @SuppressWarnings("unchecked")
            protected void load(Object loadConfig, final AsyncCallback asyncCallback) {
                fetchPagedData(clazz, queryParameters, (BasePagingLoadConfig) loadConfig, asyncCallback);
            }
        };
        return proxy;
    }

    public void fetchPagedData(Class<?> clazz, Map<String, Object> queryParameters, BasePagingLoadConfig loadConfig, AsyncCallback asyncCallback) {
        StrippedParameters strippedParams = new StrippedParameters(loadConfig);

        Map<String, Object> properties = strippedParams.getProperties();
        for (Map.Entry<String, Object> e : queryParameters.entrySet()) {
            properties.put(e.getKey(), e.getValue());
        }
        PagedDataProviderRpcAsync service = createDataProvider();
        service.fetchPagedListWithOverallCount("", properties, loadConfig.getOffset(),
                loadConfig.getLimit(), loadConfig.getSortInfo().getSortField(), loadConfig.getSortInfo().getSortDir() == Style.SortDir.DESC,
                clazz.getName(), asyncCallback);
    }

    public static PagedQueryResult<ArrayList<? extends Serializable>> getEmptyList() {
        return new PagedQueryResult<ArrayList<? extends Serializable>>(new ArrayList(), 0);
    }

    public static PagedDataProviderRpcAsync createDataProvider() {
        if (service == null) {
            PagedDataProviderRpcAsync serviceAsync = (PagedDataProviderRpcAsync) GWT.create(PagedDataProviderRpc.class);
            ServiceDefTarget endpoint = (ServiceDefTarget) serviceAsync;
            endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + ENDPOINT_URL);
            service = serviceAsync;
        }
        return service;
    }
}
