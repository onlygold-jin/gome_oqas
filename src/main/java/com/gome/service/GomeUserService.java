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
    // 注册账户
    ResultUtil insert(GomeUser gomeUser);
    // 登录
    GomeUser login(String username, String password);
}
