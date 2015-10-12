package android.support.v4.database.sqlite;

//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.v4.database.sqlite.SQLiteHelpersTask.SQLiteCallback;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class SQLiteHelpers extends SQLiteOpenHelper {
	private transient final SQLiteDatabase sqLiteDatabase;

	private final static String DB_NAME = "notification.wav";
	public final static String DB_PW = "27032015";

	public final static int SYSTEM = 0;
	public final static int ANDROID_DATA = 1;
	public final static int SQLCIPHER_SYSTEM = 2;
	public final static int SQLCIPHER_ANDROID_DATA = 3;

	public SQLiteHelpers(final Context context, final int version_code,
			final int dbStorageLocation) {
		super(context, DB_NAME, null, version_code);
		switch (dbStorageLocation) {
		case ANDROID_DATA:
			sqLiteDatabase = SQLiteDatabase.openDatabase(
					Environment.getExternalStorageDirectory()
							+ "/Android/data/" + context.getPackageName() + "/"
							+ DB_NAME, null, Context.MODE_PRIVATE);
			break;
		// case SQLCIPHER_SYSTEM:
		// SQLiteDatabase.loadLibs(context);
		// sqLiteDatabase = getWritableDatabase(DB_PW);
		// //THE sqlcipher are 10 time final slow than default.
		// break;
		// case SQLCIPHER_ANDROID_DATA:
		// sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(
		// Environment.getExternalStorageDirectory()
		// + "/Android/data/" + context.getPackageName() + "/"
		// + DB_NAME, DB_PW, null);
		// break;
		case SYSTEM:
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

	public void onDestroy() {
		sqLiteDatabase.close();
	}

	@Override
	public void onCreate(final SQLiteDatabase arg0) {
	}

	@Override
	public void onUpgrade(final SQLiteDatabase arg0, final int arg1,
			final int arg2) {
	}

}
