package service;

import model.AnalysisReport;
import java.time.LocalDate;

public interface ReportService {
    AnalysisReport generateRevenueReport(LocalDate fromDate, LocalDate toDate, boolean simulateDbError) throws Exception;
    boolean exportExcel(AnalysisReport report);
}
