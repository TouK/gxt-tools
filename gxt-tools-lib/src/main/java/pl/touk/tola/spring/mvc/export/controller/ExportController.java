package pl.touk.tola.spring.mvc.export.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import pl.touk.tola.gwt.shared.ExportParameters;
import pl.touk.tola.gwt.shared.ExportParameters.ExportParameterNameInRequest;
import pl.touk.tola.spring.mvc.export.Exporter;
import pl.touk.tola.spring.mvc.export.exporters.CsvExporter;
import pl.touk.tola.spring.mvc.export.exporters.XlsExporter;

import pl.touk.wonderfulsecurity.springsecurity.WsecUserDetails;

/**
 * Kontroler do exportu tabelki do csv.
 * Wymaga podania w requescie trzech parametrów:
 *   clazz - nazwa beana hibernate'owego do wyciagniecia z bazy
 *   filePrefix - nazwa pliku, który ma być zwrócony
 *   sortColumn - kolumna (bean property), po ktorej mają być posortowane dane;
 *
 * Parametry są okreslone w enumeracji ExportParameterNameInRequest.
 *
 * Wszystkie inne parametry traktowane są jako parametry dla hibernate i umozliwiają wyszukanie w bazie na zasadzie:
 * select xxx where param = XXX;
 * Parametry są postaci <paramName>=<paramValue>||<paramType> np:
 *  ..&idProduktu=2||java.lang.Long..
 *
 * Kontroller obsługuje następujące typy:
 *  java.lang.String
 *  java.lang.Long
 *  java.lang.Integer
 *
 * Kontroller jest wrażliwy na uprawnienia. Definiuje się je przez ustawienie w mapie: permissionMapping par:
 *  <nazwaBeanu>, <uprawnienie>
 * np:
 *      <property name="permissionMapping">
 *           <map>
 *                <entry key="pl.touk.nsw.fe.shared.model.publishingPlan.PublishingPlanItemBean" value="PUBLISHING_PLAN_TAB"/>
 *                <entry key="pl.touk.nsw.fe.shared.model.ap.mediaCampaigns.ReportPromoVersionBean" value="MEDIA_CAMPAIGNS_TAB"/>
 *           </map>
 *       </property>
 *
 * @author rpietra
 */
public class ExportController extends AbstractController {

    public static final String DELIMETER = "\\|\\|";
    private Log log = LogFactory.getLog(ExportController.class);
    private final Exporter csvExporter;
    private final Exporter xlsExporter;
    private HashMap<String, String> permissionMapping;

    
    public ExportController(CsvExporter csvExporter, XlsExporter xlsExporter) {
        this.csvExporter = csvExporter;
        this.xlsExporter = xlsExporter;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ExportParameters exportParameters = getExportParametersFromRequest(request);
        prepareResponseHeader(response, exportParameters);

        if (areAllRequiredParams(exportParameters)) {
            if (hasPermission(exportParameters.getClazz())) {
                if (exportParameters.isXlsExport()) {
                    xlsExporter.exportData(exportParameters, response.getOutputStream());
                } else {
                    csvExporter.exportData(exportParameters, response.getOutputStream());
                }
            } else {
                createResponse(response, "Brak uprawnień do eksportu.");
            }
        } else {

            log.warn("Błąd exportu danych. Brak parametrów eksportu na serwerze.");
            createResponse(response, "Błąd eksportu danych. Brak parametrów eksportu na serwerze.");
        }
        return null;
    }

    private void createResponse(HttpServletResponse response, String msg) throws IOException {
        Writer w = new OutputStreamWriter(response.getOutputStream());
        w.write(msg);
        w.close();
    }

    private ExportParameters getExportParametersFromRequest(HttpServletRequest request) {
        String fileNamePrefix = request.getParameter(ExportParameterNameInRequest.filePrefix.name());
        String clazz = request.getParameter(ExportParameterNameInRequest.clazz.name());
        String sortColumn = request.getParameter(ExportParameterNameInRequest.sortColumn.name());
        String xlsExportString = request.getParameter(ExportParameterNameInRequest.xlsExport.name());
        Boolean xlsExport = new Boolean(xlsExportString);//TODO: to sie moze wywalić
        Map<String, Object> parametersMap = copyParametersFromRequest(request);

        return new ExportParameters(clazz, sortColumn, fileNamePrefix, parametersMap, xlsExport);
    }

    private boolean areAllRequiredParams(ExportParameters exportParameters) {
        boolean clazz = exportParameters.getClazz() != null;
        boolean parameters = exportParameters.getParameters() != null;
        boolean sortColumn = exportParameters.getSortColumn() != null;

        if (!parameters) {
            exportParameters.setParameters(new HashMap<String, Object>());
        }

        return clazz && sortColumn;
    }

    private boolean hasPermission(String clazz) {
        WsecUserDetails user = (WsecUserDetails) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
        String neededPermissionName = permissionMapping.get(clazz);
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(neededPermissionName)) {
                return true;
            }
        }
        return false;
    }

    private void prepareResponseHeader(HttpServletResponse response, ExportParameters exportParameters) {
        String fileName = exportParameters.getFilenamePrefix();
        //TODO: to ponizej jest niefajnie napisane
        if (exportParameters.isXlsExport()) {
            fileName = (fileName != null && fileName.trim().length() > 0) ? fileName.trim() + ".xls" : "export.xls";
            fileName = fileName.replaceAll(" ", "_");
            response.addHeader("Content-Type", "application/xls");
        } else {
            fileName = (fileName != null && fileName.trim().length() > 0) ? fileName.trim() + ".csv" : "export.csv";
            fileName = fileName.replaceAll(" ", "_");
            response.addHeader("Content-Type", "text/csv");
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }

    private Map<String, Object> copyParametersFromRequest(HttpServletRequest request) {
        Map<String, Object> returnParameters = new HashMap<String, Object>();
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = (String) enumeration.nextElement();
            String paramValue = request.getParameter(paramName);

            if (nonStandardParameter(paramName)) {
                returnParameters.put(paramName, decodeParameterValue(paramValue));
            }
        }
        return returnParameters;
    }

    protected Object decodeParameterValue(String parameter) {
        String[] param = parameter.split(DELIMETER);
        if (param.length == 2) {
            String paramType = param[1];
            String paramValue = param[0];

            if (equalsAndNotNull(paramType, "java.lang.Long")) {
                return Long.valueOf(paramValue);
            }
            if (equalsAndNotNull(paramType, "java.lang.Integer")) {
                return Integer.valueOf(paramValue);
            }

            if (equalsAndNotNull(paramType, "java.lang.String")) {
                return paramValue;
            }

        }
        throw new IllegalArgumentException("Zła wartość parametru dodatkowego w url" + parameter);
    }

    public void setPermissionMapping(HashMap<String, String> hashMap) {
        this.permissionMapping = hashMap;
    }

    private boolean equalsAndNotNull(String stringA, String stringB) {
        return stringA != null && stringA.equals(stringB);
    }

    private boolean nonStandardParameter(String paramName) {
        try {
            ExportParameterNameInRequest.valueOf(paramName);
        } catch (Exception exc) {
            return true;
        }
        return false;
    }
}
