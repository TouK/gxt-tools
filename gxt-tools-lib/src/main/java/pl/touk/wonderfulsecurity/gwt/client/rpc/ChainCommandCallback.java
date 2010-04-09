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

import com.google.gwt.user.client.rpc.AsyncCallback;
import pl.touk.wonderfulsecurity.exceptions.PermissionCollisionException;
import pl.touk.wonderfulsecurity.gwt.client.ui.Logger;

/**
 * Use this class together with {@link pl.touk.wonderfulsecurity.gwt.client.rpc.ChainCommand} class to make reusing async calls
 * possible by chaining them. For example usage see
 * {@link pl.touk.wonderfulsecurity.gwt.client.ui.SecurityManagerController#handleEvent(com.extjs.gxt.ui.client.mvc.AppEvent)}
 * <p/>
 * This callback makes chaining async methods possible by executing command after successful completion of async operation.
 * <p/>
 * Always define your async methods to take {@link pl.touk.wonderfulsecurity.gwt.client.rpc.ChainCommand} as a last parameter
 * even when you may never use it at your particular usage scenario.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class ChainCommandCallback implements AsyncCallback {
// ------------------------------ FIELDS ------------------------------

	protected ChainCommand command;
	private Logger output;
// --------------------------- CONSTRUCTORS ---------------------------

	/**
	 * Create new instance
	 *
	 * @param command command to execute upon completion or null if you
	 *                do not want to do anything
	 */
	public ChainCommandCallback(ChainCommand command) {
		this.command = command;
	}

// ------------------------ INTERFACE METHODS ------------------------
// --------------------- Interface AsyncCallback ---------------------
	@SuppressWarnings("static-access")
	public void onFailure(Throwable throwable) {
		String failureLog = "Nie udaLo się wykonać zadania";
		if (command != null) {
			failureLog = command.getFailureLog();
		}

		if (throwable instanceof PermissionCollisionException) {
			failureLog = throwable.getMessage();
		}

		output.error(failureLog);
	}

	/**
	 * Upon successful async execution execute code in command if available
	 */
	public void onSuccess(Object o) {
		if (command != null) {
			command.getAppEvent().setData(command.getCallbackResultKey(), o);
			command.execute(command.getAppEvent());

			if (!command.getSuccessLog().equals("")) {
				output.info(command.getSuccessLog());
			}
		}
	}

}
