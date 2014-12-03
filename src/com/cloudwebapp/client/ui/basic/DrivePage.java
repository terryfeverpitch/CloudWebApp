package com.cloudwebapp.client.ui.basic;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DrivePage extends FlexTable {
	private static UploadPanel uploadPanel;
	
	public DrivePage() {
		FormPanel uploadPanel = buileFormPanel();
//		final FileUpload fileUpload = new FileUpload();
		setWidget(0, 0, uploadPanel);
		
		Label lblNewLabel = new Label("Delete ");
		lblNewLabel.setStyleName("gwt-drivePage-title");
		setWidget(1, 0, lblNewLabel);
		lblNewLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
//				String msg = uploadPanel.getFilename();
//				Window.alert(msg);
			}	
		});
		
		Label lblNewLabel_1 = new Label("Name");
		setWidget(1, 1, lblNewLabel_1);
		
		Label lblNewLabel_2 = new Label("Size");
		setWidget(1, 2, lblNewLabel_2);
		
		Label lblNewLabel_3 = new Label("Update Time");
		setWidget(1, 3, lblNewLabel_3);
	}
	
	private FormPanel buileFormPanel() {
		final FormPanel form = new FormPanel();
	    form.setAction("/myFormHandler");

	    // Because we're going to add a FileUpload widget, we'll need to set the
	    // form to use the POST method, and multipart MIME encoding.
	    form.setAction("http://www.tutorialspoint.com/gwt/myFormHandler");
	    form.setEncoding(FormPanel.ENCODING_MULTIPART);
	    form.setMethod(FormPanel.METHOD_POST);
	    // Create a panel to hold all of the form widgets.
	    VerticalPanel panel = new VerticalPanel();
	    form.setWidget(panel);
	   
	    // Create a FileUpload widget.
	    final FileUpload upload = new FileUpload();
	    upload.setName("uploadFormElement");
	    panel.add(upload);

	    // Add a 'submit' button.
	    panel.add(new Button("Submit", new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  if (upload.getFilename().length() == 0) {
	               Window.alert("No File Specified!");
	            } else {
	               //submit the form
	               form.submit();			          
	            }
	      }
	    }));

	    // Add an event handler to the form.
	    form.addSubmitHandler(new FormPanel.SubmitHandler() {
	      public void onSubmit(SubmitEvent event) {
	        // This event is fired just before the form is submitted. We can take
	        // this opportunity to perform validation.
	      }
	    });
	    form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
	      public void onSubmitComplete(SubmitCompleteEvent event) {
	        // When the form submission is successfully completed, this event is
	        // fired. Assuming the service returned a response of type text/html,
	        // we can get the result text here (see the FormPanel documentation for
	        // further explanation).
//	    	  Window.alert(upload.getFilename());
	        Window.alert(event.getResults());
	      }
	    });
	    
	    return form;
	}
	
	private class UploadPanel extends FormPanel {
		public UploadPanel() {
			
		}
	}

}
