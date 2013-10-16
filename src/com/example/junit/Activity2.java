package com.example.junit;

import com.mulodo.mla.activityeffect.ActivityEffect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Activity2 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lips);
		LinearLayout view = (LinearLayout)findViewById(R.id.lips);
		ActivityEffect.Bounce(this, view);
		//notice
	}	
	public void onReceive(Context context, Intent intent) {
	      Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
	   }

}
