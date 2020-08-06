package com.gome.controller;

import com.gome.constant.GomeConstant;
import com.gome.dto.QuestionDTO;
import com.gome.pojo.GomeUser;
import com.gome.pojo.QaQuestionItems;
import com.gome.pojo.QaQuestionList;
import com.gome.service.QaCountItemsService;
import com.gome.service.QaQuestionItemsService;
import com.gome.service.QaQuestionListService;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static com.gome.constant.GomeConstant.*;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/5 18:04
 * @Modified By:
 */
@Controller
public class AnswerController {
    @Autowired
    private QaCountItemsService countItemsService;
    @Autowired
    private QaQuestionListService questionListService;
    @Autowired
    private QaQuestionItemsService questionItemsService;

    @GetMapping("/answer")
    public String answer(Integer thisNumber, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        GomeUser gomeUser = (GomeUser) session.getAttribute(GomeConstant.USER);
        //1.如果之前已经有选中的题
        Integer integer = countItemsService.selectThisNumber(gomeUser.getUserName());
        if (integer != null) {
            thisNumber = integer;
        } else {
            // 本套题已经被当前登陆人选中
            boolean b = countItemsService.updateThisNumber(thisNumber, gomeUser.getUserName());
            if (!b) {
                return INDEX;
            }
        }
        // 2.获取本套题所有的题
        List<QaQuestionList> questionList = questionListService.getQuestionList(thisNumber);
        List<QuestionDTO> list = new ArrayList<>();
        for (QaQuestionList question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setAnswer(question.getAnswer());
            questionDTO.setQuestionTitle(question.getQuestionTitle());
            questionDTO.setQuestionType(question.getQuestionType());
            questionDTO.setThisNumber(question.getThisNumber());
            // 3.查询 abcd
            List<QaQuestionItems> questionItemsList = questionItemsService.getQuestionItemsList(question.getId());
            questionDTO.setItem(questionItemsList);
            list.add(questionDTO);
        }
        model.addAttribute("list", list);
        return ANSWER;
    }

    @PostMapping("/answer")
    @ResponseBody
    public ResultUtil ajax() {
        return null;
    }
}
