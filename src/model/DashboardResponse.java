package model;

import java.util.List;

public class DashboardResponse {
    private DashboardData dashboard;
    private AnalysisResult analysisResult;
    private TrendReport trendReport;
    private List<String> alerts;

    public DashboardResponse() {}

    public DashboardResponse(DashboardData dashboard, AnalysisResult analysisResult,
                             TrendReport trendReport, List<String> alerts) {
        this.dashboard = dashboard;
        this.analysisResult = analysisResult;
        this.trendReport = trendReport;
        this.alerts = alerts;
    }

    public DashboardData getDashboard() { return dashboard; }
    public void setDashboard(DashboardData dashboard) { this.dashboard = dashboard; }
    public AnalysisResult getAnalysisResult() { return analysisResult; }
    public void setAnalysisResult(AnalysisResult analysisResult) { this.analysisResult = analysisResult; }
    public TrendReport getTrendReport() { return trendReport; }
    public void setTrendReport(TrendReport trendReport) { this.trendReport = trendReport; }
    public List<String> getAlerts() { return alerts; }
    public void setAlerts(List<String> alerts) { this.alerts = alerts; }
}
