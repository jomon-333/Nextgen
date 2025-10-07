package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResumeController {

    private final PdfGenerationService pdfGenerationService;

    public ResumeController(PdfGenerationService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/generate")
    public String showGeneratorForm(Model model) {
        model.addAttribute("resumeData", new ResumeData());
        return "generator";
    }

    @PostMapping("/download")
    public ResponseEntity<?> downloadPdf(@ModelAttribute ResumeData resumeData, @RequestParam("template") String templateName) {
        try {
            byte[] pdfBytes = pdfGenerationService.generatePdf(resumeData, templateName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "resume.pdf");
            headers.setContentLength(pdfBytes.length);

            return ResponseEntity.ok().headers(headers).body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Error generating PDF: " + e.getMessage();
            return ResponseEntity.internalServerError().body(errorMessage);
        }
    }
}
