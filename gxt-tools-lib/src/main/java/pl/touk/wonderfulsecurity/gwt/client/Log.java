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
package pl.touk.wonderfulsecurity.gwt.client;

import com.google.gwt.core.client.GWT;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class Log {

    public static void info(String text) {
        GWT.log("INFO " + text, null);
    }

    public static void error(String text) {
        GWT.log("ERROR " + text, null);
    }

    public static void error(String text, Throwable th) {
        GWT.log("ERROR " + text, th);
    }
    public static void warn(String text) {
        GWT.log("WARN " + text, null);
    }
    public static void trace(String text) {
        GWT.log("TRACE " + text, null);
    }
}
