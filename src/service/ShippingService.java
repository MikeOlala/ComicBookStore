package service;

import model.Shipper;
import model.TrackingInfo;
import java.util.List;

public interface ShippingService {
    List<Shipper> getAvailableShippers();
    TrackingInfo assignShippingUnit(int orderID, long shippingUnitID);
    TrackingInfo autoAssignShippingUnit(int orderID);
    String generateTrackingCode();
    String regenerateTrackingCode(String oldCode);
    byte[] createShippingLabel(int orderID);
}
