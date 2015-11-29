package com.cqgas.gasmeter.connect;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by 国耀 on 2015/11/21.
 */
public class SocketRunner implements Runnable{
    private static String TAG = SocketRunner.class.getSimpleName();

    private Socket socket;
    private Handler handler;
    private ReceiveCallBack callBack;
    private String receiveData;
    private String sendData;
    public interface ReceiveCallBack {
        void onException(Exception e);
        void onRecevieCommend(String json,SocketRunner sender);
    }

    public void send(String data){
        if(Looper.myLooper() != handler.getLooper()){
            return ;
        }
        sendData = data;
        synchronized (this){
            this.notify();
        }
    }

    public static SocketRunner create(@NonNull Socket socket,@NonNull Handler handler,@NonNull ReceiveCallBack callBack){
        if(handler.getLooper() != Looper.getMainLooper()){
            return null;
        }
        return new SocketRunner(socket,handler,callBack);
    }

    protected SocketRunner(@NonNull Socket socket, @NonNull Handler handler, @NonNull ReceiveCallBack callBack){
        this.socket = socket;
        this.handler = handler;
        this.callBack = callBack;
    }

    @Override
    public void run(){
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            receiveData = reader.readLine();
            Log.d(TAG,receiveData);
        }catch (IOException e){
            postException(e);
            return ;
        }
        Log.d("sun","receiveData = "+receiveData);
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onRecevieCommend(receiveData,SocketRunner.this);
            }
        });
        synchronized (this){
            try {
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        if(!TextUtils.isEmpty(sendData)){
            try {
                OutputStreamWriter writer = new OutputStreamWriter(
                        socket.getOutputStream(), "utf-8");
                writer.write(sendData);
                writer.flush();
                writer.close();
            }catch (IOException e){
                postException(e);
                return ;
            }
        }
        try{
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 从输入流中获取数
     *
     * @param inStream
     *            输入
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream)throws IOException{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    private void postException(final Exception e){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onException(e);
            }
        });
    }
}
