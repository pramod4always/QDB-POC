package com.document.pdf.controller;

import com.document.pdf.entity.DocumentInfo;
import com.document.pdf.repository.DocumentRepository;
import com.document.pdf.response.DocumentResponse;
import com.document.pdf.service.PdfDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
///@CrossOrigin("http://localhost:8081")
public class PdfDocumentController {

    @GetMapping(value = "document1")
    public String hello(){
        return "PDF Document service 12345";
    }

    @Autowired
    PdfDocumentService pdfDocumentService;

    @Autowired
    private DocumentRepository documentRepository;

    @PostMapping("/demo")
    public String saveFile() {
        System.out.println("demo file saveFile");
        try {
            DocumentInfo document = new DocumentInfo();
            document.setDocumentName("Demo.pdf");
            document.setPostDate(new Date().toString());
            document.setPath("C:/documents");
            documentRepository.save(document);
            return "Save successfully.";
        } catch (Exception e){
            return "Save failed."+e.getMessage();
        }
    }

    /*
     * This endpoint method is used to store uploaded pdf file on given location.
     *
     */
    @PostMapping("/upload")
    public ResponseEntity<DocumentResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            if(file.isEmpty()){
                message = "No file uploaded: ";
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DocumentResponse(message));
            }
            pdfDocumentService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new DocumentResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new DocumentResponse(message));
        }
    }

    /*
     * This endpoint method is used to get all uploaded pdf files from server location.
     */
    @GetMapping("/view-all")
    public ResponseEntity<List<DocumentInfo>> viewAllDocuments() throws IOException {
        System.out.println("---view all test----");
        List<DocumentInfo> documents = pdfDocumentService.findAllDocuments();
        return ResponseEntity.status(HttpStatus.OK).body(documents);
    }

    /*
     * This endpoint method is used to delete uploaded pdf files from server location
     * and h2 db based on given pdf file.
     */
    @DeleteMapping("/remove/{filename:.+}")
    public String remove(@PathVariable(value = "filename") String filename) {
        String message = "";
        System.out.println("====delete file====="+filename);
        try {
            boolean deleted = pdfDocumentService.delete(filename);
            if (deleted) {
                message = "Delete the file successfully: " + deleted;
                //return ResponseEntity.status(HttpStatus.OK).body(new DocumentResponse(message));
            } else {
                message = "The file does not exist!";
                //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DocumentResponse(message));
            }
            System.out.println(message);
            return message;
        } catch (Exception e) {
            message = "Could not delete the file: " + filename + ". Error: " + e.getMessage();
            return message;
        }
    }

    /*
     * This endpoint method is used to get all uploaded pdf files from server location.
     * Just to check compaire db entries and uploaded files.
     *
     * This method used internally for development team.
     *
     */
    @GetMapping("/view-uploaded")
    public ResponseEntity<List<File>> viewAllUploadedDocuments() throws IOException {
        System.out.println("---view all uploaded test----");
        List<File> fileList = new ArrayList<>();
        try{
            fileList = pdfDocumentService.findAllUploadedDocuments();
            return ResponseEntity.status(HttpStatus.OK).body(fileList);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fileList);
        }
    }

    @PostMapping("/post/{id}/{post}")
    private ResponseEntity<DocumentResponse> createPost(@PathVariable String id, @PathVariable String post) {
        System.out.println("==create post doc ==="+id);
        String message = "";
        try {
            pdfDocumentService.createPost(id, post);
            message = "Post created successfully for document is : " + id;
            return ResponseEntity.status(HttpStatus.OK).body(new DocumentResponse(message));
        } catch (Exception e) {
            message = "Post not created for document is: " + id + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new DocumentResponse(message));
        }
    }

    @PostMapping("/comment/{id}/{comment}")
    private ResponseEntity<DocumentResponse> createComment(@PathVariable String id, @PathVariable String comment) {
        System.out.println("==create post doc ===");
        String message = "";
        try {
            pdfDocumentService.createComment(id, comment);
            message = "comment created successfully for document is : " + id;
            return ResponseEntity.status(HttpStatus.OK).body(new DocumentResponse(message));
        } catch (Exception e) {
            message = "comment not created for document is: " + id + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new DocumentResponse(message));
        }
    }
}
