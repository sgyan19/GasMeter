package com.cqgas.gasmeter.connect;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import com.cqgas.gasmeter.utils.GsonUtils;
import com.cqgas.gasmeter.models.OrderModel;
import com.cqgas.gasmeter.models.ResponseModel;

/**
 * Created by 国耀 on 2015/11/21.
 */
public class ConnectHandler extends Handler implements SocketRunner.ReceiveCallBack {
    private Activity mActivity;
    private Order back;
    public ConnectHandler(Activity activity,Order callback){
        mActivity = activity;
        back = callback;
    }

    @Override
    public void onException(Exception e) {

    }

    @Override
    public void onRecevieCommend(String json, SocketRunner sender) {
        OrderModel model = null;
        try {
            model = GsonUtils.fromJson(json, OrderModel.class);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        String response = "err order";
        ResponseModel rModel = null;
        if(model != null){
            switch (model.type){
                case OrderModel.TYPE_LIST:
                    rModel = back.onListFile();
                    break;
                case OrderModel.TYPE_UPDATA_START:
                    rModel = back.onStartUpdata(model.name);
                    break;
                case OrderModel.TYPE_UPDATA_END:
                    rModel = back.onEndUpdata(model.name);
                    break;
                case OrderModel.TYPE_FILE_INFO:
                    rModel = back.onFileInfo(model.name);
                    break;
                case OrderModel.TYPE_FILE_DOWNLOAD:
                    rModel = back.onFileDownload();
                    break;
            }
        }
        if(rModel != null){
            response = GsonUtils.toJson(rModel);
        }
        response += "\n";
        Log.d("sun","response data:"+response);
        sender.send(response);
    }
}
