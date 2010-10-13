package pl.touk.tola.gwt.client.utils.editors;

import pl.touk.tola.gwt.client.utils.editors.PercentPropertyEditor;
import org.junit.Test;

import static org.junit.Assert.*;

public class PercentPropertyEditorTest {

    @Test
    public void shouldDivideShortIntegerStringBy100() {
        //given
        String value = "1";

        //when
        String divided = PercentPropertyEditor.divideDecimalStringBy100(value);

        //then
        assertEquals("0,01", divided);
    }

    @Test
    public void shouldDivideLongIntegerStringBy100() {
        //given
        String value = "1234";

        //when
        String divided = PercentPropertyEditor.divideDecimalStringBy100(value);

        //then
        assertEquals("12,34", divided);
    }

    @Test
    public void shouldDivideShortFractionStringBy100() {
        //given
        String value = "0,1";

        //when
        String divided = PercentPropertyEditor.divideDecimalStringBy100(value);

        //then
        assertEquals("0,001", divided);
    }

    @Test
    public void shouldDivideLongFractionStringBy100() {
        //given
        String value = "0,123456";

        //when
        String divided = PercentPropertyEditor.divideDecimalStringBy100(value);

        //then
        assertEquals("0,00123456", divided);
    }

    @Test
    public void shouldDivideShortDecimalStringBy100() {
        //given
        String value = "1,4";

        //when
        String divided = PercentPropertyEditor.divideDecimalStringBy100(value);

        //then
        assertEquals("0,014", divided);
    }

    @Test
    public void shouldDivideLongDeciamlStringBy100() {
        //given
        String value = "12345,6789";

        //when
        String divided = PercentPropertyEditor.divideDecimalStringBy100(value);

        //then
        assertEquals("123,456789", divided);
    }

    @Test
    public void shouldWorkForPercents() {
        //given
        String value = "12345,6789%";

        //when
        String divided = PercentPropertyEditor.divideDecimalStringBy100(value);

        //then
        assertEquals("123,456789%", divided);
    }
}
