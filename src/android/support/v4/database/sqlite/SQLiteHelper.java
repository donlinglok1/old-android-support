/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
package android.support.v4.database.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;
import android.os.Environment;

/**
 *
 * @author kennetht
 *
 */
public class SQLiteHelper extends SQLiteOpenHelper {
	private final transient SQLiteDatabase sqLiteDatabase;

	private static final String DB_NAME = "notification.wav";
	public static final String DB_PW = "27032015";
	public static final int SYSTEM = 0;
	public static final int ANDROID_DATA = 1;

	/**
	 *
	 * @author kennetht
	 *
	 */
	public static abstract interface Callback {
		public abstract void onReturn(Cursor result);
	}

	/**
	 *
	 * @param context
	 * @param version_code
	 * @param dbStorageLocation
	 */
	public SQLiteHelper(final Context context, final int version_code, final int dbStorageLocation) {
		super(context, DB_NAME, null, version_code);
		switch (dbStorageLocation) {
		default:
		case SYSTEM:
			sqLiteDatabase = getWritableDatabase();
			break;
		case ANDROID_DATA:
			sqLiteDatabase = SQLiteDatabase.openDatabase(Environment.getExternalStorageDirectory() + "/Android/data/"
					+ context.getPackageName() + "/" + DB_NAME, null, Context.MODE_PRIVATE);
			break;
		}

	}

	/**
	 *
	 * @param query
	 */
	public final void execute(final String query) {
		execute(query, null);
	}

	/**
	 *
	 * @param query
	 * @param callback
	 */
	public final void execute(final String query, final Callback callback) {
		sqLiteDatabase.execSQL(query);
		if (null != callback) {
			callback.onReturn(null);
		}
	}

	/**
	 *
	 * @param query
	 */
	public final void raw(final String query) {
		raw(query, null);
	}

	/**
	 *
	 * @param query
	 * @param callback
	 */
	public final void raw(final String query, final Callback callback) {
		if (null != callback) {
			callback.onReturn(sqLiteDatabase.rawQuery(query, null));
		}
	}

	@Override
	public void onCreate(final SQLiteDatabase arg0) {
	}

	@Override
	public void onUpgrade(final SQLiteDatabase arg0, final int arg1, final int arg2) {
	}

	public final void onDestroy() {
		sqLiteDatabase.close();
	}

}
