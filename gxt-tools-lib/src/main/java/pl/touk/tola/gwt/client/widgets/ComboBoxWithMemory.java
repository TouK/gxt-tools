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

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import pl.touk.tola.gwt.client.state.TolaStateManager;

import java.util.HashSet;

/**
 * Zapamiętuje wybrany index ze stora w ciasteczkach, przy ponownej inicjalizacji, jesli znajdzie index w ciasteczkach
 * sam zaznacza odpowiednią opcję.
 *
 * UWAGA!!! Kontrolka odczytuje poprzednio zaznaczony index z ciasteczek. Nadaje się to raczej do danych slownikowych ktore
 * nie zmieniają się  w czasie dzialania aplikacji
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 * @author Rafał Pietrasik - rpt@touk.pl
 * @since 0.3.1
 */
public class ComboBoxWithMemory<D extends ModelData> extends ComboBox<D> implements Listener<FieldEvent> {

    public static final String STATE_MANAGER_KEY = "_currentCountPerPage";
    protected String uniqueName;
    protected static HashSet names = new HashSet();

    public ComboBoxWithMemory(String uniqueName) {
        if (uniqueName == null) {
            throw new IllegalArgumentException("Nazwa ComboBoxWithMemory nie moze byc nullem");
        }
// jesli w mapie jest juz taka nazwa to wypisz blad
        if (!names.add(uniqueName)) {
            throw new IllegalArgumentException("ComboWithMemory o nazwie: " + uniqueName + " juz istnieje w pamieci wybierz inna nazwe");
        }
        this.uniqueName = uniqueName;
        this.addListener(Events.Select, this);
//tu jest resetowane ciastko: TolaStateManager.get().set(uniqueName + STATE_MANAGER_KEY, -1);
    }

//  przechwyc zmiane i zapisz nowy index w StateManagerze
    public void handleEvent(FieldEvent be) {
        if (be.getType() == Events.Select) {
            ComboBox combo = (ComboBox) be.getField();
            int index = combo.getStore().indexOf(combo.getValue());
            TolaStateManager.get().set(uniqueName + STATE_MANAGER_KEY, index);

        }
    }

    /**
     * Podczas ustawiania stora sprawdz czy w ciastkach istnieje poprzednio wybrany index dla tego comboboxa
     * jesli tak to od razu go zaznacz
     */
    @Override
    public void setStore(ListStore<D> dListStore) {
        super.setStore(dListStore);
        int selectedIndex = TolaStateManager.get().getInteger(uniqueName + STATE_MANAGER_KEY);

        if (selectedIndex != -1) {
            if (selectedIndex < this.getStore().getCount()) {
                this.disableEvents(true);
                this.setValue(this.getStore().getAt(selectedIndex));
                this.disableEvents(false);
            }
        }
    }
}
