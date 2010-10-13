package pl.touk.tola.gwt.client.utils;

import pl.touk.tola.gwt.client.utils.RegexpUtils;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class RegexpUtilsTest {
    @Test
    public void shouldCreateOptionalRegexpCorrectly() throws Exception {
        //given
        String regexp = "regexp";

        //when
        String optionalRegexp = RegexpUtils.optional(regexp);

        //then
        assertEquals("(regexp|)", optionalRegexp);
    }

    @Test
    public void shouldCreateAlternativeOfTwoCorrectly() throws Exception {
        //given
        String regexp = "(reg)+(ex|p)*";
        String alt = "alt";

        //when
        String optionalRegexp = RegexpUtils.alternative(regexp, alt);

        //then
        assertEquals("(((reg)+(ex|p)*)|(alt))", optionalRegexp);
    }

    @Test
    public void shouldCreateAlternativeOfOneCorrectly() throws Exception {
        //given
        String regexp = "regexp";

        //when
        String optionalRegexp = RegexpUtils.alternative(regexp);

        //then
        assertEquals("((regexp))", optionalRegexp);
    }

    @Test
    public void shouldCreateAlternativeOfThreeCorrectly() throws Exception {
        //given
        String regexp = "regexp";
        String alt1 = "(al)*t";
        String alt2 = "a(lt)*";

        //when
        String optionalRegexp = RegexpUtils.alternative(regexp, alt1, alt2);

        //then
        assertEquals("((regexp)|((al)*t)|(a(lt)*))", optionalRegexp);
    }

    @Test
    public void shouldAcceptPercentAsPercentWithOptionalPercentSign() {
        //given
        String pattern = RegexpUtils.createRegexpAcceptingPercentWithOptionalPercentSign(false, 2);
        String value = "10%";

        //when
        boolean match = Pattern.matches(pattern, value);

        //then
        assertTrue(match);
    }

    @Test
    public void shouldAcceptPercentWithFractionAsPercentWithOptionalPercentSign() {
        //given
        String pattern = RegexpUtils.createRegexpAcceptingPercentWithOptionalPercentSign(false, 2);
        String value = "10,5%";

        //when
        boolean match = Pattern.matches(pattern, value);

        //then
        assertTrue(match);
    }

    @Test
    public void shouldAcceptIntegerAsPercentWithOptionalPercentSign() {
        //given
        String pattern = RegexpUtils.createRegexpAcceptingPercentWithOptionalPercentSign(false, 2);
        String value = "99";

        //when
        boolean match = Pattern.matches(pattern, value);

        //then
        assertTrue(match);
    }

    @Test
    public void shouldAcceptDecimalAsPercentWithOptionalPercentSign() {
        //given
        String pattern = RegexpUtils.createRegexpAcceptingPercentWithOptionalPercentSign(false, 2);
        String value = "99,99";

        //when
        boolean match = Pattern.matches(pattern, value);

        //then
        assertTrue(match);
    }

    @Test
    public void shouldNotAcceptDecimalsWithDotAsPercentWithOptionalPercentSign() {
        //given
        String pattern = RegexpUtils.createRegexpAcceptingPercentWithOptionalPercentSign(false, 2);
        String value = "10.98";

        //when
        boolean match = Pattern.matches(pattern, value);

        //then
        assertFalse(match);
    }

    @Test
    public void shouldNotAcceptTextAsPercentWithOptionalPercentSign() {
        //given
        String pattern = RegexpUtils.createRegexpAcceptingPercentWithOptionalPercentSign(false, 2);
        String value = "bla";

        //when
        boolean match = Pattern.matches(pattern, value);

        //then
        assertFalse(match);
    }

    @Test
    public void shouldAcceptCorrectDecimalValues() {

        shouldMatchDecimal("122,3444");
        shouldMatchDecimal("122");
        shouldMatchDecimal("+122,22");
        shouldMatchDecimal("-122,22");
        shouldNotMatchDecimal("--122,22");
        shouldNotMatchDecimal("aa");
        shouldNotMatchDecimal("12,");
        shouldNotMatchDecimal("124,5454,33");





    }

    private void shouldMatchDecimal(String value) {
        assertTrue(Pattern.matches(RegexpUtils.DECIMAL_REGEXP, value));
    }

    private void shouldNotMatchDecimal(String value) {
        assertFalse(Pattern.matches(RegexpUtils.DECIMAL_REGEXP, value));
    }
}
