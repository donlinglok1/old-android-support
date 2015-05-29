package android.support.v4.util;

import java.util.Iterator;

import android.graphics.Bitmap;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class LruCaches {
	private final transient LruCache<String, Bitmap> lruCache;

	public LruCaches() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = (int) (maxMemory * 0.8);
		lruCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(final String key, final Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
			}
		};
	}

	public Bitmap get(final String key) {
		return lruCache.get(key);
	}

	public void put(final String key, final Bitmap bitmap) {
		if (null == get(key)) {
			lruCache.put(key, bitmap);
		}
	}

	public void remove(final String key) {
		if (null != key && null != lruCache) {
			final Bitmap bitmap = lruCache.remove(key);
			bitmap.recycle();
		}
	}

	public void evictAll() {
		if (null != lruCache && lruCache.size() > 0) {
			lruCache.evictAll();
			final Iterator<?> iterator = lruCache.snapshot().values()
					.iterator();
			if (iterator.hasNext()) {
				((Bitmap) iterator.next()).recycle();
			}
		}
	}
}
