package com.lin.app.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lin.alllib.Model;
import com.lin.alllib.common.ScreenUtil;
import com.lin.app.R;
import com.lin.app.activity.DianBaiActivity;
import com.lin.app.activity.SelectInfoActivity;
import com.lin.app.activity.TowActivity;
import com.lin.app.common.AndroidAppManager;
import com.lin.app.data.entity.AppEntity;
import com.lin.app.data.respone.InitEntity;
import com.lin.app.model.support.popupwindow.IPopup;
import com.lin.app.model.support.popupwindow.MyPopupwindow;
import com.lin.app.service.PostmanService;
import com.lin.app.service.commander.Business;
import com.lin.app.ui.activity.NavigationActivity;
import com.yeyuanyuan.web.Zygote;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by lpds on 2017/7/26.
 */
public class MainModel extends Model implements ServiceConnection, Handler.Callback {

    @Bind(R.id.id_content_RecyclerView)
    RecyclerView recyclerView;
    private List<AppEntity> datas = new ArrayList<>();

    private Messenger mService;
    private Messenger mClient = new Messenger(new Handler(this));

    private SearchView mSearchView;

    private IPopup popup;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        popup = new MyPopupwindow(getActivity());
        popup.loadData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                outRect.top = ScreenUtil.dp2px(20);
                outRect.left = ScreenUtil.dp2px(20);
                outRect.right = ScreenUtil.dp2px(20);
                if (parent.getChildAdapterPosition(view) + 1 == datas.size()) {
                    outRect.bottom = ScreenUtil.dp2px(20);
                }
            }
        });
        recyclerView.setAdapter(new BaseQuickAdapter<AppEntity, BaseViewHolder>(R.layout.adapter_appentity, datas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final AppEntity appEntity) {
                ((ImageView) baseViewHolder.getView(R.id.item_main_iv_id)).setImageDrawable(appEntity.getIcon());
                ((TextView) baseViewHolder.getView(R.id.item_main_time_id)).setText(
                        new SimpleDateFormat("yyyyMMdd").format(appEntity.getFirstInstallTime()));
                ((TextView) baseViewHolder.getView(R.id.item_main_text_version)).setText(appEntity.getVersionCode());
                ((TextView) baseViewHolder.getView(R.id.item_main_package)).setText(appEntity.getPackageName());
                ((TextView) baseViewHolder.getView(R.id.item_main_title_id)).setText(appEntity.getAppname());
                baseViewHolder.setOnClickListener(R.id.root_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        getActivity().startActivity(new Intent(v.getContext(),NavigationActivity.class));
                        getActivity().startActivity(new Intent(v.getContext(),DianBaiActivity.class));
//                        getActivity().moveTaskToBack(false);
//                        AndroidAppManager.getInstance().startApp(appEntity.getPackageName());
//                        if(appEnti)
//                        getActivity().startActivity(new Intent(getActivity(), TowActivity.class));
//                        popup.show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        final MenuItem item = menu.findItem(R.id.sreach_layout);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        setSearchView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                startMusic();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    AndroidAppManager.getInstance().postAndroidApp("***&");
                } else {
                    AndroidAppManager.getInstance().postAndroidApp(newText);
                }
                return true;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                AndroidAppManager.getInstance().postAndroidApp("");
                return false;
            }
        });
//        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AndroidAppManager.getInstance().postAndroidApp("*(&*(&*(!&*(&");
//            }
//        });
    }

    @Override
    protected void loadData() {
        AndroidAppManager.getInstance().postAndroidApp();

        getActivity().startService(new Intent(getActivity(), PostmanService.class));
        getActivity().bindService(new Intent(getActivity(), PostmanService.class), this, Context.BIND_AUTO_CREATE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessager(List<AppEntity> list) {
        if (list != null) {
            datas.clear();
            datas.addAll(list);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        getActivity().unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mService = new Messenger(service);
    }

    private void startMusic() {
        if (mService != null) {

            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "kgmusic" + File.separator + "download"
                    + File.separator + "Alan Walker - Legends Never Die (Alan Walker Remix).mp3";
            Bundle bundle = new Bundle();
            bundle.putString("path", path);
            Message message = Message.obtain();
            message.setData(bundle);
            message.what = Business.START_MUSIC;
            message.replyTo = mClient;
            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onServiceDisconnected(ComponentName name) {


    }

//    private void sum() {
//        if (mService != null) {
//            Message message = Message.obtain();
//            message.what = PostmanService.SUM;
//            message.arg2 = (int) (Math.random() * 100);
//            message.arg1 = (int) (Math.random() * 100);
//            message.replyTo = mClient;
//            try {
//                mService.send(message);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @Override
    public boolean handleMessage(Message msg) {
        showSnackbar(msg.arg1 + " + " + msg.arg2 + " = " + ((Bundle) msg.obj).getInt("sum"));
        return true;
    }

    @Override
    public Object getAffirmObject() {
        return null;
    }
}
