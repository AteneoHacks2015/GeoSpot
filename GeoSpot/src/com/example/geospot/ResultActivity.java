package com.example.geospot;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {
	
	String score, correct, timeLeft;
	TextView finalText, correctText, timeText;
	Button okbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		score = intent.getStringExtra("score");
		correct = intent.getStringExtra("correct");
		timeLeft = intent.getStringExtra("time");
		
		setContentView(R.layout.activity_result);
		
		correctText = (TextView) findViewById(R.id.scrnobonText);
		timeText = (TextView) findViewById(R.id.timebonText);
		finalText = (TextView) findViewById(R.id.finalScoreText);
		okbtn = (Button) findViewById(R.id.okbtn);
		
		correctText.setText(score);
		int bonus = (int) (Integer.valueOf(timeLeft)*0.1);
		int finalsc = (int) bonus + Integer.valueOf(score);
		timeText.setText(Integer.toString(bonus));
		finalText.setText(Integer.toString(finalsc));
		okbtn.setText("OK");
		Button butt = (Button)findViewById(R.id.okbtn);
		butt.setOnClickListener(goBack);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
