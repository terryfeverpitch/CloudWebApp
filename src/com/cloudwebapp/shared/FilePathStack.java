package com.cloudwebapp.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class FilePathStack implements Serializable {
	private ArrayList<Long> stack;
	
	public FilePathStack(){
		this.stack = new ArrayList<Long>(); 
	};
	
	public void push(Long id) {
		stack.add(id);
	}
	
	public void pop() {
		if(stack.isEmpty())
			return;
		else
			stack.remove(stack.size() - 1);
	}
	
	public Long currentPathId() {
		if(stack.isEmpty())
			return null;
		else
			return stack.get(stack.size() - 1);
	}
	
	public void clear() {
		this.stack.clear();
	}
}
