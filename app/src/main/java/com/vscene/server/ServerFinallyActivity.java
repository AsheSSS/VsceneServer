package com.vscene.server;

/**
 * Created by alw on 2017/4/24.
 */

        import android.app.Activity;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.widget.TextView;

        import com.vscene.server.server.R;

public class ServerFinallyActivity extends Activity {

    TextView tv_showReceiveData;

    ServerLastly server;
    StringBuffer receiveData=new StringBuffer();

    Handler handler=new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1==0x11) {
                receiveData.append((String)msg.obj);
                tv_showReceiveData.setText(receiveData);
                receiveData.append("\r\n");
            }

            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_server_finally);
        tv_showReceiveData=(TextView) findViewById(R.id.tv_showReceiveData);

        server=new ServerLastly(handler);
        new Thread(server).start();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        server.close();
    }

}