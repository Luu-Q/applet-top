package com.applet.service.member;

import com.applet.common.constant.MemberConstant;
import com.applet.model.member.TokenBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 存储多组hash 用户token
 */
@Service("tokenService")
public class TokenService {

    @Autowired
    private StringRedisTemplate stringTemplate;

    public TokenBean createToken(String memberId, String loginSource) {
        if (StringUtils.isBlank(loginSource)) return null;
        String token = UUID.randomUUID().toString() + "_" + memberId + "_" + loginSource;    // uuid + 用户id + 来源
        stringTemplate.opsForHash().put(MemberConstant.LOGIN_TOKEN_KEY_PREFIX + memberId, loginSource, token);  // key 来源
        stringTemplate.expire(MemberConstant.LOGIN_TOKEN_KEY_PREFIX + memberId, MemberConstant.TOKEN_EXPIRES_TIME, TimeUnit.SECONDS);
        return new TokenBean(token, memberId, System.currentTimeMillis() / 1000);
    }

    public TokenBean getToken(String memberId, String loginSource) {
        Object obj = stringTemplate.opsForHash().get(MemberConstant.LOGIN_TOKEN_KEY_PREFIX + memberId, loginSource);
        if (obj != null) {
            stringTemplate.expire(MemberConstant.LOGIN_TOKEN_KEY_PREFIX + memberId, MemberConstant.TOKEN_EXPIRES_TIME, TimeUnit.SECONDS);
            return new TokenBean(obj.toString(), memberId, System.currentTimeMillis() / 1000);
        } else {
            return createToken(memberId, loginSource);
        }
    }

    public boolean checkTokenValid(String token) {
        try {
            if (!token.contains("_")) return false;

            String memberId = token.split("_")[1];
            String loginSource = token.split("_")[2];

            if (stringTemplate.opsForHash().hasKey(MemberConstant.LOGIN_TOKEN_KEY_PREFIX + memberId, loginSource)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void tokenDelay(String token) {
        String memberId = token.split("_")[1];
        String loginSource = token.split("_")[2];

        stringTemplate.opsForHash().put(MemberConstant.LOGIN_TOKEN_KEY_PREFIX + memberId, loginSource, token);  // key 来源
        stringTemplate.expire(MemberConstant.LOGIN_TOKEN_KEY_PREFIX + memberId, MemberConstant.TOKEN_EXPIRES_TIME, TimeUnit.SECONDS);
    }

    public Long getMemberId(String token) {
        String memberId = token.split("_")[1];
        return Long.valueOf(memberId);
    }

}
