package com.gome;

import com.gome.util.ArrayUtil;

import java.util.Arrays;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/13 23:32
 * @Modified By:
 */
public class Main {

    public static void main(String[] args) {
        String[] str1 =new String[]{"1","2","3","4"};
        String[] str2 =new String[]{"4","3","1","5"};

        System.out.println(ArrayUtil.strayEqual(str1,str2));
    }
}
