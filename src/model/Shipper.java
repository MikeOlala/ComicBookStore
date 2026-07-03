package model;

public class Shipper {
    private long shipperID;
    private String name;
    private String phone;
    private boolean available;

    public Shipper() {}

    public Shipper(long shipperID, String name, String phone, boolean available) {
        this.shipperID = shipperID;
        this.name = name;
        this.phone = phone;
        this.available = available;
    }

    public long getShipperID() { return shipperID; }
    public void setShipperID(long shipperID) { this.shipperID = shipperID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
