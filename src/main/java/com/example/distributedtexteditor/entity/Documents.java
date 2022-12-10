package com.example.distributedtexteditor.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Documents {

	@Id
	private String hash;
	private List<Character> doc;
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public List<Character> getDoc() {
		return doc;
	}
	public void setDoc(List<Character> doc) {
		this.doc = doc;
	}
	
	
}