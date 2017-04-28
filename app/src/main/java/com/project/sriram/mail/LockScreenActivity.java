package com.project.sriram.mail;

import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LockScreenActivity extends Activity{

	ImageView img;
	boolean isPaused = false;
	int curTag;
	String curImg;
	Drawable tmpDr;
	ImageView tmpIv;
	InputStream is;
	public static final String PASS = "PassFile";
	KeyguardManager km;
	KeyguardManager.KeyguardLock kl;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreen);
        
        km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("AdvanceLock");
        kl.disableKeyguard();
        
        TextView tmpTxt = (TextView) findViewById(R.id.txtTime);
        tmpTxt.setText(android.text.format.DateFormat.format("hh:mmaa", new java.util.Date()).toString());
        tmpTxt = (TextView) findViewById(R.id.txtDate);
        tmpTxt.setText(android.text.format.DateFormat.format("E, dd MMMM", new java.util.Date()).toString());
        tmpTxt = (TextView) findViewById(R.id.txtCrr);
        TelephonyManager tm = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        tmpTxt.setText(tm.getNetworkOperatorName());
        
    }
	
	@Override
	public void onAttachedToWindow()
	{
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		super.onAttachedToWindow();
	}
	
	@Override
	public void onBackPressed()
	{
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		if(isPaused)
		{
			finish();
			Intent tmpInt = new Intent(this, LockScreenActivity.class);
			startActivity(tmpInt);
			isPaused = false;
		}
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		isPaused = true;
	}
	
	//on Circls clicked
    public void onCircleClick(View v) throws IOException
    {
    	tmpIv = (ImageView) v;
    	curImg = tmpIv.getTag().toString();
    	if(curImg.equals("zero"))
    	{
    		is = getAssets().open("1.png");
    		tmpDr = Drawable.createFromStream(is, null);
    		tmpIv.setImageDrawable(tmpDr);
    		tmpIv.setTag(1);
    	}
    	else
    	{
    		curTag = (Integer) tmpIv.getTag();
    		curTag = (curTag % 7) + 1;
    		is = getAssets().open(Integer.toString(curTag) + ".png");
    		tmpDr = Drawable.createFromStream(is, null);
    		tmpIv.setImageDrawable(tmpDr);
    		tmpIv.setTag(curTag);
    	}
    }
    
    //on unlock checking the saved password
    public void onUnlock(View v)
    {
    	int tmpValid = 0;
    	SharedPreferences sp = getSharedPreferences(PASS, 0);
    	tmpIv = (ImageView) findViewById(R.id.imgOne);
    	if(sp.getString("c1", "").equals(tmpIv.getTag().toString()))
    		tmpValid = tmpValid + 1;
    	tmpIv = (ImageView) findViewById(R.id.imgTwo);
    	if(sp.getString("c2", "").equals(tmpIv.getTag().toString()))
    		tmpValid = tmpValid + 1;
    	tmpIv = (ImageView) findViewById(R.id.imgThree);
    	if(sp.getString("c3", "").equals(tmpIv.getTag().toString()))
    		tmpValid = tmpValid + 1;
    	tmpIv = (ImageView) findViewById(R.id.imgFour);
    	if(sp.getString("c4", "").equals(tmpIv.getTag().toString()))
    		tmpValid = tmpValid + 1;
    	tmpIv = (ImageView) findViewById(R.id.imgFive);
    	if(sp.getString("c5", "").equals(tmpIv.getTag().toString()))
    		tmpValid = tmpValid + 1;
    	tmpIv = (ImageView) findViewById(R.id.imgSix);
    	if(sp.getString("c6", "").equals(tmpIv.getTag().toString()))
    		tmpValid = tmpValid + 1;
    	
    	if(tmpValid == 6)
    	{
    		kl.reenableKeyguard();
    		finish();
    	}
    	else
    		Toast.makeText(getApplicationContext(), "Pattern Mismatch, Please try again", Toast.LENGTH_SHORT).show();
    }
}