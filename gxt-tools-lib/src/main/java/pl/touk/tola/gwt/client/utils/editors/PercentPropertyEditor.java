package pl.touk.tola.gwt.client.utils.editors;
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

import com.extjs.gxt.ui.client.widget.form.PropertyEditor;
import pl.touk.tola.gwt.client.utils.DecimalsFormatter;

import java.math.BigDecimal;


public class PercentPropertyEditor implements PropertyEditor<BigDecimal> {

    
    public String getStringValue(BigDecimal value) {
        return DecimalsFormatter.PERCENT_FORMATTER.format(value);
    }

    public BigDecimal convertStringValue(String fieldValue) {
        BigDecimal value;
        if (fieldValue.endsWith("%")) {
            value = DecimalsFormatter.PERCENT_FORMATTER.parseToBigDecimal(divideDecimalStringBy100(fieldValue));
        } else {
            value = DecimalsFormatter.DECIMAL_FORMATTER.parseToBigDecimal(divideDecimalStringBy100(fieldValue));
        }
        return value;
    }

    static String divideDecimalStringBy100(String value) {
        String percentOrNot = "";
        if (value.endsWith("%")) {
            percentOrNot = "%";
            value = value.substring(0, value.length() - 1);
        }
        int decimalSignPosition = value.indexOf(DecimalsFormatter.DECIMAL_SEPARATOR);
        if (decimalSignPosition < 0) {
            decimalSignPosition = value.length();
        }
        String newIntegerPart = (decimalSignPosition > 2) ? value.substring(0, decimalSignPosition - 2) : "0";
        String twoDigitsChangingSide = (decimalSignPosition >= 2) ? value.substring(decimalSignPosition - 2, decimalSignPosition) : "0" + value.substring(0, 1);
        String oldFractionPart = (decimalSignPosition + 1 >= value.length()) ? "" : value.substring(decimalSignPosition + 1);
        return newIntegerPart + DecimalsFormatter.DECIMAL_SEPARATOR + twoDigitsChangingSide + oldFractionPart + percentOrNot;
    }
}
