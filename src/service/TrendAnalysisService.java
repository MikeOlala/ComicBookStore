package service;

import dto.TrendRequest;
import dto.ValidationResult;
import model.*;

import java.util.List;

public interface TrendAnalysisService {
    DashboardResponse processTrendAnalysis(TrendRequest request) throws Exception;
    ValidationResult validate(TrendRequest request);
    TrendData getTrendData(String timeRange, String category);
    DataCheckResult checkData(List<TrendData> dataset);
    AnalysisResult analyzeData(TrendData trendData);
    TrendScore computeTrendScore(TrendData trendData);
    DashboardData generateDashboardData();
    AnalysisResult saveAnalysisResult(AnalysisResult result);
}
