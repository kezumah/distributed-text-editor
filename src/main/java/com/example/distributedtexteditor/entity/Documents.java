package com.example.distributedtexteditor.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Documents {

	@Id
	private String hash;
	private List<String> doc;
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public List<String> getDoc() {
		return doc;
	}
	public void setDoc(List<String> doc) {
		this.doc = doc;
	}
	
	
}