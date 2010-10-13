package pl.touk.tola.gwt.client.utils;

public class RegexpUtils {
    private static final String DECIMAL_SEPARATOR = "\\,";
    private static final String ONE_DIGIT_NUMBER = "[0-9]";
    private static final String ZERO = "0";
    public static final String DECIMAL_REGEXP = "([\\+\\-]|)[0-9]*(\\,[0-9]+)?";

    public static String optional(String regexp) {
        return "(" + regexp + "|)";
    }

    public static String alternative(String regexp, String ... alternatives) {
        String result = "((" + regexp;
        for (String altregexp : alternatives) {
            result = result + ")|(" + altregexp;
        }
        return result + "))";
    }

    public static String atLeastOnce(String regexp) {
        return "(" + regexp + ")+";
    }

    public static String createRegexpAcceptingPercentWithOptionalPercentSign(boolean allow100, int precisionInPercentForm) {
        String fractionPart = createRegexpAcceptingFractionPart(precisionInPercentForm);
        String regexpAcceptingNumbersBelow100 = createRegexpAcceptingRepetitions(ONE_DIGIT_NUMBER, 1, 2) + RegexpUtils.optional(fractionPart);
        String regexpAccepting100 = "100" + optional(DECIMAL_SEPARATOR + createRegexpAcceptingRepetitions(ZERO, 1, precisionInPercentForm));
        String finalRegexp = allow100 ? RegexpUtils.alternative(regexpAccepting100, regexpAcceptingNumbersBelow100) : regexpAcceptingNumbersBelow100;
        return finalRegexp + optional("\\%");
    }

    private static String createRegexpAcceptingRepetitions(String repeatedToken, int minOccurence, int maxOccurence) {
        return repeatedToken + "{" + Integer.toString(minOccurence) + "," + Integer.toString(maxOccurence) + "}";
    }

    private static String createRegexpAcceptingFractionPart(int precision) {
        return DECIMAL_SEPARATOR + createRegexpAcceptingRepetitions(ONE_DIGIT_NUMBER, 1, precision);
    }
}
