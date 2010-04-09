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

import com.extjs.gxt.ui.client.data.*;

import java.util.List;
import java.util.ArrayList;

/**
 * This is bugfixed implementation of {@link com.extjs.gxt.ui.client.data.BeanModelReader}. It fixes issue when empty
 * {@link java.util.List} is converted to list of {@link com.extjs.gxt.ui.client.data.ModelData} beans.
 * Orginal code returned original empty data object
 * (not a copy) and i makes it impossible to later change data List and redisplay it.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class FixedBeanModelReader extends BeanModelReader {
    public ListLoadResult<ModelData> read(Object loadConfig, Object data) {

    if (data instanceof List) {
      List<Object> beans = (List) data;

      if (beans.size() > 0) {
        BeanModelFactory factory = BeanModelLookup.get().getFactory(beans.get(0).getClass());
        if (factory == null) {
          throw new RuntimeException("No BeanModelFactory found for " + beans.get(0).getClass());
        }
        return new BaseListLoadResult(factory.createModel(beans));
      }
        // fixed issue here
        // previous code returned data
      return new BaseListLoadResult(new ArrayList());

    } else {
        return super.read(loadConfig, data);
    }
  }
}
