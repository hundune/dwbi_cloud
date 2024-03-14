package com.dwbi.common.manager;

import com.dwbi.common.common.ErrorCode;
import com.dwbi.common.exception.BusinessException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @hundune~
 * @version1.0 专门提供RedisLimiter 限流基础服务的（提供了通用的能力，其他项目中也能用，与 Service 不同）
 */

@Service
public class RedisLimiterManager {
    @Resource
    private RedissonClient redissonClient;

    /**
     * 限流存在
     *
     * @param key 区分不同的限流其，比如不同的用户 id 应该分别统计
     */
    public void doRateLimiter(String key) {
        //创建一个 名称位 用户id 的限流其，每秒最多访问 2 次
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        //rate 每次生成的令牌数
        rateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);
        //每当一个操作来了之后，请求一个令牌,可以判断用户的 权限，比如 vip 每次可以访问多次，就将每次访问消耗的令牌数调低，普通用户将次数调高
        boolean canOp = rateLimiter.tryAcquire(1);
        if(!canOp){
            throw new BusinessException(ErrorCode.TO_MANY_REQUEST);
        }
    }
}
