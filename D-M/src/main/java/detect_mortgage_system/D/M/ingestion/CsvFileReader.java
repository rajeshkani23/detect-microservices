package detect_mortgage_system.D.M.ingestion;

import detect_mortgage_system.D.M.model.DataRecord;
import detect_mortgage_system.D.M.model.RecordType;
import org.springframework.core.io.ClassPathResource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class CsvFileReader {

    public static Stream<DataRecord> read(String filePath) {
        try {
            BufferedReader reader;

            if (filePath.endsWith(".csv") && new File(filePath).exists()) {
                reader = Files.newBufferedReader(Paths.get(filePath));
            } else {
                ClassPathResource resource = new ClassPathResource(filePath);
                reader = new BufferedReader(
                        new InputStreamReader(resource.getInputStream())
                );
            }

            return reader.lines()
                    .map(line -> line.split(","))
                    .filter(data -> data.length == 4)
                    .map(data -> {
                        try {
                            return new DataRecord(
                                    data[0].trim(),
                                    data[1].trim(),
                                    Double.parseDouble(data[2].trim()),
                                    RecordType.valueOf(data[3].trim().toUpperCase())
                            );
                        } catch (Exception e) {
                            System.err.println("❌ Invalid CSV row skipped: " + String.join(",", data));
                            return null;
                        }
                    })
                    .filter(Objects::nonNull);

        } catch (Exception e) {
            System.err.println("❌ Failed to read CSV: " + filePath);
            return Stream.empty();
        }
    }


}
