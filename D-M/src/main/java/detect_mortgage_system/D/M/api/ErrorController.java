package detect_mortgage_system.D.M.api;

import detect_mortgage_system.D.M.broker.MessageBroker;
import detect_mortgage_system.D.M.model.DataRecord;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/errors")
public class ErrorController {


    @GetMapping
    public List<DataRecord> getFailedRecords() {
        return new ArrayList<>(MessageBroker.errorQueue);
    }


    @PostMapping("/retry")
    public String retryFailedRecords() {

        int retried = 0;

        while (!MessageBroker.errorQueue.isEmpty()) {
            DataRecord record = MessageBroker.errorQueue.poll();
            if (record != null) {
                MessageBroker.processingQueue.offer(record);
                retried++;
            }
        }

        return "Retried records count: " + retried;
    }
}
