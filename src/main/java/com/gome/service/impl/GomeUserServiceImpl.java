package com.gome.service.impl;

import com.gome.mapper.GomeUserMapper;
import com.gome.pojo.GomeUser;
import com.gome.pojo.GomeUserExample;
import com.gome.service.GomeUserService;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gome.enums.ResultEnums.LOGIN_ERROR;
import static com.gome.enums.ResultEnums.REGISTER_ERROR;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/3 19:56
 * @Modified By:
 */
@Service
public class GomeUserServiceImpl implements GomeUserService {
    @Autowired
    private GomeUserMapper mapper;

    @Override
    public ResultUtil insert(GomeUser gomeUser) {
        int i = mapper.insertSelective(gomeUser);
        if (i != 1) {
            return ResultUtil.build(REGISTER_ERROR.getStatus(), REGISTER_ERROR.getMsg());
        }
        return ResultUtil.ok();
    }

    @Override
    public ResultUtil login(GomeUser gomeUser) {
        GomeUserExample example = new GomeUserExample();
        GomeUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(gomeUser.getUserName());
        criteria.andUserPassEqualTo(gomeUser.getUserPass());
        List<GomeUser> list = mapper.selectByExample(example);
        if (list.size() != 0) {
            for (GomeUser user : list) {
                System.out.println(user.getUserName());
                return ResultUtil.ok(user.getUserName());
            }
        }
        return ResultUtil.build(LOGIN_ERROR.getStatus(), LOGIN_ERROR.getMsg());
    }
}
