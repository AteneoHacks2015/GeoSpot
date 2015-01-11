package com.example.geospot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity{
	private String name = "";
	private String password = "";
	private EditText username;
	private EditText password1;
	private EditText password2;
	String availability = "";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button back = (Button)findViewById(R.id.backButton);
        back.setOnClickListener(goToLogin);
        Button done = (Button)findViewById(R.id.doneButton);
        done.setOnClickListener(goToMenu);
        username = (EditText)findViewById(R.id.UsernameField);
        password1 = (EditText)findViewById(R.id.PasswordField);
        password2 = (EditText)findViewById(R.id.ConfirmPasswordField);
        availability = "";
    }

	OnClickListener goToLogin = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent();
			i.setClass(getBaseContext(), Login.class);
			startActivity(i);

		}
	};
	
	OnClickListener goToMenu = new OnClickListener() {//connect database here
		public void onClick(View v) {
			System.out.println("Entered hereeee");
			new verify().execute();
			if(!password1.getText().toString().equals(password2.getText().toString())){
				Toast.makeText(getApplicationContext(), 
	                    "Passwords do not match", Toast.LENGTH_LONG).show();
			}
			if(availability!=""){
				if(availability.equals("taken") || !password1.getText().toString().equals(password2.getText().toString()))
					Toast.makeText(getApplicationContext(), 
		                    "Username is taken", Toast.LENGTH_LONG).show();
				else{ new saveUser().execute();
					Intent i = new Intent();
					i.setClass(getBaseContext(), Login.class);
					startActivity(i);
				}
			}
			else Toast.makeText(getApplicationContext(), 
                    "checking username availability...", Toast.LENGTH_LONG).show();
		}
	};
	
	public class verify extends AsyncTask<String, Void, String>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = "http://17febbe6.ngrok.com/WebService/service/checkUsernameAvailability/" + username.getText().toString();
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
	
	public class saveUser extends AsyncTask<String, Void, String>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = "http://17febbe6.ngrok.com/WebService/service/saveUser/" + username.getText().toString() + "/" + password1.getText().toString() + "/" + 0;
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
			 
					
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), 
                    "Successfully Registered", Toast.LENGTH_LONG).show();
		
	}
}

	
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
