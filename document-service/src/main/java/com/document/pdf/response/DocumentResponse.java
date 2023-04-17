package com.document.pdf.response;

public class DocumentResponse {

    private String message;

    public DocumentResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
