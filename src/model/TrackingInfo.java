package model;

public class TrackingInfo {
    private String trackingCode;
    private Shipper shipper;
    private String status;

    public TrackingInfo() {}

    public TrackingInfo(String trackingCode, Shipper shipper, String status) {
        this.trackingCode = trackingCode;
        this.shipper = shipper;
        this.status = status;
    }

    public String getTrackingCode() { return trackingCode; }
    public void setTrackingCode(String trackingCode) { this.trackingCode = trackingCode; }
    public Shipper getShipper() { return shipper; }
    public void setShipper(Shipper shipper) { this.shipper = shipper; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
