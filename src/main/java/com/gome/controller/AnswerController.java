package com.gome.controller;

import com.gome.constant.GomeConstant;
import com.gome.dto.QuestionDTO;
import com.gome.pojo.GomeUser;
import com.gome.pojo.QaQuestionItems;
import com.gome.pojo.QaQuestionList;
import com.gome.pojo.QaQuestionReply;
import com.gome.service.*;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.util.*;

import static com.gome.constant.GomeConstant.*;
import static com.gome.enums.ResultEnums.*;

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
    @Autowired
    private QaQuestionReplyService questionReplyService;
    @Autowired
    private QaScoresRecordService scoresRecordService;

    @GetMapping("/answer")
    public String answer(Integer thisNumber, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        GomeUser gomeUser = (GomeUser) session.getAttribute(GomeConstant.USER);
        //1.如果之前已经有选中的题
        Integer integer = countItemsService.selectThisNumber(gomeUser.getUserName(),"2");
        if (integer != null) {
            thisNumber = integer;
        } else {
            // 本套题已经被当前登陆人选中
            boolean b = countItemsService.updateThisNumber(thisNumber, gomeUser.getUserName(),"2");
            if (!b) {
                // 选题卡页面
                return TOPIC;
            }
        }

        // 2.获取本套题所有的选择题
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
    public ResultUtil ajax(HttpServletRequest request) {
        // 1. 获取当前用户
        HttpSession session = request.getSession();
        GomeUser gomeUser = (GomeUser) session.getAttribute(GomeConstant.USER);
        // 2. 拿取form表单选择的答案
        Map<String, String[]> map = request.getParameterMap();
        String[] questionIds = map.get("questionId");
        // 4.将form表单传入的值放到集合中
        List<QaQuestionReply> list = new ArrayList<>();
        for (String questionId : questionIds) {
            QaQuestionReply questionReply = new QaQuestionReply();
            // 3.判断是否是 复选框
            boolean b = questionListService.ifCheckbox(Integer.valueOf(questionId));
            if (b) {
                // 3.1 是复选框 查询复选框的内容 并用 ；分割
                String[] strings = map.get("checkbox" + questionId);
                String checkboxs = "";
                if (strings.length != 0) {
                    for (String string : strings) {
                        checkboxs = checkboxs + string + ";";
                    }
                }
                System.out.println("checkbox" + questionId + "=" + checkboxs);
                questionReply.setRespoondent(gomeUser.getUserName());
                questionReply.setQuestionId(Integer.valueOf(questionId));
                questionReply.setAnswer(checkboxs);
                list.add(questionReply);
            } else {
                // 3.1 是单选框 查询单选框
                String radio = request.getParameter("radio" + questionId);
                System.out.println("radio" + questionId + "=" + radio);
                questionReply.setRespoondent(gomeUser.getUserName());
                questionReply.setQuestionId(Integer.valueOf(questionId));
                questionReply.setAnswer(radio);
                list.add(questionReply);
            }
        }
        // 5.存入数据库
        ResultUtil resultUtil = questionReplyService.insertQaQuestionReply(list);
        // 6. 如果答题成功 ，将本套题的状态改为是
        if (resultUtil.getStatus() == 200) {
            Boolean b = countItemsService.updateIsEnable(gomeUser.getUserName(),"2");
            if (!b) {
                return ResultUtil.build(ANSWER_UPDATE_USERNAME_ERROR.getStatus(), ANSWER_UPDATE_USERNAME_ERROR.getMsg());
            }
        }
        // 7. 计算分数 通过list
        ResultUtil result = questionListService.calculateScore(list);
        // 8. 保存分数
        Boolean b = scoresRecordService.saveScore((Double) result.getData(), gomeUser.getUserName(), "1");
        if (!b) {
            return ResultUtil.build(SAVE_SCORE_ERROR.getStatus(), SAVE_SCORE_ERROR.getMsg());
        }
        return result;
    }
}
