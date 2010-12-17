package pl.touk.tola.spring.mvc.export.exporters;

import pl.touk.tola.spring.mvc.export.beans.ExportData;
import pl.touk.tola.spring.mvc.export.Exporter;
import pl.touk.tola.spring.mvc.export.dao.JdbcExportGetter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import pl.touk.tola.gwt.shared.ExportParameters;
import pl.touk.tola.spring.mvc.export.dao.HibernateExportGetter;

/**
 * Klasa exportująca do CSV w standardzie excel'a
 *
 * Wymaga podania parametrów ExportParameters, które zawierają bean'a hibernate'owego uzytego
 * do wyciagniecia danych z bazy za pomocą wonderful dao (property clazz z ExportParameters).
 * Dane wynikowe sa posortowane po kolumnie (property) okreslonym w parametrze: sortColumn (z ExportParameters).
 * Dodatkowo uwzględniane są parametry w mapie parameters (z ExportParameters)
 *
 * @author rpietra
 */
public class CsvExporter extends Exporter {

    public CsvExporter(JdbcExportGetter jdbcExportGetter, HibernateExportGetter hibernateExportGetter) {
        super(jdbcExportGetter, hibernateExportGetter);
    }

    /**
     * Metoda musi zostać nadpisana.
     *
     * @param exportParameters parametry do eksportu
     * @param outputStream strumien, do ktorego trzeba wypisac eksportowane dane
     */
    @Override
    public void exportData(ExportParameters exportParameters, OutputStream outputStream) {

        OutputStreamWriter outFile = new OutputStreamWriter(outputStream);
        try {
            CsvBeanWriter beanWriter = new CsvBeanWriter(outFile, CsvPreference.EXCEL_PREFERENCE);

            ExportData exportData = exportData(exportParameters);

            beanWriter.writeHeader(exportData.headers);

            CellProcessor[] cellProcessors = createNullToEmptyCellProcessors(exportData.headers.length);
            for (Object object : exportData.data) {
                //FIXME: a co jak to bedzie jdbc ://
                beanWriter.write(object, exportData.headers, cellProcessors);
            }
            beanWriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return;
    }
}
