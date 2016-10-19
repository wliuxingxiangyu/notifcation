package com.example.hz.notifcation;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private String TAG = "MainActivity";
    private int mNotificationNum = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView notificationTv = (TextView) findViewById(R.id.send_notification_tv);
        notificationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "已点击--");
                for (int i = 1; i <= mNotificationNum; i++) {
                    sendNotification(i);
                }
            }
        });
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

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "new Runnable()--run()--notifyID=" + notifyID);
                //第4步:发送通知请求
                mNotificationManager.notify(notifyID, mBuilder.build());
            }
        });

        try {
            t.sleep(2000L);
        } catch (InterruptedException e) {
            Log.d(TAG, "异常--t.sleep()--");
            e.printStackTrace();
        } finally {
            Log.d(TAG, "finally--t.start()--");
            t.start();
        }

    }

}
