package pl.touk.tola.gwt.client.widgets;

import pl.touk.tola.gwt.client.utils.RegexpUtils;
import pl.touk.tola.gwt.client.widgets.form.FormTextField;

import java.math.BigDecimal;
import pl.touk.tola.gwt.client.utils.editors.PercentPropertyEditor;

public class FormPercentField extends FormTextField<BigDecimal> {

    public FormPercentField(boolean allow100, int precisionInPercentForm) {
        initializeSpecificFields(allow100, precisionInPercentForm);
    }

    public FormPercentField(String fieldName, boolean allow100, int precisionInPercentForm) {
        super(fieldName);
        initializeSpecificFields(allow100, precisionInPercentForm);
    }

    private void initializeSpecificFields(boolean allow100, int precisionInPercentForm) {
        setRegex(RegexpUtils.createRegexpAcceptingPercentWithOptionalPercentSign(allow100, precisionInPercentForm));
        setPropertyEditor(new PercentPropertyEditor());
        TextFieldMessages messages = new TextFieldMessages();
        messages.setRegexText("Pole może zawierać tylko wartość procentową od 0% do 100%. Dozwolone " + Integer.toString(precisionInPercentForm) + " miejsc po przecinku. Wartość 100% jest " + (allow100 ? "dopuszczalna." : "niedozwolona."));
        setMessages(messages);
    }


}
