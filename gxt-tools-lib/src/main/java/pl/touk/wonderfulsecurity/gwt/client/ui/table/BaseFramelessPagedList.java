package pl.touk.wonderfulsecurity.gwt.client.ui.table;

/**
 * Tabelka bez ramek
 *
 * @author rpietra
 * @param <T>
 */
public abstract class BaseFramelessPagedList<T> extends BasePagedList<T> {

    protected BaseFramelessPagedList(Class clazz, String uniqueName, String id) {
        this(clazz, uniqueName, id, true);
    }
    protected BaseFramelessPagedList(Class clazz, String uniqueName, String id, boolean withMemory) {
        super(clazz, uniqueName, id, withMemory);
        setFrame(false);
        setBodyBorder(false);
        setHeaderVisible(false);
        grid.setBorders(false);
    }
}
