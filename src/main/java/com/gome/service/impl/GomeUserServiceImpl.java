package com.gome.service.impl;

import com.gome.mapper.GomeUserMapper;
import com.gome.pojo.GomeUser;
import com.gome.pojo.GomeUserExample;
import com.gome.service.GomeUserService;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import static com.gome.enums.ResultEnums.*;

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
    public GomeUser login(String username, String password) {
        GomeUserExample example = new GomeUserExample();
        GomeUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(username);
        criteria.andUserPassEqualTo(password);
        List<GomeUser> list = mapper.selectByExample(example);
        if (list.size() != 0) {
            for (GomeUser gomeUser : list) {
                return gomeUser;
            }
        }
        return null;
    }
}
