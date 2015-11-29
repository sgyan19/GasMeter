package com.cqgas.gasmeter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.cqgas.gasmeter.R;

public class PageFragmentActivity extends AppCompatActivity {
	
	public final static String EXTRA_KEY_FRAGMENT_ARGS = "bundle_args";
    public final static String EXTRA_KEY_FRAGMENT_NAME = "fragment_class";
	
	private Fragment f;
	private Bundle args;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.pagefragment_activity_layout);
		args = getIntent().getBundleExtra(EXTRA_KEY_FRAGMENT_ARGS);
		String fClassName = args.getString(EXTRA_KEY_FRAGMENT_NAME);
		if(!createFragmentInstance(fClassName)){
			finish();
			return ;
		}
		FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
		fTransaction.replace(R.id.pagefragment_content, f);
		f.setArguments(args);
		fTransaction.commitAllowingStateLoss();
	}
	
	
	private boolean createFragmentInstance(String name){
		if(TextUtils.isEmpty(name)){
			Toast.makeText(this, "内部跳转错误", Toast.LENGTH_SHORT).show();
			return false;
		}
		try{
			Class fragmentClass = Class.forName(name);
			Object o =fragmentClass.newInstance();
			if(o == null || !(o instanceof Fragment)){
				throw new Exception("start PageFragmentActivity error(error instance)");
			}
			f = (Fragment) o;
		}catch(ClassNotFoundException e){
			Toast.makeText(this, "内部跳转错误，找不到fragment类", Toast.LENGTH_SHORT).show();
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void fastJump(Context context,String name){
		Intent intent = new Intent(context,PageFragmentActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(EXTRA_KEY_FRAGMENT_NAME, name);
		intent.putExtra(EXTRA_KEY_FRAGMENT_ARGS,bundle);
		context.startActivity(intent);
	}

	public static void fastJump(Context context,@NonNull String name,@NonNull Bundle args){
		Intent intent = new Intent(context,PageFragmentActivity.class);
		args.putString(EXTRA_KEY_FRAGMENT_NAME, name);
		intent.putExtra(EXTRA_KEY_FRAGMENT_ARGS, args);
		context.startActivity(intent);
	}
}
