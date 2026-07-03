package model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class TrendReport {
    private long reportID;
    private LocalDateTime generatedDate;
    private TrendScore trendScore;
    private ForecastResult forecast;
    private List<String> recommendations;
    private ReportStatus status;

    public TrendReport() {}

    public TrendReport(long reportID, LocalDateTime generatedDate, TrendScore trendScore,
                       ForecastResult forecast, List<String> recommendations, ReportStatus status) {
        this.reportID = reportID;
        this.generatedDate = generatedDate;
        this.trendScore = trendScore;
        this.forecast = forecast;
        this.recommendations = recommendations;
        this.status = status;
    }

    public void generate() {
        System.out.println("[TrendReport] Generating trend report #" + reportID);
        this.generatedDate = LocalDateTime.now();
        this.status = ReportStatus.GENERATED;
    }

    public File export(String format) {
        System.out.println("[TrendReport] Exporting report #" + reportID + " to " + format + " format");
        this.status = ReportStatus.EXPORTED;
        return new File("trend_report_" + reportID + "." + format.toLowerCase());
    }

    public long getReportID() { return reportID; }
    public void setReportID(long reportID) { this.reportID = reportID; }
    public LocalDateTime getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(LocalDateTime generatedDate) { this.generatedDate = generatedDate; }
    public TrendScore getTrendScore() { return trendScore; }
    public void setTrendScore(TrendScore trendScore) { this.trendScore = trendScore; }
    public ForecastResult getForecast() { return forecast; }
    public void setForecast(ForecastResult forecast) { this.forecast = forecast; }
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
    public ReportStatus getStatus() { return status; }
    public void setStatus(ReportStatus status) { this.status = status; }
}
