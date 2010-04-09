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
package pl.touk.wonderfulsecurity.gwt.client.ui.role;

import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import pl.touk.wonderfulsecurity.gwt.client.ui.BaseAssignableGridOfEntities;

import java.util.ArrayList;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public abstract class BaseAssignableRoleGrid extends BaseAssignableGridOfEntities {
// ------------------------------ FIELDS ------------------------------

    protected CheckColumnConfig plugin;

// --------------------------- CONSTRUCTORS ---------------------------

    public BaseAssignableRoleGrid(ArrayList beansToDisplay) {
        super(beansToDisplay);
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract String getAssignedToColumnName();

    protected abstract String getAssignedToColumnLabel();

    protected ColumnModel createColumnModel() {
        ArrayList<ColumnConfig> cc = new ArrayList<ColumnConfig>();
        CheckColumnConfig assignedConfig = new CheckColumnConfig(getAssignedToColumnName(), getAssignedToColumnLabel(), 100);
        this.plugin = assignedConfig;
        cc.add(assignedConfig);
        cc.add(new ColumnConfig("name", "Nazwa", 250));
        cc.add(new ColumnConfig("description", "Description", 100));

        this.setExpandedColumnId("description");

        ColumnModel cm = new ColumnModel(cc);
        return cm;
    }



    protected ComponentPlugin getGridPlugin() {
        return plugin;
    }
}
