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

import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.Validator;

import pl.touk.tola.gwt.client.widgets.form.FormNumberTextField;


public class PeselField extends FormNumberTextField<String> {
    public PeselField() {
        super("Numer PESEL");
        setMinLength(11);
        setMaxLength(11);
        setValidator(new Validator() {
                public String validate(Field field, String value) {
                    int[] m = { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3 };

                    if (Integer.parseInt(value.substring(4, 5)) > 3) {
                        return "Błędny numer PESEL";
                    } else if (controlSum(value, m) == Integer.parseInt(
                                value.substring(10, 11))) {
                        return null;
                    } else {
                        return "Błędna suma kontrolna";
                    }
                }
            });
    }

    public int controlSum(String value, int[] m) {
        int sumNum = 0;

        for (int i = 0; i < (value.length() - 1); i++) {
            int v = Integer.parseInt(value.substring(i, i + 1));
            sumNum += (v * m[i]);
        }

        return (10 - (sumNum % 10)) % 10;
    }
}
