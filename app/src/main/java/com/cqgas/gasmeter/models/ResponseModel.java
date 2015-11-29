package com.cqgas.gasmeter.models;

/**
 * Created by 国耀 on 2015/11/21.
 */
public class ResponseModel {
    public int type; // 0 成功；1 上传文件名重复
    public String[] list;
    public String dir;
    public long size = 0;
    public String name = "";
}
