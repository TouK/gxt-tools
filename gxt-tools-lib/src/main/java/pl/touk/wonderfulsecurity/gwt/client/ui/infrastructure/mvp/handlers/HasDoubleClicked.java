package pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.handlers;

/**
 *
 * @author rpietra
 */
public interface HasDoubleClicked {

    public interface Handler {

        void onDoubleClicked();
    }

    void addHandler(Handler h);
}
