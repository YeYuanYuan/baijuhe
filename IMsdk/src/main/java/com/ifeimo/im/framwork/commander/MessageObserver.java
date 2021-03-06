package com.ifeimo.im.framwork.commander;

import android.database.ContentObserver;

import com.ifeimo.im.IEmployee;
import com.ifeimo.im.OnUpdate;
import com.ifeimo.im.common.bean.model.ChatMsgModel;
import com.ifeimo.im.common.bean.model.GroupChatModel;
import com.ifeimo.im.common.bean.chat.ChatBean;
import com.ifeimo.im.common.bean.chat.GroupChatBean;
import com.ifeimo.im.framwork.message.OnGroupItemOnClickListener;
import com.ifeimo.im.framwork.message.OnHtmlItemClickListener;
import com.ifeimo.im.framwork.message.OnChatMessageReceiver;
import com.ifeimo.im.framwork.message.OnSimpleMessageListener;
import com.ifeimo.im.framwork.message.OnUnReadChange;

import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.packet.Message;

import java.util.Collection;
import java.util.Set;

/**
 * 消息管理接口
 * Created by lpds on 2017/1/17.
 */
public interface MessageObserver extends StanzaListener, MessageListener, IEmployee, OnUpdate,HandlerMessageLeader<Message>{

    int DEFAULT_CACHE_TIME = 2 * 60 * 1000;
    int WAITING_TIME = 5000;

    /**
     * 离开群聊
     *
     * @param key
     */
    void leaveMuccRoom(String key);

    /**
     * 离开单聊
     * DEFAULT_CACHE_TIME = 3分钟
     * Chat对象 默认缓存 DEFAULT_CACHE_TIME 分钟
     *
     * @param key 缓存的 key  = 发送者id+接受者id；
     */
    void leaveChat(String key);

    /**
     * 获得缓存的单聊的 ChatBean ,不允许修改bean信息
     *
     * @param key
     * @return
     */
    ChatBean getChatByChatSet(String key);

    /**
     * 尽量在删除单聊缓存 ChatBean之前，要调用 ChatBean.getChat().close() 方法；
     *
     * @param key
     */
    void removeChatSet(String key);

    /**
     * 获取一个房间实体
     * @param key
     * @return
     */
    GroupChatBean getGroupChatBean(String key);

    /**
     * 移除一个房间实体
     * @param key
     */
    void removeGroupChat(String key);

    /**
     * 创建一个单聊
     *
     * @param receiverID 对方用户
     * @param memberid   自己
     * @return
     */
    ChatBean createChat(String receiverID, String memberid);

    /**
     * 创建一个群聊
     * @param roomid 房间名字
     * @return
     */
    GroupChatBean createGruopChat(String roomid);

    /**
     * 获取所有GroupChatBean 类
     * @return
     */
    Collection<GroupChatBean> getAllGroups();

    /**
     * 获取所有进入房间号
     * @return
     */
    Set<String> getAllRoomKeys();

    /**
     * 发送群聊消息
     *
     * @param key
     * @param msg
     */
    void sendMuccMsg(String key, GroupChatModel msg);

    /**
     * 发送单聊消息
     *
     * @param key
     * @param msg
     */
    void sendChatMsg(String key, ChatMsgModel msg);

    /**
     * 重发单聊消息
     *
     * @param key
     * @param msg
     */
    void reSendChatMsg(String key, ChatMsgModel msg);

    /**
     * 重发群聊消息
     *
     * @param key
     * @param msg
     */
    void reSendMuccMsg(String key, GroupChatModel msg);

    /**
     * 註冊消息監聽事件
     * @param onChatMessageReceiver
     */
    @Deprecated
    boolean registerOnMessageReceiver(OnChatMessageReceiver onChatMessageReceiver);

    /**
     * 移除消息監聽事件
     */
    @Deprecated
    boolean removeOnMessageReceiver(OnChatMessageReceiver onChatMessageReceiver);

    /**
     * 释放所有chat缓存
     */
    void releaseAllChat();

    /**
     * 注册监听接收到消息的回调
     * @param onSimpleMessageListener
     */
    void registerOnMessageReceiver(OnSimpleMessageListener onSimpleMessageListener);

    /**
     * 获取接收到消息的回调
     * @return
     */
    OnSimpleMessageListener getOnChatMessageReceiver();

    /**
     * 注册获得未读消息总行数
     * @param onUnReadChange
     */
    void onUnReadChange(OnUnReadChange onUnReadChange);

    /**
     * 获得当前未读消息总行数观察者
     * @return
     */
    ContentObserver getUnReadObserver();

    /**
     * 点击头像回调
     * @return
     */
    @Deprecated
    OnGroupItemOnClickListener getOnGroupItemOnClickListener();

    /**
     * 群聊单击头像回调
     * @param onGroupItemOnClickListener
     */
    void setOnGroupItemOnClickListener(OnGroupItemOnClickListener onGroupItemOnClickListener);

    OnHtmlItemClickListener getOnHtmlItemClickListener();

    /**
     * 注册item点击有html回调的接口
     * @param onHtmlItemClickListener
     */
    void setOnHtmlItemClickListener(OnHtmlItemClickListener onHtmlItemClickListener);
}
