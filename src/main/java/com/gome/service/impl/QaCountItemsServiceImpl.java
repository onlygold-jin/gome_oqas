package com.gome.service.impl;

import com.gome.mapper.QaCountItemsMapper;
import com.gome.pojo.QaCountItems;
import com.gome.pojo.QaCountItemsExample;
import com.gome.service.QaCountItemsService;
import com.gome.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.gome.enums.ResultEnums.*;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/5 17:10
 * @Modified By:
 */
@Service
public class QaCountItemsServiceImpl implements QaCountItemsService {
    @Autowired
    private QaCountItemsMapper qaCountItemsMapper;

    @Override
//    @Scheduled(cron = "0/10 * * * * ?")
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public List<QaCountItems> getCountList() {
        System.err.println(new Date());
        QaCountItemsExample example = new QaCountItemsExample();
        QaCountItemsExample.Criteria criteria = example.createCriteria();
        criteria.andIsEnableLike("否");
        criteria.andRespondentIsNull();
        return qaCountItemsMapper.selectByExample(example);
    }

    /**
     * 修改n套题的 答题人
     *
     * @param thisNumber
     * @param userName
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean updateThisNumber(Integer thisNumber, String userName) {
        QaCountItemsExample example = new QaCountItemsExample();
        QaCountItemsExample.Criteria criteria = example.createCriteria();
        criteria.andThisNumberEqualTo(thisNumber);
        List<QaCountItems> list = qaCountItemsMapper.selectByExample(example);
        if (!list.isEmpty()) {
            for (QaCountItems countItems : list) {
                countItems.setRespondent(userName);
                int i = qaCountItemsMapper.updateByPrimaryKey(countItems);
                if (i == 1) {
                    System.out.println(i + "修改成功");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 查询当前用户是否有选中的题
     *
     * @param userName
     * @return 返回选中的题号
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Integer selectThisNumber(String userName) {
        QaCountItemsExample example = new QaCountItemsExample();
        QaCountItemsExample.Criteria criteria = example.createCriteria();
        criteria.andRespondentEqualTo(userName);
        List<QaCountItems> list = qaCountItemsMapper.selectByExample(example);
        if (list.size() != 0) {
            for (QaCountItems qaCountItems : list) {
                return qaCountItems.getThisNumber();
            }
        }
        return null;
    }

    /**
     * 修改本套题的状态
     *
     * @param userName
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Boolean updateIsEnable(String userName) {
        QaCountItemsExample example = new QaCountItemsExample();
        QaCountItemsExample.Criteria criteria = example.createCriteria();
        criteria.andRespondentEqualTo(userName);
        List<QaCountItems> list = qaCountItemsMapper.selectByExample(example);
        if (list.size() != 0) {
            for (QaCountItems qaCountItems : list) {
                qaCountItems.setIsEnable("是");
                int i = qaCountItemsMapper.updateByPrimaryKey(qaCountItems);
                if (i != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 选中题的开始时间是否为空
     *
     * @param userName
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResultUtil getStartTimeTONull(String userName, Date date) {
        QaCountItemsExample example = new QaCountItemsExample();
        QaCountItemsExample.Criteria criteria = example.createCriteria();
        criteria.andRespondentEqualTo(userName);
        criteria.andStartTimeIsNull();
        // 1.查询当前用户选中的题，的开始时间是否为空
        List<QaCountItems> list = qaCountItemsMapper.selectByExample(example);
        if (!list.isEmpty()) {
            for (QaCountItems countItems : list) {
                // 1.1 如果为空则填如当前传过来的时间
                countItems.setStartTime(date);
                qaCountItemsMapper.updateByPrimaryKey(countItems);
                return ResultUtil.ok(date);
            }
        } else {
            // 1.2 如果不为空则返回空中的时间
            QaCountItemsExample example1 = new QaCountItemsExample();
            QaCountItemsExample.Criteria criteria1 = example.createCriteria();
            criteria.andRespondentEqualTo(userName);
            List<QaCountItems> list1 = qaCountItemsMapper.selectByExample(example1);
            if (!list1.isEmpty()) {
                for (QaCountItems qaCountItems : list1) {
                    return ResultUtil.ok(qaCountItems.getStartTime());
                }
            }
        }
        return ResultUtil.build(START_TIME_ERROR.getStatus(), START_TIME_ERROR.getMsg());
    }
}
