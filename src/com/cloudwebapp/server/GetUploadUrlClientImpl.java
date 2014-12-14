package com.cloudwebapp.server;

import com.cloudwebapp.client.service.GetUploadUrlClient;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GetUploadUrlClientImpl extends RemoteServiceServlet implements GetUploadUrlClient {
	private static final long serialVersionUID = 1L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public String getUploadUrl(String servletUrl) {
		return blobstoreService.createUploadUrl(servletUrl);
	}
}
