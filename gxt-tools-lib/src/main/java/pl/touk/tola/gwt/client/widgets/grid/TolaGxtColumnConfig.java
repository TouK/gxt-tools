/*
 * Copyright (c) 2008 TouK.pl
 *
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
package pl.touk.tola.gwt.client.widgets.grid;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class TolaGxtColumnConfig extends ColumnConfig {

    protected boolean participateInCsvGeneration;
    protected boolean copyRendererValue;

    public boolean isCopyRendererValue() {
        return copyRendererValue;
    }

    public boolean isParticipateInCsvGeneration() {
        return participateInCsvGeneration;
    }

    public TolaGxtColumnConfig(String id, String name, int width, boolean participateInCsvGeneration) {
        this(id, name, width, participateInCsvGeneration, true);
    }

    public TolaGxtColumnConfig(String id, String name, int width, boolean participateInCsvGeneration,
            boolean copyRendererValue) {
        super(id, name, width);
        this.participateInCsvGeneration = participateInCsvGeneration;
        this.copyRendererValue = copyRendererValue;
    }
}
