package com.cqgas.gasmeter.center;

import com.cqgas.gasmeter.core.QueryCore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 国耀 on 2015/11/29.
 */
public class QueryMeterCenter {
    public static final int QUERY_TYPE_COMPANY = 0;
    public static final int QUERY_TYPE_PLACE = 1;

    public static QueryCore getUiQuery(int type,long beginDate,long endDate){
        return new QueryCore();
    }
}
