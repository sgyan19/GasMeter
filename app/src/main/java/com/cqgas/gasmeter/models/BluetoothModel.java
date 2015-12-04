package com.cqgas.gasmeter.models;

/**
 * Created by sinash94857 on 2015/12/4.
 */
public class BluetoothModel {
    public String rqb_gh;  // 表钢号 @NoNull
    public String mm;      // 密码 @NoNull
    public String cbjl_id;// 抄表记录ID @NoNull
    public String cbjl_yqdz_ms;    // 用气地址描述 @NoNull
    public String rqb_rqblx;       // 燃气表类型   @Nullable
    public String cbjl_hz_mc;      // 户主名称     @NoNull
    public String cbjl_scbd;       // 上次表底     @NoNull
    public String cbjl_sccbrq;     // 上次抄表日期 @NoNull
    public String cbjl_pingjun_yql;// 平均用气量 @NoNull
    public String jzq_bh;          // 集中器编号    @NoNull
    public String cbjl_yqzh;       // 用户号      @NoNull //（只有明光的需要，其他厂家的数据是没有该属性的），接口开发中需要注意。)
}
