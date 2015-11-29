package com.cqgas.gasmeter.connect;

import com.cqgas.gasmeter.models.ResponseModel;

/**
 * Created by 国耀 on 2015/11/21.
 */
public interface Order {
    ResponseModel onListFile();
    ResponseModel onStartUpdata(String name);
    ResponseModel onEndUpdata(String name);
    ResponseModel onFileInfo(String name);
}
