package com.function.views;

import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.function.activity.R;

/**
 * µãÔÞÐ§¹ûÖ÷ÊÓÍ¼£¬¼Ì³Ðrelativelayout
 * @author Administrator
 *
 */
public class FavorLayout extends RelativeLayout {

	private int lwidth;		//°®ÐÄ¿í¶È
	private int lheight;	//°®ÐÄ¸ß¶È
	private int mwidth;		//FavorLayout¿í¶È
	private int mheight;	//FavorLayout¸ß¶È
	private LayoutParams layoutParams;	//Í¼Æ¬µÄ²¼¾Ö²ÎÊý
	private Random random = new Random();	//Ëæ»úÊý
	private Drawable pink, green, blue;	//°®ÐÄÍ¼Æ¬
	private Drawable[] drawables;
	private Interpolator line = new LinearInterpolator();//ÏßÐÔÔÈËÙ²åÖµÆ÷
	private Interpolator acc = new AccelerateInterpolator();//¼ÓËÙ
	private Interpolator dec = new DecelerateInterpolator();//¼õËÙ
	private Interpolator accdec = new AccelerateDecelerateInterpolator();//ÏÈ¼ÓËÙºó¼õËÙ
	private Interpolator[] interpolators;

	public FavorLayout(Context context){
		super(context);
		setBackgroundColor(getResources().getColor(R.color.white));//ÉèÖÃ±³¾°ÑÕÉ«
		init();
	}
	
	public FavorLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ³õÊ¼»¯±äÁ¿
	 */
	private void init(){
		
		drawables = new Drawable[3];
		//È¡°®ÐÄÍ¼Æ¬
		pink = getResources().getDrawable(R.drawable.pink);
		green = getResources().getDrawable(R.drawable.green);
		blue = getResources().getDrawable(R.drawable.blue);
		drawables[0] = pink;
		drawables[1] = green;
		drawables[2] = blue;
		
		//»ñÈ¡Í¼Æ¬¿í¸ß
		lwidth = pink.getIntrinsicWidth();
		lheight = pink.getIntrinsicHeight();
		
		//µ×²¿Ë®Æ½¾ÓÖÐ
		layoutParams = new LayoutParams(lwidth, lheight);
		layoutParams.addRule(CENTER_HORIZONTAL, TRUE);
		layoutParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);
		
