package com.gome.service.impl;

import com.gome.mapper.QaCountItemsMapper;
import com.gome.mapper.QaQuestionListMapper;
import com.gome.pojo.QaCountItemsExample;
import com.gome.pojo.QaQuestionList;
import com.gome.pojo.QaQuestionListExample;
import com.gome.service.QaQuestionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/5 18:10
 * @Modified By:
 */
@Service
public class QaQuestionListServiceImpl implements QaQuestionListService {
    @Autowired
    private QaQuestionListMapper questionListMapper;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public List<QaQuestionList> getQuestionList(Integer thisNumber) {
        // 1.查询n套题的所有题目
        QaQuestionListExample example = new QaQuestionListExample();
        QaQuestionListExample.Criteria criteria = example.createCriteria();
        criteria.andThisNumberEqualTo(thisNumber);
        // 不选中 简答题
        criteria.andQuestionTypeNotEqualTo("3");
        List<QaQuestionList> qaQuestionLists = questionListMapper.selectByExample(example);
        return qaQuestionLists;
    }

    /**
     * 判断是否是复选框
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean ifCheckbox(Integer id) {
        QaQuestionList qaQuestionList = questionListMapper.selectByPrimaryKey(id);
        if (qaQuestionList.getQuestionType().equals("2")) {
            return true;
        }
        return false;
    }
}
