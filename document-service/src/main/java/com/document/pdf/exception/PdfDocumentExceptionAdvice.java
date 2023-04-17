package com.document.pdf.exception;

import com.document.pdf.response.DocumentResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

@RestControllerAdvice
public class PdfDocumentExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<DocumentResponse> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new DocumentResponse("Pdf document too large!"));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<DocumentResponse> handleFileNotFoundException(FileNotFoundException exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DocumentResponse("Pdf document not found!"));
    }

    @ExceptionHandler(PdfDocumentException.class)
    public ResponseEntity<DocumentResponse> noCityFound(PdfDocumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DocumentResponse("No document found!"));
    }

    /*@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        return new ResponseEntity("Incorrect value for ....", HttpStatus.BAD_REQUEST);
    }*/
}
