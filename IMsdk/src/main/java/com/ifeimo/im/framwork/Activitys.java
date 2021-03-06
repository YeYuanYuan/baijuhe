package com.ifeimo.im.framwork;

import android.app.Activity;
import android.util.Log;

import com.ifeimo.im.IEmployee;
import com.ifeimo.im.framwork.commander.ILife;
import com.ifeimo.im.framwork.commander.IMMain;

import java.util.LinkedList;

/**
 * Created by lpds on 2017/2/16.
 */
final class Activitys implements ILife,IEmployee {

    private static Activitys activitys;

    static {
        activitys = new Activitys();
    }

    private final String TAG = "XMPP_Activitys";
    private LinkedList<Activity> activities;

    private Activitys(){
        activities = new LinkedList<>();
        Proxy.getManagerList().addManager(this);
    }

    public static Activitys getInstances(){
        return activitys;
    }

    @Override
    public void onCreate(IMMain imWindow) {
        if(imWindow.getContext() instanceof Activity){
            activities.add((Activity) imWindow.getContext());
            Log.e(TAG,"全局 ----- onCreate size = "+activities.size());
        }
    }

    @Override
    public void onResume(IMMain imWindow) {

    }

    @Override
    public void onDestroy(IMMain imWindow) {
        if(imWindow.getContext() instanceof Activity){
            activities.remove(imWindow.getContext());
            Log.e(TAG,"全局 ----- onDestroy size = "+activities.size());
        }
    }

    @Override
    public void onPause(IMMain imWindow) {

    }

    @Override
    public void onStop(IMMain imWindow) {

    }

    @Override
    public boolean isInitialized() {
        return true;
    }
}
