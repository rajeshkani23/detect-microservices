package detect_mortgage_system.D.M.Service;

import detect_mortgage_system.D.M.broker.MessageBroker;
import detect_mortgage_system.D.M.model.DataRecord;

public class ValidationService implements  Runnable{
    @Override
    public void run() {
        while(true){
            try {
                DataRecord record= MessageBroker.ingestionQueue.take();
                if(isvalid(record)){
                    MessageBroker.processingQueue.put(record);
                    System.out.println("Valid record :"+ record.getRecordId());
                }
                else {
                    MessageBroker.errorQueue.put(record);
                    System.out.println("Invalid record: " + record.getRecordId());
                }
                     } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    private boolean isvalid(DataRecord record) {
        return record.getRecordId() != null && !record.getRecordId().isBlank()
                && record.getCustomerName() != null && !record.getCustomerName().isBlank()
                &&  record.getAmount() > 0
                && record.getType() != null;
    }
    }

