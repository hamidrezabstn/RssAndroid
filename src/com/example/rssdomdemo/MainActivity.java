package com.example.rssdomdemo;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new NewsTask().execute(null,null);
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
    private class NewsTask extends AsyncTask<String, String, ArrayList<News>>{
    	@Override
    	protected ArrayList<News> doInBackground(String... params) {
    		try{
    	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	        factory.setNamespaceAware(true);
    	        DocumentBuilder dombuilder = factory.newDocumentBuilder();    	 
    	        org.w3c.dom.Document w3cDocument = dombuilder.parse("http://isna.ir/fa/mainPage/feed");
    	        DOMBuilder jdomBuilder = new DOMBuilder();
    	        Document jdomDocument = jdomBuilder.build(w3cDocument);
    	        //return jdomDocument.getRootElement().getName();
    	        List<Element> items=jdomDocument.getRootElement().getChild("channel").getChildren("item");
    	        //return items[0].getClass().toString();
    	        ArrayList<News>result=new ArrayList<News>();
    	        for(int i=0;i<items.size();i++){
    	        	News newsItems = new News();
    	        	newsItems.setTitle(items.get(i).getChild("title").getValue());
    	        	newsItems.setDesc(items.get(i).getChild("description").getValue());
    	        	newsItems.setLink(items.get(i).getChild("link").getValue());
    	        	newsItems.setPubDate(items.get(i).getChild("pubDate").getValue());
    	        	
    	        	publishProgress(String.valueOf((100/(items.size()-i))));
    	        	Thread.sleep(10);
    	        	
    	        	result.add(newsItems);
    	        }
    	        return result;
    		}
    		catch(Exception e){
    			return null;
    		}
    	}
    	@Override
    	protected void onPostExecute(final ArrayList<News> result) {
    		super.onPostExecute(result);    		
    		TextView percent1 = (TextView)findViewById(R.id.txtPercent);
    		ProgressBar bar1 = (ProgressBar)findViewById(R.id.barRemain);
    		
    		percent1.setVisibility(View.INVISIBLE);
    		bar1.setVisibility(View.INVISIBLE);
    		
    		ListView newsList = (ListView)findViewById(R.id.lsNews);
    		
    		customeArrayAdapter adapter = new customeArrayAdapter(getBaseContext(), R.layout.customelayout,result);
    		
    		newsList.setAdapter(adapter);
    		newsList.setVisibility(View.VISIBLE);
    		
    		newsList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {					
					Intent webActivity = new Intent(getBaseContext(),WebActivity.class);
					webActivity.putExtra("url", result.get(position).getLink());
					startActivity(webActivity);					
					
					
				}
			});
    		
    		
    	}
    	
    	@Override
    	protected void onProgressUpdate(String... values) {
    		TextView percent = (TextView)findViewById(R.id.txtPercent);
    		ProgressBar bar = (ProgressBar)findViewById(R.id.barRemain);
    		
    		percent.setText(values[0]);
    		bar.setProgress(Integer.valueOf(values[0]));
    		
    	}
    	
    }
}
