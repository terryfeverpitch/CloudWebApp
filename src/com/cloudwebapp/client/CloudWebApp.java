package com.cloudwebapp.client;

import java.util.Date;

import com.cloudwebapp.client.ui.HomePage;
import com.cloudwebapp.client.ui.LoginPage;
import com.cloudwebapp.client.ui.MainWindow;
import com.cloudwebapp.client.ui.SignupPage;
import com.cloudwebapp.client.service.AccountService;
import com.cloudwebapp.client.service.AccountServiceAsync;
import com.cloudwebapp.server.Account;
import com.cloudwebapp.shared.FieldVerifier;
import com.cloudwebapp.shared.MessageCode;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CloudWebApp implements EntryPoint {
	private static DialogBox dialog;
	private static LoginPage loginPage;
	private SignupPage signPage; 
//	private static HomePage homepage;
	private static MainWindow mainWindow;
	public static AccountServiceAsync accountService = GWT.create(AccountService.class);
	
	public mClickHandler clickHandler = new mClickHandler();
	
	private static void createDialogBox() {

		dialog = new DialogBox();
		dialog.setGlassEnabled(true);
		dialog.setStyleName("gwt-loginPage");
		dialog.show();
	}

	public void onModuleLoad() {
		createDialogBox();
		loginPage = new LoginPage();
		loginPage.btn_submit.addClickHandler(clickHandler);
		loginPage.btn_signup.addClickHandler(clickHandler);
		dialog.setWidget(loginPage);
		dialog.center();
		
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if(dialog != null)
					dialog.center();
			}
		});
	}
	
	public class mClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			Widget source = (Widget) event.getSource();	
			if(source == loginPage.btn_signup) {
				signPage = new SignupPage(SignupPage.SIGNUP_MODE, loginPage.tb_username.getText(), loginPage.ptb_password.getText());
				signPage.btn_add.addClickHandler(clickHandler);
				signPage.btn_back.addClickHandler(clickHandler);
				dialog.setWidget(signPage);
				dialog.center();
			}
			else if(source == loginPage.btn_submit) {
				dialog.setVisible(false);
				accountService.verify(loginPage.tb_username.getText(), loginPage.ptb_password.getText(), 
						new AsyncCallback<Integer>() {
					@Override
					public void onFailure(Throwable caught) {	
						dialog.setVisible(true);
					}
					@Override
					public void onSuccess(Integer result) {
						if(result == MessageCode.VERIFY_FAILURE_UNAVAILABLE) {
							Window.alert("Your account is unavailable now.");
							dialog.setVisible(true);
						}
						else if(result == Account.ACCOUNT_TYPE_USER) {
							Window.alert("Welcome");
							dialog.hide();
							dialog = null;
							mainWindow = new MainWindow(loginPage.tb_username.getText(), result);
//							mainWindow.build(loginPage.tb_username.getText());
							RootPanel.get("mainView").add(mainWindow);
						}
						else if(result == Account.ACCOUNT_TYPE_ADMIN){
							Window.alert("Welcome back, Admin.");
							dialog.hide();
							dialog = null;
							mainWindow = new MainWindow(loginPage.tb_username.getText(), result);
//							mainWindow.build(loginPage.tb_username.getText());
							RootPanel.get("mainView").add(mainWindow);
						}
						else {
							Window.alert("Wrong username or password. \n"
									+ "If you dont have an account, plz sign up first.");
							dialog.setVisible(true);
						}
					}
				});
			}
			else if(source == signPage.btn_add) {
				String un = signPage.tb_username.getText();
				String pw = signPage.ptb_password.getText();
				
				dialog.setVisible(false);
				if(!FieldVerifier.isValid(un, pw)) {
					Window.alert("your username or password length is too short");
					dialog.setVisible(true);
					return;
				}
				
				String info_name = signPage.tb_people_name.getText();
				String info_address = signPage.tb_people_address.getText();
				String info_birthDate = signPage.db_people_birthDate.getTextBox().getText();
				String info_mail = signPage.tb_mail.getText();
				String sd = new Date().toString();
				
				accountService.register(
						un, pw, sd,info_name, info_address, info_birthDate, info_mail,
						new AsyncCallback<Integer>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("failure");
						dialog.setVisible(true);
					}

					@Override
					public void onSuccess(Integer result) {
						if(result == MessageCode.REGISTER_SUCCESS) {
							Window.alert("sign up success");
							loginPage.tb_username.setText(signPage.tb_username.getText());
							loginPage.ptb_password.setText(null);
							dialog.setWidget(loginPage);
							dialog.center();
						}
						else if(result == MessageCode.REGISTER_FAILURE_USERNAME_EXISTED)
							Window.alert("username existed, try another");
						dialog.setVisible(true);
					}
				});
			}
			else if(source == signPage.btn_back) {
				dialog.setWidget(loginPage);
				dialog.center();
			}
		}
	}
	
	public static void doLogout() {
		mainWindow.removeFromParent();
		mainWindow = null;
		
		loginPage.ptb_password.setText(null);	
		createDialogBox();
		dialog.setWidget(loginPage);
		dialog.center();
	}
}
