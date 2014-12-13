package com.cloudwebapp.server;

import com.cloudwebapp.client.service.GetUploadUrlClient;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GetUploadUrlClientImpl extends RemoteServiceServlet implements GetUploadUrlClient {
	private static final long serialVersionUID = 1L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	/*
	@Override
	public String getUploadUrl(StaffDTO user, String servletUrl) {
		Tenant tenant= Tenant.getTenant();
		long fileSizeBound= (user instanceof StaffDTO) ? 
				tenant.getFileSizeBound4Staff() : tenant.getFileSizeBound4Student();
		fileSizeBound*= 1024;		
		UploadOptions options= UploadOptions.Builder.withMaxUploadSizeBytesPerBlob(fileSizeBound);
		return blobstoreService.createUploadUrl(servletUrl, options);
	}
	*/
	@Override
	public String getUploadUrl(String servletUrl) {
		return blobstoreService.createUploadUrl(servletUrl);
	}
}
