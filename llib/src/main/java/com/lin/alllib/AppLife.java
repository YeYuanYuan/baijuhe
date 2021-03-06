package com.lin.alllib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lin.alllib.framwork.DebugGod;
import com.lin.alllib.framwork.commander.ILife;
import com.lin.alllib.framwork.commander.CommanderLife;
import com.lin.alllib.framwork.manager.ActivityManager;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by lpds on 2017/7/26.
 */
public class AppLife implements Application.ActivityLifecycleCallbacks,CommanderLife{
    private static AppLife appLife;
    private Application application;
    static{
        appLife = new AppLife();
    }

    private Set<ILife> lifeSet;
    private AppLife(){
        lifeSet = new LinkedHashSet<>();
    }

    void setApplication(Application application){
        this.application = application;
        this.application.registerActivityLifecycleCallbacks(AppLife.getInstance().getActivityLifecycleCallbacks());
        ActivityManager.getInstance();
    }



    public static CommanderLife getInstance(){return appLife;}

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        DebugGod.i(activity.getClass().getSimpleName(),activity.hashCode()+" onActivityCreated");
        for(ILife iLife : lifeSet){
            iLife.onCreate(activity,savedInstanceState);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        DebugGod.i(activity.getClass().getSimpleName(),activity.hashCode()+" onActivityStarted");
        for(ILife iLife : lifeSet){
            iLife.onStart(activity);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        DebugGod.i(activity.getClass().getSimpleName(),activity.hashCode()+" onActivityResumed");
        for(ILife iLife : lifeSet){
            iLife.onResume(activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        DebugGod.i(activity.getClass().getSimpleName(),activity.hashCode()+" onActivityPaused");
        for(ILife iLife : lifeSet){
            iLife.onPause(activity);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        DebugGod.i(activity.getClass().getSimpleName(),activity.hashCode()+" onActivityStopped");
        for(ILife iLife : lifeSet){
            iLife.onStop(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        DebugGod.i(activity.getClass().getSimpleName(),activity.hashCode()+" onActivitySaveInstanceState");
        for(ILife iLife : lifeSet){
            iLife.onSaveInstanceState(activity,outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        DebugGod.i(activity.getClass().getSimpleName(),activity.hashCode()+" onActivityDestroyed");
        for(ILife iLife : lifeSet){
            iLife.onDestroy(activity);
        }
    }

    @Override
    public void add(ILife life) {
        lifeSet.add(life);
    }

    @Override
    public void remove(ILife life) {
        lifeSet.remove(life);
    }

    @Override
    public Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return this;
    }

    @Override
    public Application getApplication() {
        return application;
    }
}
