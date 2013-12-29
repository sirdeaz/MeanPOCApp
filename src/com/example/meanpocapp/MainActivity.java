package com.example.meanpocapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.meanpocapp.RestClient.RequestMethod;

public class MainActivity extends Activity {
	
	private List<String> values = new ArrayList<String>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		BaseAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
		final ListView listview = (ListView) findViewById(R.id.listview);
		listview.setAdapter(adapter);
		
		LoadFeedData feed = new LoadFeedData(values, adapter);
		feed.execute();
	}

	public class LoadFeedData extends AsyncTask<Void, Void, List<String>> {

		private final static String URL = "http://192.168.0.249:8080/api/appointments"; 

		private List<String> values;
		private BaseAdapter adapter;
		
		public LoadFeedData(List<String> values, BaseAdapter adapter) {
			this.values = values;
			this.adapter = adapter;
		}

		

		@Override
		protected List<String> doInBackground(Void... params) {
			RestClient client = new RestClient(URL);
			List<String> results = new ArrayList<String>();
			
			try {
				client.Execute(RequestMethod.GET);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				JSONArray jsonResults = new JSONArray(client.getResponse());
				for (int i = 0; i < jsonResults.length(); i++) {
					results.add(jsonResults.getJSONObject(i).getString("text"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return results;
		}

		@Override
		protected void onPostExecute(List<String> values) {
			this.values.clear();
			this.values.addAll(values);
			System.err.println("size: " + this.values.size());
			adapter.notifyDataSetInvalidated();
			System.err.println("size: " + adapter.getCount());
		}
	}

}