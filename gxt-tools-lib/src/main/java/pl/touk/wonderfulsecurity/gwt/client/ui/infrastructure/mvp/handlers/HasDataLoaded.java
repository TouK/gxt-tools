package pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.handlers;

import com.extjs.gxt.ui.client.data.LoadEvent;

public interface HasDataLoaded<E extends LoadEvent> {

    interface Handler<E> {

        void onSelected(E ge);
    }

    void addHandler(Handler h);
}
