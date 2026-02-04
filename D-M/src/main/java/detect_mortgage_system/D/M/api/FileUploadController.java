package detect_mortgage_system.D.M.api;

import detect_mortgage_system.D.M.ingestion.IngestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    private final IngestionService ingestionService;

    public FileUploadController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/csv")
    public ResponseEntity<String> uploadCsv(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("CSV file is empty");
        }


        File tempFile = File.createTempFile("upload-", ".csv");
        file.transferTo(tempFile);


        ingestionService.ingest(tempFile.getAbsolutePath());

        return ResponseEntity.ok(
                "CSV uploaded and ingestion started: " + file.getOriginalFilename()
        );
    }
}
