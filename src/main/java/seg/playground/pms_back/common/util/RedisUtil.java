package seg.playground.pms_back.common.util;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

    private static RedisTemplate<String, String> redisTemplate;

    @SuppressWarnings("static-access")
    public RedisUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 데이터 저장
    public static void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 만료시간이 있는 데이터 저장
    public static void setWithExpiry(String key, String value, Long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    // 데이터 조회
    public static String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 데이터 삭제
    public static Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}