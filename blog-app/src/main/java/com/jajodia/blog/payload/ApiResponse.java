package com.jajodia.blog.payload;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
	
	private String message;
	private boolean success;
	private String timeOfOccurence;
	
	
	public String getTimeOfOccurence() {
		return timeOfOccurence;
	}
	public void setTimeOfOccurence(String timeOfException) {
		this.timeOfOccurence = timeOfException;
	}
	public ApiResponse() {
		super();
	}
	public ApiResponse(String message, boolean success) {
		super();
		
		this.message = message;
		this.success = success;
		this.timeOfOccurence = ApiResponse.getCurrentTimeUsingDate();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public static String getCurrentTimeUsingDate() {
		  Date dNow = new Date( );
	      SimpleDateFormat ft =  new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
	      System.out.println("Current Date: " + ft.format(dNow));
	      return ft.format(dNow);
	}
	
//	public static void getCurrentDate() {
//		Date date = new Date();
//		
//	}
	

}
