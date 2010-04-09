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

import com.extjs.gxt.ui.client.Registry;

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
public class Logger {

	private static boolean initialized = false;
	private static WsecLogger clientLogger;

	public static void debug(String msg) {
		ensureInitalized();
		clientLogger.debug(msg);
	}

	public static void warn(String msg) {
		ensureInitalized();
		clientLogger.warn(msg);
	}

	public static void info(String msg) {
		ensureInitalized();
		clientLogger.info(msg);
	}

	public static void error(String msg) {
		ensureInitalized();
		clientLogger.error(msg);
	}

	public static void error(String msg, Throwable throwable) {
		ensureInitalized();
		clientLogger.error(msg, throwable);
	}

	public static void trace(String msg) {
		ensureInitalized();
		clientLogger.trace(msg);
	}

	private static boolean isInitialized() {
		return initialized;
	}

	private static void ensureInitalized() {
		if (!isInitialized()) {
			lookupClientLogger();
		}
	}

	public static void setClientLogger(WsecLogger wsecLogger) {
		clientLogger = wsecLogger;
	}

	private static void lookupClientLogger() {
		if (!isInitialized()) {
			WsecLogger logger = Registry.get("logger");
			if (logger instanceof WsecLogger) {
				Logger.clientLogger = Registry.get("logger");
			} else {
				Logger.clientLogger = new DefaultLogger();
			}
			initialized = true;
		}
	}
}
