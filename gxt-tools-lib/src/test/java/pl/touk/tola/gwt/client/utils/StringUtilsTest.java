package pl.touk.tola.gwt.client.utils;

import pl.touk.tola.gwt.client.utils.StringUtils;
import static org.junit.Assert.*;
import org.junit.Test;


public class StringUtilsTest {

	@Test
	public void shouldCapitalizeSingleWord() {
		//given
		String toCapitalize = "cAPitaliZE";
		String afterCapitalize = "Capitalize";
		
		//when
		String result = StringUtils.capitalize(toCapitalize);
		
		//then
		assertEquals(afterCapitalize, result);
	}
	
	@Test
	public void shouldCapitalizeTwoWords() {
		//given
		String toCapitalize = "cAPitaliZE ME";
		String afterCapitalize = "Capitalize Me";
		
		//when
		String result = StringUtils.capitalize(toCapitalize);
		
		//then
		assertEquals(afterCapitalize, result);
	}
	
	@Test
	public void shouldCapitalizeAndRemoveUnderscore() {
		//given
		String toCapitalize = "cAPitaliZE_ME_pleaSE";
		String afterCapitalize = "Capitalize Me Please";
		
		//when
		String result = StringUtils.capitalizeAndRemoveUnderscore(toCapitalize);
		
		//then
		assertEquals(afterCapitalize, result);
	}
	
}
