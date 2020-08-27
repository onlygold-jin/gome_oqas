package com.gome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.gome.constant.GomeConstant.*;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/18 13:28
 * @Modified By:
 */
@Controller
public class ScoreController {

    /**
     * 计算得分，并将
     * @return
     */

    @GetMapping("/score")
    public String toScore() {


        return SCORE;
    }


}
