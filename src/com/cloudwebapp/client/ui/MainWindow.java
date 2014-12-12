package com.cloudwebapp.client.ui;

import java.util.ArrayList;
import java.util.Date;

import com.cloudwebapp.client.CloudWebApp;
import com.cloudwebapp.server.Account;
import com.cloudwebapp.shared.AccountDTO;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainWindow extends VerticalPanel {
	// data, objects, variables
	private static DialogBox dialog;
	private static AccountDTO account;
//	private String username;
	private static int IndexOfPage = 0;
	// UI widgets
	// menu bar
	private MenuBar menuBar;
	private MenuItem mntmHome;
	
	private MenuBar menuBar_account;
	private MenuItem mntmName;
	private MenuItem mntmDrive;
	private MenuItem mntmEdit;
	private MenuItem mntmLogOut;
	// center view
	private static CenterDisplay centerDisplay;

	public MainWindow() {
		
	}
	
	public MainWindow(String username) {
		this.setSize("100%", "100%");
		
		CloudWebApp.accountService.getAccount(username, new AsyncCallback<AccountDTO>() {
			@Override
			public void onFailure(Throwable caught) {						
			}
			@Override
			public void onSuccess(AccountDTO result) {
				account = result;
				buildMenuBar(account.getUsername());
				buildBasicDisplay();
				if(account.getType() == Account.ACCOUNT_TYPE_ADMIN)
					buildAdminDisplay();
			}
		});
	}
	
	private void buildBasicDisplay() {
		centerDisplay = new CenterDisplay();
		this.add(centerDisplay);
//		CenterDisplay.deckPanel.add(HomePage.getMainBody();
//		CenterDisplay.deckPanel.add(EditPage.build());
//		EditPage.getTheMain
//		CenterDisplay.changeTo(CenterDisplay.HOMEPAGE);
		
//		buildHomePage();
//		buildEditPage();

//		this.add(CenterDisplay.cptnpnlMain);
		
	}
	
	public void buildMenuBar(final String username) {
		menuBar = new MenuBar(false);
		menuBar.setSize("100%", "35px");
		
		mntmHome = new MenuItem("Home", false, new Command() {
			public void execute() {
				centerDisplay.changeTo(CenterDisplay.HOMEPAGE_INDEX);
			}
		});
		
		menuBar_account = new MenuBar(true);		
		mntmName = new MenuItem("Hello, " + username, false, menuBar_account);

		mntmDrive = new MenuItem("Drive", false, new Command() {
			public void execute() {
				CloudWebApp.accountService.getAccount(username, new AsyncCallback<AccountDTO>() {
					@Override
					public void onFailure(Throwable caught) {						
					}
					@Override
					public void onSuccess(AccountDTO result) {
//						buildeDrivePage();
						centerDisplay.changeTo(CenterDisplay.DRIVEPAGE_INDEX);
					}
				});
//				cptnpnlMain.setCaptionText("Drive");
//				deckPanel.showWidget(1);
			}
		});
		
		
		mntmEdit = new MenuItem("Edit", false,  new Command() {
			public void execute() {
				CloudWebApp.accountService.getAccount(username, new AsyncCallback<AccountDTO>() {
					@Override
					public void onFailure(Throwable caught) {						
					}
					@Override
					public void onSuccess(AccountDTO result) {
						account = result;
						centerDisplay.getEditPage().setAccountInfo(account);
						centerDisplay.changeTo(CenterDisplay.EDITPAGE_INDEX);
//						EditPage.editPage_lbl_username.setText(result.getUsername());
//						EditPage.editPage_lbl_password.setText(FieldVerifier.hidePassword(result.getPassword()));
//						EditPage.editPage_lbl_name.setText(result.getName());
//						EditPage.editPage_lbl_address.setText(result.getAddress());
//						EditPage.editPage_lbl_birthdate.setText(result.getBirthDate());
//						EditPage.editPage_lbl_email.setText(result.getEmail());
//						EditPage.editPage_lbl_signUpDate.setText(result.getSignUpDate());
					}
				});
			}
		});
		
		
		mntmLogOut = new MenuItem("Log out", false, new Command() {
			public void execute() {
				if(Window.confirm("do you really want to leave now?")) {
					CloudWebApp.doLogout();
				}
			}
		});
		
		MenuItemSeparator separator = new MenuItemSeparator();
		
		this.add(menuBar);
		menuBar.addItem(mntmHome);
		menuBar.addSeparator(separator);
		menuBar.addItem(mntmName);
		menuBar_account.addItem(mntmDrive);
		menuBar_account.addItem(mntmEdit);
		menuBar_account.addItem(mntmLogOut);
	}
	
	private void buildAdminDisplay() {
		MenuItem item = new MenuItem("Datastore viewer", false, new Command() {
			@Override
			public void execute() {
				CloudWebApp.accountService.getAccountList(new AsyncCallback<ArrayList<AccountDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("datastore viewer can't open.");
					}

					@Override
					public void onSuccess(ArrayList<AccountDTO> result) {
						centerDisplay.buildDataStoreViewer(result);
						centerDisplay.changeTo(CenterDisplay.DATASTOREVIEWER_INDEX);
					}
				});
			}
		});
		
		menuBar.addItem(item);
	}
	
	public static AccountDTO getLoginAccount() {
		return MainWindow.account;
	}
	
	
