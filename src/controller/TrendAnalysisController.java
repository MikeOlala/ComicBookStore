package controller;

import dto.FilterForm;
import dto.TrendRequest;
import model.AnalysisResult;
import model.DashboardData;
import model.DashboardResponse;
import repository.impl.AnalysisRepositoryImpl;
import service.ReportService;
import service.TrendAnalysisService;
import service.impl.ReportServiceImpl;
import service.impl.TrendAnalysisServiceImpl;
import util.OoxmlExporter;

import java.io.File;

/**
 * ============================================================================
 * TrendAnalysisController
 * ----------------------------------------------------------------------------
 * Sequence Diagram steps:
 *   [UI -> Controller]  getFilterForm()
 *   [UI -> Controller]  analyzeTrend(newFilters)
 *   [UI -> Controller]  getDefaultDashboard()
 *   [UI -> Controller]  exportReport(analysisID)
 * ============================================================================
 */
public class TrendAnalysisController {

    private final TrendAnalysisService trendAnalysisService;
    private final ReportService reportService;

    public TrendAnalysisController() {
        this.trendAnalysisService = new TrendAnalysisServiceImpl();
        this.reportService = new ReportServiceImpl();
    }

    /* Sequence step: UI -> Controller -> return FilterForm */
    public FilterForm getFilterForm() {
        System.out.println("[CONTROLLER] Getting filter form...");
        return new FilterForm();
    }

    /* Sequence step: UI -> Controller -> Service -> processTrendAnalysis() */
    public DashboardResponse analyzeTrend(TrendRequest request) throws Exception {
        System.out.println("[CONTROLLER] Analyzing trend...");
        return trendAnalysisService.processTrendAnalysis(request);
    }

    /* Sequence step: UI -> Controller -> ReportService -> exportReport */
    public File exportReport(long analysisID) {
        System.out.println("[CONTROLLER] Exporting report for analysis #" + analysisID + "...");
        AnalysisRepositoryImpl repo = new AnalysisRepositoryImpl();
        AnalysisResult result = repo.findById(analysisID);
        if (result == null) {
            System.out.println("[CONTROLLER] Analysis result #" + analysisID + " not found!");
            return null;
        }
        try {
            File file = OoxmlExporter.exportTrendReport(result);
            System.out.println("[CONTROLLER] Report exported: " + file.getPath());
            return file;
        } catch (Exception e) {
            System.out.println("[CONTROLLER] Export failed: " + e.getMessage());
            return null;
        }
    }

    /* Sequence step: UI -> Controller -> Service -> getDefaultDashboard */
    public DashboardData getDefaultDashboard() {
        System.out.println("[CONTROLLER] Getting default dashboard...");
        return trendAnalysisService.generateDashboardData();
    }
}
