package com.example.distributedtexteditor.entity;

import java.io.Serializable;
import java.util.List;

public class Doc implements Serializable{
	
	private List<String> docs;

	public List<String> getDocs() {
		return docs;
	}

	public void setDocs(List<String> docs) {
		this.docs = docs;
	}
	
	

}
