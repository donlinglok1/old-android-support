package android.support.v4.lang;

public class NBoolean {
    public static Boolean parse(final Object obj) {
	return parse(obj, null);
    }

    public static Boolean parse(final Object obj, final Object def) {
	try {
	    return Boolean.parseBoolean(NString.parse(obj));
	} catch (final Exception e) {
	}
	return (Boolean) def;
    }
}
