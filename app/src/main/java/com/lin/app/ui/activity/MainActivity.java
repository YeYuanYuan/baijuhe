package com.lin.app.ui.activity;

import com.lin.alllib.Model;
import com.lin.alllib.WoodActivity;
import com.lin.app.model.MainModel;

/**
 * Created by linhui on 2017/8/3.
 */
public class MainActivity extends WoodActivity{
    MainModel mainModel = new MainModel();
    @Override
    protected Model configurationModel() {
        return mainModel;
    }

}
