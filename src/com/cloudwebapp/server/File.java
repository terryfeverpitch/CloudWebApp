package com.cloudwebapp.server;

import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class File {
	public static final int FILE = 0;
	public static final int DIR = 1;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key primary_key;
	@Persistent
	private File parent;
	@Persistent
	private ArrayList<File> child;
	@Persistent
	private int type;
	@Persistent
	private String fileName;
	@Persistent
	private int fileSize;
	@Persistent
	private String uploadTime;
}
