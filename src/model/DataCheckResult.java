package model;

public class DataCheckResult {
    private boolean sufficient;
    private int recordCount;
    private String message;

    public DataCheckResult() {}

    public DataCheckResult(boolean sufficient, int recordCount, String message) {
        this.sufficient = sufficient;
        this.recordCount = recordCount;
        this.message = message;
    }

    public boolean isSufficient() { return sufficient; }
    public void setSufficient(boolean sufficient) { this.sufficient = sufficient; }
    public int getRecordCount() { return recordCount; }
    public void setRecordCount(int recordCount) { this.recordCount = recordCount; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
