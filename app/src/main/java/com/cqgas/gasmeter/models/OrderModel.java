package com.cqgas.gasmeter.models;

/**
 * Created by 国耀 on 2015/11/21.
 */
public class OrderModel {
    public static final int TYPE_LIST = 0;
    public static final int TYPE_UPDATA_START = 1;
    public static final int TYPE_UPDATA_END = 2;
    public static final int TYPE_FILE_INFO = 3;
    public static final int TYPE_FILE_DELETE = 4;
    public static final int TYPE_FILE_DOWNLOAD = 5;

    public int type;
    public String message;
    public String name;

}
