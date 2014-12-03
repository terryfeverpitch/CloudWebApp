package com.cloudwebapp.client.ui;

import java.util.ArrayList;
import java.util.Date;

import com.cloudwebapp.client.CloudWebApp;
import com.cloudwebapp.client.ui.basic.DrivePage;
import com.cloudwebapp.server.Account;
import com.cloudwebapp.shared.AccountDTO;
import com.cloudwebapp.shared.FieldVerifier;
import com.cloudwebapp.shared.MessageCode;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class HomePage extends VerticalPanel {
	private AccountDTO account;
	private String username;
	private static DialogBox dialog;
	private mClickHandler clickHandler = new mClickHandler();
	private MenuBar menuBar;
	protected MenuItem mntmName;

	private CaptionPanel cptnpnlMain;
	private DeckPanel deckPanel;
	
	/*private Grid infoPage;
	private Label lbl_username;
	private Label lbl_password;
	private Label lbl_signUpDate;
	private Label lbl_name;
	private Label lbl_address;
	private Label lbl_birthdate;
	private Label lbl_email;*/
	
	private DrivePage drivePage;
	
	private Grid editPage;
	private Label editPage_lbl_username;
	private Label editPage_lbl_password;
	private Label editPage_lbl_signUpDate;
	private Label editPage_lbl_name;
	private Label editPage_lbl_address;
	private Label editPage_lbl_birthdate;
	private Label editPage_lbl_email;
	private static SignupPage signPage; 
	
	private static Grid dataStorePage;
	private static DatastoreRow[] row;
	
	public HomePage() {
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public HomePage(int type) {
		if(type == Account.ACCOUNT_TYPE_USER)
			user_homepage();
		else 
			admin_homepage();
	}
	
	public void build(String name) {
		this.username = name;
		mntmName.setText("Hello, " + name);
	}
	
	public void user_homepage() {
		setSize("100%", "100%");
		menuBar = new MenuBar(false);
		menuBar.setSize("100%", "35px");
		
		add(menuBar);
		MenuItem mntmHome = new MenuItem("Home", false, new Command() {
			public void execute() {
				cptnpnlMain.setCaptionText("Home");
				deckPanel.showWidget(0);
			}
		});
		menuBar.addItem(mntmHome);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuBar menuBar_account = new MenuBar(true);		
		mntmName = new MenuItem("", false, menuBar_account);

		MenuItem mntmDrive = new MenuItem("Drive", false, new Command() {
			public void execute() {
				CloudWebApp.accountService.getAccount(username, new AsyncCallback<AccountDTO>() {
					@Override
					public void onFailure(Throwable caught) {						
					}
					@Override
					public void onSuccess(AccountDTO result) {
						/*account = result;
						lbl_username.setText(result.getUsername());
						lbl_password.setText(FieldVerifier.hidePassword(result.getPassword()));
						lbl_name.setText(result.getName());
						lbl_address.setText(result.getAddress());
						lbl_birthdate.setText(result.getBirthDate());
						lbl_email.setText(result.getEmail());
						lbl_signUpDate.setText(result.getSignUpDate());*/
						buildeDrivePage();
					}
				});
				cptnpnlMain.setCaptionText("Drive");
				deckPanel.showWidget(1);
			}
		});
		menuBar_account.addItem(mntmDrive);
		
		MenuItem mntmEdit = new MenuItem("Edit", false,  new Command() {
			public void execute() {
				CloudWebApp.accountService.getAccount(username, new AsyncCallback<AccountDTO>() {
					@Override
					public void onFailure(Throwable caught) {						
					}
					@Override
					public void onSuccess(AccountDTO result) {
						account = result;
						editPage_lbl_username.setText(result.getUsername());
						editPage_lbl_password.setText(FieldVerifier.hidePassword(result.getPassword()));
						editPage_lbl_name.setText(result.getName());
						editPage_lbl_address.setText(result.getAddress());
						editPage_lbl_birthdate.setText(result.getBirthDate());
						editPage_lbl_email.setText(result.getEmail());
						editPage_lbl_signUpDate.setText(result.getSignUpDate());
					}
				});
				cptnpnlMain.setCaptionText("Edit");
				deckPanel.showWidget(2);
			}
		});
		menuBar_account.addItem(mntmEdit);
		
		MenuItem mntmLogOut = new MenuItem("Log out", false, new Command() {
			public void execute() {
				if(Window.confirm("do you really want to leave now?")) {
					CloudWebApp.doLogout();
				}
			}
		});
		
		menuBar_account.addItem(mntmLogOut);
		menuBar.addItem(mntmName);
		
		cptnpnlMain = new CaptionPanel();
		cptnpnlMain.setCaptionText("Home");
		add(cptnpnlMain);
		cptnpnlMain.setStyleName("panel");
		deckPanel = new DeckPanel();
		deckPanel.setStyleName("deckPanel");
		cptnpnlMain.setContentWidget(deckPanel);
		
		buildMainPage(); // 0
		buildeDrivePage(); // 1
//		buildInfoPage();
		buildEditPage(); // 2
		
		deckPanel.showWidget(0);
	}
	
	public void buildMainPage() {
		final Label lbl_timer = new Label("Current TIme");
		lbl_timer.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lbl_timer.setDirectionEstimator(true);
		deckPanel.add(lbl_timer);
		
		new Timer() {
			public void run() {
				Date d = new Date();
	            lbl_timer.setText(d.toString());
				schedule(1000);
			}
		}.schedule(1000);
	}
	
	public void buildeDrivePage() {
		drivePage = new DrivePage();
		deckPanel.add(drivePage);
	}
	/*
	public void buildInfoPage() {
		infoPage = new Grid(7, 2);
		deckPanel.add(infoPage);
		infoPage.setStyleName("grid");
		
		Label lbl_title_username = new Label("username");
		infoPage.setWidget(0, 0, lbl_title_username);
		
		lbl_username = new Label("New label");
		infoPage.setWidget(0, 1, lbl_username);
		
		Label lbl_title_password = new Label("passoword");
		infoPage.setWidget(1, 0, lbl_title_password);
		
		lbl_password = new Label("New label");
		infoPage.setWidget(1, 1, lbl_password);
		
		Label lbl_title_name = new Label("name");
		infoPage.setWidget(2, 0, lbl_title_name);
		
		lbl_name = new Label("New label");
		infoPage.setWidget(2, 1, lbl_name);
		
		Label lbl_title_address = new Label("address");
		infoPage.setWidget(3, 0, lbl_title_address);
		
		lbl_address = new Label("New label");
		infoPage.setWidget(3, 1, lbl_address);
		
		Label lbl_title_birthdate= new Label("birth date");
		infoPage.setWidget(4, 0, lbl_title_birthdate);
		
		lbl_birthdate = new Label("New label");
		infoPage.setWidget(4, 1, lbl_birthdate);
		
		Label lbl_title_email = new Label("email");
		infoPage.setWidget(5, 0, lbl_title_email);
		
		lbl_email = new Label("New label");
		infoPage.setWidget(5, 1, lbl_email);
		
		Label lbl_title_signUpDate = new Label("sign up date");
		infoPage.setWidget(6, 0, lbl_title_signUpDate);
		
		lbl_signUpDate = new Label("New label");
		infoPage.setWidget(6, 1, lbl_signUpDate);

		for(int i = 0; i < infoPage.getRowCount(); i++) {
            for(int j = 0; j < infoPage.getCellCount(i); j++) {
                if((j % 2) == 0)
                	infoPage.getCellFormatter().setStyleName(i, j, "tableCell-even");
                else
                	infoPage.getCellFormatter().setStyleName(i, j, "tableCell-odd");
            }
        }
	}*/
	
	private void buildEditPage() {
		editPage = new Grid(8, 2);
		deckPanel.add(editPage);
		editPage.setStyleName("grid");
		
		Label lbl_title_username = new Label("username");
		editPage.setWidget(0, 0, lbl_title_username);
		
		editPage_lbl_username = new Label("New label");
		editPage.setWidget(0, 1, editPage_lbl_username);
		
		Label lbl_title_password = new Label("passoword");
		editPage.setWidget(1, 0, lbl_title_password);
		
		editPage_lbl_password = new Label("New label");
		editPage.setWidget(1, 1, editPage_lbl_password);
		
		Label lbl_title_name = new Label("name");
		editPage.setWidget(2, 0, lbl_title_name);
		
		editPage_lbl_name = new Label("New label");
		editPage.setWidget(2, 1, editPage_lbl_name);
		
		Label lbl_title_address = new Label("address");
		editPage.setWidget(3, 0, lbl_title_address);
		
		editPage_lbl_address = new Label("New label");
		editPage.setWidget(3, 1, editPage_lbl_address);
		
		Label lbl_title_birthdate= new Label("birth date");
		editPage.setWidget(4, 0, lbl_title_birthdate);
		
		editPage_lbl_birthdate = new Label("New label");
		editPage.setWidget(4, 1, editPage_lbl_birthdate);
		
		Label lbl_title_email = new Label("email");
		editPage.setWidget(5, 0, lbl_title_email);
		
		editPage_lbl_email = new Label("New label");
		editPage.setWidget(5, 1, editPage_lbl_email);
		
		Label lbl_title_signUpDate = new Label("sign up date");
		editPage.setWidget(6, 0, lbl_title_signUpDate);
		
		editPage_lbl_signUpDate = new Label("New label");
		editPage.setWidget(6, 1, editPage_lbl_signUpDate);
		
		Button btn_edit = new Button("Edit");		
		btn_edit.setStyleName("gwt-button");
		btn_edit.addClickHandler(new ClickHandler() {
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
		
		editPage.setWidget(7, 0, btn_edit);
		
		for(int i = 0; i < editPage.getRowCount(); i++) {
            for(int j = 0; j < editPage.getCellCount(i); j++) {
                if((j % 2) == 0)
                	editPage.getCellFormatter().setStyleName(i, j, "tableCell-even");
                else
                	editPage.getCellFormatter().setStyleName(i, j, "tableCell-odd");
            }
        }
	}

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
}
