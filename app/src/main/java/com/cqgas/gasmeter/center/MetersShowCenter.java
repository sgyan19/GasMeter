package com.cqgas.gasmeter.center;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 国耀 on 2015/11/29.
 */
public class MetersShowCenter {
    public static List<String> getUiMeterDetails(){
        // 测试数据
        List<String> result = new ArrayList<>();
        result.add("用户总数：");
        result.add("已抄户数：");
        result.add("未抄户数：");
        result.add("抄表总量：");
        result.add("抄表进度：");
        result.add("日均抄户：");
        result.add("时均抄户：");
        result.add("重抄户数：");
        result.add("重抄概率：");
        result.add("估录次数：");
        result.add("定位次数：");
        result.add("电筒次数：");
        return result;
    }
}
