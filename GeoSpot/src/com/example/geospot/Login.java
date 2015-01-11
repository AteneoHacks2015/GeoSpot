package com.example.geospot;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.geospot.Signup.saveUser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity{
EditText email, pass;
private String correct = "";
	

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageView login = (ImageView)findViewById(R.id.SigninButton);
        correct = "";
        login.setOnClickListener(goToMenu);
        TextView signup = (TextView)findViewById(R.id.SignupButton);
        signup.setOnClickListener(goToSignUp);
        email = (EditText)findViewById(R.id.EmailField);
        pass = (EditText)findViewById(R.id.PasswordField);
        email.setOnClickListener(clearemail);
        pass.setOnClickListener(clearpass);
    }
    
    
    OnClickListener clearemail = new OnClickListener() {
		public void onClick(View v) {
			email.setText("");
		}
	};
    
	OnClickListener clearpass = new OnClickListener() {
		public void onClick(View v) {
			pass.setText("");
		}
	};
	
    OnClickListener goToMenu = new OnClickListener() {//login here
		public void onClick(View v) {
			new verify().execute();
			
			
//			if(correct!=""){
//				if(correct.equals("no"))
					Toast.makeText(getApplicationContext(), 
		                    "Incorrect Details", Toast.LENGTH_LONG).show();
//				else if(correct.equals("yes")){ 
					Intent intent = new Intent();
					intent.putExtra("name", email.getText().toString());
					intent.setClass(getBaseContext(), Mainmenu.class);
					startActivity(intent);
//				}
//			}
//			else Toast.makeText(getApplicationContext(), 
//                    "Signing in", Toast.LENGTH_LONG).show();
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
			String url = "http://17febbe6.ngrok.com/WebService/service/checkPassword/" + email.getText().toString() + "/" + pass.getText().toString();
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
					correct = result.toString();
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
	
	 OnClickListener goToSignUp = new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(getBaseContext(), Signup.class);
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
