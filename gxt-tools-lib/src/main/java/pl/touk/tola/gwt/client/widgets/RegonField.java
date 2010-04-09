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


public class RegonField extends FormNumberTextField<String> {
    public RegonField() {
        super("Numer REGON");
        setMinLength(9);
        setMaxLength(14);
        setValidator(new Validator() {
                public String validate(Field field, String value) {
                    if (value.length() == 9) {
                        int[] m = { 8, 9, 2, 3, 4, 5, 6, 7 };

                        if (controlSum(value, m) == Integer.parseInt(
                                    value.substring(8, 9))) {
                            return null;
                        } else {
                            return "Błędna suma kontrolna";
                        }
                    } else if (value.length() == 14) {
                        int[] m = { 8, 9, 2, 3, 4, 5, 6, 7 };
                        int[] m2 = { 2, 4, 8, 5, 0, 9, 7, 3, 6, 1, 2, 4, 8 };

                        if ((controlSum(value.substring(0, 9), m) == Integer.parseInt(
                                    value.substring(8, 9))) &&
                                (controlSum(value, m2) == Integer.parseInt(
                                    value.substring(13, 14)))) {
                            return null;
                        } else {
                            return "Błędna suma kontrolna";
                        }
                    } else {
                        return "Zła długość, REGON ma 9 lub 14 cyfr";
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

        sumNum = sumNum % 11;

        if (sumNum == 10) {
            return 0;
        } else {
            return sumNum;
        }
    }
}
