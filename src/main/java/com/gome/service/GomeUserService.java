package com.gome.service;

import com.gome.pojo.GomeUser;
import com.gome.util.ResultUtil;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/3 19:53
 * @Modified By:
 */
public interface GomeUserService {
    // 找回密码
    ResultUtil updateUser(GomeUser gomeUser);
    // 登录
    GomeUser login(String username, String password);
    // 通过选手号，查询用户
    GomeUser selectAll(Integer competitionOrder);
}
