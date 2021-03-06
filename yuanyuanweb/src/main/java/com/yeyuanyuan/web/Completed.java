package com.yeyuanyuan.web;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by linhui on 2017/12/5.
 */
public interface Completed {

    <T extends RequestResult> void onFailure(Call call, IOException e, RequetParameter<T> requetEntity);

    <T extends RequestResult> void onResponse(Call call, Response response, RequetParameter<T> requetEntity);

}
