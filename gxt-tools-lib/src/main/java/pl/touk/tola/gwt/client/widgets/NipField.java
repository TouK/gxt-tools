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


public class NipField extends FormNumberTextField<String> {
    public NipField() {
        super("Numer NIP");
        setMaxLength(10);
        setValidator(new Validator() {
                public String validate(Field field, String value) {
                    if (value.length() == 10) {
                        int[] m = { 6, 5, 7, 2, 3, 4, 5, 6, 7 };

                        if (controlSum(value, m) == Integer.parseInt(
                                    value.substring(9, 10))) {
                            return null;
                        } else {
                            return "Błędna suma kontrolna";
                        }
                    } else {
                        return "Zła długość, NIP ma dokladnie 10 cyfr";
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

        return sumNum % 11;
    }
}
