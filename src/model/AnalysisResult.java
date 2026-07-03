package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AnalysisResult {
    private long resultID;
    private TrendData trendData;
    private TrendScore trendScore;
    private LocalDate analysisDate;
    private AnalysisStatus status;

    public AnalysisResult() {}

    public AnalysisResult(long resultID, TrendData trendData, TrendScore trendScore, LocalDate analysisDate, AnalysisStatus status) {
        this.resultID = resultID;
        this.trendData = trendData;
        this.trendScore = trendScore;
        this.analysisDate = analysisDate;
        this.status = status;
    }

    public Map<String, Object> getResult() {
        Map<String, Object> result = new HashMap<>();
        result.put("resultID", resultID);
        result.put("category", trendData != null ? trendData.getCategory() : "N/A");
        result.put("trendDirection", trendScore != null ? trendScore.getDirection() : "N/A");
        result.put("trendScore", trendScore != null ? trendScore.getScore() : 0);
        result.put("confidence", trendScore != null ? trendScore.getConfidence() : 0);
        result.put("interpretation", trendScore != null ? trendScore.getInterpretation() : "N/A");
        result.put("analysisDate", analysisDate);
        result.put("totalRevenue", trendData != null ? trendData.getRevenue() : 0);
        result.put("totalSales", trendData != null ? trendData.getSales() : 0);
        result.put("totalViews", trendData != null ? trendData.getViews() : 0);
        return result;
    }

    public long getResultID() { return resultID; }
    public void setResultID(long resultID) { this.resultID = resultID; }
    public TrendData getTrendData() { return trendData; }
    public void setTrendData(TrendData trendData) { this.trendData = trendData; }
    public TrendScore getTrendScore() { return trendScore; }
    public void setTrendScore(TrendScore trendScore) { this.trendScore = trendScore; }
    public LocalDate getAnalysisDate() { return analysisDate; }
    public void setAnalysisDate(LocalDate analysisDate) { this.analysisDate = analysisDate; }
    public AnalysisStatus getStatus() { return status; }
    public void setStatus(AnalysisStatus status) { this.status = status; }
}
