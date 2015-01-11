package com.example.geospot;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class EstablishmentPage extends Activity{
	
	ArrayList<String> quests; 
	ArrayList<String> ans; 
	TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setContentView(R.layout.establishment_page);
        Bundle bundle = getIntent().getExtras();
		String eName = bundle.getString("name");
		desc = (TextView)findViewById(R.id.content);
		new UrlHelper().execute(eName);
		
		
		TextView tv1 = (TextView)findViewById(R.id.name);
		tv1.setText(eName);
        Button take = (Button)findViewById(R.id.button1);
        take.setOnClickListener(takeQuiz);
    }
    
    OnClickListener takeQuiz = new OnClickListener() {
		public void onClick(View v) {
			startQuiz();

		}
};


public class UrlHelper extends AsyncTask<String, Void, String>{

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		String url = "http://en.m.wikipedia.org/wiki/" + params[0];
		
		Document document;
		Elements elements;
		StringBuffer info = new StringBuffer(""); 
		try {
			document = Jsoup.connect(url).get();
			elements = document.select(".content p");
			if(elements.isEmpty()){
				info.append("No Wikipedia entry found.");
			}else{
				for(Element element: elements){
					info.append(element.text() + "\n\n");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return info.toString();
	}
	
	@Override
	protected void onPostExecute(String result) {
		desc.setText(result);
	}
	
}





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    public void startQuiz(){
    	Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
    	long seed = System.nanoTime();
    	Collections.shuffle(quests, new Random(seed));
    	Collections.shuffle(ans, new Random(seed));
    	intent.putStringArrayListExtra("questions", quests);
    	intent.putStringArrayListExtra("answers", ans);
    	startActivity(intent);
    }
    
    public void getData() {
    	quests = new ArrayList<String>(
    		    Arrays.asList("What year did Ateneo de Manila University begin as a public primary school established in Intramuros for the city of Manila?",
    		    		"What destroyed the Intramuros campus?",
    		    		"In what event led to only one structure to remain standing – the statue of St. Joseph and the Child Jesus which now stands in front of the Jesuit Residence in the Loyola Heights campus?",
    		    		"Following the American Liberation, where was Ateneo De Manila temporarily reopened?",
    		    		"In what year did the Padre Faura campus reopen?",
    		    		"During Ateneo’s centennial year, it became a university. In what year was this?",
    		    		"Where did Ateneo open a new campus for its professional schools?",
    		    		"Who led the university on the year it moved most of its units to its present Loyola Heights campus?",
    		    		"What was one of the first colleges in the Philippines that were founded by the Jesuits?",
    		    		"What year were the Jesuits allowed to come back to the Philippines after their expulsion from the country?"));
    	
    	ans = new ArrayList<String>(
    			Arrays.asList("1859", "Fire", "World War 2", "Sampaloc", "1946", "1959", "Salcedo Village, Makati", "Fr. William Masterson, S.J.", "Colegio de Manila", "1859"));
    }
	
}
