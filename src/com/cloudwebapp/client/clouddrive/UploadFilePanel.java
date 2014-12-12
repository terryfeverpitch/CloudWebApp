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
import com.google.gwt.user.client.ui.FlexTable;
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
	private FlexTable flexTable;
	private FileUpload fileUpload;
//	private FileUpload fileUpload_1;
//	private FileUpload fileUpload_2;
//	private FileUpload fileUpload_3;
	private Button btnOK;
	private Hidden hdnAuthor; 
//	private Hidden hdnPurchaseRequestId;
//	private Hidden hdnAttachmentType;
	private Label lblMessage;	
	private Button btnClose;
	
	public static GetUploadUrlClientAsync getUploadUrlClient = GWT.create(GetUploadUrlClient.class);
	
	public UploadFilePanel() {		
		VerticalPanel vPanel= new VerticalPanel();
		this.setWidget(vPanel);
		
		flexTable = new FlexTable();
		vPanel.add(flexTable);
		flexTable.setSize("333px", "100%");
		
		Label lblNewLabel = new Label("File");
		lblNewLabel.setWordWrap(false);
		flexTable.setWidget(0, 0, lblNewLabel);
		
		fileUpload = new FileUpload();
		fileUpload.setName("file0");
		flexTable.setWidget(0, 1, fileUpload);
		fileUpload.setWidth("284px");
		
		fileUpload.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				genUploadUrl();
			}
		});
		
//		flexTable.setWidget(1, 0, lblNewLabel_2);
		
//		fileUpload_1 = new FileUpload();
//		fileUpload_1.setName("file1");
//		flexTable.setWidget(1, 1, fileUpload_1);
//		fileUpload_1.setWidth("284px");
		
//		Label lblNewLabel_1 = new Label("File 03");
//		lblNewLabel_1.setWordWrap(false);
//		flexTable.setWidget(2, 0, lblNewLabel_1);
		
//		fileUpload_2 = new FileUpload();
//		fileUpload_2.setName("file2");
//		flexTable.setWidget(2, 1, fileUpload_2);
//		fileUpload_2.setWidth("285px");
		
//		Label lblNewLabel_3 = new Label("File 04");
//		lblNewLabel_3.setWordWrap(false);
//		flexTable.setWidget(3, 0, lblNewLabel_3);
		
//		fileUpload_3 = new FileUpload();
//		fileUpload_3.setName("file3");
//		flexTable.setWidget(3, 1, fileUpload_3);
//		fileUpload_3.setWidth("286px");
		
		hdnAuthor = new Hidden("Author");
		hdnAuthor.setValue(MainWindow.getLoginAccount().getName());
		vPanel.add(hdnAuthor);
//		hdnPurchaseRequestId = new Hidden("purchaseRequestId");
//		vPanel.add(hdnPurchaseRequestId);
		
//		this.hdnAttachmentType= new Hidden("attachmentType");
//		vPanel.add(hdnAttachmentType);
		
		lblMessage = new Label("");
		vPanel.add(lblMessage);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		vPanel.add(horizontalPanel);
		horizontalPanel.setSize("333px", "40px");
		
		btnOK = new Button("New button");
		btnOK.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String file = fileUpload.getFilename().trim();
//				String file1= fileUpload_1.getFilename().trim();
//				String file2= fileUpload_2.getFilename().trim();
//				String file3= fileUpload_3.getFilename().trim();
//				if (file0.equals("") && file1.equals("") && file2.equals("") && file3.equals("")){
//					Window.alert("no upload file specified !");
//				} 
				if(file.equals("")) {
					Window.alert("no upload file specified !");
				}
				else {
					if (uploadUrl!= null){
						Window.alert(uploadUrl + "  , gg");
						lblMessage.setText("uploading...");
						UploadFilePanel.this.setAction(uploadUrl);
						UploadFilePanel.this.submit();
						UploadFilePanel.this.reset();	
					} else {
//						UploadFilePanel.this.hide();
						Window.alert(uploadUrl + " / 11 /");
//						Window.alert("current network is unable to connect to cloud server. \n"
//								+ "Please check your network.");
					}
				}
			}
		});
		btnOK.setText("upload");
		horizontalPanel.add(btnOK);
		horizontalPanel.setCellVerticalAlignment(btnOK, HasVerticalAlignment.ALIGN_BOTTOM);
		horizontalPanel.setCellHorizontalAlignment(btnOK, HasHorizontalAlignment.ALIGN_CENTER);
		
		btnClose = new Button("New button");
		btnClose.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
//				hide();
			}
			
		});
		btnClose.setText("close");
		horizontalPanel.add(btnClose);
		horizontalPanel.setCellVerticalAlignment(btnClose, HasVerticalAlignment.ALIGN_BOTTOM);
		horizontalPanel.setCellHorizontalAlignment(btnClose, HasHorizontalAlignment.ALIGN_CENTER);
						
		this.setEncoding(FormPanel.ENCODING_MULTIPART);
	    this.setMethod(FormPanel.METHOD_POST);
	    
//	    this.setVisible(false);
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
//				Window.alert(result);
				uploadUrl = result;
			}
		});
	}
	
	
	public void triggerFileUpload() {
		
		fileUpload.getElement().<ButtonElement>cast().click();
	}
}
