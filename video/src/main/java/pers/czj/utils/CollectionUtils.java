package pers.czj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 创建在 2020/7/17 17:14
 * 集合工具类
 */
public class CollectionUtils {

    private static final Logger log = LoggerFactory.getLogger(CollectionUtils.class);

    /**
     * @param [collection, start, length]
     * @return java.util.List<E>
     * @author czj
     * 分割集合
     * @date 2020/7/17 17:19
     */
    public static <E extends Number> List<E> slicer(Collection<E> collection, int start, int length) {
        Iterator<E> iterator = collection.iterator();
        List<E> list = new ArrayList<>();
        int len = 0;
        while (iterator.hasNext()) {
            if (len++ >= start) {
                if (len != length)
                    list.add(iterator.next());
                else {
                    break;
                }
            } else {
                iterator.next();
            }
        }
        return list;
    }

    public static List<Long> speicalSlicer(Collection<ZSetOperations.TypedTuple> collection, int start, int length) {
        Iterator<ZSetOperations.TypedTuple> iterator = collection.iterator();
        List<Long> list = new ArrayList<>();
        int len = 0;
        while (iterator.hasNext()) {
            if (len++ >= start) {
                if (len != length)
                    list.add(Long.valueOf(iterator.next().getValue().toString()));
                else {
                    break;
                }
            } else {
                iterator.next();
            }
        }
        return list;
    }

}
