package com.example.junit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public  class MainActivity extends Activity implements OnClickListener {
	Button btn,btn2;
	TextView tv;
	EditText et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		initEvent();
		
	}
	private void init()
	{
		btn=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		tv=(TextView)findViewById(R.id.textView1);
		et=(EditText)findViewById(R.id.editText1);
	}
	
	private void initEvent()
	{
		btn.setOnClickListener(this);
		btn2.setOnClickListener(this);
	}
	public void broadcastIntent(View view)
	{
	   Intent intent = new Intent();
	   intent.setAction("com.mulodo.CUSTOM_INTENT");
	   sendBroadcast(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.button1:
				tv.setText(et.getText().toString());
			break;
			case R.id.button2:
				Intent i = new Intent(getApplicationContext(),Activity2.class);
				startActivity(i);
			break;
		}
		
	}

}
