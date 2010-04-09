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
package pl.touk.wonderfulsecurity.gwt.client.ui;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * This is simple wrapping class. Introduced after migration to gxt 2.0-m1.
 * It removes extra parameters from map returned by #getProperties method.
 * In previous release information on sorting and paging was not included in this map.
 * Now however it is and it causes hibernate criteria builder fail while constructing criteria query.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class StrippedParameters {

    private BasePagingLoadConfig config = null;

    public StrippedParameters(BasePagingLoadConfig loadConfig) {
        this.config = loadConfig;
    }


    public Map<String, Object> getProperties() {

        // remove extra properties set by gxt 2.0-m1
        Map<String, Object> map = new HashMap(config.getProperties());
        map.remove("limit");
        map.remove("offset");
        map.remove("sortField");
        map.remove("sortDir");
        return map;
    }


}
