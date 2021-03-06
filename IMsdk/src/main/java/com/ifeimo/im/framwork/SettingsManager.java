package com.ifeimo.im.framwork;

import com.ifeimo.im.framwork.commander.IMSetting;
import com.ifeimo.im.framwork.setting.Builder;

/**
 * Created by lpds on 2017/1/16.
 */
final class SettingsManager implements IMSetting{


    private static SettingsManager settingsManager;

    static{
        settingsManager = new SettingsManager();
    }

    private Builder builder;

    private SettingsManager(){
        ManagerList.getInstances().addManager(this);
        builder = new Builder(this);
    }

    public static IMSetting getInstances(){
        return settingsManager;
    }

    public Builder getBuilder() {
        return builder;
    }

    @Override
    public boolean isInitialized() {
        return true;
    }


}
