package com.cloudwebapp.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetUploadUrlClientAsync {

	void getUploadUrl(String servletUrl, AsyncCallback<String> callback);
	
}
