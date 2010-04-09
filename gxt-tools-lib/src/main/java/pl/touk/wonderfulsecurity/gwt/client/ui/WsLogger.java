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

import pl.touk.wonderfulsecurity.gwt.client.Log;
import com.extjs.gxt.ui.client.widget.Info;

/**
 * Default implementation of UserOutput interface
 * @author Pawel Tomaszewski
 */

public class WsLogger implements UserOutput {

    public WsLogger() {
    }

    /**
     * Displays worning message
     *
     * @param msg
     */

    public void worn(String msg) {
        Info.display("Ostrzezenie", msg);
        Log.warn(msg);
    }

    /**
     * Displays info message
     * @param msg
     */

    public void info(String msg) {
        Info.display("Informacja", msg);
        Log.info(msg);
    }

    /**
     * Displays error message
     * @param msg
     */
    public void error(String msg, Throwable trowable) {
        Info.display("Błąd", msg);
        Log.error(msg, trowable);
    }
}
