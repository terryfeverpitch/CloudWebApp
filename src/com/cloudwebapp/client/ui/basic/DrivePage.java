package com.cloudwebapp.client.ui.basic;

import com.cloudwebapp.client.clouddrive.UploadFilePanel;
import com.cloudwebapp.shared.AccountDTO;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class DrivePage extends FlexTable {
	private static UploadFilePanel uploadFilePanel;
	
	public DrivePage() {
//		uploadFilePanel = new UploadFilePanel();
//		setWidget(0, 0, uploadFilePanel);
		
		/*Label lblNewLabel = new Label("");
		lblNewLabel.setStyleName("gwt-drivePage-title");
		setWidget(0, 1, lblNewLabel);
		lblNewLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("click");
				uploadFilePanel.triggerFileUpload();
			}	
		});*/
		
		Label lblNewLabel_1 = new Label("Name");
		setWidget(1, 0, lblNewLabel_1);
		
		Label lblNewLabel_2 = new Label("Size");
		setWidget(1, 1, lblNewLabel_2);
		
		Label lblNewLabel_3 = new Label("Update Time");
		setWidget(1, 2, lblNewLabel_3);
		getFlexCellFormatter().setColSpan(0, 0, 4);
	}
	
	public void getFileSystem() {
		uploadFilePanel = new UploadFilePanel();
		setWidget(0, 0, uploadFilePanel);
	}
}
