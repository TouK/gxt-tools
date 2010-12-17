package pl.touk.wonderfulsecurity.gwt.client.ui.table;

/**
 * Tabelka z ramkami
 * @author rpietra
 * @param <T>
 */
public abstract class BaseFramedPagedList<T> extends BasePagedList<T> {

    protected BaseFramedPagedList(Class clazz, String uniqueName, String id) {
        super(clazz, uniqueName, id);
        setFrame(true);
        setBodyBorder(true);
        setHeaderVisible(true);

        grid.setBorders(true);
    }
}
