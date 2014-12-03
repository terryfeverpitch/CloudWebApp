package com.cloudwebapp.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getuploadurl")
public interface GetUploadClient extends RemoteService {
	public String setCallback();
}
