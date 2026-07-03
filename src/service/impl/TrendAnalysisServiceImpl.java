package service.impl;

import dto.TrendRequest;
import dto.ValidationResult;
import model.*;
import repository.AnalysisRepository;
import repository.impl.AnalysisRepositoryImpl;
import service.TrendAnalysisService;
import validator.TrendAnalysisValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * TrendAnalysisServiceImpl
 * ----------------------------------------------------------------------------
 * Sequence Diagram:
 *
 *   Controller -> Service: processTrendAnalysis(request)
 *     Service -> Validator          : 7a. validate(request)
 *     Service -> AnalysisRepository  : 8. findTrendDataByDateRange/Category
 *     Service                        : 8a. checkData(dataset)
 *     Service                        : 9. analyzeData(trendData)
 *     Service -> TrendScore          : 10. computeTrendScore(trendData)
 *     Service -> AnalysisRepository  : 11. saveAnalysisResult()
 *     Service -> DashboardData       : 11b. generateDashboardData()
 *     Service -> TrendReport         : generateTrendReport()
 *   Service --> Controller: return DashboardResponse
 * ============================================================================
 */
public class TrendAnalysisServiceImpl implements TrendAnalysisService {

    private final AnalysisRepository analysisRepository;

    public TrendAnalysisServiceImpl() {
        this.analysisRepository = new AnalysisRepositoryImpl();
    }

    @Override
    public DashboardResponse processTrendAnalysis(TrendRequest request) throws Exception {
        System.out.println("[SERVICE] Starting trend analysis...");

        /* Sequence 7a: Validate parameters -> BusinessRuleValidator */
        ValidationResult validation = validate(request);
        if (!validation.isValid()) {
            throw new IllegalArgumentException("Validation failed: " + validation.getErrorMessage());
        }

        /* Sequence 8: Collect data from AnalysisRepository -> Database */
        System.out.println("[SERVICE] Collecting data from database...");
        List<TrendData> dataset = collectTrendData(request);

        /* Sequence 8a: Check if data is sufficient (min 10 transactions) */
        DataCheckResult dataCheck = checkData(dataset);
        if (!dataCheck.isSufficient()) {
            throw new NoSuchElementException("Insufficient data: " + dataCheck.getMessage());
        }

        /* Aggregate all trend data points into one dataset */
        TrendData aggregatedTrendData = aggregateTrendData(dataset, request);

        /* Sequence 9: Analyze data -> create AnalysisResult with status PROCESSING */
        System.out.println("[SERVICE] Analyzing data...");
        AnalysisResult analysisResult = analyzeData(aggregatedTrendData);

        /* Sequence 10: Compute trend score (RISING/FALLING/STABLE/VOLATILE) */
        System.out.println("[SERVICE] Computing trend score...");
        TrendScore trendScore = computeTrendScore(aggregatedTrendData);
        analysisResult.setTrendScore(trendScore);

        /* Sequence 11b (step 27): Generate DashboardData with charts & metrics */
        System.out.println("[SERVICE] Generating dashboard...");
        DashboardData dashboard = generateDashboardData();
        dashboard.getMetrics().put("Trend Score", String.format("%.1f", trendScore.getScore()));
        dashboard.getMetrics().put("Direction", trendScore.getDirection().name());
        dashboard.getMetrics().put("Confidence", String.format("%.1f%%", trendScore.getConfidence()));
        dashboard.getMetrics().put("Total Revenue", "$" + String.format("%.0f", aggregatedTrendData.getRevenue()));
        dashboard.getMetrics().put("Total Sales", aggregatedTrendData.getSales());
        dashboard.getMetrics().put("Total Views", aggregatedTrendData.getViews());
        dashboard.getCharts().add("Revenue Trend Chart");
        dashboard.getCharts().add("Category Comparison Chart");
        dashboard.getCharts().add("Sales vs Views Chart");

        /* Sequence 11 (step 28): Save AnalysisResult to AnalysisRepository -> Database */
        System.out.println("[SERVICE] Saving analysis result...");
        saveAnalysisResult(analysisResult);

        /* Generate TrendReport with ForecastResult + Recommendations */
        TrendReport trendReport = generateTrendReport(analysisResult, trendScore);

        /* Check for alerts: falling trend or low confidence */
        List<String> alerts = new ArrayList<>();
        if (trendScore.getDirection() == TrendDirection.FALLING) {
            alerts.add("ALERT: Doanh thu đang giảm, cần có biện pháp cải thiện!");
        }
        if (trendScore.getConfidence() < 50) {
            alerts.add("WARNING: Độ tin cậy phân tích thấp, dữ liệu chưa đủ lớn.");
        }

        /* Return DashboardResponse -> Controller -> UI */
        return new DashboardResponse(dashboard, analysisResult, trendReport, alerts);
    }

    /* Sequence 8 helper: query AnalysisRepository by category + date range */
    private List<TrendData> collectTrendData(TrendRequest request) {
        String category = request.getCategory();
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        List<TrendData> byCategory = analysisRepository.findTrendDataByCategory(category);
        return byCategory.stream()
                .filter(td -> {
                    LocalDate d = td.getDate();
                    return (d.isEqual(startDate) || d.isAfter(startDate))
                            && (d.isEqual(endDate) || d.isBefore(endDate));
                })
                .collect(Collectors.toList());
    }

