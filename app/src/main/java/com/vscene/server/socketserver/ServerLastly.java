package com.vscene.server.socketserver;

/**
 * Created by alw on 2017/4/24.
 */

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 通过Socket实现
 *
 * @author Administrator
 */
public class ServerLastly implements Runnable {
    private static final String TAG = "ServerLastly";
    ServerSocket server;
    Socket client;
    PrintWriter os;
    BufferedReader is;


    Handler handler;

    /**
     * 此处不将连接代码写在构造方法中的原因：
     * 我在activity的onCreate()中创建示例，如果将连接代码 写在构造方法中，服务端会一直等待客户端连接，界面没有去描绘，会一直出现白屏。
     * 直到客户端连接上了，界面才会描绘出来。原因是构造方法阻塞了主线程，要另开一个线程。在这里我将它写在了run()中。
     */
    public ServerLastly(Handler handler) {
        this.handler = handler;
//        Log.i(TAG, "Server=======打开服务=========");
        try {
//            server=new ServerSocket(8888);
//            client=server.accept();
//            Log.i(TAG, "Server=======客户端连接成功=========");
//             InetAddress inetAddress=client.getInetAddress();
//             String ip=inetAddress.getHostAddress();
//            Log.i(TAG, "===客户端ID为:"+ip);
//            os=new PrintWriter(client.getOutputStream());
//            is=new BufferedReader(new InputStreamReader(client.getInputStream()));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //发数据
    public void send(String data) {
        if (os != null) {
            os.println(data);
            os.flush();
        }
    }

    PrintWriter pw;

    //接数据
    @Override
    public void run() {
        Log.i(TAG, "Server=======打开服务=========");
        try {
            server = new ServerSocket(8080);
            client = server.accept();

            //创建一个客户端的输出流（用于在客户端显示）
            pw = new PrintWriter(client.getOutputStream());
            Log.i(TAG, "Server=======客户端连接成功=========");
            InetAddress inetAddress = client.getInetAddress();
            String ip = inetAddress.getHostAddress();
            Log.i(TAG, "===客户端ID为:" + ip);
            os = new PrintWriter(client.getOutputStream());
            is = new BufferedReader(new InputStreamReader(client.getInputStream()));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        String result = "";
        while (true) {
            try {
                if (is != null) {

                    result = is.readLine();
                    Log.i(TAG, "服务端接到的数据为：" + result);

                    pw.println("服务端说:" + result);
                    pw.flush();

                    //把数据带回activity显示
                    Message msg = handler.obtainMessage();
                    msg.obj = result;
                    msg.arg1 = 0x11;
                    if (!TextUtils.isEmpty(result)) {
                        handler.sendMessage(msg);
                    }
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

    public void close() {
        try {
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
            if (client != null) {
                client.close();
            }
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}