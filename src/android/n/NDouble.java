package android.n;

public class NDouble {
    public static Double parse(final Object obj) {
	return parse(obj, null);
    }

    public static Double parse(final Object obj, final String def) {
	return Double.parseDouble(NString.parse(obj, def));
    }
}