//	private mClickHandler clickHandler = new mClickHandler();

//	private CaptionPanel cptnpnlMain;
//	private DeckPanel deckPanel;
	
//	private DrivePage drivePage;
	
	
//	private static SignupPage signPage; 
	
//	private static Grid dataStorePage;
//	private static DatastoreRow[] row;
	/*
	public void user_homepage() {
		
		
		
		
		
		cptnpnlMain = new CaptionPanel();
		cptnpnlMain.setCaptionText("Home");
		add(cptnpnlMain);
		cptnpnlMain.setStyleName("panel");
		deckPanel = new DeckPanel();
		deckPanel.setStyleName("deckPanel");
		cptnpnlMain.setContentWidget(deckPanel);
		
//		buildMainPage(); // 0
//		buildeDrivePage(); // 1
//		buildInfoPage();
//		buildEditPage(); // 2
		
		deckPanel.showWidget(0);
	}/*
	*/
	/*
	public void buildeDrivePage() {
		drivePage = new DrivePage();
		deckPanel.add(drivePage);
	}*/
	
	private void buildEditPage() {
		
//		deckPanel.add(EditPage.grid);
/*		
		EditPage.editPage_btn_edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(account.getType() == Account.ACCOUNT_TYPE_ADMIN) {
					String pw = Window.prompt("ADMIN PASSWORD : ", "");
					if(!account.getPassword().equals(pw))
						return;
				}
				signPage = new SignupPage(SignupPage.EDIT_MODE, account.getUsername(), account.getPassword());
				signPage.tb_people_name.setText(signPage.restore[2] = account.getName());
				signPage.tb_people_address.setText(signPage.restore[3] = account.getAddress());
				signPage.db_people_birthDate.getTextBox().setText(signPage.restore[4] = account.getBirthDate());
				signPage.tb_mail.setText(signPage.restore[5] = account.getEmail());
				signPage.btn_add.addClickHandler(clickHandler);
				signPage.btn_back.addClickHandler(clickHandler);
				signPage.btn_restore.addClickHandler(clickHandler);
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
		});
*/
	}
