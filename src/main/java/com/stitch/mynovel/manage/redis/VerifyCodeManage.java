package com.stitch.mynovel.manage.redis;

import com.stitch.mynovel.core.common.util.ImgVerifyCodeUtils;
import com.stitch.mynovel.core.constant.CacheConsts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class VerifyCodeManage {

    private final StringRedisTemplate redisTemplate;

    /**
     * 生成验证码
     */
    public String generateVerifyCode(String sessonId) throws IOException {
        String code = ImgVerifyCodeUtils.getRandomVerifyCode(4);
        String img = ImgVerifyCodeUtils.genVerifyCodeImg(code);
        redisTemplate.opsForValue()
                .set(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessonId, code, Duration.ofMinutes(5));
        return img;
    }

    /**
     * 校验验证码
     */
    public boolean checkVerifyCode(String sessonId, String code) {
        String cacheCode = redisTemplate.opsForValue().get(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessonId);
        return cacheCode != null && cacheCode.equals(code);
    }

    /**
     * 删除验证码
     */
    public void deleteVerifyCode(String sessonId) {
        redisTemplate.delete(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessonId);
    }
}
