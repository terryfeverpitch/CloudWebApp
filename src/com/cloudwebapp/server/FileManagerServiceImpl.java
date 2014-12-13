package com.cloudwebapp.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.cloudwebapp.shared.FileDTO;
import com.cloudwebapp.client.PMF;
import com.cloudwebapp.client.service.FileManagerService;
import com.cloudwebapp.shared.MessageCode;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class FileManagerServiceImpl extends RemoteServiceServlet implements FileManagerService {
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	
	@Override
	public int newFolder(String author, String folderName, Long parent) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		File file = new File(author, folderName, parent);
		Date d = new Date();
		file.setUpdateTime(d.toString());
		
		try {
			Query query = pm.newQuery("SELECT FROM " + File.class.getName() + 
					" WHERE fileName == " + "'" + folderName + "'" + 
					" && parent == " + parent + 
					" && type == " + File.DIR);
			List<File> result =  (List<File>)query.execute();
			
			if(result.isEmpty()) {
				pm.makePersistent(file);
				pm.close();
				return MessageCode.NEW_FOLDER_SUCCESS;
			}
			else {
				pm.close();
				return MessageCode.NEW_FOLDER_FAILURE_NAME_OVERLAP;
			}
		} catch(Exception exception) {
			pm.close();
			return MessageCode.NEW_FOLDER_FAILURE_EXCEPTION_ERROR;
		}
	}

	@Override
	public ArrayList<FileDTO> getFiles(String author, Long parent) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		ArrayList<FileDTO> fileList = new ArrayList<FileDTO>();
		
		try {
			Query query = pm.newQuery("SELECT FROM " + File.class.getName() + 
					" WHERE parent == " + parent + 
					" ORDER BY type desc, fileName");
			List<File> result = (List<File>) query.execute();
			
			if(result.isEmpty()) {
				return null;
			}
			else {
				for(File f : result) {
					
					if(f.getType() == File.DIR) {
						FileDTO item = new FileDTO(f.getId(), f.getAuthor(), f.getFileName(), f.getParentKey());
						item.setUpdateTime(f.getUpdateTime());
						fileList.add(item);
					}
					else {
						FileDTO item = new FileDTO(f.getId(), f.getAuthor(), f.getFileName(), f.getFileSize(), f.getParentKey(),
								f.getBlobKey());
						item.setUpdateTime(f.getUpdateTime());
						fileList.add(item);
					}
				}
				pm.close();
				return fileList;
			}
		} catch(Exception exception) {
			pm.close();
			return null;
		}
	}

	@Override
	public ArrayList<String> getPath(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ArrayList<String> path = new ArrayList<String>(); 
		Long traversalId = id;
		
		while(traversalId == null) {
			File f = (File) pm.getObjectId(traversalId);
			path.add(f.getFileName());
			traversalId = f.getParentKey();
		}
		
		path.add("root");
		return path;
	}
}
