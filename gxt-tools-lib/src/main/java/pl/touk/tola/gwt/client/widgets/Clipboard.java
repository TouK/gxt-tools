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

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.widget.MessageBox;


/**
 * @author j
 */
public class Clipboard {
    public static void setText(String s) {
        if (!GXT.isIE) {
            MessageBox.alert("Błąd przeglądarki",
                "Funkcja schowka obsługiwana jedynie przez Internet Explorer 6+<br>" +
                "<br>" + "Kopiowany tekst: " + s, null);
        } else {
            nativeSetText(s);
        }
    }

    private static native void nativeSetText(String s) /*-{
    $wnd.clipboardData.setData('text', s);
    }-*/;
}
