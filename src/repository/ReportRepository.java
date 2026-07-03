package repository;

import model.ReportData;
import java.util.List;

public interface ReportRepository {
    ReportData findById(long reportID);
    boolean save(ReportData reportData);
    boolean delete(long reportID);
    List<ReportData> findAll();
    ReportData findByAnalysisID(long analysisID);
    long count();
}