		//²åÖµÆ÷·Å½øÊý×é
		interpolators = new Interpolator[4];
		interpolators[0] = line;
		interpolators[1] = acc;
		interpolators[2] = dec;
		interpolators[3] = accdec;
	}
	
	/**
	 * ¶¯»­ºÏ¼¯
	 * @param target
	 * @return
	 */
	private Animator getAnimator(View target){
		AnimatorSet set = getEnterAnimat(target); //Ëõ·Å¶¯»­
		ValueAnimator bezierAnimator = getBezier(target); //±´Èû¶ûÇúÏß¶¯»­
		AnimatorSet finalset = new AnimatorSet();
		finalset.playSequentially(set, bezierAnimator);
		finalset.setInterpolator(interpolators[random.nextInt(4)]); //Ëæ»ú±äËÙ
		finalset.setTarget(target);
		return finalset;
	}
	
	/**
	 * »ñÈ¡±´Èû¶ûÇúÏß¶¯»­
	 * @param target
	 * @return
	 */
	private ValueAnimator getBezier(View target){
		//³õÊ¼»¯Ò»ÌõÇúÏß
		Bezier bezier = new Bezier(getPointF(2), getPointF(1));
		
		//´«ÈëÁËÇúÏßµÄÆðµã ºÍ ÖÕµã
		ValueAnimator animator = ValueAnimator.ofObject(bezier, 
				new PointF((mwidth-lwidth)/2, mheight-lheight), 
				new PointF(random.nextInt(getWidth()),0));
		
		animator.addUpdateListener(new BezierListen(target));
		animator.setTarget(target);
		animator.setDuration(3000);
		return animator;
	}
	
	/**
	 * ÕâÀïÉæ¼°µ½ÁíÍâÒ»¸ö·½·¨:getPointF(),Õâ¸öÊÇÎÒÓÃÀ´»ñÈ¡Í¾¾¶µÄÁ½¸öµã
	 * ÕâÀïµÄÈ¡Öµ¿ÉÒÔËæÒâµ÷Õû,µ÷Õûµ½ÄãÏ£ÍûµÄÑù×Ó¾ÍºÃ
	 * @param scale
	 * @return
	 */
	private PointF getPointF(int scale){
		PointF pointF = new PointF();
		
		//¼õÈ¥100 ÊÇÎªÁË¿ØÖÆ xÖá»î¶¯·¶Î§,¿´Ð§¹û ËæÒâ~~
		pointF.x = random.nextInt((mwidth - 100));
		
		//ÔÙYÖáÉÏ ÎªÁËÈ·±£µÚ¶þ¸öµã ÔÚµÚÒ»¸öµãÖ®ÉÏ,ÎÒ°ÑY·Ö³ÉÁËÉÏÏÂÁ½°ë ÕâÑù¶¯»­Ð§¹ûºÃÒ»Ð© Ò²¿ÉÒÔÓÃÆäËû·½·¨
		pointF.y = random.nextInt((mheight - 100))/scale;
		return pointF;
	}
	
	/**
	 * Ò»¿ªÊ¼µÄËõ·Å¶¯»­
	 * @param target
	 * @return
	 */
	private AnimatorSet getEnterAnimat(final View target){
		ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
		ObjectAnimator scalex = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
		ObjectAnimator scaley = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);
		AnimatorSet enter = new AnimatorSet();
		enter.setDuration(500);
		enter.setInterpolator(new LinearInterpolator());
		enter.playTogether(alpha, scalex, scaley);
		enter.setTarget(target);
		return enter;
	}
	
	/**
	 * Ìí¼ÓÏÔÊ¾°®ÐÄ
	 */
	public void addfavor(){
		ImageView imageView = new ImageView(getContext());
		imageView.setImageDrawable(drawables[random.nextInt(3)]);
		imageView.setLayoutParams(layoutParams);
		addView(imageView);
		Animator set = getAnimator(imageView);//»ñÈ¡¶¯»­ºÏ¼¯
		set.addListener(new Aniendlisten(imageView));//¼àÌý¶¯»­½áÊø£¬ÒÆ³ý°®ÐÄ
		set.start();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//»ñÈ¡favorlayoutÊµ¼Ê²âÁ¿¿í¸ß
		mwidth = getMeasuredWidth();
		mheight = getMeasuredHeight();
	}

	/**
	 * ±´Èû¶ûÇúÏß,Æðµã£¬ÖÕµã£¬Í¾¾¶µÄµã
	 * @author Administrator
	 * ¹ÀÖµÆ÷
	 */
	public class Bezier implements TypeEvaluator<PointF>{

		private PointF pointF1;//Í¾¾¶µÄÁ½¸öµã
		private PointF pointF2;
		
		public Bezier(PointF pointF1, PointF pointF2){
			this.pointF1 = pointF1;
			this.pointF2 = pointF2;
		}
		
		@Override
		public PointF evaluate(float time, PointF startValue, PointF endValue) {
			// TODO Auto-generated method stub
			float timeleft = 1.0f - time;
			PointF pointF = new PointF();
			PointF pointF00 = (PointF)startValue;//Æðµã
			PointF pointF11 = (PointF)endValue;//ÖÕµã
			
			//±´Èû¶ûÇúÏß¹«Ê½
			pointF.x = timeleft * timeleft * timeleft * (pointF00.x) 
					+ 3*timeleft*timeleft*time*(pointF1.x) 
					+ 3*timeleft*time*time*(pointF2.x) 
					+ time*time*time*(pointF11.x);
			
			pointF.y = timeleft * timeleft * timeleft * (pointF00.y) 
					+ 3*timeleft*timeleft*time*(pointF1.y) 
					+ 3*timeleft*time*time*(pointF2.y) 
					+ time*time*time*(pointF11.y);
			
			return pointF;
		}

	}
	
	/**
	 * ¶¯»­½áÊø¼àÌýÆ÷
	 * @author Administrator
	 *
	 */
	private class Aniendlisten extends AnimatorListenerAdapter{
		private View target;
		
		public Aniendlisten(View tar){
			this.target = tar;
		}
		
		@Override
		public void onAnimationEnd(Animator animation) {
			// TODO Auto-generated method stub
			super.onAnimationEnd(animation);
			removeView(target);//ÒÆ³ýµô£¬·ÀÖ¹×ÓviewÊýÁ¿Ö»Ôö²»¼õ
		}
	}
	
	/**
	 * ¶¯»­¸üÐÂ¼àÌýÆ÷
	 * @author Administrator
	 *
	 */
	public class BezierListen implements ValueAnimator.AnimatorUpdateListener{

		private View target;
		 
		public BezierListen(View tar){
			this.target = tar;
		}
		
		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			// TODO Auto-generated method stub
			
			//ÕâÀï»ñÈ¡µ½±´Èû¶ûÇúÏß¼ÆËã³öÀ´µÄµÄx yÖµ ¸³Öµ¸øview ÕâÑù¾ÍÄÜÈÃ°®ÐÄËæ×ÅÇúÏß×ßÀ²
			PointF pointF = (PointF)animation.getAnimatedValue();
			target.setX(pointF.x);
			target.setY(pointF.y);
			//Ë³±ã×öÒ»¸öalpha¶¯»­,ÕâÑùalpha½¥±äÒ²Íê³ÉÀ²
			target.setAlpha(1-animation.getAnimatedFraction());
		}

	}
}
