package service.impl;

import database.FakeDatabase;
import model.Order;
import model.OrderItem;
import model.Shipper;
import model.TrackingInfo;
import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;
import service.ShippingService;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * ShippingServiceImpl
 * ----------------------------------------------------------------------------
 * Sequence Diagram steps:
 *   [OrderService -> ShippingService]  getAvailableShippers()
 *     ShippingService -> DB: query all available shippers
 *     DB --> ShippingService: return shipper list
 *
 *   [OrderService -> ShippingService]  11/11a. assignShipper / autoAssignShipper
 *     ShippingService -> DB: get shipper + generate tracking code
 *     ShippingService: 12. Tạo TrackingInfo (trackingCode + shipper)
 *     ShippingService --> OrderService: return TrackingInfo
 *
 *   [OrderService -> ShippingService]  12. generateTrackingCode()
 *     Check EF4: trùng mã -> regenerate
 * ============================================================================
 */
public class ShippingServiceImpl implements ShippingService {

    private final OrderRepository orderRepository;
    private static final List<String> USED_CODES = new java.util.ArrayList<>();

    public ShippingServiceImpl() {
        this.orderRepository = new OrderRepositoryImpl();
    }

    /* Sequence 10: Get list of available shippers from DB */
    @Override
    public List<Shipper> getAvailableShippers() {
        System.out.println("[SHIPPING] Getting available shippers...");
        return FakeDatabase.SHIPPERS.stream()
                .filter(Shipper::isAvailable)
                .collect(Collectors.toList());
    }

    /* Sequence 11: Assign a specific shipper to an order */
    @Override
    public TrackingInfo assignShippingUnit(int orderID, long shippingUnitID) {
        long shipperID = shippingUnitID;
        System.out.println("[SHIPPING] Assigning shipper #" + shipperID + " to order #" + orderID);
        Order order = orderRepository.findById(orderID);
        if (order == null) {
            System.out.println("[SHIPPING] Order not found!");
            return null;
        }

        Shipper shipper = FakeDatabase.SHIPPERS.stream()
                .filter(s -> s.getShipperID() == shipperID)
                .findFirst()
                .orElse(null);

        if (shipper == null) {
            System.out.println("[SHIPPING] Shipper not found!");
            return null;
        }

        /* Sequence 12: Generate tracking code + create TrackingInfo */
        String trackingCode = generateTrackingCode();
        TrackingInfo info = new TrackingInfo(trackingCode, shipper, "ASSIGNED");
        order.setTrackingInfo(info);
        return info;
    }

    /* Sequence 11a: Auto-assign the first available shipper */
    @Override
    public TrackingInfo autoAssignShippingUnit(int orderID) {
        System.out.println("[SHIPPING] Auto-assigning shipper for order #" + orderID);
        List<Shipper> available = getAvailableShippers();
        if (available.isEmpty()) {
            /* Sequence 10b: No available shippers */
            System.out.println("[SHIPPING] No available shippers!");
            return null;
        }

        Shipper selected = available.get(new Random().nextInt(available.size()));
        return assignShippingUnit(orderID, selected.getShipperID());
    }

    /* Sequence 12: Generate unique tracking code (check EF4: duplicate) */
    @Override
    public String generateTrackingCode() {
        String code = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        while (USED_CODES.contains(code)) {
            code = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        USED_CODES.add(code);
        return code;
    }

    /* EF4: Regenerate if tracking code is duplicate */
    @Override
    public String regenerateTrackingCode(String oldCode) {
        USED_CODES.remove(oldCode);
        return generateTrackingCode();
    }

    @Override
    public byte[] createShippingLabel(int orderID) {
        Order order = orderRepository.findById(orderID);
        if (order == null) {
            System.out.println("[SHIPPING] Order #" + orderID + " not found!");
            return new byte[0];
        }
        TrackingInfo info = order.getTrackingInfo();
        String label = "VAN DON: " + (info != null ? info.getTrackingCode() : "N/A")
                + "\nDon hang: #" + orderID
                + "\nKhach hang: " + order.getCustomer().getFullName()
                + "\nDia chi: " + order.getShippingAddress()
                + "\nDon vi van chuyen: " + (info != null ? info.getShipper().getName() : "N/A");
        System.out.println("[SHIPPING] Created shipping label for order #" + orderID);
        return label.getBytes();
    }
}
