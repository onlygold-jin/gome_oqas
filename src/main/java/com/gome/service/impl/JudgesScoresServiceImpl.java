package com.gome.service.impl;

import com.gome.mapper.JudgesScoresMapper;
import com.gome.service.JudgesScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/19 4:00
 * @Modified By:
 */
@Service
public class JudgesScoresServiceImpl implements JudgesScoresService {
    @Autowired
    private JudgesScoresMapper judgesScoresMapper;
}
