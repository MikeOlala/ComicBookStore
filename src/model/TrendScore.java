package model;

public class TrendScore {
    private long scoreID;
    private double score;
    private TrendDirection direction;
    private double confidence;
    private String period;

    public TrendScore() {}

    public TrendScore(long scoreID, double score, TrendDirection direction, double confidence, String period) {
        this.scoreID = scoreID;
        this.score = score;
        this.direction = direction;
        this.confidence = confidence;
        this.period = period;
    }

    public void calculate(TrendData trendData) {
        double growthRate = trendData.calculateGrowthRate();

        if (growthRate > 20) {
            this.direction = TrendDirection.RISING;
            this.score = Math.min(100, growthRate);
        } else if (growthRate > 5) {
            this.direction = TrendDirection.RISING;
            this.score = 50 + growthRate / 2;
        } else if (growthRate < -20) {
            this.direction = TrendDirection.FALLING;
            this.score = Math.max(-100, growthRate);
        } else if (growthRate < -5) {
            this.direction = TrendDirection.FALLING;
            this.score = -50 + growthRate / 2;
        } else if (Math.abs(growthRate) <= 5) {
            this.direction = TrendDirection.STABLE;
            this.score = growthRate;
        } else {
            this.direction = TrendDirection.VOLATILE;
            this.score = Math.abs(growthRate);
        }

        int dataPoints = Math.max(1, trendData.getViews());
        this.confidence = Math.min(100, (dataPoints / 10.0) * 100);
        this.period = trendData.getTimeRange();
    }

    public String getInterpretation() {
        if (direction == TrendDirection.RISING) {
            return "Xu hướng tăng trưởng tốt (Score: " + String.format("%.1f", score) + ")";
        } else if (direction == TrendDirection.FALLING) {
            return "Xu hướng giảm, cần cải thiện (Score: " + String.format("%.1f", score) + ")";
        } else if (direction == TrendDirection.STABLE) {
            return "Xu hướng ổn định (Score: " + String.format("%.1f", score) + ")";
        }
        return "Xu hướng biến động mạnh (Score: " + String.format("%.1f", score) + ")";
    }

    public long getScoreID() { return scoreID; }
    public void setScoreID(long scoreID) { this.scoreID = scoreID; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public TrendDirection getDirection() { return direction; }
    public void setDirection(TrendDirection direction) { this.direction = direction; }
    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}
