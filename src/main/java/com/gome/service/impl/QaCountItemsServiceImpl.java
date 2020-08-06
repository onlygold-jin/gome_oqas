package com.gome.service.impl;

import com.gome.mapper.QaCountItemsMapper;
import com.gome.pojo.QaCountItems;
import com.gome.pojo.QaCountItemsExample;
import com.gome.service.QaCountItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
                    System.out.println(i+"修改成功");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 查询当前用户是否有选中的题
     * @param userName
     * @return 返回选中的题号
     */
    @Override
    public Integer selectThisNumber(String userName) {
        QaCountItemsExample example = new QaCountItemsExample();
        QaCountItemsExample.Criteria criteria = example.createCriteria();
        criteria.andRespondentEqualTo(userName);
        List<QaCountItems> list = qaCountItemsMapper.selectByExample(example);
        if (list.size()!=0){
            for (QaCountItems qaCountItems : list) {
                return qaCountItems.getThisNumber();
            }
        }
        return null;
    }
}