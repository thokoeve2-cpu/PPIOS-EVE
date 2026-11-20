package models;

import java.util.ArrayList;
import java.util.List;

public class Shipment {
    private String shipmentId;
    private Order order;
    private Delivery delivery;
    private String carrier;
    private String trackingNumber;
    private String dispatchDate;
    private String deliveryDate;
    private String status;
    private final List<ShipmentItem> items = new ArrayList<>();

    public Shipment(String shipmentId, Order order, Delivery delivery, String carrier, String trackingNumber, String dispatchDate) {
        this.shipmentId=shipmentId; this.order=order; this.delivery=delivery; this.carrier=carrier;
        this.trackingNumber=trackingNumber; this.dispatchDate=dispatchDate; this.status="Prepared";
    }
    public boolean addItem(ShipmentItem item) {
        if(item==null || !"Prepared".equals(status)) return false;
        items.add(item);
        return true;
    }
    public boolean dispatch() {
        if(items.isEmpty() || !"Prepared".equals(status)) return false;
        status="In Transit";
        return true;
    }
    public boolean markDelivered(String date) {
        if(!"In Transit".equals(status) || date==null || date.isBlank()) return false;
        deliveryDate=date;
        status="Delivered";
        return true;
    }
    public boolean reportIssue(String details) {
        if("Delivered".equals(status) || details==null || details.isBlank()) return false;
        status="Issue Reported";
        return true;
    }
    public int countItems() { return items.size(); }
    public int countUnits() { return items.stream().mapToInt(ShipmentItem::getQuantity).sum(); }
    public boolean hasTracking() { return trackingNumber!=null && !trackingNumber.isBlank(); }
    public String getStatus() { return status; }
}
