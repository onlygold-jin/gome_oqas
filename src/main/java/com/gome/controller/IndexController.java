package com.gome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 首页
 * @Author: WangJinYue
 * @Date: 2020/8/4 19:31
 * @Modified By:
 */
@Controller
public class IndexController {
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }
}
