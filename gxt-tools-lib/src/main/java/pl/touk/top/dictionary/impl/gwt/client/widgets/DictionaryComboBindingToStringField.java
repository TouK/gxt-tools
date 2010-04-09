/*
* Copyright (c) 2007 TouK
* All rights reserved
*/
package pl.touk.top.dictionary.impl.gwt.client.widgets;

import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.Converter;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.data.ModelData;
import pl.touk.top.dictionary.model.domain.DictionaryEntry;

/**
 * Jesli wykorzystujesz mechanizm bindowania z dictionaryField na jakies pole typu String w ModelData
 * to wykorzystuj zawsze tego bindera.
 *
 *
 *
 * @author Lukasz Kucharski - lkc@touk.pl
*/
public class DictionaryComboBindingToStringField extends FieldBinding {


    /**
     * @param entryProperty ktore pole z obiektu typu DictionaryEntry wykorzystac jako źródło danych dal bindera
     */
    public DictionaryComboBindingToStringField(final ComboBox comboBox, String property, final DictionaryEntry.EntryProperty entryProperty) {
        super(comboBox, property);

        if (entryProperty == null) {
            throw new IllegalArgumentException("entryProperty argument cannot be null");
        }

        setConverter(new Converter() {
            @Override
            public Object convertModelValue(Object value) {
                ModelData md = comboBox.getStore().findModel(entryProperty.name(), value);
                return md;
            }

            @Override
            public Object convertFieldValue(Object value) {
                if (value == null) {
                    return value;
                }

                if (value instanceof ModelData) {
                    ModelData md = (ModelData) value;
                    return md.get(entryProperty.name());
                }
                throw new IllegalStateException("Converter cannot work with this type of data: " + value);
            }
        });
    }
}
