package com.cloudwebapp.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloudwebapp.client.PMF;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

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
		Long purchaseRequestId= Long.parseLong(req.getParameter("purchaseRequestId"));
//		AttachmentType aType= AttachmentType.valueOf(req.getParameter("attachmentType"));
//		PurchaseRequest pr= pm.getObjectById(PurchaseRequest.class, purchaseRequestId);		
		
		Map<String, List<BlobKey>> blobMap = blobstoreService.getUploads(req);
		for (List<BlobKey> list: blobMap.values()){
			if (list.isEmpty()) continue;			
			BlobKey blobKey= list.get(0);
			BlobInfo blobInfo= this.blobInfoFactory.loadBlobInfo(blobKey);
			
			if (blobInfo.getFilename() == null || blobInfo.getFilename().trim().isEmpty()){
				this.blobstoreService.delete(blobKey);
				continue;
			}
			File file = new File(blobInfo.getFilename());
			pm.makePersistent(file);
			
//			file.setBolbKey(blobKey);
			
			
//			PurchaseAttachment attachment= new PurchaseAttachment(purchaseRequestId, aType);
//			attachment.setBlobKey(blobKey);
//			attachment.setFileName(blobInfo.getFilename());
//			attachment.setSize(blobInfo.getSize());
//			pm.makePersistent(attachment);
			
//			if (aType== AttachmentType.PRODUCT_SPEC) pr.setSpecAttached(true);
//			else if (aType== AttachmentType.PRICE_SHEET) pr.setPriceAttached(true);
		}		
		pm.close();		
	}
}
