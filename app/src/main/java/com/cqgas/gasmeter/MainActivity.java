package com.cqgas.gasmeter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cqgas.gasmeter.utils.StorageUtils;
import com.cqgas.gasmeter.connect.ConnectHandler;
import com.cqgas.gasmeter.connect.Order;
import com.cqgas.gasmeter.connect.Server;
import com.cqgas.gasmeter.models.ResponseModel;

import java.io.File;


public class MainActivity extends AppCompatActivity implements Order {
    private ConnectHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new ConnectHandler(this,this);
        Server.start(handler,handler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
		Log.d("sun", "test git");
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

    @Override
    public ResponseModel onListFile() {
        Toast.makeText(this,"list请求",Toast.LENGTH_SHORT).show();
        String dir = StorageUtils.getSdcardAppFile(this,getResources().getString(R.string.object_dir));
        String[] names = StorageUtils.getFileList(dir);
        ResponseModel model = new ResponseModel();
        model.list = names;
        model.dir = dir;
        return model;
    }

    @Override
    public ResponseModel onStartUpdata(String name) {
        Toast.makeText(this,"请求上传请求",Toast.LENGTH_SHORT).show();
        String dir = StorageUtils.getSdcardAppFile(this, getResources().getString(R.string.object_dir));
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
        Toast.makeText(this,"上传完成",Toast.LENGTH_SHORT).show();
        String dir = StorageUtils.getSdcardAppFile(this, getResources().getString(R.string.object_dir));
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
        return model;
    }

    @Override
    public ResponseModel onFileInfo(String name) {
        Toast.makeText(this,"请求文件信息",Toast.LENGTH_SHORT).show();
        String dir = StorageUtils.getSdcardAppFile(this,getResources().getString(R.string.object_dir));
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
}
