package com.peng.live.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: spengju
 * @Slogan: Day day no bug.
 * @Date: 2024/11/17 17:04
 * @Desc:
 */
public class Cache<K, V> {
    private ConcurrentHashMap<K, V> cacheMap;
    private DelayQueue<CacheItem<K, V>> delayQueue;
    private ExecutorService executor;
    private final int maxCapacity;

    public Cache(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        cacheMap = new ConcurrentHashMap<>();
        delayQueue = new DelayQueue<>();
        executor = Executors.newSingleThreadExecutor();
        initExpiredCleaner();
    }

    // 初始化过期缓存清理任务
    private void initExpiredCleaner() {
        executor.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // 从 DelayQueue 中获取已过期的缓存项（阻塞操作）
                    CacheItem<K, V> item = delayQueue.take();
                    cacheMap.remove(item.getKey());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    // 添加缓存项
    public void put(K key, V value, long ttl) {
        if (cacheMap.size() >= maxCapacity) {
            // 移除最早过期的缓存项
            CacheItem<K, V> expiredItem = delayQueue.poll();
            if (expiredItem != null) {
                cacheMap.remove(expiredItem.getKey());
            }
        }
        cacheMap.put(key, value);
        CacheItem<K, V> item = new CacheItem<>(key, value, ttl);
        delayQueue.put(item);
    }

    // 获取缓存项
    public V get(K key) {
        return cacheMap.get(key);
    }

    // 移除缓存项
    public void remove(K key) {
        cacheMap.remove(key);
        CacheItem<K, V> dummyItem = new CacheItem<>(key, null, 0);
        delayQueue.remove(dummyItem);
    }

    // 清除所有缓存项
    public void clear() {
        cacheMap.clear();
        delayQueue.clear();
    }

    // 关闭缓存（停止后台线程）
    public void shutdown() {
        executor.shutdownNow();
    }
}
