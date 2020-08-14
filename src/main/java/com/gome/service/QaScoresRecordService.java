package com.gome.service;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/13 19:08
 * @Modified By:
 */
public interface QaScoresRecordService {
    // 保存分数
    Boolean saveScore(double score, String userName, String stage);
}
