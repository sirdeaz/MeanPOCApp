package com.example.meanpocapp;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private List<String> values = new ArrayList<String>();
	private BaseAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		final ListView listview = (ListView) findViewById(R.id.listview);
		listview.setAdapter(adapter);

		LoadFeedData feed = new LoadFeedData(values, adapter);
		feed.execute();
	}
	
	@Override
	protected void onResume() {
		LoadFeedData feed = new LoadFeedData(values, adapter);
		feed.execute();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_create:
			openCreate();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void openCreate() {
		Intent intent = new Intent(this, AppointmentActivity.class);
		startActivity(intent);
	}
	
	public class LoadFeedData extends AsyncTask<Void, Void, List<String>> {

		private List<String> values;
		private BaseAdapter adapter;
		private AppointmentService appointmentService;

		public LoadFeedData(List<String> values, BaseAdapter adapter) {
			this.values = values;
			this.adapter = adapter;
			
			RestAdapter restAdapter = new RestAdapter.Builder().setServer(
					Config.URL).build();
			appointmentService = restAdapter
					.create(AppointmentService.class);
		}

		@Override
		protected List<String> doInBackground(Void... params) {
			List<String> results = new ArrayList<String>();

			List<Appointment> appointments = appointmentService.listAppointments();
			
			for (Appointment appointment: appointments) {
				results.add(appointment.getText());
			}
			
			return results;
		}

		@Override
		protected void onPostExecute(List<String> values) {
			this.values.clear();
			this.values.addAll(values);
			adapter.notifyDataSetInvalidated();
		}
	}

}