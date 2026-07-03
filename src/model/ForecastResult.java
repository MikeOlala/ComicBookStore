package model;

public class ForecastResult {
    private long forecastID;
    private double predictedValue;
    private double confidence;
    private String period;

    public ForecastResult() {}

    public ForecastResult(long forecastID, double predictedValue, double confidence, String period) {
        this.forecastID = forecastID;
        this.predictedValue = predictedValue;
        this.confidence = confidence;
        this.period = period;
    }

    public String getInterpretation() {
        if (confidence > 80) {
            return "Dự báo độ tin cậy cao: " + String.format("%.1f", predictedValue)
                    + " (Confidence: " + String.format("%.1f", confidence) + "%)";
        } else if (confidence > 50) {
            return "Dự báo độ tin cậy trung bình: " + String.format("%.1f", predictedValue)
                    + " (Confidence: " + String.format("%.1f", confidence) + "%)";
        }
        return "Dự báo độ tin cậy thấp: " + String.format("%.1f", predictedValue)
                + " (Confidence: " + String.format("%.1f", confidence) + "%)";
    }

    public long getForecastID() { return forecastID; }
    public void setForecastID(long forecastID) { this.forecastID = forecastID; }
    public double getPredictedValue() { return predictedValue; }
    public void setPredictedValue(double predictedValue) { this.predictedValue = predictedValue; }
    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}
