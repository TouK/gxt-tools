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

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Michał Zalewski mzl@touk.pl
 */
public class InheritedEntitiesGrid extends BaseGridOfEntities {

	protected ArrayList<ColumnProperties> columnPropertiesList;
	protected String expandedColumnId;
	protected String heading;

	public InheritedEntitiesGrid(ArrayList beansToDisplay, HashMap properties) {
		super(beansToDisplay);
		this.heading = (String) properties.get("heading");
		this.columnPropertiesList = (ArrayList<ColumnProperties>) properties.get("columnPropertiesList");
		this.expandedColumnId = (String) properties.get("expandedColumnId");
	}

	protected ColumnModel createColumnModel() {
		ArrayList<ColumnConfig> cc = new ArrayList<ColumnConfig>();
		for (ColumnProperties columnProperties : columnPropertiesList) {
			cc.add(new ColumnConfig(columnProperties.getId(), columnProperties.getName(), 180));
		}
		this.setExpandedColumnId(expandedColumnId);
		ColumnModel cm = new ColumnModel(cc);

		return cm;
	}

	protected String buildHeading() {
		return heading;
	}

}
