
package com.document.pdf;

import com.document.pdf.controller.PdfDocumentController;
import com.document.pdf.service.PdfDocumentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;

@RunWith(MockitoJUnitRunner.class)
public class PdfDocumentControllerTest {

    private InputStream is;
    private MockMvc mockMvc;

    @Mock
    PdfDocumentService pdfDocumentService;

    @Spy
    @InjectMocks
    PdfDocumentController pdfDocumentController;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(pdfDocumentController).build();
        is = pdfDocumentController.getClass().getClassLoader().getResourceAsStream("excel.xlsx");
    }

    @Test
    public void testUploadPdfDocument() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "DOCUMENT_01.pdf", "multipart/form-data", is);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertNotNull(result.getResponse().getContentAsString());
        Assert.assertEquals("DOCUMENT_01.pdf", result.getResponse().getContentAsString());
    }

    @Test
    public void testUploadWhenNotPdfDocument() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "excel.xlsx\"", "multipart/form-data", is);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();
        Assert.assertEquals(400, result.getResponse().getStatus());
        Assert.assertNotNull(result.getResponse().getContentAsString());
        Assert.assertNotEquals("DOCUMENT_04.pdf", result.getResponse().getContentAsString());
    }
}

