package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AnalysisReport {
    private int reportId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalRevenue;
    private String reportType;
    private LocalDateTime createdAt;

    public AnalysisReport() {}

    public AnalysisReport(int reportId, LocalDate startDate, LocalDate endDate, double totalRevenue, String reportType, LocalDateTime createdAt) {
        this.reportId = reportId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalRevenue = totalRevenue;
        this.reportType = reportType;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getReportId() { return reportId; }
    public void setReportId(int reportId) { this.reportId = reportId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
