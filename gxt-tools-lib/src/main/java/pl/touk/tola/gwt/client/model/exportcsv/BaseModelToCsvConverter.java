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
package pl.touk.tola.gwt.client.model.exportcsv;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import java.util.Collection;
import pl.touk.tola.gwt.client.widgets.grid.TolaGxtColumnConfig;

/**
 * @author Łukasz Kucharski - lkc@touk.pl
 *         <p/>
 *         Konwertuje Wszystko co dziedziczy po BaseModel do postaci csv
 */
public class BaseModelToCsvConverter<T extends BaseModel> {

    private final Collection<T> modelBeansCollection;

    public BaseModelToCsvConverter(Collection<T> modelBeansCollection) {
        this.modelBeansCollection = modelBeansCollection;
    }



    /**
     * Konwertuje model podany w konstruktorze.
     *
     * @param columns   nazwy pol z modelu które trzeba dodać do CSV
     * @param separator - separator użyty do tworzenia CSV.
     * @return string w postaci CSV
     */

    String convert(ColumnConfig[] columns, String separator) {
        StringBuffer sb = new StringBuffer();       

        for (BaseModel model : modelBeansCollection) {
            for (int i = 0; i < columns.length; i++) {

                TolaGxtColumnConfig column = (TolaGxtColumnConfig) columns[i];

                if ((column.getRenderer() != null) &&
                        column.isCopyRendererValue()) {
                    sb.append((model.get(column.getId()) == null) ? ""
                            : column.getRenderer().render(model, column.getId(), null, 0, 0, null, null));
                } else {
                    sb.append((model.get(column.getId()) == null) ? ""
                            : model.get(
                            column.getId()));
                }                
                sb.append(((i + 1) == columns.length) ? "\r\n" : separator);

            }
        }
        return sb.toString();
    }

    /**
     * Konwertuje model podany w konstruktorze z domyślnym separatorm "\t".
     *
     * @param columns nazwy pol z modelu które trzeba dodać do CSV
     * @return string w postaci CSV
     */
    public String convert(ColumnConfig[] columns) {
        return convert(columns, "\t");
    }
    
  
}
