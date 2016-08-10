package android.support.v4.os.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.lang.NString;
import android.support.v4.lang.Strings;
import android.support.v4.util.Tools;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class Files {
    public static final String getTempFolderPath(final Context context) {
	return NString.parse(NString.parse(Environment.getExternalStorageDirectory()), Strings.SLASH, Strings.UPPA,
		Strings.LOWN, Strings.LOWD, Strings.LOWR, Strings.LOWO, Strings.LOWI, Strings.LOWD, Strings.SLASH,
		Strings.LOWD, Strings.LOWA, Strings.LOWT, Strings.LOWA, Strings.SLASH, context.getPackageName(),
		Strings.SLASH);
    }

    public static final File saveBitmap(final Context context, final Bitmap bitmap, final String name) {
	File result = null;
	try {
	    result = new File(getTempFolderPath(context), name);
	    final FileOutputStream out = new FileOutputStream(result);
	    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
	    out.flush();
	    out.close();
	} catch (final Exception exception) {
	    Tools.exceptionToJSONObject(exception);
	}
	return result;
    }

    public static final void zip(final String[] files, final String output) throws IOException {
	BufferedInputStream origin = null;
	final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
	try {
	    final byte data[] = new byte[1024];

	    for (final String file : files) {
		final FileInputStream inputStream = new FileInputStream(file);
		origin = new BufferedInputStream(inputStream, 1024);
		try {
		    final ZipEntry entry = new ZipEntry(file.substring(file.lastIndexOf(Character.valueOf('/')) + 1));
		    out.putNextEntry(entry);
		    int count;
		    while ((count = origin.read(data, 0, 1024)) != -1) {
			out.write(data, 0, count);
		    }
		} finally {
		    origin.close();
		}
	    }
	} finally {
	    out.close();
	}
    }

    public static final void copyFile(final InputStream inputStream, final OutputStream outputStream)
	    throws IOException {
	final byte[] buffer = new byte[1024];
	int read;
	while ((read = inputStream.read(buffer)) != -1) {
	    outputStream.write(buffer, 0, read);
	}
    }
}
