package com.example.demo;

import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfGenerationService {

    private final TemplateEngine templateEngine;

    public PdfGenerationService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(ResumeData resumeData, String templateName) throws DocumentException, IOException {
        // 1. Create a Thymeleaf context and add the resume data to it.
        Context context = new Context();
        context.setVariable("resume", resumeData);

        // 2. Process the HTML template with the data.
        // The path must be relative to the 'templates' folder.
        String htmlContent = templateEngine.process("resume-templates/" + templateName, context);

        // 3. Use Flying Saucer (ITextRenderer) to generate the PDF.
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

        // IMPORTANT: Set a base URL to resolve any relative paths in the template.
        // We point it to our templates directory.
        renderer.setDocumentFromString(htmlContent, "classpath:/templates/");
        renderer.layout();
        renderer.createPDF(outputStream);
        renderer.finishPDF();

        return outputStream.toByteArray();
    }
}
