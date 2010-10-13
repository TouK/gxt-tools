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
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import pl.touk.tola.gwt.client.state.TolaStateManager;

/**
 * @author Jakub urlenda jkr@touk.pl
 * @author Rafał Pietrasik rpt@touk.pl
 */
public class CheckBoxWithMemory extends CheckBox {

    private String uniqueName;

    /**
     * Creates checkbox with cookie - based memory.
     * @param uniqueName - checkbox name.
     * @param fieldLabelText - field label for this checkbox.
     */
    public CheckBoxWithMemory(String uniqueName, String fieldLabelText) {
        if (uniqueName == null) {
            throw new IllegalArgumentException("Nazwa Checkbox'a z pamięcią nie może być nullem!");
        }

        this.uniqueName = uniqueName;
        this.setBoxLabel(fieldLabelText);
        this.setHideLabel(false);


        if (TolaStateManager.get().get(this.uniqueName) == null) {
            // Jeśli w ciastkach nie był zapisany stan danego checkbox'a (identyfikowany przez uniqueName)
            this.setValue(true);
        } else {
            // w przeciwnym razie ustawiana jest wartość pobrana z ciasteczka.
            this.setValue((Boolean) TolaStateManager.get().get(this.uniqueName));
        }
    }

    public String getUniqueName() {
        return uniqueName;
    }

    @Override
    protected void onClick(ComponentEvent ce) {
        super.onClick(ce);
        TolaStateManager.get().set(uniqueName, this.getValue());
    }
}
