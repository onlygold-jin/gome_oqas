package com.gome.controller;

import com.gome.pojo.GomeUser;
import com.gome.service.GomeUserService;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.gome.constant.GomeConstant.*;
import static com.gome.enums.ResultEnums.*;

import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/3 15:33
 * @Modified By:
 */
@Controller
public class LoginController {
    @Autowired
    private GomeUserService service;

    @GetMapping({"/", "/login-ui"})
    public String loginUI() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultUtil login(String userName, String userPass, HttpSession session) {
        //1. 校验参数是否合法.
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPass)) {
            // 参数不合法.
            return ResultUtil.build(LOGIN_NULL.getStatus(), LOGIN_NULL.getMsg());
        }
        //2. 调用service执行登录.
        GomeUser gomeUser = service.login(userName, userPass);
        //3. 根据service返回结果判断登录是否成功.
        if (gomeUser != null) {
            //4. 如果成功,讲用户的信息放到session域中.
            session.setAttribute(USER, gomeUser);
            return ResultUtil.ok();
        } else {
            //5. 如果失败,响应错误信息给ajax引擎.
            return ResultUtil.build(LOGIN_ERROR.getStatus(), LOGIN_ERROR.getMsg());
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public ResultUtil register(GomeUser gomeUser) {
        return service.insert(gomeUser);
    }
}
