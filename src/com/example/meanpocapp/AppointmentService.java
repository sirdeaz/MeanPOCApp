package com.example.meanpocapp;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;


public interface AppointmentService {
	@GET("/appointments")
	List<String> listAppointments();
	
	@POST("/appointments")
	void createAppointment(@Body Appointment appointment, Callback<List<Appointment>> cb);
}
