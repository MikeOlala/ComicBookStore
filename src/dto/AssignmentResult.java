package dto;

import model.Shipper;
import model.TrackingInfo;

public class AssignmentResult {
    private int orderID;
    private boolean success;
    private String message;
    private Shipper shipper;
    private TrackingInfo trackingInfo;

    public AssignmentResult() {}

    public AssignmentResult(int orderID, boolean success, String message, Shipper shipper, TrackingInfo trackingInfo) {
        this.orderID = orderID;
        this.success = success;
        this.message = message;
        this.shipper = shipper;
        this.trackingInfo = trackingInfo;
    }

    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Shipper getShipper() { return shipper; }
    public void setShipper(Shipper shipper) { this.shipper = shipper; }
    public TrackingInfo getTrackingInfo() { return trackingInfo; }
    public void setTrackingInfo(TrackingInfo trackingInfo) { this.trackingInfo = trackingInfo; }
}
