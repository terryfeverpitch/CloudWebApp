package com.cloudwebapp.client.clouddrive;

import com.cloudwebapp.client.service.GetUploadUrlClient;
import com.cloudwebapp.client.service.GetUploadUrlClientAsync;
import com.cloudwebapp.client.ui.MainWindow;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UploadFilePanel extends FormPanel {
	private String uploadServletUrl = "/cloudwebapp/uploadServlet";
	private String uploadUrl;
	private FileUpload fileUpload;
	private Button btnOK;
	private Hidden hdnAuthor; 
	private Hidden hdnParent;
	private Label lblMessage;	
	private Button btnClear;
	
	public static GetUploadUrlClientAsync getUploadUrlClient = GWT.create(GetUploadUrlClient.class);
	private Button btnNewButton;
	private Label lbl_filename;
	
	public UploadFilePanel() {
		setWidth("100%");	
		VerticalPanel vPanel= new VerticalPanel();
		this.setWidget(vPanel);
		vPanel.setWidth("100%");
		
		fileUpload = new FileUpload();
		vPanel.add(fileUpload);
		fileUpload.setName("file0");
		fileUpload.setWidth("100%");
		fileUpload.setVisible(false);
		
		fileUpload.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				genUploadUrl();
				if(fileUpload.getFilename() == null)
					lbl_filename.setText("no file");
				else
					lbl_filename.setText(fileUpload.getFilename());
			}
		});
		
		hdnAuthor = new Hidden("Author");
		hdnAuthor.setValue(MainWindow.getLoginAccount().getUsername());
		vPanel.add(hdnAuthor);
		
		hdnParent = new Hidden("Parent");
		hdnParent.setValue(MainWindow.getFilePathStack().currentPath().folderId.toString());
		vPanel.add(hdnParent);

		lblMessage = new Label("");
		vPanel.add(lblMessage);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setStyleName("gwt-drivePage-uploadpanel");
		vPanel.add(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		btnOK = new Button("New button");
		btnOK.setStyleName("gwt-drivePage-button");
		btnOK.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String file = fileUpload.getFilename().trim();
				if(file.equals("")) {
					Window.alert("no upload file specified !");
				}
				else {
					if(uploadUrl != null){
						lblMessage.setText("uploading...");
						UploadFilePanel.this.setAction(uploadUrl);
						UploadFilePanel.this.submit();
						UploadFilePanel.this.reset();	
					} else {
						
					}
				}
			}
		});
		btnOK.setText("upload");
		horizontalPanel.add(btnOK);
		horizontalPanel.setCellHorizontalAlignment(btnOK, HasHorizontalAlignment.ALIGN_CENTER);
		
		btnClear = new Button("clear");
		btnClear.setStyleName("gwt-drivePage-button");
		btnClear.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				lblMessage.setText("");
				lbl_filename.setText("no file");
				MainWindow.refresh();
			}
		});
		horizontalPanel.add(btnClear);
		horizontalPanel.setCellHorizontalAlignment(btnClear, HasHorizontalAlignment.ALIGN_CENTER);
		
		btnNewButton = new Button("Choose...");
		btnNewButton.setText("Choose File");
		btnNewButton.setStyleName("gwt-drivePage-button");
		horizontalPanel.add(btnNewButton);
		btnNewButton.setWidth("136px");
		btnNewButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				triggerFileUpload();
			}
		});
		
		lbl_filename = new Label("no file");
		lbl_filename.setStyleName("gwt-drivePage-label-noborder");
		lbl_filename.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				triggerFileUpload();
			}
		});
		horizontalPanel.add(lbl_filename);
		lbl_filename.setWidth("100%");
		horizontalPanel.setCellVerticalAlignment(lbl_filename, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHeight(lbl_filename, "100%");
		horizontalPanel.setCellWidth(lbl_filename, "100%");
						
		this.setEncoding(FormPanel.ENCODING_MULTIPART);
	    this.setMethod(FormPanel.METHOD_POST);
	    
	    this.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				lblMessage.setText("");
				lbl_filename.setText("no file");
				MainWindow.refresh();
			}
		});
	}	
	
	public void genUploadUrl() {
		getUploadUrlClient.getUploadUrl(uploadServletUrl, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("on failure");
				uploadUrl = null;
			}
			@Override
			public void onSuccess(String result) {
				uploadUrl = result;
			}
		});
	}
	
	public void triggerFileUpload() {
		fileUpload.getElement().<ButtonElement>cast().click();
	}
}