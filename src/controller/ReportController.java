package controller;

import model.AnalysisReport;
import service.ReportService;
import service.impl.ReportServiceImpl;

import java.time.LocalDate;

public class ReportController {

    private final ReportService reportService;

    public ReportController() {
        this.reportService = new ReportServiceImpl();
    }

    public AnalysisReport generateRevenueReport(LocalDate fromDate, LocalDate toDate, boolean simulateDbError) throws Exception {
        return reportService.generateRevenueReport(fromDate, toDate, simulateDbError);
    }

    public boolean exportExcel(AnalysisReport report) {
        return reportService.exportExcel(report);
    }
}
