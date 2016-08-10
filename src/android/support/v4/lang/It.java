package android.support.v4.lang;

public class It {
    public static boolean isNull(final Object object) {
	return null == object || NString.parse(object).length() == 0;
    }

    public static boolean isEqual(final Object objA, final Object objB) {
	if (isNull(objA)) {
	    return objB.equals(objA);
	} else {
	    return objA.equals(objB);
	}
    }
}
