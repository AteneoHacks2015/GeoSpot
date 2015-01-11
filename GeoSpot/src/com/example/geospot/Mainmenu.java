package com.example.geospot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class Mainmenu extends Activity{
	//SeekBar radius;
	String red;
	String link = "";
	String availability = "";
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Bundle bundle = getIntent().getExtras();
        
		red = bundle.getString("name");
        //ImageView home = (ImageView) findViewById(R.id.home);
        //home.setOnClickListener(goToMenu);
        ImageView map = (ImageView)findViewById(R.id.search);
        map.setOnClickListener(goToMap);
        TextView un = (TextView)findViewById(R.id.textView1);
        un.setText(red);
        ProgressBar pg = (ProgressBar)findViewById(R.id.progressBar1);
        new verify().execute("http://17febbe6.ngrok.com/WebService/service/getAccuracy/" + un.getText().toString());
        //Button result = (Button)findViewById(R.id.results);
        //result.setOnClickListener(goToList);
        //radius = (SeekBar) findViewById(R.id.seekBar1);
      
    }
	
	
	public class verify extends AsyncTask<String, Void, String>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = params[0];
			HttpResponse response = null;
			try{
				HttpClient client = new DefaultHttpClient();
			
				HttpGet request = new HttpGet(url);
		 
				if(request != null){
				response = client.execute(request);
		 
				System.out.println("\nSending 'GET' request to URL : " + url);
				System.out.println("Response Code : " + 
		                       response.getStatusLine().getStatusCode());
				}
		 
				if(response.getStatusLine().getStatusCode() != 400 || request != null){
					BufferedReader rd = new BufferedReader(
			                       new InputStreamReader(response.getEntity().getContent()));
			 
					StringBuffer result = new StringBuffer();
					String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
			 
					System.out.println(result.toString());
					System.out.println("heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeerrrrrrrrrrrrreeeeeeeeeeeee");
					availability = result.toString();
					return result.toString();
				}
				
				
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			
		
	}
}

	
	
    OnClickListener goToMap = new OnClickListener() {
		public void onClick(View v) {
			int rad = 1;
			String rads = "";
			//rad = radius.getProgress();
			if(rad == 0)
				rad = 1;
			rads = Integer.toString(rad);
			Intent i = new Intent();
			i.setClass(getBaseContext(), MapActivity.class);
			i.putExtra("radius", rads);
			i.putExtra("name", red);
			startActivity(i);

		}
	};

	 OnClickListener goToList = new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(getBaseContext(), Result.class);
				startActivity(i);

			}
	};


	OnClickListener goToMenu = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent();
			i.setClass(getBaseContext(), Mainmenu.class);
			startActivity(i);

		}
	};
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
