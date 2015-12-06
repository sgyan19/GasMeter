package com.cqgas.gasmeter.core;

/**
 * Created by 国耀 on 2015/12/5.
 */
public class QueryCore {
    public int userCount = 0;       // 抄表户数
    public int readUserCount = 0;   // 已抄户数
    public int unReadUserCount = 0; // 未抄户数
    public int readData = 0;        // 抄表总数
    public float readRate = 0;        // 抄表进度
    public int dayAverageUserCount = 0;  //日均户数;
    public int hourAverageUserCount = 0;   //时均户数
    public int reReadCount = 0;         // 重抄户数
    public float reReadProbability = 0;   // 重抄概率
    public int estimateReadTimes = 0;   // 估录次数
    public int locationTimes = 0;   // 定位次数
    public int torchTimes = 0;      // 电筒次数

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

    public String getUiReadRate(){
        return String.format("     抄表进度：%.2f",readRate%100);
    }

    public String getUiDayAverageUserCount(){
        return String.format("     日均户数：%d",dayAverageUserCount);
    }

    public String getUiHourAverageUserCount(){
        return String.format("     时均户数：%d",hourAverageUserCount);
    }

    public String getUiReReadCount(){
        return String.format("     重抄户数：%d",reReadCount);
    }

    public String getUiReReadProbability(){
        return String.format("     重抄概率：%.2f",reReadProbability);
    }

    public String getUiEstimateReadTimes(){
        return String.format("     估录次数：%d",estimateReadTimes);
    }

    public String getUiTorchTimes(){
        return String.format("     电筒次数：%d",torchTimes);
    }

    public String getUiLocationTimes(){
        return String.format("     定位次数：%d",locationTimes);
    }
}
