package com.project.sriram.mail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MailActivity extends Activity {
    EditText et;
    Button btn;
   protected String mTo,mSubject,mMessage;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        et=(EditText)findViewById(R.id.mailsend);
        btn=(Button)findViewById(R.id.buttoSend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextMail();
            }
        });
    }
    public void sendTextMail(){
        mTo=et.getText().toString();
        mMessage="message";
        mSubject="subject";
        String sender;
        BackgroundMail.newBuilder(this).withUsername("sriramnatrajan10@gmail.com")
                .withPassword("kannadam")
                .withMailto(mTo)
                .withSubject(mMessage)
                .withBody(mSubject)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MailActivity.this,"SENT",Toast.LENGTH_LONG).show();
                    }
                }).withOnFailCallback(new BackgroundMail.OnFailCallback() {
            @Override
            public void onFail() {
                Toast.makeText(MailActivity.this,"failed",Toast.LENGTH_LONG).show();

            }
        }).send();
    }
}
