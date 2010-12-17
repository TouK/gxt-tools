package pl.touk.tola.spring.mvc.export.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.touk.tola.spring.mvc.export.exporters.CsvExporter;
import pl.touk.tola.spring.mvc.export.exporters.XlsExporter;
import static org.junit.Assert.*;

/**
 *
 * @author rpietra
 */
@RunWith(MockitoJUnitRunner.class)
public class ExportControllerTest {

    private static final String INTEGER_PARAM = "2||java.lang.Integer";
    private static final String LONG_PARAM = "2||java.lang.Long";
    private static final String STRING_PARAM = "aa||java.lang.String";
    @Mock
    CsvExporter csvExporter;

    @Mock
    XlsExporter xlsExporter;

    @Test
    public void shouldDecodeInteger() {
        //given
        ExportController controller = new ExportController(csvExporter, xlsExporter);

        //when
        Object paramValue = controller.decodeParameterValue(INTEGER_PARAM);

        //then
        assertTrue(paramValue instanceof Integer);
        assertTrue(((Integer) paramValue).equals(2));
    }

    @Test
    public void shouldDecodeLong() {
        //given
        ExportController controller = new ExportController(csvExporter, xlsExporter);

        //when
        Object paramValue = controller.decodeParameterValue(LONG_PARAM);

        //then
        assertTrue(paramValue instanceof Long);
        assertTrue(((Long) paramValue).equals(2l));
    }

    @Test
    public void shouldDecodeString() {
        //given
        ExportController controller = new ExportController(csvExporter, xlsExporter);

        //when
        Object paramValue = controller.decodeParameterValue(STRING_PARAM);

        //then
        assertTrue(paramValue instanceof String);
        assertTrue(((String) paramValue).equals("aa"));
    }

      @Test(expected=IllegalArgumentException.class)
    public void invalidParamShouldThrowException() {
        //given
        ExportController controller = new ExportController(csvExporter, xlsExporter);

        //when and then
        Object paramValue = controller.decodeParameterValue("wyganiałaKasiaWołki");
    }
}
