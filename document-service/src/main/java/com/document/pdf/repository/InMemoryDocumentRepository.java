package com.document.pdf.repository;

import com.document.pdf.entity.DocumentInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryDocumentRepository implements InMemoryRepository{

    private HashMap<Long, DocumentInfo> documents = new HashMap<>();

    private long currentId = 1L;

    @Override
    public DocumentInfo create(DocumentInfo documentInfo) {
        Long id = currentId++;

        DocumentInfo document = new DocumentInfo(
                id,
                documentInfo.getDocumentName(),
                documentInfo.getPath(),
                documentInfo.getPostDate(),
                documentInfo.getPosts(),
                documentInfo.getComments()
        );
        documents.put(id, document);
        return document;
    }

    @Override
    public List<DocumentInfo> list() {
        return new ArrayList<>(documents.values());
    }
}
