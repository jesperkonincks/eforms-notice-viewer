package eu.europa.ted.eforms.viewer.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.FileSystemUtils;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import eu.europa.ted.eforms.viewer.NoticeViewer;
import eu.europa.ted.eforms.viewer.NoticeDocument;

@RestController
@RequestMapping("/api/v1")
public class NoticeViewerController {
    
    @PostMapping("/convert")
    public ResponseEntity<String> convertXmlToHtml(
            @RequestParam("language") String language,
            @RequestBody String xmlContent) {
        try {
            // Create temporary directory for processing
            String tempDirName = UUID.randomUUID().toString();
            Path tempDir = Files.createTempDirectory(tempDirName);
            
            // Save XML content to temp file
            Path xmlFile = tempDir.resolve("notice.xml");
            Files.writeString(xmlFile, xmlContent);
            
            // Initialize viewer and process the document
            NoticeViewer viewer = new NoticeViewer();
            NoticeDocument document = viewer.loadDocument(xmlFile.toFile());
            String html = viewer.generateHtml(document, language);
            
            // Cleanup
            FileSystemUtils.deleteRecursively(tempDir);
            
            return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
                
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing XML: " + e.getMessage());
        }
    }
}
