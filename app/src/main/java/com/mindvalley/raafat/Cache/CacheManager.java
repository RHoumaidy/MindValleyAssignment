package com.mindvalley.raafat.Cache;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raafat alhmidi on 2/8/2019 @4:30 PM.
 */
public class CacheManager {

    private List<CacheItem> cache;
    private long maxCacheSize;
    private long size;
    private static CacheManager instance;

    public CacheManager() {
        maxCacheSize = Runtime.getRuntime().maxMemory() / 8;
        cache = new ArrayList<>();
    }

    public List<CacheItem> getCache() {
        return cache;
    }

    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public void setCache(List<CacheItem> cache) {
        this.cache = cache;
    }

    public long getMaxCacheSize() {
        return maxCacheSize;
    }

    public void setMaxCacheSize(long maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void clearCache() {
        cache.clear();
    }

    public void addItem(String key, byte[] data) {
        CacheItem cacheItem = new CacheItem(key, data);
        cache.add(0, cacheItem);  //add item at the first position to implement LRU (Least Recently Used replacement approach)
        size += data.length;

    }

    public void removeItem(String key) {
        int idx = cache.indexOf(new CacheItem(key));
        if (idx >= 0) {
            CacheItem cacheItem = cache.get(idx);
            size -= cacheItem.getData().length;
            cache.remove(idx);
        }
    }

    public void removeOldestItme() {
        CacheItem cacheItem = cache.get(cache.size() - 1); //remove item from the last position to implement LRU (Least Recently Used replacement approach)
        removeItem(cacheItem.getKey());
    }

    public void cleanCache(int neadedMemorySize) {
        while (size + neadedMemorySize >= maxCacheSize) {
            removeOldestItme();
        }
    }

    public CacheItem cacheHit(String key) {
        CacheItem res = null;
        int idx = cache.indexOf(new CacheItem(key));
        if (idx >= 0) {// if the item is exists, add it to the first position to implement LRU (Least Recently Used replacement approach)
            res = cache.get(idx);
            cache.remove(idx);
            addItem(res.getKey(),res.getData());
        }
        return res;
    }

    public static class CacheItem implements Comparable<CacheItem> {
        private String key;
        private byte[] data;
        private long timestamp;

        public CacheItem() {
            this.timestamp = System.currentTimeMillis();
        }

        public CacheItem(String key) {
            this();
            this.key = key;
        }

        public CacheItem(String key, byte[] data) {
            this(key);
            this.data = data;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            return obj instanceof CacheItem
                    && this.getKey().equals(((CacheItem) obj).getKey());
        }

        @Override
        public int compareTo(CacheItem o) {
            return (int) (this.getTimestamp() - o.getTimestamp());
        }
    }


}
