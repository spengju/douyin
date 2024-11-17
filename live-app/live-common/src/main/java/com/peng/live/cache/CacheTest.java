package com.peng.live.cache;

/**
 * @author shipengju
 */
public class CacheTest {
    public static void main(String[] args) throws InterruptedException {
        Cache<String, String> cache = new Cache<>(100); // 最大容量为 100

        cache.put("key1", "value1", 5000);

        System.out.println("获取 key1：" + cache.get("key1")); // 输出：value1

        Thread.sleep(6000);

        System.out.println("获取 key1：" + cache.get("key1")); // 输出：null

        cache.shutdown();
    }
}
