package com.example.distributedtexteditor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.distributedtexteditor.entity.Documents;
import com.example.distributedtexteditor.repository.DocumentRepository;

@Service
public class DatabaseService {
	
	@Autowired
	private DocumentRepository documentRepository;

    public void createHashInDatabase(String hash){
    	Documents doc = new Documents();
    	doc.setDoc(null);
    	doc.setHash(hash);
    	documentRepository.save(doc);
    }

    public List<String> getDocFromDatabase(String hash) {
        Optional<Documents> doc = documentRepository.findById(hash);
        if(!doc.isPresent()) {
        	return null;
        }
        return doc.get().getDoc();
    }

	public List<Documents> findAllDocs() {
		return documentRepository.findAll();
	}

	public Documents saveDocument(String hash, List<String> docs) {
		Documents doc = new Documents();
    	doc.setDoc(docs);
    	doc.setHash(hash);
    	documentRepository.save(doc);
    	return doc;
	}
}