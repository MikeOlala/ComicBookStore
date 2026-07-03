package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TrendData {
    private long dataID;
    private String category;
    private LocalDate date;
    private int views;
    private int sales;
    private double revenue;
    private String timeRange;

    public TrendData() {}

    public TrendData(long dataID, String category, LocalDate date, int views, int sales, double revenue, String timeRange) {
        this.dataID = dataID;
        this.category = category;
        this.date = date;
        this.views = views;
        this.sales = sales;
        this.revenue = revenue;
        this.timeRange = timeRange;
    }

    public double calculateGrowthRate() {
        if (views == 0) return 0;
        return ((double) sales / views) * 100;
    }

    public String getTrendDirection() {
        double growthRate = calculateGrowthRate();
        if (growthRate > 10) return "RISING";
        if (growthRate < -10) return "FALLING";
        if (growthRate >= -10 && growthRate <= 10) return "STABLE";
        return "VOLATILE";
    }

    public long getDataID() { return dataID; }
    public void setDataID(long dataID) { this.dataID = dataID; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }
    public int getSales() { return sales; }
    public void setSales(int sales) { this.sales = sales; }
    public double getRevenue() { return revenue; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
    public String getTimeRange() { return timeRange; }
    public void setTimeRange(String timeRange) { this.timeRange = timeRange; }
}
