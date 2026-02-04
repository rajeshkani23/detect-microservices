package detect_mortgage_system.D.M.broker;

import detect_mortgage_system.D.M.model.DataRecord;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageBroker {
    public static final BlockingQueue<DataRecord> ingestionQueue =
            new LinkedBlockingQueue<detect_mortgage_system.D.M.model.DataRecord>();

    public static final BlockingQueue<DataRecord> processingQueue =
            new LinkedBlockingQueue<DataRecord>();

    public static final BlockingQueue<DataRecord> errorQueue =
            new LinkedBlockingQueue<DataRecord>();

}
