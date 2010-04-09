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
package pl.touk.wonderfulsecurity.gwt.client.ui.permission;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import pl.touk.wonderfulsecurity.gwt.client.ui.BaseGridOfEntities;

import java.util.ArrayList;

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
public class InheritedPermissionsFromRoleGrid extends BaseGridOfEntities {

	public InheritedPermissionsFromRoleGrid(ArrayList beansToDisplay) {
		super(beansToDisplay);
	}

	protected ColumnModel createColumnModel() {
		ArrayList<ColumnConfig> cc = new ArrayList<ColumnConfig>();
		cc.add(new ColumnConfig("inheritedFromRole", "Nazwa roli", 180));
		cc.add(new ColumnConfig("name", "Nazwa", 180));
		cc.add(new ColumnConfig("description", "Description", 100));
		this.setExpandedColumnId("description");
		ColumnModel cm = new ColumnModel(cc);
		return cm;
	}

	protected String buildHeading() {
		return "Uprawnienia odziedziczone z nadanych ról";
	}
}
