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
package pl.touk.tola.gwt.client.rpc;

import com.extjs.gxt.ui.client.data.DataProxy;
import com.extjs.gxt.ui.client.data.DataReader;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * Proxy emulator for dynamic widget data providing.
 *
 * @author Tomasz Przybysz
 */
public class EmulatorProxy<D> implements DataProxy<D> {
    public void load(final DataReader<D> notUsed, final Object loadConfig, final AsyncCallback<D> callback) {
        try {
            D data = (D) loadConfig;
            callback.onSuccess(data);
        } catch (Throwable th) {
            callback.onFailure(th);
        }
    }
}
