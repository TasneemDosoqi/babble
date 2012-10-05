package be.lukin.android.lang;

public class Log {

	private static final boolean DEBUG = true;

	public static final String LOG_TAG = "be.lukin.android.lang";

	public static void i(String msg) {
		if (DEBUG) android.util.Log.i(LOG_TAG, msg);
	}

	public static void e(String msg) {
		if (DEBUG) android.util.Log.e(LOG_TAG, msg);
	}

	public static void i(String tag, String msg) {
		if (DEBUG) android.util.Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (DEBUG) android.util.Log.e(tag, msg);
	}
}
