package com.hihonor.adsdk.demo.external.utils;

import java.util.Map;

/**
 * Map工具类
 */
public class MapUtils {

    /**
     * 检查传入的Map是否为空
     *
     * @param map 要检查的Map
     * @return 如果Map为空或大小为0，则返回true，否则返回false
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        // 如果Map为null或者Map的大小为0，那么返回true，否则返回false
        return map == null || map.size() == 0;
    }

    /**
     * 检查传入的Map是否非空
     *
     * @param map 要检查的Map
     * @return 如果Map非空，则返回true，否则返回false
     */
    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        // 如果Map非空，那么返回true，否则返回false
        return !isEmpty(map);
    }

    /**
     * 获取传入的Map的大小
     *
     * @param map 要获取大小的Map
     * @return 如果Map为null，那么返回0，否则返回Map的大小
     */
    public static <K, V> int size(Map<K, V> map) {
        // 如果Map为null，那么返回0，否则返回Map的大小
        if (map == null) {
            return 0;
        }
        return map.size();
    }

}

