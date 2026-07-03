package repository.impl;

import database.FakeDatabase;
import model.ReportData;
import repository.ReportRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ReportRepositoryImpl implements ReportRepository {

    private static long nextReportId = 1;

    @Override
    public ReportData findById(long reportID) {
        for (ReportData rd : FakeDatabase.REPORT_DATA_LIST) {
            if (rd.getReportID() == reportID) {
                return rd;
            }
        }
        return null;
    }

    @Override
    public boolean save(ReportData reportData) {
        reportData.setReportID(nextReportId++);
        return FakeDatabase.REPORT_DATA_LIST.add(reportData);
    }

    @Override
    public boolean delete(long reportID) {
        return FakeDatabase.REPORT_DATA_LIST.removeIf(r -> r.getReportID() == reportID);
    }

    @Override
    public List<ReportData> findAll() {
        return FakeDatabase.REPORT_DATA_LIST;
    }

    @Override
    public ReportData findByAnalysisID(long analysisID) {
        return FakeDatabase.REPORT_DATA_LIST.stream()
                .filter(r -> r.getAnalysisID() == analysisID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public long count() {
        return FakeDatabase.REPORT_DATA_LIST.size();
    }
}
