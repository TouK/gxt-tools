package pl.touk.tola.gwt.client.utils;

public final class StringUtils {
	
	public static String capitalizeAndRemoveUnderscore(String stringToFormat) {
		String result = "";
		if (stringToFormat != null && stringToFormat.length() > 0) {
			stringToFormat = stringToFormat.replace("_", " ");
			result = capitalize(stringToFormat);
		}
		return result;
	}

	public static String capitalize(String string) {
		String result = "";
		if (string != null && string.length() > 0) {
			String[] words = string.trim().split(" ");
			for (String word : words) {
				result += capitalizeWord(word) + " ";
			}
		}
		return result.trim();
	}
	
	private static String capitalizeWord(String string) {
		String result = "";
		if (string != null && string.length() > 0) {
			result = string.substring(0, 1).toUpperCase();
			result += string.length() > 1 ? string.substring(1).toLowerCase() : "";
		}
		return result;
	}
}
