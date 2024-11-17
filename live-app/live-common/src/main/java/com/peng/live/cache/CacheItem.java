package com.peng.live.cache;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/17 17:04
 * @Desc:
 */
public class CacheItem<K, V> implements Delayed {
    private K key;
    private V value;
    private long expireTime; // 过期时间的时间戳（纳秒）

    public CacheItem(K key, V value, long ttl) {
        this.key = key;
        this.value = value;
        // 将过期时间转换为纳秒
        this.expireTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(ttl, TimeUnit.MILLISECONDS);
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = expireTime - System.nanoTime();
        return unit.convert(diff, TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == this) return 0;
        long diff = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return Long.compare(diff, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheItem)) return false;
        CacheItem<?, ?> that = (CacheItem<?, ?>) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
