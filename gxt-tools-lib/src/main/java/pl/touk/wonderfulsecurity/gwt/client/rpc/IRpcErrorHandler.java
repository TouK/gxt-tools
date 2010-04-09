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
package pl.touk.wonderfulsecurity.gwt.client.rpc;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;

/**
 * Interface for optional error handling in <code>RpcExecutor<code> class.
 * 
 * @see RpcExecutor
 * @see RpcExecutor#setErrorHandler(IRpcErrorHandler)
 * 
 * @author Tomasz Przybysz tpr@touk.pl
 */
public interface IRpcErrorHandler {

    /**
     * Handles exceptions thrown from failed <code>RequestBuilder.send()</code> call.
     *
     * @param requestException exception which comes from failed <code>send()</code> method call
     * @see com.google.gwt.http.client.RequestBuilder#send()
     */
    public void handleError(RequestException requestException);

    /**
     * Handles exceptions thrown during async RPC call.
     * 
     * @param request  request object passed by <code>RequestCallback.onError()</code>
     * @param throwable  exception passed by  <code>RequestCallback.onError()</code>
     * @see com.google.gwt.http.client.RequestCallback#onError(com.google.gwt.http.client.Request, java.lang.Throwable)
     */
    public void handleError(Request request, Throwable throwable);

}
