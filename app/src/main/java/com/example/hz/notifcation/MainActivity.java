package com.example.hz.notifcation;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private String TAG = "MainActivity";
    private int mNotificationNum;
    private Button mNotificationBtn;
    private EditText mNotificationEt;
    private Timer mTimer ;
    private MyTimerTask mMyTimeTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotificationBtn = (Button) findViewById(R.id.send_notification_btn);
        mNotificationEt = (EditText) findViewById(R.id.notification_num_et);

        mTimer = new Timer(true);

        mNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notificationNumStr = mNotificationEt.getText().toString();
                mNotificationNum = Integer.parseInt(notificationNumStr);
                mNotificationEt.setSelection(0, notificationNumStr.length());
                Log.d(TAG, "已点击-即将发送-mNotificationNum=" + mNotificationNum + "条");


                if(mMyTimeTask != null){
                    mMyTimeTask.cancel();  //将原任务从队列中移除
                }
                mMyTimeTask = new MyTimerTask();
                mTimer.schedule(mMyTimeTask, 2000);//延迟5秒后执行

            }
        });

        viewLeft2Right(mNotificationBtn);
    }

    public void viewLeft2Right(View v){
        Log.d(TAG,"宽度+左边距 == 右边距"+(v.getWidth()+v.getLeft()  == v.getRight() ));
    }

    public void sendNotification(final int notifyID) {

        //第1步：对PendingIntent进行配置：
        Intent in = new Intent(this, Bactivity.class);
        PendingIntent pin = PendingIntent.getBroadcast(this, 0, in, 0);

        //第2步：实例化通知栏构造器NotificationCompat.Builder：
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("测试标题" + notifyID)//设置通知栏标题
                .setContentText("测试内容")
                .setContentIntent(pin) //设置通知栏点击意图
//	.setNumber(number) //设置通知集合的数量
                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//	.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON

        //第3步：获取状态通知栏管理：
        final NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //第4步:发送通知请求
        mNotificationManager.notify(notifyID, mBuilder.build());
        Log.d(TAG, "发送notifyID=" + notifyID);

//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "new Runnable()--run()--notifyID=" + notifyID);
//                //第4步:发送通知请求
//                mNotificationManager.notify(notifyID, mBuilder.build());
//            }
//        });
//
//        try {
//            t.sleep(2000L);
//        } catch (InterruptedException e) {
//            Log.d(TAG, "异常--t.sleep()--");
//            e.printStackTrace();
//        } finally {
//            Log.d(TAG, "finally--t.start()--");
//            t.start();
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()");

    }

    public void release(){
        if(mMyTimeTask != null){
            Log.d(TAG,"mMyTimeTask.cancel()");
            mMyTimeTask.cancel();
            mMyTimeTask=null;
        }

        if(mTimer != null){
            Log.d(TAG,"mTimer.cancel()");
            mTimer.cancel();
            mTimer=null;
        }
    }

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //这里加上要执行的方法。
                    for (int i = 1; i <= mNotificationNum; i++) {
                        sendNotification(i);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    class MyTimerTask extends TimerTask{
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };


}
