package com.vscene.server.socketserver.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.vscene.server.socketserver.ServerLastly;

import static android.content.ContentValues.TAG;

/**
 * Created by David on 2017/5/1.
 * Des : 接收客户端发送指令服务
 */

public class SocketService extends Service {

    ServerLastly server;
    StringBuffer receiveData = new StringBuffer();

    Handler socketHander = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1 == 0x11) {
                receiveData.append((String) msg.obj);
//                tv_showReceiveData.setText(receiveData);
                Log.e(TAG,"receive msg:" + receiveData);
            }

            return false;
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("eeee","onStartCommand");
        // 核心拦截器
        server = new ServerLastly(socketHander);
        new Thread(server).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("eee","onUnbind");
        server.close();
        return super.onUnbind(intent);

    }

    @Override
    public void onDestroy() {
        Log.e("eee","onDestroy");
        server.close();
        super.onDestroy();
    }
}
