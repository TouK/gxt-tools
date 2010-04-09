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
package pl.touk.wonderfulsecurity.gwt.client.model;

import pl.touk.wonderfulsecurity.gwt.client.Log;
import com.extjs.gxt.ui.client.data.*;

import java.util.List;

import pl.touk.wonderfulsecurity.beans.PagedQueryResult;

/**
 * Specialized subtype of {@link com.extjs.gxt.ui.client.data.BeanModelReader} that can process
 * {@link pl.touk.wonderfulsecurity.beans.PagedQueryResult} returned from server side end.
 * <p/>
 * Beans returned from server side do not extend or implement {@link com.extjs.gxt.ui.client.data.ModelData} so it needs to
 * be done via this reader instance. I takes ordinary list of beans from server side and turns them into instances of
 * {@link com.extjs.gxt.ui.client.data.ModelData}
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class PagedQueryResultReader extends FixedBeanModelReader {
// ------------------------------ FIELDS ------------------------------

    private Class beanModeFactoryTargetClass;

// --------------------------- CONSTRUCTORS ---------------------------

    public PagedQueryResultReader() {
    }

    public PagedQueryResultReader(Class beanModeFactoryTargetClass) {
        this.beanModeFactoryTargetClass = beanModeFactoryTargetClass;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface DataReader ---------------------

    public ListLoadResult<ModelData> read(Object loadConfig, Object data) {
        if (data instanceof PagedQueryResult) {
            PagedQueryResult pq = (PagedQueryResult) data;
            Object queryResult = pq.getResult();
            PagingLoadConfig pagingConfig = (PagingLoadConfig) loadConfig;

            if (!(queryResult instanceof List)) {
                throw new IllegalArgumentException("PagedQueryResult is fed to reader but nested query result is not of List type");
            }
            List beans = (List) queryResult;

            if (beans.size() > 0) {
                  // TODO: check if trace is enabled first
//                if (Log.isTraceEnabled()) {
                    for (Object o : beans) {
                        Log.trace(o.toString());
                    }
//                }

                BeanModelFactory factory = BeanModelLookup.get().getFactory(beanModeFactoryTargetClass != null ? beanModeFactoryTargetClass : beans.get(0).getClass());
                if (factory == null) {
                    throw new RuntimeException("No BeanModelFactory found for " + (beanModeFactoryTargetClass != null ? beanModeFactoryTargetClass : beans.get(0).getClass()));
                }
                return new BasePagingLoadResult(factory.createModel(beans), pagingConfig.getOffset(), pq.getOverallCount());
            }

            return new BasePagingLoadResult(beans);
        } else {
            return super.read(loadConfig, data);
        }
    }
}
