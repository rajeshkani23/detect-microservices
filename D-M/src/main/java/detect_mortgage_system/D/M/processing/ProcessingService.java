package detect_mortgage_system.D.M.processing;

import detect_mortgage_system.D.M.Util.ShutdownManager;
import detect_mortgage_system.D.M.broker.MessageBroker;
import detect_mortgage_system.D.M.model.DataRecord;
import detect_mortgage_system.D.M.model.RecordType;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ProcessingService implements Runnable {

    private final ExecutorService executorService =
            Executors.newFixedThreadPool(3);

    @Override
    public void run() {
        System.out.println("⚙️ Processing start with thread pool");

        try {
            while (!ShutdownManager.SHUTTING_DOWN.get()) {

                DataRecord record = MessageBroker.processingQueue.take();

                executorService.submit(() -> processRecord(record));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            shutdownExecutor();
        }
    }

    private void shutdownExecutor() {
        System.out.println("🛑 Shutting down executor service");

        executorService.shutdown();
    }

    private void processRecord(DataRecord record) {
        System.out.println(
                "🧵 Thread: " + Thread.currentThread().getName()
                        + " | Processing record: " + record.getRecordId()
        );

        if (record.getType() == RecordType.DETECT) {
            detectLogic(record);
        } else if (record.getType() == RecordType.MORTGAGE) {
            mortgageLogic(record);
        }
    }

    private void detectLogic(DataRecord record) {
        sleep();
        System.out.println("🔍 DETECT processed for: " + record.getRecordId());
    }

    private void mortgageLogic(DataRecord record) {
        sleep();
        System.out.println("🏠 MORTGAGE processed for: " + record.getRecordId());
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
