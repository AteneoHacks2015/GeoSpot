package com.example.geospot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QuizActivity extends Activity{
	
	TextView question, qNumText, scoreText, timeText;
	Button btnNext;
	EditText answer;
	ArrayList<String> quests;
	ArrayList<String> ans;
	int timeLeft = 100; //in seconds
	int correct = 0;
	int flag = 0, num;
	int score = 0;
	boolean running = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		quests = intent.getStringArrayListExtra("questions");
		ans = intent.getStringArrayListExtra("answers");	
				
		setContentView(R.layout.activity_quiz);
		
		scoreText = (TextView) findViewById(R.id.scrnobonText);
		qNumText = (TextView) findViewById(R.id.qNum);
		question = (TextView) findViewById(R.id.questText);
		btnNext = (Button) findViewById(R.id.nxtQ);
		
		question.setText(quests.get(flag));
		num = flag+ 1;
		qNumText.setText(Integer.toString(num) + "/10");
		scoreText.setText(Integer.toString(score));
		
		Thread countdown = new Thread(new Runnable() {
			@Override
			public void run() {
				timeText = (TextView) findViewById(R.id.timerText);
				timeText.setText(Integer.toString(timeLeft));
				while (timeLeft > 0 && running){
					
					try {
						Thread.sleep(1000);
						timeLeft--;
						runOnUiThread(new Runnable() {
						     @Override
						     public void run() {
						    	 timeText.setText(Integer.toString(timeLeft));
						    }
						});
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				displayResults();
			}
		});
		countdown.start();
		
		btnNext.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				answer = (EditText) findViewById(R.id.editAns);
				if (ans.get(flag).equals(answer.getText().toString())) {
					score += 10;
					correct++;
				}
				
				flag++;
				answer.setText("");
				if (flag < quests.size()) {
					question.setText(quests.get(flag));
					num = flag + 1;
					qNumText.setText(Integer.toString(num) + "/10");
					scoreText.setText(Integer.toString(score));
				}
				
				else {
					displayResults();
				}
			}}
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
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
	
	public void displayResults() {
		running = false;
		Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
    	intent.putExtra("score", Integer.toString(score));
    	intent.putExtra("correct", Integer.toString(correct) );
    	intent.putExtra("time", Integer.toString(timeLeft) );
    	startActivity(intent);
	}
}
