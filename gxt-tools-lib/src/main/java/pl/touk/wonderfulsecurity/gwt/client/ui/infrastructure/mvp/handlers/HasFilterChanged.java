package pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.handlers;

import com.extjs.gxt.ui.client.store.StoreEvent;

public interface HasFilterChanged<E extends StoreEvent> {

    static interface Handler<E> {

        void onFilterChange(E se);
    }

    void addHandler(Handler<E> h);
}
