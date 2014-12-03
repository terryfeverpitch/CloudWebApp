package com.cloudwebapp.client.ui.basic;

import java.util.Date;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Timer;

public class HomePage extends Label {
	public static HomePage self; // main body
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
		
		self = this;
	}
	
//	public static Widget build() {
//		lbl_timer = new Label("Current Time");
//		lbl_timer.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//		lbl_timer.setDirectionEstimator(true);
//		deckPanel.add(lbl_timer);
		
//		clock = new Timer() {
//			public void run() {
//				Date d = new Date();
//	            lbl_timer.setText(d.toString());
//				schedule(1000);
//			}
//		};
//		clock.schedule(1000);
		
//		return HomePage.lbl_timer;
//	}
	
	public static Widget getMainBody() {
		return HomePage.self;
	}
}
