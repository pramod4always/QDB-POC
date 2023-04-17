package com.document.pdf.repository;

import com.document.pdf.entity.DocumentInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends CrudRepository<DocumentInfo, Long> {

    @Override
    public List<DocumentInfo> findAll();

    Optional<DocumentInfo> findById(Long id);

}



