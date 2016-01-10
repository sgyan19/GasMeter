package com.cqgas.gasmeter.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cqgas.gasmeter.R;
import com.cqgas.gasmeter.bluetooth.BluetoothDialog;
import com.cqgas.gasmeter.center.BluetoothCenter;
import com.cqgas.gasmeter.center.ReadMeterCenter;
import com.cqgas.gasmeter.connect.Server;
import com.cqgas.gasmeter.fragment.QueryMeterFragment;
import com.cqgas.gasmeter.fragment.ReadMeterFragment;
import com.cqgas.gasmeter.utils.StorageUtils;
import com.cqgas.gasmeter.connect.ConnectHandler;
import com.cqgas.gasmeter.connect.Order;
import com.cqgas.gasmeter.models.ResponseModel;

import java.io.File;


public class MainActivity extends AppCompatActivity implements Order,View.OnClickListener {
    private ConnectHandler handler;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new ConnectHandler(this,this);
        mHandler = new Handler();
        Server.start(handler, handler);

        initDoorListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
//		Log.d("sun", "test git");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initDoorListener(){
        findViewById(R.id.meter_door).setOnClickListener(this);
        findViewById(R.id.query_door).setOnClickListener(this);
        findViewById(R.id.bluetooth_door).setOnClickListener(this);
        findViewById(R.id.exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.meter_door:
                PageFragmentActivity.fastJump(this, ReadMeterFragment.class.getName());
                break;
            case R.id.query_door:
                PageFragmentActivity.fastJump(this, QueryMeterFragment.class.getName());
                break;
            case R.id.bluetooth_door:
                new BluetoothDialog().show(getSupportFragmentManager().beginTransaction(),"");
                break;
            case R.id.exit:
                finish();
                break;
        }
    }

    @Override
    public ResponseModel onListFile() {
        //Toast.makeText(this,"list请求",Toast.LENGTH_SHORT).show();
        String dir = StorageUtils.getTargetDirPath();
        String[] names = StorageUtils.getFileList(dir);
        ResponseModel model = new ResponseModel();
        model.list = names;
        model.dir = dir;
        return model;
    }

    @Override
    public ResponseModel onStartUpdata(String name) {
        Toast.makeText(this,"请求上传请求",Toast.LENGTH_SHORT).show();
        String dir = StorageUtils.getTargetDirPath();
        String[] names = StorageUtils.getFileList(dir);
        ResponseModel model = new ResponseModel();
        model.dir = dir;
        for(String n :names){
            if(n.equals(names)){
                model.type = 1;
            }
        }

        return model;
    }

    @Override
    public ResponseModel onEndUpdata(String name) {
        //Toast.makeText(this,"上传完成",Toast.LENGTH_SHORT).show();
        String dir = StorageUtils.getTargetDirPath();
        String[] names = StorageUtils.getFileList(dir);
        ResponseModel model = new ResponseModel();
        model.dir = dir;
        model.type = 1;
        for(String n :names){
            if(n.equals(names)){
                model.type = 0;
                File f = new File(dir,n);
                long len = f.length();
                model.size = len;
                model.name = name;
            }
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Dialog alertDialog = new AlertDialog.Builder(MainActivity.this).
                        setMessage("获取到PC新数据").
                        create();
                alertDialog.show();
            }
        },500);
        return model;
    }

    @Override
    public ResponseModel onFileInfo(String name) {
        Toast.makeText(this,"请求文件信息",Toast.LENGTH_SHORT).show();
        String dir = StorageUtils.getTargetDirPath();
        String[] names = StorageUtils.getFileList(dir);
        ResponseModel model = new ResponseModel();
        model.dir = dir;
        for(String n :names){
            if(n.equals(names)){
                model.type = 0;
                File f = new File(dir,n);
                long len = f.length();
                model.size = len;
                model.name = name;
            }
        }
        return model;
    }

    @Override
    public ResponseModel onFileDownload() {
        Toast.makeText(this,"请求下载文件",Toast.LENGTH_SHORT).show();
        ResponseModel model = new ResponseModel();
        try {
            ReadMeterCenter.buildToPcFile();
            model.type = 0;
        }catch (Exception e){
            e.printStackTrace();
            model.type = 1;
        }
        return model;
    }

    @Override
    protected void onDestroy() {
        BluetoothCenter.disConnect();
        Server.stop();
        super.onDestroy();
    }
}
