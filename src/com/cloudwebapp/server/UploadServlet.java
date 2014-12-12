package com.cloudwebapp.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloudwebapp.client.PMF;
import com.cloudwebapp.client.ui.MainWindow;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.client.Window;

public class UploadServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	private BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		PersistenceManager pm= PMF.get().getPersistenceManager();
//		Long purchaseRequestId= Long.parseLong(req.getParameter("purchaseRequestId"));
//		AttachmentType aType = AttachmentType.valueOf(req.getParameter("attachmentType"));
//		PurchaseRequest pr = pm.getObjectById(PurchaseRequest.class, purchaseRequestId);		
		
		Map<String, List<BlobKey>> blobMap = blobstoreService.getUploads(req);
		BlobKey blobKey = null;
		BlobInfo blobInfo;
		
		for (List<BlobKey> list: blobMap.values()){
			if (list.isEmpty()) continue;			
			blobKey= list.get(0);
			blobInfo= this.blobInfoFactory.loadBlobInfo(blobKey);
			
			if (blobInfo.getFilename() == null || blobInfo.getFilename().trim().isEmpty()){
				this.blobstoreService.delete(blobKey);
				continue;
			}
			// String username = req.getParameter("Author");
			
//			File file = new File(username, "tst", 100, parent, blobKey);
//			pm.makePersistent(file);
//			Window.alert("make persistent");
		}	
		String username = MainWindow.getLoginAccount().getName();
		Long parent = MainWindow.getLoginAccount().getRootId();
		File file = new File(username, "tst", 100, parent, blobKey);
		pm.makePersistent(file);
		pm.close();		
	}
}
