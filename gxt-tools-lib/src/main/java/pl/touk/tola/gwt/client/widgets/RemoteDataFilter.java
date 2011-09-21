package pl.touk.tola.gwt.client.widgets;

/**
 * Filtr służący do przemielenia danych przychodzących z serwera.
 *
 * Np. przydatne w ComboFromDao, gdy chcemy wyrzucić konkretne dane.

 * @author bzd
 */
public interface RemoteDataFilter {

    /**
     * Filtruje dane przekazane z onSuccess(data) callbacka
     * 
     * @param data
     * @return
     */
    Object filter(Object data);

}
