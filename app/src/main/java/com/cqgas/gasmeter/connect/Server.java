package com.cqgas.gasmeter.connect;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 国耀 on 2015/11/21.
 */
public class Server {
    private static final int SERVER_PORT = 9919;
    private static ServerSocket mServerSocket;
    private static Handler mainHandle;
    private static SocketRunner.ReceiveCallBack callBack;
    private static boolean socketHandling ;
    private static boolean runningFlag;

    public static void start(@NonNull Handler handler,@NonNull SocketRunner.ReceiveCallBack c){
        Server.mainHandle = handler;
        Server.callBack = c;
        try {
            if(mServerSocket != null && !mServerSocket.isClosed()){
                mServerSocket.close();
            }
            mServerSocket = new ServerSocket(SERVER_PORT);
        }catch (IOException e){
            e.printStackTrace();
            return ;
        }
        ExecutorService exe = getExecutorInstance();
        runningFlag = true;
        socketHandling = false;
        exe.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while(runningFlag) {
                        Log.d("sun","start listening");
                        Socket socket = mServerSocket.accept();
                        Log.d("sun","socket accept");
                        if(socket != null) {
                            socketHandling = true;
                            SocketRunner handler = SocketRunner.create(socket, mainHandle, callBack);
                            if(handler != null){
                                handler.run();
                            }
                        }
                        socketHandling = false;
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    return ;
                }
            }
        });
    }

    public static void stop(){
        if(socketHandling){
            runningFlag = false;
        }else{
            try {
                mServerSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            getExecutorInstance().shutdown();
        }
    }

    private static ExecutorService executor;
    private static Object lock = new Object();
    private static ExecutorService getExecutorInstance(){
        if(executor == null || executor.isShutdown()){
            executor = null;
            synchronized (lock){
                if(executor == null){
                    executor = Executors.newCachedThreadPool();
                }
            }
        }
        return executor;
    }
}