/*
	public void admin_homepage() {
		user_homepage();
		MenuItem item = new MenuItem("Datastore viewer", false, new Command() {
			@Override
			public void execute() {
				CloudWebApp.accountService.getAccountList(new AsyncCallback<ArrayList<AccountDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("datastore viewer can't open.");
					}

					@Override
					public void onSuccess(ArrayList<AccountDTO> result) {
						row = null;
						row = new DatastoreRow[result.size() + 1];
						if(dataStorePage != null) {
							dataStorePage.removeFromParent();
							dataStorePage = null;
						}
						buildDataStorePage(result.size() + 1);
						int i = 0;
						for(AccountDTO ac : result) {
							row[i] = new DatastoreRow(ac);
							i++;
							if(ac.getType() == Account.ACCOUNT_TYPE_USER) {
								dataStorePage.setWidget(i, 0, row[i - 1].btn_delete);
								dataStorePage.setWidget(i, 1, row[i - 1].btn_block);
							}
							dataStorePage.setWidget(i, 2, row[i - 1].username);
							dataStorePage.setWidget(i, 3, row[i - 1].password);
							dataStorePage.setWidget(i, 4, row[i - 1].name);
							dataStorePage.setWidget(i, 5, row[i - 1].address);
							dataStorePage.setWidget(i, 6, row[i - 1].birthdate);
							dataStorePage.setWidget(i, 7, row[i - 1].email);
							dataStorePage.setWidget(i, 8, row[i - 1].type);
							dataStorePage.setWidget(i, 9, row[i - 1].signUpDate);
						}
						cptnpnlMain.setCaptionText("Datastore");
						deckPanel.showWidget(3);
					}
				});
			}
		});
		
		menuBar.addItem(item);
	}
	
	public void buildDataStorePage(int m) {
		dataStorePage = new Grid(m, 10);
		deckPanel.add(dataStorePage);
		Label lbl_column_delete = new Label("DELETE");
		dataStorePage.setWidget(0, 0, lbl_column_delete);
		
		Label lbl_column_block = new Label("BLOCK");
		dataStorePage.setWidget(0, 1, lbl_column_block);
		
		Label lbl_column_username = new Label("USERNAME");
		dataStorePage.setWidget(0, 2, lbl_column_username);
		
		Label lbl_column_passwrod = new Label("PASSWORD");
		dataStorePage.setWidget(0, 3, lbl_column_passwrod);
		
		Label lbl_column_name = new Label("name");
		dataStorePage.setWidget(0, 4, lbl_column_name);
		
		Label lbl_column_address = new Label("address");
		dataStorePage.setWidget(0, 5, lbl_column_address);
		
		Label lbl_column_birthdate = new Label("birthdate");
		dataStorePage.setWidget(0, 6, lbl_column_birthdate);
		
		Label lbl_column_email = new Label("email");
		dataStorePage.setWidget(0, 7, lbl_column_email);
		
		Label lbl_column_type = new Label("type");
		dataStorePage.setWidget(0, 8, lbl_column_type);
		
		Label lbl_column_signUpDate = new Label("signUpDate");
		dataStorePage.setWidget(0, 9, lbl_column_signUpDate);

        for(int j = 0; j < dataStorePage.getCellCount(0); j++)
        	dataStorePage.getCellFormatter().setStyleName(0, j, "datastore-title");
	}
	
	private class DatastoreRow {
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
		private nClickHandler clickHandler = new nClickHandler();
		
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
			
			btn_delete.addClickHandler(clickHandler);
			btn_block.addClickHandler(clickHandler);
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
				
		private class nClickHandler implements ClickHandler {
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
	
	public class mClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			Button src = (Button) event.getSource();
			if(src == signPage.btn_add) {
				if(!Window.confirm("do you really want to update your account?"))
					return;
				dialog.setVisible(false);
				if(!FieldVerifier.isValid(signPage.tb_username.getText(), signPage.ptb_password.getText())) {
					Window.alert("your username or password length is too short");
					dialog.setVisible(true);
					return;
				}
				account.updateValue(signPage.tb_username.getText(), signPage.ptb_password.getText(), 
						signPage.tb_people_name.getText(), signPage.tb_people_address.getText(), 
						signPage.db_people_birthDate.getTextBox().getText(), signPage.tb_mail.getText());
				CloudWebApp.accountService.updateAccount(account, new AsyncCallback<Integer>() {
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
				signPage.ptb_password.setText(signPage.restore[1]);
				signPage.tb_people_name.setText(signPage.restore[2]);
				signPage.tb_people_address.setText(signPage.restore[3]);
				
				signPage.db_people_birthDate.getTextBox().setText(signPage.restore[4]);
				signPage.tb_mail.setText(signPage.restore[5]);
			}
		}
	}
	
	public void closeDialogBox() {
		dialog.remove(signPage);
		signPage = null;
		dialog.hide();
		dialog = null;
	}
*/	
}
