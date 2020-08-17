package com.gome.controller;

import com.gome.constant.GomeConstant;
import com.gome.pojo.GomeUser;
import com.gome.pojo.QaCountItems;
import com.gome.service.QaCountItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.gome.constant.GomeConstant.*;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/17 0:02
 * @Modified By:
 */
@Controller
public class TopicController {
    @Autowired
    private QaCountItemsService qaCountItemsService;

    @GetMapping("/topic")
    public String toTopic(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        GomeUser gomeUser = (GomeUser) session.getAttribute(GomeConstant.USER);
        // 1.查询当前用户是否有选中题
        Integer integer = qaCountItemsService.selectThisNumber(gomeUser.getUserName());
        if (integer != null) {
            // 第二环节 答题页
            return "redirect:/"+ANSWER;
        }
        // 2.没有选中的题
        List<QaCountItems> countList = qaCountItemsService.getCountList();
        model.addAttribute("list", countList);
        return TOPIC;
    }
}
