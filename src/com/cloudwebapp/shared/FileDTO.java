package com.cloudwebapp.shared;

import java.io.Serializable;

import com.cloudwebapp.server.File;

public class FileDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String author;
	private Long parent;
	private int type;
	private String fileName;
	private long fileSize;
	private String updateTime;
	private String blobKey;

	public FileDTO() {
	}
	
	public FileDTO(Long id, String author, String fileName, Long parent) {
		this.id = id;
		this.author = author;
		this.parent = parent;
		this.type = File.DIR;
		this.fileName = fileName;
		this.fileSize = 0;
		this.blobKey = null;
	}
	
	public FileDTO(Long id, String author, String fileName, long fileSize, Long parent, String blobKey) {
		this.id = id;
		this.author = author;
		this.parent = parent;
		this.type = File.FILE;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.blobKey = blobKey;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public long getFileSize() {
		return this.fileSize;
	}
	
	public Long getParentKey() {
		return this.parent;
	}
	
	public Long getId() {
		return this.id;
	}	
	
	public String getUpdateTime() {
		return this.updateTime;
	}
	
	public int getType() {
		return this.type;
	}
	
	public String getBlobKey() {
		return this.blobKey;
	}
}