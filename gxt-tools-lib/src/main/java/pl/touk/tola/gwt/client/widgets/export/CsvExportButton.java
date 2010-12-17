package pl.touk.tola.gwt.client.widgets.export;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.Window;
import java.util.HashMap;
import java.util.Map;

import pl.touk.tola.gwt.shared.ExportParameters.ExportParameterNameInRequest;


/**
 * Button do exportu.
 *
 * @author rpietra
 */
public class CsvExportButton extends Button {

    public static final String DELIMETER = "||";
    private final String controllerUrl;
    private final String clazz;
    private final String buttonLabel;
    private final String sortColumn;
    private final String filenamePrefix;
    private Map<String, Object> additionalParameters;
    private final boolean xlsExport;
    private final boolean automaticExport;

    /**
     *
     * @param controllerUrl - url do kontorllera obslugujacego export
     * @param clazz - klasa(bean hibernate'owy) ktory repezentuje exportowana tabelę
     * @param buttonLabel - nazwa przycisu
     * @param sortColumn - kolumna (bean property) po której sortować wynik exportu
     * @param additionalParameters - dodatkowe parametry typu: Long, Integer lub String
     */
    public CsvExportButton(String controllerUrl, String clazz, String buttonLabel, String sortColumn, Map<String, Object> additionalParameters, boolean xls, boolean automaticExport) {
        this(controllerUrl, clazz, buttonLabel, sortColumn, buttonLabel, additionalParameters, xls, automaticExport);
    }

    /**
     *
     * @param controllerUrl - url do kontorllera obslugujacego export
     * @param clazz - klasa(bean hibernate'owy) ktory repezentuje exportowana tabelę
     * @param buttonLabel - nazwa przycisu
     * @param sortColumn - kolumna (bean property) po której sortować wynik exportu
     */
    public CsvExportButton(String controllerUrl, String clazz, String buttonLabel, String sortColumn) {
        this(controllerUrl, clazz, buttonLabel, sortColumn, buttonLabel, null, false, true);
    }

    /**
     *
     * @param controllerUrl - url do kontorllera obslugujacego export
     * @param clazz - klasa(bean hibernate'owy) ktory repezentuje exportowana tabelę
     * @param buttonLabel - nazwa przycisu
     * @param sortColumn - kolumna (bean property) po której sortować wynik exportu
     * @param filenamePrefix - nazwa pliku
     * @param additionalParameters - dodatkowe parametry typu: Long, Integer lub String
     * @param xls - export do xls
     */
    public CsvExportButton(String controllerUrl, String clazz, String buttonLabel, String sortColumn, String filenamePrefix, Map<String, Object> additionalParameters, final boolean xls, final boolean automaticExport) {
        super(buttonLabel);
        this.controllerUrl = controllerUrl;
        this.clazz = clazz;
        this.buttonLabel = buttonLabel.replace(" ", "_");
        this.sortColumn = sortColumn;
        this.filenamePrefix = filenamePrefix;
        this.additionalParameters = additionalParameters;
        this.xlsExport = xls;
        this.automaticExport = automaticExport;

    }

    @Override
    protected void onClick(ComponentEvent ce) {
        super.onClick(ce);
        if (automaticExport) {
            export();
        }
    }

    String buildUrl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(controllerUrl);
        //standard parameters
        buffer.append("?").append(ExportParameterNameInRequest.clazz).append("=");
        buffer.append(clazz);
//        buffer.append("&sortColumn=");//TODO: use enum
        buffer.append("&").append(ExportParameterNameInRequest.sortColumn).append("=");
        buffer.append(sortColumn);
//        buffer.append("&filePrefix=");//TODO: use enum
        buffer.append("&").append(ExportParameterNameInRequest.filePrefix).append("=");
        buffer.append(filenamePrefix);
        if (xlsExport) {
            buffer.append("&").append(ExportParameterNameInRequest.xlsExport).append("=");
            buffer.append(xlsExport);
        }
        //additional parameters
        if (additionalParameters != null) {
            for (String paramName : additionalParameters.keySet()) {
                buffer.append("&").append(paramName).append("=");
                Object paramValue = additionalParameters.get(paramName);
                buffer.append(paramValue);
                buffer.append(DELIMETER);
                String className = paramValue.getClass().toString();
                buffer.append(className.substring("class ".length()));
            }
        }
        buffer = replaceInStringBuffer(buffer, " ", "%20");
        return buffer.toString();
    }

    private boolean validateExportParameters() {
        boolean clazzPresent = clazz != null && !"".equals(clazz.trim());
        boolean sortColumnPresent = sortColumn != null && !"".equals(sortColumn.trim());

        return clazzPresent && sortColumnPresent;
    }

    public void setAdditionalParams(HashMap<String, Object> params) {
        additionalParameters = params;
    }

    public String getButtonLabel() {
        return buttonLabel;
    }

    public void export() {
        String url = CsvExportButton.this.buildUrl();
        Window.open(url, buttonLabel, "export");
    }

    //TODO: to tak pospolita funkcjonalnosc, ze mozna omyslec o jej wydzieleniu do jakiejs klasy
    public StringBuffer replaceInStringBuffer(StringBuffer buffer, String textToReplace, String replacementText) {
        int position = 0;
        int limit = buffer.length() - textToReplace.length();
        while (position <= limit) {
            position = buffer.indexOf(textToReplace, position);
            if (position < 0) {
                break;
            }
            buffer.replace(position, position + textToReplace.length(), replacementText);
        }
        return buffer;
    }
}
