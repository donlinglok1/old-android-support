package android.n;

public class NLong {
    public static Long parse(final Object obj) {
	return parse(obj, null);
    }

    public static Long parse(final Object obj, final String def) {
	return Long.parseLong(NString.parse(obj, def));
    }
}
