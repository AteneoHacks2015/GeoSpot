package com.example.geospot;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CustomItemAdapter extends ArrayAdapter<Establishment>{

	Establishment[] esta;

	public CustomItemAdapter(Context context, int resource, Establishment[] objects) {
		super(context, resource, objects);
		esta = objects;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);

			convertView = layoutInflater.inflate(R.layout.list_item, parent,false);
		}

		String address = esta[position].getAddress();
		String name = esta[position].getName();

		TextView textView2 = (TextView) convertView.findViewById(R.id.tv_address);
		TextView textView = (TextView) convertView.findViewById(R.id.tv_name);

		textView2.setText(address);
		textView.setText(name);

		return convertView;
	}



	
}
