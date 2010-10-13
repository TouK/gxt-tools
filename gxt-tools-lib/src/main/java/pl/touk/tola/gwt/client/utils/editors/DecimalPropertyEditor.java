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

package pl.touk.tola.gwt.client.utils.editors;

import com.extjs.gxt.ui.client.widget.form.PropertyEditor;
import pl.touk.tola.gwt.client.utils.DecimalsFormatter;

import java.math.BigDecimal;

public class DecimalPropertyEditor implements PropertyEditor<BigDecimal> {

    public String getStringValue(BigDecimal value) {
        return DecimalsFormatter.DECIMAL_FORMATTER.format(value);
    }

    public BigDecimal convertStringValue(String value) {
        return DecimalsFormatter.DECIMAL_FORMATTER.parseToBigDecimal(value);
    }
}
