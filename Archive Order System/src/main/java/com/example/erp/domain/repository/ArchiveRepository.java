package com.example.erp.domain.repository;

import com.example.erp.domain.model.ArchivedOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveRepository extends MongoRepository<ArchivedOrder, Long> {}
