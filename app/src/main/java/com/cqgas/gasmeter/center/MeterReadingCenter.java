package com.cqgas.gasmeter.center;

import com.cqgas.gasmeter.core.UserMeter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class MeterReadingCenter {

    public static List<UserMeter> getDataForUi(){
        // 测试数据
        List<UserMeter> result = new ArrayList<>();
        UserMeter meter = new UserMeter();
        meter.setName("袁心伦");
        meter.setAddress("南华路瓦窑三社预制板厂");
        meter.setNumber("01030001-1");
        meter.setLastMonthQuantity(666f);
        meter.setQuantity(-1.0f);
        result.add(meter);

        meter = new UserMeter();
        meter.setName("唐登华");
        meter.setAddress("南华路瓦窑三社农村");
        meter.setNumber("01030002-1");
        meter.setLastMonthQuantity(411f);
        meter.setQuantity(500f);
        result.add(meter);

        meter = new UserMeter();
        meter.setName("杨世坤");
        meter.setAddress("南华路瓦窑三社农村");
        meter.setNumber("01030003-1");
        meter.setLastMonthQuantity(90f);
        meter.setQuantity(-1.0f);
        result.add(meter);
        return result;
    }
}
