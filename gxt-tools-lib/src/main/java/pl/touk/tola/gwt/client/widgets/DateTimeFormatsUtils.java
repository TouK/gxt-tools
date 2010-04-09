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
package pl.touk.tola.gwt.client.widgets;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.table.DateTimeCellRenderer;

import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;


public class DateTimeFormatsUtils {
    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT = "yyyy-MM-dd";

    public static DateTimeCellRenderer getDateTimeCellRenderer() {
        return new TolaDateTimeCellRenderer(DATE_TIME_FORMAT);
    }
    
    public static TolaDateTimeCellRenderer getTolaDateTimeCellRenderer(){
        return new TolaDateTimeCellRenderer(DATE_TIME_FORMAT);
    }

    public static DateTimeCellRenderer getDateCellRenderer() {
        return new TolaDateTimeCellRenderer(DATE_FORMAT);
    }

    public static String formatToDateTime(Date date) {
        if ( date == null ) {
            return null;
        }
        return DateTimeFormat.getFormat(DATE_TIME_FORMAT).format(date);
    }

    public static String formatToDate(Date date) {
        if ( date == null ) {
            return null;
        }
        return DateTimeFormat.getFormat(DATE_FORMAT).format(date);
    }

    public static class TolaDateTimeCellRenderer extends DateTimeCellRenderer implements GridCellRenderer{
        /**
         * Creates a date time cell renderer.
         *
         * @param pattern the date time format
         */
        public TolaDateTimeCellRenderer(String pattern) {
            super(pattern);
        }

        /**
         * Creates a new date time cell renderer.
         *
         * @param format the date time format
         */
        public TolaDateTimeCellRenderer(DateTimeFormat format) {
            super(format);
        }

        @Override
        public String render(Component item, String property, Object value) {
            if ((value == null) || (value.toString().trim().length() == 0)) {
                return "";
            } else {
                return super.render(item, property, value);
            }
        }

        public Object render(ModelData model, String property, ColumnData columnData, int i, int i1, ListStore listStore, Grid grid) {
            Object value = model.get(property);            
            return render(null, null, value);
        }
    }
}
