package pl.touk.tola.gwt.client.widgets;

import pl.touk.tola.gwt.client.utils.RegexpUtils;
import pl.touk.tola.gwt.client.widgets.form.FormTextField;

import java.math.BigDecimal;
import pl.touk.tola.gwt.client.utils.editors.DecimalPropertyEditor;

public class FormDecimalField extends FormTextField<BigDecimal> {

    public FormDecimalField() {
        initializeSpecificFields();
    }

    public FormDecimalField(String fieldName) {
        super(fieldName);
        initializeSpecificFields();
    }

    private void initializeSpecificFields() {
        setRegex(RegexpUtils.DECIMAL_REGEXP);
        setPropertyEditor(new DecimalPropertyEditor());
    }

}
