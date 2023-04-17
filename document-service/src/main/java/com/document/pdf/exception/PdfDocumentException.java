package com.document.pdf.exception;

public class PdfDocumentException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public PdfDocumentException() {
        super();
    }
    public PdfDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
    public PdfDocumentException(String message) {
        super(message);
    }
    public PdfDocumentException(Throwable cause) {
        super(cause);
    }
}
