package com.lin.download.business;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentProvider;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lin.download.FileListActivity;
import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.util.RecyclerViewCursorAdapter;
import com.liulishuo.filedownloader.services.DownloadMgrInitialParams;

import y.com.sqlitesdk.framework.business.BusinessUtil;
import y.com.sqlitesdk.framework.db.Access;

/**
 * Created by linhui on 2017/12/11.
 */
public class ViewSupportLoader implements LoaderManager.LoaderCallbacks<Cursor> {


    private Loader<Cursor> loader;
    private AppCompatActivity context;
    private CursorLoader cursorLoader;
    private ISwapCursorData swapCursor;

    public void init(AppCompatActivity context, int loadId, ISwapCursorData swapCursor) {
        init(context,
                loadId,
                new CursorLoader(context, DownLoadProvider.CONTENT_QUERY_ALL_URI, null, null, null,null),
                swapCursor);
    }

    public void init(AppCompatActivity context, int loadId, CursorLoader cursorLoader, ISwapCursorData swapCursor) {
        if (loader == null) {
            this.context = context;
            this.cursorLoader = cursorLoader;
            this.swapCursor = swapCursor;
            loader = this.context.getLoaderManager().initLoader(loadId, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        swapCursor.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        try {
            swapCursor.swapCursor(null);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
