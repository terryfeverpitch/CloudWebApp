package com.cloudwebapp.client.ui.admin;

import java.util.ArrayList;

import com.cloudwebapp.client.CloudWebApp;
import com.cloudwebapp.client.ui.CenterDisplay;
import com.cloudwebapp.server.Account;
import com.cloudwebapp.shared.AccountDTO;
import com.cloudwebapp.shared.MessageCode;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.Window;

public class DatastoreViewerPage extends Grid {
	private ArrayList<AccountDTO> accountList;
	public DatastoreViewerPage(final int x, final int y, final ArrayList<AccountDTO> in) {
		super(x, y);
		this.accountList = in;
		build();
		buildDataRows();
//		CenterDisplay parent = (CenterDisplay) this.getParent();
//		parent.changeTo(CenterDisplay.DATASTOREVIEWER_INDEX);
	}
	
	public void build() {
//		deckPanel.add(dataStorePage);
		Label lbl_column_delete = new Label("DELETE");
		this.setWidget(0, 0, lbl_column_delete);
		
		Label lbl_column_block = new Label("BLOCK");
		this.setWidget(0, 1, lbl_column_block);
		
		Label lbl_column_username = new Label("USERNAME");
		this.setWidget(0, 2, lbl_column_username);
		
		Label lbl_column_passwrod = new Label("PASSWORD");
		this.setWidget(0, 3, lbl_column_passwrod);
		
		Label lbl_column_name = new Label("name");
		this.setWidget(0, 4, lbl_column_name);
		
		Label lbl_column_address = new Label("address");
		this.setWidget(0, 5, lbl_column_address);
		
		Label lbl_column_birthdate = new Label("birthdate");
		this.setWidget(0, 6, lbl_column_birthdate);
		
		Label lbl_column_email = new Label("email");
		this.setWidget(0, 7, lbl_column_email);
		
		Label lbl_column_type = new Label("type");
		this.setWidget(0, 8, lbl_column_type);
		
		Label lbl_column_signUpDate = new Label("signUpDate");
		this.setWidget(0, 9, lbl_column_signUpDate);

        for(int j = 0; j < this.getCellCount(0); j++)
        	this.getCellFormatter().setStyleName(0, j, "datastore-title");
	}
	
	public void setAccountList(ArrayList<AccountDTO> in) {
		accountList = in;
	}
	
	public DatastoreRow buildDataRows() {
//		row = null;
		DatastoreRow[] rows = new DatastoreRow[accountList.size() + 1];
//		if(dataStorePage != null) {
//			dataStorePage.removeFromParent();
//			dataStorePage = null;
//		}
//		buildDataStorePage(account.size() + 1);
		int i = 0;
		for(AccountDTO ac : accountList) {
			rows[i] = new DatastoreRow(ac);
			i++;
			if(ac.getType() == Account.ACCOUNT_TYPE_USER) {
				this.setWidget(i, 0, rows[i - 1].btn_delete);
				this.setWidget(i, 1, rows[i - 1].btn_block);
			}
			this.setWidget(i, 2, rows[i - 1].username);
			this.setWidget(i, 3, rows[i - 1].password);
			this.setWidget(i, 4, rows[i - 1].name);
			this.setWidget(i, 5, rows[i - 1].address);
			this.setWidget(i, 6, rows[i - 1].birthdate);
			this.setWidget(i, 7, rows[i - 1].email);
			this.setWidget(i, 8, rows[i - 1].type);
			this.setWidget(i, 9, rows[i - 1].signUpDate);
		}
		return null;
	}

	public static class DatastoreRow implements ClickHandler {
		protected Button btn_delete = new Button("delete");
		protected Button btn_block = new Button("block");
		protected Label username;
		protected Label password;
		protected Label name;
		protected Label address;
		protected Label birthdate;
		protected Label email;
		protected Label type;
		protected Label signUpDate;
		private AccountDTO account;
		
		public DatastoreRow(AccountDTO row_account) {
			account = row_account;
			username = new Label(account.getUsername());
			password = new Label(account.getPassword());
			name = new Label(account.getName());
			address = new Label(account.getAddress());
			birthdate = new Label(account.getBirthDate());
			email = new Label(account.getEmail());
			
			if(account.getType() == Account.ACCOUNT_TYPE_USER)
				type = new Label("USER");
			else
				type = new Label("ADMIN");
			
			if(!account.isAvailable())
				btn_block.setText("free");
			signUpDate = new Label(account.getSignUpDate());
			
			btn_delete.addClickHandler(this);
			btn_block.addClickHandler(this);
			btn_delete.setStyleName("datastore-button");
			btn_block.setStyleName("datastore-button");
			username.setStyleName("datastore-row");
			password.setStyleName("datastore-row");
			name.setStyleName("datastore-row");
			address.setStyleName("datastore-row");
			birthdate.setStyleName("datastore-row");
			email.setStyleName("datastore-row");
			type.setStyleName("datastore-row");
			signUpDate.setStyleName("datastore-row");
		}
				
		@Override
		public void onClick(ClickEvent event) {
			Button src = (Button) event.getSource();
			if(src == btn_delete) {
				String msg = "Are you sure you want to delete " + account.getUsername() + "?\n" + "It can't be restored.";
				if(!Window.confirm(msg))
					return;
				CloudWebApp.accountService.deleteAccount(account.getUsername(), new AsyncCallback<Integer>() {
					@Override
					public void onFailure(Throwable caught) {	
						Window.alert("failure");
					}
					@Override
					public void onSuccess(Integer result) {
						if(result == MessageCode.DELETE_SUCCESS) {
							btn_delete.setEnabled(false);
							btn_block.setEnabled(false);
						}
						else if(result == MessageCode.ACCOUNT_NOT_FOUND)
							Window.alert("account not found");
					}
				});
			}
			else if(src == btn_block) {
				boolean available = true;
				if(btn_block.getText().equals("block"))
					available = false;
				else if(btn_block.getText().equals("free"))
					available = true;
				CloudWebApp.accountService.blockOrFreeAccount(account.getUsername(), available, new AsyncCallback<Integer>() {
					@Override
					public void onFailure(Throwable caught) {	
						Window.alert("failure");
					}
					@Override
					public void onSuccess(Integer result) {
						if(result == MessageCode.AVAILABLE_SUCCESS_FREE)
							btn_block.setText("block");
						else if(result == MessageCode.AVAILABLE_SUCCESS_BLOCK)
							btn_block.setText("free");
						else if(result == MessageCode.ACCOUNT_NOT_FOUND)
							Window.alert("account not found");
					}
				});
			}
		}
	}
}
