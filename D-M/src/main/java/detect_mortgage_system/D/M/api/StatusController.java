package detect_mortgage_system.D.M.api;

import detect_mortgage_system.D.M.Util.ShutdownManager;
import detect_mortgage_system.D.M.broker.MessageBroker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @GetMapping
    public Map<String, Object> systemStatus() {

        Map<String, Object> status = new HashMap<>();

        status.put("ingestionQueueSize", MessageBroker.ingestionQueue.size());
        status.put("processingQueueSize", MessageBroker.processingQueue.size());
        status.put("errorQueueSize", MessageBroker.errorQueue.size());
        status.put("shuttingDown", ShutdownManager.SHUTTING_DOWN.get());

        return status;
    }
    @GetMapping("/health")
    public String health() {
        return "UP";
    }

}
