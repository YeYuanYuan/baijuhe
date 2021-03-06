package com.ifeimo.im.framwork.connect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ifeimo.im.OnInitialization;
import com.ifeimo.im.framwork.commander.IConnect;
import com.ifeimo.im.service.LoginService;

/**
 * Created by lpds on 2017/5/2.
 */
@Deprecated
public final class ConnectSupport implements IConnectSupport,OnInitialization{
    private final String TAG = "XMPP_ConnectSupport";
    private boolean isInit = false;
    private IConnect iConnect;
    private boolean isPause = false;

    public ConnectSupport(IConnect iConnect) {
        this.iConnect = iConnect;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {


    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(isInit && isPause) {
            isPause = false;
            if (!iConnect.isConnect()) {
                Log.i(TAG, "onActivityResumed: App IM 断开连接！");
                activity.startService(new Intent(activity, LoginService.class));
            } else {
                Log.i(TAG, "onActivityResumed: App IM 保持连接！");
            }
        }else{
            isInit = true;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        isPause = true;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    @Override
    public boolean isInitialized() {
        return isInit;
    }
}
