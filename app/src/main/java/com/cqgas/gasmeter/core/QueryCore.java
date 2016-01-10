package com.cqgas.gasmeter.core;

import java.util.List;

/**
 * Created by 国耀 on 2015/12/5.
 */
public class QueryCore {
    public int userCount = 0;       // 抄表户数
    public int readUserCount = 0;   // 已抄户数
    public int unReadUserCount = 0; // 未抄户数
    public int readData = 0;        // 抄表总数

    public List<MeterCore> list;

    public String getUiUserCount(){
        return String.format("     用户总数：%d", userCount);
    }

    public String getUiReadUserCount(){
        return String.format("     已抄户数：%d",readUserCount);
    }

    public String getUiUnReadUserCount(){
        return String.format("     未抄户数：%d",unReadUserCount);
    }

    public String getUiReadData(){
        return String.format("     抄表总数：%d",readData);
    }
}
