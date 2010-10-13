package pl.touk.tola.gwt.client.utils;

import pl.touk.tola.gwt.client.utils.BigDecimalConverter;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BigDecimalConverterTest {
    @Test
    public void shouldConvertToDouble() throws Exception {
        //given
        BigDecimal decimal = new BigDecimal("123.456");

        //when
        Double doubleValue = BigDecimalConverter.convertToDouble(decimal);

        //then
        assertEquals(123.456d, doubleValue, 0.0000001);
    }

    @Test
    public void shouldConvertFromDouble() throws Exception {
        //given
        Double doubleValue = 123.456d;

        //when
        BigDecimal decimal = BigDecimalConverter.convertFromDouble(doubleValue);

        //then
        assertEquals(new BigDecimal("123.456"), decimal);
    }
}
