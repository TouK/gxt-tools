package pl.touk.tola.spring.mvc.export;

import pl.touk.tola.gwt.shared.ExportParameters;
import pl.touk.tola.spring.mvc.export.beans.ExportData;


public interface ExportDataGetter {

    ExportData getData(ExportParameters parameters) throws Exception ;


}
