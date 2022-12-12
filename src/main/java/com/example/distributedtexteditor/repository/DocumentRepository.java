
package com.example.distributedtexteditor.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.distributedtexteditor.entity.Documents;

@Repository
public interface DocumentRepository extends MongoRepository<Documents, String> {
	
}


