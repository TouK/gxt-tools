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

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.core.client.GWT;

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
public class DefaultLogger implements WsecLogger {

	public void debug(String msg) {
		Info.display("Debug", msg);
		GWT.log("Debug: " + msg, null);
	}

	public void warn(String msg) {
		Info.display("Warn", msg);
		GWT.log("Warn: " + msg, null);
	}

	public void info(String msg) {
		Info.display("Info", msg);
		GWT.log("Info: " + msg, null);
	}

	public void trace(String msg) {
		Info.display("Trace", msg);
		GWT.log("Trace: " + msg, null);
	}

	public void error(String msg, Throwable throwable) {
		Info.display("Error", msg);
		GWT.log("Error: " + msg, throwable);
	}

	public void error(String msg) {
		error(msg, null);
	}
}
