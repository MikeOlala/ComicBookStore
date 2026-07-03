package model;

public class ComparisonResult {
    private TrendData period1Data;
    private TrendData period2Data;
    private double growthRate;
    private TrendDirection trendDirection;

    public ComparisonResult() {}

    public ComparisonResult(TrendData period1Data, TrendData period2Data, double growthRate, TrendDirection trendDirection) {
        this.period1Data = period1Data;
        this.period2Data = period2Data;
        this.growthRate = growthRate;
        this.trendDirection = trendDirection;
    }

    public double getGrowthRate() { return growthRate; }
    public void setGrowthRate(double growthRate) { this.growthRate = growthRate; }
    public TrendDirection getTrendDirection() { return trendDirection; }
    public void setTrendDirection(TrendDirection trendDirection) { this.trendDirection = trendDirection; }
    public TrendData getPeriod1Data() { return period1Data; }
    public void setPeriod1Data(TrendData period1Data) { this.period1Data = period1Data; }
    public TrendData getPeriod2Data() { return period2Data; }
    public void setPeriod2Data(TrendData period2Data) { this.period2Data = period2Data; }
}
