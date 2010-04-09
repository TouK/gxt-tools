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

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
public class ColumnProperties {

	protected String id;
	protected String name;
	protected int width;

	public ColumnProperties(String id, String name, int width) {
		this.id = id;
		this.name = name;
		this.width = width;
	}

	public ColumnProperties(String id, String name) {
		this.id = id;
		this.name = name;
		this.width = 180;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}
}
