package com.gome.controller;

import com.gome.pojo.GomeUser;
import com.gome.service.GomeUserService;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/3 15:33
 * @Modified By:
 */
@Controller
public class IndexController {
    @Autowired
    private GomeUserService service;

    @GetMapping("/login")
    public String index() {
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, GomeUser gomeUser) {
        ResultUtil result = service.login(gomeUser);
        model.addAttribute("result", result);
        if (result.getStatus() == 200) {
            return "index";
        }
        return "login";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResultUtil register(GomeUser gomeUser) {
        return service.insert(gomeUser);
    }
}
