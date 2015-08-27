package android.support.v4.database.sqlite;

//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.v4.database.sqlite.SQLiteOpenHelpersTask.SQLiteCallback;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class SQLiteOpenHelpers extends SQLiteOpenHelper {
	private transient final SQLiteDatabase sqLiteDatabase;

	private static final String DB_NAME = "notification.wav";
	public static final String DB_PW = "27032015";

	public static final int SYSTEM = 0;
	public static final int ANDROID_DATA = 1;

	public SQLiteOpenHelpers(final Context context, final int version_code,
			final int dbStorageLocation) {
		super(context, DB_NAME, null, version_code);
		switch (dbStorageLocation) {
		case ANDROID_DATA:
			sqLiteDatabase = SQLiteDatabase.openDatabase(
					Environment.getExternalStorageDirectory()
							+ "/Android/data/" + context.getPackageName() + "/"
							+ DB_NAME, null, Context.MODE_PRIVATE);
			break;
		// case 2:
		// SQLiteDatabase.loadLibs(context);
		// sqLiteDatabase = getWritableDatabase(DB_PW);
		// THE sqlcipher are 10 time final slow than default.
		// break;
		// case 3:
		// sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(
		// Environment.getExternalStorageDirectory()
		// + "/Android/data/" + context.getPackageName() + "/"
		// + DB_NAME, DB_PW, null);
		// break;
		case SYSTEM:
			sqLiteDatabase = getWritableDatabase();
			break;
		default:
			sqLiteDatabase = getWritableDatabase();
			break;
		}

	}

	public void execute(final SQLiteCallback callback, final String query) {
		sqLiteDatabase.execSQL(query);
		if (null != callback) {
			callback.onReturn(null);
		}
	}

	public void raw(final SQLiteCallback callback, final String query) {
		if (null != callback) {
			callback.onReturn(sqLiteDatabase.rawQuery(query, null));
		}
	}

	@Override
	public void onCreate(final SQLiteDatabase arg0) {
	}

	@Override
	public void onUpgrade(final SQLiteDatabase arg0, final int arg1,
			final int arg2) {
	}

	public void onDestroy() {
		sqLiteDatabase.close();
	}
}
