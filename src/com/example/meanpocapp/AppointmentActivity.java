package com.example.meanpocapp;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AppointmentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appointment);
	}

	public void createAppointment(View view) {
		RestAdapter restAdapter = new RestAdapter.Builder().setServer(
				Config.URL).build();

		AppointmentService service = restAdapter
				.create(AppointmentService.class);
		Appointment appointment = new Appointment(
				((EditText) findViewById(R.id.create_appointment_text))
						.getText().toString());
		service.createAppointment(appointment, new Callback<List<Appointment>>() {

			@Override
			public void success(List<Appointment> arg0, Response arg1) {
				Toast.makeText(AppointmentActivity.this,
						"Created appointment", Toast.LENGTH_SHORT)
						.show();
				((EditText) findViewById(R.id.create_appointment_text)).setText("");
			}

			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(AppointmentActivity.this,
						"Creating appointment failed", Toast.LENGTH_SHORT).show();
				arg0.printStackTrace();
			}
		});
	}

}
