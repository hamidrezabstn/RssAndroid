package com.example.rssdomdemo;

import java.util.ArrayList;
import java.util.zip.Inflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class customeArrayAdapter extends ArrayAdapter<News> {

	private ArrayList<News> items ;
	private Context mainActivity;
	private int layoutResource;
	
	public customeArrayAdapter(Context context, int resource, ArrayList<News> objects) {
		super(context, resource , objects);
		
		this.items = objects;
		this.mainActivity = context;
		this.layoutResource = resource;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		NewsHolder holder = null;
		
		if(rowView == null)
        {
		//TODO tahghigh kon bebin chie!!!!!!!!!!!
		LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		rowView = inflater.inflate(this.layoutResource, parent,false);
		
		holder=new NewsHolder();
		holder.title = (TextView) rowView.findViewById(R.id.lblTitle);
		holder.desc = (TextView) rowView.findViewById(R.id.lblDesc);
		holder.pubDate = (TextView) rowView.findViewById(R.id.lblDate);
		 rowView.setTag(holder);
		 //TODO vaghti else ro ezafe kardam dorost shod moshkelesh ! vali cheraaaaaaa???????????
        }else{
        	holder = (NewsHolder)rowView.getTag();
        }
			News singleItem = items.get(position);
			holder.title.setText(singleItem.getTitle());
			holder.desc.setText(singleItem.getDesc()+"...");
			holder.pubDate.setText(singleItem.getPubDate());
		
		
		return rowView;
}
		static class NewsHolder{
			TextView title;
			TextView desc;
			TextView pubDate;
			
			
		}
	}
	

	


