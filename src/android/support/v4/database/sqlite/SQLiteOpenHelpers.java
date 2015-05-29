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
	private final transient SQLiteDatabase sqLiteDatabase;

	private final static String DB_NAME = "notification.wav";
	public final static String DB_PW = "27032015";

	public final static int SYSTEM = 0;
	public final static int ANDROID_DATA = 1;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(final SQLiteDatabase arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(final SQLiteDatabase arg0, final int arg1,
			final int arg2) {
	}

	public void onDestroy() {
		sqLiteDatabase.close();
	}
}
