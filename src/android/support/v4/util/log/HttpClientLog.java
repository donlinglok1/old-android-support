package android.support.v4.util.log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import android.util.Log;

public class HttpClientLog {

	private static DalvikLogHandler activeHandler;

	public static class DalvikLogHandler extends Handler {

		private final static String LOG_TAG = "HttpClient";

		@Override
		public void close() {
		}

		@Override
		public void flush() {
		}

		@Override
		public void publish(final LogRecord record) {
			if (record.getLoggerName().startsWith("org.apache")) {
				Log.d(LOG_TAG, record.getMessage());
			}
		}
	}

	public static void enable() {
		try {
			final String config = "org.apache.http.impl.conn.level = FINEST\n"
					+ "org.apache.http.impl.client.level = FINEST\n"
					+ "org.apache.http.client.level = FINEST\n"
					+ "org.apache.http.level = FINEST";
			// String config = "org.apache.http.level = FINEST\n"
			// + "org.apache.http.wire.level = SEVERE\n";
			final InputStream inputStream = new ByteArrayInputStream(
					config.getBytes());
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (final IOException exception) {
			Log.w(HttpClientLog.class.getSimpleName(),
					"Can't read configuration file for logging");
		}
		final Logger rootLogger = LogManager.getLogManager().getLogger("");
		activeHandler = new DalvikLogHandler();
		activeHandler.setLevel(Level.ALL);
		rootLogger.addHandler(activeHandler);
	}

}