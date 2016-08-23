package com.function.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.function.jni.JniUtils;
import com.function.utils.MyUtils;

/**
 * Created by lwd on 2016/8/22.
 */
public class JniActivity extends BaseActivity implements View.OnClickListener {
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9;
    EditText editText;
    Context context = JniActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jni_activity);
        editText = getEditText(R.id.edittxt1);
        button1 = getButton(R.id.button1);
        button2 = getButton(R.id.button2);
        button3 = getButton(R.id.button3);
        button4 = getButton(R.id.button4);
        button5 = getButton(R.id.button5);
        button6 = getButton(R.id.button6);
        button7 = getButton(R.id.button7);
        button8 = getButton(R.id.button8);
        button9 = getButton(R.id.button9);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                MyUtils.showToast2(context, JniUtils.getStringFromC());
                break;
            case R.id.button2:
                JniUtils.callback_myLog();
                break;
            case R.id.button3:
                JniUtils.setVar();
                MyUtils.showToast2(context, "改变后的var值为：" + JniUtils.var + "");
                break;
            case R.id.button4:
                String string = JniUtils.sayHello(editText.getText().toString());
                MyUtils.showToast2(context, string);
                break;
            case R.id.button5:
                int[] arr = new int[10];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = i;
                }
                int sum = JniUtils.sumArray(arr);
                MyUtils.showToast2(context, "sum = " + sum);
                break;
            case R.id.button6:
                int[][] arr1 = JniUtils.initInt2DArray(3);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        System.out.format("arr[%d][%d] = %d\n", i, j, arr1[i][j]);
                    }
                }
                break;
            case R.id.button7:
                JniUtils.callJavaInstanceMethod();
                break;
            case R.id.button8:
                JniUtils utils = new JniUtils();
                JniUtils.accessInstanceField(utils);
                MyUtils.showToast2(context, utils.getStr());
                break;
            case R.id.button9:
                String[] strings = JniUtils.getStrings(600, "I Love You %d Year！！！");
                for (String string1 : strings) {
                    System.out.println(string1);
                }
                break;
        }
    }
}
