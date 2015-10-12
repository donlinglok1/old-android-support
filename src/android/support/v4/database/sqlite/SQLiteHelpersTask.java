package android.support.v4.database.sqlite;

import android.database.Cursor;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class SQLiteHelpersTask {
	public final static int EXE = 0;
	public final static int RAW = 1;

	public interface SQLiteCallback {
		void onReturn(Cursor result);
	}

	public SQLiteHelpersTask(final SQLiteCallback callback, final String query,
			final int taskType, final SQLiteHelpers sqLiteOpenHelpers) {
		if (taskType == RAW) {
			sqLiteOpenHelpers.raw(callback, query);
		} else {
			sqLiteOpenHelpers.execute(callback, query);
		}
	}
}