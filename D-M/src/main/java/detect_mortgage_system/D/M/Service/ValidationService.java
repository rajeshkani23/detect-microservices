package detect_mortgage_system.D.M.Service;

import detect_mortgage_system.D.M.Util.ShutdownManager;
import detect_mortgage_system.D.M.broker.MessageBroker;
import detect_mortgage_system.D.M.model.DataRecord;
import org.springframework.stereotype.Service;

@Service
public class ValidationService implements Runnable {

    @Override
    public void run() {
        while (!ShutdownManager.SHUTTING_DOWN.get()) {
            try {
                DataRecord record = MessageBroker.ingestionQueue.take();

                if (isValid(record)) {
                    MessageBroker.processingQueue.put(record);
                    System.out.println("✅ Valid record: " + record.getRecordId());
                } else {
                    MessageBroker.errorQueue.put(record);
                    System.out.println("❌ Invalid record: " + record.getRecordId());
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("🛑 Validation service stopped");
    }

    private boolean isValid(DataRecord record) {
        return record.getRecordId() != null && !record.getRecordId().isBlank()
                && record.getCustomerName() != null && !record.getCustomerName().isBlank()
                && record.getAmount() > 0
                && record.getType() != null;
    }
}