    /* Aggregate multiple TrendData records into one summary */
    private TrendData aggregateTrendData(List<TrendData> dataset, TrendRequest request) {
        TrendData aggregated = new TrendData();
        aggregated.setCategory(request.getCategory() != null ? request.getCategory() : "ALL");
        aggregated.setTimeRange(request.getTimeRange());
        aggregated.setDate(request.getStartDate());

        int totalViews = 0;
        int totalSales = 0;
        double totalRevenue = 0;

        for (TrendData td : dataset) {
            totalViews += td.getViews();
            totalSales += td.getSales();
            totalRevenue += td.getRevenue();
        }

        aggregated.setViews(totalViews);
        aggregated.setSales(totalSales);
        aggregated.setRevenue(totalRevenue);

        return aggregated;
    }

    /* Sequence 7a: validate request via TrendAnalysisValidator */
    @Override
    public ValidationResult validate(TrendRequest request) {
        if (request == null) {
            return new ValidationResult(false, "NULL_REQUEST", "Request must not be null.");
        }
        ValidationResult rangeResult = TrendAnalysisValidator.validateTimeRange(
                request.getStartDate(), request.getEndDate());
        if (!rangeResult.isValid()) {
            return rangeResult;
        }
        return request.validate();
    }

    @Override
    public TrendData getTrendData(String timeRange, String category) {
        System.out.println("[SERVICE] Getting trend data for timeRange=" + timeRange + ", category=" + category);
        List<TrendData> data = analysisRepository.findTrendDataByCategory(category);
        if (data.isEmpty()) return null;
        TrendData aggregated = new TrendData();
        aggregated.setCategory(category);
        aggregated.setTimeRange(timeRange);
        int tv = 0, ts = 0; double tr = 0;
        for (TrendData d : data) { tv += d.getViews(); ts += d.getSales(); tr += d.getRevenue(); }
        aggregated.setViews(tv); aggregated.setSales(ts); aggregated.setRevenue(tr);
        return aggregated;
    }

    /* Sequence 8a: check if dataset has >= 10 records (minTransactions) */
    @Override
    public DataCheckResult checkData(List<TrendData> dataset) {
        if (dataset == null || dataset.isEmpty()) {
            return new DataCheckResult(false, 0, "No data found for the selected filters.");
        }
        int totalRecords = dataset.size();
        ValidationResult txResult = TrendAnalysisValidator.validateMinTransactions(totalRecords);
        return new DataCheckResult(
                txResult.isValid(),
                totalRecords,
                txResult.isValid()
                        ? "Data sufficient: " + totalRecords + " records found."
                        : txResult.getErrorMessage()
        );
    }

    /* Sequence 9: create AnalysisResult with PROCESSING status */
    @Override
    public AnalysisResult analyzeData(TrendData trendData) {
        AnalysisResult result = new AnalysisResult();
        result.setTrendData(trendData);
        result.setAnalysisDate(LocalDate.now());
        result.setStatus(AnalysisStatus.PROCESSING);
        return result;
    }

    /* Sequence 10: compute TrendScore via TrendScore.calculate() -> TrendDirection */
    @Override
    public TrendScore computeTrendScore(TrendData trendData) {
        TrendScore score = new TrendScore();
        score.calculate(trendData);
        return score;
    }

    /* Sequence 11b: generate empty DashboardData shell */
    @Override
    public DashboardData generateDashboardData() {
        DashboardData dashboard = new DashboardData();
        dashboard.setDashboardID(System.currentTimeMillis());
        dashboard.setLastUpdated(LocalDateTime.now());
        return dashboard;
    }

    /* Sequence 11: save COMPLETED AnalysisResult to repository */
    @Override
    public AnalysisResult saveAnalysisResult(AnalysisResult result) {
        result.setStatus(AnalysisStatus.COMPLETED);
        analysisRepository.save(result);
        return result;
    }

    /* Generate TrendReport with ForecastResult + recommendations */
    private TrendReport generateTrendReport(AnalysisResult analysisResult, TrendScore trendScore) {
        TrendReport report = new TrendReport();
        report.setReportID(System.currentTimeMillis());
        report.setTrendScore(trendScore);
        report.setGeneratedDate(LocalDateTime.now());
        report.setStatus(ReportStatus.DRAFT);

        /* Build forecast: predicted revenue = current * 1.1, confidence = score * 0.8 */
        ForecastResult forecast = new ForecastResult();
        forecast.setForecastID(System.currentTimeMillis());
        double predictedRevenue = analysisResult.getTrendData().getRevenue() * 1.1;
        forecast.setPredictedValue(predictedRevenue);
        forecast.setConfidence(trendScore.getConfidence() * 0.8);
        forecast.setPeriod("NEXT_30_DAYS");
        report.setForecast(forecast);

        /* Generate recommendations based on trend direction */
        List<String> recommendations = new ArrayList<>();
        if (trendScore.getDirection() == TrendDirection.RISING) {
            recommendations.add("Tăng cường nhập hàng cho danh mục đang tăng trưởng.");
            recommendations.add("Đẩy mạnh marketing cho các sản phẩm bán chạy.");
        } else if (trendScore.getDirection() == TrendDirection.FALLING) {
            recommendations.add("Xem xét giảm giá hoặc khuyến mãi để kích cầu.");
            recommendations.add("Đánh giá lại chiến lược kinh doanh cho danh mục đang giảm.");
        } else {
            recommendations.add("Duy trì chiến lược hiện tại.");
            recommendations.add("Theo dõi biến động thị trường để điều chỉnh kịp thời.");
        }
        report.setRecommendations(recommendations);

        report.generate();
        return report;
    }
}
