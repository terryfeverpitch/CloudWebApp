package com.cloudwebapp.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class FilePathStack implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Path> stack;
	
	public static class Path implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Long folderId;
		public String folderName;
		
		public Path(Long id, String name) {
			this.folderId = id;
			this.folderName = name;
		}
	}
	
	public FilePathStack(){
		this.stack = new ArrayList<Path>(); 
	};
	
	public void push(Path p) {
		stack.add(p);
	}
	
	public void pop() {
		if(stack.isEmpty())
			return;
		else
			stack.remove(stack.size() - 1);
	}
	
	public Path currentPath() {
		if(stack.isEmpty())
			return null;
		else
			return stack.get(stack.size() - 1);
	}
	
	public void clear() {
		this.stack.clear();
	}
	
	public String getPath() {
		String path = "";
		for(Path p : stack)
			path += "/" + p.folderName;
		return path;
	}
}
