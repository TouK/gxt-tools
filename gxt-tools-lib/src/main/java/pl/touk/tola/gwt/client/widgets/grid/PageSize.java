package pl.touk.tola.gwt.client.widgets.grid;

import com.extjs.gxt.ui.client.data.BaseModelData;
import java.util.ArrayList;
import java.util.List;

/**
 * Domyslne wartosci dla wyboru ilości wierszy na stronie
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class PageSize extends BaseModelData {

    public static final List<PageSize> DEFAULT_SET = new ArrayList<PageSize>();

    static {

        DEFAULT_SET.add(new PageSize(10));
        DEFAULT_SET.add(new PageSize(15));
        DEFAULT_SET.add(new PageSize(20));
        DEFAULT_SET.add(new PageSize(30));
        DEFAULT_SET.add(new PageSize(50));
        DEFAULT_SET.add(new PageSize(100));

    }
    private int size;

    public PageSize(int size) {

        this.size = size;
        this.set("size", size);
    }

    public int getSize() {
        return size;
    }
}
