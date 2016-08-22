package com.function.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.function.jni.JniUtils;
import com.function.receiver.AlarmReceiver;
import com.function.utils.MyLocationManager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * ÄÖÖÓ¶¨Ê±Æ÷¹¦ÄÜ
 *
 * @author Administrator
 */
public class AlarmTimerActivity extends BaseActivity implements MyLocationManager.LocationCallBack {

    private TextView textView;
    private Button button;
    AlarmManager alarmManager;
    PendingIntent pIntent;
    int count = 0;

    TelephonyManager Tel;
    MyPhoneStateListener MyListener;
    Method getDbmMethod;

    MyLocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        textView = getTextView(R.id.alarm_text);
        button = getButton(R.id.alarm_btn);

        myLocationManager = new MyLocationManager(AlarmTimerActivity.this, this);

        registerReceiver(receiver, new IntentFilter("refresh"));

//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.HOUR_OF_DAY, 9);
//		calendar.set(Calendar.MINUTE, 45);
//		calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("isalarm");
        pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000, pIntent);

        button.setOnClickListener(this);

        MyListener = new MyPhoneStateListener(this);
        Tel = ( TelephonyManager )getSystemService(Context.TELEPHONY_SERVICE);
        Tel.listen(MyListener ,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        try {
            //反射
            Class clazz=Class.forName("android.telephony.SignalStrength");//得到SignalStrength类
            getDbmMethod = clazz.getMethod("getDbm", null);//得到SignalStrength类中的getDbm方法
            getDbmMethod.setAccessible(true);//取消getDbm的hind属性
        } catch (Exception e) {
// TODO Auto-generated catch block
                   e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Tel.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    private static class MyPhoneStateListener extends PhoneStateListener

    {
        private WeakReference<AlarmTimerActivity> weakReference;

        public MyPhoneStateListener(AlarmTimerActivity activity){
            weakReference = new WeakReference<AlarmTimerActivity>(activity);
        }

      /* Get the Signal strength from the provider, each tiome there is an update  从得到的信号强度,每个tiome供应商有更新*/

        @Override

        public void onSignalStrengthsChanged(SignalStrength signalStrength)

        {

            super.onSignalStrengthsChanged(signalStrength);
//            Toast.makeText(getApplicationContext(), String.valueOf(signalStrength.getGsmSignalStrength()), Toast.LENGTH_SHORT).show();
            AlarmTimerActivity activity = weakReference.get();
            if (activity != null){

                try{
                    //反射调用getDbm()方法
                    Toast.makeText(activity, activity.getDbmMethod.invoke(signalStrength, null).toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }



    };

    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
			textView.setText(count + "");
            count++;
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        super.onClick(v);
        //È¡ÏûÄÖÖÓ
        alarmManager.cancel(pIntent);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        myLocationManager.destoryLocationManager();
        Tel.listen(MyListener, PhoneStateListener.LISTEN_NONE);
        alarmManager.cancel(pIntent);
        sendBroadcast(new Intent("stop"));
        unregisterReceiver(receiver);
    }

    @Override
    public void onCurrentLocation(Location location) {
        textView.setText(location.getLatitude() + "");
    }
}
