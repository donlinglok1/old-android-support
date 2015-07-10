package android.support.v4.database.sqlite;

import android.database.Cursor;
import android.support.v4.util.AsyncTask;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class SQLiteOpenHelpersTask extends AsyncTask<Void, Void, String> {
	private transient final String query;
	private transient final SQLiteCallback callback;

	public static final int EXE = 0;
	public static final int RAW = 1;
	private transient final int taskType;

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

	private transient final SQLiteOpenHelpers sqLiteOpenHelpers;

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