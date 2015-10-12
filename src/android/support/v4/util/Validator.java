package android.support.v4.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	public final static boolean validateEmail(final String hex) {
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		final Matcher matcher = pattern.matcher(hex);
		return matcher.matches();
	}
}