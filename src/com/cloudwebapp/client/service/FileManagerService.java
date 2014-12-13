package com.cloudwebapp.client.service;

import java.util.ArrayList;

import com.cloudwebapp.shared.FileDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("fileManager")
public interface FileManagerService extends RemoteService {
	public int newFolder(String author, String folderName, Long parent);
	public ArrayList<FileDTO> getFiles(String author, Long parent);
	public ArrayList<String> getPath(Long id);
}
