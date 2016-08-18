package android.n;

public class NBoolean {
    public static Boolean parse(final Object obj) {
	return parse(obj, null);
    }

    public static Boolean parse(final Object obj, final String def) {
	return Boolean.parseBoolean(NString.parse(obj, def));
    }
}
