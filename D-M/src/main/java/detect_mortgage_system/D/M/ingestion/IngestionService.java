package detect_mortgage_system.D.M.ingestion;

import detect_mortgage_system.D.M.broker.MessageBroker;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class IngestionService {

    public void ingest(String filePath) {
        CsvFileReader.read(filePath)
                .forEach(record -> {
                    try {
                        MessageBroker.ingestionQueue.put(record);
                        System.out.println("Ingested: " + record.getRecordId());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
    }
}
