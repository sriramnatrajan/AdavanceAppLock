package com.project.sriram.mail;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

 

public class MainActivity extends Activity {

	ImageView img;
	int curTag;
	String curImg;
	Drawable tmpDr;
	ImageView tmpIv;
	InputStream is;
	public static final String PASS = "PassFile";
	SharedPreferences sp;
	SharedPreferences.Editor edi;
	EditText email;
	String strPatt;
	String mTo,mSubject,mMessage;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sp = getSharedPreferences(PASS, 0);
		edi = sp.edit();
		if(sp.getString("email", "null").equals("null")){
			AlertDialog.Builder build = new AlertDialog.Builder(this);
			build.setTitle("Pattern");
			build.setMessage("Enter your Mail Id: \n(Pattern will be sent to this id)");
			email = new EditText(this);
			build.setView(email);
			build.setPositiveButton("Confirm", new OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					edi.putString("email", email.getText().toString());
					edi.commit();
					dialog.dismiss();
				}
			});
			build.create();
			build.show();
		}
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

	//on saving
	public void onSave(View v) throws Exception
	{
		strPatt = "      C1  C2\n\nC3        C4\n\n   C5  C6\n\nC1 - ";
		int tmp = 0;
		String tmpTag;
		SharedPreferences.Editor spEdi = sp.edit();
		tmpIv = (ImageView) findViewById(R.id.imgOne);
		if(!tmpIv.getTag().toString().equals("zero"))
		{
			tmpTag = tmpIv.getTag().toString();
			spEdi.putString("c1", tmpTag);
			tmp = tmp + 1;
			strPatt = strPatt.concat(tmpTag + " Press");
		}
		tmpIv = (ImageView) findViewById(R.id.imgTwo);
		if(!tmpIv.getTag().toString().equals("zero"))
		{
			tmpTag = tmpIv.getTag().toString();
			spEdi.putString("c2", tmpTag);
			tmp = tmp + 1;
			strPatt = strPatt.concat("\nC2 - " + tmpTag + " Press");
		}
		tmpIv = (ImageView) findViewById(R.id.imgThree);
		if(!tmpIv.getTag().toString().equals("zero"))
		{
			tmpTag = tmpIv.getTag().toString();
			spEdi.putString("c3", tmpTag);
			tmp = tmp + 1;
			strPatt = strPatt.concat("\nC3 - " + tmpTag + " Press");
		}
		tmpIv = (ImageView) findViewById(R.id.imgFour);
		if(!tmpIv.getTag().toString().equals("zero"))
		{
			tmpTag = tmpIv.getTag().toString();
			spEdi.putString("c4", tmpTag);
			tmp = tmp + 1;
			strPatt = strPatt.concat("\nC4 - " + tmpTag + " Press");
		}
		tmpIv = (ImageView) findViewById(R.id.imgFive);
		if(!tmpIv.getTag().toString().equals("zero"))
		{
			tmpTag = tmpIv.getTag().toString();
			spEdi.putString("c5", tmpTag);
			tmp = tmp + 1;
			strPatt = strPatt.concat("\nC5 - " + tmpTag + " Press");
		}
		tmpIv = (ImageView) findViewById(R.id.imgSix);
		if(!tmpIv.getTag().toString().equals("zero"))
		{
			tmpTag = tmpIv.getTag().toString();
			spEdi.putString("c6", tmpTag);
			tmp = tmp + 1;
			strPatt = strPatt.concat("\nC6 - " + tmpTag + " Press");
		}
		if(tmp == 6)
		{
			sendMail();
		}

	}

public void sendMail(){

		//mTo=et.getText().toString();
		mMessage="message";
		mSubject="Mail from Advance lock";

		BackgroundMail.newBuilder(this).withUsername("sriramnatrajan10@gmail.com")
				.withPassword("kannadam")
				.withMailto(sp.getString("email",""))
				.withSubject(mSubject)
				.withBody(strPatt)
				.withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
					@Override
					public void onSuccess() {
						Toast.makeText(MainActivity.this,"SENT",Toast.LENGTH_SHORT).show();
					}
				}).withOnFailCallback(new BackgroundMail.OnFailCallback() {
			@Override
			public void onFail() {
				Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();

			}
		}).send();
	}

	//on going
	public void onClick(View v)
	{
		Intent tmpInt = new Intent(this, LockScreenActivity.class);
		startActivity(tmpInt);
	}
}
