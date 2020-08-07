package com.gome.service;

import com.gome.pojo.QaQuestionList;

import java.util.List;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/5 18:06
 * @Modified By:
 */
public interface QaQuestionListService {
    // 查询n套题的所有题，并将此套提放入登录的用户中
    List<QaQuestionList> getQuestionList(Integer thisNumber);

    // 判断是否是复选框
    boolean ifCheckbox(Integer id);
}
