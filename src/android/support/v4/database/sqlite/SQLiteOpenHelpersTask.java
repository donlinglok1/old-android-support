package android.support.v4.database.sqlite;

import android.database.Cursor;
import android.os.AsyncTask;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class SQLiteOpenHelpersTask extends AsyncTask<Void, Void, String> {
	private final transient String query;
	private final transient SQLiteCallback callback;

	public final static int EXE = 0;
	public final static int RAW = 1;
	private final transient int taskType;

	public interface SQLiteCallback {
		void onReturn(Cursor result);
	}

	public SQLiteOpenHelpersTask(final SQLiteCallback callback,
			final String query, final int taskType,
			final SQLiteOpenHelpers sqLiteOpenHelpers) {
		super();
		this.sqLiteOpenHelpers = sqLiteOpenHelpers;
		this.query = query;
		this.callback = callback;
		this.taskType = taskType;
	}

	private final transient SQLiteOpenHelpers sqLiteOpenHelpers;

	@Override
	protected String doInBackground(final Void... params) {
		if (taskType == RAW) {
			sqLiteOpenHelpers.raw(callback, query);
		} else {
			sqLiteOpenHelpers.execute(callback, query);
		}
		return null;
	}
}