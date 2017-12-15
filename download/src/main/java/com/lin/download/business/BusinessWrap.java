package com.lin.download.business;

import android.content.Context;
import android.database.ContentObserver;

import com.lin.download.basic.OperatorRespone;
import com.lin.download.business.model.DownLoadInfo;
import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by linhui on 2017/12/11.
 */
public class BusinessWrap {

    /**
     * 添加一个下载任务任务
     *
     * @param downLoadTable
     */
    public static void addDownloadTask(DownLoadInfo downLoadTable) {
        WorkUtil.addDownloadTask(downLoadTable);
    }

    /**
     * 暂停
     *
     * @param object_id
     * @param soFarBytes
     * @param totalBytes
     */
    public static void progress(String object_id, final long soFarBytes, final long totalBytes) {
        WorkUtil.progress(object_id, soFarBytes, totalBytes);
    }

    /**
     * 完成
     *
     * @param ObjectId
     */
    public static void completed(String ObjectId) {
        WorkUtil.completed(ObjectId);
    }

    /**
     * 暂停
     *
     * @param ObjectId
     */
    public static void paused(String ObjectId) {
        WorkUtil.paused(ObjectId);
    }

    public static void notifyStatus(){
        WorkUtil.notifyStatus();
    }

    /**
     * 错误
     * 有可能没有存储权限，或者磁盘不够，或者网络错误
     *
     * @param ObjectId
     */
    public static void error(String ObjectId) {
        WorkUtil.error(ObjectId);
    }

    public static void waitting(String ObjectId){WorkUtil.waitting(ObjectId);}

    /**
     * id查找
     *
     * @param object_id
     * @return
     */
    public static DownLoadInfo getInfoByObjectId(String object_id) {
        return WorkUtil.getInfoByObjectId(object_id);
    }

    /**
     * 唤醒contentprovide
     *
     * @param c
     */
    public static void notifyAllQueryDownload(ContentObserver c) {
        WorkUtil.notifyAllQueryDownload(c);
    }

    /**
     * 找到对应状态的下载文件
     *
     * @param code
     * @param stutas
     */
    public static void findStutasDownloadList(int code, int stutas) {
        WorkUtil.findStutasDownloadList(code, stutas);
    }

    public static void addOperatorRespone(OperatorRespone operatorRespone) {
        WorkUtil.addOperatorRespone(operatorRespone);
    }

    public static void removeOperatorRespone(OperatorRespone operatorRespone) {
        WorkUtil.removeOperatorRespone(operatorRespone);
    }

    /**
     * 删除下载文件与数据库行，不建议直接使用
     *
     * @param object_id
     * @param savePath
     */
    public static void delete(String object_id, String savePath,boolean isDeleteFile) {
        WorkUtil.delete(object_id, savePath,isDeleteFile);
    }

    public static void launchApp(Context context, String packageName, String appPath) {
        WorkUtil.launchApp(context, packageName, appPath);
    }

    /**
     * 重新下载删除源文件，不建议直接使用
     *
     * @param table
     */
    public static void reset(String table) {
        WorkUtil.reset(table);
    }

    /**
     * 只删除文件
     *
     * @param savePath
     */
    public static void deleteSavePath(String savePath) {
        WorkUtil.deleteSavePath(savePath);
    }

    /**
     * 根据path获取一行数据看信息
     *
     * @param savePath
     * @return
     */
    public static DownLoadInfo getInfoBySavePath(String savePath) {
        return WorkUtil.getInfoBySavePath(savePath);
    }

    public static void pauseAll() {
        FileDownloader.getImpl().pauseAll();
    }

    public static void scannerDoingStatusException() {
        WorkUtil.scannerDoingStatusException();
    }

    public static void modiStatus2(String appPath, int notHadStatus) {
        WorkUtil.modiStatus2(appPath,notHadStatus);
    }
}
