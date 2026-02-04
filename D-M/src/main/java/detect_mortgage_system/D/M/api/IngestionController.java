package detect_mortgage_system.D.M.api;

import detect_mortgage_system.D.M.ingestion.IngestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api/ingestion")
public class IngestionController {

    private final IngestionService ingestionService;

    public IngestionController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }


    @Operation(summary = "Start CSV ingestion",
            description = "Triggers asynchronous ingestion of a CSV file")
    @PostMapping("/start")
    public ResponseEntity<String> startIngestion(
            @RequestParam(defaultValue = "records.csv") String fileName) {
        ingestionService.ingest(fileName);
        return ResponseEntity.ok("Ingestion started for file: " + fileName);
    }


}
