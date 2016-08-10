package android.support.v4.lang;

public class NDouble {
    public static Double parse(final Object obj) {
	return parse(obj, null);
    }

    public static Double parse(final Object obj, final Object def) {
	try {
	    return Double.parseDouble(NString.parse(obj));
	} catch (final Exception e) {
	}
	return (Double) def;
    }
}
