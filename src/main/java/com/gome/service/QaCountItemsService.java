package com.gome.service;

import com.gome.pojo.QaCountItems;

import java.util.List;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/5 17:09
 * @Modified By:
 */
public interface QaCountItemsService {
    // 查询所有为答的题
    List<QaCountItems> getCountList();

    // 添加套题用户
    boolean updateThisNumber(Integer thisNumber, String userName);

    // 查看当前用户是否有选中的题
    Integer selectThisNumber(String userName);
}
