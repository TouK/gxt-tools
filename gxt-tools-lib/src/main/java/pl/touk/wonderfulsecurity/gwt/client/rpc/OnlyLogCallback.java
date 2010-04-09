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
 * Callback which does nothing but prints messages specified in constructor
 * upon async execution finish.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class OnlyLogCallback implements AsyncCallback {
// ------------------------------ FIELDS ------------------------------

	protected String errorMessage,  successMessage;

// --------------------------- CONSTRUCTORS ---------------------------
	/**
	 * Constructs object instance
	 *
	 * @param errorMessage   error message to print on execution of {@link #onFailure(Throwable)}
	 * @param successMessage success message to print on execution of {@link #onSuccess(Object)}
	 */
	public OnlyLogCallback(String errorMessage, String successMessage) {
		this.errorMessage = errorMessage;
		this.successMessage = successMessage;
	}

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface AsyncCallback ---------------------
	public void onFailure(Throwable throwable) {
		if (throwable instanceof PermissionCollisionException) {
			errorMessage = throwable.getMessage();
		}
		Logger.error(errorMessage);
	}

	public void onSuccess(Object o) {
		Logger.info(successMessage);
	}
}
