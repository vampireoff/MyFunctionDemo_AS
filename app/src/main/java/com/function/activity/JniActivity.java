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
    Button button1, button2, button3, button4;
    EditText editText;
    Context context = JniActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jni_activity);
        button1 = getButton(R.id.button1);
        button2 = getButton(R.id.button2);
        button3 = getButton(R.id.button3);
        button4 = getButton(R.id.button4);
        editText = getEditText(R.id.edittxt1);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
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
        }
    }
}
