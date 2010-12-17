package pl.touk.tola.spring.mvc.export.beans;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ExportData {

    public final List data;

    public final String[] headers;

    public ExportData(List data, List<String> headers) {
        this.data = data;
        this.headers = headers.toArray(new String[headers.size()]);
    }
}
