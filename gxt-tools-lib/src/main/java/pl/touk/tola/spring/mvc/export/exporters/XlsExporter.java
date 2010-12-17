package pl.touk.tola.spring.mvc.export.exporters;

import pl.touk.tola.spring.mvc.export.Exporter;
import pl.touk.tola.spring.mvc.export.dao.JdbcExportGetter;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import pl.touk.tola.gwt.shared.ExportParameters;
import pl.touk.tola.spring.mvc.export.beans.ExportData;
import pl.touk.tola.spring.mvc.export.dao.HibernateExportGetter;

/**
 * Klasa exportująca do XLS w standardzie excel'a
 * <p/>
 * Wymaga podania parametrów ExportParameters, które zawierają bean'a hibernate'owego uzytego
 * do wyciagniecia danych z bazy za pomocą wonderful dao (property clazz z ExportParameters).
 * Dane wynikowe sa posortowane po kolumnie (property) okreslonym w parametrze: sortColumn (z ExportParameters).
 * Dodatkowo uwzględniane są parametry w mapie parameters (z ExportParameters)
 *
 * @author rpietra
 */
public class XlsExporter extends Exporter {

     
    public XlsExporter(JdbcExportGetter jdbcExportGetter, HibernateExportGetter hibernateExportGetter) {
        super(jdbcExportGetter, hibernateExportGetter);
    }

    @Override
    public void exportData(ExportParameters exportParameters, OutputStream outputStream) {

        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            CreationHelper createHelper = wb.getCreationHelper();

            //Workbook wb = new XSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Export z NSW");
            wb.createSheet("new sheet2");
            ExportData exportData = exportData(exportParameters);

            String[] headerNames = exportData.headers;

            DataFormat dataFormat = wb.createDataFormat();

            CellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
            dateStyle.setDataFormat(dataFormat.getFormat("dd-mmm-yy"));

            CellStyle decimalStyle = wb.createCellStyle();
            decimalStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("0.00"));

            HSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < headerNames.length; i++) {
                // Create a cell and put a value in it.
                HSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(headerNames[i]);
            }

            for (int rowIter = 0; rowIter < exportData.data.size(); rowIter++) {
                HSSFRow row = sheet.createRow(rowIter + 1);
                for (int i = 0; i < headerNames.length; i++) {
                    // Create a cell and put a value in it.
                    HSSFCell cell = row.createCell(i);
                    Object data = exportData.data.get(rowIter);
                    if (data instanceof List) {
                        data = ((List) data).get(i);
                    } else {
                        data = BeanUtils.getProperty(exportData.data.get(rowIter), headerNames[i]);
                    }
                    setCellValue(cell, data, dateStyle, decimalStyle);
                }
            }
            wb.write(outputStream);
            return;

        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private void setCellValue(HSSFCell cell, Object value, CellStyle dateStyle, CellStyle valueStyle) {
        if (value == null) {
            return;
        } else if (value instanceof Date) {
            cell.setCellStyle(dateStyle);
            cell.setCellValue((Date) value);
        } else if (value instanceof Calendar) {
            cell.setCellStyle(dateStyle);
            cell.setCellValue((Calendar) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellStyle(valueStyle);
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else {
            cell.setCellValue(value.toString());
        }

    }

}
