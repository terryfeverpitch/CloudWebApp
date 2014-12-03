package com.cloudwebapp.server;

import com.cloudwebapp.client.service.GetUploadClient;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GetUploadClientImpl extends RemoteServiceServlet implements GetUploadClient {
	private static final long serialVersionUID = 1L;

	@Override
	public String setCallback() {
		return null;
	}

}
