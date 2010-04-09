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

import pl.touk.tola.gwt.client.widgets.TolaPopup;

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
public class ServerCommunicationInfo {

	static TolaPopup infoPopup = new TolaPopup();

	public static void show(String info) {
		show(info, 500, 10);
	}

	public static void show(String info, int x, int y) {
		if (!infoPopup.isVisible()) {
			infoPopup.display(info, x, y);
		}
	}

	public static void hide() {
		infoPopup.hide();
	}
}
