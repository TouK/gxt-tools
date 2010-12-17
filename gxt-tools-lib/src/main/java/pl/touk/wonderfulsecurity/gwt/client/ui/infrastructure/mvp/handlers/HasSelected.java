package pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.handlers;

import com.extjs.gxt.ui.client.event.BaseEvent;

public interface HasSelected<E extends BaseEvent> {

    static interface Handler<E> {

        void onSelected(E ge);
    }

    void addHandler(Handler<E> h);
}
