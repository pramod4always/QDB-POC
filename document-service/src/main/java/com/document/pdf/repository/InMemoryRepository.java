package com.document.pdf.repository;

import com.document.pdf.entity.DocumentInfo;

import java.util.List;

public interface InMemoryRepository {

    DocumentInfo create(DocumentInfo timeEntry);

    List<DocumentInfo> list();
}
