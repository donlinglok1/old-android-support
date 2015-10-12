package android.support.v4.os.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class Files {
	public final static void zip(final String[] files, final String output)
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

	public final static void copyFile(final InputStream inputStream,
			final OutputStream outputStream) throws IOException {
		final byte[] buffer = new byte[1024];
		int read;
		while ((read = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, read);
		}
	}
}
