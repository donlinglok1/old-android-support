package android.support.v4.lang;

public class NFloat {
    public static Float parse(final Object obj) {
	return parse(obj, null);
    }

    public static Float parse(final Object obj, final Object def) {
	try {
	    return Float.parseFloat(NString.parse(obj));
	} catch (final Exception e) {
	}
	return (Float) def;
    }
}
