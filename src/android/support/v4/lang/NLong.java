package android.support.v4.lang;

public class NLong {
    public static Long parse(final Object obj) {
	return parse(obj, null);
    }

    public static Long parse(final Object obj, final Object def) {
	try {
	    return Long.parseLong(NString.parse(obj));
	} catch (final Exception e) {
	}
	return (Long) def;
    }
}
