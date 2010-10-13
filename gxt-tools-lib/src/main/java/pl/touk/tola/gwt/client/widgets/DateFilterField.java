package pl.touk.tola.gwt.client.widgets;

import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.DateTimePropertyEditor;
import com.google.gwt.core.client.GWT;
import pl.touk.tola.gwt.client.widgets.DateTimeFormatsUtils;

import java.util.Date;

public class DateFilterField extends DateField {

    protected Date currentFilterValue = null;
    PagingLoader pagingLoader;

    public DateFilterField(PagingLoader pagingLoader) {
        setAutoValidate(true);
        setValidateOnBlur(false);
        setWidth(112);
        setPropertyEditor(new DateTimePropertyEditor(
                DateTimeFormatsUtils.DATE_FORMAT));
        this.pagingLoader = pagingLoader;
    }

    @Override
    protected void onTriggerClick(ComponentEvent ce) {
        //super.onTriggerClick(ce);
        GWT.log("on tc " + ce.getType() + " " + ce, null);
        setValue(null);

        //onFilter();
    }

    protected void onClick(ComponentEvent ce) {
        GWT.log("on click " + ce.getType() + " " + ce, null);
        super.onTriggerClick(ce);

        //onFilter();
    }

    protected void onCompositeEvent(ComponentEvent ce) {
        GWT.log("Handle event ce - " + ce.getType() + " " + ce, null);

        // Ten fragment kodu nie zostal zmigrowany do wersji 1.6.
        //if (ce.getEventType() == Event.ONCHANGE) {
        //    onFilter();
        //}
    }

    @Override
    protected boolean validateValue(String value) {
        boolean ret = super.validateValue(value);
        GWT.log("validate --- " + getValue(), null);
        onFilter();

        return ret;
    }

    protected void onFilter() {
        GWT.log("On filter" + getValue(), null);
        currentFilterValue = getValue();
        if (currentFilterValue != null) {
            pagingLoader.load();
        }
    }

    public Date getCurrentFilterValue() {
        return currentFilterValue;
    }
}
