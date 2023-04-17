package com.document.pdf.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.document.pdf.entity.DocumentInfo;

import org.springframework.web.multipart.MultipartFile;

public interface PdfDocumentService {

    public void init();

    public void save(MultipartFile file);

    public List<DocumentInfo> findAllDocuments() throws IOException;

    public String createPost(String id, String post) throws Exception;

    public String createComment(String id, String comment) throws Exception;

    public List<File> findAllUploadedDocuments() throws IOException;

    public boolean delete(String filename);

}

