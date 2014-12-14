package com.cloudwebapp.client.ui;

import java.util.ArrayList;

import com.cloudwebapp.client.CloudWebApp;
import com.cloudwebapp.server.Account;
import com.cloudwebapp.shared.AccountDTO;
import com.cloudwebapp.shared.FilePathStack;
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
	public static DialogBox dialog;
	private static AccountDTO account;
	private static FilePathStack pathStack = new FilePathStack();
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
						account = result;
						pathStack.clear();
						pathStack.push(new FilePathStack.Path(account.getRootId(), "root"));
						CenterDisplay.buildDrivePage();
						centerDisplay.changeTo(CenterDisplay.DRIVEPAGE_INDEX);
					}
				});
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
						CenterDisplay.getEditPage().setAccountInfo(account);
						centerDisplay.changeTo(CenterDisplay.EDITPAGE_INDEX);
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
						CenterDisplay.buildDataStoreViewer(result);
						centerDisplay.changeTo(CenterDisplay.DATASTOREVIEWER_INDEX);
					}
				});
			}
		});
		
		menuBar.addItem(item);
	}
	
	public static void refresh() {
		CenterDisplay.drivePage.removeFromParent();
		CenterDisplay.drivePage = null;
		CenterDisplay.buildDrivePage();
		centerDisplay.changeTo(CenterDisplay.DRIVEPAGE_INDEX);
	}

	public static AccountDTO getLoginAccount() {
		return MainWindow.account;
	}
	
	public static FilePathStack getFilePathStack() {
		return MainWindow.pathStack;
	}
}
