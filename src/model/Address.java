package model;

/**
 * ============================================================================
 * Class: Address
 * ----------------------------------------------------------------------------
 * Quản lý thông tin địa chỉ giao hàng của Khách hàng.
 * ============================================================================
 */
public class Address {

    private int addressId;
    private String receiverName;
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;
    private String detailAddress;
    private boolean isDefault;

    /**
     * Tham chiếu ngược đến Customer sở hữu địa chỉ này.
     */
    private Customer customer;

    public Address() {
    }

    public Address(int addressId, String receiverName, String phoneNumber, 
                   String province, String district, String ward, 
                   String detailAddress, boolean isDefault) {
        this.addressId = addressId;
        this.receiverName = receiverName;
        this.phoneNumber = phoneNumber;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.detailAddress = detailAddress;
        this.isDefault = isDefault;
    }

    /**
     * Thêm địa chỉ này vào danh sách địa chỉ của Customer.
     */
    public void addAddress() {
        if (customer != null && !customer.getAddresses().contains(this)) {
            customer.getAddresses().add(this);
            if (this.isDefault) {
                this.setDefault();
            }
        }
    }

    /**
     * Cập nhật thông tin địa chỉ.
     */
    public void updateAddress() {
        System.out.println("Address updated locally: ID " + addressId);
    }

    /**
     * Xóa địa chỉ này khỏi danh sách địa chỉ của Customer.
     */
    public void deleteAddress() {
        if (customer != null) {
            customer.getAddresses().remove(this);
        }
    }

    /**
     * Thiết lập địa chỉ này làm địa chỉ mặc định, đặt các địa chỉ khác thành không mặc định.
     */
    public void setDefault() {
        this.isDefault = true;
        if (customer != null) {
            customer.setAdress(this); // Cập nhật địa chỉ mặc định chính của Customer
            for (Address addr : customer.getAddresses()) {
                if (addr != this) {
                    addr.setDefault(false);
                }
            }
        }
    }

    // Getters and Setters

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
