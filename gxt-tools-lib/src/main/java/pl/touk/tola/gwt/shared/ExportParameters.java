package pl.touk.tola.gwt.shared;

import java.io.Serializable;
import java.util.Map;

/**
 * Parametry do eksportu.
 *
 * @author rpietra
 */
public class ExportParameters implements Serializable {

    /**
     * @return the xlsExport
     */
    public boolean isXlsExport() {
        return xlsExport;
    }

    public enum ExportParameterNameInRequest {
        //nazwy parametrów w requescie
        clazz, filePrefix, sortColumn, xlsExport
    }

    private String clazz;
    private boolean xlsExport;
    private String sortColumn;
    private Map<String, Object> parameters;
    private String filenamePrefix;

    public ExportParameters(String clazz, String sortColumn, String filenamePrefix, Map<String, Object> parametersm, boolean xlsExport) {
        this.clazz = clazz;
        this.sortColumn = sortColumn;
        this.filenamePrefix = filenamePrefix;
        this.parameters = parametersm;
        this.xlsExport = xlsExport;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getFilenamePrefix() {
        return filenamePrefix;
    }

    public void setFilenamePrefix(String filenamePrefix) {
        this.filenamePrefix = filenamePrefix;
    }
}
