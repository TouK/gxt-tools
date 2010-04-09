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

import com.extjs.gxt.ui.client.mvc.AppEvent;

/**
 * Command that should be executed at completion of every asynchronous call made to server. This class together with
 * {@link pl.touk.wonderfulsecurity.gwt.client.rpc.ChainCommandCallback} should make it possible to define reusable methods
 * which fetch data asynchronously. Basic usage pattern is this:<br>
 * <p/>
 * If you define method that does asyc call to server eg:<br>
 * <p/>
 * <code>void fetchUserById(String userId)</code>
 * <p/>
 * <br>
 * <p/>
 * redefine it to sth like this:
 * <p/>
 * <code>void fetchUserById(String userId, ChainCommand command)</code><br>
 * <p/>
 * Then after successful completion in you async callback check if command argument is non null, if so execute its "execute" method.
 * Alternatively you can use {@link pl.touk.wonderfulsecurity.gwt.client.rpc.ChainCommandCallback} which does exactly this for you.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class ChainCommand {
// ------------------------------ FIELDS ------------------------------

    /**
     * Failure log which gets printed to log appender when command fails
     */
    protected String failureLog = "";
    /**
     * Success log which gets printed to log appender when command execution ends with success
     */
    protected String successLog = "";
    /**
     * Result of asynchronous call will be accessible via this key after async execution ends.
     * Result will be placed to {@link com.extjs.gxt.ui.client.mvc.AppEvent} object via
     * {@link com.extjs.gxt.ui.client.mvc.AppEvent#setData(String, Object)}
     */
    protected String callbackResultKey = "";
    /**
     * Application event that will be defacto map for values returned by async calls
     */
    protected AppEvent appEvent;

// --------------------------- CONSTRUCTORS ---------------------------

    public ChainCommand(AppEvent event) {
        this.appEvent = event;
    }

    public ChainCommand(AppEvent event, String failureLog) {
        this(event);
        this.failureLog = failureLog;
    }

    public ChainCommand(AppEvent event, String failureLog, String callbackResultKey) {
        this(event, failureLog);
        this.callbackResultKey = callbackResultKey;
    }

    public ChainCommand(AppEvent event, String failureLog, String callbackResultKey, String successLog) {
        this(event, failureLog, callbackResultKey);
        this.successLog = successLog;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public AppEvent getAppEvent() {
        return appEvent;
    }

    public String getCallbackResultKey() {
        return callbackResultKey;
    }

    public String getFailureLog() {
        return failureLog;
    }

    public String getSuccessLog() {
        return successLog;
    }

// -------------------------- OTHER METHODS --------------------------

    public void execute(AppEvent event) {
    }
}
