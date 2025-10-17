package com.hihonor.adsdk.demo.external.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * 集合工具类
 */
public class CollectionUtils {

    private static final String TAG = "CollectionUtils";

    /**
     * 检查集合是否为空.
     *
     * @param coll 要检查的集合
     * @return 如果集合为null或为空，则返回true；否则返回false
     */
    public static <E> boolean isEmpty(Collection<E> coll) {
        return coll == null || coll.size() == 0;
    }

    /**
     * 检查集合是否不为空.
     *
     * @param coll 要检查的集合
     * @return 如果集合不为null且不为空，则返回true；否则返回false
     */
    public static <E> boolean isNotEmpty(Collection<E> coll) {
        return !isEmpty(coll);
    }

    /**
     * 返回对象的大小或元素个数，支持 Map、Collection、数组、迭代器和枚举类型的对象.
     *
     * @param object 要获取大小的对象
     * @return 对象的大小或元素个数，如果对象为null则返回0
     */
    public static int size(Object object) {
        if (object == null) {
            return 0;
        }
        int total = 0;
        if (object instanceof Map) {
            total = ((Map) object).size();
        } else if (object instanceof Collection) {
            total = ((Collection) object).size();
        } else if (object instanceof Object[]) {
            total = ((Object[]) object).length;
        } else if (object instanceof Iterator) {
            Iterator it = (Iterator) object;
            while (it.hasNext()) {
                total++;
                it.next();
            }
        } else if (object instanceof Enumeration) {
            Enumeration it = (Enumeration) object;
            while (it.hasMoreElements()) {
                total++;
                it.nextElement();
            }
        } else {
            try {
                total = Array.getLength(object);
            } catch (IllegalArgumentException ex) {
                LogUtil.info(TAG,
                    "size, Object type does not support getting size, IllegalArgumentException: " + ex.getMessage());
            }
        }
        return total;
    }

}

