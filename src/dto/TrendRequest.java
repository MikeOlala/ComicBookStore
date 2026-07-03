package dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TrendRequest {
    private String timeRange;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;

    public TrendRequest() {}

    public TrendRequest(String timeRange, String category, LocalDate startDate, LocalDate endDate) {
        this.timeRange = timeRange;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ValidationResult validate() {
        if (timeRange == null || timeRange.trim().isEmpty()) {
            return new ValidationResult(false, "INVALID_TIME_RANGE", "Time range is required.");
        }
        if (startDate == null) {
            return new ValidationResult(false, "INVALID_START_DATE", "Start date is required.");
        }
        if (endDate == null) {
            return new ValidationResult(false, "INVALID_END_DATE", "End date is required.");
        }
        if (startDate.isAfter(endDate)) {
            return new ValidationResult(false, "INVALID_DATE_RANGE", "Start date must be before end date.");
        }
        return new ValidationResult(true, null, null);
    }

    public Map<String, String> getParameters() {
        Map<String, String> params = new HashMap<>();
        params.put("timeRange", timeRange);
        params.put("category", category);
        params.put("startDate", startDate != null ? startDate.toString() : null);
        params.put("endDate", endDate != null ? endDate.toString() : null);
        return params;
    }

    public String getTimeRange() { return timeRange; }
    public void setTimeRange(String timeRange) { this.timeRange = timeRange; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
