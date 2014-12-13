package com.cloudwebapp.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class DownloadServlet extends HttpServlet {
	
	private BlobstoreService blobstoreService = 
			BlobstoreServiceFactory.getBlobstoreService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		resp.addHeader("content-disposition", "attachment;filename=" + req.getParameter("name"));
		blobstoreService.serve(blobKey, resp);
	}
}
