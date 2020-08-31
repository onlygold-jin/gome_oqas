package com.gome.controller;

import com.gome.pojo.GomeQaRule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArgueController {



    @GetMapping("/argue")
    public String toArgue(){

        return "argue";
    }
}
