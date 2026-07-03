package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardData {
    private long dashboardID;
    private List<String> charts;
    private Map<String, Object> metrics;
    private LocalDateTime lastUpdated;

    public DashboardData() {
        this.charts = new ArrayList<>();
        this.metrics = new HashMap<>();
    }

    public DashboardData(long dashboardID, List<String> charts, Map<String, Object> metrics, LocalDateTime lastUpdated) {
        this.dashboardID = dashboardID;
        this.charts = charts != null ? charts : new ArrayList<>();
        this.metrics = metrics != null ? metrics : new HashMap<>();
        this.lastUpdated = lastUpdated;
    }

    public void render() {
        System.out.println("=== TREND ANALYSIS DASHBOARD ===");
        System.out.println("Dashboard ID: " + dashboardID);
        System.out.println("Last Updated: " + lastUpdated);
        System.out.println("--- Charts ---");
        for (String chart : charts) {
            System.out.println("  - " + chart);
        }
        System.out.println("--- Metrics ---");
        for (Map.Entry<String, Object> entry : metrics.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("================================");
    }

    public long getDashboardID() { return dashboardID; }
    public void setDashboardID(long dashboardID) { this.dashboardID = dashboardID; }
    public List<String> getCharts() { return charts; }
    public void setCharts(List<String> charts) { this.charts = charts; }
    public Map<String, Object> getMetrics() { return metrics; }
    public void setMetrics(Map<String, Object> metrics) { this.metrics = metrics; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
