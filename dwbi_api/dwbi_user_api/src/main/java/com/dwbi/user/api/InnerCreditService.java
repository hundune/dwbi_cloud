package com.dwbi.user.api;

import com.dwbi.user.api.model.entity.User;


/**
 * 远程用户服务 user
 *
 */
/**
 * @author MA_dou
 * @description 针对表【credit(积分表)】的数据库操作Service
 * @createDate 2023-06-28 21:29:40
 */
public interface InnerCreditService{

    /**
     * 更新积分（内部方法） 正数为增加积分，负数为消耗积分
     * @param userId
     * @param credits
     * @return
     */
    Boolean updateCredits(Long userId,long credits);

}