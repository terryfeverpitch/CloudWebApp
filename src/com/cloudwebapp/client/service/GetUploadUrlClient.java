package com.cloudwebapp.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("getuploadurlclient")
public interface GetUploadUrlClient extends RemoteService {
	public String getUploadUrl(String servletUrl);
}
