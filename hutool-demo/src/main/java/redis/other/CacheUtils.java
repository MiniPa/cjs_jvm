package redis.other;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

/**
 * 通用缓存工具类
 *
 */
public class CacheUtils {
    private static RedisTemplate<String, Object> redisTemplate;

    public CacheUtils() {

    }

    public CacheUtils(RedisTemplate<String, Object> redisTemplate) {
        CacheUtils.redisTemplate = redisTemplate;
    }

    /**
     * 删除缓存<br>
     * 根据key精确匹配删除
     * @param key
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key){
        if(key!=null && key.length > 0){
            if(key.length == 1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 批量删除<br>
     * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
     * @param pattern
     */
    public static void batchDel(String... pattern){
        for (String kp : pattern) {
            redisTemplate.delete(redisTemplate.keys(kp + "*"));
        }
    }

    /**
     * 获取缓存<br>
     * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
     * @param key
     * @return
     */
    public static Object getObj(String key){
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存<br>
     * 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值
     * @param key
     * @param retain    是否保留
     * @return
     */
    public static Object getObj(String key, boolean retain){
        Object obj = redisTemplate.boundValueOps(key).get();
        if(!retain){
            redisTemplate.delete(key);
        }
        return obj;
    }

    /**
     * 获取缓存<br>
     * 注：该方法暂不支持Character数据类型
     * @param key   key
     * @param clazz 类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        return (T)redisTemplate.boundValueOps(key).get();
    }

    /**
     * 将value对象写入缓存
     * @param key
     * @param value
     * @param time 失效时间(秒)
     */
    public static void set(String key,Object value){
        redisTemplate.opsForValue().set(key, value);
//        if(time > 0){
//        	redisTemplate.expire(key, time, TimeUnit.SECONDS);
//        }
    }

    /**
     * 递减操作
     * @param key
     * @param by
     * @return
     */
    public static double decr(String key, double by){
        return redisTemplate.opsForValue().increment(key, -by);
    }

    /**
     * 递增操作
     * @param key
     * @param by
     * @return
     */
    public static double incr(String key, double by){
        return redisTemplate.opsForValue().increment(key, by);
    }

    /**
     * 将map写入缓存
     * @param key
     * @param map
     */
    public static <T> void setMap(String key, Map<String, T> map){
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向key对应的map中添加缓存对象
     * @param key
     * @param map
     */
    public static <T> void addMap(String key, Map<String, T> map){
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向key对应的map中添加缓存对象
     * @param key   cache对象key
     * @param field map对应的key
     * @param value     值
     */
    public static void addMap(String key, String field, String value){
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 向key对应的map中添加缓存对象
     * @param key   cache对象key
     * @param field map对应的key
     * @param obj   对象
     */
    public static <T> void addMap(String key, String field, T obj){
        redisTemplate.opsForHash().put(key, field, obj);
    }

    /**
     * 获取map缓存
     * @param key
     * @param clazz
     * @return
     */
    public static <T> Map<String, T> mget(String key, Class<T> clazz){
        BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);
        return boundHashOperations.entries();
    }

    /**
     * 获取map缓存中的某个对象
     * @param key
     * @param field
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMapField(String key, String field, Class<T> clazz){
        return (T)redisTemplate.boundHashOps(key).get(field);
    }

    /**
     * 删除map中的某个对象
     * @param key   map对应的key
     * @param field map中该对象的key
     */
    public static void delMapField(String key, String... field){
        BoundHashOperations<String, String, ?> boundHashOperations = redisTemplate.boundHashOps(key);
        boundHashOperations.delete(field);
    }

    /**
     * 指定缓存的失效时间
     *
     * @param key 缓存KEY
     * @param time 失效时间(秒)
     */
    public static void expire(String key, Integer time) {
        if(time > 0){
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 添加set
     * @param key
     * @param value
     */
    public static void sadd(String key, String... value) {
        redisTemplate.boundSetOps(key).add(value);
    }

    /**
     * 删除set集合中的对象
     * @param key
     * @param value
     */
    public static void srem(String key, String... value) {
        redisTemplate.boundSetOps(key).remove(value);
    }

    /**
     * set重命名
     * @param oldkey
     * @param newkey
     */
    public static void srename(String oldkey, String newkey){
        redisTemplate.boundSetOps(oldkey).rename(newkey);
    }

    /**
     * 模糊查询keys
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除list中的value
     * @param
     * @return
     */
    public static void removeListForValue(String key, long count ,Object obj){
        redisTemplate.opsForList().remove(key,count,obj);
    }

    /**
     * List右插入数据
     * @param
     * @return
     */
    public static void rightPush(String key,Object obj){
        redisTemplate.opsForList().rightPush(key, obj);
    }

    /**
     * List左插入数据
     * @param
     * @return
     */
    public static void leftPush(String key,Object obj){
        redisTemplate.opsForList().leftPush(key, obj);
    }

    /**
     * List右取数据
     * @param
     * @return
     */
    public static Object rightPop(String key){
        Object obj = redisTemplate.opsForList().rightPop(key);
        return obj;
    }

    /**
     * 判断redis中的值是否存在
     * @param
     * @return
     */
    public static Boolean existVlaueForList(String key,int value){
        List<Object> range = redisTemplate.opsForList().range(key, 0, -1);
        if(range.contains(value)){
            return false;
        } else {
            return true;
        }

    }

    /**
     * 返回redis中list
     * */
    public static List<Object> returnList(String key){
        return redisTemplate.opsForList().range(key,0,-1);
    }

}