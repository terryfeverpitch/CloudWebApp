package com.cloudwebapp.client.ui;

import java.util.Date;

import com.cloudwebapp.shared.FieldVerifier;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class SignupPage extends FlexTable {
	public final static int SIGNUP_MODE = 0;
	public final static int EDIT_MODE = 1;

	public TextBox tb_username = new TextBox();
	public PasswordTextBox ptb_password = new PasswordTextBox();
	public TextBox tb_people_name = new TextBox();
	public TextBox tb_people_address = new TextBox();
	public DateBox db_people_birthDate = new DateBox();
	public TextBox tb_mail = new TextBox();
	public Button btn_back = new Button("back");
	public Button btn_add = new Button("new");
	public Button btn_restore;

	protected String[] restore = new String[6];
	private Boolean[] canAdd = new Boolean[6];
	private FieldValueChangeHandler fieldValueChangeHandler = new FieldValueChangeHandler();
	private DateValueChangeHandler dateValueChangeHandler = new DateValueChangeHandler();
	
	public SignupPage(int mode, String un, String pw) {
		if(mode == SIGNUP_MODE)
			buildSignupMode(un, pw);
		else if(mode == EDIT_MODE)
			buildEditMode(un, pw);
		
		for(int i = 0; i < canAdd.length; i++)
			canAdd[i] = true;
		
		db_people_birthDate.setFireNullValues(true);
		db_people_birthDate.setFormat(new DateBox.DefaultFormat (DateTimeFormat.getFormat("yyyy/MM/dd"))); 
		tb_username.addValueChangeHandler(fieldValueChangeHandler);
		tb_people_name.addValueChangeHandler(fieldValueChangeHandler);
		tb_people_address.addValueChangeHandler(fieldValueChangeHandler);
		db_people_birthDate.addValueChangeHandler(dateValueChangeHandler);
		tb_mail.addValueChangeHandler(fieldValueChangeHandler);
	}
	
	public void buildSignupMode(String un, String pw) {
		Label lbl_title = new Label("Sign up a new account");
		lbl_title.setStyleName("gwt-signupPage-label");
		setWidget(0, 0, lbl_title);
		lbl_title.setSize("100%", "100%");
		
		Label lb_necessarily = new Label("necessarily");
		lb_necessarily.setStyleName("gwt-signupPage-inline");
		setWidget(1, 0, lb_necessarily);
		
		Label lbl_username = new Label("USERNAME*");
		lbl_username.setStyleName("gwt-signupPage-label");
		setWidget(2, 0, lbl_username);	
		
		tb_username.setStyleName("gwt-signupPage-input");
		tb_username.setText(un);
		setWidget(2, 1, tb_username);
		
		Label lbl_password = new Label("PASSWORD*");
		lbl_password.setStyleName("gwt-signupPage-label");
		setWidget(3, 0, lbl_password);
				
		ptb_password.setStyleName("gwt-signupPage-input");
		ptb_password.setText(pw);
		setWidget(3, 1, ptb_password);
		getFlexCellFormatter().setColSpan(0, 0, 2);
		
		Label inlb_optional = new Label("optional");
		inlb_optional.setStyleName("gwt-signupPage-inline");
		setWidget(4, 0, inlb_optional);
		
		Label lbl_name = new Label("name");
		lbl_name.setStyleName("gwt-signupPage-label");
		setWidget(5, 0, lbl_name);
		
		tb_people_name.setStyleName("gwt-signupPage-input");
		setWidget(5, 1, tb_people_name);
		
		Label lbl_address = new Label("address");
		lbl_address.setStyleName("gwt-signupPage-label");
		setWidget(6, 0, lbl_address);
			
		tb_people_address.setStyleName("gwt-signupPage-input");
		setWidget(6, 1, tb_people_address);
		
		Label lbl_birthDate = new Label("birthDate");
		lbl_birthDate.setStyleName("gwt-signupPage-label");
		setWidget(7, 0, lbl_birthDate);	
		
		db_people_birthDate.setStyleName("gwt-signupPage-input");
		setWidget(7, 1, db_people_birthDate);
		
		Label lbl_email = new Label("email");
		lbl_email.setStyleName("gwt-signupPage-label");
		setWidget(8, 0, lbl_email);
		
		tb_mail.setStyleName("gwt-signupPage-input");
		setWidget(8, 1, tb_mail);
			
		btn_back.setStyleName("gwt-loginPage-button");
		setWidget(10, 0, btn_back);
		
		btn_add.setStyleName("gwt-loginPage-button");
		setWidget(10, 1, btn_add);
		
		getFlexCellFormatter().setColSpan(4, 0, 2);	
		getFlexCellFormatter().setColSpan(1, 0, 2);
		getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_CENTER);
		getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		getCellFormatter().setHorizontalAlignment(10, 0, HasHorizontalAlignment.ALIGN_CENTER);
		getCellFormatter().setHorizontalAlignment(10, 1, HasHorizontalAlignment.ALIGN_CENTER);
		getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
		getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_LEFT);
		getCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_LEFT);
		getCellFormatter().setHorizontalAlignment(7, 0, HasHorizontalAlignment.ALIGN_LEFT);
		getCellFormatter().setHorizontalAlignment(8, 0, HasHorizontalAlignment.ALIGN_LEFT);
	}
	
	public void buildEditMode(String un, String pw) {
		restore[0] = un;
		restore[1] = pw;
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSize("100%", "100%");
		setWidget(0, 0, horizontalPanel);
		Label lbl_title = new Label("Edit your personal information.");	
		lbl_title.setStyleName("gwt-signupPage-label");
		lbl_title.setSize("100%", "100%");
		horizontalPanel.add(lbl_title);
		
		btn_restore = new Button("restore");
		btn_restore.setStyleName("gwt-loginPage-button");
		horizontalPanel.add(btn_restore);
		horizontalPanel.setCellVerticalAlignment(btn_restore, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(btn_restore, HasHorizontalAlignment.ALIGN_CENTER);
		
		Label lb_necessarily = new Label("necessarily");
		lb_necessarily.setStyleName("gwt-signupPage-inline");
		setWidget(1, 0, lb_necessarily);
		
		Label lbl_username = new Label("USERNAME*");
		lbl_username.setStyleName("gwt-signupPage-label");
		setWidget(2, 0, lbl_username);	
		
		tb_username.setStyleName("gwt-signupPage-input");
		tb_username.setText(un);
		tb_username.setEnabled(false);
		setWidget(2, 1, tb_username);
		
		Label lbl_password = new Label("PASSWORD*");
		lbl_password.setStyleName("gwt-signupPage-label");
		setWidget(3, 0, lbl_password);
				
		ptb_password.setStyleName("gwt-signupPage-input");
		ptb_password.setText(pw);
		setWidget(3, 1, ptb_password);
		
		Label inlb_optional = new Label("optional");
		inlb_optional.setStyleName("gwt-signupPage-inline");
		setWidget(4, 0, inlb_optional);
		
		Label lbl_name = new Label("name");
		lbl_name.setStyleName("gwt-signupPage-label");
		setWidget(5, 0, lbl_name);
		
		tb_people_name.setStyleName("gwt-signupPage-input");
		setWidget(5, 1, tb_people_name);
		
		Label lbl_address = new Label("address");
		lbl_address.setStyleName("gwt-signupPage-label");
		setWidget(6, 0, lbl_address);
			
		tb_people_address.setStyleName("gwt-signupPage-input");
		setWidget(6, 1, tb_people_address);
		
		Label lbl_birthDate = new Label("birthDate");
		lbl_birthDate.setStyleName("gwt-signupPage-label");
		setWidget(7, 0, lbl_birthDate);	
		
		db_people_birthDate.setStyleName("gwt-signupPage-input");
		setWidget(7, 1, db_people_birthDate);
		
		Label lbl_email = new Label("email");
		lbl_email.setStyleName("gwt-signupPage-label");
		setWidget(8, 0, lbl_email);
		
		tb_mail.setStyleName("gwt-signupPage-input");
		setWidget(8, 1, tb_mail);
			
		btn_back.setStyleName("gwt-loginPage-button");
		setWidget(10, 0, btn_back);
		
		btn_add.setText("Update");
		btn_add.setStyleName("gwt-loginPage-button");
		setWidget(10, 1, btn_add);
		
		getFlexCellFormatter().setColSpan(4, 0, 2);	
		getFlexCellFormatter().setColSpan(1, 0, 2);
		getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_CENTER);
		getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		getCellFormatter().setHorizontalAlignment(10, 0, HasHorizontalAlignment.ALIGN_CENTER);
		getCellFormatter().setHorizontalAlignment(10, 1, HasHorizontalAlignment.ALIGN_CENTER);
		getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
		getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_LEFT);
		getCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_LEFT);
		getCellFormatter().setHorizontalAlignment(7, 0, HasHorizontalAlignment.ALIGN_LEFT);
		getCellFormatter().setHorizontalAlignment(8, 0, HasHorizontalAlignment.ALIGN_LEFT);
		getFlexCellFormatter().setColSpan(0, 0, 2);
	}
	
	public String[] getRestoreValue() {
		return restore;
	}
	
	private class FieldValueChangeHandler implements ValueChangeHandler<String> {
		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			TextBox textBox = (TextBox) event.getSource();
			
			boolean isCorrect = true;
			
			if(textBox == tb_username) {
				if(!FieldVerifier.usernameFormatCheck(event.getValue()))
					isCorrect = false;
				canAdd[0] = isCorrect;
			}
			else if(textBox == tb_people_name) {
				if(!FieldVerifier.peopleNameFormatCheck(event.getValue()))
					isCorrect = false;
				canAdd[2] = isCorrect;
			}
			else if(textBox == tb_people_address) {
				if(!FieldVerifier.peopleAddressFormatCheck(event.getValue()))
					isCorrect = false;
				canAdd[3] = isCorrect;
			}
			else if(textBox == tb_mail) {
				if(!FieldVerifier.emailFormatCheck(event.getValue()))
					isCorrect = false;
				canAdd[5] = isCorrect;
			}
			
			if(isCorrect == false)
				textBox.addStyleName("gwt-field-error");
			else
				textBox.removeStyleName("gwt-field-error");
			
			check();
		}
	};
	
	private class DateValueChangeHandler implements ValueChangeHandler<Date> {
		@Override
		public void onValueChange(ValueChangeEvent<Date> event) {
			DateBox dateBox = (DateBox) event.getSource();
			boolean isCorrect = true;
			if(dateBox == db_people_birthDate) {
				String dateString = dateBox.getTextBox().getText();
				if(!FieldVerifier.peopleBirthDateFormatCheck(dateString))
					isCorrect = false;
				canAdd[4] = isCorrect;
			}
			
			if(isCorrect == false)
				dateBox.addStyleName("gwt-field-error");
			else
				dateBox.removeStyleName("gwt-field-error");
			
			check();
		}
	}
	
	private void check() {
		for(Boolean t : canAdd) {
			if(t == false) {
				btn_add.setVisible(false);
				return;
			}
		}
		
		btn_add.setVisible(true);
	}
}
