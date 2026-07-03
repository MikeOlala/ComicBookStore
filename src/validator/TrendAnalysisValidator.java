package validator;

import dto.ValidationResult;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class TrendAnalysisValidator {

    private static final int MIN_DAYS = 7;
    private static final int MIN_TRANSACTIONS = 10;
    private static final int RETENTION_DAYS = 30;

    private TrendAnalysisValidator() {}

    public static ValidationResult validateTimeRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return new ValidationResult(false, "NULL_DATE", "Dates must not be null.");
        }
        if (startDate.isAfter(endDate)) {
            return new ValidationResult(false, "INVALID_RANGE", "Start date must be before end date.");
        }
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        if (daysBetween < MIN_DAYS) {
            return new ValidationResult(false, "RANGE_TOO_SHORT",
                    "Time range must be at least " + MIN_DAYS + " days.");
        }
        return new ValidationResult(true, null, null);
    }

    public static ValidationResult validateMinTransactions(int transactionCount) {
        if (transactionCount < MIN_TRANSACTIONS) {
            return new ValidationResult(false, "INSUFFICIENT_DATA",
                    "Need at least " + MIN_TRANSACTIONS + " transactions for analysis. Found: " + transactionCount);
        }
        return new ValidationResult(true, null, null);
    }

    public static boolean checkRetentionPeriod(LocalDate analysisDate) {
        if (analysisDate == null) return false;
        long daysSinceAnalysis = ChronoUnit.DAYS.between(analysisDate, LocalDate.now());
        return daysSinceAnalysis <= RETENTION_DAYS;
    }

    public static ValidationResult validateComparisonPeriods(String period1, String period2) {
        if (period1 == null || period2 == null) {
            return new ValidationResult(false, "NULL_PERIOD", "Comparison periods must not be null.");
        }
        if (period1.equals(period2)) {
            return new ValidationResult(false, "SAME_PERIOD", "Comparison periods must be different.");
        }
        return new ValidationResult(true, null, null);
    }
}
