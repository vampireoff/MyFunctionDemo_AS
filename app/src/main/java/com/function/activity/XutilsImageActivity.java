package com.function.activity;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * xutilsµÄÊ¹ÓÃ
 * @author Administrator
 *
 */
@SuppressLint("ShowToast")
@ContentView(R.layout.activity_xutil)
public class XutilsImageActivity extends BaseFragActivity {

	private Context context;
	
	@ViewInject(R.id.button)
	private Button button;
	
	@ViewInject(R.id.button1)
	private Button button1;
	
	@ViewInject(R.id.img)
    private ImageView imageView;
	
    ImageOptions imageOptions;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		context = this;
		
		//²½ÖèÒ»£ºÌí¼ÓÒ»¸öFragmentTransactionµÄÊµÀý
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		//²½Öè¶þ£ºÓÃadd()·½·¨¼ÓÉÏFragmentµÄ¶ÔÏórightFragment 
		transaction.replace(R.id.mainview, new ImageFragment());
		//²½ÖèÈý£ºµ÷ÓÃcommit()·½·¨Ê¹µÃFragmentTransactionÊµÀýµÄ¸Ä±äÉúÐ§
		transaction.commit();     
		
		imageOptions = new ImageOptions.Builder()
		.setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
		.setRadius(DensityUtil.dip2px(5))
		// Èç¹ûImageViewµÄ´óÐ¡²»ÊÇ¶¨ÒåÎªwrap_content, ²»Òªcrop.
		.setCrop(true)
		// ¼ÓÔØÖÐ»ò´íÎóÍ¼Æ¬µÄScaleType
		//.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		//ÉèÖÃ¼ÓÔØ¹ý³ÌÖÐµÄÍ¼Æ¬
		.setLoadingDrawableId(R.drawable.ic_launcher)
		//ÉèÖÃ¼ÓÔØÊ§°ÜºóµÄÍ¼Æ¬
		.setFailureDrawableId(R.drawable.ic_launcher)
		//ÉèÖÃÊ¹ÓÃ»º´æ
		.setUseMemCache(true)
		//ÉèÖÃÖ§³Ögif
		.setIgnoreGif(false)
		//ÉèÖÃÏÔÊ¾Ô²ÐÎÍ¼Æ¬
//	      .setCircular(false)
		.build();
	}

	@Event(value={R.id.img})
    private void loadImage(View view){
		Toast.makeText(context, "ÎÒÊÇÍ¼Æ¬", 2000).show();
    }
    
    @Event(value={R.id.button1,R.id.button}, type=View.OnClickListener.class) 
    private void onClick(View view){
        //±ØÐëÎªprivate 
        switch (view.getId()) {
        case R.id.button:
        	x.image().bind(imageView, "http://pica.nipic.com/2007-11-09/200711912453162_2.jpg", imageOptions);
        	Toast.makeText(context, "ÎÒÊÇbtn", 2000).show();
            break;
        case R.id.button1:
        	x.image().bind(imageView, "http://image.photophoto.cn/m-6/Animal/Amimal%20illustration/0180300271.jpg", imageOptions);
        	Toast.makeText(context, "ÎÒÊÇbtn1", 2000).show();
            break;

        default:
            break;
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
