package pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.handlers;

public interface HasValue<T> {

    T getValue();

    void setValue(T value);
}
