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
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("unchecked")
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
				updateParent(file.getParentKey(), d.toString());
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
	public int renameFile(Long id, String newName) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			File chosenFile = (File) pm.getObjectById(File.class, id);
			if(chosenFile == null) {
				pm.close();
				return MessageCode.RENAME_FILE_FAILURE_NOTFOUND;
			}
			Query query = pm.newQuery("SELECT FROM " + File.class.getName() + 
					" WHERE parent == " + chosenFile.getParentKey());
			List<File> result = (List<File>) query.execute();

			for(File f : result) {
				if(f.getFileName().equals(newName) && f.getId() != id) {
					pm.close();
					return MessageCode.RENAME_FILE_FAILURE_NAME_OVERLAP;
				}
			}
			String updateTime = new Date().toString();
			chosenFile.setFileName(newName);
			chosenFile.setUpdateTime(updateTime);
			updateParent(chosenFile.getParentKey(), updateTime);
			
			pm.close();
			return MessageCode.RENAME_FILE_SUCCESS;
		} 
		catch(Exception exception) {
			pm.close();
			return  MessageCode.RENAME_FILE_FAILURE_EXCEPTION_ERROR;
		}
	}
	
	private void updateParent(Long id, String updateTime) {
		if(id == null)
			return;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		File f = (File) pm.getObjectById(File.class, id);
		if(f != null) {
			f.setUpdateTime(updateTime);
			pm.close();
			updateParent(f.getParentKey(), updateTime);
		}
		pm.close();
	}

	@Override
	public int deleteFiles(ArrayList<Long> id_list) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		File first = (File) pm.getObjectById(File.class, id_list.get(0));
		Long id = first.getParentKey();
		String updateTime = new Date().toString();
		
		for(Long deleteId : id_list) {
			File f = (File) pm.getObjectById(File.class, deleteId);
			
			if(f != null) {
				if(f.getType() == File.FILE) {
//					blobstoreService.delete(new BlobKey(f.getBlobKey()));
					pm.deletePersistent(f);
				}
				else {
					deleteChilds(f.getId());
					pm.deletePersistent(f);
				}
			}
			else {
				pm.close();
				return MessageCode.DELETE_FILE_FAILURE;
			}
		}

		updateParent(id, updateTime);
		pm.close();
		return MessageCode.DELETE_FILE_SUCCESS;
	}
	
	private void deleteChilds(Long id) {
		if(id == null)
			return;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		File parent = (File) pm.getObjectById(File.class, id);
		Query query = pm.newQuery("SELECT FROM " + File.class.getName() + 
				" WHERE parent == " + parent.getId());
		List<File> result = (List<File>) query.execute();
//		if(result == null || result.isEmpty()) {
//			pm.deletePersistent(parent);
//		}
//		else {
			for(File f : result) {
				if(f.getType() == File.FILE) {
					blobstoreService.delete(new BlobKey(f.getBlobKey()));
					pm.deletePersistent(f);
				}
				else {
					deleteChilds(f.getId());
					pm.deletePersistent(f);
				}
			}
//		}
		pm.close();
	}
}
