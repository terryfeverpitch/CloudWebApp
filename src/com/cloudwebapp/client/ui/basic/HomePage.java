package com.cloudwebapp.client.ui.basic;

import java.util.Date;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.Timer;

public class HomePage extends Label {
	public static Timer clock;
	
	public HomePage() {
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setDirectionEstimator(true);
		
		clock = new Timer() {
			public void run() {
				Date d = new Date();
				HomePage.this.setText(d.toString());
				schedule(1000);
			}
		};
		clock.schedule(1000);
	}
}