package com.example.geospot;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Result extends Activity{
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
		setContentView(R.layout.result);
		Establishment[] est = { new Establishment("Datablitz", "Main Mall, SM Mall of Asia, Pasay City"),
			new Establishment("Ippudo", "Mega Fashion Hall, SM Megamall, Ortigas"),
			new Establishment("Mcdonald's", "Fairview Terraces, Quirino Highway corner, Quezon City"),
			new Establishment("Ace Hardware", "Alabang Town Center, Muntinlupa City, Metro Manila"),
			new Establishment("Toyota Makati", "Ayala COrner Metropolitan Avenues, Makati City"),
			new Establishment("National Bookstore", "32nd Stree COrner Bonifacio Boulevard, Taguig City"),};

		ListView lv = (ListView) findViewById(R.id.listview_result);

		CustomItemAdapter lvAdapter = new CustomItemAdapter(getBaseContext(), R.layout.list_item, est);

		lv.setAdapter(lvAdapter);
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				String eName, eAddress;
				TextView name = (TextView)view.findViewById(R.id.tv_name);
				TextView address = (TextView) view.findViewById(R.id.tv_address);
				
				eName = (String) name.getText();
				eAddress = (String) address.getText();
				
				Intent i = new Intent(Result.this, EstablishmentPage.class);
				i.putExtra("name", eName);
				i.putExtra("address", eAddress);
				startActivity(i);
				
				
				
			}
	    });
	}
	
	
	
	OnClickListener goToHome = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent();
			i.setClass(getBaseContext(), Mainmenu.class);
			startActivity(i);

		}
	};

	
}
