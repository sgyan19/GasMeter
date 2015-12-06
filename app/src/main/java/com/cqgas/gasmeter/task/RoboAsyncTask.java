package com.cqgas.gasmeter.task;


import java.util.concurrent.Executor;

import android.content.Context;
import android.os.Handler;

public abstract class RoboAsyncTask<ResultT> extends SafeAsyncTask<ResultT> {
    protected Context context;

    protected RoboAsyncTask(Context context) {
        this.context = context;
    }

    protected RoboAsyncTask(Context context, Handler handler) {
        super(handler);
        this.context = context;
    }

    protected RoboAsyncTask(Context context, Handler handler, Executor executor) {
        super(handler, executor);
        this.context = context;
    }

    protected RoboAsyncTask(Context context, Executor executor) {
        super(executor);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
