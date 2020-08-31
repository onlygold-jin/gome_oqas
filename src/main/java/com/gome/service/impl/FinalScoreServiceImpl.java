package com.gome.service.impl;

import com.gome.mapper.FinalScoreMapper;
import com.gome.pojo.FinalScore;
import com.gome.pojo.FinalScoreExample;
import com.gome.service.FinalScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/31 2:03
 * @Modified By:
 */
@Service
public class FinalScoreServiceImpl implements FinalScoreService {
    @Autowired(required = false)
    private FinalScoreMapper finalScoreMapper;

    @Override
    public Boolean saveScore(FinalScore finalScore) {
        if(finalScoreMapper.insertSelective(finalScore)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<FinalScore> findByAll() {

        FinalScoreExample example = new FinalScoreExample();
        FinalScoreExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("COMPETITION_ORDER ASC");

        return finalScoreMapper.selectByExample(example);
    }
}
