package repository;

import model.AnalysisResult;
import model.TrendData;
import java.time.LocalDate;
import java.util.List;

public interface AnalysisRepository {
    AnalysisResult findById(long analysisID);
    boolean save(AnalysisResult analysisResult);
    boolean delete(long analysisID);
    List<AnalysisResult> findAll();
    List<AnalysisResult> findByDateRange(LocalDate startDate, LocalDate endDate);
    List<AnalysisResult> findByCategory(String category);
    List<TrendData> findTrendDataByDateRange(LocalDate startDate, LocalDate endDate);
    List<TrendData> findTrendDataByCategory(String category);
    long count();
}
