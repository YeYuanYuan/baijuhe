package com.lin.download.basic;

import android.content.Context;
import android.content.CursorLoader;
import android.content.pm.PackageManager;
import android.database.ContentObserver;

import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.business.callback.FileDownloadExceptionListener;
import com.lin.download.business.callback.InstallListener;
import com.lin.download.business.callback.OperatorRespone;
import com.lin.download.business.model.DownLoadInfo;
import com.lin.download.business.work.BusinessWrap;
import com.lin.download.business.WorkController;

import java.io.File;

import y.com.sqlitesdk.framework.db.Access;

/**
 * Created by linhui on 2017/12/11.
 * <p/>
 * 尽量所有操作都在此类中调用
 */
public class Entrance {
    public static final String TAG = "DownloadPlan";

    public static void init(Context context) {

        Access.setSqliteDB(new DonwloadSqlLiteOpenHelp(context).getWritableDatabase());
        WorkController.getInstance().init(context);
    }

    public static void addOperatorRespone(OperatorRespone operatorRespone) {
        BusinessWrap.addOperatorRespone(operatorRespone);
    }

    public static void removeOperatorRespone(OperatorRespone operatorRespone) {
        BusinessWrap.removeOperatorRespone(operatorRespone);
    }

    public static void addFileDownloadExceptionListener(FileDownloadExceptionListener fileDownloadExceptionListener){
        WorkController.getInstance().getOperator().addFileDownloadException(fileDownloadExceptionListener);
    }

    public static void removeFileDownloadExceptionListener(FileDownloadExceptionListener fileDownloadExceptionListener){
        WorkController.getInstance().getOperator().removeFileDownloadException(fileDownloadExceptionListener);
    }

    public static void pause(String objectId) {
        WorkController.getInstance().pause(objectId);
    }

    public static void download(String objectId) {
        WorkController.getInstance().download(objectId);
    }

//    public static void download(DownLoadInfo info) {
//        WorkController.getInstance().download(info);
//    }

    public static void delete(String objectId, boolean isdeleteFile) {
        WorkController.getInstance().delete(objectId, isdeleteFile);
    }

    public static void reset(String objectId) {
        WorkController.getInstance().reset(objectId);
    }

    public static void pauseAll() {
        WorkController.getInstance().pauseAll();
    }

    public static void addTask(DownLoadInfo downLoadTable) {
        WorkController.getInstance().addTask(downLoadTable);
    }

    public static void notifyAllQueryDownload(ContentObserver c) {
        BusinessWrap.notifyAllQueryDownload(c);
    }

    public static void findStutasDownloadList(int code, int stutas) {
        BusinessWrap.findStutasDownloadList(code, stutas);
    }

    public static void launchApp(Context context, String packageName, String appPath) {
        BusinessWrap.launchApp(context, packageName, appPath);
    }

    public static void launchApp(Context context, String appPath) {
        if(!new File(appPath).exists()){
            BusinessWrap.modiStatus2(appPath,IBasicInfo.NOT_HAD_STATUS);
            WorkController.getInstance().getInstall().onApkPathError(appPath);
            return;
        }
        BusinessWrap.launchApp(context, getPackageName(context, appPath), appPath);
    }

    public static String getPackageName(Context context, String appPath) {
        return context.getPackageManager().getPackageArchiveInfo(appPath, PackageManager.GET_ACTIVITIES).packageName;
    }

    public static Plan createSimplePlan(String tableId) {
        return PlanImp.getNewInstance(tableId);
    }

    public static void deleteSavePath(String savePath) {
        WorkController.getInstance().deleteSavePath(savePath);
    }

    public static DownLoadInfo getInfoBySavePath(String savePath) {
        return BusinessWrap.getInfoBySavePath(savePath);
    }


    public static DownLoadInfo createInfo(String objectId, String downloadUrl, String savePath, String name) {

        DownLoadInfo downLoadTable = new DownLoadInfo();
        downLoadTable.setDownloadUrl(downloadUrl);
        downLoadTable.setSavePath(savePath);
        downLoadTable.setName(name);
        downLoadTable.setObjectId(objectId);
        return downLoadTable;

    }

    public static void notifyStatus() {
        BusinessWrap.notifyStatus();
    }

    public static CursorLoader createCl(Context context, String where) {
        return createCl(context, where, null);
    }


    public static CursorLoader createCl(Context context, String where, String[] strings) {
        return createCl(context,where,strings,null);

    }

    public static CursorLoader createCl(Context context, String where, String[] strings,String order) {

        return new CursorLoader(context, DownLoadProvider.CONTENT_QUERY_ALL_URI,
                null, where, strings, order);

    }


    public static void addInstallListener(InstallListener installListener){
        WorkController.getInstance().getInstall().addInstallListener(installListener);
    }

    public static void removeInstallListener(InstallListener installListener){
        WorkController.getInstance().getInstall().removeInstallListener(installListener);
    }

}
