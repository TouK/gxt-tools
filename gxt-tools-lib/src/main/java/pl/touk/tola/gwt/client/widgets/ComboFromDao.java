package pl.touk.tola.gwt.client.widgets;

import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import pl.touk.wonderfulsecurity.gwt.client.model.PagedQueryResultReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import pl.touk.wonderfulsecurity.gwt.client.ui.table.provider.impl.DefaultPagingRpcProxyProvider;

/**
 * Combo, ktore jest zasilane beanami hibernate'owymi
 * <p/>
 * Mozna go uzyc np tak:
 * new ComboFromDao("blabla","nazwa",pl.touk.nsw.fe.shared.model.products.SimpleProductBean.class);
 *
 * TODO: nie ma sensu ładować przy każdym kliknięciu listy z DAO
 * TODO: trzeba jeszcze dopracować ten komponent i lepiej opisać
 *
 * @author rpt
 * @author mpr
 */
public class ComboFromDao extends ComboBox<ModelData> {

    private static final int NO_LIMIT = 9999999;
    Map<String, Object> parametersToQuery = new HashMap<String, Object>();
    private String lazyId;
    private String lazyIdProperty;
    private final String sortField;
    private String displayField;
    private final boolean withTips;
    private boolean triggerClicked = false;

    public ComboFromDao(String label, String displayField, Class clazz, boolean editable, boolean withTips) {
        this(label, displayField, displayField, clazz, editable, withTips);
    }

    public ComboFromDao(String label, String displayField, Class clazz, boolean editable) {
        this(label, displayField, displayField, clazz, editable, false);
    }

    /**
     * Tworzy combo o podanych parametrach.
     * @param label        nazwa comboboxa
     * @param displayField pole z beana, które bedzie wyswietlane
     * @param sortField pole z beana, po ktorym bedzie sortowanie
     * @param clazz        klasa z mapowaniem hiberante
     * @param editable	   czy combo ma byc edytowalne	
     */
    public ComboFromDao(String label, String displayField, String sortField, Class clazz, boolean editable, boolean withTips) {
        PagingLoader<PagingLoadResult<ModelData>> loader = createLoader(sortField, clazz);
        setStore(new ListStore(loader));
        setUpCombo(label, displayField);
        setEditable(editable);
        setTypeAhead(editable);
        if (editable) {
            setValidator(createNotNullDictionaryValidator(displayField));
        }
        this.sortField = sortField;
        this.displayField = displayField;
        this.withTips = withTips;
        this.setLazyRender(!editable); //gdy edytowalne, to wyłaczamy lazyInit'a

    }

    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);

    }

    /**
     * Tworzy edytowalne combo o podanych parametrach.
     * @param label        nazwa comboboxa
     * @param displayField pole z beana, które bedzie wyswietlane
     * @param clazz        klasa z mapowaniem hiberante
     */
    public ComboFromDao(String label, String displayField, Class clazz) {
        this(label, displayField, clazz, true);
    }

    private Validator createNotNullDictionaryValidator(final String propertyToValidate) {
        return new Validator() {

            public String validate(Field<?> field, String value) {

                ModelData tempValue = ComboFromDao.this.getValue();
                if (tempValue == null || tempValue.get(propertyToValidate) == null) {
                    return "Nie ma takiej wartości (spróbuj wybrać wartość z listy).";
                }
                return null;
            }
        };
    }

    public void load() {
        getStore().getLoader().load();
    }

    private void setUpCombo(String label, String displayField) {
        setFieldLabel(label);
        setValueField(displayField);
        setDisplayField(displayField);
    }

    private PagingLoader<PagingLoadResult<ModelData>> createLoader(String sortField, Class clazz) {

        RpcProxy proxy = createProxy(clazz);
        PagingLoader<PagingLoadResult<ModelData>> loader = new BasePagingLoader(proxy, new PagedQueryResultReader(clazz));

        return loader;
    }

    @Override
    public void doQuery(String q, boolean forceAll) {
        triggerClicked = forceAll;
        super.doQuery(q, forceAll);
    }

    public RpcProxy createProxy(final Class clazz) {

        final DefaultPagingRpcProxyProvider defaultPagingRpcProxyProvider = new DefaultPagingRpcProxyProvider();

        RpcProxy proxy = new RpcProxy() {

            protected void load(Object loadConfig, final AsyncCallback asyncCallback) {
                GWT.log("combo from dao loading: " + parametersToQuery);
                BasePagingLoadConfig basePagingLoadConfig = new BasePagingLoadConfig();
                basePagingLoadConfig.setSortField(sortField);
                //to ponizsze musi zostac, bo inaczej jest max 50 rekordów
                basePagingLoadConfig.setLimit(NO_LIMIT);
                basePagingLoadConfig.setOffset(0);
                if (withTips) {
                    if (!triggerClicked) {
                        String newValue = getRawValue();
                        parametersToQuery.put(displayField + "#LIKE_MATCH_START", newValue);
                    } else {
                        parametersToQuery.remove(displayField + "#LIKE_MATCH_START");
                    }
                    triggerClicked = false;
                }
                defaultPagingRpcProxyProvider.fetchPagedData(clazz, parametersToQuery, basePagingLoadConfig, getCallback(asyncCallback));
            }
        };
        return proxy;
    }

    /**
     * Ustawia parametr, ktory pojdzie do zapytania hibernate
     *
     * @param propertyName
     * @param propertyValue
     */
    public void addQueryParameter(String propertyName, Object propertyValue) {
        parametersToQuery.put(propertyName, propertyValue);
    }

    /**
     * Usuwa juz ustawiony parametr z zapytania do bazy.
     *
     * @param propertyName
     */
    public void removeQueryParameter(String propertyName) {
        parametersToQuery.remove(propertyName);
    }

    public void clearQueryParameters() {
        parametersToQuery.clear();
    }

    protected AsyncCallback getCallback(final AsyncCallback original) {
        GWT.log("return from combo" + lazyIdProperty);
        return new AsyncCallback() {

            public void onFailure(Throwable throwable) {
                original.onFailure(throwable);
            }

            public void onSuccess(Object data) {
                original.onSuccess(data);
                GWT.log("return from combo " + getStore().getCount() + " lazyIdProperty: " + lazyIdProperty + " lazyId " + lazyId);
                updateIdFromLazy();


            }
        };
    }

    public void updateIdFromLazy() {
        if (lazyIdProperty == null || lazyId == null) {
            return;
        }
        for (ModelData modelData : getStore().getModels()) {
            GWT.log("Found id=" + modelData.get(lazyIdProperty));
            if (lazyId.equals(modelData.get(lazyIdProperty).toString())) {
                GWT.log("Found id=" + lazyId + " in comboBox, selecting");
                setSelection(Arrays.asList(modelData));
                return;
            }
        }
    }

    //TODO: o co chodzi z tym lazy ? Opisać to
    public void setLazyId(String lazyId) {
        this.lazyId = lazyId;
        updateIdFromLazy();
    }

    public void setLazyIdProperty(String lazyIdProperty) {
        this.lazyIdProperty = lazyIdProperty;
    }

    public Long getPropertyFromSelectionAsLong(String property) {
        ModelData selectedModel = this.getValue();
        if (selectedModel != null) {
            return (Long) selectedModel.get(property);
        }
        return null;
    }
}
