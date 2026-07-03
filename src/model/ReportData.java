package model;

import java.time.LocalDateTime;

public class ReportData {
    private long reportID;
    private long analysisID;
    private LocalDateTime generatedDate;
    private String format;
    private byte[] data;

    public ReportData() {}

    public ReportData(long reportID, long analysisID, LocalDateTime generatedDate, String format, byte[] data) {
        this.reportID = reportID;
        this.analysisID = analysisID;
        this.generatedDate = generatedDate;
        this.format = format;
        this.data = data;
    }

    public void generate() {
        System.out.println("[ReportData] Generating report data for analysis #" + analysisID);
        this.generatedDate = LocalDateTime.now();
        this.data = ("Report for Analysis #" + analysisID).getBytes();
    }

    public byte[] getReport() {
        return data;
    }

    public long getReportID() { return reportID; }
    public void setReportID(long reportID) { this.reportID = reportID; }
    public long getAnalysisID() { return analysisID; }
    public void setAnalysisID(long analysisID) { this.analysisID = analysisID; }
    public LocalDateTime getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(LocalDateTime generatedDate) { this.generatedDate = generatedDate; }
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
}
