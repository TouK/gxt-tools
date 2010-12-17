package pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.handlers;

import com.extjs.gxt.ui.client.data.ModelData;

public interface HasValueChanged {

    interface Handler {

        void onValueChange();
    }

    interface HandlerWithEvent {
        void onValueChange(ModelData data);
    }

    void addHandler(Handler h);

    void addHandler(HandlerWithEvent h);
}