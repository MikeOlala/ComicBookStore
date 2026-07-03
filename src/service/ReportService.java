package service;

import model.AnalysisReport;
import model.ReportData;
import java.io.File;
import java.time.LocalDate;

public interface ReportService {
    AnalysisReport generateRevenueReport(LocalDate fromDate, LocalDate toDate, boolean simulateDbError) throws Exception;
    boolean exportExcel(AnalysisReport report);

    // Trend report methods (from drawio)
    File generateReport(long analysisID);
    ReportData getReportData(long analysisID);
    byte[] exportToPDF(ReportData data);
    byte[] exportToExcel(ReportData data);
}
