package com.document.pdf;

import com.document.pdf.repository.InMemoryDocumentRepository;
import com.document.pdf.repository.InMemoryRepository;
import com.document.pdf.service.PdfDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication implements CommandLineRunner {

    @Autowired
    PdfDocumentService pdfDocumentService;

    public static void main(String[] args){
        SpringApplication.run(SpringBootApplication.class);
    }

    @Override
    public void run(String... arg) throws Exception {
        pdfDocumentService.init();
    }

    @Bean
    InMemoryRepository inMemoryRepository(){
        return new InMemoryDocumentRepository();
    }

}
