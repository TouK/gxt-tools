package pl.touk.tola.gwt.client.utils;

import java.math.BigDecimal;

public class BigDecimalConverter {

    public static Double convertToDouble(BigDecimal decimal) {
        String stringRepresentation = decimal.toString();
        return Double.parseDouble(stringRepresentation);
    }

    public static BigDecimal convertFromDouble(Double value) {
        String stringRepresentation = value.toString();
        return new BigDecimal(stringRepresentation);
    }
}
