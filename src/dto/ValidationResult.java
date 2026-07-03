package dto;

public class ValidationResult {
    private boolean isValid;
    private String errorCode;
    private String errorMessage;

    public ValidationResult() {}

    public ValidationResult(boolean isValid, String errorCode, String errorMessage) {
        this.isValid = isValid;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ValidationResult(boolean isValid, String errorMessage) {
        this.isValid = isValid;
        this.errorCode = isValid ? "SUCCESS" : "ERROR";
        this.errorMessage = errorMessage;
    }

    public boolean isValid() { return isValid; }
    public void setValid(boolean valid) { isValid = valid; }
    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
