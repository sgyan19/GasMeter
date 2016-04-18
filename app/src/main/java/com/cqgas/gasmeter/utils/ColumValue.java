package com.cqgas.gasmeter.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LiHao on 2016/4/7.
 */
public class ColumValue {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public ColumValue(Context context) {
        sp = context.getSharedPreferences("personal", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean getIsLogin() {

        return sp.getBoolean("islogin", false);
    }

    /**
     *是否登录
     *
     * @return
     */
    public void setIsLogin(boolean islogin) {
        editor.putBoolean("islogin", islogin);
        editor.commit();
    }


    /**
     * 屏幕高度
     *
     * @param ScreenHeight
     */
    public void setScreenHeight(int ScreenHeight) {
        editor.putInt("ScreenHeight", ScreenHeight);
        editor.commit();
    }

    /**
     * 屏幕高度
     *
     */
    public int getScreenHeight() {
        return sp.getInt("ScreenHeight", 0);
    }

    /**
     * 屏幕宽度
     *
     * @param ScreenWidth
     */
    public void setScreenWidth(int ScreenWidth) {
        editor.putInt("ScreenWidth", ScreenWidth);
        editor.commit();
    }

    /**
     * 屏幕宽度
     *
     */
    public int getScreenWidth() {
        return sp.getInt("ScreenWidth", 0);
    }

    /**
     * 设置keyCode
     */
    public void setKeyCode(String keyCode){
        editor.putString("keyCode",keyCode);
        editor.commit();
    }
    /**
     * 得到keyCode
     */
    public String getKeyCode(){
        return sp.getString("keyCode","");
    }
    /**
     * 设置relCode
     */
    public void setRelCode(String relCode){
        editor.putString("relCode",relCode);
        editor.commit();
    }
    /**
     * 得到relCode
     */
    public String getRelCode(){
        return sp.getString("relCode","");
    }





}
