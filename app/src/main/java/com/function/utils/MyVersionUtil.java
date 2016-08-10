package com.function.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.function.activity.R;
import com.function.config.Common;
import com.function.config.Myapplication;

/**
 * °æ±¾¸üÐÂ¹¤¾ß
 * @author Administrator
 *
 */
public class MyVersionUtil {

	private Map<String, String> versionMap;
	private File apkFile;
	public Activity activity;
	private int progress = 0;
	private ProgressDialog progressDialog;
	private static final int DOWNLOAD = 1; /* ÏÂÔØÖÐ */
	private static final int DOWNLOAD_FINISH = 2; /* ÏÂÔØ½áÊø */
	private boolean cancelUpdate; /* ÊÇ·ñÈ¡Ïû¸üÐÂ */
	private Dialog versionDialog;
	private String apkname;
	private boolean ism;
	
	public MyVersionUtil(Activity activity, Map<String, String> versionMap, boolean ism){
		this.activity = activity;
		this.versionMap = versionMap;
		this.ism = ism;
		
		apkname = versionMap.get("url").substring(versionMap.get("url").lastIndexOf("/") + 1);
		apkFile = new File(Common.apkurl, apkname);
		
		checkversion();
	}
	
	/**
	 * [»ñÈ¡Ó¦ÓÃ³ÌÐò°æ±¾Ãû³ÆÐÅÏ¢]
	 * 
	 * @param context
	 * @return µ±Ç°Ó¦ÓÃµÄ°æ±¾Ãû³Æ
	 */
	public static String getVersionName(Context context)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ÅÐ¶Ï°æ±¾ºÅ´óÐ¡
	 * @param no
	 * @param context
	 * @return
	 */
		public static boolean isUpdate(String no, Context context) {
			int num = getAppVersionCode(context);
			if (num != 0) {
				// °æ±¾ÅÐ¶Ï
				if (Integer.parseInt(no) > num) {
					return true;
				}
			}
			return false;
		}
		
		/**
		 * »ñÈ¡°æ±¾ºÅ
		 * @param appPackageCode
		 * @return
		 */
		public static int getAppVersionCode(Context context) {
			PackageInfo info = null;
			try {
				info = context.getPackageManager().getPackageInfo(
						Myapplication.getInstance().getPackageName(), 0);
			} catch (NameNotFoundException e) {
				info = null;
				e.printStackTrace();
			}
			if (null == info) {
				return 0;
			}
			return info.versionCode;
		}
	
	/**
	 * ¼ì²é°æ±¾£¬±È½Ï°æ±¾ºÅ
	 */
	public void checkversion(){
		if (isUpdate(versionMap.get("no"), activity)) {
			// ÏÔÊ¾ÌáÊ¾¶Ô»°¿ò
			showNoticeDialog();
		} else {
			if (!ism) {
				MyUtils.showToast2(activity, "µ±Ç°ÒÑÊÇ×îÐÂ°æ±¾");
			}
		}
    }
    
    /**
	 * ÏÔÊ¾°æ±¾¸üÐÂ¶Ô»°¿ò
	 */
	public void showNoticeDialog(){
		View view = LayoutInflater.from(activity).inflate(R.layout.versiontip_view, null);
		Button vyButton = (Button)view.findViewById(R.id.u_button);
		Button vnButton = (Button)view.findViewById(R.id.nu_button);
		TextView versioninfo = (TextView)view.findViewById(R.id.version_info);
		versioninfo.setText("°æ±¾ºÅ£º" + versionMap.get("name") + 
				"\n" + "·¢²¼Ê±¼ä£º" + versionMap.get("time") + "\n" 
				+ "¸üÐÂÄÚÈÝ£º" + "\n" + versionMap.get("content"));
		
		versionDialog = new Dialog(activity, R.style.alert_dialog);
		versionDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		versionDialog.show();
		versionDialog.setCanceledOnTouchOutside(false);
		WindowManager.LayoutParams params = versionDialog.getWindow()   
                .getAttributes();   
        params.width = MyScreenUtils.getWidth(activity) - activity.getResources().getDimensionPixelSize(R.dimen.versiondialogpadding);   
//        params.height = height - 200;   
        versionDialog.getWindow().setAttributes(params);  
        
		vyButton.setOnClickListener(new yclick());
		
		vnButton.setOnClickListener(new nclick());
	}
	
	public class yclick implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			versionDialog.dismiss();
			if (apkFile.exists()) {
				//apk´æÔÚÔòÖ±½Ó°²×°
				installApk();
			}else {
				//ÏÔÊ¾ÏÂÔØ¶Ô»°¿ò
				showDownloadDialog();
			}
		}
	}
	
	public class nclick implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			versionDialog.dismiss();
		}
	}
	
	/**
	 * ÏÔÊ¾Èí¼þÏÂÔØ¶Ô»°¿ò
	 */
	@SuppressLint("NewApi")
	public void showDownloadDialog() {
		// ¹¹ÔìÈí¼þÏÂÔØ¶Ô»°¿ò
		progressDialog = new ProgressDialog(activity);
		progressDialog.setTitle("ÏÂÔØÖÐ...");
		progressDialog.setMax(100);
		// ÉèÖÃ½ø¶ÈÌõ·ç¸ñSTYLE_HORIZONTAL
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setProgress(0);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

		// ÏÂÔØÎÄ¼þ
		new downloadApkThread().start();
	}

	/**
	 * ÏÂÔØÎÄ¼þÏß³Ì
	 */
	public class downloadApkThread extends Thread {
		
		@Override
		public void run() {
			try {
				// ÅÐ¶ÏSD¿¨ÊÇ·ñ´æÔÚ£¬²¢ÇÒÊÇ·ñ¾ßÓÐ¶ÁÐ´È¨ÏÞ
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					
					URL url = new URL(MyUtils.getIMG_url() + versionMap.get("url"));
					// ´´½¨Á¬½Ó
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// »ñÈ¡ÎÄ¼þ´óÐ¡
					int length = conn.getContentLength();
					// ´´½¨ÊäÈëÁ÷
					InputStream is = conn.getInputStream();

					MyFileUtil.createDirectory(Common.apkurl);
					
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// »º´æ
					byte buf[] = new byte[1024];
					// Ð´Èëµ½ÎÄ¼þÖÐ
					do {
						int numread = is.read(buf);
						count += numread;
						// ¼ÆËã½ø¶ÈÌõÎ»ÖÃ
						progress = (int) (((float) count / length) * 100);
						// ¸üÐÂ½ø¶È
						handler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// ÏÂÔØÍê³É
							handler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// Ð´ÈëÎÄ¼þ
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// µã»÷È¡Ïû¾ÍÍ£Ö¹ÏÂÔØ.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// È¡ÏûÏÂÔØ¶Ô»°¿òÏÔÊ¾
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.cancel();
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// ÕýÔÚÏÂÔØ
			case DOWNLOAD:
				// ÉèÖÃ½ø¶ÈÌõÎ»ÖÃ
				if (progressDialog != null && progressDialog.isShowing())
					progressDialog.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// °²×°ÎÄ¼þ
				installApk();
				break;
			default:
				break;
			}
		};
	};
	
	/**
	 * °²×°APKÎÄ¼þ
	 */
	private void installApk() {
		if (!apkFile.exists()) {
			return;
		}
		// Í¨¹ýIntent°²×°APKÎÄ¼þ
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkFile.toString()),
				"application/vnd.android.package-archive");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //¼ÓÉÏÕâ¾ä£¬°²×°¹ý³ÌÖÐapp²Å²»»á¹Ø±Õ
		activity.startActivity(i);
	}
}
