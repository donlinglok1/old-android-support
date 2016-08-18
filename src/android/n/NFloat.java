package android.n;

public class NFloat {
    public static Float parse(final Object obj) {
	return parse(obj, null);
    }

    public static Float parse(final Object obj, final String def) {
	return Float.parseFloat(NString.parse(obj, def));
    }
}
