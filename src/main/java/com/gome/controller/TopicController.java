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
    public String toTopic(@RequestParam Integer userSortnum, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        GomeUser gomeUser = (GomeUser) session.getAttribute(GomeConstant.USER);
        if (userSortnum != -1) {
            // 3. 通过号查询用户，如果用户和登录用户相等
            if (gomeUser.getCompetitionOrder().equals(userSortnum)) {
                model.addAttribute("disable", "否");
            }else{
                model.addAttribute("disable", "是");
            }
            // 1.查询当前用户是否有选中题
            Integer integer = qaCountItemsService.selectThisNumber(gomeUser.getUserName(),"2");
            if (integer != null) {
                // 第二环节 答题页
                return "redirect:/" + ANSWER;
            }
            // 2.没有选中的题
            List<QaCountItems> countList = qaCountItemsService.getCountList("2");
            model.addAttribute("list", countList);
        } else {
            // 查询所有的题
            List<QaCountItems> countList = qaCountItemsService.getCountList("2");
            model.addAttribute("list", countList);
            model.addAttribute("disable", "是");
        }
        return TOPIC;
    }
}
