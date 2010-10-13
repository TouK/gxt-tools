package pl.touk.tola.gwt.client.utils;

import com.google.gwt.i18n.client.NumberFormat;

import java.math.BigDecimal;

import static pl.touk.tola.gwt.client.utils.BigDecimalConverter.convertFromDouble;
import static pl.touk.tola.gwt.client.utils.BigDecimalConverter.convertToDouble;

public class DecimalsFormatter {

    public static final String DECIMAL_SEPARATOR = ",";
    public static final DecimalsFormatter DECIMAL_FORMATTER = new DecimalsFormatter(NumberFormat.getFormat("#,###,##0.0##"));
    public static final DecimalsFormatter PERCENT_FORMATTER = new DecimalsFormatter(NumberFormat.getFormat("#,###,##0.00##%"));

    private final NumberFormat format;

    public DecimalsFormatter(NumberFormat number_format) {
        format = number_format;
    }

    public String format(BigDecimal value) {
        if (value == null) {
            return null;
        }

        double valueAsDouble = convertToDouble(value);
        return format.format(valueAsDouble);
    }

    public BigDecimal parseToBigDecimal(String valueAsString) {
        if (valueAsString == null) {
            return null;
        }

        double valueAsDouble = format.parse(valueAsString);
        return convertFromDouble(valueAsDouble);
    }
}
