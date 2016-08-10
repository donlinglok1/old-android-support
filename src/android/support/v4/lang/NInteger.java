package android.support.v4.lang;

public class NInteger {
    public static Integer parse(final Object obj) {
	return parse(obj, null);
    }

    public static Integer parse(final Object obj, final Object def) {
	try {
	    return Integer.parseInt(NString.parse(obj));
	} catch (final Exception e) {
	}
	return (Integer) def;
    }
}
