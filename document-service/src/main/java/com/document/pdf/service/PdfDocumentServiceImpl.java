package com.document.pdf.service;

import com.document.pdf.entity.DocumentInfo;
import com.document.pdf.exception.PdfDocumentException;
import com.document.pdf.repository.DocumentRepository;
import com.document.pdf.repository.InMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PdfDocumentServiceImpl implements PdfDocumentService{

    private static final Path root = Paths.get("C:/documents");

    private static Pattern fileExtnPtrn = Pattern.compile("([^\\s]+(\\.(?i)(pdf))$)");

    @Autowired
    InMemoryRepository inMemoryRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    /*
    * This method is used to check pdf file extension, if file uploading other than pdf
    * method will throw error.
    *
    * If pdf file is uploaded then document will be store in server and entry inserted
    * into temp in memory cache database.
    *
    */
    @Override
    public void save(MultipartFile file) throws PdfDocumentException {
        String message = "";
        try {
            boolean res = isPdfDocumentOnly(file.getOriginalFilename());
            System.out.println("isPdfDocumentOnly ::: "+res);
            if(isPdfDocumentOnly(file.getOriginalFilename())) {
                Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
                DocumentInfo document = new DocumentInfo();
                document.setDocumentName(this.root.resolve(file.getOriginalFilename()).getFileName().toString());
                document.setPostDate(new Date().toString());
                document.setPath(this.root.resolve(file.getOriginalFilename()).toString());
                documentRepository.save(document);
            } else {
                throw new PdfDocumentException("Only Pdf file is allowed to upload.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /*
    * This method used to validate uploaded file extension.
    */
    private static boolean isPdfDocumentOnly(String fileName){
        Matcher matcher = fileExtnPtrn.matcher(fileName);
        return matcher.matches() ? true : false;
    }

    @Override
    public List<DocumentInfo> findAllDocuments() throws IOException {
        return documentRepository.findAll();
    }

    @Override
    public boolean delete(String filename) {
        boolean result = false;
        try {
            Path file = root.resolve(filename);
            System.out.println("delete file ::::: "+file.getFileName().toString());
            if(Files.deleteIfExists(file)){
                DocumentInfo documentInfo = findAllDocuments().stream()
                        .filter(document -> filename.equals(document.getDocumentName()))
                        .findFirst()
                        .orElse(null);
                System.out.println("documentInfo.getId() :::: "+documentInfo.getId());
                documentRepository.delete(documentInfo);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    Optional<DocumentInfo> findById(Long id){
        return documentRepository.findById(id);
    }

    @Override
    public String createPost(String id, String post) throws Exception{
        try{
            DocumentInfo documentInfo = findById(Long.valueOf(id)).get();
            documentInfo.setPostDate(new Date().toString());
            documentInfo.setPosts(post);
            System.out.println("Post  is ::: "+documentInfo.getPosts());
            documentRepository.save(documentInfo);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    public String createComment(String id, String comments) throws Exception{
        try{
            DocumentInfo documentInfo = findById(Long.valueOf(id)).get();
            documentInfo.setPostDate(new Date().toString());
            documentInfo.setComments(comments);
            System.out.println("comments  is ::: "+documentInfo.getComments());
            documentRepository.save(documentInfo);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    public List<File> findAllUploadedDocuments() throws PdfDocumentException, IOException {
        System.out.println("----findAllDocuments----");
        String directory = "C:/documents";
        List<Path> pathList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(Paths.get(directory))) {
            pathList = stream.map(Path::normalize)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        pathList.forEach(System.out::println);
        List<File> fileList = listFiles(directory);
        return fileList;
    }

    private static List<File> listFiles(final String directory) {
        if (directory == null) {
            return Collections.EMPTY_LIST;
        }
        List<File> fileList = new ArrayList<>();
        File[] files = new File(directory).listFiles();
        for (File element : files) {
            if (element.isDirectory()) {
                fileList.addAll(listFiles(element.getPath()));
            } else {
                fileList.add(element);
            }
        }
        return fileList;
    }

}
