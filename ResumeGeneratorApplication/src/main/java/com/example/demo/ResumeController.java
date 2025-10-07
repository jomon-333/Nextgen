package com.example.demo;

import com.lowagie.text.DocumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class ResumeController {

    private final PdfGenerationService pdfGenerationService;
    private final GeneratedResumeRepository resumeRepository;

    public ResumeController(PdfGenerationService pdfGenerationService, GeneratedResumeRepository resumeRepository) {
        this.pdfGenerationService = pdfGenerationService;
        this.resumeRepository = resumeRepository;
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
    public ResponseEntity<byte[]> downloadPdf(@ModelAttribute ResumeData resumeData,
                                            @RequestParam("template") String templateName,
                                            @RequestParam("photoUpload") MultipartFile photoUpload) {
        try {
            // Handle the photo upload if a file is provided
            if (photoUpload != null && !photoUpload.isEmpty()) {
                String base64Photo = Base64.getEncoder().encodeToString(photoUpload.getBytes());
                resumeData.setPhoto(base64Photo);
            }

            // Generate the PDF
            byte[] pdfBytes = pdfGenerationService.generatePdf(resumeData, templateName);

            // Save the generated resume to the database for the history page
            GeneratedResume generatedResume = new GeneratedResume();
            String fileName = "resume-" + System.currentTimeMillis() + ".pdf";
            generatedResume.setFileName(fileName);
            generatedResume.setPdfData(pdfBytes);
            generatedResume.setCreatedAt(LocalDateTime.now());
            resumeRepository.save(generatedResume);

            // Prepare the response for the browser to download the file
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Return an error message if something goes wrong
            String errorMessage = "Error generating PDF: " + e.getMessage();
            return new ResponseEntity<>(errorMessage.getBytes(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/history")
    public String showHistory(Model model) {
        // Fetch all resumes from the database, ordered by the newest first
        List<GeneratedResume> resumes = resumeRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("resumes", resumes);
        return "history";
    }

    @GetMapping("/history/view/{id}")
    public ResponseEntity<byte[]> viewPdf(@PathVariable Long id) {
        // Find a specific resume by its ID to display it
        Optional<GeneratedResume> optionalResume = resumeRepository.findById(id);
        if (optionalResume.isPresent()) {
            GeneratedResume resume = optionalResume.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // 'inline' tells the browser to display the PDF instead of downloading it
            headers.setContentDispositionFormData("inline", resume.getFileName());
            return new ResponseEntity<>(resume.getPdfData(), headers, HttpStatus.OK);
        } else {
            // Return a "Not Found" error if the ID doesn't exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

