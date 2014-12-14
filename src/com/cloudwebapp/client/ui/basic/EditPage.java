package com.cloudwebapp.client.ui.basic;

import com.cloudwebapp.client.CloudWebApp;
import com.cloudwebapp.client.ui.MainWindow;
import com.cloudwebapp.client.ui.SignupPage;
import com.cloudwebapp.server.Account;
import com.cloudwebapp.shared.AccountDTO;
import com.cloudwebapp.shared.FieldVerifier;
import com.cloudwebapp.shared.MessageCode;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;

public class EditPage extends Grid implements ClickHandler {
	public static Label editPage_lbl_username;
	public static Label editPage_lbl_password;
	public static Label editPage_lbl_signUpDate;
	public static Label editPage_lbl_name;
	public static Label editPage_lbl_address;
	public static Label editPage_lbl_birthdate;
	public static Label editPage_lbl_email;
	public static Button editPage_btn_edit;
	
	public static SignupPage signPage;
	
	private static DialogBox dialog;
	
	public EditPage() {
		super(8, 2);
		this.setStyleName("grid");
		
		Label lbl_title_username = new Label("username");
		this.setWidget(0, 0, lbl_title_username);

		editPage_lbl_username = new Label("New label");
		this.setWidget(0, 1, editPage_lbl_username);
		
		Label lbl_title_password = new Label("passoword");
		this.setWidget(1, 0, lbl_title_password);
		
		editPage_lbl_password = new Label("New label");
		this.setWidget(1, 1, editPage_lbl_password);
		
		Label lbl_title_name = new Label("name");
		this.setWidget(2, 0, lbl_title_name);
		
		editPage_lbl_name = new Label("New label");
		this.setWidget(2, 1, editPage_lbl_name);
		
		Label lbl_title_address = new Label("address");
		this.setWidget(3, 0, lbl_title_address);
		
		editPage_lbl_address = new Label("New label");
		this.setWidget(3, 1, editPage_lbl_address);
		
		Label lbl_title_birthdate= new Label("birth date");
		this.setWidget(4, 0, lbl_title_birthdate);
		
		editPage_lbl_birthdate = new Label("New label");
		this.setWidget(4, 1, editPage_lbl_birthdate);
		
		Label lbl_title_email = new Label("email");
		this.setWidget(5, 0, lbl_title_email);
		
		editPage_lbl_email = new Label("New label");
		this.setWidget(5, 1, editPage_lbl_email);
		
		Label lbl_title_signUpDate = new Label("sign up date");
		this.setWidget(6, 0, lbl_title_signUpDate);
		
		editPage_lbl_signUpDate = new Label("New label");
		this.setWidget(6, 1, editPage_lbl_signUpDate);
		
		editPage_btn_edit = new Button("Edit");		
		editPage_btn_edit.setStyleName("gwt-button");
		
		this.setWidget(7, 0, editPage_btn_edit);
		
		for(int i = 0; i < this.getRowCount(); i++) {
            for(int j = 0; j < this.getCellCount(i); j++) {
                if((j % 2) == 0)
                	this.getCellFormatter().setStyleName(i, j, "tableCell-even");
                else
                	this.getCellFormatter().setStyleName(i, j, "tableCell-odd");
            }
        }
		editPage_btn_edit.addClickHandler(this);
	}
	
	public void setAccountInfo(AccountDTO account) {
		editPage_lbl_username.setText(account.getUsername());
		editPage_lbl_password.setText(FieldVerifier.hidePassword(account.getPassword()));
		editPage_lbl_name.setText(account.getName());
		editPage_lbl_address.setText(account.getAddress());
		editPage_lbl_birthdate.setText(account.getBirthDate());
		editPage_lbl_email.setText(account.getEmail());
		editPage_lbl_signUpDate.setText(account.getSignUpDate());
	}
	
	@Override
	public void onClick(ClickEvent event) {
		Button src = (Button) event.getSource();
		if(src == editPage_btn_edit) {
			AccountDTO account = MainWindow.getLoginAccount();
			if(account.getType() == Account.ACCOUNT_TYPE_ADMIN) {
				String pw = Window.prompt("ADMIN PASSWORD : ", "");
				if(!account.getPassword().equals(pw))
					return;
			}

			signPage = new SignupPage(SignupPage.EDIT_MODE, account.getUsername(), account.getPassword());
			String[] restore = signPage.getRestoreValue();
			signPage.tb_people_name.setText(restore[2] = account.getName());
			signPage.tb_people_address.setText(restore[3] = account.getAddress());
			signPage.db_people_birthDate.getTextBox().setText(restore[4] = account.getBirthDate());
			signPage.tb_mail.setText(restore[5] = account.getEmail());
			signPage.btn_add.addClickHandler(EditPage.this);
			signPage.btn_back.addClickHandler(EditPage.this);
			signPage.btn_restore.addClickHandler(EditPage.this);
			if(account.getType() == Account.ACCOUNT_TYPE_ADMIN) {
				signPage.tb_people_name.setEnabled(false);
				signPage.tb_people_address.setEnabled(false);
				signPage.db_people_birthDate.setEnabled(false);
				signPage.tb_mail.setEnabled(false);
			}
			dialog = new DialogBox();
			dialog.setWidget(signPage);
			dialog.center();
			dialog.show();
		}
		
		if(signPage == null)
			return;
		else if(src == signPage.btn_add) {
			if(!Window.confirm("do you really want to update your account?"))
				return;
			dialog.setVisible(false);
			if(!FieldVerifier.isValid(signPage.tb_username.getText(), signPage.ptb_password.getText())) {
				Window.alert("your username or password length is too short");
				dialog.setVisible(true);
				return;
			}
			MainWindow.getLoginAccount().updateValue(signPage.tb_username.getText(), signPage.ptb_password.getText(), 
					signPage.tb_people_name.getText(), signPage.tb_people_address.getText(), 
					signPage.db_people_birthDate.getTextBox().getText(), signPage.tb_mail.getText());
			CloudWebApp.accountService.updateAccount(MainWindow.getLoginAccount(), new AsyncCallback<Integer>() {
				@Override
				public void onFailure(Throwable caught) {	
					Window.alert("update failure");
					closeDialogBox();
				}
				@Override
				public void onSuccess(Integer result) {
					if(result == MessageCode.UPDATE_SUCCESS)
						Window.alert("update success");
					else if(result == MessageCode.ACCOUNT_NOT_FOUND)
						Window.alert("account not found");
					closeDialogBox();
				}
			});
		}
		else if(src == signPage.btn_back) {
			closeDialogBox();
		}
		else if(src == signPage.btn_restore) {
			String[] restore = signPage.getRestoreValue();
			signPage.ptb_password.setText(restore[1]);
			signPage.tb_people_name.setText(restore[2]);
			signPage.tb_people_address.setText(restore[3]);
			signPage.db_people_birthDate.getTextBox().setText(restore[4]);
			signPage.tb_mail.setText(restore[5]);
		}
		
	}
	
	public void closeDialogBox() {
		dialog.remove(signPage);
		signPage = null;
		dialog.hide();
		dialog = null;
	}
}
