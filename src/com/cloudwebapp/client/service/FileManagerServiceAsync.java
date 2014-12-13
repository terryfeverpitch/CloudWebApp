package com.cloudwebapp.client.service;

import java.util.ArrayList;

import com.cloudwebapp.shared.FileDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FileManagerServiceAsync {

	void newFolder(String author, String folderName, Long parent,
			AsyncCallback<Integer> callback);

	void getFiles(String author, Long parent,
			AsyncCallback<ArrayList<FileDTO>> callback);

	void getPath(Long id, AsyncCallback<ArrayList<String>> callback);

}
