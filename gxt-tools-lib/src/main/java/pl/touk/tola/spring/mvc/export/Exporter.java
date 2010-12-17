package pl.touk.tola.spring.mvc.export;

import pl.touk.tola.spring.mvc.export.dao.JdbcExportGetter;
import java.io.OutputStream;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.ift.CellProcessor;
import pl.touk.tola.gwt.shared.ExportParameters;
import pl.touk.tola.spring.mvc.export.dao.HibernateExportGetter;
import pl.touk.tola.spring.mvc.export.beans.ExportData;

/**
 * Klasa exportująca
 *
 * Wymaga podania parametrów ExportParameters, które zawierają bean'a hibernate'owego uzytego
 * do wyciagniecia danych z bazy za pomocą wonderful dao (property clazz z ExportParameters).
 * Dane wynikowe sa posortowane po kolumnie (property) okreslonym w parametrze: sortColumn (z ExportParameters).
 * Dodatkowo uwzględniane są parametry w mapie parameters (z ExportParameters)
 *
 * @author rpietra
 */
public abstract class Exporter {

    JdbcExportGetter jdbcExportGetter;

    HibernateExportGetter hibernateExportGetter;

    protected Exporter(JdbcExportGetter jdbcExportGetter, HibernateExportGetter hibernateExportGetter) {
        this.jdbcExportGetter = jdbcExportGetter;
        this.hibernateExportGetter = hibernateExportGetter;
    }

    public abstract void exportData(ExportParameters exportParameters, OutputStream outputStream);

    protected CellProcessor[] createNullToEmptyCellProcessors(int size) {
        CellProcessor[] cellProcessors = new CellProcessor[size];
        for (int i=0;i<size;i++) {
            cellProcessors[i]=new ConvertNullTo(" ");
        }
        return cellProcessors;

    }

    protected ExportData exportData(ExportParameters  exportParameters) throws Exception {

        if (JdbcExportGetter.SqlPreparer.class.isAssignableFrom(getClass().getClassLoader().loadClass(exportParameters.getClazz()))) {
          return jdbcExportGetter.getData(exportParameters);
        } else {
            return hibernateExportGetter.getData(exportParameters);
        }


    }
}
