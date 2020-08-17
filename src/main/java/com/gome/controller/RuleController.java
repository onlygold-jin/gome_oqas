package com.gome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.gome.constant.GomeConstant.*;

/**
 * @Description: 规则页面
 * @Author: WangJinYue
 * @Date: 2020/8/17 2:22
 * @Modified By:
 */
@Controller
public class RuleController {
    @GetMapping("/rule")
    public String toRule() {
        return RULE;
    }
}
