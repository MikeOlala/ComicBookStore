package service.impl;

import database.FakeDatabase;
import model.AnalysisReport;
import model.Order;
import model.Payment;
import model.ReportData;
import model.TrendData;
import repository.impl.AnalysisRepositoryImpl;
import service.ReportService;
import state.ShippedState;
import util.OoxmlExporter;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class ReportServiceImpl implements ReportService {

    private static int reportCounter = 1;

    @Override
    public AnalysisReport generateRevenueReport(LocalDate fromDate, LocalDate toDate, boolean simulateDbError) throws Exception {
        System.out.println("[SERVICE] Generating revenue report from " + fromDate + " to " + toDate);

        // Flow 5c: Database connection error
        if (simulateDbError) {
            throw new java.sql.SQLException("Lỗi hệ thống, vui lòng thử lại sau");
        }

        double totalRevenue = 0;
        int transactionCount = 0;

        for (Order order : FakeDatabase.ORDERS) {
            LocalDate orderDate = order.getOrderDate().toLocalDate();

            if ((orderDate.isAfter(fromDate) || orderDate.isEqual(fromDate)) &&
                (orderDate.isBefore(toDate) || orderDate.isEqual(toDate))) {

                // BR2.1-1: Revenue criteria
                boolean isEligible = false;
                if (order.getState() instanceof ShippedState) {
                    isEligible = true;
                } else {
                    for (Payment p : FakeDatabase.PAYMENTS) {
                        if (p.getOrder() != null && p.getOrder().getOrderId() == order.getOrderId() && p.isPaid()) {
                            isEligible = true;
                            break;
                        }
                    }
                }

                if (isEligible) {
                    totalRevenue += order.getTotalAmount();
                    transactionCount++;
                }
            }
        }

        if (transactionCount == 0) {
            throw new NoSuchElementException("Không có dữ liệu giao dịch trong khoảng thời gian này");
        }

        System.out.println("[SERVICE] Report generated successfully. Eligible count: " + transactionCount + ", Total Revenue: $" + totalRevenue);

        return new AnalysisReport(
                reportCounter++,
                fromDate,
                toDate,
                totalRevenue,
                "REVENUE",
                LocalDateTime.now()
        );
    }

    @Override
    public boolean exportExcel(AnalysisReport report) {
        if (report == null) {
            System.out.println("[SERVICE] Export failed: Report is null.");
            return false;
        }
        System.out.println("[SERVICE] Exporting report to Excel sheet...");
        System.out.println("[SERVICE] Generating file: revenue_report_" + report.getStartDate() + "_to_" + report.getEndDate() + ".xlsx");
        System.out.println("[SERVICE] Data summarized: Start=" + report.getStartDate() +
                           ", End=" + report.getEndDate() +
                           ", Revenue=$" + report.getTotalRevenue() +
                           ", GeneratedAt=" + report.getCreatedAt());
        System.out.println("[SERVICE] Excel file successfully downloaded to client device.");
        return true;
    }

    /* ================================================================
       Trend report methods (from drawio)
       ================================================================ */
    @Override
    public File generateReport(long analysisID) {
        System.out.println("[SERVICE] Generating trend report for analysis #" + analysisID);
        AnalysisRepositoryImpl repo = new AnalysisRepositoryImpl();
        model.AnalysisResult result = repo.findById(analysisID);
        if (result == null) {
            System.out.println("[SERVICE] Analysis result not found!");
            return null;
        }
        try {
            return OoxmlExporter.exportTrendReport(result);
        } catch (Exception e) {
            System.out.println("[SERVICE] Report generation failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ReportData getReportData(long analysisID) {
        System.out.println("[SERVICE] Getting report data for analysis #" + analysisID);
        return FakeDatabase.REPORT_DATA_LIST.stream()
                .filter(rd -> rd.getAnalysisID() == analysisID)
                .findFirst().orElse(null);
    }

    @Override
    public byte[] exportToPDF(ReportData data) {
        if (data == null) {
            System.out.println("[SERVICE] Export PDF failed: data is null.");
            return new byte[0];
        }
        System.out.println("[SERVICE] Exporting report #" + data.getReportID() + " to PDF...");
        return "PDF content placeholder".getBytes();
    }

    @Override
    public byte[] exportToExcel(ReportData data) {
        if (data == null) {
            System.out.println("[SERVICE] Export Excel failed: data is null.");
            return new byte[0];
        }
        System.out.println("[SERVICE] Exporting report #" + data.getReportID() + " to Excel...");
        return "Excel content placeholder".getBytes();
    }
}
