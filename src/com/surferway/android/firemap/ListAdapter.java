package com.surferway.android.firemap;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<SMSData> {

	// List context
    private final Context context;
    // List values
    private final List<SMSData> smsList;
	
	public ListAdapter(Context context, List<SMSData> smsList) {
		super(context, R.layout.activity_main, smsList);
		this.context = context;
		this.smsList = smsList;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
        View rowView = inflater.inflate(R.layout.activity_main, parent, false);
        rowView.setBackgroundColor(Color.WHITE);
           
        TextView senderNumber = (TextView) rowView.findViewById(R.id.smsNumberText);
        senderNumber.setText(smsList.get(position).getDay() + "\n " + smsList.get(position).getCallType() + "\n " + smsList.get(position).getAddress());

        return rowView;
	}	
	

	
	
}
