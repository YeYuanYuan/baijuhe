package com.lin.app.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.util.Log;

import com.lin.app.service.binder.PostmanWrapper;
import com.lin.app.service.binder.ServerSupport;
import com.lin.app.service.commander.Business;

import java.util.List;

/**
 * Created by linhui on 2017/8/31.
 */
public class PostmanService extends MediaBrowserServiceCompat implements Handler.Callback {

    public static final String ROOT_ID = "0x91241";

    private final Business business = new BusinessSupport();
    private final String TAG = this.getClass().getSimpleName();
    private Handler handler;
    private Messenger messenger;
    @Override
    public boolean handleMessage(Message msg) {
        if(!business.handlerMsg(msg)){
            switch (msg.what){
                case Business.STOP:
                    stopSelf();
                    break;
            }
        }
        return true;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(this);
        messenger = new Messenger(handler);
        PostmanWrapper.getInstance().attach(this);
        Log.i(TAG, "onCreate: ");

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return START_STICKY;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.i(TAG, "unbindService: ");
        super.unbindService(conn);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return messenger.getBinder();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {

        if(clientPackageName.equals(getPackageName())){
            return new BrowserRoot(null,null);
        }

        return new BrowserRoot(ROOT_ID,null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }

}
