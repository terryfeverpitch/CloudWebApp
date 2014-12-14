package com.cloudwebapp.client.ui;

import java.util.ArrayList;

import com.cloudwebapp.shared.AccountDTO;
import com.cloudwebapp.client.ui.admin.DatastoreViewerPage;
import com.cloudwebapp.client.ui.basic.DrivePage;
import com.cloudwebapp.client.ui.basic.EditPage;
import com.cloudwebapp.client.ui.basic.HomePage;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.DeckPanel;

public class CenterDisplay extends CaptionPanel {
	public final static int HOMEPAGE_INDEX  = 0;
	public final static int DRIVEPAGE_INDEX = 1;
	public final static int EDITPAGE_INDEX  = 2;
	public final static int DATASTOREVIEWER_INDEX = 3;
	
	public final static String HOMEPAGE_TITLE = "Home";
	public final static String EDITPAGE_TITLE = "Edit";
	public final static String DRIVEPAGE_TITLE = "Drive";
	public final static String DATASTOREVIEWER_TITLE = "Datastore";
	
	public static CenterDisplay self;
	public static DeckPanel deckPanel;
	
	// content
	public static HomePage homePage;
	public static EditPage editPage;
	public static DrivePage drivePage;
//	public static ShareDrivePage
	// content for admin
	public static DatastoreViewerPage datastoreViewerPage;
	
	public CenterDisplay() {
		buildDeckPanel();
		this.setStyleName("panel");	
		this.add(deckPanel);
	}
	
	private void buildDeckPanel() {
		deckPanel = new DeckPanel();
		deckPanel.setStyleName("deckPanel");
		deckPanel.add(homePage = new HomePage());
		deckPanel.add(editPage = new EditPage());
		
		this.setCaptionText(HOMEPAGE_TITLE);
		deckPanel.showWidget(HOMEPAGE_INDEX);
	}	
	
	public void changeTo(int INDEX_OF_PAGE) {
		int currentIndex = 0;
		
		if(INDEX_OF_PAGE == HOMEPAGE_INDEX) {
			this.setCaptionText(CenterDisplay.HOMEPAGE_TITLE);
			currentIndex = deckPanel.getWidgetIndex(homePage);
		}
		else if(INDEX_OF_PAGE == DRIVEPAGE_INDEX) {
			this.setCaptionText(CenterDisplay.DRIVEPAGE_TITLE);
			currentIndex = deckPanel.getWidgetIndex(drivePage);
		}
		else if(INDEX_OF_PAGE == EDITPAGE_INDEX) {
			this.setCaptionText(CenterDisplay.EDITPAGE_TITLE);
			currentIndex = deckPanel.getWidgetIndex(editPage);
		}
		else if(INDEX_OF_PAGE == DATASTOREVIEWER_INDEX) {
			this.setCaptionText(CenterDisplay.DATASTOREVIEWER_TITLE);
			currentIndex = deckPanel.getWidgetIndex(datastoreViewerPage);
		}
			
		deckPanel.showWidget(currentIndex);
	}
	
	public static void buildDataStoreViewer(ArrayList<AccountDTO> in) {
		datastoreViewerPage = new DatastoreViewerPage(in.size() + 1, 10, in);
		deckPanel.add(datastoreViewerPage);
	}
	
	public static void buildDrivePage() {
		drivePage = null;
		drivePage = new DrivePage();
		deckPanel.add(drivePage);
	}
	
	public static EditPage getEditPage() {
		return editPage;
	}
	
	public static DrivePage getDrivePage() {
		return drivePage;
	}
}
