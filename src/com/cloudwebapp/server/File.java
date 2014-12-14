package com.cloudwebapp.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;

@PersistenceCapable
public class File {
	public static final int FILE = 0;
	public static final int DIR = 1;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String blobKey;
	@Persistent
	private String author;
	@Persistent
	private Long parent;
	@Persistent
	private int type;
	@Persistent
	private String fileName;
	@Persistent
	private long fileSize;
	@Persistent
	private String updateTime;

	public File() {
	}
	
	public File(String author, String fileName, Long parent) {
		this.blobKey = null;
		this.author = author;
		this.parent = parent;
		this.type = File.DIR;
		this.fileName = fileName;
		this.fileSize = 0;
	}
	
	public File(String author, String fileName, long fileSize, Long parent, BlobKey blobKey) {
		this.blobKey = blobKey.getKeyString();
		this.author = author;
		this.parent = parent;
		this.type = File.FILE;
		this.fileName = fileName;
		this.fileSize = fileSize;
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
	public int getType() {
		return this.type;
	}
	
	public String getUpdateTime() {
		return this.updateTime;
	}
	
	public String getBlobKey() {
		return this.blobKey;
	}
}
