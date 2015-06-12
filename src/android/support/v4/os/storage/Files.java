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
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.lang.Strings;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public final class Files {
	private Files() {
	}

	public static void zip(final String[] files, final String output)
			throws IOException {
		BufferedInputStream origin = null;
		final ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(new FileOutputStream(output)));
		try {
			final byte data[] = new byte[1024];

			for (final String file : files) {
				final FileInputStream inputStream = new FileInputStream(file);
				origin = new BufferedInputStream(inputStream, 1024);
				try {
					final ZipEntry entry = new ZipEntry(file.substring(file
							.lastIndexOf(Character.valueOf('/')) + 1));
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

	private static final String[] NOCOPYFILE = { "fonts", "googlemap",
			"images", "offlinemap", "sounds", "webkit",
			"crashlytics-build.properties", "icudt46l.zip" };

	public static void copyAssets(final Context context) throws IOException {
		copyAssets(context, Strings.EMPTY);
	}

	public static void copyAssets(final Context context, final String folderName)
			throws IOException {
		final String dirPathString = Strings.fString(
				Strings.valueOf(Environment.getExternalStorageDirectory()),
				Strings.SLASH, Strings.UPPA, Strings.LOWN, Strings.LOWD,
				Strings.LOWR, Strings.LOWO, Strings.LOWI, Strings.LOWD,
				Strings.SLASH, Strings.LOWD, Strings.LOWA, Strings.LOWT,
				Strings.LOWA, Strings.SLASH, context.getPackageName(),
				Strings.SLASH, folderName);
		new File(dirPathString).mkdirs();

		final AssetManager assetManager = context.getAssets();
		final String[] files = assetManager.list(folderName.replace(
				Strings.SLASH, Strings.EMPTY));
		if (null != files) {
			for (final String file : files) {
				try {
					boolean isNoCopyFile = false;
					for (final String element : NOCOPYFILE) {
						if (element.equals(file)) {
							isNoCopyFile = true;
						}
					}
					if (!isNoCopyFile) {
						if (file.contains(".")) {
							final File outFile = new File(dirPathString, file);
							if (!outFile.exists()) {
								final InputStream inputStream = assetManager
										.open(Strings.fString(folderName, file));
								final OutputStream outputStream = new FileOutputStream(
										outFile);
								copyFile(inputStream, outputStream);
								inputStream.close();
								outputStream.flush();
								outputStream.close();
							}
						} else {
							copyAssets(context,
									Strings.fString(file, Strings.SLASH));
						}
					}
				} catch (final Exception exception) {
				}
			}
		}
	}

	public static void copyFile(final InputStream inputStream,
			final OutputStream outputStream) throws IOException {
		final byte[] buffer = new byte[1024];
		int read;
		while ((read = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, read);
		}
	}
}
