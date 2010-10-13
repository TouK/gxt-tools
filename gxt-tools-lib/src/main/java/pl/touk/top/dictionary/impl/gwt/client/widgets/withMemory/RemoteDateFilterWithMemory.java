package pl.touk.top.dictionary.impl.gwt.client.widgets.withMemory;

/*
 * Copyright (c) 2010 TouK.pl
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


import com.extjs.gxt.ui.client.data.PagingLoader;
import com.google.gwt.core.client.GWT;
import java.util.Date;
import java.util.HashSet;
import pl.touk.tola.gwt.client.state.TolaStateManager;
import pl.touk.tola.gwt.client.widgets.DateFilterField;

public class RemoteDateFilterWithMemory extends DateFilterField {

    private static final HashSet<String> names = new HashSet<String>();
    private static final String DATE_MEMORY_SUFFIX = "dateSuff";
    private String uniqueName;

    /**
     * Creates date filter with cookie - based memory.
     * @param uniqueName - checkbox name.
     * @param emptyText - field label for this checkbox.
     */
    public RemoteDateFilterWithMemory( final PagingLoader loader, String emptyText,String uniqueName) {
        super(loader);
        if (uniqueName == null) {
            throw new IllegalArgumentException("Nazwa RemoteTextFilterWithMemory nie może być nullem!");
        }

        if (names.contains(uniqueName)) {
            throw new IllegalArgumentException("To nie jest unikalna nazwa" + uniqueName);
        }

        this.uniqueName = uniqueName;
        this.setEmptyText(emptyText);
        Object oldValue = TolaStateManager.get().get(this.uniqueName + DATE_MEMORY_SUFFIX);
        if (oldValue != null) {
            // w przeciwnym razie ustawiana jest wartość pobrana z ciasteczka.
            this.setValue((Date) oldValue);
        }
    }

    public String getUniqueName() {
        return uniqueName;
    }

    @Override
    public boolean validate() {
        boolean valid = super.validate();
        if (valid) {
            TolaStateManager.get().set(uniqueName + DATE_MEMORY_SUFFIX, this.getValue());
        }
        return valid;
    }
}
