package com.function.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.function.activity.R;
import com.function.views.NumberPicker.OnValueChangeListener;
/**
 * ÈÕÆÚÑ¡Ôñ½çÃæ
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class DateTimePicker extends FrameLayout
{
	private final NumberPicker mHourSpinner;
	private final NumberPicker mMinuteSpinner;
	private Button button;
	private int hour, minute; 
	private OnDateTimeChangedListener mOnDateTimeChangedListener;
	private OnClickListenered clListener;
	
	public DateTimePicker(Context context)
	{
		super(context);
		
		inflate(context, R.layout.datedialog, this);
		//Ê±
		mHourSpinner=(NumberPicker)this.findViewById(R.id.np_hour);
		mHourSpinner.setMinValue(0);
		mHourSpinner.setMaxValue(23);
		mHourSpinner.setValue(0);
		mHourSpinner.setOnValueChangedListener(mOnHourChangedListener);
		//·Ö
		mMinuteSpinner=(NumberPicker)this.findViewById(R.id.np_minute);
		mMinuteSpinner.setMaxValue(59);
		mMinuteSpinner.setMinValue(0);
		mMinuteSpinner.setValue(0);
		mMinuteSpinner.setOnValueChangedListener(mOnMinuteChangedListener);
		//È·¶¨°´Å¥
		button = (Button)this.findViewById(R.id.sure_btn);
		button.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onButtonClicked(v);
		}
	};
	//Êý×Ö¹ö¶¯¼àÌý
	private NumberPicker.OnValueChangeListener mOnMinuteChangedListener=new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
			minute = mMinuteSpinner.getValue();
			onDateTimeChanged();
		}
	};
	//Êý×Ö¹ö¶¯¼àÌý
	private NumberPicker.OnValueChangeListener mOnHourChangedListener=new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
			hour = mHourSpinner.getValue();
			onDateTimeChanged();
		}
	};
	
	
	//Êý×Ö¹ö¶¯¼àÌý½Ó¿Ú
	public interface OnDateTimeChangedListener 
	{
		void onDateTimeChanged(DateTimePicker view, int hour, int minute);
	}
	
	public void setOnDateTimeChangedListener(OnDateTimeChangedListener callback) 
	{
		mOnDateTimeChangedListener = callback;
	}
	
	private void onDateTimeChanged() 
	{
		if (mOnDateTimeChangedListener != null)
		{
			mOnDateTimeChangedListener.onDateTimeChanged(this, hour, minute);
		}
	}
	public interface OnClickListenered
	{
		void OnButtonClickListener(View v);
	}
	
	public void setOnClickListenered(OnClickListenered callback) 
	{
		clListener = callback;
	}
	
	private void onButtonClicked(View v) 
	{
		if (clListener != null)
		{
			clListener.OnButtonClickListener(v);
		}
	}
}
