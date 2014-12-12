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
import com.cloudwebapp.client.ui.MainWindow;
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
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		Map<String, List<BlobKey>> blobMap = blobstoreService.getUploads(req);
				
		for (List<BlobKey> list: blobMap.values()){
			if (list.isEmpty()) continue;			
			BlobKey blobKey = list.get(0);
			BlobInfo blobInfo = this.blobInfoFactory.loadBlobInfo(blobKey);
			System.out.println("File with blobkey " + blobKey.getKeyString() + " was saved in blobstore.");
			if (blobInfo.getFilename() == null || blobInfo.getFilename().trim().isEmpty()){
				this.blobstoreService.delete(blobKey);
				continue;
			}
			String username = req.getParameter("Author");
			Long parent = Long.parseLong(req.getParameter("Parent"));
			String filename = blobInfo.getFilename();
			long fileSize = blobInfo.getSize();
//			Long parent = MainWindow.getLoginAccount().getRootId();
			System.out.println("parent = " + req.getParameter("Parent"));
			File file = new File(username, filename, fileSize, parent, blobKey);
			file.getParentKey();
			pm.makePersistent(file);
			
			/*
			 * public File(String author, String fileName, long fileSize, Long parent, BlobKey blobKey) {
		this.blobKey = blobKey.getKeyString();
		this.author = author;
		this.parent = parent;
//		this.child = null;
		this.type = File.FILE;
		this.fileName = fileName;
		this.fileSize = fileSize;
	}*/
		}	
		
		pm.close();		
	}
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setHeader("Content-Type", "text/plain");
//        resp.getWriter().write(blobstore.createUploadUrl("/uploadfinished"));
    }
}
