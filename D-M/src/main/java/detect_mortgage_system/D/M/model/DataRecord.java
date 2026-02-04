package detect_mortgage_system.D.M.model;

public class DataRecord {
    private final String recordID;
    private final String CustomerName;
    private final double amount;
    private final RecordType type;

    public DataRecord(String recordID, String customerName, double amount, RecordType type) {
        this.recordID = recordID;
        CustomerName = customerName;
        this.amount = amount;
        this.type = type;
    }

    public String getRecordID() {
        return recordID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public double getAmount() {
        return amount;
    }

    public RecordType getType() {
        return type;
    }

    public String getRecordId() {
        return  recordID;
    }
}
