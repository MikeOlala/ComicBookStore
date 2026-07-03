package dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FilterForm {
    private String timeRange;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;

    public FilterForm() {}

    public FilterForm(String timeRange, String category, LocalDate startDate, LocalDate endDate) {
        this.timeRange = timeRange;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean validate() {
        return timeRange != null && !timeRange.trim().isEmpty()
                && startDate != null && endDate != null
                && !startDate.isAfter(endDate);
    }

    public Map<String, String> getFilters() {
        Map<String, String> filters = new HashMap<>();
        filters.put("timeRange", timeRange);
        filters.put("category", category);
        filters.put("startDate", startDate != null ? startDate.toString() : null);
        filters.put("endDate", endDate != null ? endDate.toString() : null);
        return filters;
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
