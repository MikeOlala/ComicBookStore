package repository.impl;

import database.FakeDatabase;
import model.AnalysisResult;
import model.TrendData;
import repository.AnalysisRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnalysisRepositoryImpl implements AnalysisRepository {

    private static long nextAnalysisId = 1;

    @Override
    public AnalysisResult findById(long analysisID) {
        for (AnalysisResult result : FakeDatabase.ANALYSIS_RESULTS) {
            if (result.getResultID() == analysisID) {
                return result;
            }
        }
        return null;
    }

    @Override
    public boolean save(AnalysisResult analysisResult) {
        analysisResult.setResultID(nextAnalysisId++);
        return FakeDatabase.ANALYSIS_RESULTS.add(analysisResult);
    }

    @Override
    public boolean delete(long analysisID) {
        return FakeDatabase.ANALYSIS_RESULTS.removeIf(a -> a.getResultID() == analysisID);
    }

    @Override
    public List<AnalysisResult> findAll() {
        return FakeDatabase.ANALYSIS_RESULTS;
    }

    @Override
    public List<AnalysisResult> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return FakeDatabase.ANALYSIS_RESULTS.stream()
                .filter(a -> {
                    LocalDate d = a.getAnalysisDate();
                    return (d.isEqual(startDate) || d.isAfter(startDate))
                            && (d.isEqual(endDate) || d.isBefore(endDate));
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<AnalysisResult> findByCategory(String category) {
        return FakeDatabase.ANALYSIS_RESULTS.stream()
                .filter(a -> a.getTrendData() != null
                        && category.equals(a.getTrendData().getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrendData> findTrendDataByDateRange(LocalDate startDate, LocalDate endDate) {
        return FakeDatabase.TREND_DATA_LIST.stream()
                .filter(td -> {
                    LocalDate d = td.getDate();
                    return (d.isEqual(startDate) || d.isAfter(startDate))
                            && (d.isEqual(endDate) || d.isBefore(endDate));
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TrendData> findTrendDataByCategory(String category) {
        if (category == null || category.trim().isEmpty() || "ALL".equalsIgnoreCase(category)) {
            return FakeDatabase.TREND_DATA_LIST;
        }
        return FakeDatabase.TREND_DATA_LIST.stream()
                .filter(td -> category.equals(td.getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return FakeDatabase.ANALYSIS_RESULTS.size();
    }
}
